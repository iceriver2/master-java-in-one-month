> Java的常用库。需要说明的是，初始内容来自《Java技术手册》，部分内容可能已经过时。在整理过程中，会补充部分来自网络的资料，并不会特地说明。  
> 可能Java的常用库，并没有那么难，因为同名的方法，是可能出现在JS或PHP中的。

- [异常](#%e5%bc%82%e5%b8%b8)
- [集合](#%e9%9b%86%e5%90%88)
  - [访问](#%e8%ae%bf%e9%97%ae)
  - [实用方法](#%e5%ae%9e%e7%94%a8%e6%96%b9%e6%b3%95)
  - [数组和辅助方法](#%e6%95%b0%e7%bb%84%e5%92%8c%e8%be%85%e5%8a%a9%e6%96%b9%e6%b3%95)
  - [函数式与流式表达式](#%e5%87%bd%e6%95%b0%e5%bc%8f%e4%b8%8e%e6%b5%81%e5%bc%8f%e8%a1%a8%e8%be%be%e5%bc%8f)
- [文本处理](#%e6%96%87%e6%9c%ac%e5%a4%84%e7%90%86)
- [数字与数学](#%e6%95%b0%e5%ad%97%e4%b8%8e%e6%95%b0%e5%ad%a6)
- [时间日期](#%e6%97%b6%e9%97%b4%e6%97%a5%e6%9c%9f)
- [文件相关](#%e6%96%87%e4%bb%b6%e7%9b%b8%e5%85%b3)
  - [异步](#%e5%bc%82%e6%ad%a5)
- [网络](#%e7%bd%91%e7%bb%9c)
- [元操作](#%e5%85%83%e6%93%8d%e4%bd%9c)


# 异常

异常与过程的关系
```
Throwable
- Error
- Exception
  - IOException
  - RuntimeException
```

所有的异常类是从 java.lang.Exception 类继承的子类。
异常类有两个主要的子类：IOException 类和 RuntimeException 类。

Java 语言定义了一些异常类在 java.lang 标准包中。
常见的非检查性异常有：ArithmeticException，ArrayIndexOutOfBoundsException，ArrayStoreException，ClassCastException，IllegalArgumentException，IllegalMonitorStateException，IllegalStateException，IllegalThreadStateException，IndexOutOfBoundsException，NegativeArraySizeException，NullPointerException，NumberFormatException，SecurityException，StringIndexOutOfBoundsException，UnsupportedOperationException。
检查性异常类有：ClassNotFoundException、CloneNotSupportedException、IllegalAccessException、InstantiationException、InterruptedException、NoSuchFieldException、NoSuchMethodException。

Throwable的主要方法：getMessage(), getCause(), printStackTrace(), getStackTrace(), fillInStackTrace() 。

可以自定义异常，所有异常都是 Thowable 的子类。如果希望写一个检查性异常类，则需要继承 Exception 类。如果想写一个运行时异常类，那么需要继承 RuntimeException 类。

# 集合

集合是一系列泛型接口，描述常见的数据类型格式。Java为每一种典型的数据结构都提供了多种实现方式，而且这些类型都通过接口实现。

集合定义了两种基本的数据结构，一种是 Collection，表示一组对象的集合；一种是Map，表示对象间的一系列映射或关联关系。
> iceman注：两种结构的区别在于，对于k-v对来说，k重要还是v重要。如果v重要，就用Collection；如果k重要，就用Map。

来自网络的一张图：
![集合以及集合之间的继承关系](https://img-blog.csdnimg.cn/20190708225326227.jpg)

集合类及其继承关系（带*的是类，不带是接口）：
```
Map
- SortedMap
  - TreeMap*
- WeakHashMap*
- Hashtable*
- HashMap*

Collection
- Queue
- List
  - AbstractList*
    - LinkedList
    - ArrayList
    - Vector
- Set
  - HashSet*
  - SortedSet
    - TreeSet*
```

要点：**最重要的类：HashMap（Map类）、HashSet（Set类）、ArrayList（List类）**。


Set是一种Collection，其中没有重复对象；List也是一种Collection，其中的元素是按照顺序排列。  
SortedSet和SortedMap是特殊的集和映射，其中的元素按顺序排列。

Collection、Set、List、Map、SortedSet、SortedMap都是接口，不过java.util包定义了多个具体实现，如基于数组和链表的列表，基于哈希表或二叉树的映射和集。此外还有两个重要接口，Iterator和Iterable，用于遍历集合中的对象。

【Collection接口】

`Collection<E>` 是参数化接口，表示由泛型E对象组成的集合。
**Set、List、Queue都是Collection的接口。**

Collection和Map及其子接口都没有扩展 Cloneable 或 Serializable 接口。但在Java集合框架中，实现集合或映射的所有类都实现了这两个接口。

Collection接口提供了一些方法，可以用于Set、List或Queue。如：add(), addAll(), remove(), removeAll(), retainAll(), clear(), isEmpty(), size(), contains(), containisAll()。

**在数据和集合之间，经常需要来回转换，需要用到两个方法： Arrays.asList() 和 Collection.toArray() 。**
> iceman注：猜测，因为 Collection 中，只有 List 的属性与 Array 相似，所以，Array 可以转为 List 。

> iceman住：目前，对于在不同类型之间的转来转去，还是有些模糊。书中的样例代码的写法形如 `Collection<String> c = new HashSet<>();`，但 openjdk 1.8 下编译报错，提示 Collection 没有这种写法。但 List，Set，Map 就没有这个问题。

【Set接口】

集是无重复对象组成的集合。多数通用的Set实现不会对元素排序，但不禁止使用有序集（如 SortedSet 和 LinkedHashSet）。

除了Collection接口定义的方法外，Set没有定义其他方法，但对这些方法做了额外的限制。

**实现Set接口的类：HashSet，LinkedHashSet，EnumSet，TreeSet，CopyOnWriteArraySet。**
- **HashSet 内部使用哈希表表示，无序，最佳通用实现**
- LinkedHashSet 内部使用哈希链表表示，元素顺序为插入的顺序
- EnumSet 内部使用位域表示，只能保存非null枚举值
- TreeSet 内部使用红黑树表示，元素升序排列，元素所属的类型要实现 Comparable 或 Comparator 接口
- CopyOnWriteArraySet 内部用数组表示，元素顺序为插入的顺序，不使用同步方法也能保证线程安全

TreeSet类使用红黑树数据结构维护集，这个集中的元素按照Comparable对象的自然顺序升序迭代，或按照Comparator对象指定的顺序迭代。其实，TreeSet实现的是Set子接口，SortedSet接口。SortedSet接口提供了很多用于有序元素的方法。

【List接口】

List是一族有序的对象集合。列表中的每个元素都有特定的位置。List与数组很像，但List的大小能按需变化。

List接口没有定义操作索引范围的方法，但是定义了一个 subList() 方法。这个方法返回一个 List 对象，表示原列表指定范围内的元素。**子列表会立即反馈父列表，只要修改了子列表，父列表立即就能觉察到。**

对于有序的集合，可以使用遍历循环操作集合中的每个元素，也可以使用 Iterator 对象完成遍历。
```java
// 遍历循环
ListCollection<String> c = new ArrayList<String>;
for(String w: c) {
  System.out.print(word);
}

// Iterator循环
for(Iterator<String> i = c.iterator(); i.hasNext();) {
  System.out.print(i.next());
}

// Iterator的另一种写法
Iterator<String> iterator = c.iterator();
while (iterator.hasNext()) {
  System.out.print(i.next());
}
```

**实现List接口的5种类：ArrayList、LinkedList、CopyOnWriteArrayList、Vector、Stack。**

【Map接口】

map是一系列键值对，一个键对应一个值。Map接口定义了用于定义和查询映射的API。Map接口属于Java集合框架，但没有扩展Collection接口，因此Map只是一种集合，不是Collection类型。

Map接口最常用的几个方法：put(), get(), remove()。

Map的重要特性之一是，可以视为集合。虽然Map对象不是Collection类型，但映射的键可以看成Set对象，映射的值可以看作Collection对象，而映射的键值对可以看成由 Map.Entry 对象组成的Set对象。

**实现了Map接口的类：HashMap、ConcurrentHashMap、ConcurrentSkipListMap、EnumMap、LinkedHashMap、TreeMap、IdentityHashMap、WeakHashMap、Properties。**
- **HashMap 内部用哈希表表示，通用实现**
- ConcurrentHashMap 内部用哈希表实现，通用的线程安全实现
- ConcurrentSkipListMap 内部用哈希表实现，专用的线程安全实现
- EnumMap 内部用数组表示，键是枚举类型
- LinkedHashMap 哈希表加列表表示，保留插入或访问顺序
- TreeMap 内部用红黑树表示，按照键排序
- IdentityHashMap 内部用哈希表表示，比较时用==而非equals()
- WeakHashMap 内部用哈希表表示，不会阻止垃圾回收键
- Properties 内部用哈希表表示，使用String类的方法扩展Hashtable接口

TreeMap类实现SortedMap接口。这个接口扩展Map接口，添加了一些方法。SortedMap接口类似 SortedSet 接口，firstKey() 和 lastKey() 返回 keySet() 所得集的第一个和最后一个键；而 headMap(), tailMap(), subMap() 返回一个新的映射。

【Queue接口和BlockingQueue接口】

队列（queue）是一组有序的元素，提取元素时按顺序从头读取。队列一般按照插入元素的顺序实现，分为FIFO，LIFO两类。其中，LIFO也叫栈（stack），Java有Stack类，但不建议使用。

Queue接口没有处理索引的方法，只能从头开始访问。Queue的所有实现都有一个固定的容量，队列已满时，就不能再添加；队列已空，也不能再删除。

BlockingQueue队列是一种定义了阻塞式 put() 和 take() 方法的队列。put()插入元素，如果有需要会一直等待到有空间；take()从队首移除元素，如果有需要，会一直等待。  
阻塞式队列是很多多线程算法的重要组成部分，BlockingQueue 接口（扩展Queue接口）在 java.util.concurrent 包中定义。

队列不像集、列表和映射那么常用，只在特定的多线程编程风格中会用到。

LinkedList类也实现了Queue接口，提供的是无界FIFO顺序。

java.util 包中还有另外两个Queue接口的实现。
一个是 PriorityQueue 类，这种队列根据 Comparator对象排序元素，或根据 Comparable 类型元素的 CompareTo() 方法排序元素。
另一个是 ArrayDeque 类，实现的是双端队列，一般在需要用到栈的情况下使用。

## 访问

Iterator接口定义了一种迭代集合或其他数据结构中元素的方式。迭代的过程是：只要集合中还有更多的元素（hasNext()返回true），就调用next()获得集合中的下一个元素。有序集合一般能保证按顺序返回元素，而无序集合只能保证最终返回所有但不能保证顺序。

引入Iterable接口是为了支持遍历循环。类实现这个接口是为了表明它提供了Iterator对象，可以迭代。如果某个对象是`Iterable<E>`类型，表明它有一个能返回`Iterator<E>`对象的 iterator() 方法，而 `Iterator<E>`对象有 next() 方法能返回 E 类型的对象。如果使用遍历循环迭代 `Iterable<E>` 对象，循环变量必须是E类型，或是E类型的超类或实现的接口。

提供随机访问的类都实现了 RandomAccess 接口。可以通过 instanceof 运算符测试是否实现了这个接口。

## 实用方法

java.util.Collections 类定义了一些静态实用方法，用于处理集合。其中有一类方法很重要，是**集合的包装方法**：这些方法包装指定的集合，返回特殊的集合。包装集合的目的是把集合本身没有提供的功能绑定到集合上。**包装集合能提供的功能有：线程安全性、写保护和运行时类型检查。**包装集合都以原来的集合为后盾，因此，在包装集合上调用的方法其实会分派给原集合的等效方法完成。这意味着，通过包装集合修改集合后，改动也会体现在原集合身上，反之亦然。

java.util 包中的集合实现，都没有 synchronized 方法。如果要保证线程安全性，可以使用 Collections.synchronzedList 方法： ` List<String> list = Collections.synchronziedList(new ArrayList<String>());`。  
另一个用于多线程的方法是将集合设置为只读： `List<Integer> readonly = Collections.unmodifiableList(new ArrayList<Integer>());`。

Collections还有一些重要静态方法，如： sort(), binarySearch(), copy(), fill(), max(), min(), reverse(); shuffle(), emptySet(), emptyList(), emptyMap(), nCopies()（返回不可变的List对象副本） 等。

最好熟悉 Collections 和 Arrays 类的全部实用方法。

## 数组和辅助方法

由对象组成的数组和集合作用相似，二者可以相互转换。
```java
String[] a = {"this", "is", "a", "test"};
List<String> l = Arrays.toList(a);

Set<Character> abc = new HashSet<Character>(Arrays.asList('a','b','c'));
Object[] members = set.toArray();
Object[] items = list.toArray();
```

Arrays 的一些重要方法： Arrays.asList(), Arrays.sort(), Arrays.binarySearch()， Arrays.equals(), Arrays.fill() 。

System.arrayCopy() 可以局部复制数组。

## 函数式与流式表达式

Java的函数式编程表达式，主要设计4个方法：filter(), map(), forEach(), reduce()。

filter() 方法的参数是一个 Predicate 接口的实例。Predicate接口在 java.util.function 中定义，只有一个非默认方法。

map() 方法使用接口是 `Function<T,R>`，也是在 java.util.function 中定义，只有一个默认方法 apply()。

forEach() 方法的参数是一个 `Consumer<T>` 类型的对象，这是个函数式接口，要求使用副作用执行操作，签名是 `(T t) -> void` 。

reduce() 方法有两个参数：一个是初始值，另一个是一个执行函数。这个函数属于 `BinaryOperator<T>` 类型。`BinaryOperator<T>`也是函数式接口，有两个类型相同的参数，返回值也是同一类型。

一个示例（注意，Arrays.asList 要求数组的元素是引用类型，而不能是基本类型）
```java
String[] input = {"tiger", "cat", "TIGER", "Tiger", "leopard"};
List<String> cats = Arrays.asList(input);

String search = "tiger";
String tigers = cats.stream()
              .filter(s -> s.equalsIgnoreCase(search))
              .collect(Collectors.joining(","));
System.out.println(tigers);
```


库的设计者之所以引入流API，是因为集合核心接口的大量实现已经广泛使用。设计者引入了一层新的抽象-- Stream。Stream对象可以通过 stream() 方法从集合对象上生成。  
在处理集合的新方式中，**Stream对象的作用类似于 Iterator对象**。总体思想是让开发者把一系列操作（也叫“管道”）当成一个整体运用在集合上。具体执行的各个操作一般使用 lambda 表达式。  
在管道的末尾需要收集结果，或再次“具化”为真正的集合。这一步使用 Collector 对象完成，或者以“终结方式”（如 reduce()）结束管道，返回一个具体的值。  
整个过程差不多是这样的 `Collection -> Stream -> Collection` 。

Stream类也需要使用引用类型参数化。不过，多数情况下需要使用基本类型，尤其是 int 和 double 类型构成的流，但是又没有 `Stream<int>` 类型，所以， java.util.Stream 包提供了专用的类，如 IntStream 和 DoubleStream 。这些类是 Stream 类的基本类型特化。

# 文本处理

Character的常用方法：isLetter(), isDigit(), isWhiteSpace(), isUpperCase(), isLowerCase(), toUpperCase(), toLowerCase()。


字符串字面类是有效的对象。

字符串的处理中，经常会有 ignorecase 的选项，例如 compareTo() 和 equals() 都有 ignrecse 版本。

String的常用方法：compareTo(), compareToIgnoreCase(), lastIndexOf(), replace(), replaceFirst(), replaceAll(), indexOf(), lastIndexOf(), split(), toUpperCase(), toLowerCase(), regionMatches().

Object.toString() 可以将任何对象转为字符串。

字符串连接使用+运算符。实际的过程是：先创建一个使用 StringBuilder 对象表示“工作区”，其内容与原始字符串一样；然后更新 StringBuilder 对象，把另一个字符串的字符添加到末尾；最后，调用 StringBuilder 对象的 toString() 方法得到新的字符串。

StringBuilder不是线程安全的，但是速度很快。
```java
StringBuilder sb = new StringBuilder();
sb.append("hello");
sb.delete(1, 3);
sb.insert(1, "here");
out.println(sb);
```

ava.util.regex 包主要包括以下三个类：Pattern， Matcher， PatternSyntaxException。
Matcher的重要方法： start(), end(), find(), repalceAll(), replaceFirst()

字符串中正则表达式的使用
```java
Pattern p = Pattern.compile("hnono?r");

String caesarUK = "For brutus is an honourable man";
Matcher umk = p.matcher(caesarUK);

System.out.println("Does it match?" + umk.find());
```

为了让Pattern支持lambda写法，Pattern新增了一个方法 asPredicate() 。这样，就可以用简单的方法把正则表达式与Java集合和对lambda的支持联系起来了。
```java
String pstr = "\\d";
Pattern p = Pattern.compile(pstr);
String[] inputs = { "cat", "Dog", "Ice-9" };
List<String> ls = Array.asList(inputs);
List<String> containsDigits = ls.stream().filter(p.asPredicate()).collect(Collectors.toList());
System.out.println(containisDigits);
```

# 数字与数学

浮点数是不精确的，只是近似值。

java.lang.Math 定义了很多**静态辅助方法**，如： abs(), 三角函数, max(), min(), floor(), ceil(), pow(), exp(), log()。

Math.random() 方法，首次调用时，会创建一个 java.util.Random 类的实例。返回一个 [0.0, 1.0) 之间的浮点数。random()生成的随机数，并不是真正的随机数，而是伪随机数。为了提高随机性，可以使用一个随机种子，例如，CPU计数器的值。

Number类有一系列xxxValue()方法，将 Number 对象转为xxx数据类型，包括：byteValue(), doubleValue(), floatValue(), intValue(), longValue(), shortValue() 。

.valueOf() 静态方法，可以生成一个执行数据类型的实例。

# 时间日期

Java8之前使用 java.util.Date 类处理时间和日期。这个包有很多问题。
时间是用 Date 类。日期是用 Calendar 类。GregorianCalendar是Calendar类的一个具体实现。

Java8引入一个新包 java.time，包含了很多时间日期的核心类。
- java.time.chrono 非ISO标准历法，如日本历法。
- java.time.format 将时间日期格式化伪字符串，以及把字符串解析成日期和时间对象。
- java.time.temporal 日期和时间的核心类所需的接口，还抽象了一些日期方面的高级操作
- java.time.zone 时区

使用 Instant 对象表示一个时间点。使用 Duration 类表示时间间隔。

```java
LocalDate date = LocalDate.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
String text = date.format(formatter);
LocalDate parsedDate = LocalDate.parse(text, formatter);
```

时间的显示，也可以通过 System.out.printf() 进行。

> iceman注：时间日期虽然是个较小的部分，但是，涉及到的类却很多，需要重点研究一下。

# 文件相关

Java.io 包几乎包含了所有操作输入、输出需要的类。所有这些流类代表了输入源和输出目标。Java 为 I/O 提供了强大的而灵活的支持，使其更广泛地应用到文件传输和网络编程中。

系统输入输出的使用，通过将 System.in 和 System.out 关联到 BufferedReader 和 BufferedWriter 。
```java
import java.io.*;
 
public class BRRead {
    public static void main(String args[]) throws IOException {
        char c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入字符, 按下 'q' 键退出。");
        // 读取字符
        do {
            c = (char) br.read();
            System.out.println(c);
        } while (c != 'q');
    }
}
```

文件读写类的一个图：![文件读写类导图](https://www.runoob.com/wp-content/uploads/2013/12/iostream2xx.png)

File类有很多方法，但根本没有直接提供一些基本功能（如读取文件内容）
```java
File f = new File();

//权限管理
boolean canX = f.canExecute();
boolean canR = f.canRead();
boolean canW = f.canWrite();

boolean ok;
ok = f.setReadOnly();
ok = f.setExecutable(true);
ok = f.setReadable(true);
ok = f.setWritable(false);

// 使用不同的方式表示文件名
File absF = f.getAbsoluteFile();
File canF = f.getCanonicalFile();
String absName = f.getAbsolutePath();
String canName = f.getCanonicalPath();
String name = f.getName();
String pName = f.getParent();
URI fileURI = f.toURI(); // 创建文件路径的URI形式

// 文件的元数据
boolean exists = f.exists();
boolean isAbs = f.isAbsolute();
boolean isDir = f.isDirectory();
boolean ifFile = f.isFile();
boolean ifHidden = f.isHidden();
long modTime = f.lastModified(); // 距离 epoch 时间的毫秒
boolean updateOk = f.setLastModified(updateTime); // 毫秒
long fileLen = f.length();

// 文件管理操作
boolean renamed = f.renameTo(destFile);
boolean deleted = f.delete();

// 创建文件不会覆盖现有文件
boolean createdOk = f.createNewFile();

// 处理临时文件
File tmp = File.createTempFile("somename", ".tmp");
tmp.deleteOnExit();

// 处理目录
boolean createdDir = dir.mkdir();
String[] fileNames = dir.list();
File[] files = dir.listFiles();

// 其他
long free, total, usable;
free = f.getFreeSpace();
total = f.getTotalSpace();
usable = f.getUsableSpace();

File[] roots = File.listRoots(); // 列出可用的文件系统根目录
```

Scanner 类可以用于获取用户输入。
```java
import java.util.Scanner; 

public class ScannerDemo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // 从键盘接收数据
 
        // next方式接收字符串
        System.out.println("next方式接收：");
        // 判断是否还有输入
        if (scan.hasNext()) {
            String str1 = scan.next();
            System.out.println("输入的数据为：" + str1);
        }
        scan.close();
    }
}
```

文件读写的工作，开始是使用IO流进行的，即 InputStream 和 OutStream 。实际上，标准输入和输出流（System.in 和 System.out），就是这种流。  
IO流的两个子类 FileInputStream 和 FileOutputStream 用于以字节流方式操作文件。但这种方式效率较低，因此，一般是结合 Reader 和 Writer 类使用。这两个类还有几个子类，一般结合使用：FileReader/FileWriter, BufferedReader/BufferedWriter, InputStreamReader。  
**InputStream/OutputStream 是字节流，Reader/Writer 是字符流。**

```java
try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
  String line;
  while((line = in.readLine()) != null) {
    System.out.println(line);
  }
} catch(IOException e) {

}
```

对于要使用类似于管道式通信的线程来说，还可以选择 PipedInputStream 和 PipedReader 类及对应的写入器。

TWR（try-with-resource）的关键是一个新接口 AutoClosable ，它是 Closeable 的直接超接口，表示资源必须自动关系。
在TWR的资源子句中，只能声明实现了 AutoClosable 接口的对象，数量不限。
```java
try(BufferedReader in=new BufferedReader(new FileReader(filename));
    PrintWriter out=new PrintWriter(new BufferWriter(new FileWriter(othername)))
) {
  //...
} catch(IOException e) {
  //...
}
```

Java7引入了全新的IO API（一般称为NIO.2），几乎可以完全取代以前使用File类处理IO的方式。新添加的各个类都在 java.nio.file 包。新API分为两大部分，第一部分是一个新抽象 Path接口；第二部分是很多处理文件和文件系统的新方法。
> iceman注：从新API来看，就是将之前的实例方法，转为类方法。

Files类的重要方法
```java
Path source, target;
Attributes attr;
Charset cs = StandardCharsets.UTF_8;

// 创建文件
Files.createFile(target, attr);

// 删除文件
Files.delete(target);
boolean deleted = Files.deleteIfExists(target);

// 复制/移动
Files.copy(source, target);
Files.move(source, target);

// 读取信息
long size = Files.size(target);

FileTime fTime = Files.getLastModifiedTime(target);

// 处理文件类型
boolean isDir = Files.isDirectory(target);
boolean isSym = Files.isSymbolicLink(target);

// 处理读写操作
List<String> lines = Files.readAllLines(target, cs);
byte[] b = Files.readAllBytes(target);

BufferedReader br = Files.newBufferedReader(target, cs);
BufferedWriter bwr = Files.newBufferedWriter(target, cs);

InputStream is = Files.newInputStream(target);
OutputStream os = Files.newOutputStream(target);
```

Path 接口用于在文件系统中定位文件。Path是接口，而不是类。  
Paths 类提供了两个 get() 方法，用于创建 Path 对象。普通版本接受一个String对象，使用默认的文件系统提供方；另一个版本接受一个URI对象。

Path对象和File对象之间可以轻易的相互转换。
```java
Path p = Paths.get("/tmp/1.txt");

File f = p.toFile();
Path p2 = f.toPath();
```

## 异步

新异步功能的关键组成部分是实现 Channel 接口的类，这些类可以处理需要交给后台线程完成的IO操作。这种哦功能功能还可以应用于长期运行的大型操作和其他几种场合。

几个异步的类
- AsynchronousFileChannel 类处理文件 IO
- AsynchronousSocketChannel 处理客户端套接字IO
- AsynchronousServerSocketChannel 类处理能接受连入连接的异步套接字

和异步通道交互有两种不同的方式：使用 Feture接口和回调。
- Feture接口有两个关键方法： isDone(), get()
- 回调基于 CompletionHandler 接口，接口有两个方法： completed() 和 failed() 。

```java
// Future 方式
try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fillname))) {
  ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024*100);
  Future<Integer> result = channel.read(buffer, 0);

  while(!result.isDone()) {
    // ...
  }
  System.out.println("Bytes read:" + result.get());
} catch(Exception e) {

}

// 回调方式
byte[] data = {1,2,3};
ByteBuffer buffy = ByteBuffer.wrap(data);

CompletionHandler<Integer, Object> h = new CompletionHandler() {
  public void completed(Integer written, Object o) {
    System.out.println("Bytes written"+ written);
  }
  public void failed(Throwable x, Object o) {
    System.out.println("Asynch write failed" + x.getMessage());
  }
}
try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(filename), StandardOpenOption.CREATE， StandardOpenOption.WRITE)) { 
  channel.write(buffy, 0, null, h); // 这个格式确实比较像JS的回调
  Thread.sleep(1000); // 必须这么做，防止太快退出
}
```

# 网络

Java支持大量的标准网络协议。网络协议的核心API在 java.net 包中，其他扩展在 javax.net 包中（尤其是 javax.net.ssl 包）。

HTTP是最常见的协议，URL是关键的类。
```java
URL url = new URL("http://exmaple.com");
try(
  InputStream in = url.openStream();
) {
  Files.copy(in, Paths.get("out.txt"));
} catch (IOException ex) {
  //
}
```

要深入底层控制，如获取请求和响应的元数据，可以使用 URLConnection 类。
```java
try {
  URLConnection conn = url.openConnection();

  String type = conn.getContentType();
  String encoding = conn.getContentEncoding();
  Date lastModified = new Date(conn.getLastModified());
  int len = conn.getContentLength();
  InputStream in = conn.getInputStream();
} catch (IOException e) {
  //...
}
```

URLConnection类还有很多其他方法，例如发起请求。
```java
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("POST");

// ...

conn.setDoOutput(true);
OutputStream os = conn.getOutputStream();
os.write("");

int response = conn.getResponseCode();
if (response == HttpURLConnection.HTTP_MOVED_PERM
  || response == HttpURLConnection.HTTP_MOVED_TEMP) { // HttpURLConnection的缺陷，不能处理重定向请求
    //...
  }
```

TCP是双向通信通道，可以保证可靠的通信。为了在同一个网络主机上支持多个不同的服务，TCP使用端口号识别服务。
Java使用 Socket 类表示客户端，ServerSocket 类表示服务端。当然，在生产环境中，很少需要自己写服务端，一般框架都会提供。
```java
// 服务端通信的核心代码之一（另一个部分，就是一个无限循环，不断调用本类完成处理）
private static class HttpHandler implememts Runnable {
  private final Socket sock;
  HttpHandler(Socket client) { this.sock = client;}

  public void run() {
    try(
      BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      PrintWriter out = new PrintWriter(new OutputStream(sock.getOutputStream()));
    ) {
      out.print("HTTP/1.0 200\r\nContent: text/plain\r\n\r\n");
      String line;
      while((line = in.readLine()) != null) {
        if (line.length() == 0) break;
        out.println(line);
      }
    } catch (Exception e) {
      //
    }
  }
}
```

以下步骤在两台计算机之间使用套接字建立TCP连接时会出现：
- 服务器实例化一个 ServerSocket 对象，表示通过服务器上的端口通信。
- 服务器调用 ServerSocket 类的 accept() 方法，该方法将一直等待，直到客户端连接到服务器上给定的端口。
- 服务器正在等待时，一个客户端实例化一个 Socket 对象，指定服务器名称和端口号来请求连接。
- Socket 类的构造函数试图将客户端连接到指定的服务器和端口号。如果通信被建立，则在客户端创建一个 Socket 对象能够与服务器进行通信。
- 在服务器端，accept() 方法返回服务器上一个新的 socket 引用，该 socket 连接到客户端的 socket。
- 连接建立后，通过使用 I/O 流在进行通信，每一个socket都有一个输出流和一个输入流，客户端的输出流连接到服务器端的输入流，而客户端的输入流连接到服务器端的输出流。

ServerSocket类的主要方法： getLocalPort(), accept(), setSoTimeout(), bind() 。  
Socket 类的主要方法： connect(), getInetAddress(), getPort(), getLocalPort(), getRemoteSocketAddress(), getInputStream(), getOutputStream(), close() 。  
InetAddress 类的常用方法： getByAddress(), getByAddress(), getByName(), getHostAddress(), getHostName(), getLocalHost(), 

----------------------------

# 元操作

> iceman注：这个部分算比较高级的内容，平时用到的并不算很多，简单了解下。

类的加载过程：
- 先加载一个字节数组，这个数组从文件系统中读取或URL等。通过 Classloader::defineClass() 方法把类文件转为类对象，但不会生成完整的类对象。
- 验证类文件与预期相符，没有违背JVM的安全模型。
- JVM确保这个类文件中引用的每个类型都被加载。
- 分配内存，初始化类的静态变量，运行静态初始化代码。
