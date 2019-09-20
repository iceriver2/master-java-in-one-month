/**
 * 简单演示enum的用法
 */
import static java.lang.System.out;

public class EnumBasic
{
    public static void main(String[] args) {
        showSize();
        showWeek();
    }

    public static void showSize() {
        Size.showAll();
    }

    public static void showWeek() {
        out.println(WeekDay.Mon); // 打印 Mon 
        out.println(WeekDay.Tue.getDay()); // 打印 Tuesday
        out.println(getWeekdaysAsString(WeekDay.values())); // 打印 Mon,Tue,Wed,Thu,Fri,Sat,Sun
    }

    private static String getWeekdaysAsString(WeekDay[] d) {
        String x = "";
        for (WeekDay w : d) {
            x += w.toString() + ",";
        }
        return x.substring(0, x.length()-1);
    }
}

// 最简单的形式
enum Size {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE;

    public static void showAll() { // 声明了static，因为编译器不允许在 main 中调用 Color的非 static 方法。
        out.print(SMALL);
        out.print(MEDIUM);
        out.print(LARGE);
        out.println(EXTRA_LARGE);
    }
}

// 带构造方法的形式
enum WeekDay {
    Mon("Monday"), Tue("Tuesday"), Wed("Wednesday"), Thu("Thursday"), Fri("Friday"), Sat("Saturday"), Sun("Sunday");

    private final String day;
    private WeekDay(String day) {
        this.day = day;
        out.println("I am " + day); // 打印了7次，确实证明了枚举的每个值都在运行时被创建了一个实例
    }
    public String getDay() { 
        return day; 
    }
}

interface Food { // 我以为要报错的，但是，居然没有报错！这是什么操作？？
    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO
    }
    enum Dessert implements Food {
        FRUIT, CAKE, GELATO
    }
}