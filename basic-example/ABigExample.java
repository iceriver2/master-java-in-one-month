
/**
 * 一个比较大的示例类
 * 打算把某一（几）个教程的全部示例都列在此处。再度熟悉练练手。
 */

import static java.lang.System.out;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class ABigExample {
  public static void main(String[] args) {
    MyDataType dt = new MyDataType();
    // dt.showMaxMin();
    // dt.showStringBuilder();
    // dt.reduceArray();
    // dt.showDate();
    // dt.compareString();
    // dt.splitString();
    // dt.reverseArray();
    // dt.concatArray();
    // dt.intersetArray();
    dt.unionArray();

    // showRepl();
  }

  public static void showRepl() {
    String c;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 使用 System.in 创建 BufferedReader
    System.out.println("输入字符, 按下 'exit' 键退出。");
    try {
      do {
        c = br.readLine(); // 读取字符
        System.out.println(c);
    } while (!c.equals("exit"));
    } catch (IOException e) {
    }
  }
}

class MyDataType {
  public void showMaxMin() {
    // 其他数字类型类似
    out.println("Byte Range: " + Byte.MAX_VALUE + " " + Byte.MIN_VALUE);

    Integer[] numbers = { 8, 2, 7, 1, 4, 9, 5};
    int min = (int) Collections.min(Arrays.asList(numbers));
    System.out.println("Min of " + Arrays.toString(numbers) + ": " + min);
  }

  public void showStringBuilder() {
    StringBuilder sb = new StringBuilder();
    sb.append("hello");
    sb.delete(1, 3);
    sb.insert(1, "here");
    out.println(sb);
  }

  public void reduceArray() {
    int[] a = new int[10];
    for (int i=0; i<a.length; i++) {
      a[i] = (int)Math.pow((int)i, 2);
    }
    out.println(Arrays.toString(a));
    int sum = Arrays.stream(a).reduce(0, (i, j) -> i+j);
    int sum2 = Arrays.stream(a).reduce(0, Integer::sum);
    out.println("sum is :" + sum);
    out.println("sum2 is :" + sum2);
  }

  public void showDate() {
    Date d = new Date();
    out.println(d);
    out.println(new SimpleDateFormat("yyyy-MM-dd").format(d));

    try {
      Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-30");
      out.println(d1);
    } catch (ParseException e) {
      
    }

    Calendar c = Calendar.getInstance();
    c.set(2019, 1, 1);
    out.println(c.get(Calendar.MONTH));
  }

  public boolean compareString() {
    String s1 = "Hello";
    String s2 = "hello";

    return s1.equals(s2);
  }

  public void splitString() {
    String original = "hello,world,man";

    // 使用默认方法
    String[] t = original.split(",");
    for (String x : t) {
      out.print(x + " ");
    }
    out.println();

    // 使用另一种方法
    StringTokenizer st = new StringTokenizer(original, ",");
    while(st.hasMoreElements()) {
      out.print(st.nextElement()+"|");
    }
    out.println();
  }

  public void reverseArray() {
    // 反转借助其他库（如 Apache Commons Lang）也可以

    // 对字符串数组的反转，借助 Collections.reverse 。
    String[] sa = new String[] { "a", "b", "c" };
    List<String> sl = Arrays.asList(sa.clone());
    Collections.reverse(sl);
    out.println(Arrays.toString(sa) + "-->" + Arrays.toString(sl.toArray()));

    Integer[] ia = new Integer[] { 1,2,3 }; // 必须要 Integer[] 不能 int[]，前者是 Object Array，后者不是，否则后续经 Collections.reverse 后无法转为数组
    List il = Arrays.asList(ia.clone());
    Collections.reverse(il);
    out.println(Arrays.toString(ia) + "-->" + Arrays.toString(il.toArray()));
  }

  public void concatArray() {
    // 可以用最土的方法，逐个复制
    // 也可以使用System.arrayCopy。
    int[] a = {1,2,3};
    int[] b = {4,5,6};
    int[] c = new int[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);
    out.println(Arrays.toString(c));

    // 借助 List 作为中转
    String[] e = { "A", "E", "I" };
    String[] f = { "O", "U" };
    // List<String> list = Arrays.asList(e); // 这种写法尝试在长度固定的数组上加入新数组，编译不错运行出错
    List<String> list = new ArrayList<>(Arrays.asList(e));
    list.addAll(Arrays.asList(f));
    Object[] d = list.toArray();
    System.out.println(Arrays.toString(d));
  }

  // 找出两个数组的交集
  public void intersetArray() {
    // 最简单的方法，是双重循环
    int[] a = {1,2,3};
    int[] b = {3,4,5,6};
    for(int a1: a) {
      for (int b1: b) {
        if (a1 == b1) {
          out.print(a1);
        }
      }
    }
    out.println();

    // 借助 ArrayList.retainAll
    Integer[] e = {1,2,3};
    Integer[] f = {3,4,5,6};
    ArrayList<Integer> c = new ArrayList<>(Arrays.asList(e));
    ArrayList<Integer> d = new ArrayList<>(Arrays.asList(f));
    c.retainAll(d);
    out.println(c);
  }
  
  public void unionArray() {
    String[] arr1 = { "1", "2", "3" };
    String[] arr2 = { "4", "5", "6" };

    HashSet<String> hs = new HashSet<>();
    hs.addAll(Arrays.asList(arr1));
    hs.addAll(Arrays.asList(arr2));

    out.println(hs);
  }
}


