import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 简单演示Collection的用法
 * 
 * 对照 objects.md 的集合类与继承关系图，可以使用的类有：
 * TreeMap, WeakHashMap, HashMap, LinkedList, ArrayList, HashSet, TreeSet
 * 
 * 有一些书上的写法，在vsc中会报错，例如 `Collection<String> c = new HashSet<>();`
 * 因为 Collection 是接口
 */

public class Collection
{
  public static void main(String[] args) {
   TestClass obj = new TestClass();

   String[] example = new String[] {"this", "is", "a", "test"};

   Set set = obj.testSet(example);
   out.println(set.toString());

   List list = obj.testList(example);

   Map map = obj.testMap(example);
   out.println("map contains `world` ? " + map.containsKey("world"));
   out.println("value of a:" + map.get("a"));
   out.println(map.toString());

   obj.testStream();
  }
}

/** 
 * 测试类，主要从 Map、Set、List 接口中各找一个主要类进行试用
 * - Map--H阿水Map
 * - Set--HashSet
 * - List--ArrayList
 * 
 */
class TestClass {
  public Set<?> testSet(String[] s) {
    Set<String> x = new HashSet<>(); // 不使用泛型，会有提示“使用了未检查或不安全的操作“
    x.addAll(Arrays.asList(s));

    // 进行一些操作
    out.print(x.size() + "->");
    x.add("hello,world");
    out.println(x.size());

    out.println("set implements iterable ? " + (x instanceof Iterable));

    return x;
  }

  public List<?> testList(String[] s) {
    List<String> x = new ArrayList<>();
    x.addAll(Arrays.asList(s));
    
    // 进行一些操作
    out.print(x.size() + "->");
    x.add("hello,world");
    out.println(x.size());

    x.set(0, "first");
    out.println(x.toString());

    out.println("list implements iterable ? " + (x instanceof Iterable));

    return x;
  }

  public Map<?, ?> testMap(String[] s) {
    Map<String, Integer> x = new HashMap<>();
    for(int i=0; i<s.length; i++) {
      x.put(s[i], i);
    }
    out.println("map implements iterable ? " + (x instanceof Iterable));
    return x;
  }

  public void testStream() {
    String[] input = {"tiger", "cat", "TIGER", "Tiger", "leopard"};
    List<String> cats = Arrays.asList(input);

    String search = "tiger";
    String tigers = cats.stream()
                  .filter(s -> s.equalsIgnoreCase(search))
                  .collect(Collectors.joining(","));
    out.println(tigers);
  }
}