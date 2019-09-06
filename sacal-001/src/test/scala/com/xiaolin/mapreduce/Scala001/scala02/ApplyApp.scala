package com.xiaolin.mapreduce.Scala001.scala02

/**
  * 讲师：PK哥
  */
object ApplyApp {

  def main(args: Array[String]): Unit = {

//    ApplyTest.static

    val applyTest = new ApplyTest()
    println(applyTest)

    val test = ApplyTest() // 类名() ==> object apply
    println(test)
    println(applyTest==test)
//    val applyTest = new ApplyTest
//    print(applyTest) // 对象() ==> Class apply

    /**
      * 类名() ==> Object apply   *****
      * new出来的对象() ==>Class apply *
      */

  }

}

/**
  * class是object伴生类
  * object是class的伴生对象
  *
  * object里面的方法直接通过object.方法名调用，不需要new
  */
class ApplyTest {

  def apply(): Unit ={
    println("class apply....")
  }
}

object ApplyTest {
  println("进入ApplyTest object")

  def static: Unit = {
    println("我是static方法")
  }

  // 一般情况下object的apply方法的作用是完成new class
  def apply() ={
    println("object apply....")
    new ApplyTest
  }

  println("执行完ApplyTest object")
}
