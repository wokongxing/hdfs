package com.xiaolin.mapreduce.CreteLog;

import com.xiaolin.mapreduce.utils.IpUtil;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CreateLogData {

    @Test
    public void getip(){
        String str="1.0.1.0|1.0.3.255|16777472|16778239|亚洲|中国|福建|福州||电信|350100|China|CN|119.306239|26.075302";
        String[] splits = str.split("\\|");
        System.out.println(splits[2]);
    }

    @Test
    public void CreateData(){

        int size =10000;
        ArrayList<String> list = new ArrayList<String>(10000);
        //先设置固定的域名
        //String domianNames[]={"www.baidu.com","www.ruozedata.com","www.alibaba.com"};
        //错误数据
        String flows[]={"0","1","2","b","3","4","5","6","c","7","8","9","a"};
        StringBuffer stringBuffer;
        Random rdint = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss Z");
        for (int i=0;i<size;i++){
            stringBuffer =new StringBuffer();
            //获取随机域名
            //int index = rdint.nextInt(3);
            stringBuffer.append("ruozedata.com").append("\t");
            //获取随机时间
            stringBuffer.append("[").append(sdf.format(new Date())).append("]").append("\t");
            //获取随机流量,制造随机错误数据
            int flow=rdint.nextInt(100000)+1000;
            stringBuffer.append(flow).append(flows[rdint.nextInt(13)]).append("\t");
            //获取随机IP
            stringBuffer.append(getRandomIp()).append("\n");
            list.add(stringBuffer.toString());
        }
        //写入数据到本地
        writeDataToLoacl(list);

    }
    //写入数据到本地
    public void writeDataToLoacl( ArrayList<String> list) {
        String dstpath="data/access02.log";
        FileOutputStream fileOutputStream=null;
        FileChannel fileChannel=null;
        ByteBuffer buffer =null;
        int size =list.size();
        try {
            fileOutputStream = new FileOutputStream(new File(dstpath));
            fileChannel=fileOutputStream.getChannel();

            for (int i=0;i<size;i++){
                buffer = ByteBuffer.wrap(list.get(i).toString().getBytes());
                fileChannel.write(buffer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileChannel!=null){
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    /*
     * 随机生成国内IP地址
     */
    public static String getRandomIp() {

        // ip范围
        int[][] range = { { 607649792, 608174079 },// 36.56.0.0-36.63.255.255
                { 1038614528, 1039007743 },// 61.232.0.0-61.237.255.255
                { 1783627776, 1784676351 },// 106.80.0.0-106.95.255.255
                { 2035023872, 2035154943 },// 121.76.0.0-121.77.255.255
                { 2078801920, 2079064063 },// 123.232.0.0-123.235.255.255
                { -1950089216, -1948778497 },// 139.196.0.0-139.215.255.255
                { -1425539072, -1425014785 },// 171.8.0.0-171.15.255.255
                { -1236271104, -1235419137 },// 182.80.0.0-182.92.255.255
                { -770113536, -768606209 },// 210.25.0.0-210.47.255.255
                { -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = numip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成ip地址
     */
    public static String numip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }

}
