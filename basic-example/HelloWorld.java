/**
 * HelloWorld 类名必须与文件名相同
 * 
 * @author Iceman Tong
 * 
 * 检查使用-Xlint参数
 * 检查 javac -Xlint HelloWorld.java
 * 
 * 编译和执行使用javac的参数@filename
 * 编译 javac @options-sourcefile
 * 执行 cd classdir; java HelloWorld
 * 
 * 反编译 javap HelloWorld
 * 
 * 打包 jar --create --file HelloWorld.jar HelloWorld.class
 * 
 * 文档 javadoc -d ./html HelloWorld.java
 */

public class HelloWorld
{
    /* main()函数是每个应用的入口点 */
    public static void main(String args[]) // args 将输入视为字符串数组
    {
        Int divideByZero = 42 / 0;
        System.out.println("Hello World"); // 调用系统包打印
    }
}