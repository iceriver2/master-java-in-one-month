package com.iceman;

public interface Runable {
  default public void say() {
    System.out.println("Who am I ?");
  }
}