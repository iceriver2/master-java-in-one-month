> 2019-10-09 想不到 mybatis 居然有[中文版](https://mybatis.org/mybatis-3/zh/index.html)，不易啊！
> 从 SqlSessionFactory，SqlSessionFactoryBuilder等可以看出来，设计模式真是无处不在啊。

- [安装](#%e5%ae%89%e8%a3%85)
- [使用](#%e4%bd%bf%e7%94%a8)
  - [构建SqlSessionFactory实例](#%e6%9e%84%e5%bb%basqlsessionfactory%e5%ae%9e%e4%be%8b)
  - [获取SqlSession实例](#%e8%8e%b7%e5%8f%96sqlsession%e5%ae%9e%e4%be%8b)
  - [Mapper映射](#mapper%e6%98%a0%e5%b0%84)
- [XML配置](#xml%e9%85%8d%e7%bd%ae)
  - [configuration（配置）](#configuration%e9%85%8d%e7%bd%ae)
  - [properties（属性）](#properties%e5%b1%9e%e6%80%a7)
  - [settings（设置）](#settings%e8%ae%be%e7%bd%ae)
  - [typeAliases（类型别名）](#typealiases%e7%b1%bb%e5%9e%8b%e5%88%ab%e5%90%8d)
  - [typeHandlers（类型处理器）](#typehandlers%e7%b1%bb%e5%9e%8b%e5%a4%84%e7%90%86%e5%99%a8)
  - [objectFactory（对象工厂）](#objectfactory%e5%af%b9%e8%b1%a1%e5%b7%a5%e5%8e%82)
  - [plugins（插件）](#plugins%e6%8f%92%e4%bb%b6)
  - [environments（环境配置）](#environments%e7%8e%af%e5%a2%83%e9%85%8d%e7%bd%ae)
  - [databaseIdProvider（数据库厂商标识）](#databaseidprovider%e6%95%b0%e6%8d%ae%e5%ba%93%e5%8e%82%e5%95%86%e6%a0%87%e8%af%86)
  - [mappers（映射器）](#mappers%e6%98%a0%e5%b0%84%e5%99%a8)
- [映射文件](#%e6%98%a0%e5%b0%84%e6%96%87%e4%bb%b6)
  - [select](#select)
  - [insert/update/delete](#insertupdatedelete)
  - [sql](#sql)
  - [参数](#%e5%8f%82%e6%95%b0)
  - [resultMap](#resultmap)
  - [cache/cache-ref](#cachecache-ref)
  - [动态SQL](#%e5%8a%a8%e6%80%81sql)
- [在项目中应用](#%e5%9c%a8%e9%a1%b9%e7%9b%ae%e4%b8%ad%e5%ba%94%e7%94%a8)
- [注解](#%e6%b3%a8%e8%a7%a3)
- [日志](#%e6%97%a5%e5%bf%97)

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

# 安装

将 mybatis-xxx.jar 置于 classpath 中。如果是 Maven，需要加入 dependency 。
> 注：再次强调，做Java项目，不开IDE是不行的。

最新版是3.5.2。

# 使用

**每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。**  
SqlSessionFactoryBuilder 从 XML 配置文件或一个预先定制的 Configuration 的实例构建出 SqlSessionFactory 的实例。  
从 SqlSessionFactory 构建 SqlSession 实例。

SqlSessionFactoryBuilder的作用仅限于创建 SqlSessionFactory 。  
SqlSessionFactory 一旦创建就始终存在，且应该保持唯一。可以使用单例模式。  
SqlSession 应该在一次请求中被创建、使用并销毁。同样，由 SqlSession 创建的 Mapper 实例也应该仅在一次请求中存在。


> 注：猜测一下全过程（纯属推断）。
> - SqlSessionFactoryBuilder 实例读取主配置文件 mybatis-config.xml，生成 SqlSessionFactory 实例。
>   - 主配置文件的 environment 配置，使得 SqlSessionFactory 能连接到数据库。
>   - 主配置文件的 mappers-mapper 配置，并在 SqlSessionFactory 实例内部生成 Mapper 类的声明（只是声明，没有实例化），声明中包含 mapper 配置中所说明的方法。
> - SqlSessionFactory 实例通过 openSession() 获取一个 SqlSession 实例。
> - SqlSession 实例通过传入的 Mapper 类名称，执行 getMapper() 生成一个 Mapper 类实例。
> - Mapper 类实例直接调用方法。

> 以代码表示（注意，代码不规范，只是示意）：
> 主配置文件（已经合并了分配置文件的内容）
> ```xml
> <configuration>
>   <!-- 数据库配置 -->
>   <environment id="development"> ... </environment>
>   <!-- 来自分配置文件的映射 -->
>   <mapper namespace="com.test.SomeMapper">
>      <select id="doSomething" resultType="XXX">
>        select * from XXX where id = #{id}
>      </select>
>   </mapper>
> </configuration>
> ```
> SqlSessionFactoryBuilder 读取配置后，两个隐式的动作
> ```java
> // 设置数据库连接参数，见配置 environment 部分
> SqlSessionFactoryBuilder.setDatabaseConfig();
>
> // 声明 Mapper 类，见配置 mapper 部分
> class com.test.SomeMapper {
>   public XXX doSomething(id) { ... }
> }
> ```
> 执行过程
> ```java
> SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
> SqlSession sqlSession = sqlSessionFactory.openSession();
> SomeMapper someMapper = sqlSession.getMapper(SomeMapper.class);
> someMapper.doSomething(id);
> ```

## 构建SqlSessionFactory实例

XML配置文件中包含了对 MyBatis 系统的核心设置，包含获取数据库连接实例的数据源（DataSource）和决定事务作用域和控制方式的事务管理器（TransactionManager）。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!-- 验证XML的格式的DTD -->
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  <!-- 引入一个映射 -->
  <mappers>
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```

从XML文件构建 SqlSessionFactory 实例。
```java
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

SqlSessionFactoryBuilder 有五个 build() 方法，每一种都允许你从不同的资源中创建一个 SqlSession 实例。
```java
SqlSessionFactory build(InputStream inputStream)
SqlSessionFactory build(InputStream inputStream, String environment)
SqlSessionFactory build(InputStream inputStream, Properties properties)
SqlSessionFactory build(InputStream inputStream, String env, Properties props)
SqlSessionFactory build(Configuration config)
```

前4种方法大致相同，都是从一个 InputStream 中获得配置。用到了一个 Resources 工具类，这个类在 org.apache.ibatis.io 包中，主要用于从文件中加载加载资源。Resources的主要方法有
```java
URL getResourceURL(String resource)
URL getResourceURL(ClassLoader loader, String resource)
InputStream getResourceAsStream(String resource)
InputStream getResourceAsStream(ClassLoader loader, String resource)
Properties getResourceAsProperties(String resource)
Properties getResourceAsProperties(ClassLoader loader, String resource)
Reader getResourceAsReader(String resource)
Reader getResourceAsReader(ClassLoader loader, String resource)
File getResourceAsFile(String resource)
File getResourceAsFile(ClassLoader loader, String resource)
InputStream getUrlAsStream(String urlString)
Reader getUrlAsReader(String urlString)
Properties getUrlAsProperties(String urlString)
Class classForName(String className)
```


## 获取SqlSession实例

从 SqlSessionFactory 获取 SqlSession 实例。

SqlSession 完全包含了面向数据库执行 SQL 命令所需的所有方法。SqlSession可以直接执行已经映射的SQL语句。
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
  // 猜测，调用 BlogMapper 类的 selectBlog() 方法，参数是 101 
}
```

但更简洁的方法是使用正确描述每个语句的参数和返回值的接口（比如 BlogMapper.class）。也就是 Mapper 映射。
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  Blog blog = mapper.selectBlog(101);
}
```

SqlSessionFactory 有六个方法创建 SqlSession 实例。选择不同方法时需要考虑的因素：事务处理、连接、执行语句。
```java
SqlSession openSession()
SqlSession openSession(boolean autoCommit)
SqlSession openSession(Connection connection)
SqlSession openSession(TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType)
SqlSession openSession(ExecutorType execType, boolean autoCommit)
SqlSession openSession(ExecutorType execType, Connection connection)
Configuration getConfiguration();
```

默认的 openSession()方法没有参数，它会创建有如下特性的 SqlSession：
- 会开启一个事务（也就是不自动提交）。
- 将从由当前环境配置的 DataSource 实例中获取 Connection 对象。
- 事务隔离级别将会使用驱动或数据源的默认设置。
- 预处理语句不会被复用，也不会批量处理更新。

多数方法的可读性很强。只有最后几个带 ExecutorType 的方法，定义是否复用预处理语句。


SqlSession 实例在 MyBatis 中是非常强大的一个类。它可以用于执行所有语句、提交或回滚事务、获得映射器等。

执行语句方法
```java

<T> T selectOne(String statement, Object parameter)
<E> List<E> selectList(String statement, Object parameter)
<T> Cursor<T> selectCursor(String statement, Object parameter)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
int insert(String statement, Object parameter)
int update(String statement, Object parameter)
int delete(String statement, Object parameter)

// 不带 parameter 的形式
<T> T selectOne(String statement)
<E> List<E> selectList(String statement)
<T> Cursor<T> selectCursor(String statement)
<K,V> Map<K,V> selectMap(String statement, String mapKey)
int insert(String statement)
int update(String statement)
int delete(String statement)

// 三个高级版本，限制返回行数的范围，或者提供自定义结果控制逻辑，用于大数据集
<E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
<T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
void select (String statement, Object parameter, ResultHandler<T> handler)
void select (String statement, Object parameter, RowBounds rowBounds, ResultHandler<T> handler)
```

批量立即更新方法
```java
List<BatchResult> flushStatements()
```

事务控制方法
```java
void commit()
void commit(boolean force)
void rollback()
void rollback(boolean force)
```

本地缓存
```java
void clearCache()
```

关闭（更好的方式是 TWR）
```java
void close()
```

使用映射器
```java
<T> T getMapper(Class<T> type)
```

## Mapper映射

所谓映射的SQL语句，在主配置 mybatis-config.xml 中表示为 `<mapper resource="org/mybatis/example/BlogMapper.xml"/>`。
该映射文件内容是：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.example.BlogMapper">
  <select id="selectBlog" resultType="Blog">
    select * from Blog where id = #{id}
  </select>
</mapper>
```

在命名空间 “org.mybatis.example.BlogMapper” 中定义了一个名为 “selectBlog” 的映射语句，允许使用指定的完全限定名 “org.mybatis.example.BlogMapper.selectBlog” 来调用映射语句。
```java
Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
```

这和使用完全限定名调用 Java 对象的方法类似。这样，该命名就可以直接映射到在命名空间中同名的 Mapper 类，并将已映射的 select 语句中的名字、参数和返回类型匹配成方法。
更简单的写法是：
```java
BlogMapper mapper = session.getMapper(BlogMapper.class);
Blog blog = mapper.selectBlog(101);
```

命名空间的作用有两个，一个是利用更长的完全限定名来将不同的语句隔离开来，同时也实现了接口绑定。
长远来看，只要将命名空间置于合适的 Java 包命名空间之中，代码会变得更加整洁，也有利于更方便地使用 MyBatis。

为了减少输入量，MyBatis 对所有的命名配置元素（包括语句，结果映射，缓存等）使用了如下的命名解析规则。
- 完全限定名（比如 “com.mypackage.MyMapper.selectAllThings）将被直接用于查找及使用。
- 短名称（比如 “selectAllThings”）如果全局唯一也可以作为一个单独的引用。 如果不唯一，有两个或两个以上的相同名称（比如 “com.foo.selectAllThings” 和 “com.bar.selectAllThings”），那么使用时就会产生“短名称不唯一”的错误，这种情况下就必须使用完全限定名。

除了 Mapper 映射器外，还可以通过注解的方式处理映射，但使用比较不方便。

# XML配置

配置文档的顶层结构如下：
- configuration（配置）
- **properties（属性）**
- settings（设置）
- **typeAliases（类型别名）**
- typeHandlers（类型处理器）
- objectFactory（对象工厂）
- plugins（插件）
- **environments（环境配置）**
  - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）
- databaseIdProvider（数据库厂商标识）
- **mappers（映射器）**


## configuration（配置）

`<configuration>` 是根元素。

## properties（属性）

属性都是可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来传递。

例如：
```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>

<dataSource type="POOLED">
  <property name="driver" value="${driver}"/> <!-- 未设置属性，去config.properties找 -->
  <property name="url" value="${url}"/> <!-- 未设置属性，去config.properties找 -->
  <property name="username" value="${username}"/> <!-- 使用属性username -->
  <property name="password" value="${password}"/> <!-- 使用属性password -->
</dataSource>
```

如果在 SqlSessionFactoryBuilder.build() 方法的参数中传入了同名的属性，则以参数中的属性为准。

属性的优先级顺序是：通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的是 properties 属性中指定的属性。
> 注：这个顺序中后两位，有点反常啊～一般是 resource 最低，properties稍高，方法参数最高。

允许为参数指定默认值。该特性默认关闭，需要手动开启。
```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- 启用默认值特性 -->
</properties>

<dataSource type="POOLED">
  <property name="username" value="${username:ut_user}"/> <!-- 如果属性 'username' 没有被配置，'username' 属性的值将为 'ut_user' -->
</dataSource>
```

## settings（设置）

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。

| 设置名 | 描述 | 有效值 | 默认值 |
|-------|-----|-------|--------|
| cacheEnabled | 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。 | true | false | true
| lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 fetchType 属性来覆盖该项的开关状态。 | true | false | false
| aggressiveLazyLoading | 当开启时，任何方法的调用都会加载该对象的所有属性。 否则，每个属性会按需加载（参考 lazyLoadTriggerMethods)。 | true | false | false （在 3.4.1 及之前的版本默认值为 true）
| multipleResultSetsEnabled | 是否允许单一语句返回多结果集（需要驱动支持）。 | true | false | true
| useColumnLabel | 使用列标签代替列名。不同的驱动在这方面会有不同的表现。 | true | false | true
| useGeneratedKeys | 允许 JDBC 支持自动生成主键，需要驱动支持。 | true | false | False
| autoMappingBehavior | 指定 MyBatis 应如何自动映射列到字段或属性。NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL | PARTIAL
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或者未知属性类型）的行为。NONE：不做任何反应；WARNING：输出提醒日志('org.apache.ibatis.session.AutoMappingUnknownColumnBehavior' 的日志等级必须设置为 WARN)；FAILING: 映射失败 (抛出 SqlSessionException) | NONE, WARNING, FAILING | NONE
| defaultExecutorType | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。 | SIMPLE REUSE BATCH | SIMPLE
| defaultStatementTimeout | 设置超时时间，它决定驱动等待数据库响应的秒数。 | 任意正整数 | 未设置 (null)
| defaultFetchSize | 为驱动的结果集获取数量（fetchSize）设置一个提示值。此参数只可以在查询设置中被覆盖。 | 任意正整数 | 未设置 (null)
| defaultResultSetType | Specifies a scroll strategy when omit it per statement settings. (Since: 3.5.2) | FORWARD_ONLY | SCROLL_SENSITIVE | SCROLL_INSENSITIVE | DEFAULT(same behavior with 'Not Set') | Not Set (null)
| safeRowBoundsEnabled | 允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为 false。 | true | false | False
| safeResultHandlerEnabled | 允许在嵌套语句中使用分页（ResultHandler）。如果允许使用则设置为 false。 | true | false | True
| mapUnderscoreToCamelCase | 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | true | false | False
| localCacheScope | MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。 | SESSION | STATEMENT | SESSION
| jdbcTypeForNull | 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量，常用值：NULL, VARCHAR 或 OTHER。 | OTHER
| lazyLoadTriggerMethods | 指定哪个对象的方法触发一次延迟加载。 | 用逗号分隔的方法列表。 | equals,clone,hashCode,toString
| defaultScriptingLanguage | 指定动态 SQL 生成的默认语言。 | 一个类型别名或完全限定类名。 | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
| defaultEnumTypeHandler | 指定 Enum 使用的默认 TypeHandler 。（新增于 3.4.5） | 一个类型别名或完全限定类名。 | org.apache.ibatis.type.EnumTypeHandler
| callSettersOnNulls | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这在依赖于 Map.keySet() 或 null 值初始化的时候比较有用。注意基本类型（int、boolean 等）是不能设置成 null 的。 | true | false | false
| returnInstanceForEmptyRow | 当返回行的所有列都是空时，MyBatis默认返回 null。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集 （如集合或关联）。（新增于 3.4.2） | true | false | false
| logPrefix | 指定 MyBatis 增加到日志名称的前缀。 | 任何字符串 | 未设置
| logImpl | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。 | SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING | NO_LOGGING | 未设置
| proxyFactory | 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。 | CGLIB | JAVASSIST | JAVASSIST （MyBatis 3.3 以上）
| vfsImpl | 指定 VFS 的实现 | 自定义 VFS 的实现的类全限定名，以逗号分隔。 | 未设置
| useActualParamName | 允许使用方法签名中的名称作为语句参数名称。 | true | false | true
| configurationFactory | 指定一个提供 Configuration 实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为static Configuration getConfiguration() 的方法。（新增于 3.2.3） | 类型别名或者全类名. | 未设置

一个配置完整的 settings 元素
```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

## typeAliases（类型别名）

类型别名是为 Java 类型设置一个短的名字。 它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

可以分别配置；也可以指定一个包名，由MyBatis自动搜索，别名是Bean的首字母小写；若有注解，以注解值为准。
```xml
<typeAliases>
  <!-- 分别指定 -->
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>

  <!-- 自动搜索 -->
  <package name="domain.blog"/>
</typeAliases>
```

常见的Java类型内建类型的别名（不区分大小写，注意基本类型的特殊格式）
| 别名 | 映射的类型 |
|-----|-----------|
| _byte | byte |
| _long | long |
| _short | short |
| _int | int |
| _integer | int |
| _double | double |
| _float | float |
| _boolean | boolean |
| string | String |
| byte | Byte |
| long | Long |
| short | Short |
| int | Integer |
| integer | Integer |
| double | Double |
| float | Float |
| boolean | Boolean |
| date | Date |
| decimal | BigDecimal |
| bigdecimal | BigDecimal |
| object | Object |
| map | Map |
| hashmap | HashMap |
| list | List |
| arraylist | ArrayList |
| collection | Collection |
| iterator | Iterator |

## typeHandlers（类型处理器）

无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。

默认的类型处理器
| 类型处理器 | Java 类型 | JDBC 类型 |
|-----------|---------|-----------|
| BooleanTypeHandler | java.lang.Boolean, boolean | 数据库兼容的 BOOLEAN |
| ByteTypeHandler | java.lang.Byte, byte | 数据库兼容的 NUMERIC 或 BYTE |
| ShortTypeHandler | java.lang.Short, short | 数据库兼容的 NUMERIC 或 SMALLINT |
| IntegerTypeHandler | java.lang.Integer, int | 数据库兼容的 NUMERIC 或 INTEGER |
| LongTypeHandler | java.lang.Long, long | 数据库兼容的 NUMERIC 或 BIGINT |
| FloatTypeHandler | java.lang.Float, float | 数据库兼容的 NUMERIC 或 FLOAT |
| DoubleTypeHandler | java.lang.Double, double | 数据库兼容的 NUMERIC 或 DOUBLE |
| BigDecimalTypeHandler | java.math.BigDecimal | 数据库兼容的 NUMERIC 或 DECIMAL |
| StringTypeHandler | java.lang.String | CHAR, VARCHAR |
| ClobReaderTypeHandler | java.io.Reader | - |
| ClobTypeHandler | java.lang.String | CLOB, LONGVARCHAR |
| NStringTypeHandler | java.lang.String | NVARCHAR, NCHAR |
| NClobTypeHandler | java.lang.String | NCLOB |
| BlobInputStreamTypeHandler | java.io.InputStream | - |
| ByteArrayTypeHandler | byte[] | 数据库兼容的字节流类型 |
| BlobTypeHandler | byte[] | BLOB, LONGVARBINARY |
| DateTypeHandler | java.util.Date | TIMESTAMP |
| DateOnlyTypeHandler | java.util.Date | DATE |
| TimeOnlyTypeHandler | java.util.Date | TIME |
| SqlTimestampTypeHandler | java.sql.Timestamp | TIMESTAMP |
| SqlDateTypeHandler | java.sql.Date | DATE |
| SqlTimeTypeHandler | java.sql.Time | TIME |
| ObjectTypeHandler | Any | OTHER 或未指定类型 |
| EnumTypeHandler | Enumeration Type | VARCHAR 或任何兼容的字符串类型，用以存储枚举的名称（而不是索引值） |
| EnumOrdinalTypeHandler | Enumeration Type | 任何兼容的 NUMERIC 或 DOUBLE 类型，存储枚举的序数值（而不是名称）。 |
| SqlxmlTypeHandler | java.lang.String | SQLXML |
| InstantTypeHandler | java.time.Instant | TIMESTAMP |
| LocalDateTimeTypeHandler | java.time.LocalDateTime | TIMESTAMP |
| LocalDateTypeHandler | java.time.LocalDate | DATE |
| LocalTimeTypeHandler | java.time.LocalTime | TIME |
| OffsetDateTimeTypeHandler | java.time.OffsetDateTime | TIMESTAMP |
| OffsetTimeTypeHandler | java.time.OffsetTime | TIME |
| ZonedDateTimeTypeHandler | java.time.ZonedDateTime | TIMESTAMP |
| YearTypeHandler | java.time.Year | INTEGER |
| MonthTypeHandler | java.time.Month | INTEGER |
| YearMonthTypeHandler | java.time.YearMonth | VARCHAR 或 LONGVARCHAR |
| JapaneseDateTypeHandler | java.time.chrono.JapaneseDate | DATE |

可以重写类型处理器或创建自己的类型处理器来处理不支持的或非标准的类型。
具体做法为：实现 org.apache.ibatis.type.TypeHandler 接口， 或继承一个很便利的类 org.apache.ibatis.type.BaseTypeHandler， 然后可以选择性地将它映射到一个 JDBC 类型。最后在 mybatis-config.xml 中声明。
> 注：看了下例子，有点复杂。

## objectFactory（对象工厂）

MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。


如果想覆盖对象工厂的默认行为，则可以通过创建自己的对象工厂来实现。比如：
```java
// ExampleObjectFactory.java
public class ExampleObjectFactory extends DefaultObjectFactory {
  public Object create(Class type) {
    return super.create(type);
  }
  public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
    return super.create(type, constructorArgTypes, constructorArgs);
  }
  public void setProperties(Properties properties) {
    super.setProperties(properties);
  }
  public <T> boolean isCollection(Class<T> type) {
    return Collection.class.isAssignableFrom(type);
  }}
```
```xml
<!-- mybatis-config.xml -->
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory>
```

ObjectFactory 接口很简单，它包含两个创建用的方法，一个是处理默认构造方法的，另外一个是处理带参数的构造方法的。
最后，setProperties 方法可以被用来配置 ObjectFactory，在初始化 ObjectFactory 实例后， objectFactory 元素体中定义的属性会被传递给 setProperties 方法。

## plugins（插件）

MyBatis 允许在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。
```java
// ExamplePlugin.java
@Intercepts({@Signature(
  type= Executor.class,
  method = "update",
  args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
  private Properties properties = new Properties();
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre processing if need
    Object returnObject = invocation.proceed();
    // implement post processing if need
    return returnObject;
  }
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
```
```xml
<!-- mybatis-config.xml -->
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins>
```
上面的插件将会拦截在 Executor 实例中所有的 “update” 方法调用， 这里的 Executor 是负责执行低层映射语句的内部对象。

注意：修改或覆盖MyBatis的核心行为，可能导致不可预知的结果。

## environments（环境配置）

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置。

尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。
要连接多个数据库，就需要创建多个实例。
```java
// 将环境作为参数，指定创建哪种环境
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);

// 忽略环境参数，将创建默认环境
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, properties);
```

定义环境的示例
```xml
<!--  -->
<environments default="development"> <!-- 指定默认环境 -->
  <environment id="development"> <!-- 环境ID -->
    <transactionManager type="JDBC"> <!-- 事务管理区类型 -->
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED"> <!-- 数据源类型 -->
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>

  <environment id="production">
    <transactionManager type="MANAGED">
        ...
    <dataSource type="JNDI">
        ...
  </environment>
</environments>
```

事务管理器（transactionManager）在 MyBatis 中有两种：JDBC、MANAGED。JDBC – 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域；MANAGED – 这个配置几乎没做什么，它让容器来管理事务的整个生命周期，默认会关闭连接，如果不想关闭，需要在 property 中设置 closeConnection=false 。  
如果正在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。

dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。
有三种内建的数据源类型（也就是 type=”[UNPOOLED|POOLED|JNDI]”）：
- UNPOOLED– 这个数据源的实现只是每次被请求时打开和关闭连接。虽然有点慢，但对于在数据库连接可用性方面没有太高要求的简单应用程序来说，是一个很好的选择。 UNPOOLED 类型的数据源具有以下属性：
  - `driver` JDBC 驱动的 Java 类的完全限定名
  - `url` 数据库的 JDBC URL 地址
  - `username` 登录数据库的用户名
  - `password` 登录数据库的密码
  - `defaultTransactionIsolationLevel` 默认的连接事务隔离级别
  - `defaultNetworkTimeout` 网络超时时间
  - 可选属性：`driver.encoding=UTF8` 数据库字符集
- POOLED– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。这是一种使得并发 Web 应用快速响应请求的流行处理方式。除了UNPOOLED的属性外，还有更多属性：
  - `poolMaximumActiveConnections` – 在任意时间可以存在的活动连接数量，默认值：10
  - `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
  - `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出时间，默认值：20000 毫秒
  - `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直安静的失败），默认值：20000 毫秒。
  - `poolMaximumLocalBadConnectionTolerance` – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3 （新增于 3.4.5）
  - `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动失败时带有一个恰当的错误消息。
  - `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
  - `poolPingConnectionsNotUsedFor` – 配置 `poolPingQuery` 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 `poolPingEnabled` 为 true 时适用）。
- JNDI – 这个数据源的实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。这种数据源配置只需要两个属性：
  - `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
  - `data_source` – 这是引用数据源实例位置的上下文的路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

## databaseIdProvider（数据库厂商标识）

MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 databaseId 属性。 MyBatis 会加载不带 databaseId 属性和带有匹配当前数据库 databaseId 属性的所有语句。 如果同时找到带有 databaseId 和不带 databaseId 的相同语句，则后者会被舍弃。

为支持多厂商特性只要像下面这样在 mybatis-config.xml 文件中加入 databaseIdProvider 即可：
```xml
<databaseIdProvider type="DB_VENDOR" />
```

## mappers（映射器）

映射器告诉 MyBatis 关于定义 SQL 映射语句的映射文件的位置。可以使用相对于类路径的资源引用， 或完全限定资源定位符（包括 file:/// 的 URL），或类名和包名等。

示例：
```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>

<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>

<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
</mappers>

<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

# 映射文件

MyBatis 的真正强大在于它的映射语句。MyBatis 为聚焦于 SQL 而构建。

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：
- cache – 对给定命名空间的缓存配置。
- cache-ref – 对其他命名空间缓存配置的引用。
- **resultMap** – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- sql – 可被其他语句引用的可重用语句块。
- **insert** – 映射插入语句
- **update** – 映射更新语句
- **delete** – 映射删除语句
- **select** – 映射查询语句


## select

查询语句是 MyBatis 中最常用的元素之一。

简单查询的 select 元素是非常简单的。比如：
```xml
<!-- 语句 selectPerson，参数int类型,结果HashMap类型 -->
<select id="selectPerson" parameterType="int" resultType="hashmap">
  <!-- #{id}是预处理（PreparedStatement）参数 -->
  SELECT * FROM PERSON WHERE ID = #{id}
</select>
```

select 元素可以配置更多的属性
```xml
<select
  id="selectPerson"
  parameterType="int"
  parameterMap="deprecated"
  resultType="hashmap"
  resultMap="personResultMap"
  flushCache="false"
  useCache="true"
  timeout="10"
  fetchSize="256"
  statementType="PREPARED"
  resultSetType="FORWARD_ONLY">
```


属性
| 属性 | 描述 |
|------|-----|
| id | 在命名空间中唯一的标识符，可以被用来引用这条语句。 |
| parameterType | 将会传入这条语句的参数类的完全限定名或别名。可选，MyBatis 可以通过类型处理器（TypeHandler） 推断，默认值为未设置（unset）。 |
| resultType | 从这条语句中返回的期望类型的类的完全限定名或别名。 注意如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身。可以使用 resultType 或 resultMap，但不能同时使用。 |
| resultMap | 外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性。可以使用 resultMap 或 resultType，但不能同时使用。 |
| flushCache | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。 |
| useCache | 将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。 |
| timeout | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖驱动）。 |
| fetchSize | 这是一个给驱动的提示，尝试让驱动程序每次批量返回的结果行数和这个设置值相等。 默认值为未设置（unset）（依赖驱动）。 |
| statementType | STATEMENT，PREPARED 或 CALLABLE 中的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| resultSetType | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖驱动）。 |
| databaseId | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。 |
| resultOrdered | 这个设置仅针对嵌套结果 select 语句适用：如果为 true，就是假设包含了嵌套结果集或是分组，这样的话当返回一个主结果行的时候，就不会发生有对前面结果集的引用的情况。 这就使得在获取嵌套的结果集的时候不至于导致内存不够用。默认值：false。 |
| resultSets | 这个设置仅对多结果集的情况适用。它将列出语句执行后返回的结果集并给每个结果集一个名称，名称是逗号分隔的。 |


## insert/update/delete

数据变更语句 insert，update 和 delete 的实现非常接近。

```xml
<!-- 插入，使用数据库的自增主键id -->
<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</insert>

<!-- 传入数组或集合，插入多行，返回主键 -->
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>

<update id="updateAuthor">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</update>

<delete id="deleteAuthor">
  delete from Author where id = #{id}
</delete>
```

insert/update/delete的更多属性
```xml
<insert
  id="insertAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  keyProperty=""
  keyColumn=""
  useGeneratedKeys=""
  timeout="20">

<update
  id="updateAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">

<delete
  id="deleteAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">
```


Insert, Update, Delete 元素的属性
| 属性 | 描述 |
|------|-----|
| id | 命名空间中的唯一标识符，可被用来代表这条语句。 |
| parameterType | 将要传入语句的参数的完全限定类名或别名。可选，MyBatis 可以通过类型处理器推断出具体传入语句的参数，默认值为未设置（unset）。 |
| flushCache | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：true（对于 insert、update 和 delete 语句）。 |
| timeout | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖驱动）。 |
| statementType | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| useGeneratedKeys | （仅对 insert 和 update 有用）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。 |
| keyProperty | （仅对 insert 和 update 有用）唯一标记一个属性，MyBatis 会通过 getGeneratedKeys 的返回值或者通过 insert 语句的 selectKey 子元素设置它的键值，默认值：未设置（unset）。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn | （仅对 insert 和 update 有用）通过生成的键值设置表中的列名，这个设置仅在某些数据库（像 PostgreSQL）是必须的，当主键列不是表中的第一列的时候需要设置。如果希望使用多个生成的列，也可以设置为逗号分隔的属性名称列表。 |
| databaseId | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。 |


## sql

这个元素可以被用来定义可重用的 SQL 代码段，这些 SQL 代码可以被包含在其他语句中。它可以（在加载的时候）被静态地设置参数。 在不同的包含语句中可以设置不同的值到参数占位符上。

设定SQL片段，然后通过 include 元素引入，通过 include-property 设置属性。注意，属性可以应用在 include 元素的 refid 属性或 include元素的内部语句（也就是子元素的子元素）


例如：
```xml
<!-- 设定sql -->
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>

<!-- 使用sql -->
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```

## 参数

参数是 MyBatis 非常强大的元素。
多数语句，是简单的参数的，是因为 MyBatis会进行自动推断。

简单的场景
```xml
<!-- 将查询结果，自动映射到对象 -->
<select id="selectUsers" resultType="User">
  select id, username, password
  from users
  where id = #{id}
</select>
```

自动查找参数中的树形
```xml
<!-- 将对象属性自动映射 -->
<insert id="insertUser" parameterType="User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>
```

对参数进行特别说明
```xml
<!-- 指定javatype、jdbctype -->
#{property,javaType=int,jdbcType=NUMERIC}
```

自定义类型处理方式，可以指定一个特殊的类型处理器类（或别名）：
```xml
#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
```

对数值类型指定保留小数位数
```xml
#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
```

## resultMap

resultMap 元素是 MyBatis 中最重要最强大的元素。它可以节约 90% 的 JDBC ResultSets 数据提取代码，并在一些情形下允许进行一些 JDBC 不支持的操作。
ResultMap 的设计思想是，对于简单的语句根本不需要配置显式的结果映射，而对于复杂一点的语句只需要描述它们的关系就行了。

简单的映射语句，不需要指定 resultMap 。
```xml
<select id="selectUsers" resultType="map">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

大部分情况下，将结果的列映射到 HashMap 就可以了。但有些情况，需要使用 JavaBean 或 POJO（Plain Old Java Objects）作为领域模型。MyBatis会隐式创建一个 ResultMap，并映射到 JevaBean 属性上。
```java
package com.someapp.model;
public class User {
  private int id;
  private String username;
  private String hashedPassword;

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
}
```
使用这个JavaBean的映射
```xml
<select id="selectUsers" resultType="com.someapp.model.User">
  select id, username
  from some_table
  where id = #{id}
</select>
```
可以使用类型别名来减少输入
```xml
<!-- mybatis-config.xml 中 -->
<typeAlias type="com.someapp.model.User" alias="User"/>

<!-- SQL 映射 XML 中 -->
<select id="selectUsers" resultType="User">
  select id, username
  from some_table
  where id = #{id}
</select>
```

ResultMap很少需要直接使用，除非一些特殊情况，如列名不匹配
```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```
使用时，以 resultMap 替代之前的 resultType
```xml
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

> 注：此处有一个很复杂的 ResultMap 的示例：博客，有一个作者，很多评论，评论又有关联的作者和标签。暂时跳过。


简单场景下，MyBatis会自动映射；复杂情况，需要手动使用 resultMap 进行映射。这两种机制可以混合使用。
```xml
<select id="selectUsers" resultMap="userResultMap">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password
  from some_table
  where id = #{id}
</select>
<resultMap id="userResultMap" type="User">
  <result property="password" column="hashed_password"/>
</resultMap>
```


## cache/cache-ref

MyBatis 内置了一个强大的事务性查询缓存机制，它可以非常方便地配置和定制。

默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。 要启用全局的二级缓存，只需要在你的 SQL 映射文件中添加一行：`<cache/>`。  
这个简单语句的效果如下:
- 映射语句文件中的所有 select 语句的结果将会被缓存。
- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
- 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

缓存作用于 cache 标签所在的映射文件中的命名空间的语句。
同一命名空间的所有语句和缓存行为一致。但可以通过再每条语句上设置来接受或排除。
```xml
<select ... flushCache="false" useCache="true"/>
<insert ... flushCache="true"/>
<update ... flushCache="true"/>
<delete ... flushCache="true"/>
```
想要在多个命名空间中共享相同的缓存配置和实例，可以使用 cache-ref 元素来引用另一个缓存。
```xml
<cache-ref namespace="com.someone.application.data.SomeMapper"/>
```

如果要对缓存进行设置，可以通过修改 cache 元素的属性来实现：
```xml
<!-- 清除策略、刷新间隔、引用数目、是否只读 -->
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```

二级缓存是事务性的。这意味着，当 SqlSession 完成并提交时，或是完成并回滚，但没有执行 flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。

可以自定义缓存，或使用其他第三方方案创建适配器。
```xml
<cache type="com.domain.something.MyCustomCache"/>
```

## 动态SQL

MyBatis 的强大特性之一便是它的动态 SQL。

动态 SQL 元素和 JSTL 或基于类似 XML 的文本处理器相似。MyBatis 采用功能强大的基于 OGNL 的表达式。

if标签，根据条件包含部分子句
```xml
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG
  WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

choose, when, otherwise 选择子句，类似 switch
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

where 仅当有条件存在时，才插入where子句
```xml
<select id="findActiveBlogLike" resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

set 仅当有字段存在时，才插入set子句
```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```

foreach对集合遍历，通常用在构建in子句
```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

_databaseId 变量可以用于判断不同的数据库厂商
```xml
<insert id="insert">
  <selectKey keyProperty="id" resultType="int" order="BEFORE">
    <if test="_databaseId == 'oracle'">
      select seq_users.nextval from dual
    </if>
    <if test="_databaseId == 'db2'">
      select nextval for seq_users from sysibm.sysdummy1"
    </if>
  </selectKey>
  insert into users values (#{id}, #{name})
</insert>
```

所有 xml 标签都是由默认 MyBatis 语言提供的，而它由别名为 xml 的语言驱动器 org.apache.ibatis.scripting.xmltags.XmlLanguageDriver 所提供。

# 在项目中应用

MyBatis 非常灵活。但是对于任一框架，都有一些最佳的方式。典型的应用目录结构是：
```
/my_application
  /bin
  /devlib
  /lib                <-- MyBatis *.jar 文件在这里
  /src
    /org/myapp/
      /action
      /data           <-- MyBatis 配置文件,包括映射器类,XML配置,XML映射文件
        /mybatis-config.xml
        /BlogMapper.java
        /BlogMapper.xml
      /model
      /service
      /view
    /properties       <-- 在你 XML 中配置的属性文件在这里。
  /test
    /org/myapp/
      /action
      /data
      /model
      /service
      /view
    /properties
  /web
    /WEB-INF
      /web.xml
```

使用 MyBatis 的主要 Java 接口就是 SqlSession。正常 SqlSession 由 SqlSessionFactory.openSession() 创建。
当 Mybatis 与一些依赖注入框架（如 Spring 或者 Guice）同时使用时，SqlSessions 将被依赖注入框架所创建。

# 注解

最初设计时，MyBatis 是一个 XML 驱动的框架。配置信息是基于 XML 的，而且映射语句也是定义在 XML 中的。
而 MyBatis3 之后可以使用 Java 注解来完成。注解提供了一种简单的方式来实现简单映射语句，而不会引入大量的开销。

注解
| 注解 | 使用对象 | 相对应的 XML | 描述 |
|-----|---------|-------------|-----|
| `@CacheNamespace` | 类 | `<cache>` | 为给定的命名空间（比如类）配置缓存。属性有：implemetation, eviction, flushInterval, size, readWrite, blocking 和properties。
| `@Property` | N/A | `<property>` | 指定参数值或占位值（placeholder）（能被 mybatis-config.xml内的配置属性覆盖）。属性有：name, value。（仅在MyBatis 3.4.2以上版本生效）
| `@CacheNamespaceRef` | 类 | `<cacheRef>` | 参照另外一个命名空间的缓存来使用。属性有：value, name。如果你使用了这个注解，你应设置 value 或者 name 属性的其中一个。value 属性用于指定 Java 类型而指定命名空间（命名空间名就是指定的 Java 类型的全限定名），name 属性（这个属性仅在MyBatis 3.4.2以上版本生效）直接指定了命名空间的名字。
| `@ConstructorArgs` | 方法 | `<constructor>` | 收集一组结果传递给一个结果对象的构造方法。属性有：value，它是形式参数数组。
| `@Arg` | N/A | `<arg>` `<idArg>` | 单参数构造方法，是 ConstructorArgs 集合的一部分。属性有：id, column, javaType, jdbcType, typeHandler, select 和 resultMap。id 属性是布尔值，来标识用于比较的属性，和`<idArg>` XML 元素相似。
| `@TypeDiscriminator` | 方法 | `<discriminator>` | 一组实例值被用来决定结果映射的表现。属性有：column, javaType, jdbcType, typeHandler 和 cases。cases 属性是实例数组。
| `@Case` | N/A | `<case>` | 单独实例的值和它对应的映射。属性有：value, type, results。results 属性是结果数组，因此这个注解和实际的 ResultMap 很相似，由下面的 Results 注解指定。
| `@Results` | 方法 | `<resultMap>` | 结果映射的列表，包含了一个特别结果列如何被映射到属性或字段的详情。属性有：value, id。value 属性是 Result 注解的数组。这个 id 的属性是结果映射的名称。
| `@Result` | N/A | `<result>` `<id>` | 在列和属性或字段之间的单独结果映射。属性有：id, column, javaType, jdbcType, typeHandler, one, many。id 属性是一个布尔值，来标识应该被用于比较（和在 XML 映射中的`<id>`相似）的属性。one 属性是单独的联系，和 `<association>` 相似，而 many 属性是对集合而言的，和`<collection>`相似。它们这样命名是为了避免名称冲突。
| `@One` | N/A | `<association>` | 复杂类型的单独属性值映射。属性有：select，已映射语句（也就是映射器方法）的全限定名，它可以加载合适类型的实例。fetchType会覆盖全局的配置参数 lazyLoadingEnabled。注意 联合映射在注解 API中是不支持的。这是因为 Java 注解的限制,不允许循环引用。
| `@Many` | N/A | `<collection>` | 映射到复杂类型的集合属性。属性有：select，已映射语句（也就是映射器方法）的全限定名，它可以加载合适类型的实例的集合，fetchType 会覆盖全局的配置参数 lazyLoadingEnabled。注意 联合映射在注解 API中是不支持的。这是因为 Java 注解的限制，不允许循环引用
| `@MapKey` | 方法 |  | 这是一个用在返回值为 Map 的方法上的注解。它能够将存放对象的 List 转化为 key 值为对象的某一属性的 Map。属性有： value，填入的是对象的属性名，作为 Map 的 key 值。
| `@Options` | 方法 | 映射语句的属性 | 这个注解提供访问大范围的交换和配置选项的入口，它们通常在映射语句上作为属性出现。Options 注解提供了通俗易懂的方式来访问它们，而不是让每条语句注解变复杂。属性有：useCache=true, flushCache=FlushCachePolicy.DEFAULT, resultSetType=DEFAULT, statementType=PREPARED, fetchSize=-1, timeout=-1, useGeneratedKeys=false, keyProperty="", keyColumn="", resultSets=""。值得一提的是， Java 注解无法指定 null 值。因此，一旦你使用了 Options 注解，你的语句就会被上述属性的默认值所影响。要注意避免默认值带来的预期以外的行为。
| `@Insert` `@Update` `@Delete` `@Select` | 方法 | `<insert>` `<update>` `<delete>` `<select>` | 这四个注解分别代表将会被执行的 SQL 语句。它们用字符串数组（或单个字符串）作为参数。如果传递的是字符串数组，字符串之间先会被填充一个空格再连接成单个完整的字符串。这有效避免了以 Java 代码构建 SQL 语句时的“丢失空格”的问题。然而，你也可以提前手动连接好字符串。属性有：value，填入的值是用来组成单个 SQL 语句的字符串数组。
| `@InsertProvider` `@UpdateProvider` `@DeleteProvider` `@SelectProvider` | 方法 | `<insert>` `<update>` `<delete>` `<select>` | 允许构建动态 SQL。这些备选的 SQL 注解允许你指定类名和返回在运行时执行的 SQL 语句的方法。（自从MyBatis 3.4.6开始，你可以用 CharSequence 代替 String 来返回类型返回值了。）当执行映射语句的时候，MyBatis 会实例化类并执行方法，类和方法就是填入了注解的值。你可以把已经传递给映射方法了的对象作为参数，"Mapper interface type" 和 "Mapper method" and "Database ID" 会经过 ProviderContext （仅在MyBatis 3.4.5及以上支持）作为参数值。（MyBatis 3.4及以上的版本，支持多参数传入） 属性有： value, type, method。 value and type 属性需填入类(The type attribute is alias for value, you must be specify either one)。 method 需填入该类定义了的方法名 (Since 3.5.1, you can omit method attribute, the MyBatis will resolve a target method via the ProviderMethodResolver interface. If not resolve by it, the MyBatis use the reserved fallback method that named provideSql)。 注意 接下来的小节将会讨论类，能帮助你更轻松地构建动态 SQL。
| `@Param` | 参数 | N/A | 如果你的映射方法的形参有多个，这个注解使用在映射方法的参数上就能为它们取自定义名字。若不给出自定义名字，多参数（不包括 RowBounds 参数）则先以 "param" 作前缀，再加上它们的参数位置作为参数别名。例如 #{param1}, #{param2}，这个是默认值。如果注解是 @Param("person")，那么参数就会被命名为 #{person}。
| `@SelectKey` | 方法 | `<selectKey>` | 这个注解的功能与 `<selectKey>` 标签完全一致，用在已经被 @Insert 或 @InsertProvider 或 @Update 或 @UpdateProvider 注解了的方法上。若在未被上述四个注解的方法上作 @SelectKey 注解则视为无效。如果你指定了 @SelectKey 注解，那么 MyBatis 就会忽略掉由 @Options 注解所设置的生成主键或设置（configuration）属性。属性有：statement 填入将会被执行的 SQL 字符串数组，keyProperty 填入将会被更新的参数对象的属性的值，before 填入 true 或 false 以指明 SQL 语句应被在插入语句的之前还是之后执行。resultType 填入 keyProperty 的 Java 类型和用 Statement、 PreparedStatement 和 CallableStatement 中的 STATEMENT、 PREPARED 或 CALLABLE 中任一值填入 statementType。默认值是 PREPARED。
| `@ResultMap` | 方法 | N/A | 这个注解给 @Select 或者 @SelectProvider 提供在 XML 映射中的 `<resultMap>` 的id。这使得注解的 select 可以复用那些定义在 XML 中的 ResultMap。如果同一 select 注解中还存在 @Results 或者 @ConstructorArgs，那么这两个注解将被此注解覆盖。
| `@ResultType` | 方法 | N/A | 此注解在使用了结果处理器的情况下使用。在这种情况下，返回类型为 void，所以 Mybatis 必须有一种方式决定对象的类型，用于构造每行数据。如果有 XML 的结果映射，请使用 @ResultMap 注解。如果结果类型在 XML 的 `<select>` 节点中指定了，就不需要其他的注解了。其他情况下则使用此注解。比如，如果 @Select 注解在一个将使用结果处理器的方法上，那么返回类型必须是 void 并且这个注解（或者@ResultMap）必选。这个注解仅在方法返回类型是 void 的情况下生效。
| `@Flush` | 方法 | N/A | 如果使用了这个注解，定义在 Mapper 接口中的方法能够调用 SqlSession#flushStatements() 方法。（Mybatis 3.3及以上）

# 日志

Mybatis 的内置日志工厂提供日志功能，内置日志工厂将日志交给以下其中一种工具作代理：SLF4J、Apache Commons Logging、Log4j 2、Log4j、JDK logging。

MyBatis 内置日志工厂基于运行时自省机制选择合适的日志工具。它会使用第一个查找得到的工具（按上文列举的顺序查找）。如果一个都未找到，日志功能就会被禁用。

不少应用服务器（如 Tomcat 和 WebShpere）的类路径中已经包含 Commons Logging，所以在这种配置环境下的 MyBatis 会把它作为日志工具，而忽略掉 log4j 。
如果确定需要使用 log4j 之类的工具，而不是 Commons Logging，需要进行设置。logImpl 可选的值有：SLF4J、LOG4J、LOG4J2、JDK_LOGGING、COMMONS_LOGGING、STDOUT_LOGGING、NO_LOGGING，或者是实现了接口 org.apache.ibatis.logging.Log 的，且构造方法是以字符串为参数的类的完全限定名。
```xml
<configuration>
  <settings>
    ...
    <setting name="logImpl" value="LOG4J"/>
    ...
  </settings>
</configuration>
```

以log4j为例，MyBatis使用日志的步骤：
- 添加 log4j.jar 包到应用的类路径。对 Web应用或企业应用，需要将 log4j.jar 加入到 WEN-INF/lib ；独立应用则添加到 JVM 的启动参数 -classpath 。
- 配置 log4j。创建一个名为 log4j.properties 的文件，并添加到应用的类路径。

例如对这个映射器接口进行记录(使用Mapper文件也是一样的效果)
```java
package org.mybatis.example;
public interface BlogMapper {
  @Select("SELECT * FROM blog WHERE id = #{id}")
  Blog selectBlog(int id);
}
```
那么 log4j.properties 的内容是(记录 org.mybatis.example.BlogMapper 的详细操作，并记录其他类的错误信息)
```
log4j.rootLogger=ERROR, stdout # Global logging configuration
log4j.logger.org.mybatis.example.BlogMapper=TRACE # MyBatis logging configuration...

# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```
对语句进行控制，则是
```
log4j.logger.org.mybatis.example.BlogMapper.selectBlog=TRACE
```
对一组映射器接口进行控制，则是
```
log4j.logger.org.mybatis.example=TRACE
```
如果只想记录SQL，不想记录结果，只要将日志级别提升到 DEBUG（对应JDK日志的FINE）
```
log4j.logger.org.mybatis.example=DEBUG
```