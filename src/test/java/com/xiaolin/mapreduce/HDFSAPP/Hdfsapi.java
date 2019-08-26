package com.xiaolin.mapreduce.HDFSAPP;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Hdfsapi {
    public static final String HDFS_PATH="hdfs://114.67.98.145:9000";
    @Test
    public void mkdir() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration,"hadoop");
        boolean flag = fileSystem.mkdirs(new Path("/ruozedata002/hdfsapi"));
        Assert.assertEquals(true,flag);
        fileSystem.close();
    }
}
