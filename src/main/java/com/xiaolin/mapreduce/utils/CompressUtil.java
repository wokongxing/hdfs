package com.xiaolin.mapreduce.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 讲师：PK哥   交流群：545916944
 *
 * 压缩：原始数据压缩
 * 解压：压缩后的数据解压开
 */
public class CompressUtil {

    public static void main(String[] args) throws Exception {
//        compress("data/access.log","org.apache.hadoop.io.compress.BZip2Codec");
        decompress("data/access.log.bz2");
        //decompress("data/log_20190920.1568961787946.bz2");
    }

    /**
     * 解压缩
     */
    public static void decompress(String filename) throws Exception {

        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(filename));
        if(codec == null) {
            System.out.println("不能找到codec:" + filename);
            return;
        }

        CompressionInputStream cis = codec.createInputStream(new FileInputStream(new File(filename)));
        FileOutputStream fos = new FileOutputStream(new File(filename+".xiaolin"));
        IOUtils.copyBytes(cis, fos, 1024*1024*5, false);

        fos.close();
        cis.close();
    }

    /**
     * 压缩测试：
     * 1）通过输入流读入
     * 2）通过输出流写出
     * 3）IOUtils.copyBytes
     */
    public static void compress(String filename, String method) throws Exception {

        FileInputStream fis = new FileInputStream(new File(filename));

        Class<?> codecClass = Class.forName(method);

        CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, new Configuration());

        FileOutputStream fos = new FileOutputStream(new File(filename + codec.getDefaultExtension()));
        CompressionOutputStream cos = codec.createOutputStream(fos);
        IOUtils.copyBytes(fis, cos, 1024*1024*5, false);

        cos.close();
        fos.close();
        fis.close();
    }
}
