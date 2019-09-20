package com.xiaolin.mapreduce.flume;

import com.alibaba.fastjson.JSON;
import com.xiaolin.mapreduce.utils.IpUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

public class flumeEtl {
    // 面向套路编程
    public static void main(String[] args) throws Exception {
        // step1: 获取Job对象
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        String input = args[0];
        String outpath = args[1];
        FileSystem fileSystem = FileSystem.get(configuration);
        if (fileSystem.exists(new Path(outpath))){
            fileSystem.delete(new Path(outpath));
        }

        job.setJarByClass(flumeEtl.class);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //把ip放入环形缓冲区
        job.addCacheFile(new URI("hdfs://114.67.98.145:9000/data/ip.txt"));

        FileInputFormat.setInputPaths(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(outpath));

        boolean result = job.waitForCompletion(true);
        CounterGroup etlCounter = job.getCounters().getGroup("flumeEtl");
        Iterator<Counter> iterator = etlCounter.iterator();
        while(iterator.hasNext()) {
            Counter next = iterator.next();
            System.out.println("~~~~~华丽的分割线~~~~~" + next.getName() + " : " + next.getValue());
        }
        System.exit(result?0:1);

    }

    public static class MyMapper
            extends Mapper<LongWritable, Text, Text, NullWritable> {

        private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss Z");
        List<IpInfo> list = new ArrayList<IpInfo>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            URI[] cacheFiles = context.getCacheFiles();
            String path = cacheFiles[0].getPath().toString();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            String line ;
            IpInfo ipInfo =null;
            while(StringUtils.isNotEmpty(line = reader.readLine())) {
                String[] splits = line.split("\\|");
                ipInfo = new IpInfo();
                ipInfo.setStart(Long.parseLong(splits[2]));
                ipInfo.setEnd(Long.parseLong(splits[3]));
                ipInfo.setProvince(splits[6]);
                ipInfo.setCity(splits[7]);
                ipInfo.setIsp(splits[9]);
                list.add(ipInfo);
            }
            IOUtils.closeStream(reader);
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String json = value.toString();
            AccessLog accessLog = JSON.parseObject(json, AccessLog.class);
            //获取数据总数量
            context.getCounter("flumeEtl","access_totals").increment(1);
            try {
                Date date = format.parse(accessLog.getTime().substring(1,accessLog.getTime().length()-1));
                //解析日期
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DATE);
                accessLog.setYear(year+"");
                accessLog.setMonth(month<10?"0"+month:month+"");
                accessLog.setDay(day<10?"0"+day:day+"");

                // 去除掉不符合要求的流量字段
                Long sizes = Long.valueOf(accessLog.getFlow());
                accessLog.setSizes(sizes);

                //解析ip
                IpInfo ipInfo = IpUtil.searchIp(list,accessLog.getIp());
                accessLog.setProvice(ipInfo.getProvince());
                accessLog.setCity(ipInfo.getCity());
                accessLog.setIsp(ipInfo.getIsp());

                //获取正确的数据条数
                context.getCounter("flumeEtl","access_true_counts").increment(1);

                context.write(new Text(json), NullWritable.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
