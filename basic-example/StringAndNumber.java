import static java.lang.System.out;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAndNumber
{
  public static void main(String[] args) {
    MyString ms = new MyString();
    MyNumber mn = new MyNumber();

    ms.a();
    ms.pattern();

    out.println(mn.random1());
    out.println(mn.random2());
  }
}

class MyString
{
  public void a() {
    String s1 = "AB";
    String s2 = "CD";

    String s3 = s1;
    out.println(s1 == s3);

    s3 = s1 + s2;
    out.println(s1 == s3);
    out.println(s1);
    out.println(s3);
  }

  public void pattern() {
    String ps = "(\\d+)";
    String text = "23 Apollo 13";
    Pattern p = Pattern.compile(ps);
    Matcher m = p.matcher(text);

    while(m.find()) { // 很土的迭代寻找方式，不能一次性找到
      out.println("find a num in `23 Apollo 13` ? " + m.group());
    }
  }
}

class MyNumber
{
  public int random1() {
    // Random r = new Random(1); // 指定种子，每次都能得出同一个值
    Random r = new Random(System.currentTimeMillis()); // 随机种子，每次不同的值
    int ran1 = r.nextInt(100);
    return ran1;
  }

  public int random2() {
    int max=100,min=1;
    int ran2 = (int) (Math.random()*(max-min)+min); // 每次不同的值
    return ran2;
  }
}