/**
 * 简单演示enum的用法
 */
import static java.lang.System.out;

public class EnumBasic
{
    public static void main(String[] args) {
        showSize();
        showWeek();
        showEnumInEnum();
    }

    public static void showSize() {
        Size.showAll();
    }

    public static void showWeek() {
        out.println(WeekDay.Mon); // 打印 Mon 
        out.println(WeekDay.Tue.getDay()); // 打印 Tuesday
        out.println(getWeekdaysAsString(WeekDay.values())); // 打印 Mon,Tue,Wed,Thu,Fri,Sat,Sun
    }

    public static void showEnumInEnum() {
        out.println(Meal.APPETIZER.toString()); // 得到一个 Food[]，但不是我想要的，我想要直接获得Food.Coffee.LATTE
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

// 接口嵌套枚举
interface Food {
    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO
    }
    enum Dessert implements Food {
        FRUIT, CAKE, GELATO
    }
}

// 枚举嵌套枚举
enum Meal{
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);
  
    private Food[] values;
    private Meal(Class<? extends Food> kind) {
        values = kind.getEnumConstants(); //通过class对象获取枚举实例
    }
    public Food[] getValues() {
        return values;
    }
    public String toString() {
        String x = "";
        for(Food f: values) {
            x += f.toString() + ",";
        }
        return x.substring(0, x.length()-1);
    }
  
    public interface Food {
      enum Appetizer implements Food {
        SALAD, SOUP, SPRING_ROLLS;
      }
      enum MainCourse implements Food {
        LASAGNE, BURRITO, PAD_THAI,
        LENTILS, HUMMOUS, VINDALOO;
      }
      enum Dessert implements Food {
        TIRAMISU, GELATO, BLACK_FOREST_CAKE,
        FRUIT, CREME_CARAMEL;
      }
      enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
        LATTE, CAPPUCCINO, TEA, HERB_TEA;
      }
    }
  } 