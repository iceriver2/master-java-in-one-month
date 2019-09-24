package com.iceman;

import com.iceman.Runable;

/**
 * 包的演示
 * 
 * 编译时，会自动生成 com/iceman/xxx.class 结构
 * 运行时，必须进入 com/ 的同一层次，才能运行 java com.iceman.Main 或直接运行 java -cp classdir/ com.iceman.Main
 */

public class Main {
  public static void main(String[] args) {
    A a = new A();
    a.say();
  }
}

class A implements Runable {

}