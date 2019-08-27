package com.xiaolin.mapreduce.HDFSAPP;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.eclipse.jdt.internal.eval.EvaluationResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class Hdfsapi {

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
        boolean flag = fileSystem.mkdirs(new Path("/ruozedata/hdfsapi"));
        Assert.assertEquals(true,flag);
    }

    //拷贝 从本地到Hadoop
    @Test
    public void copyFromLocalFile() throws Exception{
        Path srcpath = new Path("data/access.log");
        Path dstpath = new Path("/data001/");
        fileSystem.copyFromLocalFile(srcpath,dstpath);

    }
}
