> Java的常用库。需要说明的是，内容来自《Java技术手册》，部分内容可能已经过时。

- [集合](#%e9%9b%86%e5%90%88)
  - [访问](#%e8%ae%bf%e9%97%ae)
  - [实用方法](#%e5%ae%9e%e7%94%a8%e6%96%b9%e6%b3%95)
  - [数组和辅助方法](#%e6%95%b0%e7%bb%84%e5%92%8c%e8%be%85%e5%8a%a9%e6%96%b9%e6%b3%95)
- [文本处理](#%e6%96%87%e6%9c%ac%e5%a4%84%e7%90%86)
- [数字与数学](#%e6%95%b0%e5%ad%97%e4%b8%8e%e6%95%b0%e5%ad%a6)
- [时间日期](#%e6%97%b6%e9%97%b4%e6%97%a5%e6%9c%9f)
- [文件相关](#%e6%96%87%e4%bb%b6%e7%9b%b8%e5%85%b3)
- [网络](#%e7%bd%91%e7%bb%9c)
- [元操作](#%e5%85%83%e6%93%8d%e4%bd%9c)
  - [类加载](#%e7%b1%bb%e5%8a%a0%e8%bd%bd)
  - [反射](#%e5%8f%8d%e5%b0%84)
  - [句柄](#%e5%8f%a5%e6%9f%84)


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

> iceman住：目前，对于在不同类型之间的转来转去，还是有些模糊。书中的样例代码的写法形如 `Collection<String> c = new HashSet<>();`。但 openjdk 1.8 下编译报错。

【Set接口】

集是无重复对象组成的集合。多数通用的Set实现不会对元素排序，但不禁止使用有序集（如 SortedSet 和 LinkedHashSet）。

除了Collection接口定义的方法外，Set没有定义其他方法，但对这些方法做了额外的限制。

**实现Set接口的类：HashSet，LinkedHashSet，EnumSet，TreeSet，CopyOnWriteArraySet。**
- HashSet 内部使用哈希表表示，无序，最佳通用实现
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
- HashMap 内部用哈希表表示，通用实现
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

# 文本处理

# 数字与数学

# 时间日期

# 文件相关

# 网络

----------------------------

# 元操作

## 类加载

## 反射

## 句柄

