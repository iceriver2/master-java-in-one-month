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

> 工具就是要多用，熟练生巧，其他没什么可说的。

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

Use the java command to launch a Java application.

```bash
# To launch a class file:
java [options] mainclass [args...] # args: the arguments for the main method.

# To launch the main class in a JAR file:
java [options] -jar jarfile [args...] # The jarfile argument is the name of a JAR file with a manifest that contains a line in the form `Main-Class:classname` that defines the class with the `public static void main(String[] args)` method that serves as your application's starting point.

# To launch the main class in a module:
java [options] --module module[/mainclass] [args...] # Executes the main class in a module specified by `mainclass` if it is given, or, if it is not given, the value in the module. `mainclass` can be used when it is not specified by the module.

# To launch a single source-file program:
java [options] source-file [args...] # Only used to launch a single source-file program. Specifies the source file that contains the main class when using source-file mode. The --source option can be used to specify the source version or N of the source code.
```

java允许时，启动jre，加载class文件，调用main()方法。 main() 的定义必须为 `public static void main(String[] args)`，必须为 public static ，必须无返回值，必须接受一个字符串数组作为参数。

JDK_JAVA_OPTIONS环境变量可用于预先设置java命令的参数。

标准参数，用于通用操作如：检查jre版本、设置classpath。  
扩展参数，用于Java HotSpot VM，以`-X`开头。  
高级参数，不推荐任意使用，以`-XX`开头，包括：Advanced Runtime Options、Advanced JIT Compiler Options、Advanced Serviceability Options、Advanced Garbage Collection Options。

重要的标准参数：
- `--class-path classpath`, `-classpath classpath`, or `-cp classpath` 分号隔开的目录、jar压缩文件、zip压缩文件，用于搜索类文件。这将覆盖 CLASSPATH 环境变量，默认为当前目录。可以使用 * 符，如 `mydir/*`。
- `--module-path modulepath...` or `-p modulepath` 分号隔开的目录，每个目录都是一个模块目录。
- `--upgrade-module-path modulepath...` 分号隔开的目录列表，每个目录都是一个模块目录，用于代替在运行时映像中可升级的模块。
- `--add-modules module[,module...]` 在初始化模块之外指定根模块，还可以是ALL-DEFAULT, ALL-SYSTEM, 和 ALL-MODULE-PATH。
- `--disable-@files` 可在命令行任何地方使用，包括参数文件。这个参数后，将停止展开 `@-argfiles` 。
- `--list-modules` 列出显式模块后退出。
- `-d module name or --describe-module module_name` 描述一个指定的模块后退出。
- `--dry-run` 创建VM但不执行main 方法。这在验证命令行参数（如模块系统配置）时很有用。
- `--validate-modules` 验证所有的模块后退出。这对于发现模块和模块路径的冲突和其他错误很有帮助。
- `-Dproperty=value` 设置一个属性值。
- `--show-version` 打印产品版本号到输出流后继续。
- `-verbose:class` 显示每个被加载的类的信息。
- `-verbose:gc` 显示每个gc事件的信息。
- `-verbose:jni` 显示自带方法的使用信息，和其他Java Native Interface (JNI) 活动的信息。
- `-verbose:module` 显示使用中的模块的信息。
- `-version` 打印产品版本号到输出流后退出。
- `@argfile` 指定一个或多个参数文件，以`@`符开头。参数文件也可以包含主类名和所有参数。

重要扩展参数：
- `-Xbatch` 禁用后台编译，改为前台编译。
- `-Xbootclasspath/a:directories|zip|JAR-files` 指定目录、jar文件、zip文件的列表，最佳到默认 bootstrap class 路径。
- `-Xdiag` 显示额外的诊断消息。
- `-Xfuture` 启用严格的类文件格式检查，以获得接近类文件格式说明中的性能。开发者开发新代码时应该使用本选项。
- `-Xinternalversion` 显示Jvm版本信息的细节后退出。
- `-Xlog:option` Configure or enable logging with the Java Virtual Machine (JVM) unified logging framework. The default configuration is `-Xlog:all=warning:stdout:uptime,level,tags`
- `-Xmn size` 设置初始和最大的新生代使用的堆大小，单位byte，也可以使用k，m，g。新生代的堆用于新对象的使用。推荐把这个值设置为全部堆大小的25%-50%。也可以将初始值和最大值分开设置，`-XX:NewSize` 和 `-XX:MaxNewSize`。
- `-Xms size` 设置堆的初始大小。单位支持k，m，g。
- `-Xmx size` 指定内存池的最大大小，单位bytes，支持k，m，g。`-Xms` 和 `-Xmx` 经常设置为同样大小。
- `-Xss size` 设置线程栈大小。单位支持k，m，g。
- `-XshowSettings` 显示所有设置后继续
- `-XshowSettings:category` 显示设置后继续，category可能为：all，locale，properties，vm，system。

## javadoc

javadoc读取源码的特定内容，并转为一种内部格式。其结构是：`The structure is: (source-files)->[javadoc-tool:doclet]->(generated files)`。其中，doclet类似后端插件，用于分析这种内部格式。doclet不同，可能生成不同的文件，如HTML、MIF、XML等。默认使用Standard Doclet。

Javadoc特性包括：Javadoc搜索，支持生成HTML5输出，支持模块系统中的文档注释，简化的Doclet API。

Javadoc能处理的文件包括：java源码文件，包注释文件，概述注释文件，和其他未处理文件。

每个应用程序或包可有有自己的概述注释文件，javadoc将合并其内容以生成概述页。overview.html可以放在任何地方，当一般放置于源码树的顶层。  
概述注释文件是HTML格式的一个大的文档注释。第一句是关于应用程序或包的简介。不要在body标签和第一句话之间放置标题或任何文字。所有标签，除了内连标签如`{@link}`，必须出现在主描述之后。如果增加`@see`标签，它必须拥有一个全名。  
运行javadoc时，通过`-overview`指定概述注释文件的位置。其后续处理与包的注释文件相似，过程为：（1）拷贝body部分的所有内容待处理（2）处理出现的概述标签（3）处理过的文本插入到概述页的底部（4）将第一句简介拷贝到概述简介页的顶部。
```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
jMonkeyEngine is a game engine made for developers who want to create 3D games following modern technology standards.
</body>
</html>
```

源码文件可以包含任何想要javadoc工具拷贝到目标目录的文件。这些文件通常包括图形文件，示例代码，html文件。
要包含这些未处理文件，将它们放入名为doc-files的目录。doc-files目录可能是任何包目录的子目录。每个包都可以有一个doc-files子目录。
注意，javadoc不会处理这些文件只是拷贝，因此，所有指向未处理文件的链接必须要使用html代码指定，如
```java
/**
 * <p> This button looks like this:</p> 
 * <img src="doc-files/Button.gif"/>
 */
```

测试文件和模板文件也会保存在源码目录中，也会有.java后缀。
要想不被javadoc处理，可以将其放入一个不可能是包名的子目录中，例如test-files（含有连字符）。
如果这些文件中含有注释，可以单独运行一次javadoc，以便生成文档，例如`javadoc com/package1/test-files/*.java`。


javadoc使用方法是： `javadoc [options] [packagenames] [sourcefiles] [@files]`。
如果指定源文件名称，可以控制哪些文件被处理。但多数开发者不这么做，因为直接传入一个包名跟简单。
如果不指定源文件名称，可以传入包名，使用`-subpackages`参数，或使用通配符匹配源文件。

使用示例
```bash
# Recursive Run from One or More Packages.
# This example uses -sourcepath so the javadoc command can be run from any directory for recursion. It traverses the subpackages of the Java directory excluding packages rooted at java.net and java.lang. Notice this excludes java.lang.ref, a subpackage of java.lang. To also traverse down other package trees, append their names to the -subpackages argument, such as java:javax:org.xml.sax.
javadoc -d /home/html -sourcepath /home/src -subpackages java -exclude

# Run Explicit Packages
# To also traverse down other package trees, append their names to the -subpackages argument, such as java:javax:org.xml.sax.
javadoc -d /home/html java.awt java.awt.event

# Run from Any Directory on Explicit Packages in One Tree
javadoc -d /home/html -sourcepath /home/src java.awt java.awt.event

# Run from Any Directory on Explicit Packages in Multiple Trees
javadoc -d /home/html -sourcepath /home/src1:/home/src2 java.awt java.awt.event

# Document One or More Classes
javadoc -d /home/html java/awt/Button.java java/math/BigDecimal.java

# Document Files from Any Directory
javadoc -d /home/html /home/src/java/awt/Button.java /home/src/java/awt/Graphics*.java

# Document Packages and Classes
javadoc -d /home/html -sourcepath /home/src java.awt /home/src/java/math/BigDecimal.java
```


## jar

jar命令可以用于创建class文件和资源的压缩文件，操纵或恢复压缩包中的单个类或资源。

jar命令是个通用的打包和压缩工具，基于zip和zlib压缩格式。

The jar command also enables individual entries in a file to be signed so that their origin can be authenticated.

```bash
jar [OPTION...] [ [--release VERSION] [-C dir] files] ...
```

Jar文件可以被用过class path入口。

当一个压缩包在指定目录的根或jar包的根上包含一个模块描述文件module-info.class时，就会变成一个模块jar。

主要操作模式（jar使用时必选）
- `-c` or `--create` 创建压缩包
- `-i=FILE` or `--generate-index=FILE` 为指定的jar文件生成索引信息
- `-t` or `--list` 列出压缩包的内容
- `-u` or `--update` 更新一个已经存在的jar文件。
- `-x` or `--extract` 从压缩包中解压指定文件或全部文件
- `-d` or `--describe-module` 打印模块描述器或自动模块名称

所有模式通用的操作修饰符
- `-C DIR` 改变指定的目录，并在命令行的末尾包含指定的文件（可以使用`.`）
- `-f=FILE` or `--file=FILE` 指定压缩包的名称
- `--release VERSION` 创建一个多版本的jar文件。将所有指定文件放入到jar文件中一个编号的目录`META-INF/versions/VERSION/`中。运行时，当一个类存在多个版本时，JDK会使用它找到的第一个，开始时搜索与JDK主版本号匹配VERSION的目录，然后相继搜索较低版本号的目录，最后搜索jar根目录。
- `-v` or `--verbose` 发送或打印可视化输出到表stdout。

只对create和update有用的操作修饰符
- `-e=CLASSNAME` 或 `--main-class=CLASSNAME` 指定模块捆绑的独立应用或可执行jar文件的入口点。
- `-m=FILE` 或 `--manifest=FILE` 从指定的manifest文件中引入manifest信息。
- `-M` 或 `--no-manifest` 不为入口创建manifest文件。
- `--module-version=VERSION` 当创建或更新jar文件时，指定模块版本。
- `--hash-modules=PATTERN` 创建模块化jar文件或更新非模块化jar文件时，计算并记录匹配指定pattern的模块的hash值。
- `-p path` 或 `--module-path path` 指定用于生成hash的模块依赖的位置。
- `@file` 从文本文件中读取jar参数和文件名称。

只对create、update、Generate-index有用的操作修饰符
- `-0` or `--no-compress` 不使用zip压缩

使用示例
```bash
# Create an archive, classes.jar, that contains two class files, Foo.class and Bar.class.
jar --create --file classes.jar Foo.class Bar.class

# Create an archive, classes.jar, by using an existing manifest, mymanifest, that contains all of the files in the directory foo/.
jar --create --file classes.jar --manifest mymanifest -C foo/

# Create a modular JAR archive, foo.jar, where the module descriptor is located in classes module-info.class.
jar --create --file foo.jar --main-class com.foo.Main --module-version 1.0 -C foo/classes resources

# Update an existing non-modular JAR, foo.jar, to a modular JAR file.
jar --update --file foo.jar --main-class com.foo.Main --module-version 1.0 -C foo/module-info.class

# Create an archive, my.jar, by reading options and lists of class files from the file classes.list.
jar --create --file my.jar @classes.list
```

```bash
# 创建一个版本化或多发布的jar，foo.jar，将 classes 目录的文件放在 jar 的根，classes-10 目录的文件放在 jar 的 META-INF/versions/10 目录。
# 本例中，classes/com/foo目录包含2个class，com.foo.Hello(入口class)和com.foo.NameProvider，均基于JDK8编译。classes-10/com/foo目录包含一个不同版本的com.foo.NameProvider，这个class包含JDK10特定的代码并基于JDK10编译.
# 从包含classes和classes-10目录的目录下，运行以下命令，创建一个多发布的jar文件foo.jar。
jar --create --file foo.jar --main-class com.foo.Hello -C classes . --release 10 -C classes-10 .

# JAR文件foo.jar的内容：
# - META-INF/
# - META-INF/MANIFEST.MF
# - com/
# - com/foo/
# - com/foo/Hello.class
# - com/foo/NameProvider.class
# - META-INF/versions/10/com/
# - META-INF/versions/10/com/foo/
# - META-INF/versions/10/com/foo/NameProvider.class 

# META-INF/MANIFEST.MF文件，包含以下内容，以指明这是个多发布的jar文件，入口点是com.foo.Hello。
# - Main-Class: com.foo.Hello
# - Multi-Release: true

# 假定com.foo.Hello调用com.foo.NameProvider的方法，运行在JDK10时需要保证com.foo.NameProvider存在于META-INF/versions/10/com/foo/下，运行在JDK8时需要保证com.foo.NameProvider在jar的根下的目录com/foo下。
```

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

Use the jconsole command to start a graphical console to monitor and manage Java applications.

```bash
jconsole [-interval=n] [-notile] [-plugin path] [-version] [connection ... ] [-Jinput_arguments]
jconsole -help
```

主要参数
- `-interval` 设置更新间隔为n秒(默认4秒)。
- `-pluginpath path` 指定jconsole使用的插件的目录。插件路径应该包含一个名为META-INF/services/com.sun.tools.jconsole.JConsolePlugin的配置文件，文件每一行对应一个插件。 每一行指定一个应用了com.sun.tools.jconsole.JConsolePlugin的类的全名。
- `connection = pid | host:port | jmxURL` 由pid、host:port或jmxURL描述的连接。
- `-Jinput_arguments` 将input_arguments传递给jconsole运行的JVM。
