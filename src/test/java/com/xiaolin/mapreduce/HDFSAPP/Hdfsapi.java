package com.xiaolin.mapreduce.HDFSAPP;


import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.eclipse.jdt.internal.eval.EvaluationResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class Hdfsapi<main> {

    public static final String HDFS_PATH="hdfs://114.67.98.145:9000";
    Configuration configuration =null;
    
     FileSystem fileSystem = null;

    @Before
    public void initdata() throws Exception{
         configuration = new Configuration();
         configuration.set("dfs.replication","1");
         configuration.set("dfs.client.use.datanode.hostname","true");
         fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration,"hadoop");
    }

    @After
    public void destorydata() throws Exception{
        fileSystem.close();
    }

    //创建目录
    @Test
    public void mkdir() throws Exception {
        boolean flag = fileSystem.mkdirs(new Path("hdfs://114.67.98.145:9000/ruozedata/test"));
        Assert.assertEquals(true,flag);
    }

    //拷贝 从本地到Hdfs
    @Test
    public void copyFromLocalFile() throws Exception{
        Path srcpath = new Path("data/a.log");
        Path dstpath = new Path("/ruozedata/2019826");
        fileSystem.copyFromLocalFile(srcpath,dstpath);

    }
    //拷贝 从hdfs到本地
    @Test
    public void copyToLocalFile() throws Exception{
        Path srcpath  = new Path("/ruozedata/hdfsapi/access.log");
        Path  dstpath= new Path("data/2-access.log");
        fileSystem.copyToLocalFile(srcpath,dstpath);
    }
    //重命名
    @Test
    public void testrename() throws Exception{
        Path srcpath  = new Path("/ruozedata/826");
        String name = srcpath.getName();
        Path newpath  = new Path("/ruozedata/20190826");
        fileSystem.rename(srcpath,newpath);
    }

    //05 8月24日作业
    /*HDFS上的目录结构：20191001这个参数不是写死的，是外面传进去的
    public static void rename(String time)
    /ruozedata/20191001/a.txt
    /ruozedata/20191001/b.txt
    /ruozedata/20191001/c.txt
    使用HDFS API完成如下格式的输出
    /ruozedata/191001/1-20191001.txt
    /ruozedata/191001/2-20191001.txt
    /ruozedata/191001/3-20191001.txt*/
    @Test
    public void testRename(){
        try {
            reFliename("20190826");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  void reFliename(String name) throws  Exception{
        if (StringUtils.isBlank(name)){
            throw new NullPointerException();
        }
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/ruozedata"));
        boolean isDir=false;
        String realname = name.substring(2);
        for ( FileStatus fileStatuse:fileStatuses){
            String dstname = fileStatuse.getPath().getName();
            isDir = fileStatuse.isDirectory();
            //判断是否为文件夹并且名字与传入参数相同
            if (isDir&&name.equals(dstname)){
               updateFile(dstname,fileStatuse,name,realname);
            }
        }
    }

    private  void updateFile(String dstname, FileStatus fileStatuse, String name, String realname) throws Exception{
        //获取当前目录下的文件
        FileStatus[] dstfileStatues = fileSystem.listStatus(fileStatuse.getPath());
        int index=1;
        for (FileStatus dstfileStatuse:dstfileStatues){
            boolean isDir = dstfileStatuse.isDirectory();
            dstname = dstfileStatuse.getPath().getName();
            if (!isDir){
                //修改文件名称
                String type =dstname.substring(dstname.lastIndexOf("."));
                String newFilename = index+"-"+name+type;
                fileSystem.rename(dstfileStatuse.getPath(),new Path(dstfileStatuse.getPath().getParent().toString()+"/"+newFilename));
                index++;
            }
        }
        //修改目录名称
        fileSystem.rename(fileStatuse.getPath(),new Path(fileStatuse.getPath().getParent().toString()+"/"+realname));
    }
}
