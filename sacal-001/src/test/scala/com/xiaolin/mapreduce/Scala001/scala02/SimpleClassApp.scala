package com.xiaolin.mapreduce.Scala001.scala02

/**
  * 讲师：PK哥
  */
object SimpleClassApp {

  def main(args: Array[String]): Unit = {
    val people = new People   // 实例化一个Class对象
    people.name = "周琦"  // 为对象赋值
    people.age = 30

//    people.gender

    println(people.name + " : " + people.age )

    people.playBasketball("China")
    println(people.drink())  //白斩鸡
  }
}

/**
  * Scala中定义类：class 类名
  * 类中有属性 有方法
  */
class People {

  // 属性的定义： val/var 名称:类型 =  值
  var name:String = _  // _ 占位符  前提是一定要明确是什么数据类型
  var age:Int = 3

  // 私有
  private var gender = "M"

  // 定义方法
  def playBasketball(team:String) = {
    println(name + " is playing basketball of " + team)
  }

  def drink() = {
    name + " is drinking..."
  }
}
