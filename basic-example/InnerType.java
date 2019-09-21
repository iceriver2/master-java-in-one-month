/**
 * 演示嵌套类型的用法
 * 
 */
import static java.lang.System.out;
public class InnerType
{
    public static void main(String[] args) {
        Outter ot = new Outter("Hello");
        out.println(ot.uname());

        B b = new B();
        out.println(b.iname());
    }
}

class Outter
{
    // 静态成员接口
    static interface If {
        default String iname() { // 为方法提供默认实现
            return type; // 访问顶层类的私有静态成员
        }
    }

    private static String type = "Outter"; //顶层类的私有静态成员

    private String name;
    public Outter(String n) {
        name = n;
    }
    public String uname() {
        return name;
    }
}
class B implements Outter.If { // 实现If接口

}