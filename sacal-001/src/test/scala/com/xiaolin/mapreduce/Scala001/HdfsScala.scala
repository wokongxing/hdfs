package com.xiaolin.mapreduce.Scala001

import java.net.URI

import org.apache.commons.lang.StringUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}
import org.junit.{After, Assert, Before, Test}

import scala.collection.mutable.ArrayBuffer


class HdfsScala {

  //作业 求100以内的素数
  @Test
  def isQrimeNumber: Unit ={
    var buffer = ArrayBuffer[Int]();
    buffer +=2;//0,1,2不需要做判断
    for (i <- 3 to 100 by 2 if i%2!=0){
      var boolean=true;
      for (j <- 3 to Math.sqrt(i).toInt if i%j==0){
        boolean=false;
      }
      if (boolean){
        buffer +=i;
      }
    }
    buffer.toArray.foreach(println)
  }


  val HDFS_PATH:String= "hdfs://114.67.98.145:9000";
  var configuration: Configuration = null;
  var fileSystem: FileSystem = null;
  //创建目录
  @Test
  def mkdir: Unit ={
    val bool = fileSystem.mkdirs(new Path("/scala"));
    Assert.assertEquals(true,bool);
  }

  //拷贝 从本地到Hdfs
  @Test
  def copyFromLocalFile: Unit = {
    val srcpath:Path = new Path("data/Topn.txt");
    val dstpath = new Path("/data");
    fileSystem.copyFromLocalFile(srcpath, dstpath)

  }
  //拷贝 从hdfs到本地
  @Test
  def copytToLacolFile: Unit ={
    val srcpath:Path = new Path("/scala/test.txt")
    val dstpath:Path = new Path("data/test02.txt")
    fileSystem.copyToLocalFile(srcpath,dstpath);

  }
  //重命名
  @Test
  def rename: Unit ={
    val srcpath:Path = new Path("/scala/test.txt")
    val dstpath:Path = new Path("/scala/test-2.log")
      fileSystem.rename(srcpath,dstpath);
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
  def testRename(): Unit = {
    try
      reFliename("190826")
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def reFliename(name: String): Unit = {
    if (StringUtils.isBlank(name)) {
      throw new NullPointerException
    }
    val fileStatuses: Array[FileStatus] = fileSystem.listStatus(new Path("/ruozedata"))
    var isDir: Boolean = false
    val realname: String = name.substring(2)
    //判断是否为文件夹并且名字与传入参数相同
    for (fileStatuse <- fileStatuses if fileStatuse.isDirectory;if name.equals(fileStatuse.getPath.getName)) {
        updateFile(fileStatuse.getPath.getName, fileStatuse, name, realname)
    }
  }
  //获取当前目录下的文件
   def updateFile(dstname: String, fileStatuse: FileStatus, name: String, realname: String): Unit = {
    val dstfileStatues: Array[FileStatus] = fileSystem.listStatus(fileStatuse.getPath)
    var index: Int = 1
    for (dstfileStatuse <- dstfileStatues) {
      val isDir: Boolean = dstfileStatuse.isDirectory
      var dstname = dstfileStatuse.getPath.getName
      if (!(isDir)) { //修改文件名称
        val `type`: String = dstname.substring(dstname.lastIndexOf("."))
        val newFilename: String = index + "-" + name + `type`
        fileSystem.rename(dstfileStatuse.getPath, new Path(dstfileStatuse.getPath.getParent.toString + "/" + newFilename))
        index += 1
      }
    }
    //修改目录名称
    fileSystem.rename(fileStatuse.getPath, new Path(fileStatuse.getPath.getParent.toString + "/" + realname))
  }




  @Before
  def init: Unit ={
      configuration = new Configuration();
      configuration.set("dfs.replication", "1");
      configuration.set("dfs.client.use.datanode.hostname", "true");
      fileSystem = FileSystem.get(new URI(HDFS_PATH),configuration,"hadoop");
  }

  @After
  def destory: Unit = {
    fileSystem.close();
  }

}
