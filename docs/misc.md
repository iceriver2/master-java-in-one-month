- [类型系统](#类型系统)
  - [接口](#接口)
  - [范型](#范型)
  - [枚举](#枚举)
  - [注解](#注解)
  - [嵌套类型](#嵌套类型)
    - [闭包](#闭包)
    - [嵌套类型的运作方式](#嵌套类型的运作方式)
    - [继承与包含](#继承与包含)
  - [lambda表达式](#lambda表达式)
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

> iceman注：《Java技术手册》对枚举的介绍较少，又参考了一些[网络文章](https://my.oschina.net/u/1421583/blog/1843469)。注意，这篇文章讲的很好，还顺带介绍了 EnumMap 和 EnumSet 。

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

注解是一种特殊的接口，作用是注解Java程序的某个部分。**注解没有直接作用**，只是为注解的方法提供额外的信息，可为编译器、IDE或其他工具提供提示。

基本注解包括：
- **@Override 方法是覆盖的**
- **@Deprecated 方法废弃了**
- @SuppressWarning 静默编译器的警告，需要提供值，如：deprecation、unchecked、fallthrough、path、serial、finally、all（不全）
- @SafeVarargs 为变长参数方法提供增强的警告静默功能
- @FunctionalInterface 接口可用作lambda表达式的目标
- @Retention 元注解，指明javac和java运行时如何处理自定义的注解类型，可用的值在枚举RetentionType中定义：SOURCE（只保留在源码中）、CLASS（保留在类文件中，很少使用）、RUNTIME（运行时可通过反射访问）。
- @Target 元注解，指明自定义的新注解能在源码的什么地方用，可用的值在枚举ElementType中定义，包括：TYPE、FIELD、METHOD、PARAMETER、CONSTRUCTOR、LOCAL_VARIABLE、ANNOTATION_TYPE、PACKAGE、TYPE_PARAMETER、TYPE_USE。


与普通的接口相比，注解有些特殊的特性：都隐式扩展 java.lang.annotation.Annotation 接口；不能范型化；不能扩展其他接口；**只能定义没有参数的方法**；**不能定义会抛出异常的方法**；方法的返回类型有限制；方法可以有一个默认返回值。

一个简单的示例
```java
@Override
public void toString() {
}
```

自定义注解的步骤：先指明注解能出现在哪里，然后是保留原则，最后是注解的名称。

自定义注解的示例
```java
// MyTarget.java 定义一个注解，注意写法
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTarget
{

}

// 其他文件 ，使用注解
public class MyTargetTest
{
    @MyTarget
    public void doSomething()
    {
        System.out.println("hello world");
    }
}
```

注解也是接口，因此，也可以有成员。
```java
// MyAnnotation.java 定义注解
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation
{
    String hello() default "gege"; // 注意default的用法
    String world();
    int[] array() default { 2, 4, 5, 6 };
    EnumTest.TrafficLamp lamp() ;
    TestAnnotation lannotation() default @TestAnnotation(value = "ddd");
    Class style() default String.class;
}

// 其他文件使用，可以传值。这些传入的值，只有使用反射才能读取。
@MyAnnotation(hello = "beijing", world="shanghai",array={},lamp=TrafficLamp.RED,style=int.class)
public class MyTest
{
    @MyAnnotation(lannotation=@TestAnnotation(value="baby"), world = "shanghai",array={1,2,3},lamp=TrafficLamp.YELLOW)
    @Deprecated
    @SuppressWarnings("")
    public void output()
    {
        System.out.println("output something!");
    }
}
```

## 嵌套类型

嵌套类型，又称内部类。嵌套类有两个目的，都与封装有关，可能某个类型与另一个类型联系非常紧密，也可能某个类型只在局部区域使用。

嵌套类型的4种方式：静态成员类型、非静态成员类型、局部类、匿名类。
- 静态成员类型是定义为static的类型，嵌套的接口、枚举和注解使用是静态成员类型。
- 非静态成员类型是未使用static的类型。只有类才能作为非静态成员类型。
- 局部类，在代码块中定义的类。接口、枚举和注解不能定义为局部类型。
- 匿名类，是没有名称的局部类。接口、枚举和注解不能定义为匿名类。

【静态成员类型】

静态成员类型的特性
- 静态成员类型类似于类的其他静态成员：静态字段和静态方法
- **静态成员类型和所在类的任何实例都不关联**（即没有this对象）
- 静态成员类型只能访问所在类的静态成员
- **静态成员类型能访问所在类型中的所有静态成员（包括其他静态成员类型）**
- 嵌套的接口、枚举和注解都隐式声明为静态类型
- 接口或注解中的嵌套类型也都隐式声明为静态类型。
- 静态成员类型可以在顶层类型中定义，也可以嵌入任何深度的其他静态成员类型中
- 静态成员类型不能在其他嵌套类型中定义。

简单的说：静态访问静态，静态嵌套静态。接口、枚举和注解默认是静态。

```java
public class LinkedStack {
    // 静态成员
    static interface Linkable {
        public Linkable getNext();
        public void setNext(Linkable node);
    }

    Linkable head;
}
public LinkableInteger implements LinkedStack.Linkable {
    public LinkedStack.Linkable getNext() { }
    public void setNext(LinkedStack.Linkable node) { }
}
```

> iceman注：有一种想法，顶层类如果视为一个包的话，那么顶层类中的静态成员就相当于该包中的类，自动获得包访问权，但相互之间并不是直接关联。


【非静态成员类型】

非静态成员声明为外层类或枚举类型的成员，不使用static。其特性包括：
- 静态成员类型类似于类的成员，非静态成员类似于实例成员。
- 只有类才能作为非静态成员类型。
- 一个非静态成员类的实例始终关联着外层类型的实例。（this指向外层实例。）
- 非静态成员类的代码能访问外层类型的所有字段和方法（静态和非静态的都可以访问）
- 非静态成员类不能与任何外层类或包同名。
- 非静态成员类不能包含任何静态字段、方法或类型，但可以包含 static final 的常量。

> iceman注：注意到一个很微妙的关系，非静态成员类总是关联到一个外层类型，但外层类型却关联不到非静态成员。
> 反编译class文件，可以看到不管是静态成员类型还是非静态成员类型，都不在顶层类的字节码中。但是，非静态成员类型的字节码中保留了一个 `final this$0` 的成员，应该是指向顶层类的实例的。

非静态成员类型可以通过 classname.this 访问顶层类，其中 classname 是顶层类的名称。仅当引用的外层类成员与非静态成员出现重名时，才使用这种结构。
非静态成员类实例的语法，在外层类的外部创建 outterInstance.new Outter.Inner() ；在外层类的内部创建，直接用 new Outter.Inner() 。

【局部类】

局部类在代码块中声明，不是类的成员。只有类才能就定义，接口、枚举和注解都必须是顶层类型或静态成员类型。
局部类一般在方法中定义，也可以在类的静态初始化程序或实例初始化程序中定义。

```java
public void print() {
    class printMe {
        public printMe(String x) {
            out.print("It uses a local class.");
            out.println("2 exists: " + x);
        }
    }
    new printMe(name + " " + mi.getName()); // 直接建立局部类的一个实例
}
```

局部类的特性
- 局部类和外层实例关联，并且能访问外层类的任何成员，包括私有成员
- 局部类还能访问局部方法的作用域中声明为final的任何局部变量、方法参数和异常参数。
- 局部类的名称在代码块的外部不能使用
- 局部类不能声明为 public，protected，private 或 static
- 局部类不能包含静态字段、方法或类。唯一的例外是使用 static final 声明的常量。
- 接口、枚举和注解不能局部定义
- 局部类能使用同一个作用域中的局部变量、方法参数和异常参数，但这些变量或参数必须声明为final。这是因为局部类实例的生命周期可能比定义它的方法的自行时间长很多。

【匿名类】

匿名类是没有名称的局部类，使用new运算符在一个简洁的表达式中定义和实例化。

匿名类是个表达式。多数情况下，可以将匿名类转为lambda表达式。

```java
public class OuterClass {
    public InnerClass getInnerClass(final int num,String str2){
        return new InnerClass(){
            int number = num + 3;
            public int getNumber(){
                return number;
            }
        };
    }
}
```

定义匿名类和创建这个类的实例使用 new ，后面跟某个类的名称和类主体。如果new后跟一个类的名称，那么这个匿名类是指定类的子类；如果new后跟一个接口名称，那么这个匿名类实现指定的接口，并扩展Object。  
匿名类使用的语法无法指定 extends 子句和 implements 子句，也不能为这个类指定名称。  
没有名称，就不能定义构造方法。但可以使用实例初始化程序代替。

### 闭包

局部类有时也称闭包。闭包是一个对象，它保存作用域的状态，并让这个作用域在后面可以继续使用。
> 闭包的特性，跟JS中闭包相似。

Java通过局部类、匿名类和lambda表达式实现闭包。

### 嵌套类型的运作方式

引入嵌套类型后，JVM和class文件的格式并没有发生变化。对Java解释器而言，没有所谓的嵌套类型，所有类都是普通的顶层类。

javac把每个嵌套类型都编译为单独的类文件，得到的就是顶层类，只是类文件的名称使用特殊的约定。
- （静态或非静态）成员类型，格式为 EnclosingType$Member.class
- 匿名类，使用数字表示，如 EnclosingType$$1.class
- 局部类综合前两种，如 EnclosingType4$1Member.class

普通类不具备嵌套类型的访问权限，因此，编译器会生成合法的访问方法（具有默认的包访问权限），然后把访问私有成员的表达式转换为调用合成方法的表达式。
- 非静态成员类：非静态成员的每个实例都和一个外层的实例关联，编译器为每个成员类定义了一个名为 this$0 的合成字段，保存一个外层实例的引用。这个合成字段的值，在非静态成员类构造时被初始化。
- 局部类和匿名类：局部类访问外层类的字段和方法的原因，与非静态成员相同。不同指出在于，局部类还能访问所在块的局部变量，其实现方法，是javac自动为局部类创建了各个局部变量的副本，所以，如果局部类修改了局部变量，就会出现不一致。

### 继承与包含

两种类的层次结构，一种是超类到子类的继承层次结构，一种是外层到内层的包含层次结构。

这两种层次结构的特性和经验法则
- 两种层次结构完全相互独立
- 避免命名冲突，超类的字段或方法不能和外层类的字段或方法同名
- 如果出现了命名冲突，那么继承的字段或方法取代外层类的同名字段或方法
- 为了避免混淆继承层次结构和包含层次结构，包含层次结构不要太深
- 如果类嵌套超过两层，可能导致更大的混乱
- 如果类的继承层次结构很深，可以考虑不把它定义为非静态成员类，而是定义为顶层类

## lambda表达式

lambda 表达式以字面量的形式把少量代码直接写在程序中，而且让 Java 编程更符合函数式风格。
lambda 表达式的句法是一个参数列表和方法主体，如下所示: `(p, q) -> {}`，这种句法能通过一种十分紧凑的方式表示简单的方法，而且能很大程度上避免使用匿名类。

javac 遇到 lambda 表达式时会把它解释为一个方法的主体，这个方法具有特定的签名。不过，是哪个方法呢?  
为了解决这个问题，javac 会查看周围的代码。对于满足条件的 lambda 表达式，编译器会创建一个类型，实现期望使用的接口，然后把 lambda 表达式的主体当作强制方法的实现。  
lambda 表达式必须满足以下条件才算是合法的 Java 代码:
- lambda 表达式必须出现在期望使用接口类型实例的地方;
- **期望使用的接口类型必须只有一个强制方法;**
- **这个强制方法的签名要完全匹配 lambda 表达式。**

虽然 lambda 表达式和匿名类有很多相似之处，但 lambda 表达式并不只是匿名类的语法糖。其实，lambda 表达式使用方法句柄和一个特殊的新 JVM 字节码 invokedynamic 实现。

Java 实质上是面向对象语言。不过，引入 lambda 表达式后，可以更轻易地编写符合函数式风格的代码。

有几个常用的常用的lambda模式：map、filter、reduce。

值得注意的是，最好把 Java 看成轻度支持函数式编程的语言。Java 不是专门的函数式语言，也不想变成函数式语言。

# 内存管理

# 并发编程