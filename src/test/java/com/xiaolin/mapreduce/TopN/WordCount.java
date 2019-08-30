package com.xiaolin.mapreduce.TopN;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public  static class ServerMap extends Mapper<LongWritable, Text, ServerInfo, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split(",");
            if (splits.length==3){
                context.write(new ServerInfo(splits[0],splits[1],new BigDecimal(splits[2])),NullWritable.get());
            }
        }
    }

    public static class ServerReduce extends Reducer<ServerInfo,NullWritable,ServerInfo,NullWritable>{
        //int TOPN=2;
        int TOPN=1;
        Map<String,Integer> map = new HashMap();
        @Override
        protected void reduce(ServerInfo key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            for (NullWritable value:values){
                String userid = key.getUserid();
                if (map.containsKey(userid)){
                    if (map.get(userid)<TOPN){
                        context.write(key,NullWritable.get());
                        map.put(userid, map.get(userid)+1);
                    }
                }else {
                    context.write(key,NullWritable.get());
                    map.put(userid, 1);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取配置信息
        Configuration configuration = new Configuration();
        //获取job实例
        Job job = Job.getInstance(configuration);
        //指定jar所在位置
        job.setJarByClass(WordCount.class);
        //置顶job使用的map/reduce业务类
        job.setMapperClass(ServerMap.class);
        job.setReducerClass(ServerReduce.class);
        //指定MAP输出类型
        job.setMapOutputKeyClass(ServerInfo.class);
        job.setMapOutputValueClass(NullWritable.class);
        //指定最终数据输出类型
        job.setOutputKeyClass(ServerInfo.class);
        job.setOutputValueClass(NullWritable.class);
        //指定job获取数据的路径 以及输出路径
        FileInputFormat.setInputPaths(job,new Path("data/Topn.txt"));
        FileOutputFormat.setOutputPath(job,new Path("out/result01.txt"));

        System.exit(job.waitForCompletion(true)?0:1);


    }
}
