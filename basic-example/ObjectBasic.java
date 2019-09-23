/**
 * 简单演示面向对象的特性，如集成、重载、覆盖
 * 
 * 在本目录编译，并将class文件生成到 classdir/ 目录下并执行的命令为
 * javac -d classdir ObjectBasic.java; java -cp classdir/ ObjectBasic
 */
import static java.lang.System.out;
import java.util.Calendar;

public class ObjectBasic
{
    public static void main(String[] args) {
        showOverride();
        showAbstract();
        showInterface();
    }

    private static void showOverride() {
        B b = new B();
        out.println(b.i); // 2
        out.println(b.f()); // -2
        out.println(B.g()); // B

        A a = (A)b; // 类型矫正
        out.println(a.i); // 1 字段被遮盖，能找回
        out.println(a.f()); // -2 ，方法被覆盖，不能找回
        out.println(A.g()); // A

        C c = new C();
        out.println(c.i); // 3
        out.println(c.f()); // 4 调用了父类的被覆盖方法
        out.println(C.g()); // C
    }

    private static void showAbstract() {
        CClass c = new CClass();
        out.println(c.uname());
    }

    private static void showInterface() {
        MClass m = new MClass();
        m.say();
        out.println(m.id());
    }
}


/* 这三类的成员，均为包可见 */
class A {
    int i = 1;
    int f() {return i;}
    static char g() { return 'A'; }
}
class B extends A {
    int i = 2;
    int f() { return -i; } // 直接覆盖父类的同名方法
    static char g() { return 'B'; }
}
class C extends A {
    int i = 3;
    int f() { return super.f()+i; } // 内部调用父类的同名方法
    static char g() { return 'C'; }
}

/* 抽象类的使用 */
abstract class ABClass { // 抽象类
    public abstract String uname(); // 抽象方法
}
class CClass extends ABClass { // 具体类
    @Override
    public String uname() { // 实现抽象方法。如果没有这个函数，编译报错
        return "World!";
    }
}

/* 接口的使用 */
interface Mask {
    void say();
    default String id() { return Calendar.getInstance().getTime().toString(); }
}
class MClass implements Mask {
    public void say() {
        out.println("I am an instance of MClass.");
    }
}