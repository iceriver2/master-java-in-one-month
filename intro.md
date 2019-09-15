- [Java简介](#java简介)
  - [与其他语言的简单比较](#与其他语言的简单比较)
  - [下载与安装](#下载与安装)
- [目录树](#目录树)
- [常用工具](#常用工具)
  - [javac](#javac)
  - [javap](#javap)
  - [java](#java)
  - [javadoc](#javadoc)
  - [jar](#jar)
  - [jshell](#jshell)
  - [jconsole](#jconsole)

# Java简介

Java由多个不同环境和规范组成，包括 JavaME、JavaSE、JavaEE。

Java语言受Java语言规范（Java Language Specification, JLS）的约束。

**JVM是运行Java程序所需的运行时环境。** JVM运行的是class文件。JVM运行时能监控并优化在其中运行的程序。  
JVM算是解释器（通过JIT编译大幅提升性能）。  
JVM能运行任何有效的class文件，因此，Java以外的语言可以通过两种方式在JVM中运行：一种是使用类似javac的源码编译器将源码编译为class文件（如Scala）；另一种是使用Java实现解释器和运行时，然后解释该语言的源码（如Jruby）。
> JVM是很重要的一个内容。

Java的生态系统极大，包含大量的第三方库和和组件。这使得开发者能利用现有的库和组件实现最佳的开放式架构。

**整个流程是：源码编经javac处理后得到class文件，class文件通过类加载机制载入JVM并运行。**

**Java的安全架构坚固牢靠，在设计层面没有任何安全漏洞。** Java安全模型的基础是，严格限制字节码能表达的操作，如不能直接访问内存，对不信任的类进行字节码校验等。

使用范围最广的Java语言是通过OpenJDK实现的。

## 与其他语言的简单比较

与C相比：Java面向对象，C面向过程；Java通过类文件实现移植，C要重新编译；**Java为运行时提供全面的监控**；Java没有指针；**Java实现自动垃圾回收**；Java无法直接操作内存；Java没有预处理器。

与C++相比：Java的对象模型比较简单；**Java始终使用值传递**；Java默认使用虚分派；Java不完全支持多重继承；Java无法重载运算符。

与PHP相比：Java是静态类型语言，PHP是动态类型语言；Java有JIT，PHP没有；PHP基本只能用于网站；Java支持多线程，PHP不支持。

与JS相比：Java是静态类型语言，JS是动态类型语言；Java的对象基于类，JS的对象基于原型；Java提供了良好的封装，JS没有；Java有命名空间，JS没有；Java支持多线程，JS不支持。


## 下载与安装

系统为 Ubuntu 14.04 amd64

安装系统自带默认版本（版本可能有点低）
```bash
sudo apt-get install default-jdk
```

第三方安装Oracle Java
```bash
sudo apt update
sudo apt install software-properties-common
sudo add-apt-repository ppa:linuxuprising/java
sudo apt update
sudo apt search oracle-java
sudo apt install oracle-java12-installer
```

手动安装jdk
```bash
## 下载最新版本 https://www.oracle.com/technetwork/java/javase/downloads/index.html ##
## 注意匹配版本与架构 ##
wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/12.0.2+10/e482c34c86bd4bf8b56c0b35558996b9/jdk-12.0.2_linux-x64_bin.tar.gz

## 解压即是安装 ##
mkdir /opt/java
tar -zxf jdk-12.0.2_linux-x64_bin.tar.gz -C /opt/java

## 配置环境变量 ##
sudo vi /etc/environment
# 将 /opt/java/jdk-12.0.2/bin 加入PATH
# 增加 JAVA_HOME="/opt/java/jdk-12.0.2"
source /etc/environment
```

# 目录树

包结构与文件目录保持一致，类`com.mysoft.mypack.MyClass`对应的源码文件是`./com/mysoft/mypack/MyClass.java`。

# 常用工具

参见[官方文档](https://docs.oracle.com/en/java/javase/12/tools/tools-and-command-reference.html)的介绍。

主要工具：
- javac: 读取类和接口定义文件，并编译为字节码和class文件。
- javap: 反编译class文件。
- javadoc: 从源码文件中生成API文档。
- java: 启动一个Java应用程序。
- jar: 创建类和资源的压缩包，或从压缩包中操作或恢复单个类或资源。
- jlink: 汇编并优化一系列模块及其依赖到一个自定义的运行时映像（runtime image）。
- jmod: 创建 JMOD 文件，列出 JMOD 文件的内容。
- jdeps: 启动 Java 类依赖分析器。
- jdeprscan: 静态分析工具，用于扫描jar文件（或其他类文件聚合体）中不建议使用的API元素。

Shell工具：
- jshell: 启动一个REPL，用于交互式计算声明、语句和表达式。

安全工具：
- keytool: manage a keystore (database) of cryptographic keys, X.509 certificate chains, and trusted certificates.
- jarsigner: 为jar文件签名和验证。

远程方法调用工具：
- rmic: You use the rmic compiler to generate stub and skeleton class files using the Java Remote Method Protocol (JRMP).
- rmiregistry: You use the rmiregistry command on UNIX-based systems to create and start a remote object registry on the specified port on the current host.
- rmid: You use the rmid command to start the activation system daemon that enables objects to be registered and activated in a Java Virtual Machine (JVM).
- serialver: You use the serialver command to return the serialVersionUID for one or more classes in a form suitable for copying into an evolving class.

部署工具（可能将被废弃）：
- pack200: 将jar文件用Java gzip压缩器转为压缩的pack200文件。
- unpack200: 将一个打包文件转为用于web部署的jar文件。

监控工具：
- jconsole: 启动一个图形化的控制台，用于监控和管理Java应用程序。

Java Accessibility Utilities
- jaccessinspector: You use jaccessinspector to examine accessible information about objects in the Java Virtual Machine that use the Java Accessibility Utilities API.
- jaccesswalker: You use jaccesswalker to navigate through the component trees in a particular Java Virtual Machine and presents the hierarchy in a tree view.

故障排除工具：
- jcmd: 发送诊断命令请求到运行中的JVM。
- jdb: 在Java平台程序中寻找并修复bug。
- jhsdb: You use the jhsdb tool to attach to a Java process or to a core dump from a crashed Java Virtual Machine (JVM).

## javac

Use the javac tool and its options to read Java class and interface definitions and compile them into bytecode and class files. The javac command can also process annotations in Java source files and classes.

```bash
javac [options] [sourcefiles]
```

引入源码文件的两种方法：
- 少量文件，直接列出源码文件名
- 大量文件，使用 `@filename` 指定一个清单文件

使用 `@` 引入文件时，可以将参数和源文件放在一个文件中，也可以分别放在2个文件中，一个`options`存储参数，一个`classes`存放所有类文件，**所有文件内容都换行保存**。

编译后的class文件默认存在于源码文件同目录。可以用`-d`指定输出class目录。

JDK_JAVAC_OPTIONS 环境变量可以用来代替 options 参数。

options参数有三种：标准参数、当前开发环境支持的交叉编译参数、非标准参数（以`-X`开头）。

重要的标准参数：
- `@filename` 从文件中读取 options 和 sourcefiles。
- `-Akey[=value]` 指定传递到annotation processor的参数。
- `--class-path path`, `-classpath path`, or `-cp path` 指定用户class文件和annotation processors的位置。
- `-d directory` 生成class文件的目录。
- `-s directory` 生成源文件的目录。
- `--source-path path` or `-sourcepath path` 指定源文件的位置。

重要的交叉编译参数：
- `-Xlint` 使用所有推荐的警告。
- `-Xlint:[-]key(,[-]key)*` 禁用某些警告。
- `-Xdoclint` 检查javadoc中推荐检查的问题
- `-Xdoclint:(all|none|[-]group)[/access]` 启用或禁用某些组的检查

当编译时，编译器需要寻找在源文件中被使用、扩展、应用的每个类和接口的类型信息，包括没有明确被使用类的父类。所以，当使用了不在当前目录中的类和接口时，需要使用 `CLASSPATH` 环境变量或 `-classpath` 指定用户类的位置。

## javap

Use the javap command to disassemble one or more class files.

```bash
javap [options] classes...
```

当没有任何参数指定时，javap只打印 protected 和 public 成员。

javap打印输出到stdout。

重要参数：
- `-l` 打印本地变量和行数的表格。
- `-package` 显示 package/protected/public 类和成员(默认)。
- `-public` 只显示 public 类和成员。
- `-protected` 只显示 protected ／ public 类和成员。
- `-private` or `-p` 显示所有类和成员。
- `-s` 打印内部类型签名。
- `-sysinfo` 显示当前处理类的系统信息(path, size, date, MD5 hash)。
- `-constants` 显示 `static final` 常量。
- `--module module` or `-m module` 指定包含将被反编译的类的模块。
- `--module-path path` 指定模块的路径。
- `--class-path path`, `-classpath path`, or `-cp path` 指定javap用于寻找用户类文件的路径。这将覆盖默认值或CLASSPATH（如果未设置的话）。
- `-bootclasspath path` Overrides the location of bootstrap class files.
- `-Joption` 传递指定参数到JVM，例如: `javap -J-version`

以HelloWorld.java为例
```java
public class HelloWorld
{
    public static void main(String args[])
    {
        System.out.println("Hello World");
    }
}
```
反编译HelloWorld.class的结果
```java
public class HelloWorld {
  public HelloWorld();
  public static void main(java.lang.String[]);
}
```

## java

## javadoc

## jar

## jshell

JShell is a Read-Evaluate-Print Loop (REPL), which evaluates declarations, statements, and expressions as they are entered and immediately shows the results.


修改变量、方法和类，只要输入一个新的即可覆盖旧的。

出现错误时，会提示错误和行数。行数格式为`#ID:line-number`，其中ID是`/list`命令显示的数值，`line-number`是在代码片段中的行数。

支持tab键自动补全。

通过 `jshell --class-path myOwnClassPath` 指定自定义类的路径，以便通过`import my.cool.code.*`引入。也可通过`/env`进行。

通过 `jshell --module-path myOwnModulePath  --add-modules my.module` 指定模块路径，引入指定模块。也可通过`/env`进行。

通过`/set feedback`设置不同的反馈级别（连提示符都不同）：verbose／normal（默认）／concise／silent。

JShell启动脚本是一个文件中的一系列代码片段和命令，可以是一个本地文件或系统预定义的几种文件（DEFAULT／PRINTING／JAVASE）之一。  
允许自定义启动脚本`jshell --startup mystartup.jsh`，或使用启动脚本`jshell --startup DEFAULT`，或使用`/set start`设置启动脚本。  
支持同时引入多个启动脚本，如：`jshell --startup DEFAULT --startup PRINTING`。  
启动脚本在`/reset`，`/reload`，`/env`命令时会重新加载。  
通过`/save mysnippets.jsh`命令，保存当前session为自定义启动脚本。

命令（支持tab键自动补全）
- `/vars` 所有变量
- `/methods` 所有方法
- `/types` 所有类型
- `/list` 代码片段列表
- `/env` 设置环境变量，如`--class-path`
- `/list -start` 或 `/list -all` 显示所有代码片段（含系统的启动脚本）
- `/set` 设置系统配置，包括 editor，start，feedback（）
- `/set start` 设置系统的启动脚本
- `/exit` 退出
- `/save` 保存为.jsh脚本文件
- `/help` 帮助
- `/edit` 启动外部编辑器（可`/set editor`设置），编辑所有代码片段

## jconsole
