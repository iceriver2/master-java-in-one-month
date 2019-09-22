import java.util.Arrays;
import java.util.HashSet;

/**
 * 简单演示Collection的用法
 * 
 * 对照 objects.md 的集合类与继承关系图，可以使用的类有：
 * TreeMap, WeakHashMap, HashMap, LinkedList, ArrayList, HashSet, TreeSet
 * 
 * 有一些书上的写法，在vsc中会报错，例如 `Collection<String> c = new HashSet<>();`
 * 因为 Collection 是接口
 */
import static java.lang.System.out;

public class Collection
{
  public static void main(String[] args) {
    TestClass tc = new TestClass();
    HashSet<String> tcc = tc.testHashSet();

    Object[] d = tcc.toArray(); // 直接赋值给对象数组
    out.println(d.toString());

    String[] e = tcc.toArray(new String[tcc.size()]); // 复制给字符串数组
    out.println(e.toString());

    out.println(tcc.toString());
    out.println(tcc.contains("zero"));
    // tcc.clear();
    // out.println(tcc.toString());
    out.println("empty: " + tcc.isEmpty() + " size: " + tcc.size());
  }
}

/** 
 * 测试类，主要从 Map、Set、List 接口中各找一个主要类进行试用
 * - Map
 * - Set--HashSet
 * - List
 * 
 */
class TestClass {
  public HashSet<String> testHashSet() {
    HashSet<String> c = new HashSet<>();
    c.add("zero");
    c.add("one");
    c.remove("one");
    return c;
  }
}