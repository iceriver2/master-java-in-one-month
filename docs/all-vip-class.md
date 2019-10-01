> 2019-09-25 考虑到效率问题，资料来源是Java文档的中文版，最新的是[Java8](http://www.matools.com/api/java8)（实话说，这一版的翻译真不行）。从之前的资料可知，Java的最新几个长期版本正好是8，11。尤其是8，出现了大幅更新。BTW 最新版已经到13了。分析核心库的主要目的，不是抄书，而是对核心类的使用有个基本了解，知道在什么时候需要使用什么类。其间，可能会可以忽略某些类，例如，大部分接口，安全类等。

> 2019-09-26 抄书一天半下来，发现：抄录全部方法，有一些效果，但效果的是在归类方法时发生的。整体来说，效率还是偏慢。后面打算采取的策略是：先过一遍类，对于重要的类再抄录方法，不重要的类只保留名称。有些包可能整体被忽略，如 java.nio.channels, java.nio.charset 。

> 2019-09-28 整理方法列表还是有点太慢了。需要快速阅读类的主要介绍，以此判定重要类，毕竟，重要的类没有那么多。

> 2019-09-28 使用上面说的方法，快速过一边包，发现：Compact2/Compact3的包，在初期能用到的确实不错，类似 management 之类和高级语言特性，应该是比较高级的场景才能实用到。也就说，掌握 compact1 的主要类，就差不多可以完成日常任务了。

> 2019-09-29 初步整理完成。由于一些内容散落在不同的包中，但使用时却必须全部都用，因此，需要以特性为中心，重新整理一下到 Overview 中。例如：Collection，在 jang.lang 和 java.util 中都存在。

- [Overview](#overview)
  - [重要类](#%e9%87%8d%e8%a6%81%e7%b1%bb)
    - [数据类型与结构](#%e6%95%b0%e6%8d%ae%e7%b1%bb%e5%9e%8b%e4%b8%8e%e7%bb%93%e6%9e%84)
      - [数值](#%e6%95%b0%e5%80%bc)
      - [字符串](#%e5%ad%97%e7%ac%a6%e4%b8%b2)
      - [数据结构](#%e6%95%b0%e6%8d%ae%e7%bb%93%e6%9e%84)
    - [国际化](#%e5%9b%bd%e9%99%85%e5%8c%96)
    - [格式化](#%e6%a0%bc%e5%bc%8f%e5%8c%96)
      - [时间日期](#%e6%97%b6%e9%97%b4%e6%97%a5%e6%9c%9f)
      - [语言资源TODO](#%e8%af%ad%e8%a8%80%e8%b5%84%e6%ba%90todo)
    - [辅助工具](#%e8%be%85%e5%8a%a9%e5%b7%a5%e5%85%b7)
    - [输入输出TODO](#%e8%be%93%e5%85%a5%e8%be%93%e5%87%batodo)
      - [文件操作](#%e6%96%87%e4%bb%b6%e6%93%8d%e4%bd%9c)
      - [内容操作TODO](#%e5%86%85%e5%ae%b9%e6%93%8d%e4%bd%9ctodo)
    - [日志TODO](#%e6%97%a5%e5%bf%97todo)
    - [网络TODO](#%e7%bd%91%e7%bb%9ctodo)
    - [数据库TODO](#%e6%95%b0%e6%8d%ae%e5%ba%93todo)
  - [特殊形式](#%e7%89%b9%e6%ae%8a%e5%bd%a2%e5%bc%8f)
    - [回调（异步）](#%e5%9b%9e%e8%b0%83%e5%bc%82%e6%ad%a5)
    - [注解](#%e6%b3%a8%e8%a7%a3)
    - [泛型](#%e6%b3%9b%e5%9e%8b)
    - [嵌套类](#%e5%b5%8c%e5%a5%97%e7%b1%bb)
    - [流式表达](#%e6%b5%81%e5%bc%8f%e8%a1%a8%e8%be%be)
    - [lambda表达式](#lambda%e8%a1%a8%e8%be%be%e5%bc%8f)
    - [设计模式](#%e8%ae%be%e8%ae%a1%e6%a8%a1%e5%bc%8f)
- [profile](#profile)
- [Compact1](#compact1)
  - [`java.io`](#javaio)
  - [`java.lang`](#javalang)
  - [`java.math`](#javamath)
  - [`java.net`](#javanet)
  - [`java.nio`](#javanio)
  - [`java.nio.file`](#javaniofile)
  - [`java.text`](#javatext)
  - [`java.time`](#javatime)
  - [`java.time.format`](#javatimeformat)
  - [`java.util`](#javautil)
  - [`java.util.logging`](#javautillogging)
  - [`java.util.regex`](#javautilregex)
- [Compact2](#compact2)
  - [java.sql](#javasql)

# Overview

> 包和类都很多，因此，Overview 只梳理关系和要点，不记录详细，使用方法参见 vipclass-example 。

> 很多包和类，涉及到线程安全，使用时需要注意相关说明。这部分在初步学习时被忽略了。

> 理解时，注意“合理性”。如 Duration（时间段），可以以任何单位进行加减，同时，两个Duration的加减也是可以的，这就是“合理性”。

> 有点小尴尬，数组这么重要，居然不是一个Class，但数组实例是Object。一般总是以 `[]` 出现。可能是已经作为语言的一部分了吧。

> 与时间日期相关的类，很少需要新建实例。多数时候，要处理的时间要么已经存在，要么基于已经存在的时间。

## 重要类

### 数据类型与结构

#### 数值

Boolean
- 类变量：FALSE / TRUE / TYPE
- 类方法：
  - 解析与转换：**parseBoolean()** / toString() / **valueOf()**
  - 运算：logicalAnd() / logicalOr() / logicalXor()

Number(抽象类)，子类包括：Byte，Double，Float，Integer，Long，Short。各个数值和对象之间，可以互转。
- 类变量：
  - 共有：BYTES / SIZE, **MAX_VALUE** / **MIN_VALUE**, TYPE
  - 浮点共有：MAX_EXPONENT / MIN_EXPONENT, NEGATIVE_INFINITY / POSITIVE_INFINITY, **NaN**
- 类方法（共有）： **valueOf()**, **parseXXX()**
- 对象方法（共有）：XXXValue()
- Byte / Short / Integer / Long 都有 **decode()** 类方法解析非十进制。
- Double / Float
  - 类方法：isFinite(), isInfinite(), isNaN(), max(), min(), sum()
  - 对象方法： isInfinite(), isNaN()
- Integer / Long / Short
  - 类方法（非Short）：signum(), toBinaryString(), toHexString(), toOctalString(), getXXX()
  - 类方法：max(), min(), sum()

Character
- 类变量：BYTES, SIZE, TYPE, MAX_VALUE / MIN_VALUE
- 类方法：
  - 实例：**valueOf()**
  - 判定：**isXXX()**，如数字、字母、大小写，空白等
  - 转大小写：toLowerCase(), toUpperCase()

#### 字符串

String
- 字符串的主要特性：1）类似字符数组，可与字符数组互转；2）任何对象都有toString()方法；3）正则。
- 类方法：
  - 实例：**valueOf()**, copyValueOf(), format()
  - 组装：join()
- 对象方法
  - 实例： String()
  - 视为数组： toCharArray(), charAt(), subSequence()
  - 视为字符串：indexOf() / lastIndexOf(), substring(), concat(), split(), replace() / replaceFirst() / replaceAll(), contains(), startsWith() / endsWith(), isEmpty(), toLowerCase() / toUpperCase(), matches() / regionMatches()
  - 转基本类型：getBytes(), getChars()。开始以为没用，发现Base64用到getBytes。

StringBuilder
- 对象方法
  - 实例：StringBuilder()
  - 视为数组：charAt(), deleteCharAt(), insert() / delete() / append(), subSequence(), **reverse()**
  - 视为字符串：substring(), indexOf() / lastIndexOf(), replace()
  - 容量：capacity(), ensureCapacity(), length(), setLength()

StringTokenizer
- 对象方法：
  - 实例：StringTokenizer()
  - 遍历：hasMoreElements() / nextElement(), hasMoreTokens() / nextToken(), countTokens()

StringJoiner
- 对象方法
  - 实例：StringJoiner()
  - 操作：add(), length(), toString()

Scanner
- 支持 File, Path, InputStream, Channel 。支持正则。支持只读某种类型。
- 对象方法
  - 实例： Scanner()
  - 设置：useDelimiter() / delimiter(), useLocale() / locale(), useRadix() / radix()。
  - 遍历： hasNext() / next(), hasNextXXX(), nextXXX()
  - 搜索：findInLine(), findWithinHorizon()
  - 关闭： close()

Pattern
- 配合 Matcher 使用
- 类方法
  - 实例：compile()
  - 判定：matches()
- 对象方法
  - 匹配：matcher()
  - 分割：split(), splitAsStream()
  - 属性：pattern(), flags()

Matcher
- 配合 Pattern 使用，没有构造方法
- 对象方法
  - 设置：pattern() / usePattern()
  - 区域：region() / regionStart(), regionEnd()
  - 遍历：find() / hitEnd(), start() / end() / group() / groupCount() 是接口MatchResult的方法
  - 迭代替换： appendReplacement(), appendTail()
  - 直接替换：replaceAll(), replaceFirst()
  - 判定：lookingAt() 部分匹配, matches() 整体匹配
  - 重置： reset()


MessageFormat
- MessageFormat 是 Format 的子类，兄弟类包括 DateFormat, NumberFormat 。区别：无静态方法获取实例；存在模板变量替换。
- 注意其 pattern 格式，如 `"{0,number,#.##}"`表示：第0个元素是数字，格式为"#.##"。pattern中，使用单引号表示字符串，如`"'{0}'"`表示字符串`{0}`；单引号自身使用2个单引号表示，如`"'{''}'"`表示字符串`{'}`。
- 类方法
  - 格式化：format() 供一次性使用，重复用实例。
- 对象方法
  - 实例：MessageFormat()，可以指定 Locale，或通过 getLocale() / setLocale() 指定
  - 模式：可以构造时指定，也可以 applyPattern()
  - 格式化与解析：format() / parse()

#### 数据结构

Enum(抽象类)，所有枚举类型的基类。类方法是 valueOf()，对象方法包括 name(), ordinal() 。
（奇怪的是，并没有说明其子类有那些，EnumMap的超类是 AbstractMap，EnumSet 的超类是 AbstractSet/AbstractCollection 。）

集合分为：Map、Collection(Queue/List/Set)。主要区别在于：重复/唯一，有序/无序，顺序/倒序。

HashMap
- Map的最佳实现。
- 对象方法
  - 实例：HashMap()，需要指定KV类型
  - 键值：entrySet(), keySet(), values()。特别注意： entrySet() 是 Map.Entry 类型，主要方法：getKey(), getValue() 。
  - 操作： put() / putIfAbsent(), get() / getOrDefault(), remove(), replace(), clear() 。增删改都可以有条件。
  - 判定：containsKey(), containsValue(), isEmpty(), size()
  - 迭代：forEach(), replaceAll(), merge()

HashSet
- Set的最佳实现。
- 对象方法
  - 实例：HashSet()，需要指定类型。
  - 操作：add(), remove(), clear()
  - 判定：contains(), isEmpty(), size()
  - 迭代：iterator()

ArrayList
- List的最佳实现。
- 对象方法
  - 实例：ArrayList(), 需要指定类型。
  - 操作：get() / set(), add() / remove(), addAll() / removeAll() / replaceAll() / retainAll(),  removeRange(), clear(), sort() 
  - 判定：contains(), indexOf(), lastIndexOf(), isEmpty(), size()
  - 迭代：forEach(), iterator(), listIterator(), removeIf(),
  - 转换：subList(), toArray()

TreeMap
- 红黑树。自动排序。自然顺序，或指定的Comparator。
- 对象方法
  - 实例：TreeMap()，需要指定KV类型
  - 键值：entrySet(), keySet(), values()
  - 大于或小于某键值的键或值，这是TreeMap特有的：ceilingEntry() / floorEntry(),  ceilingKey() / floorKey(), firstEntry() / lastEntry(), firstKey() / lastKey(), higherEntry(), lowerEntry(), higherKey() / lowerKey(), pollFirstEntry() / pollLastEntry()
  - 操作：get() / put() / remove() / replace(), putAll(), clear()
  - 判定：containsKey(), containsValue(), size()
  - 迭代：forEach(), replaceAll()

Stack
- 栈。ArrayList的兄弟。
- 对象方法
  - 实例：Stack()
  - 操作：peek(), pop(), push(), search()
  - 判定：empty()

Vector
- 矢量数组。ArrayList的兄弟。
- 对象方法
  - 实例：Vector() 。有一个构造函数规定了起始容量和步进容量。
  - 操作：get() / set(), add() / remove(), addElement() / removeElement() / insertElementAt() / removeElementAt() / setElementAt(), addAll() / removeAll() / retainAll(), removeAllElements(), copyInto(), clear(), sort(), setSize()
  - 值：elements() / firstElement() / lastElement()
  - 判定：elementAt(), contains(), indexOf(), lastIndexOf(), containsAll(), isEmpty(), capacity(), size()
  - 迭代：removeIf(), iterator(), listIterator(), forEach()
  - 转换：subList(), toArray()

Collections
- 全部是静态方法。涉及 List/Map/Set，List方法居多。
- **数组与列表，超类于子类，兄弟类型之间的转换和互操作，问题极多。**
- 有专用的线程安全的集合，包括 checkedXXX(), synchronizedXXX()， unmodifiableXXX() 。
- 通用
  - addAll()
  - disjoint()
  - frequency()
  - max() / min()
  - reverseOrder()
- List方法
  - EMPTY_LIST, emptyList()
  - binarySearch()
  - singletonList()
  - copy(), nCopies(), fill(), replaceAll(), reverse(), rotate(), shuffle(), sort(), swap()
  - indexOfSubList(), lastIndexOfSubList()
- Map方法
  - EMPTY_MAP, emptyMap()
  - singletonMap()
- Set方法
  - EMPTY_SET, emptySet()
  - singleton()

Arrays
- 全部是静态方法。
- 判定：deepEquals(), equals(), binarySearch()
- 操作：fill(), copyOf(), copyOfRange()
- 迭代：parallelSetAll(), setAll() 使用lambda表达式。并行效率大过串行
- sort(), parallelSort() 并行效率大过串行
- stream() 转流式操作，参见[流式表达](#%e6%b5%81%e5%bc%8f%e8%a1%a8%e8%be%be)。
- toString()

### 国际化

Locale
- 实例枚举：CHINA / CHINESE / PRC / SIMPLIFIED_CHINESE， ENGLISH / UK / US
- 类方法
  - 默认实例：getDefault() 可以指定 Locale 或默认， setDefault() 设置默认。
  - getAvailableLocales() 所有可用区域。
- 对象方法
  - 实例：Locale()
  - 属性：getCountry() / getDisplayCountry(), getLanguage() / getDisplayLanguage(), getDisplayName(), toLanguageTag()

Currency
- 类方法：
  - 实例：getInstance() 必须指定 Locale 
  - getAvailableCurrencies() 所有可用货币。
- 对象方法
  - 属性：getCurrencyCode(), getDisplayName(), getSymbol()

### 格式化

NumberFormat(抽象类)
- 类方法：
  - 获得系统实例：getInstance() / getNumberInstance() / getPercentInstance() / getCurrencyInstance() / getIntegerInstance() ，可以指定 Locale 或默认。注意：实例中有getCurrencyInstance()
  - getAvailableLocales() 所有可用区域。
- 对象方法：
  - format() / parse() 互转。

DecimalFormatSymbols
- 类方法：
  - 实例：getInstance() 可以指定 Locale 或默认。
  - getAvailableLocales() 所有可用区域。
- 对象方法：
  - 实例：DecimalFormatSymbols() 可以指定 Locale 。
  - format() / parse() 互转。
  - 属性：getCurrency(), getCurrencySymbol(), getDecimalSeparator(), getDigit(), getGroupingSeparator(), getMonetaryDecimalSeparator() 也可以设置。还有其他属性。

DecimalFormat
- 一般不直接构建 DecimalFormat ，借助 NumberFormat.getInstance() 获取实例、转换并定制。
- 对象方法：
  - format() / parse() 互转。
  - 属性：getCurrency(), getDecimalFormatSymbols(), getGroupingSize() ，也可以设置。或通过 applyPattern() 设置，例如常见模式是 "#,##0.###"。

#### 时间日期


ZoneId(时区，抽象)
- 时区ID，是字符串表示，如"Asia/Shanghai"
- 类方法
  - 实例：from()， of(), ofOffset(), systemDefault()
  - getAvailableZoneIds() 所有可用时区
- 对象方法
  - 转换：normalized(), toString()

TimeZone
- 时区。
- 类方法
  - 实例：getDefault() / setDefault(), getTimeZone()
  - getAvailableIDs() 所有可用时区。
- 对象方法
  - 实例：TimeZone()
  - 属性：getDisplayName(), getID() / setID(), getOffset()
  - 转换：toZoneId()

DateFormatSymbols
- 类方法
  - 实例：DateFormatSymbols()
  - getAvailableLocales() 获得所有可用区域。
- 对象方法
  - 实例：DateFormatSymbols()
  - 属性：成对出现，getXXX 和 setXXX，包括：AmPmStrings, Eras, Months/ShortMonths, Weekdays/ShortWeekdays, ZoneStrings
  - 模式：getLocalPatternChars() / setLocalPatternChars()

DateFormat
- 类方法
  - 实例：getInstance(), getDateInstance(), getDateTimeInstance(), getTimeInstance()
  - getAvailableLocales() 所有可用区域。
- 对象方法
  - 格式化与解析：format() / parse() 处理Date。
  - 属性：getCalendar() / setCalendar(), setTimeZone() / getTimeZone(), setNumberFormat() / getNumberFormat()

SimpleDateFormat
- DateFormat的子类。用于格式化日期。
- 对象方法
  - 实例：SimpleDateFormat() 必须指定 pattern，可以指定Locale。
  - 格式化与解析： format(), parse()
  - 符号集：getDateFormatSymbols(), setDateFormatSymbols()
  - 模式：applyLocalizedPattern() / toLocalizedPattern(), applyPattern() / toPattern()

DateTimeFormatter
- 用于格式化时间日期。
- 类方法
  - 实例：ofLocalizedDate(), ofLocalizedTime(), ofLocalizedDateTime(), ofPattern(), ISO_LOCAL_DATE / ISO_LOCAL_TIME / ISO_LOCAL_DATE_TIME / ISO_INSTANT


LocalDate
- 类方法
  - 实例：from(), of(), now(), ofEpochDay(), parse()
- 对象方法
  - 转换：atStartOfDay() / atTime() 转为 LocalDateTime 。
  - 属性：getChronology(), getDayOfMonth() / getDayOfYear(), getDayOfWeek(), getEra(), getYear() / getMonth(), getMonthValue(), lengthOfMonth() / lengthOfYear()
  - 判定：isAfter() / isBefore() / isEqual(), isLeapYear(), toEpochDay()
  - 运算：minus() / plus(), 也可指定时间单位 minusXXX() / plusXXX()，包括：Days,Months, Weeks, Years 。 withXXX()， 包括：DayOfMonth, withDayOfYear, Month, Year 。adjustInto()
  - 格式化：format()

LocalTime
- 类方法
  - 实例：MIDNIGHT / NOON, now(), from(), of(), ofNanoOfDay(), ofSecondOfDay(), parse()
- 对象方法
  - 转换：atDate()
  - 属性：getHour(), getMinute(), getSecond(), getNano()
  - 判定：isAfter(), isBefore(), toSecondOfDay(), toNanoOfDay()
  - 运算：minus() / plus(), 也可指定单位 minusXXX() / plusXXX()，包括：Hours, Minutes, Seconds, Nanos 。withXXX(), 包括：Hour, Minute, Second, Nano 。adjustInto()
  - 格式化：format()

LocalDateTime
- 差不多LocalDate和LocalTime的合体。
- 还有另外两种DateTime： OffsetDateTime、ZonedDateTime 。
- 类方法
  - 实例：now(), of(), from(), ofEpochSecond(), ofInstant(), parse()
- 对象方法
  - 转换：atZone(), atOffset(), toLocalDate(), toLocalTime()
  - 属性：getDayOfMonth()， getDayOfWeek()， getDayOfYear()， getHour()， getMinute()， getMonth()， getMonthValue()， getNano()， getSecond()， getYear()
  - 判定：isAfter()， isBefore(), isEqual()
  - 运算：minus() / plus(), 也可指定单位 minusXXX() / plusXXX()，包括：Days， Hours， Minutes， Months， Nanos, Seconds, Weeks, Years 。 withXXX()，包括：DayOfMonth, DayOfYear, Hour, Minute, Month, Nano, Second, Year 。
  - 格式化：format()


Duration
- 时间段。
- 类方法
  - 实例：between(), of(), from(), ofDays(), ofHours(), ofMillis(), ofMinutes(), ofNanos(), ofSeconds(), parse()
- 对象方法
  - 运算：minus() / plus(), 也可以指定单位 minusXXX() / plusXXX(), 包括：Days(), Hours(), Millis(), Minutes(), Nanos(), Seconds() 。 addTo() / subtractFrom() / multipliedBy() / dividedBy() 。 withNanos() / withSeconds(), abs() / negated() 。
  - 属性：getNano(), getSeconds(), get() 注意与 toXXX 的区别：get是自身值，to是转换值。
  - 判定：toDays(), toHours(), toMillis(), toMinutes(), toNanos() 。 isNegative(), isZero()

Instant
- 时间点。
- 类方法
  - 实例：EPOCH, MAX / MIN 。from(), now(), ofEpochMilli(), ofEpochSecond(), parse()
- 对象方法
  - 转换：atOffset(), atZone()
  - 计算：minus() / plus(), 也可以指定单位 minusXXX() / plusXXX(), 包括：Millis， Nanos，Seconds。 adjustInto() 。 withXXX。
  - 属性：getNano(), get() / getLong(), getEpochSecond() / toEpochMilli()
  - 判定：isAfter(), isBefore()

#### 语言资源TODO

ListResourceBundle
ResourceBundle

Properties
PropertyResourceBundle

### 辅助工具

Math
- Math都是静态方法。还有一个 StrictMath 。
- 三角函数
- 绝对值：abs()， signum()
- 四舍五入：ceil() / floor() / round() / rint()
- 比大小：min() / max()
- 随机：random()
- 立方开方：hypot(), pow(), sqrt()
- 临界：addExact() / decrementExact(), incrementExact() , multiplyExact(), negateExact(), subtractExact(), toIntExact()
- 无限相关的：nextDown() / nextUp()

System
- System都是静态方法。
- 系统流：err / in / out, setErr() / setIn() / setOut()
- 系统操作：gc(), exit(), load() / loadLibrary(), mapLibraryName(), runFinalization()
- 系统属性：setProperty() / getProperty(), setProperties() / getProperties(), clearProperty()
- 其他：arraycopy(), currentTimeMillis(), nanoTime()

Random
- 对象方法
  - 实例：Random()
  - 设置：setSeed()
  - 生成流：doubles(), ints(), longs(), nextBytes()（这个不是流）
  - 生成单个：nextBoolean(), nextDouble(), nextFloat(), nextGaussian(), nextInt(), nextLong()

UUID
- 最有用的就是类方法 randomUUID()
- 类方法
  - 实例：randomUUID(), nameUUIDFromBytes(), fromString()
- 对象方法
  - 实例：UUID()
  - 属性：不是基于时间的UUID，部分方法无效。clockSequence(), node(), timestamp(), variant(), version()

Throwable / Exception
- Throwable是Error和Exception的父类，具体类。
- Exception未定义除构造方法外的其他方法。
- 对象方法
  - 实例：Throwable()，构造方法可以保护 message/cause 等属性
  - 设置：addSuppressed() / getSuppressed()
  - 属性：fillInStackTrace() / printStackTrace(), setStackTrace() / getStackTrace(), initCause() / getCause(), getLocalizedMessage() / getMessage()


Formatter
- Formatter有多个同名类，本类是 java.util.Formatter 。可以用来格式化到 File, OutputStream, PrintStream 。
- 格式有点多，需要研究文档。
- 对象方法
  - 实例：Formatter()，多种。
  - 操作：format(), close(), flush()
  - 属性：ioException(), locale(), out()

Objects
- Objects都是静态方法。
- deepEquals() / equals()
- isNull() / nonNull() / requireNonNull()
- toString()

Base64
- 静态方法：getDecoder() / getEncoder(), getMimeDecoder() / getMimeEncoder(), getMimeEncoder(), getUrlDecoder() / getUrlEncoder(), 返回静态内部类：Base64.Decoder，Base64.Encoder 。
- Base64.Decoder
  - 对象方法：
    - 解码：decode()，可以处理byte[] 或 String
    - 转换：wrap()
- Base64.Encoder
  - 对象方法：
    - 编码：encode(), encodeToString()，只能处理byte[]
    - 转换：wrap()

DoubleSummaryStatistics / IntSummaryStatistics / LongSummaryStatistics
- 统计用，方法很统一
- 对象方法
  - 实例：DoubleSummaryStatistics() / IntSummaryStatistics() / LongSummaryStatistics()
  - 计数：accept(), combine()
  - 结果：getAverage(), getCount(), getMax(), getMin(), getSum()

### 输入输出TODO

#### 文件操作

File
- File是旧的文件操作方式，新的方式使用Files。
- 类方法
  - 常量：pathSeparator / pathSeparatorChar, separator / separatorChar 。注意两者的区别，pathSeparator是多路径分隔符`:`，separator是单路径分隔符`/`。
- 对象方法
  - 实例：File()
  - 转换：toPath(), toURI()
  - 路径：isAbsolute(), getAbsoluteFile() / getAbsolutePath(), getCanonicalFile() / getCanonicalPath(), getParentFile() / getParent(), getName() / getPath()
  - 文件：createTempFile(), createNewFile(), renameTo(), delete(), deleteOnExit()
  - 目录：mkdir() / mkdirs(), listRoots() / list() / listFiles()
  - 属性： canExecute() / setExecutable(), canRead() / setReadable(), setReadOnly(), canWrite() / setWritable(), exists(), isDirectory() / isFile(), isHidden(), lastModified() / setLastModified()
  - 空间：getFreeSpace() / getTotalSpace() / getUsableSpace(), length()

Files
- Files使用静态方法。除了File的功能外，还包括了输入输出流的内容。
- 文件：copy(), createFile() / createLink() / createTempFile() / createSymbolicLink(), delete(), deleteIfExists(), move(), find()
- 内容：lines() / readAllLines() / readAllBytes(), write(), readSymbolicLink()
- 目录：createDirectories() / createDirectory(), createTempDirectory(), list(), walk() / walkFileTree()
- 属性：exists() / notExists(), isRegularFile(), isSameFile(), isSymbolicLink(), isDirectory(), isExecutable(), isWritable(), isReadable(), isHidden(), getLastModifiedTime() / setLastModifiedTime(), getOwner() / setOwner(), getPosixFilePermissions() / setPosixFilePermissions(), probeContentType(), getAttribute() / setAttribute(), getFileAttributeView(), readAttributes()
- 空间：size()
- 流：newBufferedReader() / newBufferedWriter(), newByteChannel(), newDirectoryStream(), newInputStream() / newOutputStream(), getFileStore()

Paths
- Paths使用静态方法。
- get() 转为Path 。

Path
- 只在 java.nio.file 的接口中看到，不确定是不是。
- Path 表示文件的路径。
- 对象方法
  - 判定：startsWith() / endsWith(), 	isAbsolute()
  - 属性：getFileName(), getName() / getNameCount() / subpath() 是路径段, getFileSystem()， getParent() / getRoot(), normalize(), relativize(), resolve() / resolveSibling(), toAbsolutePath() / toRealPath(), toUri() / toFile()

#### 内容操作TODO

BufferedReader, BufferedWriter
BufferedInputStream, BufferedOutputStream
FileInputStream, FileOutputStream
FileReader, FileWriter
PrintStream, PrintWriter
RandomAccessFile

### 日志TODO

Handler
ConsoleHandler
FileHandler
MemoryHandler
SocketHandler
StreamHandler

Level

LogRecord

Formatter
SimpleFormatter
XMLFormatter
Logger
LogManager

### 网络TODO

HttpCookie

URLConnection
HttpURLConnection
InetAddress/Inet4Address/Inet6Address

ServerSocket/Socket

URI
URL

URLDecoder
URLEncoder

### 数据库TODO


## 特殊形式

### 回调（异步）

### 注解

### 泛型

### 嵌套类

（静态成员，非静态成员，局部类，匿名类）

### 流式表达

### lambda表达式

### 设计模式


# profile

Java8提供不同级别的JRE配置，以适应不同环境的需求。默认的profile有四个：compact1/compact2/compact3/FULL JRE（按从小到大的次序）。

从组件的层面看差异：
- Full SE API： Beans, Input Methods, IDL, Preferences, Accessibility, Print Service, RMI-IIOP, CORBA, Java 2D, Sound, Swing, AWT, Drag and Drop, Image I/O, JAX-WS.
- compact3： Security(Adds kerberos, acl, and sasl to compact1 Security), JMX, JNDI, XML JAXP(Adds crypto to compact2 XML JAXP), Management, Instrumentation.
- compact2： JDBC, RMI, XML JAXP,
- compact1： Core(java.lang.*), Security, Serialization, Networking, Ref Objects, Regular Expressions, Date and Time, Input/Output, Collections, Logging, Concurrency, Reflection, JAR, ZIP, Versioning, Internationalization, JNI, Override Mechanism, Extension Mechanism, Scripting.

`javac -profile` 可以选择使用的配置级别。  
`jdeps -profile` 显示类文件所需的包对应的配置级别。

| compact1 | compact2 Additions | compact3 Additions |
|----------|--------------------|--------------------|
| java.io | java.rmi | java.lang.instrument |
| java.lang | java.rmi.activation | java.lang.management |
| java.lang.annotation | java.rmi.dgc | java.security.acl |
| java.lang.invoke | java.rmi.registry | java.util.prefs |
| java.lang.ref | java.rmi.server | javax.annotation.processing |
| java.lang.reflect | java.sql | javax.lang.model |
| java.math | javax.rmi.ssl | javax.lang.model.element |
| java.net | javax.sql | javax.lang.model.type |
| java.nio | javax.transaction | javax.lang.model.util |
| java.nio.channels | javax.transaction.xa | javax.management |
| java.nio.channels.spi | javax.xml | javax.management.loading |
| java.nio.charset | javax.xml.datatype | javax.management.modelmbean |
| java.nio.charset.spi | javax.xml.namespace | javax.management.monitor |
| java.nio.file | javax.xml.parsers | javax.management.openmbean |
| java.nio.file.attribute | javax.xml.stream | javax.management.relation |
| java.nio.file.spi | javax.xml.stream.events | javax.management.remote |
| java.security | javax.xml.stream.util | javax.management.remote.rmi |
| java.security.cert | javax.xml.transform | javax.management.timer |
| java.security.interfaces | javax.xml.transform.dom | javax.naming |
| java.security.spec | javax.xml.transform.sax | javax.naming.directory |
| java.text | javax.xml.transform.stax | javax.naming.event |
| java.text.spi | javax.xml.transform.stream | javax.naming.ldap |
| java.time | javax.xml.validation | javax.naming.spi |
| java.time.chrono | javax.xml.xpath | javax.security.auth.kerberos |
| java.time.format | org.w3c.dom | javax.security.sasl |
| java.time.temporal | org.w3c.dom.bootstrap | javax.sql.rowset |
| java.time.zone | org.w3c.dom.events | javax.sql.rowset.serial |
| java.util | org.w3c.dom.ls | javax.sql.rowset.spi |
| java.util.concurrent | org.xml.sax | javax.tools |
| java.util.concurrent.atomic | org.xml.sax.ext | javax.xml.crypto |
| java.util.concurrent.locks | org.xml.sax.helpers | javax.xml.crypto.dom |
| java.util.function |   | javax.xml.crypto.dsig |
| java.util.jar |   | javax.xml.crypto.dsig.dom |
| java.util.logging |   | javax.xml.crypto.dsig.keyinfo |
| java.util.regex |   | javax.xml.crypto.dsig.spec |
| java.util.spi |   | org.ietf.jgss |
| java.util.stream |   |   |
| java.util.zip |   |   |
| javax.crypto |   |   |
| javax.crypto.interfaces |   |   |
| javax.crypto.spec |   |   |
| javax.net |   |   |
| javax.net.ssl |   |   |
| javax.script |   |   |
| javax.security.auth |   |   |
| javax.security.auth.callback |   |   |
| javax.security.auth.login |   |   |
| javax.security.auth.spi |   |   |
| javax.security.auth.x500 |   |   |
| javax.security.cert |   |   |

# Compact1

## `java.io`

接口：Closeable, DataInput, DataOutput, Externalizable, FileFilter, FilenameFilter, Flushable, ObjectInput, ObjectOutput, ObjectInputValidation, ObjectStreamConstants, Serializable.

BufferedInputStream
- `protected byte[] buf` / `protected int count` / `protected int pos`
- java.io.FilterInputStream: in
- `BufferedInputStream(InputStream in)` / `BufferedInputStream(InputStream in, int size)`
- `int available()`
- `int read()` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- `void close()`
- java.io.FilterInputStream: read()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

BufferedOutputStream
- `protected byte[] buf` / `protected int count`
- java.io.FilterOutputStream: out
- `BufferedOutputStream(OutputStream out)` / `BufferedOutputStream(OutputStream out, int size)`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void flush()`
- java.io.FilterOutputStream: close(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

**BufferedReader**
- java.io.Reader: lock
- `BufferedReader(Reader in)` / `BufferedReader(Reader in, int sz)`
- `boolean ready()`
- `Stream<String> lines()`
- `int read()` / `int read(char[] cbuf, int off, int len)`
- `String readLine()`
- `long skip(long n)`
- `void close()`
- java.io.Reader: read(), read()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

**BufferedWriter**
- java.io.Writer: lock
- `BufferedWriter(Writer out)` / `BufferedWriter(Writer out, int sz)`
- `void newLine()`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String s, int off, int len)`
- `void flush()`
- `void close()`
- java.io.Writer: append(), append(), append(), write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

ByteArrayInputStream(类似BufferedInputStream)
- `protected byte[] buf` / `protected int count` / `protected int pos`
- `ByteArrayInputStream(byte[] buf)` / `ByteArrayInputStream(byte[] buf, int offset, int length)`
- `int available()`
- `int read()` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- `void close()`
- java.io.InputStream: read()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

ByteArrayOutputStream(类似BufferedOutputStream)
- `protected byte[] buf` / `protected int count`
- `ByteArrayOutputStream()` / `ByteArrayOutputStream(int size)`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void writeTo(OutputStream out)`
- `byte[] toByteArray()`
- `int size()`
- `void close()`
- `String toString()` / `String toString(String charsetName)`
- java.io.OutputStream: flush(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), wait(), wait(), wait()

CharArrayReader
- `protected char[] buf` / `protected int count` / `protected int pos`
- java.io.Reader: lock
- `CharArrayReader(char[] buf)` / `CharArrayReader(char[] buf, int offset, int length)`
- `boolean ready()`
- `int read()` / `int read(char[] b, int off, int len)`
- `long skip(long n)`
- `void close()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

CharArrayWriter
- `protected char[] buf` / `protected int count`
- java.io.Writer: lock
- `CharArrayWriter()` / `CharArrayWriter(int initialSize)`
- `CharArrayWriter append(char c)` / `CharArrayWriter append(CharSequence csq)` / `CharArrayWriter append(CharSequence csq, int start, int end)`
- `void write(char[] c, int off, int len)` / `void write(int c)` / `void write(String str, int off, int len)`
- `void writeTo(Writer out)`
- `int size()`
- `char[] toCharArray()`
- `String toString()`
- `void flush()`
- `void close()`
- java.io.Writer: write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), wait(), wait(), wait()

DataInputStream
- java.io.FilterInputStream: in
- `DataInputStream(InputStream in)`
- `static String readUTF(DataInput in)`
- `int read(byte[] b)` / `int read(byte[] b, int off, int len)`
- `boolean readBoolean()` / `byte readByte()` / `char readChar()` / `double readDouble()` / `float readFloat()` / `int readInt()` / `long readLong()` / `short readShort()` / `int readUnsignedByte()` / `int readUnsignedShort()`
- `void readFully(byte[] b)` / `void readFully(byte[] b, int off, int len)`
- `String readUTF()` / `static String readUTF(DataInput in)`
- java.io.FilterInputStream: available(), close(), mark(), markSupported(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

DataOutputStream
- `protected int written`
- java.io.FilterOutputStream: out
- `DataOutputStream(OutputStream out)`
- `int size()`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void writeBoolean(boolean v)` / `void writeByte(int v)` / `void writeBytes(String s)` / `void writeChar(int v)` / `void writeChars(String s)` / `void writeDouble(double v)` / `void writeFloat(float v)` / `void writeInt(int v)` / `void writeLong(long v)` / `void writeShort(int v)` / `void writeUTF(String str)`
- `void flush()`
- java.io.FilterOutputStream: close(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()
- java.io.DataOutput: write()

**File**(参见java.nio.file)
- `static String pathSeparator` / `static char pathSeparatorChar` / `static String separator` / `static char separatorChar`(注意两者的区别，pathSeparator是多路径分隔符`:`，separator是单路径分隔符`/`)
- 构造方法及转换：`File(File parent, String child)` / `File(String pathname)` / `File(String parent, String child)` / `File(URI uri)` / `Path toPath()` / `URI toURI()`
- 名称路径：`File getAbsoluteFile()` / `String getAbsolutePath()` / `File getCanonicalFile()` / `String getCanonicalPath()` / `String getName()` / `String getPath()` / `String getParent()` / `File getParentFile()`
- 创建临时文件：`static File createTempFile(String prefix, String suffix)` / `static File createTempFile(String prefix, String suffix, File directory)`
- 目录：`boolean mkdir()` / `boolean mkdirs()` / `String[] list()` / `String[] list(FilenameFilter filter)` / `File[] listFiles()` / `File[] listFiles(FileFilter filter)` / `File[] listFiles(FilenameFilter filter)` / `static File[] listRoots()`
- 可读可写可执行：`boolean canExecute()` / `boolean setExecutable(boolean executable)` / `boolean setExecutable(boolean executable, boolean ownerOnly)` / `boolean canRead()` / `boolean setReadable(boolean readable)` / `boolean setReadable(boolean readable, boolean ownerOnly)` / `boolean setReadOnly()` / `boolean canWrite()` / `boolean setWritable(boolean writable)` / `boolean setWritable(boolean writable, boolean ownerOnly)`
- 其他属性：`boolean exists()` / `boolean isAbsolute()` / `boolean isDirectory()` / `boolean isFile()` / `boolean isHidden()`
- 比较：`int compareTo(File pathname)` / `boolean equals(Object obj)`
- 增删改名：`boolean createNewFile()` / `boolean renameTo(File dest)` / `boolean delete()` / `void deleteOnExit()`
- 文件长度与空间使用统：`long getFreeSpace()` / `long getTotalSpace()` / `long getUsableSpace()` / `long length()`
- 最后修改时间：`long lastModified()` / `boolean setLastModified(long time)`
- `int hashCode()` / `String toString()`
- java.lang.Object: clone(), finalize(), getClass(), notify(), notifyAll(), wait(), wait(), wait()

FileDescriptor
- `static FileDescriptor err` / `static FileDescriptor in` / `static FileDescriptor out`
- `FileDescriptor()`
- `void sync()` / `boolean valid()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FilePermission
- `FilePermission(String path, String actions)`
- `PermissionCollection newPermissionCollection()`
- `boolean equals(Object obj)`
- `String getActions()` / `boolean implies(Permission p)`
- `int hashCode()`
- java.security.Permission: checkGuard(), getName(), toString()
- java.lang.Object: clone(), finalize(), getClass(), notify(), notifyAll(), wait(), wait(), wait()

FileInputStream(读取原始字节流如图像)
- `FileInputStream(File file)` / `FileInputStream(FileDescriptor fdObj)` / `FileInputStream(String name)`
- `int available()`
- `protected void finalize()` / `void close()`
- `FileChannel getChannel()` / `FileDescriptor getFD()`
- `int read()` / `int read(byte[] b)` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- java.io.InputStream: mark(), markSupported(), reset()
- java.lang.Object: clone(), equals(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FileOutputStream（写入原始字节流如图像）
- `FileOutputStream(File file)` / `FileOutputStream(File file, boolean append)` / `FileOutputStream(FileDescriptor fdObj)` / `FileOutputStream(String name)` / `FileOutputStream(String name, boolean append)`
- `protected void finalize()` / `void close()`
- `FileChannel getChannel()` / `FileDescriptor getFD()`
- `void write(byte[] b)` / `void write(byte[] b, int off, int len)` / `void write(int b)`
- java.io.OutputStream: flush()
- java.lang.Object: clone(), equals(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

**FileReader**（读取字符流）
- java.io.Reader: lock
- `FileReader(File file)` / `FileReader(FileDescriptor fd)` / `FileReader(String fileName)`
- java.io.InputStreamReader: close(), getEncoding(), read(), read(), ready()
- class java.io.Reader: mark(), markSupported(), read(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

**FileWriter**（写入字符流）
- java.io.Writer: lock
- `FileWriter(File file)` / `FileWriter(File file, boolean append)` / `FileWriter(FileDescriptor fd)` / `FileWriter(String fileName)` / `FileWriter(String fileName, boolean append)`
- java.io.OutputStreamWriter: close(), flush(), getEncoding(), write(), write(), write()
- java.io.Writer: append(), append(), append(), write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FilterInputStream
- `protected InputStream in`
- `protected FilterInputStream(InputStream in)`
- `int available()`
- `int read()` / `int read(byte[] b)` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- `void close()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FilterOutputStream
- `protected OutputStream out`
- `FilterOutputStream(OutputStream out)`
- `void write(byte[] b)` / `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void flush()`
- `void close()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FilterReader
- `protected Reader in`
- java.io.Reader: lock
- `protected FilterReader(Reader in)`
- `int read()` / `int read(char[] cbuf, int off, int len)`
- `boolean ready()`
- `void close()`
- `long skip(long n)`
- java.io.Reader: read(), read()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

FilterWriter
- `protected Writer out`
- java.io.Writer: lock
- `protected FilterWriter(Writer out)`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String str, int off, int len)`
- `void flush()`
- `void close()`
- java.io.Writer: append(), append(), append(), write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

InputStream
- `InputStream()`
- `int available()`
- `abstract int read()` / `int read(byte[] b)` / `int read(byte[] b, int off, int len)`
- `void close()`
- `long skip(long n)`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

OutputStream
- `OutputStream()`
- `void flush()`
- `void write(byte[] b)` / `void write(byte[] b, int off, int len)` / `abstract void write(int b)`
- `void close()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

InputStreamReader(字节流到字符流)
- java.io.Reader: lock
- `InputStreamReader(InputStream in)` / `InputStreamReader(InputStream in, Charset cs)` / `InputStreamReader(InputStream in, CharsetDecoder dec)` / `InputStreamReader(InputStream in, String charsetName)`
- `void close()`
- `String getEncoding()`
- `int read()` / `int read(char[] cbuf, int offset, int length)`
- `boolean ready()`
- java.io.Reader: mark(), markSupported(), read(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

OutputStreamWriter(字符流到字节流)
- java.io.Writer: lock
- `OutputStreamWriter(OutputStream out)` / `OutputStreamWriter(OutputStream out, Charset cs)` / `OutputStreamWriter(OutputStream out, CharsetEncoder enc)` / `OutputStreamWriter(OutputStream out, String charsetName)`
- `void flush()`
- `String getEncoding()`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String str, int off, int len)`
- `void close()`
- java.io.Writer: append(), append(), append(), write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

对象反序列化与反序列化：ObjectInputStream, ObjectInputStream.GetField, ObjectOutputStream, ObjectOutputStream.PutField, ObjectStreamClass, ObjectStreamField, SerializablePermission 。

PipedInputStream
- `protected byte[] buffer` / `protected int in` / `protected int out` / `protected static int PIPE_SIZE`
- `PipedInputStream()` / `PipedInputStream(int pipeSize)` / `PipedInputStream(PipedOutputStream src)` / `PipedInputStream(PipedOutputStream src, int pipeSize)`
- `int available()`
- `void connect(PipedOutputStream src)`
- `int read()` / `int read(byte[] b, int off, int len)`
- `protected void receive(int b)`
- `void close()`
- java.io.InputStream: mark(), markSupported(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

PipedOutputStream
- `PipedOutputStream()` / `PipedOutputStream(PipedInputStream snk)`
- `void close()`
- `void connect(PipedInputStream snk)`
- `void flush()`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- java.io.OutputStream: write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

PipedReader
- class java.io.Reader: lock
- `PipedReader()` / `PipedReader(int pipeSize)` / `PipedReader(PipedWriter src)` / `PipedReader(PipedWriter src, int pipeSize)`
- `void close()`
- `void connect(PipedWriter src)`
- `int read()` / `int read(char[] cbuf, int off, int len)`
- `boolean ready()`
- java.io.Reader: mark(), markSupported(), read(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

PipedWriter
- class java.io.Writer: lock
- `PipedWriter()` / `PipedWriter(PipedReader snk)`
- `void close()`
- `void connect(PipedReader snk)`
- `void flush()`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)`
- java.io.Writer: append(), append(), append(), write(), write(), write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

PrintStream
- java.io.FilterOutputStream: out
- `PrintStream(File file)` / `PrintStream(File file, String csn)` / `PrintStream(OutputStream out)` / `PrintStream(OutputStream out, boolean autoFlush)` / `PrintStream(OutputStream out, boolean autoFlush, String encoding)` / `PrintStream(String fileName)` / `PrintStream(String fileName, String csn)`
- `PrintStream append(char c)` / `PrintStream append(CharSequence csq)` / `PrintStream append(CharSequence csq, int start, int end)`
- `boolean checkError()` / `protected void clearError()`
- `void close()`
- `void flush()`
- `PrintStream format(Locale l, String format, Object... args)` / `PrintStream format(String format, Object... args)`
- `void print(boolean b)` / `void print(char c)` / `void print(char[] s)` / `void print(double d)` / `void print(float f)` / `void print(int i)` / `void print(long l)` / `void print(Object obj)` / `void print(String s)`
- `PrintStream printf(Locale l, String format, Object... args)` / `PrintStream printf(String format, Object... args)`
- `void println()` / `void println(boolean x)` / `void println(char x)` / `void println(char[] x)` / `void println(double x)` / `void println(float x)` / `void println(int x)` / `void println(long x)` / `void println(Object x)` / `void println(String x)`
- `protected void setError()`
- `void write(byte[] buf, int off, int len)` / `void write(int b)`
- java.io.FilterOutputStream: write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

PrintWriter
- `protected Writer out`
- java.io.Writer: lock
- `PrintWriter(File file)` / `PrintWriter(File file, String csn)` / `PrintWriter(OutputStream out)` / `PrintWriter(OutputStream out, boolean autoFlush)` / `PrintWriter(String fileName)` / `PrintWriter(String fileName, String csn)` / `PrintWriter(Writer out)` / `PrintWriter(Writer out, boolean autoFlush)`
- `PrintWriter append(char c)` / `PrintWriter append(CharSequence csq)` / `PrintWriter append(CharSequence csq, int start, int end)`
- `boolean checkError()` / `protected void clearError()` / `protected void setError()`
- `void close()`
- `void flush()`
- `PrintWriter format(Locale l, String format, Object... args)` / `PrintWriter format(String format, Object... args)`
- `void print(boolean b)` / `void print(char c)` / `void print(char[] s)` / `void print(double d)` / `void print(float f)` / `void print(int i)` / `void print(long l)` / `void print(Object obj)` / `void print(String s)`
- `PrintWriter printf(Locale l, String format, Object... args)` / `PrintWriter printf(String format, Object... args)`
- `void println()` / `void println(boolean x)` / `void println(char x)` / `void println(char[] x)` / `void println(double x)` / `void println(float x)` / `void println(int x)` / `void println(long x)` / `void println(Object x)` / `void println(String x)`
- `void write(char[] buf)` / `void write(char[] buf, int off, int len)` / `void write(int c)` / `void write(String s)` / `void write(String s, int off, int len)`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

推回流：PushbackInputStream, PushbackReader

**RandomAccessFile**(随机读写)
- `RandomAccessFile(File file, String mode)` / `RandomAccessFile(String name, String mode)`
- `void close()`
- `FileChannel getChannel()` / `FileDescriptor getFD()`
- `long getFilePointer()` / `long length()` / `void seek(long pos)` / `void setLength(long newLength)` / `int skipBytes(int n)`
- `int read()` / `int read(byte[] b)` / `int read(byte[] b, int off, int len)` / `boolean readBoolean()` / `byte readByte()` / `char readChar()` / `double readDouble()` / `float readFloat()` / `int readInt()` / `long readLong()` / `short readShort()` / `int readUnsignedByte()` / `int readUnsignedShort()`
- `String readLine()`
- `String readUTF()` / `void writeUTF(String str)`
- `void readFully(byte[] b)` / `void readFully(byte[] b, int off, int len)`
- `void write(byte[] b)` / `void write(byte[] b, int off, int len)` / `void write(int b)` / `void writeBoolean(boolean v)` / `void writeByte(int v)` / `void writeBytes(String s)` / `void writeChar(int v)` / `void writeChars(String s)` / `void writeDouble(double v)` / `void writeFloat(float v)` / `void writeInt(int v)` / `void writeLong(long v)` / `void writeShort(int v)`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

SequenceInputStream(有序多个输入流)
- `SequenceInputStream(Enumeration<? extends InputStream> e)` / `SequenceInputStream(InputStream s1, InputStream s2)`
- `int available()`
- `void close()`
- `int read()` / `int read(byte[] b, int off, int len)`
- java.io.InputStream: mark(), markSupported(), read(), reset(), skip()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

StreamTokenizer
- `double nval` / `String sval` / `static int TT_EOF` / `static int TT_EOL` / `static int TT_NUMBER` / `static int TT_WORD` / `int ttype`
- `StreamTokenizer(Reader r)`
- `void commentChar(int ch)` / `void slashSlashComments(boolean flag)` / `void slashStarComments(boolean flag)`
- `int lineno()`
- `void lowerCaseMode(boolean fl)` / `void eolIsSignificant(boolean flag)`
- `int nextToken()`
- `void ordinaryChar(int ch)` / `void ordinaryChars(int low, int hi)`
- `void parseNumbers()` / `void quoteChar(int ch)`
- `void pushBack()`
- `void resetSyntax()`
- `String toString()`
- `void whitespaceChars(int low, int hi)` / `void wordChars(int low, int hi)`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), wait(), wait(), wait()

StringReader(读取字符串)
- java.io.Reader: lock
- `StringReader(String s)`
- `void close()`
- `int read()` / `int read(char[] cbuf, int off, int len)`
- `boolean ready()`
- `long skip(long ns)`
- java.io.Reader: read(), read()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

StringWriter(写入字符串)
- java.io.Writer: lock
- `StringWriter()` / `StringWriter(int initialSize)`
- `StringWriter append(char c)` / `StringWriter append(CharSequence csq)` / `StringWriter append(CharSequence csq, int start, int end)`
- `void close()`
- `void flush()`
- `StringBuffer getBuffer()`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String str)` / `void write(String str, int off, int len)`
- `String toString()`
- java.io.Writer: write()
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), wait(), wait(), wait()

Reader
- `protected Object lock`
- `protected Reader()` / `protected Reader(Object lock)`
- `abstract void close()`
- `int read()` / `int read(char[] cbuf)` / `abstract int read(char[] cbuf, int off, int len)` / `int read(CharBuffer target)`
- `boolean ready()`
- `long skip(long n)`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

Writer
- `protected Object lock`
- `protected Writer()` / `protected Writer(Object lock)`
- `Writer append(char c)` / `Writer append(CharSequence csq)` / `Writer append(CharSequence csq, int start, int end)`
- `void write(char[] cbuf)` / `abstract void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String str)` / `void write(String str, int off, int len)`
- `abstract void close()`
- `abstract void flush()`
- java.lang.Object: clone(), equals(), finalize(), getClass(), hashCode(), notify(), notifyAll(), toString(), wait(), wait(), wait()

## `java.lang`

接口：Appendable, AutoCloseable, CharSequence, Cloneable, `Comparable<T>`, `Iterable<T>`, Readable, Runnable, Thread.UncaughtExceptionHandler.  
枚举：Character.UnicodeScript, ProcessBuilder.Redirect.Type, Thread.State.

**Character**
- `static class Character.Subset` / `static class Character.UnicodeBlock` / `static class Character.UnicodeScript`
- `static int BYTES` / Unicode Specification Categories / `static int MAX_CODE_POINT` / `static int MIN_CODE_POINT` / `static int MAX_RADIX` / `static int MIN_RADIX` / `static char MAX_VALUE` / `static char MIN_VALUE` / `static int SIZE` / `static Class<Character> TYPE`
- `Character(char value)`
- `static int charCount(int codePoint)` / `char charValue()`
- 码点：`static int codePointAt(char[] a, int index)` / `static int codePointAt(char[] a, int index, int limit)` / `static int codePointAt(CharSequence seq, int index)` / `static int codePointBefore(char[] a, int index)` / `static int codePointBefore(char[] a, int index, int start)` / `static int codePointBefore(CharSequence seq, int index)` / `static int codePointCount(char[] a, int offset, int count)` / `static int codePointCount(CharSequence seq, int beginIndex, int endIndex)` / `static int offsetByCodePoints(char[] a, int start, int count, int index, int codePointOffset)` / `static int offsetByCodePoints(CharSequence seq, int index, int codePointOffset)`
- `static int compare(char x, char y)` / `int compareTo(Character anotherCharacter)` / `boolean equals(Object obj)`
- `static int digit(char ch, int radix)` / `static int digit(int codePoint, int radix)` / `static char forDigit(int digit, int radix)`
- `static String getName(int codePoint)`
- `static int getNumericValue(char ch)` / `static int getNumericValue(int codePoint)`
- `static int getType(char ch)` / `static int getType(int codePoint)`
- `int hashCode()` / `static int hashCode(char value)`
- 判定：`static boolean isAlphabetic(int codePoint)` / `static boolean isBmpCodePoint(int codePoint)` / `static boolean isDefined(char ch)` / `static boolean isDefined(int codePoint)` / `static boolean isDigit(char ch)` / `static boolean isDigit(int codePoint)` / `static boolean isHighSurrogate(char ch)` / `static boolean isIdentifierIgnorable(char ch)` / `static boolean isIdentifierIgnorable(int codePoint)` / `static boolean isIdeographic(int codePoint)` / `static boolean isISOControl(char ch)` / `static boolean isISOControl(int codePoint)` / `static boolean isJavaIdentifierPart(char ch)` / `static boolean isJavaIdentifierPart(int codePoint)` / `static boolean isJavaIdentifierStart(char ch)` / `static boolean isJavaIdentifierStart(int codePoint)` / `static boolean isLetter(char ch)` / `static boolean isLetter(int codePoint)` / `static boolean isLetterOrDigit(char ch)` / `static boolean isLetterOrDigit(int codePoint)` / `static boolean isLowerCase(char ch)` / `static boolean isLowerCase(int codePoint)` / `static boolean isLowSurrogate(char ch)` / `static boolean isMirrored(char ch)` / `static boolean isMirrored(int codePoint)` / `static boolean isSpaceChar(char ch)` / `static boolean isSpaceChar(int codePoint)` / `static boolean isSupplementaryCodePoint(int codePoint)` / `static boolean isSurrogate(char ch)` / `static boolean isSurrogatePair(char high, char low)` / `static boolean isTitleCase(char ch)` / `static boolean isTitleCase(int codePoint)` / `static boolean isUnicodeIdentifierPart(char ch)` / `static boolean isUnicodeIdentifierPart(int codePoint)` / `static boolean isUnicodeIdentifierStart(char ch)` / `static boolean isUnicodeIdentifierStart(int codePoint)` / `static boolean isUpperCase(char ch)` / `static boolean isUpperCase(int codePoint)` / `static boolean isValidCodePoint(int codePoint)` / `static boolean isWhitespace(char ch)` / `static boolean isWhitespace(int codePoint)`
- `static int toCodePoint(char high, char low)`
- 大小写：`static char toLowerCase(char ch)` / `static int toLowerCase(int codePoint)` / `static char toUpperCase(char ch)` / `static int toUpperCase(int codePoint)`
- `String toString()` / `static String toString(char c)`
- `static Character valueOf(char c)`

Boolean
- `static Boolean FALSE` / `static Boolean TRUE` / `static Class<Boolean> TYPE`
- `Boolean(boolean value)` / `Boolean(String s)`
- `boolean booleanValue()`
- `static int compare(boolean x, boolean y)` / `int compareTo(Boolean b)`
- `boolean equals(Object obj)`
- `static boolean getBoolean(String name)` / `static boolean parseBoolean(String s)`
- `static boolean logicalAnd(boolean a, boolean b)` / `static boolean logicalOr(boolean a, boolean b)` / `static boolean logicalXor(boolean a, boolean b)`
- `int hashCode()` / `static int hashCode(boolean value)`
- `String toString()` / `static String toString(boolean b)`
- `static Boolean valueOf(boolean b)` / `static Boolean valueOf(String s)`

Byte
- `static int BYTES` / `static byte MAX_VALUE` / `static byte MIN_VALUE` / `static int SIZE` / `static Class<Byte> TYPE`
- `Byte(byte value)` / `Byte(String s)`
- `byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- `static int compare(byte x, byte y)` / `int compareTo(Byte anotherByte)`
- `static Byte decode(String nm)`
- `boolean equals(Object obj)`
- `static byte parseByte(String s)` / `static byte parseByte(String s, int radix)`
- `int hashCode()` / `static int hashCode(byte value)`
- `String toString()` / `static String toString(byte b)`
- `static int toUnsignedInt(byte x)` / `static long toUnsignedLong(byte x)`
- `static Byte valueOf(byte b)` / `static Byte valueOf(String s)` / `static Byte valueOf(String s, int radix)`

Double
- `static int BYTES` / `static int MAX_EXPONENT` / `static int MIN_EXPONENT` / `static double MAX_VALUE` / `static double MIN_VALUE` / `static double MIN_NORMAL` / `static double NaN` / `static double NEGATIVE_INFINITY` / `static double POSITIVE_INFINITY` / `static int SIZE` / `static Class<Double> TYPE`
- `Double(double value)` / `Double(String s)`
- 数值互转：`byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- 比较：`static int compare(double d1, double d2)` / `int compareTo(Double anotherDouble)` / `boolean equals(Object obj)`
- `int hashCode()` / `static int hashCode(double value)`
- 测试无限：`static boolean isFinite(double d)` / `boolean isInfinite()` / `static boolean isInfinite(double v)`
- 测试非数字：`boolean isNaN()` / `static boolean isNaN(double v)`
- 最大最小：`static double max(double a, double b)` / `static double min(double a, double b)`
- 加和：`static double sum(double a, double b)`
- 与字符串互转：`static String toHexString(double d)` / `String toString()` / `static String toString(double d)` / `static double parseDouble(String s)`
- 转为对象：`static Double valueOf(double d)` / `static Double valueOf(String s)`

Float
- `static int BYTES` / `static int MAX_EXPONENT` / `static int MIN_EXPONENT` / `static double MAX_VALUE` / `static double MIN_VALUE` / `static double MIN_NORMAL` / `static double NaN` / `static double NEGATIVE_INFINITY` / `static double POSITIVE_INFINITY` / `static int SIZE` / `static Class<Double> TYPE`
- `Float(double value)` / `Float(float value)` / `Float(String s)`
- 数值互转：`byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- 比较：`static int compare(float f1, float f2)` / `int compareTo(Float anotherFloat)` / `boolean equals(Object obj)`
- `int hashCode()` / `static int hashCode(float value)`
- 测试无限：`static boolean isFinite(float f)` / `boolean isInfinite()` / `static boolean isInfinite(float v)`
- 测试非数字：`boolean isNaN()` / `static boolean isNaN(float v)`
- 最大最小：`static float max(float a, float b)` / `static float min(float a, float b)`
- 加和：`static float sum(float a, float b)`
- 与字符串互转：`static String toHexString(float f)` /`String toString()` / `static String toString(float f)` / `static float parseFloat(String s)`
- 转为对象：`static Float valueOf(float f)` / `static Float valueOf(String s)`

Integer
- `static int BYTES` / `static int MAX_VALUE` / `static int MIN_VALUE` / `static int SIZE` / `static Class<Integer> TYPE`
- `Integer(int value)` / `Integer(String s)`
- 数值互转：`byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- 比较：`static int compare(int x, int y)` / `int compareTo(Integer anotherInteger)` / `static int compareUnsigned(int x, int y)` / `boolean equals(Object obj)`
- 转为对象：`static Integer decode(String nm)` 
- 解析属性值：`static Integer getInteger(String nm)` / `static Integer getInteger(String nm, int val)` / `static Integer getInteger(String nm, Integer val)`
- 计算：`static int divideUnsigned(int dividend, int divisor)` / `static int remainderUnsigned(int dividend, int divisor)` / `static int signum(int i)` / `static int sum(int a, int b)`
- `int hashCode()` / `static int hashCode(int value)`
- 最大最小：`static int max(int a, int b)` / `static int min(int a, int b)`
- 转为数值：`static int parseInt(String s)` / `static int parseInt(String s, int radix)` / `static int parseUnsignedInt(String s)` / `static int parseUnsignedInt(String s, int radix)`
- 与字符串互转：`static String toBinaryString(int i)` / `static String toHexString(int i)` / `static String toOctalString(int i)` / `String toString()` / `static String toString(int i)` / `static String toString(int i, int radix)` / `static long toUnsignedLong(int x)` / `static String toUnsignedString(int i)` / `static String toUnsignedString(int i, int radix)`
- 转为对象：`static Integer valueOf(int i)` / `static Integer valueOf(String s)` / `static Integer valueOf(String s, int radix)`

Long
- `static int BYTES` / `static int MAX_VALUE` / `static int MIN_VALUE` / `static int SIZE` / `static Class<Integer> TYPE`
- `Long(long value)` / `Long(String s)`
- 数值互转：`byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- 比较：`static int compare(long x, long y)` / `int compareTo(Long anotherLong)` / `static int compareUnsigned(long x, long y)` / `boolean equals(Object obj)`
- 转为对象：`static Long decode(String nm)` / `static Long getLong(String nm)` / `static Long getLong(String nm, long val)` / `static Long getLong(String nm, Long val)`
- 计算：`static long divideUnsigned(long dividend, long divisor)` / `static long remainderUnsigned(long dividend, long divisor)` / `static int signum(long i)` / `static long sum(long a, long b)`
- `int hashCode()` / `static int hashCode(long value)`
- 最大最小：`static long max(long a, long b)` / `static long min(long a, long b)` / `static long parseLong(String s)` / `static long parseLong(String s, int radix)` / `static long parseUnsignedLong(String s)` / `static long parseUnsignedLong(String s, int radix)`
- 与字符串互转：`static String toBinaryString(long i)` / `static String toHexString(long i)` / `static String toOctalString(long i)` / `String toString()` / `static String toString(long i)` / `static String toString(long i, int radix)` / `static String toUnsignedString(long i)` / `static String toUnsignedString(long i, int radix)`
- 转为对象：`static Long valueOf(long l)` / `static Long valueOf(String s)` / `static Long valueOf(String s, int radix)`

Short
- `static int BYTES` / `static int MAX_VALUE` / `static int MIN_VALUE` / `static int SIZE` / `static Class<Integer> TYPE`
- `Short(short value)` / `Short(String s)`
- 数值互转：`byte byteValue()` / `double doubleValue()` / `float floatValue()` / `int intValue()` / `long longValue()` / `short shortValue()`
- 比较：`static int compare(short x, short y)` / `int compareTo(Short anotherShort)` / `boolean equals(Object obj)`
- 转为对象：`static Short decode(String nm)`
- `int hashCode()` / `static int hashCode(short value)`
- 转为数值：`static short parseShort(String s)` / `static short parseShort(String s, int radix)`
- 与字符串互转：`String toString()` / `static String toString(short s)` / `static int toUnsignedInt(short x)` / `static long toUnsignedLong(short x)`
- 转为对象：`static Short valueOf(short s)` / `static Short valueOf(String s)` / `static Short valueOf(String s, int radix)`

Number(抽象类，子类 AtomicInteger ， AtomicLong ， BigDecimal ， BigInteger ， Byte ， Double ， DoubleAccumulator ， DoubleAdder ， Float ， Integer ， Long ， LongAccumulator ， LongAdder ， Short)
- `Number()`
- `byte byteValue()` / `abstract double doubleValue()` / `abstract float floatValue()` / `abstract int intValue()` / `abstract long longValue()` / `short shortValue()`

**String**
- `static Comparator<String> CASE_INSENSITIVE_ORDER`
- `String()` / `String(byte[] bytes)` / `String(byte[] bytes, Charset charset)` / `String(byte[] bytes, int offset, int length)` / `String(byte[] bytes, int offset, int length, Charset charset)` / `String(byte[] bytes, int offset, int length, String charsetName)` / `String(byte[] bytes, String charsetName)` / `String(char[] value)` / `String(char[] value, int offset, int count)` / `String(int[] codePoints, int offset, int count)` / `String(String original)` / `String(StringBuffer buffer)` / `String(StringBuilder builder)`
- 元素：`char charAt(int index)` / `int indexOf(int ch)` / `int indexOf(int ch, int fromIndex)` / `int indexOf(String str)` / `int indexOf(String str, int fromIndex)` / `int lastIndexOf(int ch)`/ `int lastIndexOf(int ch, int fromIndex)`/ `int lastIndexOf(String str)`/ `int lastIndexOf(String str, int fromIndex)` / `CharSequence subSequence(int beginIndex, int endIndex)` / `String substring(int beginIndex)` / `String substring(int beginIndex, int endIndex)`
- 码点：`int codePointAt(int index)` / `int codePointBefore(int index)` / `int codePointCount(int beginIndex, int endIndex)` / `int offsetByCodePoints(int index, int codePointOffset)`
- 比较：`int compareTo(String anotherString)` / `int compareToIgnoreCase(String str)` / `boolean contentEquals(CharSequence cs)` / `boolean contentEquals(StringBuffer sb)` / `boolean equals(Object anObject)` / `boolean equalsIgnoreCase(String anotherString)`
- 操作：`static String copyValueOf(char[] data)` / `static String copyValueOf(char[] data, int offset, int count)` / `static String format(Locale l, String format, Object... args)` / `static String format(String format, Object... args)` / `String trim()`
- 分与合：`String concat(String str)` / `static String join(CharSequence delimiter, CharSequence... elements)` / `static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements)` / `String[] split(String regex)` / `String[] split(String regex, int limit)`
- 搜索与替换：`boolean matches(String regex)` / `boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)` / `boolean regionMatches(int toffset, String other, int ooffset, int len)` / `String replace(char oldChar, char newChar)` / `String replace(CharSequence target, CharSequence replacement)` / `String replaceAll(String regex, String replacement)` / `String replaceFirst(String regex, String replacement)`
- 测试：`boolean contains(CharSequence s)` / `boolean endsWith(String suffix)` / `boolean startsWith(String prefix)` / `boolean startsWith(String prefix, int toffset)` / `boolean isEmpty()`
- `byte[] getBytes()` / `byte[] getBytes(Charset charset)` / `byte[] getBytes(String charsetName)` / `void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)`
- `int hashCode()`
- `String intern()`
- 属性：`int length()`
- 大小写：`String toLowerCase()` / `String toLowerCase(Locale locale)` / `String toUpperCase()` / `String toUpperCase(Locale locale)`
- 转换：`char[] toCharArray()` / `String toString()`
- 转为对象：`static String valueOf(boolean b)` / `static String valueOf(char c)` / `static String valueOf(char[] data)` / `static String valueOf(char[] data, int offset, int count)` / `static String valueOf(double d)` / `static String valueOf(float f)` / `static String valueOf(int i)` / `static String valueOf(long l)` / `static String valueOf(Object obj)`

StringBuffer(线程安全的字符序列，单线程使用 StringBuilder)  
**StringBuilder**(字符序列)
- `StringBuilder()` / `StringBuilder(CharSequence seq)` / `StringBuilder(int capacity)` / `StringBuilder(String str)`
- 追加：`StringBuilder append(boolean b)` / `StringBuilder append(char c)` / `StringBuilder append(char[] str)` / `StringBuilder append(char[] str, int offset, int len)` / `StringBuilder append(CharSequence s)` / `StringBuilder append(CharSequence s, int start, int end)` / `StringBuilder append(double d)` / `StringBuilder append(float f)` / `StringBuilder append(int i)` / `StringBuilder append(long lng)` / `StringBuilder append(Object obj)` / `StringBuilder append(String str)` / `StringBuilder append(StringBuffer sb)` / `StringBuilder appendCodePoint(int codePoint)`
- 容量：`int capacity()` / `void ensureCapacity(int minimumCapacity)` / `void trimToSize()`
- 元素：`char charAt(int index)` / `void setCharAt(int index, char ch)` / `void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)` / `CharSequence subSequence(int start, int end)` / `String substring(int start)` / `String substring(int start, int end)`
- 码点：`int codePointAt(int index)` / `int codePointBefore(int index)` / `int codePointCount(int beginIndex, int endIndex)` / `int offsetByCodePoints(int index, int codePointOffset)`
- 删除：`StringBuilder delete(int start, int end)` / `StringBuilder deleteCharAt(int index)`
- 索引：`int indexOf(String str)` / `int indexOf(String str, int fromIndex)` / `int lastIndexOf(String str)` / `int lastIndexOf(String str, int fromIndex)`
- 插入：`StringBuilder insert(int offset, boolean b)` / `StringBuilder insert(int offset, char c)` / `StringBuilder insert(int offset, char[] str)` / `StringBuilder insert(int index, char[] str, int offset, int len)` / `StringBuilder insert(int dstOffset, CharSequence s)` / `StringBuilder insert(int dstOffset, CharSequence s, int start, int end)` / `StringBuilder insert(int offset, double d)` / `StringBuilder insert(int offset, float f)` / `StringBuilder insert(int offset, int i)` / `StringBuilder insert(int offset, long l)` / `StringBuilder insert(int offset, Object obj)` / `StringBuilder insert(int offset, String str)`
- `int length()` / `void setLength(int newLength)`
- 替换：`StringBuilder replace(int start, int end, String str)`
- 翻转：`StringBuilder reverse()`
- `String toString()`

`Enum<E extends Enum<E>>`(公共基类)

`Class<T>`(正在运行的类或接口)

ClassLoader(加载类)

Package(包的实现与规范)

**Math**/StrictMath
- `static double PI`
- `static double abs(double a)` / `static float abs(float a)` / `static int abs(int a)` / `static long abs(long a)`
- 临界：`static int addExact(int x, int y)` / `static long addExact(long x, long y)` / `static int decrementExact(int a)` / `static long decrementExact(long a)` / `static int incrementExact(int a)` / `static long incrementExact(long a)` / `static int multiplyExact(int x, int y)` / `static long multiplyExact(long x, long y)` / `static int negateExact(int a)` / `static long negateExact(long a)` / `static int subtractExact(int x, int y)` / `static long subtractExact(long x, long y)` / `static int toIntExact(long value)`
- `static double hypot(double x, double y)` / `static double pow(double a, double b)` / `static double sqrt(double a)`
- 舍入：`static double ceil(double a)` / `static double floor(double a)` / `static double rint(double a)` / `static long round(double a)` / `static int round(float a)`
- 最大最小：`static double max(double a, double b)` / `static float max(float a, float b)` / `static int max(int a, int b)` / `static long max(long a, long b)` / `static double min(double a, double b)` / `static float min(float a, float b)` / `static int min(int a, int b)` / `static long min(long a, long b)`
- 接近无限：`static double nextAfter(double start, double direction)` / `static float nextAfter(float start, double direction)` / `static double nextDown(double d)` / `static float nextDown(float f)` / `static double nextUp(double d)` / `static float nextUp(float f)`
- `static double random()`
- 符号：`static double signum(double d)` / `static float signum(float f)`

Object
- `Object()`
- `protected Object clone()`
- `boolean equals(Object obj)`
- `protected void finalize()`
- `Class<?> getClass()`
- `int hashCode()`
- `void notify()` / `void notifyAll()` / `void wait()` / `void wait(long timeout)` / `void wait(long timeout, int nanos)`
- `String toString()`


ProcessBuilder(创建操作系统进程)
- `static class ProcessBuilder.Redirect`
- `ProcessBuilder(List<String> command)` / `ProcessBuilder(String... command)`
- `List<String> command()` / `ProcessBuilder command(List<String> command)` / `ProcessBuilder command(String... command)`
- `File directory()` / `ProcessBuilder directory(File directory)`
- `Map<String,String> environment()`
- `ProcessBuilder inheritIO()`
- 重定向：`ProcessBuilder.Redirect redirectError()` / `ProcessBuilder redirectError(File file)` / `ProcessBuilder redirectError(ProcessBuilder.Redirect destination)` / `boolean redirectErrorStream()` / `ProcessBuilder redirectErrorStream(boolean redirectErrorStream)` / `ProcessBuilder.Redirect redirectInput()` / `ProcessBuilder redirectInput(File file)` / `ProcessBuilder redirectInput(ProcessBuilder.Redirect source)` / `ProcessBuilder.Redirect redirectOutput()` / `ProcessBuilder redirectOutput(File file)` / `ProcessBuilder redirectOutput(ProcessBuilder.Redirect destination)`
- 启动：`Process start()`

**Process**(ProcessBuilder.start()创建一个线程)
- `Process()`
- `abstract void destroy()` / `Process destroyForcibly()`
- `abstract int exitValue()`
- `boolean isAlive()`
- `abstract InputStream getErrorStream()` / `abstract InputStream getInputStream()` / `abstract OutputStream getOutputStream()`
- `abstract int waitFor()` / `boolean waitFor(long timeout, TimeUnit unit)`


Runtime
- `static Runtime getRuntime()`
- `void addShutdownHook(Thread hook)` / `boolean removeShutdownHook(Thread hook)`
- `int availableProcessors()`
- `Process exec(String command)` / `Process exec(String[] cmdarray)` / `Process exec(String[] cmdarray, String[] envp)` / `Process exec(String[] cmdarray, String[] envp, File dir)` / `Process exec(String command, String[] envp)` / `Process exec(String command, String[] envp, File dir)`
- `void exit(int status)` / `void halt(int status)`
- `long totalMemory()` / `long maxMemory()` / `long freeMemory()`
- `void gc()`
- `void load(String filename)`
- `void loadLibrary(String libname)`
- `void runFinalization()`
- `void traceInstructions(boolean on)` / `void traceMethodCalls(boolean on)`

SecurityManager(安全策略)

StackTraceElement(堆栈跟踪)

**System**
- 流：`static PrintStream err` / `static InputStream in` / `static PrintStream out` / `static void setErr(PrintStream err)` / `static void setIn(InputStream in)` / `static void setOut(PrintStream out)`
- `static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)`
- 属性：`static String setProperty(String key, String value)` / `static void setProperties(Properties props)` / `static Properties getProperties()` / `static String clearProperty(String key)` / `static String getProperty(String key)` / `static String getProperty(String key, String def)`
- 系统级对象：`static Console console()` / `static void setSecurityManager(SecurityManager s)` / `static SecurityManager getSecurityManager()` / `static Channel inheritedChannel()`
- 环境变量：`static Map<String,String> getenv()` / `static String getenv(String name)` / `static String lineSeparator()`
- 时间：`static long currentTimeMillis()` / `static long nanoTime()`
- 系统级操作：`static void gc()` / `static void exit(int status)`
- `static int identityHashCode(Object x)`
- 库：`static void load(String filename)` / `static void loadLibrary(String libname)` / `static String mapLibraryName(String libname)`
- `static void runFinalization()`

**Thread**(线程)
ThreadGroup(线程组)
`ThreadLocal<T>`(线程局部变量)
`InheritableThreadLocal<T>`(继承的线程局部变量)

**Throwable**(Error和Exception的父类)
- ` Throwable()` / `Throwable(String message)` / `Throwable(String message, Throwable cause)` / `protected Throwable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)` / `Throwable(Throwable cause)`
- `void addSuppressed(Throwable exception)` / `Throwable[] getSuppressed()`
- `Throwable fillInStackTrace()` / `void setStackTrace(StackTraceElement[] stackTrace)` / `StackTraceElement[] getStackTrace()` / `void printStackTrace()`/ `void printStackTrace(PrintStream s)`/ `void printStackTrace(PrintWriter s)`
- `Throwable getCause()` / `Throwable initCause(Throwable cause)`
- `String getLocalizedMessage()` / `String getMessage()`
- `String toString()`

Void(占位符)

## `java.math`

BigDecimal(不变的、任意精度的十进制数字)

BigInteger(不变的、任意精度的整型)

MathContext(描述一些规则)

## `java.net`

Authenticator(网络连接认证)  
PasswordAuthentication(Authenticator使用的数据持有者)

CacheRequest(抽象类) CacheResponse(抽象类) SecureCacheResponse(抽象类)

ContentHandler(从URLConnection中读取Object的所有类的超类，抽象类)

CookieHandler(抽象类)
- `CookieHandler()`
- `abstract Map<String,List<String>> get(URI uri, Map<String,List<String>> requestHeaders)`
- `static CookieHandler getDefault()` / `static void setDefault(CookieHandler cHandler)`
- `abstract void put(URI uri, Map<String,List<String>> responseHeaders)`

CookieManager(提供CookieHandler的具体应用)
- `CookieManager()` / `CookieManager(CookieStore store, CookiePolicy cookiePolicy)`
- `Map<String,List<String>> get(URI uri, Map<String,List<String>> requestHeaders)` / `void put(URI uri, Map<String,List<String>> responseHeaders)`
- `CookieStore getCookieStore()` / `void setCookiePolicy(CookiePolicy cookiePolicy)`

**HttpCookie**
- `HttpCookie(String name, String value)`
- `Object clone()`
- `static boolean domainMatches(String domain, String host)`
- `boolean equals(Object obj)`
- 注释：`String getComment()` / `void setComment(String purpose)` / `String getCommentURL()` / `void setCommentURL(String purpose)`
- 属性：
  - `boolean getDiscard()` / `void setDiscard(boolean discard)`
  - `boolean isHttpOnly()` / `void setHttpOnly(boolean httpOnly)`
  - `String getDomain()` / `void setDomain(String pattern)`
  - `long getMaxAge()` / `void setMaxAge(long expiry)`
  - `String getName()`
  - `String getPath()` / `void setPath(String uri)`
  - `String getPortlist()` / `void setPortlist(String ports)`
  - `boolean getSecure()` / `void setSecure(boolean flag)`
  - `String getValue()` / `void setValue(String newValue)`
  - `int getVersion()` / `void setVersion(int v)`
- `boolean hasExpired()`
- `int hashCode()`
- `static List<HttpCookie> parse(String header)`
- `String toString()`


DatagramSocketImpl(DatagramPacket和DatagramSocket的抽象基类)

**DatagramPacket**(数据包封装)
- `DatagramPacket(byte[] buf, int length)` / `DatagramPacket(byte[] buf, int length, InetAddress address, int port)` / `DatagramPacket(byte[] buf, int offset, int length)` / `DatagramPacket(byte[] buf, int offset, int length, InetAddress address, int port)` / `DatagramPacket(byte[] buf, int offset, int length, SocketAddress address)` / `DatagramPacket(byte[] buf, int length, SocketAddress address)`
- `InetAddress getAddress()` / `void setAddress(InetAddress iaddr)`
- `byte[] getData()` / `void setData(byte[] buf)` / `void setData(byte[] buf, int offset, int length)`
- `int getLength()` / `void setLength(int length)`
- `int getOffset()`
- `int getPort()` / `void setPort(int iport)`
- `SocketAddress getSocketAddress()` / `void setSocketAddress(SocketAddress address)`

**DatagramSocket**(数据发送接受)
- `DatagramSocket()` / `protected DatagramSocket(DatagramSocketImpl impl)` / `DatagramSocket(int port)` / `DatagramSocket(int port, InetAddress laddr)` / `DatagramSocket(SocketAddress bindaddr)`
- 开关：`void connect(InetAddress address, int port)` / `void connect(SocketAddress addr)` / `void disconnect()` / `void close()` / `boolean isClosed()` / `boolean isConnected()`
- 广播：`boolean getBroadcast()` / `void setBroadcast(boolean on)`
- `DatagramChannel getChannel()`
- 地址端口：`void bind(SocketAddress addr)` / `boolean isBound()` / `InetAddress getInetAddress()` / `InetAddress getLocalAddress()` / `int getLocalPort()` / `SocketAddress getLocalSocketAddress()` / `int getPort()` / `SocketAddress getRemoteSocketAddress()`
- `void setReuseAddress(boolean on)` / `boolean getReuseAddress()`
- 缓冲区：`void setReceiveBufferSize(int size)` / `int getReceiveBufferSize()` / `int getSendBufferSize()` / `void setSendBufferSize(int size)`
- 超时：`void setSoTimeout(int timeout)` / `int getSoTimeout()`
- `void setTrafficClass(int tc)` / `int getTrafficClass()`
- 发送接受：`void receive(DatagramPacket p)` / `void send(DatagramPacket p)`
- `static void setDatagramSocketImplFactory(DatagramSocketImplFactory fac)`

MulticastSocket(DatagramSocket的子类，多播)
- `MulticastSocket()` / `MulticastSocket(int port)` / `MulticastSocket(SocketAddress bindaddr)`
- `InetAddress getInterface()` / `void setInterface(InetAddress inf)`
- `boolean getLoopbackMode()` / `void setLoopbackMode(boolean disable)`
- `NetworkInterface getNetworkInterface()` / `void setNetworkInterface(NetworkInterface netIf)`
- `int getTimeToLive()` / `void setTimeToLive(int ttl)`
- `byte getTTL()` / `void setTTL(byte ttl)`
- `void joinGroup(InetAddress mcastaddr)` / `void joinGroup(SocketAddress mcastaddr, NetworkInterface netIf)` / `void leaveGroup(InetAddress mcastaddr)` / `void leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)`
- `void send(DatagramPacket p, byte ttl)`


**URLConnection**(抽象类)
- `protected boolean allowUserInteraction` / `protected boolean connected` / `protected boolean doInput` / `protected boolean doOutput` / `protected long ifModifiedSince` / `protected URL url` / `protected boolean useCaches`
- `protected URLConnection(URL url)`
- `URL getURL()`
- 请求属性：`void addRequestProperty(String key, String value)` / `void setRequestProperty(String key, String value)` / `Map<String,List<String>> getRequestProperties()` / `String getRequestProperty(String key)`
- `abstract void connect()`
- 超时：`int getConnectTimeout()` / `void setConnectTimeout(int timeout)` / `void setReadTimeout(int timeout)` / `int getReadTimeout()`
- `boolean getAllowUserInteraction()` / `static boolean getDefaultAllowUserInteraction()` / `static void setDefaultAllowUserInteraction(boolean defaultallowuserinteraction)` / `void setAllowUserInteraction(boolean allowuserinteraction)`
- 头：`String getHeaderField(int n)` / `String getHeaderField(String name)` / `long getHeaderFieldDate(String name, long Default)` / `int getHeaderFieldInt(String name, int Default)` / `String getHeaderFieldKey(int n)` / `long getHeaderFieldLong(String name, long Default)` / `Map<String,List<String>> getHeaderFields()`
- 内容：`Object getContent()` / `Object getContent(Class[] classes)` / `String getContentEncoding()` / `int getContentLength()` / `long getContentLengthLong()` / `String getContentType()`
- `long getDate()` / `long getExpiration()`
- `boolean getDefaultUseCaches()` / `void setDefaultUseCaches(boolean defaultusecaches)`
- `boolean getDoInput()` / `void setDoInput(boolean doinput)` / `boolean getDoOutput()` / `void setDoOutput(boolean dooutput)`
- `static FileNameMap getFileNameMap()` / `static void setFileNameMap(FileNameMap map)`
- `long getIfModifiedSince()` / `void setIfModifiedSince(long ifmodifiedsince)` / `long getLastModified()`
- `InputStream getInputStream()`/ `OutputStream getOutputStream()`
- `Permission getPermission()`
- `boolean getUseCaches()` / `void setUseCaches(boolean usecaches)`
- `static String guessContentTypeFromName(String fname)` / `static String guessContentTypeFromStream(InputStream is)`
- `static void setContentHandlerFactory(ContentHandlerFactory fac)`
- `String toString()`

ResponseCache(URLConnection缓存的实现)
- `ResponseCache()`
- `abstract CacheResponse get(URI uri, String rqstMethod, Map<String,List<String>> rqstHeaders)` / `abstract CacheRequest put(URI uri, URLConnection conn)`
- `static ResponseCache getDefault()` / `static void setDefault(ResponseCache responseCache)`

**HttpURLConnection**(单个请求，抽象类，借助URLConnection)
- `protected int chunkLength` / `protected int fixedContentLength` / `protected long fixedContentLengthLong` / `protected boolean instanceFollowRedirects` / `protected String method` / `protected int responseCode` / `protected String responseMessage`
- 状态码(很多)：`HTTP_INTERNAL_ERROR`(500) / `HTTP_MOVED_PERM`(301) / `HTTP_MOVED_TEMP`(302) / `HTTP_NOT_FOUND`(404) / `HTTP_OK`(200)
- `protected HttpURLConnection(URL u)`
- `abstract void disconnect()`
- `InputStream getErrorStream()`
- 重定向：`static boolean getFollowRedirects()` / `static void setFollowRedirects(boolean set)` / `boolean getInstanceFollowRedirects()` / `void setInstanceFollowRedirects(boolean followRedirects)`
- 头字段：`String getHeaderField(int n)` / `long getHeaderFieldDate(String name, long Default)` / `String getHeaderFieldKey(int n)`
- `Permission getPermission()`
- `String getRequestMethod()` / `void setRequestMethod(String method)`
- `int getResponseCode()` / `String getResponseMessage()`
- `void setChunkedStreamingMode(int chunklen)`
- `void setFixedLengthStreamingMode(int contentLength)` / `void setFixedLengthStreamingMode(long contentLength)`
- `abstract boolean usingProxy()`

JarURLConnection(连接jar，抽象类，借助URLConnection)
- `protected URLConnection jarFileURLConnection`
- `protected JarURLConnection(URL url)`
- `Attributes getAttributes()`
- `Certificate[] getCertificates()`
- `String getEntryName()` / `JarEntry getJarEntry()`
- `abstract JarFile getJarFile()` / `URL getJarFileURL()`
- `Attributes getMainAttributes()` / `Manifest getManifest()`


**InetAddress**(IP地址超类)
- `boolean equals(Object obj)`
- 实例：`static InetAddress[] getAllByName(String host)` / `static InetAddress getByAddress(byte[] addr)` / `static InetAddress getByAddress(String host, byte[] addr)` / `static InetAddress getByName(String host)` / `static InetAddress getLocalHost()` / `static InetAddress getLoopbackAddress()`
- 获取：`byte[] getAddress()` / `String getCanonicalHostName()` / `String getHostAddress()` / `String getHostName()`
- `int hashCode()`
- 测试：`boolean isAnyLocalAddress()` / `boolean isLinkLocalAddress()` / `boolean isLoopbackAddress()` / `boolean isSiteLocalAddress()`
- 多播：`boolean isMCGlobal()` / `boolean isMCLinkLocal()` / `boolean isMCNodeLocal()` / `boolean isMCOrgLocal()` / `boolean isMCSiteLocal()` / `boolean isMulticastAddress()`
- `boolean isReachable(int timeout)` / `boolean isReachable(NetworkInterface netif, int ttl, int timeout)`
- `String toString()`

**Inet4Address**
- `boolean equals(Object obj)`
- `byte[] getAddress()` / `String getHostAddress()`
- `int hashCode()`
- 测试：`boolean isAnyLocalAddress()` / `boolean isLinkLocalAddress()` / `boolean isLoopbackAddress()` / `boolean isSiteLocalAddress()`
- 多播：`boolean isMCGlobal()` / `boolean isMCLinkLocal()` / `boolean isMCNodeLocal()` / `boolean isMCOrgLocal()` / `boolean isMCSiteLocal()` / `boolean isMulticastAddress()`

**Inet6Address**
- `boolean equals(Object obj)`
- `byte[] getAddress()` / `static Inet6Address getByAddress(String host, byte[] addr, int scope_id)` / `static Inet6Address getByAddress(String host, byte[] addr, NetworkInterface nif)` / `String getHostAddress()`
- `NetworkInterface getScopedInterface()` / `int getScopeId()`
- `int hashCode()`
- 测试：`boolean isAnyLocalAddress()` / `boolean isIPv4CompatibleAddress()` / `boolean isLinkLocalAddress()` / `boolean isLoopbackAddress()` / `boolean isSiteLocalAddress()`
- 多播：`boolean isMCGlobal()` / `boolean isMCLinkLocal()` / `boolean isMCNodeLocal()` / `boolean isMCOrgLocal()` / `boolean isMCSiteLocal()` / `boolean isMulticastAddress()`


InetSocketAddress(IP套接字)
- `InetSocketAddress(InetAddress addr, int port)` / `InetSocketAddress(int port)` / `InetSocketAddress(String hostname, int port)`
- `static InetSocketAddress createUnresolved(String host, int port)`
- `boolean equals(Object obj)`
- `InetAddress getAddress()` / `String getHostName()` / `String getHostString()` / `int getPort()`
- `boolean isUnresolved()`
- `int hashCode()`
- `String toString()`

InterfaceAddress(网络接口地址)
- `boolean equals(Object obj)`
- `InetAddress getAddress()` / `InetAddress getBroadcast()` / `short getNetworkPrefixLength()`
- `int hashCode()`
- `String toString()`


NetPermission(网络权限)

NetworkInterface(网络接口)
- `boolean equals(Object obj)`
- 实例：`static NetworkInterface getByIndex(int index)` / `static NetworkInterface getByInetAddress(InetAddress addr)` / `static NetworkInterface getByName(String name)`
- `String getDisplayName()` / `byte[] getHardwareAddress()` / `Enumeration<InetAddress> getInetAddresses()` / `List<InterfaceAddress> getInterfaceAddresses()` / `int getMTU()` / `String getName()` / `static Enumeration<NetworkInterface> getNetworkInterfaces()` / `NetworkInterface getParent()` / `Enumeration<NetworkInterface> getSubInterfaces()`
- `int hashCode()`
- `boolean isLoopback()` / `boolean isPointToPoint()` / `boolean isUp()` / `boolean isVirtual()` / `boolean supportsMulticast()`
- `String toString()`


Proxy(代理设置，通常是http和socks)  
ProxySelector(代理选择器)


**ServerSocket**(服务端套接字)
- `ServerSocket()` / `ServerSocket(int port)` / `ServerSocket(int port, int backlog)` / `ServerSocket(int port, int backlog, InetAddress bindAddr)`
- `Socket accept()`
- `void bind(SocketAddress endpoint)` / `void bind(SocketAddress endpoint, int backlog)` / `boolean isBound()`
- `void close()` / `boolean isClosed()`
- `ServerSocketChannel getChannel()`
- 获取：`InetAddress getInetAddress()` / `int getLocalPort()` / `SocketAddress getLocalSocketAddress()`
- `int getReceiveBufferSize()` / `void setReceiveBufferSize(int size)`
- `boolean getReuseAddress()` / `void setReuseAddress(boolean on)`
- `int getSoTimeout()` / `void setSoTimeout(int timeout)`
- `protected void implAccept(Socket s)`
- `void setPerformancePreferences(int connectionTime, int latency, int bandwidth)`
- `static void setSocketFactory(SocketImplFactory fac)`
- `String toString()`

**Socket**(客户端套接字)
- `Socket()` / `Socket(InetAddress address, int port)` / `Socket(InetAddress address, int port, InetAddress localAddr, int localPort)` / `Socket(Proxy proxy)` / `Socket(SocketImpl impl)` / `Socket(String host, int port)` / `Socket(String host, int port, InetAddress localAddr, int localPort)`
- 绑定：`void bind(SocketAddress bindpoint)` / `boolean isBound()`
- 开关：`void close()` / `boolean isClosed()` / `void connect(SocketAddress endpoint)` / `void connect(SocketAddress endpoint, int timeout)` / `boolean isConnected()`
- 通道：`SocketChannel getChannel()`
- 地址端口：`InetAddress getInetAddress()` / `InetAddress getLocalAddress()` / `int getLocalPort()` / `SocketAddress getLocalSocketAddress()` / `int getPort()` / `SocketAddress getRemoteSocketAddress()`
- 流：`InputStream getInputStream()` / `OutputStream getOutputStream()`
- `boolean getKeepAlive()` / `void setKeepAlive(boolean on)`
- 紧急数据：`boolean getOOBInline()` / `void setOOBInline(boolean on)` / `void sendUrgentData(int data)`
- 缓冲：`int getReceiveBufferSize()` / `void setReceiveBufferSize(int size)` / `int getSendBufferSize()` / `void setSendBufferSize(int size)`
- `boolean getReuseAddress()` / `void setReuseAddress(boolean on)`
- `int getSoLinger()` / `void setSoLinger(boolean on, int linger)`
- `int getSoTimeout()` / `void setSoTimeout(int timeout)`
- `boolean getTcpNoDelay()` / `void setTcpNoDelay(boolean on)`
- `int getTrafficClass()` / `void setTrafficClass(int tc)`
- `boolean isInputShutdown()` / `boolean isOutputShutdown()` / `void shutdownInput()` / `void shutdownOutput()`
- `void setPerformancePreferences(int connectionTime, int latency, int bandwidth)`
- `static void setSocketImplFactory(SocketImplFactory fac)`
- `String toString()`

SocketAddress(仅代表套接字地址)

SocketImpl(所有套接字的公共超类)

SocketPermission(套接字的网络访问权限)

StandardSocketOptions(套接字标准选项)
- `static SocketOption<NetworkInterface> IP_MULTICAST_IF` / `static SocketOption<Boolean> IP_MULTICAST_LOOP` / `static SocketOption<Integer> IP_MULTICAST_TTL` / `static SocketOption<Integer> IP_TOS`
- `static SocketOption<Boolean> SO_BROADCAST` / `static SocketOption<Boolean> SO_KEEPALIVE` / `static SocketOption<Integer> SO_LINGER` / `static SocketOption<Integer> SO_RCVBUF` / `static SocketOption<Boolean> SO_REUSEADDR` / `static SocketOption<Integer> SO_SNDBUF`
- `static SocketOption<Boolean> TCP_NODELAY`

**URI**(统一资源标识符)
URI实例有以下九个组件:scheme, scheme-specific-part, authority, user-info, host, port, path, query, fragment.
- `URI(String str)` / `URI(String scheme, String ssp, String fragment)` / `URI(String scheme, String userInfo, String host, int port, String path, String query, String fragment)` / `URI(String scheme, String host, String path, String fragment)` / `URI(String scheme, String authority, String path, String query, String fragment)`
- 比较：`int compareTo(URI that)` / `boolean equals(Object ob)`
- 转换：`static URI create(String str)` / `URL toURL()`
- 获取9个组件：`String getScheme()` / `String getSchemeSpecificPart()` / `String getAuthority()` / `String getUserInfo()` / `String getHost()` / `int getPort()` / `String getPath()` / `String getQuery()` / `String getFragment()`
- 原始数据：`String getRawAuthority()` / `String getRawFragment()` / `String getRawPath()` / `String getRawQuery()` / `String getRawSchemeSpecificPart()` / `String getRawUserInfo()`
- `int hashCode()`
- `boolean isAbsolute()` / `boolean isOpaque()`
- `URI normalize()`
- 解析：`URI parseServerAuthority()`
- `URI relativize(URI uri)` / `URI resolve(String str)` / `URI resolve(URI uri)`
- `String toASCIIString()` / `String toString()`

**URL**
- `URL(String spec)` / `URL(String protocol, String host, int port, String file)` / `URL(String protocol, String host, int port, String file, URLStreamHandler handler)` / `URL(String protocol, String host, String file)` / `URL(URL context, String spec)` / `URL(URL context, String spec, URLStreamHandler handler)`
- 比较：`boolean equals(Object obj)` / `boolean sameFile(URL other)`
- 获取不同组件（略参照URI）：`String getProtocol()` / `String getAuthority()` / `String getUserInfo()` / `String getHost()` / `int getPort()` / `String getPath()` / `String getQuery()` / `String getFile()` / `String getRef()`
- 内容：`Object getContent()` / `Object getContent(Class[] classes)`
- `int getDefaultPort()`
- `int hashCode()`
- `URLConnection openConnection()` / `URLConnection openConnection(Proxy proxy)`
- `InputStream openStream()`
- `static void setURLStreamHandlerFactory(URLStreamHandlerFactory fac)`
- `String toExternalForm()` / `String toString()`
- `URI toURI()`

URLClassLoader(ClassLoader的子类，从Jar或URL中加载类)

**URLDecoder**
- `static String decode(String s, String enc)`

**URLEncoder**
- `static String encode(String s, String enc)`

URLPermission(URL访问权限)

URLStreamHandler(所有流协议处理程序的通用超类)


## `java.nio`

ByteOrder(类型安全枚举)


Buffer(抽象类)
- `abstract Object array()` / `abstract int arrayOffset()` / `abstract boolean hasArray()`
- `int capacity()` / `boolean hasRemaining()` / `int remaining()`
- `Buffer clear()`
- `Buffer flip()` / `Buffer rewind()`
- `abstract boolean isDirect()` / `abstract boolean isReadOnly()`
- `int limit()` / `Buffer limit(int newLimit)`
- `Buffer mark()` / `Buffer reset()`
- `int position()` / `Buffer position(int newPosition)`

ByteBuffer(抽象类)
MappedByteBuffer(ByteBuffer的子类, 抽象类)

CharBuffer
- 实例：`static CharBuffer allocate(int capacity)` / `static CharBuffer wrap(char[] array)` / `static CharBuffer wrap(char[] array, int offset, int length)` / `static CharBuffer wrap(CharSequence csq)` / `static CharBuffer wrap(CharSequence csq, int start, int end)`
- `char[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract CharBuffer asReadOnlyBuffer()`
- `char charAt(int index)`
- `IntStream chars()`
- `abstract CharBuffer compact()`
- 比较：`int compareTo(CharBuffer that)` / `boolean equals(Object ob)`
- 存取：
  - `int read(CharBuffer target)`
  - `abstract char get()` / `CharBuffer get(char[] dst)` / `CharBuffer get(char[] dst, int offset, int length)` / `abstract char get(int index)`
  - `CharBuffer put(char[] src)` / `CharBuffer put(char[] src, int offset, int length)` / `CharBuffer put(CharBuffer src)` / `abstract CharBuffer put(int index, char c)` / `CharBuffer put(String src)` / `CharBuffer put(String src, int start, int end)` / `abstract CharBuffer put(char c)`
  - `CharBuffer append(char c)` / `CharBuffer append(CharSequence csq)` / `CharBuffer append(CharSequence csq, int start, int end)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `int length()`
- `abstract ByteOrder order()`
- 操作：`abstract CharBuffer slice()` / `abstract CharBuffer subSequence(int start, int end)` /`abstract CharBuffer duplicate()`
- `String toString()`

DoubleBuffer
- 实例：`static DoubleBuffer allocate(int capacity)` / `static DoubleBuffer wrap(double[] array)` / `static DoubleBuffer wrap(double[] array, int offset, int length)`
- `double[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract DoubleBuffer asReadOnlyBuffer()`
- `abstract DoubleBuffer compact()`
- 比较：`int compareTo(DoubleBuffer that)` / `boolean equals(Object ob)`
- 存取：
  - `abstract double get()` / `DoubleBuffer get(double[] dst)` / `DoubleBuffer get(double[] dst, int offset, int length)` / `abstract double get(int index)`
  - `abstract DoubleBuffer put(double d)` / `DoubleBuffer put(double[] src)` / `DoubleBuffer put(double[] src, int offset, int length)` / `DoubleBuffer put(DoubleBuffer src)` / `abstract DoubleBuffer put(int index, double d)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `abstract ByteOrder order()`
- 操作：`abstract DoubleBuffer slice()` / `abstract DoubleBuffer duplicate()`
- `String toString()`

FloatBuffer
- 实例：`static FloatBuffer allocate(int capacity)` / `static FloatBuffer wrap(float[] array)` / `static FloatBuffer wrap(float[] array, int offset, int length)`
- `float[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract FloatBuffer asReadOnlyBuffer()`
- `abstract FloatBuffer compact()`
- 比较：`int compareTo(FloatBuffer that)` / `boolean equals(Object ob)`
- 存取：
  - `abstract float get()` / `FloatBuffer get(float[] dst)` / `FloatBuffer get(float[] dst, int offset, int length)` / `abstract float get(int index)`
  - `abstract FloatBuffer put(float f)` / `FloatBuffer put(float[] src)` / `FloatBuffer put(float[] src, int offset, int length)` / `FloatBuffer put(FloatBuffer src)` / `abstract FloatBuffer put(int index, float f)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `abstract ByteOrder order()`
- 操作：`abstract FloatBuffer slice()` / `abstract FloatBuffer duplicate()`
- `String toString()`

IntBuffer
- 实例：`static IntBuffer allocate(int capacity)` / `static IntBuffer wrap(int[] array)` / `static IntBuffer wrap(int[] array, int offset, int length)`
- `int[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract IntBuffer asReadOnlyBuffer()`
- `abstract IntBuffer compact()`
- 比较：`int compareTo(IntBuffer that)` / `boolean equals(Object ob)`
- 存取：
  - `abstract int get()` / `abstract int get(int index)` / `IntBuffer get(int[] dst)` / `IntBuffer get(int[] dst, int offset, int length)`
  - `abstract IntBuffer put(int i)` / `IntBuffer put(int[] src)` / `IntBuffer put(int[] src, int offset, int length)` / `IntBuffer put(IntBuffer src)` / `abstract IntBuffer put(int index, int i)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `abstract ByteOrder order()`
- 操作：`abstract IntBuffer slice()` / `abstract IntBuffer duplicate()`
- `String toString()`

LongBuffer
- 实例：`static LongBuffer allocate(int capacity)` / `static LongBuffer wrap(long[] array)` / `static LongBuffer wrap(long[] array, int offset, int length)`
- `long[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract LongBuffer asReadOnlyBuffer()`
- `abstract LongBuffer compact()`
- 比较：`int compareTo(LongBuffer that)` / `boolean equals(Object ob)`
- 存取：
  - `abstract long get()` / `abstract long get(int index)` / `LongBuffer get(long[] dst)` / `LongBuffer get(long[] dst, int offset, int length)`
  - `abstract LongBuffer put(int index, long l)` / `abstract LongBuffer put(long l)` / `LongBuffer put(long[] src)` / `LongBuffer put(long[] src, int offset, int length)` / `LongBuffer put(LongBuffer src)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `abstract ByteOrder order()`
- 操作：`abstract LongBuffer slice()` / `abstract LongBuffer duplicate()`
- `String toString()`

ShortBuffer
- 实例：`static ShortBuffer allocate(int capacity)` / `static ShortBuffer wrap(short[] array)` / `static ShortBuffer wrap(short[] array, int offset, int length)`
- `short[] array()` / `int arrayOffset()` / `boolean hasArray()`
- `abstract ShortBuffer asReadOnlyBuffer()`
- `abstract ShortBuffer compact()`
- 比较：`int compareTo(ShortBuffer that)` / `boolean equals(Object ob)`
- 操作：
  - `abstract short get()` / `abstract short get(int index)` / `ShortBuffer get(short[] dst)` / `ShortBuffer get(short[] dst, int offset, int length)`
  - `abstract ShortBuffer put(int index, short s)` / `abstract ShortBuffer put(short s)` / `ShortBuffer put(short[] src)` / `ShortBuffer put(short[] src, int offset, int length)` / `ShortBuffer put(ShortBuffer src)`
- `int hashCode()`
- `abstract boolean isDirect()`
- `abstract ByteOrder order()`
- 操作：`abstract ShortBuffer slice()` / `abstract ShortBuffer duplicate()`
- `String toString()`

## `java.nio.file`

**Files**(静态方法集)
- 文件：
  - 复制文件：`static long copy(InputStream in, Path target, CopyOption... options)` / `static long copy(Path source, OutputStream out)`
  - 创建文件：`static Path createFile(Path path, FileAttribute<?>... attrs)` / `static Path createLink(Path link, Path existing)`
  - 创建临时文件：`static Path createTempFile(Path dir, String prefix, String suffix, FileAttribute<?>... attrs)` / `static Path createTempFile(String prefix, String suffix, FileAttribute<?>... attrs)`
  - 读取：`static Stream<String> lines(Path path)` / `static Stream<String> lines(Path path, Charset cs)` / `static List<String> readAllLines(Path path)` / `static List<String> readAllLines(Path path, Charset cs)` / `static byte[] readAllBytes(Path path)`
  - 写入：`static Path write(Path path, byte[] bytes, OpenOption... options)` / `static Path write(Path path, Iterable<? extends CharSequence> lines, Charset cs, OpenOption... options)` / `static Path write(Path path, Iterable<? extends CharSequence> lines, OpenOption... options)`
  - 链接：`static Path readSymbolicLink(Path link)` / `static Path createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs)`
  - 大小：`static long size(Path path)`
- 目录：
  - 创建目录：`static Path createDirectories(Path dir, FileAttribute<?>... attrs)` / `static Path createDirectory(Path dir, FileAttribute<?>... attrs)`
  - 创建临时目录：`static Path createTempDirectory(Path dir, String prefix, FileAttribute<?>... attrs)` / `static Path createTempDirectory(String prefix, FileAttribute<?>... attrs)`
  - 列表：`static Stream<Path> list(Path dir)`
  - 遍历：
    - `static Stream<Path> walk(Path start, FileVisitOption... options)` / `static Stream<Path> walk(Path start, int maxDepth, FileVisitOption... options)`
    - `static Path walkFileTree(Path start, FileVisitor<? super Path> visitor)` / `static Path walkFileTree(Path start, Set<FileVisitOption> options, int maxDepth, FileVisitor<? super Path> visitor)`
- 通用
  - 删除：`static void delete(Path path)` / `static boolean deleteIfExists(Path path)`
  - 存在：`static boolean exists(Path path, LinkOption... options)` / `static boolean notExists(Path path, LinkOption... options)`
  - 复制：`static Path copy(Path source, Path target, CopyOption... options)`
  - 移动：`static Path move(Path source, Path target, CopyOption... options)`
  - 搜索：`static Stream<Path> find(Path start, int maxDepth, BiPredicate<Path,BasicFileAttributes> matcher, FileVisitOption... options)`
  - 性质：`static boolean isRegularFile(Path path, LinkOption... options)` / `static boolean isSameFile(Path path, Path path2)` / `static boolean isSymbolicLink(Path path)` / `static boolean isDirectory(Path path, LinkOption... options)`
  - RWX：`static boolean isExecutable(Path path)` / `static boolean isWritable(Path path)` / `static boolean isReadable(Path path)` / `static boolean isHidden(Path path)`
  - 修改时间：`static FileTime getLastModifiedTime(Path path, LinkOption... options)` / `static Path setLastModifiedTime(Path path, FileTime time)`
  - 所有者：`static UserPrincipal getOwner(Path path, LinkOption... options)` / `static Path setOwner(Path path, UserPrincipal owner)`
  - 文件权限：`static Set<PosixFilePermission> getPosixFilePermissions(Path path, LinkOption... options)` / `static Path setPosixFilePermissions(Path path, Set<PosixFilePermission> perms)`
  - 文件类型：`static String probeContentType(Path path)`
- 属性：
  - `static Object getAttribute(Path path, String attribute, LinkOption... options)` / `static Path setAttribute(Path path, String attribute, Object value, LinkOption... options)`
  - `static <V extends FileAttributeView>V getFileAttributeView(Path path, Class<V> type, LinkOption... options)`
  - `static <A extends BasicFileAttributes>A readAttributes(Path path, 类<A> type, LinkOption... options)` / `static Map<String,Object> readAttributes(Path path, String attributes, LinkOption... options)`
- 读写对象：
  - `static BufferedReader newBufferedReader(Path path)` / `static BufferedReader newBufferedReader(Path path, Charset cs)`
  - `static BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... options)` / `static BufferedWriter newBufferedWriter(Path path, OpenOption... options)`
  - `static SeekableByteChannel newByteChannel(Path path, OpenOption... options)` / `static SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)`
  - `static DirectoryStream<Path> newDirectoryStream(Path dir)` / `static DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter)` / `static DirectoryStream<Path> newDirectoryStream(Path dir, String glob)`
  - `static InputStream newInputStream(Path path, OpenOption... options)`
  - `static OutputStream newOutputStream(Path path, OpenOption... options)`
- `static FileStore getFileStore(Path path)`

FileStore（文件存储的具体方式，抽象）
- `protected FileStore()`
- `abstract Object getAttribute(String attribute)`
- `abstract <V extends FileStoreAttributeView>V getFileStoreAttributeView(Class<V> type)`
- `abstract long getTotalSpace()` / `abstract long getUnallocatedSpace()` / `abstract long getUsableSpace()`
- `abstract boolean isReadOnly()`
- `abstract String name()` / `abstract String type()`
- `abstract boolean supportsFileAttributeView(Class<? extends FileAttributeView> type)` / `abstract boolean supportsFileAttributeView(String name)`

**Paths**(转为Path对象)
- `static Path get(String first, String... more)`
- `static Path get(URI uri)`


FileSystem（文件系统，抽象）
- `protected FileSystem()`
- `abstract void close()`
- `abstract Iterable<FileStore> getFileStores()`
- `abstract Path getPath(String first, String... more)`
- `abstract PathMatcher getPathMatcher(String syntaxAndPattern)` / `abstract Iterable<Path> getRootDirectories()`
- `abstract String getSeparator()`
- `abstract UserPrincipalLookupService getUserPrincipalLookupService()`
- `abstract boolean isOpen()` / `abstract boolean isReadOnly()`
- `abstract WatchService newWatchService()`
- `abstract FileSystemProvider provider()`
- `abstract Set<String> supportedFileAttributeViews()`

FileSystems(工厂方法)
- `static FileSystem getDefault()`
- `static FileSystem getFileSystem(URI uri)` / `static FileSystem newFileSystem(Path path, ClassLoader loader)` / `static FileSystem newFileSystem(URI uri, Map<String,?> env)` / `static FileSystem newFileSystem(URI uri, Map<String,?> env, ClassLoader loader)`

`SimpleFileVisitor<T>`
- `protected SimpleFileVisitor()`
- `FileVisitResult postVisitDirectory(T dir, IOException exc)` / `FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs)`
- `FileVisitResult visitFile(T file, BasicFileAttributes attrs)`
- `FileVisitResult visitFileFailed(T file, IOException exc)`

StandardWatchEventKinds
- `static WatchEvent.Kind<Path> ENTRY_CREATE`
- `static WatchEvent.Kind<Path> ENTRY_DELETE`
- `static WatchEvent.Kind<Path> ENTRY_MODIFY`
- `static WatchEvent.Kind<Object> OVERFLOW`

## `java.text`

BreakIterator(文本中的边界)
- `static int DONE`
- `protected BreakIterator()`
- `Object clone()`
- 元素：`abstract int current()` / `abstract int first()` / `abstract int last()` / `abstract int previous()` / `abstract int next()` / `abstract int next(int n)` / `abstract int following(int offset)` / `int preceding(int offset)`
- `abstract CharacterIterator getText()`
- `static Locale[] getAvailableLocales()`
- `static BreakIterator getCharacterInstance()` / `static BreakIterator getCharacterInstance(Locale locale)`
- `static BreakIterator getLineInstance()` / `static BreakIterator getLineInstance(Locale locale)`
- `static BreakIterator getSentenceInstance()` / `static BreakIterator getSentenceInstance(Locale locale)`
- `static BreakIterator getWordInstance()` / `static BreakIterator getWordInstance(Locale locale)`
- `boolean isBoundary(int offset)`
- `abstract void setText(CharacterIterator newText)` / `void setText(String newText)`

**NumberFormat**(抽象)
- `NumberFormat.Field`
- `static int FRACTION_FIELD` / `static int INTEGER_FIELD`
- `protected NumberFormat()`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化：`String format(double number)` / `abstract StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)` / `String format(long number)` / `abstract StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)` / `StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos)`
- `static Locale[] getAvailableLocales()`
- 货币：`void setCurrency(Currency currency)` / `Currency getCurrency()`
- 最大最小位数：`int getMaximumFractionDigits()` / `int getMaximumIntegerDigits()` / `int getMinimumFractionDigits()` / `int getMinimumIntegerDigits()` / `void setMaximumFractionDigits(int newValue)` / `void setMaximumIntegerDigits(int newValue)` / `void setMinimumFractionDigits(int newValue)` / `void setMinimumIntegerDigits(int newValue)`
- 实例：
  - `static NumberFormat getNumberInstance()` / `static NumberFormat getNumberInstance(Locale inLocale)`
  - `static NumberFormat getPercentInstance()` / `static NumberFormat getPercentInstance(Locale inLocale)`
  - `static NumberFormat getCurrencyInstance()` / `static NumberFormat getCurrencyInstance(Locale inLocale)`
  - `static NumberFormat getInstance()` / `static NumberFormat getInstance(Locale inLocale)`
  - `static NumberFormat getIntegerInstance()` / `static NumberFormat getIntegerInstance(Locale inLocale)`
- `RoundingMode getRoundingMode()` / `void setRoundingMode(RoundingMode roundingMode)`
- `int hashCode()`
- `boolean isGroupingUsed()` / `void setGroupingUsed(boolean newValue)`
- `boolean isParseIntegerOnly()` / `void setParseIntegerOnly(boolean value)`
- 解析：`Number parse(String source)` / `abstract Number parse(String source, ParsePosition parsePosition)`
- `Object parseObject(String source, ParsePosition pos)`


**ChoiceFormat**(NumberFormat子类，追加格式到一组数字)
- `ChoiceFormat(double[] limits, String[] formats)` / `ChoiceFormat(String newPattern)`
- 应用模式：`void applyPattern(String newPattern)`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化与解析：`StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition status)` / `StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition status)` / `Number parse(String text, ParsePosition status)`
- `Object[] getFormats()` / `double[] getLimits()`
- `int hashCode()`
- `static double nextDouble(double d)` / `static double nextDouble(double d, boolean positive)` / `static double previousDouble(double d)`
- `void setChoices(double[] limits, String[] formats)`
- `String toPattern()`

**DecimalFormat**(NumberFormat子类，数字的格式化)
格式化的符号（如#表示数字或补零）
- `DecimalFormat()` / `DecimalFormat(String pattern)` / `DecimalFormat(String pattern, DecimalFormatSymbols symbols)`
- 应用模式：`void applyLocalizedPattern(String pattern)` / `void applyPattern(String pattern)`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化与解析：`StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition)` / `StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition)` / `StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos)` / `Number parse(String text, ParsePosition pos)`
- 最大最小位数：`int getMaximumFractionDigits()` / `int getMaximumIntegerDigits()` / `int getMinimumFractionDigits()` / `int getMinimumIntegerDigits()` / `void setMaximumFractionDigits(int newValue)` / `void setMaximumIntegerDigits(int newValue)` / `void setMinimumFractionDigits(int newValue)` / `void setMinimumIntegerDigits(int newValue)`
- 前缀后缀：`String getNegativePrefix()` / `String getNegativeSuffix()` / `String getPositivePrefix()` / `String getPositiveSuffix()` / `void setNegativePrefix(String newValue)` / `void setNegativeSuffix(String newValue)` / `void setPositivePrefix(String newValue)` / `void setPositiveSuffix(String newValue)`
-  成对属性：
  - `int getMultiplier()` / `void setMultiplier(int newValue)`
  - `RoundingMode getRoundingMode()` / `void setRoundingMode(RoundingMode roundingMode)`
  - `AttributedCharacterIterator formatToCharacterIterator(Object obj)`
  - `Currency getCurrency()` / `void setCurrency(Currency currency)`
  - `DecimalFormatSymbols getDecimalFormatSymbols()` / `void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols)`
  - `int getGroupingSize()` / `void setGroupingSize(int newValue)`
  - `boolean isDecimalSeparatorAlwaysShown()` / `void setDecimalSeparatorAlwaysShown(boolean newValue)`
  - `boolean isParseBigDecimal()` / `void setParseBigDecimal(boolean newValue)`
- `void setGroupingUsed(boolean newValue)`
- `String toLocalizedPattern()` / `String toPattern()`
- `int hashCode()`

**DecimalFormatSymbols**（DecimalFormat的符号集，如小数分隔符）
- `DecimalFormatSymbols()` / `DecimalFormatSymbols(Locale locale)`
- `Object clone()`
- `boolean equals(Object obj)`
- `static Locale[] getAvailableLocales()`
- 属性：
  - `Currency getCurrency()` / `void setCurrency(Currency currency)`
  - `String getCurrencySymbol()` / `void setCurrencySymbol(String currency)`
  - `char getDecimalSeparator()` / `void setDecimalSeparator(char decimalSeparator)`
  - `char getDigit()` / `void setDigit(char digit)`
  - `String getExponentSeparator()` / `void setExponentSeparator(String exp)`
  - `char getGroupingSeparator()` / `void setGroupingSeparator(char groupingSeparator)`
  - `String getInfinity()` / `void setInfinity(String infinity)`
  - `String getInternationalCurrencySymbol()` / `void setInternationalCurrencySymbol(String currencyCode)`
  - `char getMinusSign()` / `void setMinusSign(char minusSign)`
  - `char getMonetaryDecimalSeparator()` / `void setMonetaryDecimalSeparator(char sep)`
  - `String getNaN()` / `void setNaN(String NaN)`
  - `char getPatternSeparator()` / `void setPatternSeparator(char patternSeparator)`
  - `char getPercent()` / `void setPercent(char percent)`
  - `char getPerMill()` / `void setPerMill(char perMill)`
  - `char getZeroDigit()` / `void setZeroDigit(char zeroDigit)`
- 实例：`static DecimalFormatSymbols getInstance()` / `static DecimalFormatSymbols getInstance(Locale locale)`
- `int hashCode()`

**DateFormat**（日期格式化，抽象）
- `static class DateFormat.Field`
- 常量：
  - `static int ERA_FIELD` / `static int AM_PM_FIELD` / `static int MILLISECOND_FIELD` / `static int DATE_FIELD` / `static int MINUTE_FIELD` / `static int MONTH_FIELD` / `static int SECOND_FIELD` / `static int TIMEZONE_FIELD` / `static int YEAR_FIELD`
  - `static int DAY_OF_WEEK_FIELD` / `static int DAY_OF_WEEK_IN_MONTH_FIELD` / `static int DAY_OF_YEAR_FIELD`
  - `static int DEFAULT` / `static int FULL` / `static int LONG` / `static int MEDIUM` / `static int SHORT`
  - `static int HOUR_OF_DAY0_FIELD` / `static int HOUR_OF_DAY1_FIELD` / `static int HOUR0_FIELD` / `static int HOUR1_FIELD`
  - `static int WEEK_OF_MONTH_FIELD` / `static int WEEK_OF_YEAR_FIELD`
- `protected DateFormat()`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化：`String format(Date date)` / `abstract StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)` / `StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition fieldPosition)`
- `static Locale[] getAvailableLocales()`
- 相关对象：
  - `Calendar getCalendar()` / `void setCalendar(Calendar newCalendar)`
  - `void setTimeZone(TimeZone zone)` / `TimeZone getTimeZone()`
  - `void setNumberFormat(NumberFormat newNumberFormat)` / `NumberFormat getNumberFormat()`
- 实例：
  - `static DateFormat getInstance()`
  - `static DateFormat getDateInstance()` / `static DateFormat getDateInstance(int style)` / `static DateFormat getDateInstance(int style, Locale aLocale)`
  - `static DateFormat getDateTimeInstance()` / `static DateFormat getDateTimeInstance(int dateStyle, int timeStyle)` / `static DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale aLocale)`
  - `static DateFormat getTimeInstance()` / `static DateFormat getTimeInstance(int style)` / `static DateFormat getTimeInstance(int style, Locale aLocale)`
- `int hashCode()`
- `void setLenient(boolean lenient)` / `boolean isLenient()`
- 解析：`Date parse(String source)` / `abstract Date parse(String source, ParsePosition pos)` / `Object parseObject(String source, ParsePosition pos)`

**DateFormatSymbols**（DateFormat的符号集，本地化时间格式，抽象）
- `DateFormatSymbols()` / `DateFormatSymbols(Locale locale)`
- `Object clone()`
- `boolean equals(Object obj)`
- 属性：
  - `String[] getAmPmStrings()` / `void setAmPmStrings(String[] newAmpms)`
  - `String[] getEras()` / `void setEras(String[] newEras)`
  - `String getLocalPatternChars()` / `void setLocalPatternChars(String newLocalPatternChars)`
  - `String[] getMonths()` / `void setMonths(String[] newMonths)`
  - `String[] getShortMonths()` / `void setShortMonths(String[] newShortMonths)`
  - `String[] getShortWeekdays()` / `void setShortWeekdays(String[] newShortWeekdays)`
  - `String[] getWeekdays()` / `void setWeekdays(String[] newWeekdays)`
  - `String[][] getZoneStrings()` / `void setZoneStrings(String[][] newZoneStrings)`
- `static Locale[] getAvailableLocales()`
- 实例：`static DateFormatSymbols getInstance()` / `static DateFormatSymbols getInstance(Locale locale)`
- `int hashCode()`

**SimpleDateFormat**（DateFormat的子类，具体）
格式代表的字母（如Y代表年，M代表月）
- `SimpleDateFormat()` / `SimpleDateFormat(String pattern)` / `SimpleDateFormat(String pattern, DateFormatSymbols formatSymbols)` / `SimpleDateFormat(String pattern, Locale locale)`
- 应用模式：`void applyLocalizedPattern(String pattern)` / `void applyPattern(String pattern)`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化与解析：`StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos)` / `Date parse(String text, ParsePosition pos)`
- `AttributedCharacterIterator formatToCharacterIterator(Object obj)`
- `Date get2DigitYearStart()` / `void set2DigitYearStart(Date startDate)`
- 符号集：`DateFormatSymbols getDateFormatSymbols()` / `void setDateFormatSymbols(DateFormatSymbols newFormatSymbols)`
- `String toLocalizedPattern()` / `String toPattern()`
- `int hashCode()`


**Format**(格式化的抽象基类，子类包括：DateFormat ， MessageFormat ， NumberFormat)

**MessageFormat**(消息格式化)
- `static class MessageFormat.Field`
- `MessageFormat(String pattern)` / `MessageFormat(String pattern, Locale locale)`
- 应用模式：`void applyPattern(String pattern)`
- `Object clone()`
- `boolean equals(Object obj)`
- 格式化：`StringBuffer format(Object[] arguments, StringBuffer result, FieldPosition pos)` / `StringBuffer format(Object arguments, StringBuffer result, FieldPosition pos)` / `static String format(String pattern, Object... arguments)`
- `AttributedCharacterIterator formatToCharacterIterator(Object arguments)`
- 设置格式：`Format[] getFormats()` / `void setFormats(Format[] newFormats)` / `Format[] getFormatsByArgumentIndex()` / `void setFormatsByArgumentIndex(Format[] newFormats)` / `void setFormatByArgumentIndex(int argumentIndex, Format newFormat)` / `void setFormat(int formatElementIndex, Format newFormat)`
- 解析：`Object[] parse(String source)` / `Object[] parse(String source, ParsePosition pos)` / `Object parseObject(String source, ParsePosition pos)`
- `Locale getLocale()` / `void setLocale(Locale locale)`
- `int hashCode()`
- `String toPattern()`

## `java.time`

注意三种DateTime：LocalDateTime、OffsetDateTime、ZonedDateTime 。
示例为：LocalDateTime（2007-12-03T10:15:30）、OffsetDateTime（2007-12-03T10:15:30+01:00）、ZonedDateTime（2007-12-03T10:15:30+01:00 Europe/Paris）。

**Duration**(时间段)
- 实例：`static Duration between(Temporal startInclusive, Temporal endExclusive)` / `static Duration of(long amount, TemporalUnit unit)` / `static Duration ofDays(long days)` / `static Duration ofHours(long hours)` / `static Duration ofMillis(long millis)` / `static Duration ofMinutes(long minutes)` / `static Duration ofNanos(long nanos)` / `static Duration ofSeconds(long seconds)` / `static Duration ofSeconds(long seconds, long nanoAdjustment)` / `static Duration parse(CharSequence text)` / `static Duration from(TemporalAmount amount)`
- 比较：`boolean equals(Object otherDuration)` / `int compareTo(Duration otherDuration)`
- 计算：
  - `Duration minus(Duration duration)` / `Duration plus(Duration duration)`
  - `Duration minus(long amountToSubtract, TemporalUnit unit)` / `Duration plus(long amountToAdd, TemporalUnit unit)`
  - `Duration minusDays(long daysToSubtract)` / `Duration plusDays(long daysToAdd)`
  - `Duration minusHours(long hoursToSubtract)` / `Duration plusHours(long hoursToAdd)`
  - `Duration minusMillis(long millisToSubtract)` / `Duration plusMillis(long millisToAdd)`
  - `Duration minusMinutes(long minutesToSubtract)` / `Duration plusMinutes(long minutesToAdd)`
  - `Duration minusNanos(long nanosToSubtract)` / `Duration plusNanos(long nanosToAdd)`
  - `Duration minusSeconds(long secondsToSubtract)` / `Duration plusSeconds(long secondsToAdd)`
  - `Temporal addTo(Temporal temporal)` / `Temporal subtractFrom(Temporal temporal)`
  - `Duration dividedBy(long divisor)` / `Duration multipliedBy(long multiplicand)`
- 转换：`long toDays()` / `long toHours()` / `long toMillis()` / `long toMinutes()` / `long toNanos()` / `int getNano()` / `long getSeconds()` / `long get(TemporalUnit unit)`
- 副本：`Duration abs()` / `Duration negated()` / `Duration withNanos(int nanoOfSecond)` / `Duration withSeconds(long seconds)`
- 单位：`List<TemporalUnit> getUnits()`
- `int hashCode()`
- `boolean isNegative()` / `boolean isZero()`
- `String toString()`

**Instant**(时间点)
- `static Instant EPOCH`(1970年新世纪) / `static Instant MAX` / `static Instant MIN`
- 实例：`static Instant from(TemporalAccessor temporal)` / `static Instant now()` / `static Instant now(Clock clock)` / `static Instant ofEpochMilli(long epochMilli)` / `static Instant ofEpochSecond(long epochSecond)` / `static Instant ofEpochSecond(long epochSecond, long nanoAdjustment)` / `static Instant parse(CharSequence text)`
- 比较：`int compareTo(Instant otherInstant)` / `boolean equals(Object otherInstant)`
- 计算：
  - `Instant minus(long amountToSubtract, TemporalUnit unit)` / `Instant plus(long amountToAdd, TemporalUnit unit)`
  - `Instant minus(TemporalAmount amountToSubtract)` / `Instant plus(TemporalAmount amountToAdd)`
  - `Instant minusMillis(long millisToSubtract)` / `Instant plusMillis(long millisToAdd)`
  - `Instant minusNanos(long nanosToSubtract)` / `Instant plusNanos(long nanosToAdd)`
  - `Instant minusSeconds(long secondsToSubtract)` / `Instant plusSeconds(long secondsToAdd)`
  - `Temporal adjustInto(Temporal temporal)`
  - `OffsetDateTime atOffset(ZoneOffset offset)`
  - `ZonedDateTime atZone(ZoneId zone)`
  - `Instant truncatedTo(TemporalUnit unit)`
  - `long until(Temporal endExclusive, TemporalUnit unit)`
- 副本：`Instant with(TemporalAdjuster adjuster)` / `Instant with(TemporalField field, long newValue)`
- 测试：`boolean isAfter(Instant otherInstant)` / `boolean isBefore(Instant otherInstant)`
- 转换：`int getNano()` / `int get(TemporalField field)` / `long getEpochSecond()` / `long getLong(TemporalField field)` / `long toEpochMilli()`
- `int hashCode()`
- `boolean isSupported(TemporalField field)` / `boolean isSupported(TemporalUnit unit)`
- `<R> R query(TemporalQuery<R> query)`
- `ValueRange range(TemporalField field)`
- `String toString()`


Clock(抽象)
- `protected	Clock()`
- `boolean equals(Object obj)`
- 实例：`static Clock fixed(Instant fixedInstant, ZoneId zone)` / `static Clock offset(Clock baseClock, Duration offsetDuration)` / `static Clock system(ZoneId zone)` / `static Clock systemDefaultZone()` / `static Clock systemUTC()` / `static Clock tick(Clock baseClock, Duration tickDuration)` / `static Clock tickMinutes(ZoneId zone)` / `static Clock tickSeconds(ZoneId zone)`
- 相关对象：`abstract ZoneId getZone()` / `abstract Instant instant()`
- `long millis()`
- 副本：`abstract Clock withZone(ZoneId zone)`


**LocalDate**
- 实例：`static LocalDate from(TemporalAccessor temporal)` / `static LocalDate now()` / `static LocalDate now(Clock clock)` / `static LocalDate now(ZoneId zone)` / `static LocalDate of(int year, int month, int dayOfMonth)` / `static LocalDate of(int year, Month month, int dayOfMonth)` / `static LocalDate ofEpochDay(long epochDay)` / `static LocalDate ofYearDay(int year, int dayOfYear)` / `static LocalDate parse(CharSequence text)` / `static LocalDate parse(CharSequence text, DateTimeFormatter formatter)`
- 指定时间：`LocalDateTime atStartOfDay()` / `ZonedDateTime atStartOfDay(ZoneId zone)` / `LocalDateTime atTime(int hour, int minute)` / `LocalDateTime atTime(int hour, int minute, int second)` / `LocalDateTime atTime(int hour, int minute, int second, int nanoOfSecond)` / `LocalDateTime atTime(LocalTime time)` / `OffsetDateTime atTime(OffsetTime time)`
- 比较：`int compareTo(ChronoLocalDate other)` / `boolean equals(Object obj)`
- 格式化：`String format(DateTimeFormatter formatter)`
- 字段：`IsoChronology getChronology()` / `int getDayOfMonth()` / `DayOfWeek getDayOfWeek()` / `int getDayOfYear()` / `Era getEra()` / `Month getMonth()` / `int getMonthValue()` / `int getYear()` / `int lengthOfMonth()` / `int lengthOfYear()`
- 判定：`boolean isAfter(ChronoLocalDate other)` / `boolean isBefore(ChronoLocalDate other)` / `boolean isEqual(ChronoLocalDate other)` / `boolean isLeapYear()`
- 运算：
  - `Temporal adjustInto(Temporal temporal)`
  - `LocalDate minus(long amountToSubtract, TemporalUnit unit)` / `LocalDate plus(long amountToAdd, TemporalUnit unit)`
  - `LocalDate minus(TemporalAmount amountToSubtract)` / `LocalDate plus(TemporalAmount amountToAdd)`
  - `LocalDate minusDays(long daysToSubtract)` / `LocalDate plusDays(long daysToAdd)`
  - `LocalDate minusMonths(long monthsToSubtract)` / `LocalDate plusMonths(long monthsToAdd)`
  - `LocalDate minusWeeks(long weeksToSubtract)` / `LocalDate plusWeeks(long weeksToAdd)`
  - `LocalDate minusYears(long yearsToSubtract)` / `LocalDate plusYears(long yearsToAdd)`
- 副本：`LocalDate with(TemporalAdjuster adjuster)` / `LocalDate with(TemporalField field, long newValue)` / `LocalDate withDayOfMonth(int dayOfMonth)` / `LocalDate withDayOfYear(int dayOfYear)` / `LocalDate withMonth(int month)` / `LocalDate withYear(int year)`
- 转换：`int get(TemporalField field)` / `long getLong(TemporalField field)` / `long toEpochDay()` / `Period until(ChronoLocalDate endDateExclusive)` / `long until(Temporal endExclusive, TemporalUnit unit)`
- `boolean isSupported(TemporalField field)` / `boolean isSupported(TemporalUnit unit)`
- `<R> R query(TemporalQuery<R> query)`
- `ValueRange range(TemporalField field)`
- `String toString()`

**LocalDateTime**(差不多LocalDate和LocalTime的合体)

**LocalTime**
- `static LocalTime	MIDNIGHT` / `static LocalTime	NOON`
- 实例：`static LocalTime from(TemporalAccessor temporal)` / `static LocalTime now()` / `static LocalTime now(Clock clock)` / `static LocalTime now(ZoneId zone)` / `static LocalTime of(int hour, int minute)` / `static LocalTime of(int hour, int minute, int second)` / `static LocalTime of(int hour, int minute, int second, int nanoOfSecond)` / `static LocalTime ofNanoOfDay(long nanoOfDay)` / `static LocalTime ofSecondOfDay(long secondOfDay)` / `static LocalTime parse(CharSequence text)` / `static LocalTime parse(CharSequence text, DateTimeFormatter formatter)`
- 字段：`int get(TemporalField field)` / `int getHour()` / `long getLong(TemporalField field)` / `int getMinute()` / `int getNano()` / `int getSecond()` / `int hashCode()` / `long toNanoOfDay()` / `int toSecondOfDay()`
- 比较：`int compareTo(LocalTime other)` / `boolean equals(Object obj)`
- 格式化：`String format(DateTimeFormatter formatter)`
- 判定：`boolean isAfter(LocalTime other)` / `boolean isBefore(LocalTime other)`
- `boolean isSupported(TemporalField field)` / `boolean isSupported(TemporalUnit unit)`
- 运算：
  - `LocalTime minus(long amountToSubtract, TemporalUnit unit)` / `LocalTime plus(long amountToAdd, TemporalUnit unit)`
  - `LocalTime minus(TemporalAmount amountToSubtract)` / `LocalTime plus(TemporalAmount amountToAdd)`
  - `LocalTime minusHours(long hoursToSubtract)` / `LocalTime plusHours(long hoursToAdd)`
  - `LocalTime minusMinutes(long minutesToSubtract)` / `LocalTime plusMinutes(long minutesToAdd)`
  - `LocalTime minusNanos(long nanosToSubtract)` / `LocalTime plusNanos(long nanosToAdd)`
  - `LocalTime minusSeconds(long secondsToSubtract)` / `LocalTime plusSeconds(long secondstoAdd)`
  - `Temporal adjustInto(Temporal temporal)`
  - `LocalDateTime atDate(LocalDate date)`
  - `OffsetTime atOffset(ZoneOffset offset)`
  - `<R> R query(TemporalQuery<R> query)`
  - `ValueRange range(TemporalField field)`
  - `LocalTime truncatedTo(TemporalUnit unit)`
  - `long until(Temporal endExclusive, TemporalUnit unit)`
- 副本：`LocalTime with(TemporalAdjuster adjuster)` / `LocalTime with(TemporalField field, long newValue)` / `LocalTime withHour(int hour)` / `LocalTime withMinute(int minute)` / `LocalTime withNano(int nanoOfSecond)` / `LocalTime withSecond(int second)`
- `String toString()`


OffsetDateTime(UTC时间日期)
OffsetTime(UTC时间)

Year  
YearMonth  
MonthDay  

ZoneId(时区，抽象)
- `boolean equals(Object obj)`
- 实例：`static ZoneId from(TemporalAccessor temporal)` / `static ZoneId of(String zoneId)` / `static ZoneId of(String zoneId, Map<String,String> aliasMap)` / `static ZoneId ofOffset(String prefix, ZoneOffset offset)` / `static ZoneId systemDefault()`
- `static Set<String> getAvailableZoneIds()`
- `String getDisplayName(TextStyle style, Locale locale)`
- `abstract String getId()`
- `abstract ZoneRules getRules()`
- `int hashCode()`
- `ZoneId normalized()`
- `String toString()`

ZoneOffset(ZoneId的子类，相对UTC的偏移)
- `static ZoneOffset	MAX` / `static ZoneOffset	MIN` / `static ZoneOffset	UTC`(Z)
- `Temporal adjustInto(Temporal temporal)`
- 比较：`int compareTo(ZoneOffset other)` / `boolean equals(Object obj)`
- 实例：`static ZoneOffset from(TemporalAccessor temporal)` / `static ZoneOffset of(String offsetId)` / `static ZoneOffset ofHours(int hours)` / `static ZoneOffset ofHoursMinutes(int hours, int minutes)` / `static ZoneOffset ofHoursMinutesSeconds(int hours, int minutes, int seconds)` / `static ZoneOffset ofTotalSeconds(int totalSeconds)`
- `int get(TemporalField field)`
- `String getId()`
- `long getLong(TemporalField field)`
- `ZoneRules getRules()`
- `int getTotalSeconds()`
- `int hashCode()`
- `boolean isSupported(TemporalField field)`
- `<R> R query(TemporalQuery<R> query)`
- `ValueRange range(TemporalField field)`
- `String toString()`

## `java.time.format`

**DateTimeFormatter**
- 预定义的格式：`DateTimeFormatter.ofLocalizedDate(dateStyle)`（'2011-12-03'）、`DateTimeFormatter.ofLocalizedTime(timeStyle)`（'10:15:30'）、`DateTimeFormatter.ISO_LOCAL_DATE`（'2011-12-03'）、`DateTimeFormatter.ISO_LOCAL_TIME`（'10:15:30'）、`DateTimeFormatter.ISO_LOCAL_DATE_TIME`（'2011-12-03T10:15:30'）、`DateTimeFormatter.ISO_INSTANT`（'2011-12-03T10:15:30Z'）
- 匹配模式参见 SimpleDateFormat
- 实例：`String format(TemporalAccessor temporal)` / `static DateTimeFormatter ofLocalizedDate(FormatStyle dateStyle)` / `static DateTimeFormatter ofLocalizedDateTime(FormatStyle dateTimeStyle)` / `static DateTimeFormatter ofLocalizedDateTime(FormatStyle dateStyle, FormatStyle timeStyle)` / `static DateTimeFormatter ofLocalizedTime(FormatStyle timeStyle)` / `static DateTimeFormatter ofPattern(String pattern)` / `static DateTimeFormatter ofPattern(String pattern, Locale locale)`
- 字段：`Chronology getChronology()` / `DecimalStyle getDecimalStyle()` / `Locale getLocale()` / `Set<TemporalField> getResolverFields()` / `ResolverStyle getResolverStyle()` / `ZoneId getZone()`
- 解析：`TemporalAccessor parse(CharSequence text)` / `TemporalAccessor parse(CharSequence text, ParsePosition position)` / `<T> T parse(CharSequence text, TemporalQuery<T> query)` / `TemporalAccessor parseBest(CharSequence text, TemporalQuery<?>... queries)` / `static TemporalQuery<Period> parsedExcessDays()` / `static TemporalQuery<Boolean> parsedLeapSecond()` / `TemporalAccessor parseUnresolved(CharSequence text, ParsePosition position)`
- `void formatTo(TemporalAccessor temporal, Appendable appendable)`
- `Format toFormat()` / `Format toFormat(TemporalQuery<?> parseQuery)`
- `String toString()`
- 副本：`DateTimeFormatter withChronology(Chronology chrono)` / `DateTimeFormatter withDecimalStyle(DecimalStyle decimalStyle)` / `DateTimeFormatter withLocale(Locale locale)` / `DateTimeFormatter withResolverFields(Set<TemporalField> resolverFields)` / `DateTimeFormatter withResolverFields(TemporalField... resolverFields)` / `DateTimeFormatter withResolverStyle(ResolverStyle resolverStyle)` / `DateTimeFormatter withZone(ZoneId zone)`

## `java.util`

抽象类：`AbstractCollection<E>`， `AbstractList<E>`， `AbstractMap<K,V>`， `AbstractMap.SimpleEntry<K,V>`， `AbstractMap.SimpleImmutableEntry<K,V>`， `AbstractQueue<E>`， `AbstractSequentialList<E>`， `AbstractSet<E>` 。

`ArrayDeque<E>`
- 操作：
  - `boolean add(E e)` / `void addFirst(E e)` / `void addLast(E e)`
  - `boolean offer(E e)` / `boolean offerFirst(E e)` / `boolean offerLast(E e)`
  - `E poll()` / `E pollFirst()` / `E pollLast()`
  - `E remove()` / `boolean remove(Object o)` / `E removeFirst()` / `boolean removeFirstOccurrence(Object o)` / `E removeLast()` / `boolean removeLastOccurrence(Object o)`
  - `E pop()` / `void push(E e)`
  - `void clear()`
- `ArrayDeque<E> clone()`
- 元素：
  - `boolean contains(Object o)`
  - `E element()` / `E getFirst()` / `E getLast()`
  - `E peek()` / `E peekFirst()` / `E peekLast()`
- `Iterator<E> descendingIterator()`
- 判定：`boolean isEmpty()`
- `Iterator<E> iterator()` / `Spliterator<E> spliterator()`
- `int size()`
- 转换：`Object[] toArray()` / `<T> T[] toArray(T[] a)`

**`ArrayList<E>`**
- `ArrayList()` / `ArrayList(Collection<? extends E> c)` / `ArrayList(int initialCapacity)`
- 操作：
  - `boolean add(E e)` / `void add(int index, E element)`
  - `boolean addAll(Collection<? extends E> c)` / `boolean addAll(int index, Collection<? extends E> c)`
  - `E remove(int index)` / `boolean remove(Object o)`
  - `boolean removeAll(Collection<?> c)` / `boolean removeIf(Predicate<? super E> filter)` / `protected void removeRange(int fromIndex, int toIndex)`
  - `void replaceAll(UnaryOperator<E> operator)`
  - `boolean retainAll(Collection<?> c)`
  - `void clear()`
  - `void sort(Comparator<? super E> c)`
- `Spliterator<E> spliterator()`
- `Object clone()`
- 元素：
  - `boolean contains(Object o)`
  - `E get(int index)` / `E set(int index, E element)`
  - `int indexOf(Object o)` / `int lastIndexOf(Object o)`
  - `boolean isEmpty()`
  - `List<E> subList(int fromIndex, int toIndex)`
- 容量：`void ensureCapacity(int minCapacity)` / `int size()` / `void trimToSize()`
- 遍历：
  - `void forEach(Consumer<? super E> action)`
  - `Iterator<E> iterator()` / `ListIterator<E> listIterator()` / `ListIterator<E> listIterator(int index)`
- 转换：`Object[] toArray()` / `<T> T[] toArray(T[] a)`

**Collections**（静态方法,List/Map/Set）
- `static List	EMPTY_LIST` / `static Map	EMPTY_MAP` / `static Set	EMPTY_SET`
- 搜索：`static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key)` / `static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c)`
- Checked：`static <E> Collection<E> checkedCollection(Collection<E> c, Class<E> type)` / `static <E> List<E> checkedList(List<E> list, Class<E> type)` / `static <K,V> Map<K,V> checkedMap(Map<K,V> m, Class<K> keyType, Class<V> valueType)` / `static <E> Queue<E> checkedQueue(Queue<E> queue, Class<E> type)` / `static <E> Set<E> checkedSet(Set<E> s, Class<E> type)` / `static <K,V> SortedMap<K,V> checkedSortedMap(SortedMap<K,V> m, Class<K> keyType, Class<V> valueType)` / `static <E> SortedSet<E> checkedSortedSet(SortedSet<E> s, Class<E> type)`
- synchronized: `static <T> Collection<T> synchronizedCollection(Collection<T> c)` / `static <T> List<T> synchronizedList(List<T> list)` / `static <K,V> Map<K,V> synchronizedMap(Map<K,V> m)` / `static <T> Set<T> synchronizedSet(Set<T> s)` / `static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m)` / `static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s)`
- unmodifiable： `static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c)` / `static <T> List<T> unmodifiableList(List<? extends T> list)` / `static <K,V> Map<K,V> unmodifiableMap(Map<? extends K,? extends V> m)` / `static <T> Set<T> unmodifiableSet(Set<? extends T> s)` / `static <K,V> SortedMap<K,V> unmodifiableSortedMap(SortedMap<K,? extends V> m)` / `static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s)`
- singleton： `static <T> Set<T> singleton(T o)` / `static <T> List<T> singletonList(T o)` / `static <K,V> Map<K,V> singletonMap(K key, V value)`
- 空：`static <T> Enumeration<T> emptyEnumeration()` / `static <T> Iterator<T> emptyIterator()` / `static <T> List<T> emptyList()` / `static <T> ListIterator<T> emptyListIterator()` / `static <K,V> Map<K,V> emptyMap()` / `static <T> Set<T> emptySet()` / `static <K,V> SortedMap<K,V> emptySortedMap()` / `static <E> SortedSet<E> emptySortedSet()`
- 操作：
  - 复制：`static <T> void copy(List<? super T> dest, List<? extends T> src)` / `static <T> boolean addAll(Collection<? super T> c, T... elements)` / `static <T> List<T> nCopies(int n, T o)`
  - 填充：`static <T> void fill(List<? super T> list, T obj)`
  - 替换：`static <T> boolean replaceAll(List<T> list, T oldVal, T newVal)`
  - 反转：`static void reverse(List<?> list)`
  - 翻转：`static void rotate(List<?> list, int distance)`
  - 乱序：`static void shuffle(List<?> list)` / `static void shuffle(List<?> list, Random rnd)`
  - 排序：`static <T extends Comparable<? super T>>void sort(List<T> list)` / `static <T> void sort(List<T> list, Comparator<? super T> c)`
  - 判定：`static boolean disjoint(Collection<?> c1, Collection<?> c2)`
- 转换：`static <T> Enumeration<T> enumeration(Collection<T> c)` / `static <T> Queue<T> asLifoQueue(Deque<T> deque)` / `static <T> ArrayList<T> list(Enumeration<T> e)` / `static <E> Set<E> newSetFromMap(Map<E,Boolean> map)`
- 元素：
  - 子序列：`static int indexOfSubList(List<?> source, List<?> target)` / `static int lastIndexOfSubList(List<?> source, List<?> target)`
  - 统计：`static int frequency(Collection<?> c, Object o)`
  - 大小：`static <T extends Object & Comparable<? super T>>T max(Collection<? extends T> coll)` / `static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp)` / `static <T extends Object & Comparable<? super T>>T min(Collection<? extends T> coll)` / `static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp)`
  - `static <T> Comparator<T> reverseOrder()` / `static <T> Comparator<T> reverseOrder(Comparator<T> cmp)`
  - 交换：`static void swap(List<?> list, int i, int j)`

**Arrays**（静态方法，很多重载方法）
- `static <T> List<T>	asList(T... a)`
- binarySearch()
- copyOf(), copyOfRange()
- deepEquals(), equals()
- fill()
- parallelPrefix(), parallelSetAll(), parallelSort()
- setAll()
- sort()
- stream()
- toString()

base64编解码：Base64， Base64.Decoder， Base64.Encoder

Currency（货币）
- 实例：`static Set<Currency> getAvailableCurrencies()` / `static Currency getInstance(Locale locale)` / `static Currency getInstance(String currencyCode)`
- `String getCurrencyCode()` / `String getDisplayName()` / `String getDisplayName(Locale locale)` / `int getDefaultFractionDigits()` / `int getNumericCode()`
- `String getSymbol()` / `String getSymbol(Locale locale)`
- `String toString()`

DoubleSummaryStatistics(统计)
- `DoubleSummaryStatistics()`
- `void accept(double value)`
- `void combine(DoubleSummaryStatistics other)`
- 结果：`double getAverage()` / `long getCount()` / `double getMax()` / `double getMin()` / `double getSum()`
- `String toString()`

IntSummaryStatistics(统计)
- `IntSummaryStatistics()`
- `void accept(int value)`
- `void combine(IntSummaryStatistics other)`
- 结果：`double getAverage()` / `long getCount()` / `int getMax()` / `int getMin()` / `long getSum()`
- `String toString()`

LongSummaryStatistics(统计)
- `LongSummaryStatistics()`
- `void accept(int value)`
- `void accept(long value)`
- `void combine(LongSummaryStatistics other)`
- 结果：`double getAverage()` / `long getCount()` / `long getMax()` / `long getMin()` / `long getSum()`
- `String toString()`


Formatter（用于printf的格式化）

`EnumMap<K extends Enum<K>,V>`
- `EnumMap(Class<K> keyType)` / `EnumMap(EnumMap<K,? extends V> m)` / `EnumMap(Map<K,? extends V> m)`
- `void clear()`
- `EnumMap<K,V> clone()`
- 元素：
  - `boolean containsKey(Object key)` / `boolean containsValue(Object value)`
  - `V get(Object key)` / `V put(K key, V value)`
  - `Set<Map.Entry<K,V>> entrySet()`
  - `void putAll(Map<? extends K,? extends V> m)`
  - `V remove(Object key)`
- `boolean equals(Object o)`
- `Set<K> keySet()` / `Collection<V> values()`
- `int size()`

`EnumSet<E extends Enum<E>>`
- `EnumSet<E> clone()`
- `static <E extends Enum<E>>EnumSet<E> allOf(Class<E> elementType)` / `static <E extends Enum<E>>EnumSet<E> complementOf(EnumSet<E> s)` / `static <E extends Enum<E>>EnumSet<E> copyOf(Collection<E> c)` / `static <E extends Enum<E>>EnumSet<E> copyOf(EnumSet<E> s)` / `static <E extends Enum<E>>EnumSet<E> noneOf(Class<E> elementType)`
- `static <E extends Enum<E>>EnumSet<E> of(E e)` / `static <E extends Enum<E>>EnumSet<E> of(E first, E... rest)` / `static <E extends Enum<E>>EnumSet<E> of(E e1, E e2)` / `static <E extends Enum<E>>EnumSet<E> of(E e1, E e2, E e3)` / `static <E extends Enum<E>>EnumSet<E> of(E e1, E e2, E e3, E e4)` / `static <E extends Enum<E>>EnumSet<E> of(E e1, E e2, E e3, E e4, E e5)`
- `static <E extends Enum<E>>EnumSet<E> range(E from, E to)`

**`HashMap<K,V>`**
- `HashMap()` / `HashMap(int initialCapacity)` / `HashMap(int initialCapacity, float loadFactor)` / `HashMap(Map<? extends K,? extends V> m)`
- `void clear()`
- `Object clone()`
- `V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)` / `V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)` / `V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)`
- `Set<Map.Entry<K,V>> entrySet()` / `Set<K> keySet()` / `Collection<V> values()`
- `void forEach(BiConsumer<? super K,? super V> action)`
- `boolean isEmpty()`
- 元素
  - `boolean containsKey(Object key)` / `boolean containsValue(Object value)`
  - `V put(K key, V value)` / `V putIfAbsent(K key, V value)`
  - `V get(Object key)` / `V getOrDefault(Object key, V defaultValue)`
  - `void putAll(Map<? extends K,? extends V> m)`
  - `V remove(Object key)` / `boolean remove(Object key, Object value)`
  - `V replace(K key, V value)` / `boolean replace(K key, V oldValue, V newValue)`
  - `void replaceAll(BiFunction<? super K,? super V,? extends V> function)`
  - `V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction)`
- `int size()`

**`HashSet<E>`** / `LinkedHashSet<E>`(有序的HashSet)
- `HashSet()` / `HashSet(Collection<? extends E> c)` / `HashSet(int initialCapacity)` / `HashSet(int initialCapacity, float loadFactor)`
- 元素：
  - `boolean add(E e)` / `boolean remove(Object o)`
  - `boolean contains(Object o)`
- `void clear()`
- `Object clone()`
- `boolean isEmpty()`
- `Iterator<E> iterator()`
- `int size()`

`LinkedHashMap<K,V>`(有序的HashMap)
- `LinkedHashMap()` / `LinkedHashMap(int initialCapacity)` / `LinkedHashMap(int initialCapacity, float loadFactor)` / `LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)` / `LinkedHashMap(Map<? extends K,? extends V> m)`
- `void clear()`
- `boolean containsValue(Object value)`
- `Set<Map.Entry<K,V>> entrySet()` / `Set<K> keySet()` / `Collection<V> values()`
- `void forEach(BiConsumer<? super K,? super V> action)`
- `V get(Object key)` / `V getOrDefault(Object key, V defaultValue)`
- `protected boolean removeEldestEntry(Map.Entry<K,V> eldest)`
- `void replaceAll(BiFunction<? super K,? super V,? extends V> function)`

`LinkedList<E>`(双链表)
- `LinkedList()` / `LinkedList(Collection<? extends E> c)`
- 元素：
  - `boolean add(E e)` / `void add(int index, E element)`
  - `boolean addAll(Collection<? extends E> c)` / `boolean addAll(int index, Collection<? extends E> c)`
  - `void addFirst(E e)` / `void addLast(E e)`
  - `void clear()`
  - `boolean contains(Object o)`
  - `E get(int index)` / `E getFirst()` / `E getLast()`
  - `E element()`
  - `int indexOf(Object o)` / `int lastIndexOf(Object o)`
  - `boolean offer(E e)` / `boolean offerFirst(E e)` / `boolean offerLast(E e)`
  - `E peek()` / `E peekFirst()` / `E peekLast()`
  - `E poll()` / `E pollFirst()` / `E pollLast()`
   - `E pop()` / `void push(E e)`
   - `E remove()` / `E remove(int index)` / `boolean remove(Object o)`
   - `E removeFirst()` / `boolean removeFirstOccurrence(Object o)`
   - `E removeLast()` / `boolean removeLastOccurrence(Object o)`
   - `E set(int index, E element)`
- `Object clone()`
- `Iterator<E> descendingIterator()` / `ListIterator<E> listIterator(int index)`
- `int size()`
- `Object[] toArray()` / `<T> T[] toArray(T[] a)`

`TreeMap<K,V>`(红黑树)
- `TreeMap()` / `TreeMap(Comparator<? super K> comparator)` / `TreeMap(Map<? extends K,? extends V> m)` / `TreeMap(SortedMap<K,? extends V> m)`
- 元素：
  - `V get(Object key)` / `V put(K key, V value)`
  - `void putAll(Map<? extends K,? extends V> map)`
  - `V remove(Object key)`
  - `V replace(K key, V value)` / `boolean replace(K key, V oldValue, V newValue)`
- 键值：
  - `Map.Entry<K,V> ceilingEntry(K key)` / `K ceilingKey(K key)` / `Map.Entry<K,V> floorEntry(K key)` / `K floorKey(K key)`
  - `Map.Entry<K,V> firstEntry()` / `K firstKey()` / `Map.Entry<K,V> lastEntry()` / `K lastKey()`
  - `boolean containsKey(Object key)` / `boolean containsValue(Object value)`
  - `Map.Entry<K,V> higherEntry(K key)` / `K higherKey(K key)` / `Map.Entry<K,V> lowerEntry(K key)` / `K lowerKey(K key)`
  - `Map.Entry<K,V> pollFirstEntry()` / `Map.Entry<K,V> pollLastEntry()`
  - `NavigableSet<K> navigableKeySet()`
- 子树：
  - `SortedMap<K,V> headMap(K toKey)` / `NavigableMap<K,V> headMap(K toKey, boolean inclusive)` / `SortedMap<K,V> tailMap(K fromKey)` / `NavigableMap<K,V> tailMap(K fromKey, boolean inclusive)`
  - `NavigableSet<K> descendingKeySet()` / `NavigableMap<K,V> descendingMap()`
  - `NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)` / `SortedMap<K,V> subMap(K fromKey, K toKey)`
- `void forEach(BiConsumer<? super K,? super V> action)` / `void replaceAll(BiFunction<? super K,? super V,? extends V> function)`
- `Collection<V> values()` / `Set<K> keySet()` / `Set<Map.Entry<K,V>> entrySet()`
- `void clear()`
- `Object clone()`
- `Comparator<? super K> comparator()`
- `int size()`

`TreeSet<E>`(基于TreeMap)
- `TreeSet()` / `TreeSet(Collection<? extends E> c)` / `TreeSet(Comparator<? super E> comparator)` / `TreeSet(SortedSet<E> s)`
- `boolean addAll(Collection<? extends E> c)`
- 元素：
  - `boolean add(E e)` / `boolean remove(Object o)`
  - `boolean contains(Object o)`
  - `E first()` / `E last()`
  - `E ceiling(E e)` / `E floor(E e)`
  - `E higher(E e)` / `E lower(E e)`
  - `E pollFirst()` / `E pollLast()`
- 子集：
  - `NavigableSet<E> descendingSet()`
  - `SortedSet<E> headSet(E toElement)` / `NavigableSet<E> headSet(E toElement, boolean inclusive)`
  - `SortedSet<E> tailSet(E fromElement)` / `NavigableSet<E> tailSet(E fromElement, boolean inclusive)`
  - `NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)` / `SortedSet<E> subSet(E fromElement, E toElement)`
- `void clear()`
- `Object clone()`
- `Comparator<? super E> comparator()`
- `boolean isEmpty()`
- `Iterator<E> descendingIterator()` / `Iterator<E> iterator()` / `Spliterator<E> spliterator()`
- `int size()`

`Vector<E>`(可扩展数组)
- `protected int	capacityIncrement` / `protected int	elementCount` / `protected Object[]	elementData`
- `Vector()` / `Vector(Collection<? extends E> c)` / `Vector(int initialCapacity)` / `Vector(int initialCapacity, int capacityIncrement)`
- 元素：
  - `boolean add(E e)` / `void add(int index, E element)`
  - `void addElement(E obj)` / `boolean removeElement(Object obj)`
  - `E elementAt(int index)`
  - `boolean contains(Object o)`
  - `Enumeration<E> elements()`
  - `E firstElement()` / `E lastElement()`
  - `int indexOf(Object o)` / `int indexOf(Object o, int index)` / `int lastIndexOf(Object o)` / `int lastIndexOf(Object o, int index)`
  - `E get(int index)`
  - `E remove(int index)` / `boolean remove(Object o)`
  - `void insertElementAt(E obj, int index)` / `void removeElementAt(int index)`
  - `boolean removeIf(Predicate<? super E> filter)`
  - `void setElementAt(E obj, int index)`
  - `E set(int index, E element)`
- 数组：
  - `boolean addAll(Collection<? extends E> c)` / `boolean addAll(int index, Collection<? extends E> c)`
  - `boolean removeAll(Collection<?> c)`
  - `void removeAllElements()`
  - `boolean containsAll(Collection<?> c)`
  - `void copyInto(Object[] anArray)`
  - `boolean isEmpty()`
  - `void clear()`
  - `protected void removeRange(int fromIndex, int toIndex)`
  - `Object clone()`
  - `void replaceAll(UnaryOperator<E> operator)`
  - `boolean retainAll(Collection<?> c)`
  - `void sort(Comparator<? super E> c)`
  - `List<E> subList(int fromIndex, int toIndex)`
- 容量：`int capacity()` / `void ensureCapacity(int minCapacity)` / `void setSize(int newSize)` / `int size()` / `void trimToSize()`
- `boolean equals(Object o)`
- 遍历：`Iterator<E> iterator()` / `ListIterator<E> listIterator()` / `ListIterator<E> listIterator(int index)` / `void forEach(Consumer<? super E> action)`
- `Object[] toArray()` / `<T> T[] toArray(T[] a)`
- `String toString()`

`Stack<E>`(栈)
- `Stack()`
- `boolean empty()`
- `E peek()` / `E pop()` / `E push(E item)`
- `int search(Object o)`

ListResourceBundle(抽象类，ResourceBundle管理器)
- `ListResourceBundle()`
- `protected abstract Object[][]	getContents()` / `Enumeration<String>	getKeys()`
- `Object	handleGetObject(String key)`
- `protected Set<String>	handleKeySet()`

ResourceBundle（应该是用于国际化）
- `static class ResourceBundle.Control`
- `protected ResourceBundle parent`
- `ResourceBundle()`
- 实例：`static ResourceBundle getBundle(String baseName)` / `static ResourceBundle getBundle(String baseName, Locale locale)` / `static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader)` / `static ResourceBundle getBundle(String baseName, Locale targetLocale, ClassLoader loader, ResourceBundle.Control control)` / `static ResourceBundle getBundle(String baseName, Locale targetLocale, ResourceBundle.Control control)` / `static ResourceBundle getBundle(String baseName, ResourceBundle.Control control)`
- `String getBaseBundleName()`
- `Locale getLocale()`
- 元素：
  - `boolean containsKey(String key)`
  - `abstract Enumeration<String> getKeys()`
  - `Object getObject(String key)` / `protected abstract Object handleGetObject(String key)`
  - `String getString(String key)`
- `static void clearCache()` / `static void clearCache(ClassLoader loader)`
- `Set<String> keySet()` / `protected Set<String> handleKeySet()`
- `String[] getStringArray(String key)`
- `protected void setParent(ResourceBundle parent)`

ResourceBundle.Control（ResourceBundle.getBundle工厂方法调用的一系列回调方法）
- `static List<String>	FORMAT_CLASS` / `static List<String>	FORMAT_DEFAULT` / `static List<String>	FORMAT_PROPERTIES` / `static long	TTL_DONT_CACHE` / `static long	TTL_NO_EXPIRATION_CONTROL`
- `protected	Control()`
- 实例：`static ResourceBundle.Control getControl(List<String> formats)` / `static ResourceBundle.Control getNoFallbackControl(List<String> formats)`
- `List<Locale> getCandidateLocales(String baseName, Locale locale)`
- `Locale getFallbackLocale(String baseName, Locale locale)`
- `List<String> getFormats(String baseName)`
- `long getTimeToLive(String baseName, Locale locale)`
- `boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime)`
- `ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)`
- `String toBundleName(String baseName, Locale locale)`
- `String toResourceName(String bundleName, String suffix)`

**Locale**(地理区域)
- `static class 	Locale.Builder` / `static class 	Locale.Category`(地区)) / `static class 	Locale.FilteringMode` / `static class 	Locale.LanguageRange`(语言)
- 很多枚举：`static Locale	CHINA` / `static Locale	CHINESE` / `static Locale	PRC` / `static Locale	SIMPLIFIED_CHINESE` / `static Locale	TRADITIONAL_CHINESE` / `static Locale	ENGLISH` / `static Locale	UK` / `static Locale	US` / `static char	PRIVATE_USE_EXTENSION`(私人扩展)
- `Locale(String language)` / `Locale(String language, String country)` / `Locale(String language, String country, String variant)`
- 实例：
  - `static Locale forLanguageTag(String languageTag)`
  - `static Locale[] getAvailableLocales()`
  - `static Locale getDefault()` / `static Locale getDefault(Locale.Category category)`
  - `static Locale lookup(List<Locale.LanguageRange> priorityList, Collection<Locale> locales)`
  - `static void setDefault(Locale.Category category, Locale newLocale)`
  - `static void setDefault(Locale newLocale)`
- `Object clone()`
- `boolean equals(Object obj)`
- `static List<Locale> filter(List<Locale.LanguageRange> priorityList, Collection<Locale> locales)` / `static List<Locale> filter(List<Locale.LanguageRange> priorityList, Collection<Locale> locales, Locale.FilteringMode mode)`
- `static List<String> filterTags(List<Locale.LanguageRange> priorityList, Collection<String> tags)` / `static List<String> filterTags(List<Locale.LanguageRange> priorityList, Collection<String> tags, Locale.FilteringMode mode)`
- 显示：
  - `String getCountry()`
  - `String getLanguage()`
  - `String getScript()`
  - `String getVariant()`
  - `String getDisplayCountry()` / `String getDisplayCountry(Locale inLocale)`
  - `String getDisplayLanguage()` / `String getDisplayLanguage(Locale inLocale)`
  - `String getDisplayName()` / `String getDisplayName(Locale inLocale)`
  - `String getDisplayScript()` / `String getDisplayScript(Locale inLocale)`
  - `String getDisplayVariant()` / `String getDisplayVariant(Locale inLocale)`
- `boolean hasExtensions()` / `String getExtension(char key)` / `Set<Character> getExtensionKeys()`
- `String getISO3Country()`
- `String getISO3Language()`
- `static String[] getISOCountries()`
- `static String[] getISOLanguages()`
- `Set<String> getUnicodeLocaleAttributes()` / `Set<String> getUnicodeLocaleKeys()` / `String getUnicodeLocaleType(String key)`
- `static String lookupTag(List<Locale.LanguageRange> priorityList, Collection<String> tags)`
- `Locale stripExtensions()`
- `String toLanguageTag()`
- `String toString()`

Locale.Builder（构建Locale）
- `Builder()`
- `Locale build()`构建
- `Locale.Builder addUnicodeLocaleAttribute(String attribute)`
- 设置
  - `Locale.Builder setExtension(char key, String value)` / `Locale.Builder clearExtensions()`
  - `Locale.Builder setLanguage(String language)`
  - `Locale.Builder setLanguageTag(String languageTag)`
  - `Locale.Builder setLocale(Locale locale)`
  - `Locale.Builder setRegion(String region)`
  - `Locale.Builder setScript(String script)`
  - `Locale.Builder setUnicodeLocaleKeyword(String key, String type)` / `Locale.Builder removeUnicodeLocaleAttribute(String attribute)`
  - `Locale.Builder setVariant(String variant)`
  - `Locale.Builder clear()`

Locale.LanguageRange（语言范围）
- `LanguageRange(String range)` / `LanguageRange(String range, double weight)`
- `boolean equals(Object obj)`
- `String getRange()` / `double getWeight()`
- `static List<Locale.LanguageRange> mapEquivalents(List<Locale.LanguageRange> priorityList, Map<String,List<String>> map)`
- `static List<Locale.LanguageRange> parse(String ranges)` / `static List<Locale.LanguageRange> parse(String ranges, Map<String,List<String>> map)`


**Objects**(对象静态方法)
- `static <T> int compare(T a, T b, Comparator<? super T> c)`
- `static boolean deepEquals(Object a, Object b)` / `static boolean equals(Object a, Object b)`
- `static int hash(Object... values)`
- `static int hashCode(Object o)`
- `static boolean isNull(Object obj)` / `static boolean nonNull(Object obj)`
- `static <T> T requireNonNull(T obj)` / `static <T> T requireNonNull(T obj, String message)` / `static <T> T requireNonNull(T obj, Supplier<String> messageSupplier)`
- `static String toString(Object o)` / `static String toString(Object o, String nullDefault)`

Observable(观察者，这是直接实现设计模式了吗？)
- `Observable()`
- `void addObserver(Observer o)` / `void deleteObserver(Observer o)` / `void deleteObservers()` / `int countObservers()`
- `boolean hasChanged()` / `protected void setChanged()` / `protected void clearChanged()`
- `void notifyObservers()` / `void notifyObservers(Object arg)`


`Optional<T>`(可能或不含有null的容器)  
OptionalDouble(可能或不含有double的容器)  
OptionalInt(可能或不含有int的容器)  
OptionalLong(可能或不含有long的容器)

`PriorityQueue<E>`
- `PriorityQueue()` / `PriorityQueue(Collection<? extends E> c)` / `PriorityQueue(Comparator<? super E> comparator)` / `PriorityQueue(int initialCapacity)` / `PriorityQueue(int initialCapacity, Comparator<? super E> comparator)` / `PriorityQueue(PriorityQueue<? extends E> c)` / `PriorityQueue(SortedSet<? extends E> c)`
- 元素：
  - `boolean add(E e)` / `boolean remove(Object o)`
  - `void clear()`
  - `boolean contains(Object o)`
  - `boolean offer(E e)`
  - `E peek()` / `E poll()`
- `Comparator<? super E> comparator()` / `Iterator<E> iterator()`
- `int size()`
- `Object[] toArray()` / `<T> T[] toArray(T[] a)`

Properties(字符串键值对)
- `protected Properties	default`
- `Properties()` / `Properties(Properties defaults)`
- `String getProperty(String key)` / `String getProperty(String key, String defaultValue)` / `Object setProperty(String key, String value)`
- `void list(PrintStream out)` / `void list(PrintWriter out)`
- `void load(InputStream inStream)` / `void load(Reader reader)`
- `void loadFromXML(InputStream in)` / `void storeToXML(OutputStream os, String comment)` / `void storeToXML(OutputStream os, String comment, String encoding)`
- `Enumeration<?> propertyNames()`
- `void save(OutputStream out, String comments)`
- `void store(OutputStream out, String comments)`
- `void store(Writer writer, String comments)`
- `Set<String> stringPropertyNames()`

PropertyResourceBundle(用于ResourceBundle的属性，只需指定资源文件)
- `PropertyResourceBundle(InputStream stream)` / `PropertyResourceBundle(Reader reader)`
- `Enumeration<String>	getKeys()` / `Object	handleGetObject(String key)` / `protected Set<String>	handleKeySet()`

Random
- `Random()` / `Random(long seed)`
- `DoubleStream doubles()` / `DoubleStream doubles(double randomNumberOrigin, double randomNumberBound)` / `DoubleStream doubles(long streamSize)` / `DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound)`
- `IntStream ints()` / `IntStream ints(int randomNumberOrigin, int randomNumberBound)` / `IntStream ints(long streamSize)` / `IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound)`
- `LongStream longs()` / `LongStream longs(long streamSize)` / `LongStream longs(long randomNumberOrigin, long randomNumberBound)` / `LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound)`
- `protected int next(int bits)` / `boolean nextBoolean()` / `void nextBytes(byte[] bytes)` / `double nextDouble()` / `float nextFloat()` / `double nextGaussian()` / `int nextInt()` / `int nextInt(int bound)` / `long nextLong()`
- `void setSeed(long seed)`


Scanner(文本扫描器)
- `Scanner(File source)` / `Scanner(File source, String charsetName)` / `Scanner(InputStream source)` / `Scanner(InputStream source, String charsetName)` / `Scanner(Path source)` / `Scanner(Path source, String charsetName)` / `Scanner(Readable source)` / `Scanner(ReadableByteChannel source)` / `Scanner(ReadableByteChannel source, String charsetName)` / `Scanner(String source)`
- `void close()`
- 分隔符：`Pattern delimiter()` / `Scanner useDelimiter(Pattern pattern)` / `Scanner useDelimiter(String pattern)`
- `String findInLine(Pattern pattern)` / `String findInLine(String pattern)`
- `String findWithinHorizon(Pattern pattern, int horizon)` / `String findWithinHorizon(String pattern, int horizon)`
- 是否有下一个：`boolean hasNext()` / `boolean hasNext(Pattern pattern)` / `boolean hasNext(String pattern)` / `boolean hasNextBigDecimal()` / `boolean hasNextBigInteger()` / `boolean hasNextBigInteger(int radix)` / `boolean hasNextBoolean()` / `boolean hasNextByte()` / `boolean hasNextByte(int radix)` / `boolean hasNextDouble()` / `boolean hasNextFloat()` / `boolean hasNextInt()` / `boolean hasNextInt(int radix)` / `boolean hasNextLine()` / `boolean hasNextLong()` / `boolean hasNextLong(int radix)` / `boolean hasNextShort()` / `boolean hasNextShort(int radix)`
- `IOException ioException()`
- `Locale locale()` / `Scanner useLocale(Locale locale)`
- `MatchResult match()`
- 下一个：`String next()` / `String next(Pattern pattern)` / `String next(String pattern)` / `BigDecimal nextBigDecimal()` / `BigInteger nextBigInteger()` / `BigInteger nextBigInteger(int radix)` / `boolean nextBoolean()` / `byte nextByte()` / `byte nextByte(int radix)` / `double nextDouble()` / `float nextFloat()` / `int nextInt()` / `int nextInt(int radix)` / `String nextLine()` / `long nextLong()` / `long nextLong(int radix)` / `short nextShort()` / `short nextShort(int radix)`
- 基数：`int radix()` / `Scanner useRadix(int radix)`
- `void remove()`
- `Scanner reset()`
- `Scanner skip(Pattern pattern)` / `Scanner skip(String pattern)`
- `String toString()`


**StringJoiner**
- `StringJoiner(CharSequence delimiter)` / `StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix)`
- `StringJoiner setEmptyValue(CharSequence emptyValue)`
- `StringJoiner add(CharSequence newElement)` / `StringJoiner merge(StringJoiner other)`
- `int length()`
- `String toString()`

**StringTokenizer**
- `StringTokenizer(String str)` / `StringTokenizer(String str, String delim)` / `StringTokenizer(String str, String delim, boolean returnDelims)`
- `int countTokens()`
- `boolean hasMoreElements()` / `Object nextElement()`
- `boolean hasMoreTokens()` / `String nextToken()` / `String nextToken(String delim)`


TimeZone
- `TimeZone()`
- `Object clone()`
- 实例：`static TimeZone getDefault()` / `static TimeZone getTimeZone(String ID)` / `static TimeZone getTimeZone(ZoneId zoneId)` / `static void setDefault(TimeZone zone)`
- `static String[] getAvailableIDs()` / `static String[] getAvailableIDs(int rawOffset)`
- `String getDisplayName()` / `String getDisplayName(boolean daylight, int style)` / `String getDisplayName(boolean daylight, int style, Locale locale)` / `String getDisplayName(Locale locale)`
- `int getDSTSavings()`
- `String getID()` / `void setID(String ID)`
- `abstract int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds)` / `int getOffset(long date)`
- `abstract int getRawOffset()` / `abstract void setRawOffset(int offsetMillis)`
- `boolean hasSameRules(TimeZone other)`
- `abstract boolean inDaylightTime(Date date)` / `boolean observesDaylightTime()` / `abstract boolean useDaylightTime()`
- `ZoneId toZoneId()`

Timer(线程调度，执行TimerTask)
- `Timer()` / `Timer(boolean isDaemon)` / `Timer(String name)` / `Timer(String name, boolean isDaemon)`
- `void cancel()`
- `int purge()`
- `void schedule(TimerTask task, Date time)` / `void schedule(TimerTask task, Date firstTime, long period)` / `void schedule(TimerTask task, long delay)` / `void schedule(TimerTask task, long delay, long period)` / `void scheduleAtFixedRate(TimerTask task, Date firstTime, long period)` / `void scheduleAtFixedRate(TimerTask task, long delay, long period)`

TimerTask（计划任务，抽象类）
- `protected	TimerTask()`
- `boolean cancel()`
- `abstract void run()`
- `long scheduledExecutionTime()`

UUID
- `UUID(long mostSigBits, long leastSigBits)`
- `int clockSequence()` / `long node()` / `long timestamp()` / `int variant()` / `int version()`
- `int compareTo(UUID val)` / `boolean equals(Object obj)`
- `long getLeastSignificantBits()` / `long getMostSignificantBits()`
- 实例：`static UUID nameUUIDFromBytes(byte[] name)` / `static UUID randomUUID()`
- `static UUID fromString(String name)` / `String toString()`

## `java.util.logging`

Handler(抽象类)
- `protected	Handler()`
- `abstract void close()`
- `abstract void flush()`
- `String getEncoding()` / `void setEncoding(String encoding)`
- `ErrorManager getErrorManager()` / `void setErrorManager(ErrorManager em)`
- `Filter getFilter()` / `void setFilter(Filter newFilter)`
- `Formatter getFormatter()` / `void setFormatter(Formatter newFormatter)`
- `Level getLevel()` / `void setLevel(Level newLevel)`
- `boolean isLoggable(LogRecord record)`
- `abstract void publish(LogRecord record)`
- `protected void reportError(String msg, Exception ex, int code)`

ConsoleHandler(日志发布到system.err)
- `ConsoleHandler()`
- `void	close()`
- `void	publish(LogRecord record)`

FileHandler(文件记录日志，默认使用XMLFormatter)
- `FileHandler()` / `FileHandler(String pattern)` / `FileHandler(String pattern, boolean append)` / `FileHandler(String pattern, int limit, int count)` / `FileHandler(String pattern, int limit, int count, boolean append)`
- `void	close()`
- `void	publish(LogRecord record)`

MemoryHandler(内存记录日志)
- `MemoryHandler()` / `MemoryHandler(Handler target, int size, Level pushLevel)`
- `void close()`
- `void flush()`
- `Level getPushLevel()` / `void setPushLevel(Level newLevel)`
- `boolean isLoggable(LogRecord record)`
- `void publish(LogRecord record)`
- `void push()`

SocketHandler（套接字记录日志,StreamHandler子类）
- `SocketHandler()` / `SocketHandler(String host, int port)`
- `void	close()`
- `void	publish(LogRecord record)`

StreamHandler（流记录日志）
- `StreamHandler()` / `StreamHandler(OutputStream out, Formatter formatter)`
- `void close()`
- `void flush()`
- `boolean isLoggable(LogRecord record)`
- `void publish(LogRecord record)`
- `void setEncoding(String encoding)`
- `protected void setOutputStream(OutputStream out)`


ErrorManager
- 静态成员：`CLOSE_FAILURE` / `FLUSH_FAILURE` / `FORMAT_FAILURE` / `GENERIC_FAILURE` / `OPEN_FAILURE` / `WRITE_FAILURE`
- `ErrorManager()`
- `void	error(String msg, Exception ex, int code)`

Level(日志级别)
- 静态变量：SEVERE,WARNING,INFO,CONFIG,FINE,FINER,FINEST,ALL/OFF
- `protected	Level(String name, int value)` / `protected	Level(String name, int value, String resourceBundleName)`
- `boolean equals(Object ox)`
- `String getLocalizedName()` / `String getName()` / `String getResourceBundleName()`
- `int intValue()`
- `static Level parse(String name)`
- `String toString()`

LogRecord(日志记录)
- `LogRecord(Level level, String msg)`
- `Level getLevel()` / `void setLevel(Level level)`
- `String getLoggerName()` / `void setLoggerName(String name)`
- `String getMessage()` / `void setMessage(String message)`
- `long getMillis()` / `void setMillis(long millis)`
- `Object[] getParameters()` / `void setParameters(Object[] parameters)`
- `ResourceBundle getResourceBundle()` / `void setResourceBundle(ResourceBundle bundle)`
- `String getResourceBundleName()` / `void setResourceBundleName(String name)`
- `long getSequenceNumber()` / `void setSequenceNumber(long seq)`
- `String getSourceClassName()` / `void setSourceClassName(String sourceClassName)`
- `String getSourceMethodName()` / `void setSourceMethodName(String sourceMethodName)`
- `int getThreadID()` / `void setThreadID(int threadID)`
- `Throwable getThrown()` / `void setThrown(Throwable thrown)`

Formatter(格式化抽象类)
- `protected	Formatter()`
- `abstract String format(LogRecord record)`
- `String formatMessage(LogRecord record)`
- `String getHead(Handler h)`
- `String getTail(Handler h)`

SimpleFormatter
- `SimpleFormatter()`
- `String	format(LogRecord record)`

XMLFormatter
- `XMLFormatter()`
- `String format(LogRecord record)`
- `String getHead(Handler h)`
- `String getTail(Handler h)`


**Logger**
- `protected	Logger(String name, String resourceBundleName)`
- 实例：
  - `static Logger getAnonymousLogger()` / `static Logger getAnonymousLogger(String resourceBundleName)`
  - `static Logger getGlobal()`
  - `static Logger getLogger(String name)` / `static Logger getLogger(String name, String resourceBundleName)`
- 不同级别：
  - `boolean isLoggable(Level level)`
  - `void config(String msg)` / `void config(Supplier<String> msgSupplier)`
  - `void fine(String msg)` / `void fine(Supplier<String> msgSupplier)`
  - `void finer(String msg)` / `void finer(Supplier<String> msgSupplier)`
  - `void finest(String msg)` / `void finest(Supplier<String> msgSupplier)`
  - `void info(String msg)` / `void info(Supplier<String> msgSupplier)`
  - `void severe(String msg)` / `void severe(Supplier<String> msgSupplier)`
  - `void warning(String msg)` / `void warning(Supplier<String> msgSupplier)`
- 进入退出环境：
  - `void entering(String sourceClass, String sourceMethod)` / `void entering(String sourceClass, String sourceMethod, Object param1)` / `void entering(String sourceClass, String sourceMethod, Object[] params)`
  - `void exiting(String sourceClass, String sourceMethod)` / `void exiting(String sourceClass, String sourceMethod, Object result)`
- 有参调用
  - `void log(Level level, String msg)` / `void log(Level level, String msg, Object param1)` / `void log(Level level, String msg, Object[] params)` / `void log(Level level, String msg, Throwable thrown)` / `void log(Level level, Supplier<String> msgSupplier)` / `void log(Level level, Throwable thrown, Supplier<String> msgSupplier)` / `void log(LogRecord record)`
  - `void logp(Level level, String sourceClass, String sourceMethod, String msg)` / `void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1)` / `void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params)` / `void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown)` / `void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier)` / `void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> msgSupplier)`
- 属性：
  - `void addHandler(Handler handler)` / `Handler[] getHandlers()` / `void removeHandler(Handler handler)`
  - `Filter getFilter()` / `void setFilter(Filter newFilter)`
  - `Level getLevel()` / `void setLevel(Level newLevel)`
  - `String getName()`
  - `Logger getParent()` / `void setParent(Logger parent)`
  - `ResourceBundle getResourceBundle()` / `void setResourceBundle(ResourceBundle bundle)` / `String getResourceBundleName()`
  - `boolean getUseParentHandlers()` / `void setUseParentHandlers(boolean useParentHandlers)`
- `void throwing(String sourceClass, String sourceMethod, Throwable thrown)`

LogManager(维护日志记录器的共享状态)
- `protected	LogManager()`
- 实例：`static LogManager getLogManager()`
- `boolean addLogger(Logger logger)` / `Logger getLogger(String name)`
- `void checkAccess()`
- `Enumeration<String> getLoggerNames()`
- `static LoggingMXBean getLoggingMXBean()`
- `String getProperty(String name)`
- `void readConfiguration()` / `void readConfiguration(InputStream ins)`
- `void reset()`

## `java.util.regex`

**Matcher**
- `Matcher appendReplacement(StringBuffer sb, String replacement)` / `StringBuffer appendTail(StringBuffer sb)`
- 偏移量：`int end()` / `int end(int group)` / `int end(String name)`
- 搜索：`boolean find()` / `boolean find(int start)`
- 替换：`String replaceAll(String replacement)` / `String replaceFirst(String replacement)`
- 子序列：`String group()` / `String group(int group)` / `String group(String name)` / `int groupCount()`
- 边界：`boolean hasAnchoringBounds()` / `boolean hasTransparentBounds()` / `Matcher useAnchoringBounds(boolean b)` / `Matcher useTransparentBounds(boolean b)`
- `boolean hitEnd()`
- 匹配：`boolean lookingAt()` / `boolean matches()`
- `Pattern pattern()` / `Matcher usePattern(Pattern newPattern)`
- `static String quoteReplacement(String s)`
- 区域限制：`Matcher region(int start, int end)` / `int regionEnd()` / `int regionStart()`
- `boolean requireEnd()`
- 重置：`Matcher reset()` / `Matcher reset(CharSequence input)`
- 索引：`int start()` / `int start(int group)` / `int start(String name)`
- `MatchResult toMatchResult()`
- `String toString()`

**Pattern**
- `Predicate<String> asPredicate()`
- `static Pattern compile(String regex)` / `static Pattern compile(String regex, int flags)`
- `int flags()`
- `Matcher matcher(CharSequence input)`
- `static boolean matches(String regex, CharSequence input)`
- `String pattern()`
- `static String quote(String s)`
- `String[] split(CharSequence input)` / `String[] split(CharSequence input, int limit)`
- `Stream<String> splitAsStream(CharSequence input)`
- `String toString()`

# Compact2

## java.sql

Date(用于SQL的Date值)
- `Date(long date)`
- `void	setTime(long date)`
- 实例：`static Date	valueOf(LocalDate date)` / `static Date	valueOf(String s)`
- `Instant	toInstant()` / `LocalDate	toLocalDate()`
- `String	toString()`

Time(用于SQL的TIME值)
- `Time(long time)`
- `Instant	toInstant()` / `LocalTime	toLocalTime()`
- `static Time	valueOf(LocalTime time)` / `static Time	valueOf(String s)`
- `String	toString()`

Timestamp（用于SQL的TIMESTAMP值）
- `Timestamp(long time)`
- 实例：`static Timestamp from(Instant instant)` / `static Timestamp valueOf(LocalDateTime dateTime)` / `static Timestamp valueOf(String s)`
- 比较：`int compareTo(Date o)` / `int compareTo(Timestamp ts)` / `boolean equals(Object ts)` / `boolean equals(Timestamp ts)`
- `boolean after(Timestamp ts)` / `boolean before(Timestamp ts)`
- `int getNanos()` / `void setNanos(int n)`
- `long getTime()` / `void setTime(long time)`
- `Instant toInstant()` / `LocalDateTime toLocalDateTime()`
- `String toString()`


DriverManager（驱动管理，JDBC2.0提供另一种更好方法：DataSource）
- `static void registerDriver(Driver driver)` / `static void registerDriver(Driver driver, DriverAction da)` / `static void deregisterDriver(Driver driver)`
- `static Connection getConnection(String url)` / `static Connection getConnection(String url, Properties info)` / `static Connection getConnection(String url, String user, String password)`
- `static Driver getDriver(String url)` / `static Enumeration<Driver> getDrivers()`
- `static int getLoginTimeout()` / `static void setLoginTimeout(int seconds)`
- `static PrintWriter getLogWriter()` / `static void setLogWriter(PrintWriter out)`
- `static void println(String message)`

DriverPropertyInfo（驱动程序属性）
- `String[]	choices` / `String	description` / `String	name` / `boolean required` / `String value`
- `DriverPropertyInfo(String name, String value)`

Types(JDBC类型)