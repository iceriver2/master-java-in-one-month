/**
 * 基本数据类型的使用
 * 结合 basic.md 学习
 */
public class DataType
{
    public static void main(String[] args)
    {
        boolean b0 = true;
        System.out.println(b0);

        byte b1 = 127, b2 = 1;
        System.out.println((byte)(b1+b2));

        int i0 = 123_1245;
        System.out.println(i0);

        double d0 = 1.23E07;
        System.out.println(d0);

        int i1 = d0; // 此处有警告：incompatible types: possible lossy conversion from double to int
        System.out.println(i1);
    }
}