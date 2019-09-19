> 考虑到高级语言之间的相似性，尤其是词法语法的相似性，相似的一些内容可能会忽略。

- [文件](#文件)
- [词法](#词法)
- [数据类型](#数据类型)
  - [运算符](#运算符)
- [语法](#语法)
  - [语句](#语句)
  - [方法](#方法)
  - [修饰符](#修饰符)
  - [变长参数](#变长参数)
- [类和对象](#类和对象)
- [数组](#数组)
- [引用类型](#引用类型)
- [包和命名空间](#包和命名空间)

# 文件

Java程序由一个或多个Java源码文件（又称编译单元）组成。每个编译单元都以可选的package声明开始，后跟若干import声明。这些声明指定一个命名空间，编译单元中定义的名称都在这个命名空间里，而且还指定了编译单元从哪些命名空间中导入名称。  
在package和import之后，是零个或多个引用类型定义，一般是class或interface定义。在其中定义一些成员，如字段、方法和构造方法。

一个文件最多只能有一个public的顶层类，但public的嵌套类或内部类的数量不限。  
文件名必须与public顶层类的名称相同。（建议不管类是否为public，一个文件只定义一个类。）

`public static void main(String[] args)` 是程序的主要入口。
运行时，需要指定main()所在类的完全限定名称（注意是类名，不是文件名），可以通过 -classpath 指定类所在路径。
mian()方法返回后，Java解释器（java命令）就会退出。

jar也可以运行 `java -jar /path/to/jarball.jar`。

# 词法

Unicode字符、区分大小写

三种注释，尤其是javadoc形式

标识符使用Unicode，不能数字开头；不能包含标点，除了下划线和货币符号（$¥等）。
可以出现在标识符开头和之中的字符由java.lang.Character类中的`isJavaIdentifierStart()`和`isJavaIdentifierPart()`定义。

分隔符12个：`()` `{}` `[]` `...` `@` `::` `;` `,` `.`

保留字共53个
- 未使用 goto、const
- 访问修饰符（3个） public、protected、private
- 类、接口、抽象类（9个）
  - class、interface、abstract——定义
  - extends——继承类
  - implements——实现接口
  - new——新建一个对象
  - super——调用父类方法
  - this——指代当前对象
  - instanceof——通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
- 数据类型（13个）
  - void——没有返回值
  - byte、short、int、long——整型数据
  - float、double——浮点型数据
  - char——字符型数据
  - boolean——判断型数据
  - enum——枚举
  - null、true、false——值类型
- 线程（2个）
  - synchronized——线程同步（修饰方法、代码块，方法、代码块的同步）
  - volatile——线程同步（修饰属性，属性的同步）
- 异常（5个）
  - throw——抛出方法代码中的异常给方法自身。使用位置：方法中间
  - throws——抛出方法中的异常给调用者。使用位置：方法外部
  - try——捕获{}中代码是否有发生异常
  - catch——处理try捕获的异常
  - finally——不管有没有异常发生都会执行的代码块
- 返回（1个） return
- 循环、条件（10个） if、else、switch、case、break、default、continue、while、do、for
- 包（2个） package、import
- 瞬时的（1个） transient
- 断言（1个） assert
- 调用底层代码（C\C++）（1个） native
- 不可变的——final（1个）
- 静态的——static（1个）
- 格式规范——strictfp（1个）

转义字符 `\b`退格 `\t`制表 `\n`换行 `\f`换页 `\r`回车 `\"`双引号 `\'`单引号 `\\`反斜线 `\xxx`八进制 `\uxxxx`十六进制

# 数据类型

8种基本类型：布尔，字符，四种整型，两种浮点。
| 类型 | 取值 | 默认值 | 大小 | 范围 |
|------|-----|-------|------|-----|
| boolean | true/false | false | 1位 | NA |
| char | Unicode字符 | \u0000 | 16位 | \u0000~\uffff |
| byte | 有符号的整数 | 0 | 8位 | -128～127 |
| short | 有符号的整数 | 0 | 16位 | -32768～32767 |
| int | 有符号的整数 | 0 | 32位 | -2147483648~2147483647 |
| long | 有符号的整数 | 0 | 64位 | -9223372036854775808~9223372036854775807 |
| float | IEEE754浮点数 | 0.0 | 32位 | 1.4E-45～3.4028235E+38 |
| double | IEEE754浮点数 | 0.0 | 64位 | 4.9E-324～1.7976931348623157E+308 |

字符字面量用单引号。字符对应Character类。  
字符串字面量使用双引号。字符串对应String类。预计到单个字符无法表示时，应该使用String类。
> String并非基本类型，而是应用类型。String字面量，只是为了方便使用而提供的一种语法。

整型有byte、short、int、long。每种类型都有对应的包装类：Byte、Short、Integer、Long。  
整型运算时，超过该类型范围不会报错，而是直接回绕。  
整数的多种表达形式： `0` `0xff` `1234` `1234L` `0xffL`。
整数有一个奇怪的语法，就是在数字中加入下划线，如`112_123`。这只是为了方便阅读，编译时会自动忽略。  
整数字面量是32位int类型，如果以L或l结尾的是64位long类型。

浮点数有float、double类型。每种类型都有对应的包装类：Float、Double。  
浮点数的多种表达形式： `123.34` `.01` `1.23E02` `1e-6`。
浮点数字面量是double类型，如果以F或f结尾的是float类型。

整数和浮点数之间可以相互转换，包括放大转换和缩小转换。  
缩小转换可能会丢失数据，编译器会给出警告，除非使用对应的类型进行矫正，例如`(int)(13.456)`。  
基本类型的矫正最常用于把浮点数转换为整数。执行转换时，浮点数的小数部分会直接截掉。

## 运算符

Java的运算符优先级与C兼容。

大多数运算符从左向右计算，但赋值和一元运算符从右向左结合。

`->` lambda表达式。

# 语法

## 语句

标注语句就是有名称的语句。命名方法是，在语句前假一个标识符和一个冒号。break和continue会用到，例如：
```java
rowLoop: for(int r=0; r <rows.length; r++) {
    colLoop: for (int c = 0; c <columns.length; c++) {
        break rowLoop;
    }
}
```

遍历循环用于处理需要循环的对象集合。（相当于js的`foreach(array as element)`）
使用遍历循环也有很多不能：没有计数器，无法识别第一次／最后一次／其中某一次；无法中途退出；无法反向迭代；无法同时处理多个数组（如对比）。
```java
for (declaration: expression)
  statement
```

synchronized语句用于在多线程工作时避免冲突。执行时，Java会先为expression计算得到的对象或数组设置一个排他锁，直到执行完成才释放。
> synchronized还可用作类和方法的修饰符。

throw语句抛出一个异常对象。Java执行throw语句时，会立即停止常规的程序执行，开始寻找能捕获或处理异常的程序。如果有catch和finally会先处理，否则，向上冒泡到能处理的语句。

try语句的catch可以多次出现以捕获不同的异常，或可以一次捕获多个异常。
```java
try {}
catch (IndexOutOfBoundsException e) {}
catch (IOException e) {}

try{}
catch (IndexOutOfBoundsException | IOException e) {}
```

try语句从Java7开始提供一种自动关闭资源的语句TWR（try-with-resource）。不管try以任何方式退出，都会自动清理资源。
```java
try (InputStream is = new FileInputStream("/path/to/file")) {
    //...
}
```

assert断言语句一般只用于调试，不用于测试。

## 方法

方法的签名：名称、参数的数量、顺序、类型和名称、返回值类型、能抛出的异常（方法还能列出未检异常，但不是必需的）、提供方法额外信息的多个修饰符。
格式为 `modifiers type name (paramlist) [throws exceptions]`。
方法签名中可能含有类型变量生命，这种方法叫范型方法。

如果方法会抛出异常，必须在throws中声明这个异常。即使方法不抛出异常，但方法中调用了会抛出异常的方法，要么使用异常处理代码，要么使用throws字句声明这个方法也能抛出该异常。  
如果throws中没有声明，但又必须处理或声明调用的方法抛出的异常，编译器会提醒的。
> iceman注：开始很费解，来回折腾了好几番。
> 简单地说：**如果方法明确会抛出异常，那么异常一定要得到处理**，要么是方法通过catch内部处理，要么是方法的调用者通过catch进行外部处理。

签名之后是方法的主体，也就是花括号中的语句序列。抽象方法没有主体，用分号表示。

```java
public static void main(String[]) {
    //...
}
protected abstract String readText() throws FileNotFoundException, UnsupportEncodingException;
```

Java没有匿名方法。lambda表达式类似于匿名方法，但Java运行时会自动把lambda表达式转换为适当的具名方法。

允许定义和调用参数数量不定的方法，使用的句法是变长参数。

## 修饰符

可以使用的修饰符
- abstract 抽象，抽象方法没有实现主体。抽象方法所在的类也必须使用abstract声明。
- final 终极，方法不能被覆盖。所有private类都隐式添加了final。final类中的所有方法都隐式添加了final。
- native 使用某种“本地”语言编写的方法，如C。
- public，protected，private 访问修饰符
- static 类方法
- strictfp 在方法中严格遵守标准，只使用32位或64位的浮点数格式进行计算。
- synchronized 实现线程安全，避免两个线程同时执行同一个方法。synchronized 可以用于修饰代码或方法。

## 变长参数

方法可以接受数量不定的参数。变长参数列表的声明方式是，在最后一个参数的类型后面加上省略号。
```java
public static int max(int first, int... rest) {
    
}
```

# 类和对象

String表示文本，文本字面量为双引号包围的字符，例如：`String x = "Hello";`  
类型字面量即Class类。要使用Class对象字面量，要在数据类型的后面加上`.class`。例如：`Class<?> t = int.class;`。  
null是一种特殊的字面量，不引用任何值。null可以是任何引用类型的成员。例如：`String s = null;`。

lambda表达式原见于函数式语言。格式为`(paramList) -> {statement}`。
lambda表达式其实就是匿名函数，在Java中可以当作一个值。lambda表达式在当作值使用时，会根据要存储的变量类型，自动转换为相应的对象。自动转换和类型推导是Java实现lambda表达式的基础。

# 数组

数组类型不是类，但数组实例是对象。这意味着，数组从java.lang.Object继承了方法。数组实现了Cloneable接口，而且覆盖了clone()方法，确保数组始终能被复制，而且clone()方法从不抛出CloneNotSupportedExction异常。数组还实现了Serializable接口，只要元素能被序列化，数组就能被序列化。而且所有数组都有length字段，表示数组中元素的数量。

```java
String[] arrayOfString; // 一维字符串数组
int[][] arrayOfArrayOfInt; // 二维整型数组

byte[] buffer = new byte[1024]; // 创建和初始化数组时，必须指定长度。这样创建的数组元素都有默认值。
int[][] pro = new int[10][10]; // 创建和初始化二维数组，必须指定长度。多维数组也是如此。

String[] xx = new String[] { "hel", "lo", "world" }; // 表达式初始化数组
String[] mm = { "hel", "lo", "world" }; // 上句的简略形式
```

数组的访问
- 单个元素的访问，使用`[]`。
- 循环访问，使用for加`[]`，或使用遍历循环`for(:)`。

任何数组都可以使用 clone() 方法复制自己。数组部分拷贝时，可以使用`System.arrayCopy()`完成。

java.utils.Arrays 类中包含很多处理数组的静态使用方法。例如：binarySearch(), sort(), equals(), toString() 等。还有能正确处理多维数组的方法，如 deepEquals(), deepHashCode(), deepToString() 等。

# 引用类型

引用类型是聚合类型，可以保存零个或多个基本值或对象。

把对象赋值给变量或传入方法时，不会复制表示这个对象的内存，而是把这个内存的引用存储在变量中或传入方法。
```java
Point p = new Point(1.0, 2.0); // 创建一个对象、一个引用副本
Point q = p; // 创建另外一个引用副本
```

对象的比较，使用`==`只能判断引用是否相等，使用`equals()`才能判断对象是否相等。
```java
String letter = "o";
String s = "hello";
String t = "hello" + letter;
if (s == t) {
    System.out.println("ref equals");
}
if (s.equals(t)) {
    System.out.println("obj equals");
}
```

Java为每种基本类型提供了包装类，Boolean、Byte、Short、Character、Integer、Long、Float、Double都是不可变的最终类，每个实例只保存一个基本值。
Java支持自动装包和拆包。

# 包和命名空间

包由一些具名的类、接口和其他引用类型组成，目的是将其组织在一起，并为这些类定义命名空间。

若要指定类所属的包名，使用package声明。例如：`package org.apache.commons.net`。不提供package的，默认所有类都属于一个无名包。  
常用包的命名方式是：使用域名，倒序排列各部分，作为包的前缀。例如`package org.apache.commons.net`包，是位于 http://commons.apache.org 的项目 Commons 的网络库。


通过import引用其他包。import必须放于文件的开头，如果有package声明的话，要紧随其后。
import声明有两种格式：导入单个类，按需导入全部。
需要注意的是，多个包中可能包含名称相同的类，解决的办法是：使用明确指定所需的类的全名。
```java
import java.io.File; // 导入单个File类
import java.io.*;  // 导入全部类
```

还可以通过 import static 导入类的静态成员。与导入类型相似，导入静态成员也有两种形式：导入单个成员，按需导入成员。
import static 一个重要的作用就是把常量导入代码，尤其是枚举类型。
```java
import static java.lang.System.out;
```

通过 import 方法导入的是“名称”，而不是某个具体成员。
例如 `import static java.util.Arrays.sort;` 导入的是 sort 名称，而不是19个具体方法中的任何一个，当调用sort()时，编译器会根据参数类型自动选择调用哪个方法。
