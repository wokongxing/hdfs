package com.xiaolin.mapreduce.Scala001.scala02

/**
  * 讲师：PK哥
  *
  * 抽象类:
  * 1） 类中有一个或者多个方法/属性的定义，没有实现
  * 2） 抽象类是不能直接new
  * 3） 使用时要有完全实现了抽象方法/属性的子类才行
  * 4） 子类重写父类方法或者属性时一定需要override吗
  */
object AbstractClassApp {

  def main(args: Array[String]): Unit = {
    val student = new Student2
    student.speak
    println(student.name)
  }

}

abstract class Person2 {
  def speak
  val name:String
}

class Student2 extends Person2 {
  def speak: Unit = {
    println("speak...")
  }

  val name: String = "若泽"
}
