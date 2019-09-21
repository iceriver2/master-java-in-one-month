- [类型系统](#类型系统)
  - [接口](#接口)
  - [范型](#范型)
  - [枚举](#枚举)
  - [注解](#注解)
- [内存管理](#内存管理)
- [并发编程](#并发编程)

# 类型系统

## 接口

接口是一种引用类型（跟类一样），接口的作用是描绘API。  
一般来说，接口不为其方法提供实现，所有实现接口的类必须强制实现全部方法。  
接口中标记为default的方法（默认方法，也叫可选方法），允许提供默认实现。否则，接口新增强制方法，会导致应用该接口的类抛出NoClassDefError。

接口的所有强制方法都隐式使用abstract，也隐式使用public（降低访问权限会导致编译出错）。  
接口不能定义任何实例字段，只能定义 static final 修饰的常量。  
接口不能定义构造方法。  
接口可以包含嵌套类型，嵌套类型隐式使用 public 和 static 。

接口可以扩展其他接口，可以继承父接口中的所有方法和常量。  
接口可以扩展多个其他接口。

类使用 implements 指定要扩展的接口。

有时定义全空的接口很有用。类实现这种接口不需要实现任何方法。这种接口，又称标记接口。 java.io.Serializable 就是一种标记接口。

## 范型

Java提供一种语法，指明某种类型是个容器，这个容器中保存着其他引用类型的实例。容器中保存的负载类型在尖括号中指定。这种语法能让编译器捕获大量不安全的代码。
> iceman注：对这句话的理解是：有些容器（如`List`），既可以存储A又可以存储B，但读取时无法判断其准确类型，容易出错。因此，对该容器加入限制，只允许存储A，如 `List<A>`，这样取出时就不会出错了。这是范型最初的用法。

容器类型一般叫做范型。尖括号连同其中的负载类型，称为类型参数。  
类型参数始终代表引用类型，不能使用基本类型。类型参数可以在方法的签名和主体中使用，就像真正的类型一样。  

```java
interface Box<T> {
    void box(T t);
    T unbox();
}
```

创建范型时，赋值语句的右侧会重复类型参数的值。一般情况下，编译器能推导出类型参数的值，因此可以使用菱形语法省略右侧的类型值。
```java
List<A> shaps = new ArrayList<>();
```

如果编译时不知道要使用什么类型，可以使用通配符`<?>`表示未知类型。  
还有一种受限通配符（也称类型参数约束条件），作用是限制类型参数的值能使用哪些类型。例如 `<? extends List>` 表示，虽然不知道是什么类型，但该类型实现了List接口，这种称为“类型协变”。还有一种“类型逆变”，`<? super List>` 。  
Java集合库使用了大量的协变和逆变，确保范型“做正确的事”。

范型添加于Java5。为了确保兼容Java5之前的代码，Java实行类型擦除，即范型的类型参数仅在编译时可见，javac会去掉类型参数，字节码中不会体现。非范型的类型称为原始类型。范型经过类型擦除后，只会剩下原始类型。  
对同一段代码，某个变量的类型，在编译时（javac）和运行时（JVM）来说，是不同的。例如： `List<String> l = new ArrayList<>()` ，对 javac 来说，l 是 List-of-String ，对 JVM 来说， l 是 ArrayList 类型。  
编译时和运行时的类型稍微有些不同，这个不同点是，运行时类型既比编译时类型精确，又没有编译时类型精确。运行时类型没有编译时类型精确，因为负载类型的信息被擦除，只有原始类型；编译时类型没有运行时类型精确，因为不知道具体类型是什么，只知道它像什么。

范型是Java语言规范中最难理解的部分，潜藏着许多极端情况。

## 枚举

枚举是类的变种，终归是类，也可以拥有成员。编译后也是一个class文件。  
枚举的特殊属性：**隐式扩展自 java.lang.Enum 类**；不能范型化；**可以实现接口**；**不能被扩展**；如果枚举中的所有值都有实现主体，那么只能定义为抽象方法；**只能有一个私有（或使用默认访问权限）的构造方法**。
> iceman注：

```java
public enum Color {
    RED, GREEN, BLUE // Color枚举有三个实例，而且对一个确定的外部变量来说，只能是三个实例其中一个
}
```

> iceman注：《Java技术手册》对枚举的介绍较少，又参考了一些[网络文章](https://my.oschina.net/u/1421583/blog/1843469)。以下为自行整理的部分。

枚举的每一个值都是枚举类型的一个实例，而且始终保持每个值只有一个实例。因此，枚举值的比较可以使用`==`。  
枚举值（实例）由JVM自动创建，不能手动实例化。  
枚举的值必须定义在所有枚举类型的方法之前。

枚举可以结合switch，但直接使用枚举值即可。
```java
enum Color {GREEN,RED,BLUE}

// Switch中的Color
switch (color){
    case BLUE: //无需使用Color进行引用
        System.out.println("蓝色");
        break;
    case RED:
        System.out.println("红色");
        break;
    case GREEN:
        System.out.println("绿色");
        break;
}
```

举例一个枚举类型
```java
// WeekDay.java 文件
public enum WeekDay {
    Mon("Monday"), Tue("Tuesday"), Wed("Wednesday"), Thu("Thursday"), Fri("Friday"), Sat("Saturday"), Sun("Sunday"); // 对应构造方法，将字符串传入构造方法

    private final String day;
    private WeekDay(String day) {
        this.day = day;
        out.println("I am " + day); // 打印了7次，确实证明了枚举的每个值都在运行时被创建了一个实例
    }
    public String getDay() { // 实例方法
        return day; 
    }
}

// 其他文件
{
    out.println(WeekDay.Mon); // 打印 Mon 
    out.println(WeekDay.Tue.getDay()); // 打印 Tuesday // 从 getDay() 的调用可以看出，每个枚举值就是一个实例
}
```
该枚举类型的反编译后的内容为
```java
public final class WeekDay extends java.lang.Enum{ 
    public static final WeekDay Mon;
    public static final WeekDay Tue;
    public static final WeekDay Wed;
    public static final WeekDay Thu;
    public static final WeekDay Fri;
    public static final WeekDay Sat;
    public static final WeekDay Sun;
    static {};
    public java.lang.String getDay();
    public static WeekDay[] values(); // JVM自动插入
    public static WeekDay valueOf(java.lang.String); // JVM自动插入
}
```

如果需要扩展enum中的元素，在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组。
```java
public interface Food {
    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO
    }
    enum Dessert implements Food {
        FRUIT, CAKE, GELATO
    }
}
// 编译后会形成3个类文件： Food.class Food$Coffee.class Food$Dessert.class
```
更复杂的用法，是枚举嵌套枚举。
```java
/* 认真学习下这个用法 */
public enum Meal{
  APPETIZER(Food.Appetizer.class),
  MAINCOURSE(Food.MainCourse.class),
  DESSERT(Food.Dessert.class),
  COFFEE(Food.Coffee.class);

  private Food[] values;
  private Meal(Class<? extends Food> kind) {
    values = kind.getEnumConstants(); //通过class对象获取枚举实例
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
```

java.util.EnumSet和java.util.EnumMap是两个枚举集合。  
EnumSet保证集合中的元素不重复;EnumMap中的 key是enum类型，而value则可以是任意类型。
> EnumSet／EnumMap 与 Enum 最大的区别是，Enum是抽象类。

所有的枚举类型都是Enum类的子类。它们继承了这个类的许多方法。  
其中最有用的一个方法是`toString()`，这个方法能够返回枚举常量名。`toString()`是枚举类型唯一可以覆盖的超类方法。  
`toString()`方法的逆方法是静态方法`valueOf(Class, String)`。  
`ordinal()` 方法返回enum声明中枚举常量的位置，位置从0开始计数。  
Enum类实现了Comparable接口 `int compareTo(E other)`，如果枚举常量在other之前，则返回一个负值；如果this==other，则返回0；否则，返回正值。枚举常量的出现次序在enum声明中给出。

`values()` 方法是编译器插入到enum定义中的static方法，所以，将enum实例向上转型为父类Enum时，`values()`就不可访问了。解决办法：在Class中有一个`getEnumConstants()`方法，所以即便Enum接口中没有`values()`方法，仍然可以通过Class对象取得所有的enum实例。
```java
Day[] ds=Day.values(); //正常使用

Enum e = Day.MONDAY; //向上转型Enum
//e.values(); //无法调用,没有此方法

//获取class对象引用
Class<?> clasz = e.getDeclaringClass();
if(clasz.isEnum()) { // 只有enum才能使用
    Day[] dsz = (Day[]) clasz.getEnumConstants();
    System.out.println("dsz:"+Arrays.toString(dsz));
}
```

使用EnumSet代替标志。enum要求其成员都是唯一的，但是enum中不能删除添加元素。
```java
public class Test {
    public static void main(String[] args) {
        EnumSet<WeekDay> week = EnumSet.noneOf(WeekDay.class);
        week.add(WeekDay.MON);
        System.out.println("EnumSet中的元素：" + week);
        week.remove(WeekDay.MON);
        System.out.println("EnumSet中的元素：" + week);
        week.addAll(EnumSet.complementOf(week));
        System.out.println("EnumSet中的元素：" + week);
        week.removeAll(EnumSet.range(WeekDay.FRI, WeekDay.SAT));
        System.out.println("EnumSet中的元素：" + week);
    }
}
```

enum允许程序员为eunm实例编写方法。所以可以为每个enum实例赋予各自不同的行为。
```java
public enum EnumDemo3 {
    FIRST {
        @Override
        public String getInfo() { return "FIRST TIME"; }
    },
    SECOND {
        @Override
        public String getInfo() { return "SECOND TIME"; }
    };
    // 定义抽象方法，在每个实例中分别实现
    public abstract String getInfo();
}
```

Enum是所有 Java 语言枚举类型的公共基本类（注意Enum是抽象类）以下是它的常见方法：
| 返回类型 | 方法名称 | 方法说明 |
|--------|---------|----------|
| `int` | `compareTo(E o)` | 比较此枚举与指定对象的顺序
| `boolean` | `equals(Object other)` | 当指定对象等于此枚举常量时，返回 true。
| `Class<?>` | `getDeclaringClass()` | 返回与此枚举常量的枚举类型相对应的 Class 对象
| `String` | `name()` | 返回此枚举常量的名称，在其枚举声明中对其进行声明
| `int` | `ordinal()` | 返回枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零），不推荐使用
| `String` | `toString()` | 返回枚举常量的名称，它包含在声明中
| `static<T extends Enum<T>> T` | `static valueOf(Class<T> enumType, String name)` | 返回带指定名称的指定枚举类型的枚举常量。

## 注解

# 内存管理

# 并发编程