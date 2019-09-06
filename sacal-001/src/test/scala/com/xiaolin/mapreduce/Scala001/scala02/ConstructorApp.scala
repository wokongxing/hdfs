package com.xiaolin.mapreduce.Scala001.scala02

import org.apache.spark.SparkContext

/**
  * 讲师：PK哥
  */
object ConstructorApp {

  def main(args: Array[String]): Unit = {
//    val person = new Person("穆里尼奥", 50)
//    println(person.name + " : " + person.age + " : " + person.job)
//
//    val person2 = new Person("穆里尼奥", 50, "皇家马德里")
//    println(person2.name + " : " + person2.age + " : " + person2.job + " : " + person2.team)
//    val sc = new SparkContext("","",null)

    /**
      * 子类构造方法/构造器执行之前一定要先执行父类的构造器
      * 对于父类中没有的字段，要使用var/val修饰，否则访问不到
      */
    val student = new Student("穆里尼奥",50,"翻译")
//    println(student.job)
    println(student)
  }}

/**
  * 类名(......)   主构造器
  */
class Person(val name:String, val age:Int) {

  println("进入Person构造器...")
  val job = "解说员"
  var team = ""

  /**
    * 附属构造器的名称 this
    * 每个附属构造器的第一行必须调用主构造器或者其他的附属构造器
    */
  def this(name:String, age:Int, team:String) {
    this(name, age)
    this.team = team
  }


  println("执行完Person构造器...")
}

class Student(name:String,age:Int,val major:String) extends Person(name, age) {
  println("进入Student构造器...")

  // 子类重写父类中的属性或者方法的修饰符
  override val job: String = "钟馗"

  /**
    * 如果 你想重写父类中的属性或者方法，需要使用override关键字来修饰
    */
  override def toString = name + "    " + job

  println("执行完Student构造器...")
}