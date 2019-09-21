/**
 * 演示嵌套类型的用法
 * 
 */
import static java.lang.System.out;
public class InnerType
{
    public static void main(String[] args) {
        Outter ot = new Outter("Hello");
        out.println("Outter name: " + ot.uname());

        Outter.Inner in = ot.new Inner(); // 注意这个 new 的用法
        in.print();

        B b = new B();
        out.println("B name: " + b.iname());

        ot.print();
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

    // 非静态成员接口
    public class Inner {
        private String iname = "";
        public Inner(String n) {
            iname = n;
        }
        public Inner() {
            iname = Outter.this.name; // 用外层类的name复制，在没有重名的情况也可以直接使用name
        }
        public String getName() {
            return iname;
        }
        public void print() {
            out.println("Inner says: " + iname);
        }
    }

    public static String type = "Outter"; //顶层类的私有静态成员

    private String name;
    private Inner mi; // 内部创建一个非静态成员实例
    public Outter(String n) {
        name = n;
        mi = new Inner(name);
    }
    public Outter() {
        name = "Unknown";
        mi = new Inner(name);
    }
    public String uname() {
        return name;
    }
    public void print() {
        out.println("2 exists: " + name + " " + mi.getName());
    }
}
class B implements Outter.If { // 实现If接口

}