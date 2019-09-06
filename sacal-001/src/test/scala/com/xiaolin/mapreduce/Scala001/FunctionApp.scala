package com.xiaolin.mapreduce.Scala001

/**
  * 讲师：PK哥
  */
object FunctionApp {

  def main(args: Array[String]): Unit = {
    val array02 = Array("a_b","c_d","e_f")
    array02.mkString(",")
    array02.mkString("@"," ","*")
    val stringses = array02.map(x => x.split("_"))

//    println(sum2(2,5))

//    sayHello()
    // sayHello  //约定：如果没有入参，在方法调用时可以不写括号

//    println(mult(10))

    val array = Array(1,2,3,4,5)
    array.map(x => x.toString.mkString("aa") )
//    //array.flatMap(_)
//    for(ele <- array) {
//      //println(ele)
//    }
//    for(i<-0 until (array.length)){
//      //println(array(i))
//    }

    // y = f(x)
    // array.foreach(ele => println(ele))

//    array.foreach(println)
//    val result = for(ele <- array) yield ele * 10
//    result.foreach(println)

    // 0-100求和  sum index
//    var (sum,index) = (0, 100)
//    while (index > 0) {
//      sum = sum + index
//      index = index - 1
//    }
//    println(sum)

    //loadConfiguration()

//    println(speed(10,2))
//    println(speed(time=2, distance = 10))

//    println(sum(1,2,3,4))
//    println(sum(1,2,3,4,5,6,7))
//    println(sum(1 to 10 : _*))

//    printCourses("Hadoop","Spark","Flink")

//    printCourses(Array("Hadoop","Spark","Flink"): _*)

    /**
      * 函数的定义： val/var 函数名称 = （参数列表） => {函数体}
      */
    val f1 = (a:Int, b:Int) => {
      a+b
    }

//    println(f1(2,3))

    /**
      * 第二种定义方式
      *
      *  val/var 函数名称：（输入参数类型） => 返回值类型  = （输入参数的引用） => {函数体}
      */
    val f2:(Int,Int) => Int =(a:Int, b:Int) => a+b
    println(f2(3,5))


    val f3:(Int,Int) => Int =(a, b) => a+b
    println(f3(4,5))


  }

  def printCourses(courses:String*): Unit = {
    courses.foreach(println)
  }

  /**
    * 变长参数：可变参数
    */
  def sum(nums:Int*) = {
    var result = 0
    for(num <- nums) {
      result += num
    }
    result  // 返回值
  }


  /**
    * 默认参数：
    * 在定义function时，允许指定参数的默认值
    * @param name
    */
  def loadConfiguration(name:String = "spark-default.conf"): Unit = {
    println(name)
  }

  /**
    * 命名参数   知道有这玩意即可，开发时少用，没有任何意义
    * 默认调用时：入参是按照顺序的
    * Scala中可以根据参数名称来指定
    */
  def speed(distance:Float, time:Float) = distance/time

  /**
    * Scala中不强制使用; 作为每行代码的最后
    *
    * 最后一行当做返回值，不需要写return
   */
//  def sum(x:Int, y:Int):Int = {
//    x + y
//  }
//
//  def sum(x:Int, y:Int, z:Int):Int = {
//    x + y + z
//  }

  def sum2(x:Int, y:Int) = x + y

  def sayHello(): Unit ={
    println("你好，若泽数据...")
  }

  /**
    *  大部分场景返回值可以Scala自身推导出来
    *
    *  但是：对于递归调用的，一定要显式指明返回值类型
    *
    */
  def mult(i:Int):Int = {
    if( i<=1 ) {
      1
    } else {
      i * mult(i-1)
    }
  }

}
