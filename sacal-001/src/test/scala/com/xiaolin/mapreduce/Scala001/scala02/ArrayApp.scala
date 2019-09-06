package com.xiaolin.mapreduce.Scala001.scala02

import scala.collection.mutable.ArrayBuffer

/**
  * 讲师：PK哥
  *
  * 长度可变
  * 长度不可变
  */
object ArrayApp {

  def main(args: Array[String]): Unit = {

    /**
      * 元组   可以装任意类型
      */
      val t = (1,2,3,4,5)

    val a: Array[Int] = Array(1,2,3,4,5,6,7,8)

    val array = Array(Array(("ruoze",30)),Array(("jepson",18)))
    array.map(x => x.map(t => (t._1, t._2+2)))

    /**
      * map: 映射  一一映射
      * y = f(x)
      */
    // f: A => B
    a.map((x:Int) => x*2)

    /**
      * filter: 过滤掉不需要的东西（true）
      *
      * 链式编程
      */
    a.reduce(_+_)    //op: (A1, A1) => A1
    a.reduceLeft((a,b)=>{
      println(a + ", " + b)
      a - b
    })

    /**
      * zip
      */


      // flatMap = flatten + map
    val f = Array(Array(1,2),Array(3,4),Array(3,4))
    f.map(_.map(_*2))

    val l = Array("hello,world,welcome","ruoze,jepson")
    l.map(x => x.split(",")).flatten

    val ints = Array(1,2,3,4,5,6)
//    ints.find()

    // groupBy  自定义分组
    val arrs = Array(("a",100),("b",10),("a",190), ("d",10))
    arrs.groupBy(x => x._2)
    val aaa = Array("c","b","a")


    val data = Array("hello world hello","hello world")
      val words: Array[String] = data.flatMap(_.split(" "))
      val wordWithOne: Array[(String, Int)] = words.map((_,1))
      val groupByData: Map[String, Array[(String, Int)]] = wordWithOne.groupBy(_._1)
      val result: Map[String, Int] = groupByData.mapValues(x => x.map(t=>t._2).sum)
      result.toList.sortBy(_._2)


    data.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(x => x.map(t=>t._2).sum).toList.sortBy(_._2)


    // mapValues

    //    val a = Array("ruoze","jepson","xingxing")
//    println(a.mkString("[","$$$","]"))
//    println(a.toString)

//    for(ele <- a) {
//      println(ele)
//    }
//
//    println("........")
//
//    a.foreach(println)
//
//    println("........")
//
//    for(i<-a.length-1 to 0 by -1 ){
//      println(a(i))
//    }


  }

}
