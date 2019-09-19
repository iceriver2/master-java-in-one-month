/**
 * 简单的练习
 * 
 * 练习的项目包括：
 * - [x]输出文本消息
 * - [x]输出数组的每个元素，每行一个
 * - [x]比较两个数的大小
 * - [x]一个简单的四则运算方法
 * - [x]从console读取输入，并打印
 * - [x]读取一个文件的内容
 * - []创建一个文件，并写入内容
 * - []从URL读入内容，并执行文本搜索
 * - []对一个list执行遍历
 * - []使用hashmap存储一些随机键值对，执行遍历
 * - []对集合元素执行搜索
 */

import static java.lang.System.out;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class Practise
{
    public static void main(String[] args) {
        Code code = new Code();

        // args 接受需要执行的方法名称
        if (args.length == 0) {
            out.println("Nothing");
            return;
        }
        for(String cmd : args) {
            out.println("Command is "+cmd);
            if (cmd.equals("print")) { // 注意！此处不能使用==，因为实质上是两个String对象在比较
                code.printMessage("hello, world");
                code.printArray(new String[] { "hello", "world" });
            }
            if (cmd.equals("compare")) {
                code.compareNumbers(3.2f, 5.3f);
            }
            if (cmd.equals("calc")) {
                code.calc(3, 4, OP.MULTI);
            }
            if (cmd.equals("file")) {
                code.readFileMD();
            }
        }
    }
}


enum OP {
    PLUS, MINUS, MULTI, DEVIDE
}
class Code
{
    public Code() {
    }

    public void printMessage(String x) {
        out.println();
        out.println(x);
    }

    public void printArray(String[] x) {
        out.println();
        if (x.length == 0) {
            out.println("array is empty");
        }
        for(String i : x) {
            out.print(i + ",");
        }
    }

    public void compareNumbers(float a, float b) {
        out.println();
        if (a > b) {
            out.println(Float.valueOf(a).toString() + ">" + Float.valueOf(b).toString());
        }
        if (a < b) {
            out.println(Float.valueOf(a).toString() + "<" + Float.valueOf(b).toString());
        }
    }

    public int calc(int a, int b, OP op) {
        out.println();
        int c = 0;
        switch(op) {
            case PLUS:
                c=a+b;
                break;
            case MINUS:
                c=a-b;
                break;
            case MULTI:
                c=a*b;
                break;
            case DEVIDE:
                c=a/b;
                break;
            default:
                break;
        }
        out.println(Integer.valueOf(a).toString()
        +op
        +Integer.valueOf(b).toString()
        +"="
        +Integer.valueOf(c).toString());
        return c;
    }

    public void readFileMD() {
        String relativePath = "../docs/java file read and write.md"; // 文件名可以含有空格
        out.println(relativePath+" will be read");

        File fp = new File(relativePath);
        String path = fp.getAbsolutePath();
        out.println(path+" will be read");

        String content = "";

        // 没有这两个catch动作，编译会报错。
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[1024];
            int n = -1;
            while((n = in.read(buffer, 0, buffer.length)) != -1) {
                content += new String(buffer, 0, n, "utf8");
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pos = content.indexOf("\n");
        if (pos != -1) {
            out.println(content.substring(0, pos));
        } else {
            out.println("No content found");
        }
    }
}