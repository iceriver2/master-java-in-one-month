# master-java-in-one-month

已经使用过PHP和JavaScript，了解这两种语言的语法、范式、应用开发、生态体系。尝试在一个月时间内，初步掌握Java Web。
掌握的标准：
- 是可以使用JavaWeb框架，编写一个服务程序，计算前段传来的四则运算表达式，并记录日志。
- 了解JavaWeb生态系统的概貌，对常见名词不陌生，对核心名词有了解

值得注意的是，Java的基于继承的对象，完全不同于PHP的过程、JS的基于原型的对象。  
需要重点关注Java的**继承**、**重载**、**封装**等特性，以及**设计模式**。

计划是：
- [x]了解java[版本历史](./docs/history.md)，不同版本的主要差异
- [x]掌握[基本语法](./docs/basic.md)，关键字
- [x]掌握java的[安装和自带工具的使用](./docs/intro.md)，如：javac，java
- [ ]OO基本思想
- [ ]一些简单的单应用，熟悉常用的类，如数组排序，文件读写
- [ ]codewar做一些题目
- [ ]掌握tomcat的基本使用，实际使用时可考虑使用docker
- [ ]编写一个单文件的服务程序，用于计算四则运算表达式
- [ ]单元测试引入
- [ ]文件系统引入
- [ ]数据库引入 
- [ ]框架引入
- [ ]重构整个服务程序
- [ ]以docker方式发布服务程序

# User It

最好的学习方法，就是使用。

遇到问题，先琢磨一下，再Google。

# 感想

Java比较严谨。在学习throws过程中发现，只要是方法签名中包含了throws部分，那么，方法的调用者就必须处理可能抛出的异常，要么catch，要么throw，不允许忽略（编译器会提示）。

编译器在检查错误方面优于解释器。

字符串是对象，不是基本类型！这一点，对于从PHP和JS转过来的开发者来说，可能需要适应一下。
