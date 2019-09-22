import static java.lang.System.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAndNumber
{
  public static void main(String[] args) {
    MyString ms = new MyString();
    MyNumber mn = new MyNumber();

    ms.a();
    ms.pattern();
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

}