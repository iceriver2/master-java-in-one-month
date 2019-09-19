/**
 * 语法的使用
 */
import static java.lang.System.out;
public class Syntax {
    public static void main(String[] args) {
        out.println("args begin----->"); // 注意此处的写法，用了System包中导入的out
        for (String n: args) {
            out.println(n);
        }
        out.println("<-----args end");

        out.println();
        out.println(max(1,2,45));

        someMethod();

        handleArray();

        objectEquals();
    }

    public static int max(int first, int... rest) {
        int max = first;
        for (int i : rest) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public static void handleArray() {
        String[] mm = { "hel", "lo", "world" };
        String[] nn = mm.clone();
        System.out.println(nn[0]);
    }

    public static void objectEquals() {
        System.out.println();
        System.out.println("objectEquals() begin----->");

        String letter = "o";
        String s = "hello";
        String t = "hell" + letter;
        if (s == t) {
            System.out.println("ref equals");
        }
        if (s.equals(t)) {
            System.out.println("obj equals");
        }

        System.out.println("<-----objectEquals() end");
    }

    public static void someMethod() {
        System.out.println();
        System.out.println("someMethod() begin----->");

        int[] p = new int[] {1,2,3};
        for (int n: p) {
            System.out.println(n);
        }

        try {
            int i = 250 / 0;
            System.out.println(i);
        } catch (ArithmeticException ae) {
            System.out.println("Catch Exception");
        } finally {
            System.out.println("Divided by zero");
        }

        System.out.println("<-----someMethod() end");
    }
}