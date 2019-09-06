package com.xiaolin.mapreduce.WordCount;

import com.xiaolin.mapreduce.YARN.ServerInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Progressable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/*在yarn上运行本地代码*/
public class WordCount {

    public  static class ServerMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(new Text(value),new IntWritable(1));
        }
    }

    public static class ServerReduce extends Reducer<Text,IntWritable, Text,IntWritable>{

        protected void reduce(final Text key, final Iterable<IntWritable> values, final Reducer.Context context) throws IOException, InterruptedException {
            int sum=0;
            for (IntWritable value:values){
                sum = value.get()+sum;
            }
            context.write(key,new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取配置信息
        Configuration configuration = new Configuration();
        //配置已在资源目录配置好了,以下可以省略
//        configuration.set("yarn.nodemanager.aux-services","mapreduce_shuffle");
//        configuration.set("yarn.resourcemanager.hostname", "hadoop001");
//
        configuration.set("mapreduce.framework.name", "yarn");//集群的方式运行
        configuration.set("mapreduce.app-submission.cross-platform", "true"); //跨平台提交
        //配置系统提交的用户名称
        System.setProperty("HADOOP_USER_NAME","hadoop");
        String outpath ="outdata/result02.txt";
        FileSystem fileSystem = FileSystem.get(configuration);
        if (fileSystem.exists(new Path(outpath))){
            fileSystem.delete(new Path(outpath));
        }

        //获取job实例
        Job job = Job.getInstance(configuration);
        //指定jar所在位置
        job.setJar("D:\\IDEAspaces\\hdfs\\out\\artifacts\\hdfs_jar\\hdfs.jar");
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
        FileInputFormat.setInputPaths(job,new Path("data/test.txt"));

        FileOutputFormat.setOutputPath(job,new Path(outpath));

        System.exit(job.waitForCompletion(true)?0:1);


    }
}
