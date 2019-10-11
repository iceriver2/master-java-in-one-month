> 2019-10-10 在gitee有一个项目，是一个 SSM+Maven 的Demo。本文档来自该项目的 README.md 。不过，由于没有相应的源码可以直接查看，所以，对 README.md 做了一些更改。

# MavenSSM

用Maven整合SSM框架，fork自 https://github.com/JYRoy/MavenSSM 。现已转移到我的 gitee 下。  
整体构建过程参看博客 https://www.cnblogs.com/jyroy/p/11111328.html 。但其实根本没介绍过程，作者直接一次性添加了所有文件。

# 理解

作为一个 fresh bird 接触这个项目，项目虽小，确是个 Maven + Spring + SpringMVC + MyBatis 的 webapp 项目。
多个因素出现在一起，需要整理一下。但是其中的过程确实有点理不清。

按博客的介绍：
- Maven统一维护jar包。  
- SSM（Spring+SpringMVC+MyBatis）框架，主要是Spring与MyBatis的整合、SpringMVC与MyBatis的整合。
  - Spring与MyBatis整合，通过Spring实例化Bean，然后调用实例对象中的查询方法来执行MyBatis映射文件中的SQL语句的。
  - 加入Spring MVC后，通过前台页面执行查询方法，并能正确显示。

## 文件清单

先说目录
```
pom.xml
src/
  main/
    java/
      controller/
        StudentController.java(Class)
      dao/
        StudentDao.java(Interface)
        StudentDao.xml(MyBatis Mapper)
      po/
        Student.java(Class)
      service/
        StudentService.java(Class)
        impl/
          StudentServiceImpl.java(Interface)
    resources/
      applicationContext.xml(Spring configuration)
      db.properties(DB settings)
      log4j.properties(Logger settings)
      mybatis-config.xml(MyBatis configuration)
      springmvc-config.xml(Springmvc configuration)
    webapp/
      index.jsp
      WEB-INF/
        web.xml
        jsp/
          student.jsp
target/
```

最终显示结果的页面为 `/findStudentById?id=1` 。

java文件不是最重要，重要的是配置文件：
- `pom.xml` Maven配置文件，记录包依赖
- `src/main/resources/db.properties` 数据库连接参数
- `src/main/resources/log4j.properties` 日志参数
- `src/main/resources/mybatis-config.xml` MyBatis配置，只有一个别名
- `src/main/resources/applicationContext.xml` Spring主配置文件，读取 db.properties 文件的配置和数据源配置，配置事务管理器、开启事务注解。配置用于整合 Mybatis 框架的 Mybatis 工厂信息，定义 mapper 扫描器来扫描 DAO 和 Service 层的配置。
- `src/main/resources/springmvc-config.xml` SpringMVC配置，用于扫描 @Controller 注解的包扫描器、注解驱动器以及视图解析器。
- `src/main/webapp/WEB-INF/web.xml` 配置 spring 的文件监听器、编码过滤器以及 Spring MVC 的前端控制器等信息。

注：当 MyBatis 与 Spring 配合使用时，以 Spring 的配置为准，因为 MyBatis 不用再自己生成 SqlSession 了，而是由 Spring 生成。

## 配置文件的关系

web.xml
```xml
	<!-- 配置加载Spring文件的监听器 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>
	<listener>
		<listener-class>
			org.springframework.web.context.ContextLoaderListener
		</listener-class>
	</listener>
	<!-- 编码过滤器 -->
	<filter>
		<filter-name>encoding</filter-name>
		<filter-class>
			org.springframework.web.filter.CharacterEncodingFilter
		</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encoding</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	<!-- 配置SpringMVC前端核心控制器 -->
	<servlet>
		<servlet-name>springmvc</servlet-name>
		<servlet-class>
			org.springframework.web.servlet.DispatcherServlet
		</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:springmvc-config.xml</param-value>
		</init-param>
		<!-- 配置服务器启动后立即加载SpringMVC配置文件 -->
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>springmvc</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```

**webapp 总是从 `web.xml` 开始。**

`web.xml` 中重点有3个内容：
- `<listener>`，引入 org.springframework.web.context.ContextLoaderListener ，是启动和关闭 Spring 的 root WebApplicationContext 的 bootstrap listener ，只是简单的委托给 ContextLoader 和 ContextCleanupListerner 。ContextLoaderListener 只有2个方法：`contextDestroyed(ServletContextEvent event)`，	`contextInitialized(ServletContextEvent event)` 。作用暂时不明。
- `<filter>` 引入 org.springframework.web.filter.CharacterEncodingFilter ，从内容看，是使得根目录下所有内容，全部使用 UTF8 编码。不重要。
- `<servlet>` 引入 org.springframework.web.servlet.DispatcherServlet ，并将 `/` 下的所有URL都转给 `DispatcherServlet` 。`DispatcherServlet` 的初始化参数时 `springmvc-config.xml` 。需要注意的是，本部分设置还使用了一个参数 contextConfigLocation，而这个参数的值是 `applicationContext.xml` 。

`web.xml` 引出 `applicationContext.xml` 和 `springmvc-config.xml` 。

从常识判定，可能是两种情况：要么 DispatcherServlet 参数默认是 `applicationContext.xml`，允许 `springmvc-config.xml` 取代；要么 DispatcherServlet 的参数默认是 `applicationContext.xml`，允许 `springmvc-config.xml` 合并。  
总归，**DispatcherServlet从 `applicationContext.xml` 和 `springmvc-config.xml` 中读取参数，并接管了所有的 `/` 下的URL访问。**
> 从某处看到这个解释说，applicationContext.xml 使用于整个应用，而springmvc-config.xml只用于web应用。

`applicationContext.xml` 是Spring的主配置。主要的内容：
- `<bean class="org.apache.commons.dbcp.BasicDataSource">` 引入 dataSource 。
- `<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager">` 引入了事务管理器，并将 dataSource 作为参数传入。
- `<tx:annotation-driven transaction-manager="transactionManager"/>` 开启事务注解 。(事务管理器，是否就是用于解析注解的？)
- `<bean class="org.mybatis.spring.SqlSessionFactoryBean">` 配置MyBatis工厂 SqlSessionFactory ，并将 dataSource 作为参数传入。
- `<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">` 配置mapper扫描器，扫描 `dao/` 下的Mapper文件。本项目中只有一个 StudentDao.xml ，定义一个 dao.StudentDao 的映射，有一个 findStudentById 的方法。
- `<context:component-scan base-package="service"/>` 扫描 service 目录。注意：此处未引入包，直接扫描，看起来 service下的文件（接口）应该是能直接被识别的。


`springmvc-config.xml` 是SpringMVC的主配置。主要的内容：
- `<context:component-scan base-package="controller"/>` 扫描 `controller/`下的 @Controller注解的类
- `<mvc:annotation-driven/>` 加载注解驱动
- `<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">` 配置视图解析器, 解析 `/WEB-INF/jsp/xxx.jsp` 

注意：`applicationContext.xml` 除了处理系统级的内容（如数据库、事务管理器）之外，还处理了： dao 的映射文件，service 下的文件。`springmvc-config.xml` 处理的是 controller, 视图。  
以标准的MVC模型来说，分工还是很明确的： **Spring 负责 Dispatcher 和 Model ， SpringMVC 控制 Controller 和 View 。**


至此，所有的配置文件都解释完毕。引入顺序是
```
web.xml
  |----> applicationContext.xml
                |----> mybatis-config.xml
                |----> dao/xxx.xml
  |----> springmvc-config.xml
```

## 执行的过程

启动
- JVM启动，运行 Tomcat，读取 `web.xml` 。
- 从 `web.xml` 中得知，所有URL交由 org.springframework.web.servlet.DispatcherServlet 处理。
- 读取 `applicationContext.xml`，读取 `springmvc-config.xml` ，生成 DispatcherServlet 实例。(不确定是否还生成其他实例？)

请求
- 请求 `/findStudentById?id=1` 
- DispatcherServlet 寻找对应的 Controller 。只有一个 StudentController，其有一个方法 `findStudentById()`。从理论上推断，应该都都不符合的，但是，该方法有一条注解 `@RequestMapping("/findStudentById")`，也就是说，做了路径改写。
- 执行 `StudentController.findStudentById()` 方法，传入参数 id。该方法会调用 `studentService.findStudentById()`，其中 studentService 是一个 StudentService 的实例，已经通过 `@Autowired` 做了注解。
- StudentService 只是一个接口，无法实例化，因此，需要找一个能实例化的子类，也就是 StudentServiceImpl 。调用 `StudentServiceImpl.findStudentById()`，这个方法又会调用 StudentDao 的一个实例的方法 `studentDao.findStudentById()`。 TODO1
- `studentDao.findStudentById()`执行sql，作为一个 Student 类型返回结果。
- 在之前的 `StudentController.findStudentById()`，其实有两个参数，一个是id，另外就是不明用途的 Model 参数。将 Student 类型的结果，通过 `model.addAttribute("student", student)` 赋值给变量 student。 TODO2
- 最后，在 `student.jsp` 文件中，展示 student 的值。


在上面的步骤中，有2个TODO部分，解释一下。
- TODO1：StudentDao.java 中定义的也是接口，无法实例化。貌似卡住了。猜测：此时调用的 StudentDao 其实是 Mapper 出来的 StudentDao 实例，而不是接口 StudentDao 。也就是说，博主在此处应该是犯了一些错误，根本没有搞清楚关系。StudentDao.java文件是可以删除的。
- TODO2：很莫名其妙的，完全没有看到有调用jsp的动作，结果就把结果渲染出去了。猜测：应该是在 `springmvc-config.xml` 中指定了视图解析器，所以，当 `model.addAttribute()` 时，是把 Student 类型的结果，直接赋值给 student.jsp 了。

这样一来，基本上就可以解释的通了。

## 没说到的隐含条件

在以上的推断当中，有两个隐含的条件没有提到，但其实很重要。

第一，是Maven项目的约定结构。  
以本项目来说， `main/java` 存储源码文件（其中又按Controller/Dao/Service分类），`main/resources/`存储资源文件（配置），`main/webapp`存储JSP文件（View文件），`target/`存储编译后的字节码文件。所有的 package 声明都默认以 `main/java` 为根目录。

第二，MVC模式的组件。  
MVC的组件应该包括：Model， Controller，View。  
在本项目中，Model就是 MyBatis 和 `main/java/dao` 目录下的文件，View 就是 `main/webapp/jsp` 下的文件，Controller就是 `main/java/controller` 下的文件，而Controller的Action 则是Controller的方法。

# 用到的Spring的组件

TODO

## 出现的注解

# 重构项目

我觉得，这个项目有一些不太好的地方，就是 findStudentById 这个方法名称出现的频次太高，以至于根本不知道是调用的哪一个。

打算重构的目标:
- 将MVC各个组件的 findStudentById 改为不一样的名称，如带有其所在组件的名称
- 先前的猜测可以删除的文件 StudentDao.java，尝试删除了看看。

## 删除 StudentDao.java

有点意外，删除这个文件 StudentDao.java ，会直接导致无法运行。在 StudentServiceImpl 中明确 `import dao.StudentDao` 。

这确实不同于单用 MyBatis 。单用 MyBatis，可以设置 Mapper 文件后，就可以直接将 Mapper 文件作为类用
```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  Blog blog = mapper.selectBlog();
}
```

在本项目中，没有 SqlSessionFactory 出现，而是在 `applicationContext.xml` 中出现了一个 SqlSessionFactoryBean 。

本项目最大的不同，可能是在 import 这一行。JVM必定去寻找这个包。

虽然删除 StudentDao.java 做不到，但是，**`StudentDao.xml` 却可以改成其他名称（如 `SutdentDaoOtherName.xml`）是可以的**，只要其内容保持不变即可。  
换句话说，StudentDao.java 提供的接口是给 Spring 用的，而 `StudentDao.xml` 可以看作 StudentDao 的子类，叫什么名称真的无所谓，只要是 StudentDao 类型即可。 

## 方法更名

第一步，将 StudentController 中的 `findStudentById` 改为 `findStudentByIdInController` 。成功。

第二步，将 service 下两个文件的 `findStudentById` 改为 `findStudentByIdInService`。成功。

第三步，问题就来了。

首先，只该 StudentDao.java 和 StudentServiceImpl 的 `findStudentById` 为 `findStudentByIdInDao` 。报错500，提示是: Invalid bound statement (not found): dao.StudentDao.findStudentByIdInDao 。  
即使将 StudentDao.xml 的 select 语句的 id 也修改为 `findStudentByIdInDao` 也是 500 。  
设置往上加一个方法，也是如此。

更加郁闷的是，经过这一番修改，原来好好能运行的项目，现在居然不能运行的，而且是报同样的错误。MD！

在网上搜索了一下，出现 Invalid bound statement (not found) 的原因，就是 Mapper 文件写的不好，只存在低级或更低级的原因。

关于方法更名，我的猜测应该是对的。不过，现在实在不知道问题出在哪儿，已经麻爪了～

## 调试

经过一番尝试，已经大致知道原因了：  
在 dao/ 下面有两个文件，一个是接口文件 StudentDao.java ，一个是映射文件 StudentDao.xml 。
通过打印信息到输出，可以确定知道 StudentDao.java 已经将接口成功引入文件，但是，在获取映射器 StudentDao 时出了问题，未找到这个映射器的实例，就出现所谓的 Invalid bound statement 。

网上找了一圈，很多胡说八道的。
最后终于看到一个[明白人](https://blog.csdn.net/k469785635/article/details/77532512)：因为 Maven 没有找到这个 Student.xml ！

我强行解释一下：  
首先，一定要明白的是，Maven项目的状态分为编译前和编译后。  
编译时，Maven依靠 pom.xml 获得编译所需的信息和资源。编译后，当运行 target/ 中字节码文件时，Spring/MyBatis之流才能起作用。  
而之前的错误，恰恰是因为Maven丢了 StudentDao.xml 文件，没有复制到生成 target/ 中。（这一点，我通过对比 target/ 的文件列表，已经确认了。）

至此，问题解决，上方的“方法更名”，也是完全没有问题的，符合预期。
