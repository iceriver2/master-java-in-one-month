> 2019-10-02 读取各种pdf，熟悉JSP相关内容时的简要笔记。拒绝大段抄录，没有时间。第一本书《Java Web轻量级开发全体验》

> 2019-10-03 按前后端分离的趋势，再继续混写，意义不大。应该直接跳过JSP的混写阶段，进入纯数据后端阶段。

> 2019-10-04 《Java Web轻量级开发全体验》这本书看完了，有点粗略，书中的代码也没有实验一下，但对JSP的基本概念算是建立起来了。稍候再整理精简一下。

SSH 通常指的是 Struts2 做控制器(controller)，spring 管理各层的组件，hibernate 负责持久化层。  
SSM 则指的是 SpringMVC 做控制器(controller)，Spring 管理各层的组件，MyBatis 负责持久化层。  
共同点：Spring依赖注入DI来管理各层的组件；用面向切面编程AOP来管理事物、日志、权限等。
> SSM相对比较轻量级，也是最新的趋势。


Maven是一个构建工具，其核心思想是 POM（项目对象模型）。POM文件是以XML文件的形式表述项目的资源，如源码、测试代码、依赖包等。 pom.xml 应该位于项目的根目录。
Maven的功能：生成源码；生成文档；编译源码；打包；部署。  
运行Maven只需在命令行执行mvn命令，传入一些参数。如`mvn install`。  
Maven的本地仓库在本机的用户目录下，中央仓库在maven网站。Maven可以从中央仓库下载依赖。  
Maven有三个内嵌的构建生命期：default, clean, site 。default生命期关注编译和打包，clean负责清理，site生命期关注文档。default生命期又有几个重点：validate验证；compile编译源码；test测试；package打包；install将项目打包后安装到本地仓库；deploy发布。


Ant是另一款构建工具。
> 从目前的一篇文章来看，Ant 类似于 Node 的 gulp 之类的工作流工具。

----------------------------------------------

JSP页面被**编译**成Java Servlet。
JSP本质上还是一个 Servlet，编译后会变成一个 HttpJspPage 类（这是HttpServlet的一个子类）

前后端分离，Servlet 只需返回 JSON 即可。


JavaBeans组件  EJB(Enterprise JavaBeans)组件

JDBC(Java DataBase Connectivity)
JDBC-ODBC 支持多数数据库，Oracle，Sybase，infomix，MySQL，MSSQL等。


支持JSP的服务器 Tomcat,WebLogic,WebSphere 。（一个比一个强大）  
- Tomcat，是支持JSP和Servlet的容器，也是Web服务器。占用资源小，扩展性好。Tomcat小型轻量，在中小型系统和并发量不大时实用。（为什么跟Apache Http一个尿性？） Tomcat 需要JDK支持。最新版本 9.0 。
- WebLogic原是BEA公司产品，被Oracle买了。WebLogic Server支持企业级、分布式的Web应用，支持包括JSP、Servlet、EJB在内的JEE体系。WebLogic功能强大，操作简单，大量用于电子商务。最新版本 12c 。
- IBM WebSphere是一系列产品，包括 WebSphere Performance Pack、Cache Manager、Studio、Web应用开发工具以及WebSphere Application Server。其中 Application Server基于Java的应用环境。WebSphere Application Server是行业领先的企业级Web应用服务器，支持行业内最广泛的平台，能够为不同类型的应用提供不同的解决方案。它使用基于开放标准的编程模型，包括 JavaEE, OGSi应用，Web2.0和Mobile、Java Batch、XML、Service Component Architecture(SCA)、Communications Enabled Applications(CEA)、Session Initiation Protocol(SIP)和动态脚本。

tomcat可以安装，也可以解压后运行。

JSP引擎将JSP页面转为Java文件（即Servlet），再被编译为字节码，并响应请求。
JSP文件的后缀名就是 `.jsp` 。（JSP的尿性，与ASP很像，尤其是顶部的格式声明。）

基于Eclipse生成的项目的目录包括：build, src, WebContent，其中JSP保存在 WebContent 中。

混写的JSP页面
```jsp
<%@ page contentType="text/html;charset=utf8" %> <!--JSP指令-->
<%@ page import="java.util.*" %> <!-- JSP指令 -->
<html>
    <head></head>
    <body>
        <%! String s = null; %> <!-- 变量声明 -->
        <%
            s = request.getParameter("somename");
            if (s == null) {
                //...
            } else {
                // ...
            }
        %>
    </body>
</html>
```

目前发现JSP标签的写法，与PHP语法差不多
```jsp
<%-- JSP的注释 --%>

<!-- 猜测是针对整个页面的指令 -->


<!-- 变量、方法声明 -->
<%! String s=null; %>

<!-- 代码段 -->
<%
    int i =0;
    int j =1;
%>

<!-- 代码表达式 -->
<%=i%>
```

**JSP指令**
```jsp
<!-- page指令，针对本页的设置。包括 language, import, contentType, session, buffer, autoFlash, isThreadSafe, info, errorPage, isErrorPage -->
<%@ page contentType="text/html;charset=utf8" %>
<%@ page import="java.util.*", "java.awt.*" %>

<!-- include指令，静态包含文件 -->
<%@ include file="path/to/something"  %>

<!-- include动作指令，动态包含文件 -->
<jsp:include page="path/to/something" />
<jsp:include page="path/to/other">
    <jsp:param name="key" value="value">
</jsp:include>

<!-- forward动作指令，重定向(也可以使用 response.sendRedirect -->
<jsp:forward page="somepage">
<jsp:forward page="somepage">
    <jsp:param name="" value="">
</jsp:forward>

<!-- useBean动作指令，创建并使用一个JavaBean组件 -->
<jsp:useBean id="beanId" scope="page/request/session/application" class="package.classname">
</jsp:useBean>

<!-- setProperty动作指令，设定Bean中属性的值。可以在 useBean 中使用或之后使用 -->
<jsp:setProperty name="beanName" property="*" />
<jsp:setProperty name="beanName" property="property" />
<jsp:setProperty name="beanName" property="property" param="" />
<jsp:setProperty name="beanName" property="property" value="" />

<!-- getProperty动作指令，获得Bean中属性的值，再输出 -->
```

汉字在传输时被编码，拿到后需要解码。
```java
// 转换过程，可以写成一个方法
String tmp = request.getParameter("somefield");
byte[] tmpByte = tmp.getBytes("ISO-8859-1"); // 不知道这个编码是不是应该是UTF8
tmp = new String(tmpByte);
```

JSP的内置对象：request、response、session、application、out、config、pageContext 。重点是 request、response、session、application、out。 注意：**javax.servlet是JEE的包。**
- request对象与 HttpServletRequest 类关联，是 javax.servlet.ServletRequest 的子类，用于处理请求数据。
- response对象与 HttpServletResponse 类关联，用于处理响应数据。
- session对象与 HttpSession 类关联，用于处理Session。
- application对象与 ServletContext 类关联，一旦创建就一直存在，用于处理全局的数据和对象。
- out对象使用 PrintWriter 类向客户端输出数据。
- config对象是 ServletConfig 类的一个对象，是JSP配置处理程序的句柄，在JSP页面范围内有效。
- pageContext 用来管理属于 JSP 中特殊可见部分中已命名对象的访问。

request对象所属的类实现了 javax.servlet.http.httpServletRequest 接口，此接口位于 servlet-api.jar 包中。  
request对象的常用方法：getAttrbute(), getAttributeNames(), getCharacterEncoding(), getContentType(), getContextPath(), getLocalAddr(), getLocalName(), getMethod(), getParameter(), getParameterMap(), getParameterNames(), getParameterValues(), getProtocol(), getQueryString(), getRemoteAddr(), getRemoteHost(), getRemotePort(), getRequestURI(), getRequestURL(), getServerPort(), getServletPath(), getSession(), removeAttribute(), setAttribute(), setCharacterEncoding()。
> 有点奇怪，JSP部分 GET数据和POST数据的吗？如PHP的 $_GET 和 $_POST 可能存在同名参数。

response对象代表响应，它实现了 javax.servlet.ServletResponse 接口，该接口位于 servlet-api.jar 包中。  
response 对象的常用方法：flushBuffer(), getBufferSize(), getCharacterEncoding(), getContentType(), getOutputStream(), getWriter(), sendRedirect(), setBufferSize(), setCharacterEncoding(), setContentType(), setHeader()

Cookie在安全性较高的场合不要使用。浏览器只允许存放300个Cookie，每个站点最多20个，每个Cookie最大4KB。  
Cookie的使用，先创建一个 Cookie对象，然后通过 response.addCookie() 将其发送到客户端，通过 request.getCookies() 方法查找获取。  
Cookie对象自身也有一些方法可以设置属性，如 getComment()/setComment(), getDomain()/setDomian(), getMaxAge()/setMaxAge(), getName()/setName(), getPath()/getPath(), getSecure()/setSecure(), getValue()/setValue().

session对象表示客户端与服务端的会话，实现了 javax.servlet.http.HttpSession接口，此接口位于 servlet-api.jar 包中。  
session对象的常用方法有：getAttribute(), getAttributeNames(), getMaxInactiveInterval(), getServletContext(), invalidate(), isNew(), removeAttribute(), setMaxInactiveInterval()

application对象在多个程序或多个用户之间共享数据。服务器一启动，就会自动创建 application 对象，会一直持续到服务器关闭。(注：应该只有持续运行的服务器环境，才能维持application，如Node或Tomcat，而PHP就不会，因为环境无法由PHP控制。)  
application对象实现了 javax.servlet.ServletContext 接口，此接口位于 servlet-api.jar 包中。  
application对象的常用方法：getAttribute(), getAttributeNames(), getContext(), getInitParameter(), getInitParameterName(), getRealPath(), getServletContextName(), removeAttribute(), setAttribute()

out对象向客户端输出内容，由 Web容器指定为 javax.servlet.jsp.JspWriter 类的一个子类，javax.servlet.jsp.JspWriter类位于 jsp-api.jar组件包中，其继承了 java.io.Writer 类。  
out对象的常用方法包括：clear(), clearBuffer(), close(), flush(), getBufferSize(), isAutoFlush(), print(), println()

config对象是 servletConfig 类的一个对象，用于处理当前页面的句柄（？），仅在页面范围内有效，使用的较少。



JSP使用JDBC连接数据库。连接池技术扩展了JDBC的能力。

JDBC API是一个统一的标准接口，这样可以屏蔽异种数据库之间的差异。
JDBC可以与ODBC协同工作。

java.sql.* 包定义的一系列类、接口、异常等，用于实现对数据库的访问。

JDBC的四种驱动
- JDBC-ODBC Bridge 访问ODBC数据源。需要安装ODBC驱动。访问过程是：JSP --> JDBC-ODBC --> ODBC --> Database
- JDBC Native Bridge 。需要安装特定数据库的驱动，在通过 JDBC 访问，性能比前者更好。访问过程是： JSP --> JDBC-ODBC --> Database
- JDBC-Network Bridge 。不需要安装驱动，JDBC通过网络连接数据库，多用于Web应用。访问过程是： JSP --> JDBC-Network --> Database
- Pure Java JDBC Drive 。驱动程序安装在客户端，客户端直接访问数据库，这个安全性不好。 


如何安装JDBC？
从书中猜测（书中是windows），下载JDBC驱动压缩包，得到：sqljdbc.jar 。在应用中使用，只要将 sqljdbc.jar 拷贝到 Web 应用的 WEB-INF/lib 目录。在全局使用，需要考虑到 Tomcat 的 lib 目录。

使用SQL之前必须先引入 sql 包： `import java.sql.*` 。

使用 DriverManager.getConnection 方法获得数据库链接。
`DriverManager.getConnection("jdbc:mysql://localhost:3306:testDatabase", "root", "password")`

连接数据库时需要捕获异常。


Connection定义了5个事务级别：TRANSATION_NONE, TRANSATION_READ_COMMITTED, TRANSATION_READ_UNCOMMITTED, TRANSATION_REPEATABLE_READ, TRANSATION_SERIALIZABLE 。

一个Connection对象表示一个连接。Connection默认自动提交。  
Connection的常用方法：close(), commit(), createStatement(), getAutoCommit(), getMetaData(), getTransationIsolation(), isClosed(), isReadOnly(), prepareCall(), prepareStatement(), rollback(), setAutoCommit(), setHoldability(), setReadonly(), setTransactionIsolation() 。


Statement对象表示SQL语句。存在三种Statement对象，一是Statement，用于执行基本的SQL语句；二是 PreparedStatement，继承Statement，用于提供可以与查询信息一起预编译的语句；三是CallableStatement，继承PreparedStatement，用于执行数据库中的存储过程。

Statement用于执行静态SQL语句并返回它所生成的对象。默认情况下，同一时刻每个Statement对象只能打开一个 Result 对象。  
Statement接口的常用方法：addBatch(), cancel(), clearBatch(), close(), execute(), executeBatch(), executeQuery(), executeUpdate(), getConnection(), getMaxFieldSize(), getMaxRows(), getMoreResults(), getResultSet(), getUpdateCount(), isClosed(), setMaxFieldSize(), setMaxRows()

PreparedStatement 用于表示预编译SQL语句的对象。SQL语句被预编译并且存储在 PreparedStatement 对象中，然后多次被执行。  
PreparedStatement 扩展了 Statement 接口。除了 Statement 的方法外，PreparedStatement 增加了一系列 setXXX 方法。

CallableStatement 是用于执行SQL存储过程的接口。存储过程的输入参数值，必须在执行前注册类型，其值是通过本类的 get 方法获取的。注册输出参数用 registerParameter() 方法。  
CallableStatement 返回一个 ResultSet 对象或多个 ResultSet 对象。多个 Result 对象是使用从 Statement 中继承的操作处理的。

ResultSet 表示数据库结果记录集。ResultSet对象具有指向其当前数据行的指针，通过 next() 返回下一个结果。  
ResultSet 接口提供从当前行返回值的方法（如 getBoolean(), getLong() ）。  
ResultSet的常用方法： absolute(), afterLast(), beforeFirst(), cancelRowUpdates(), close(), deleteRow(), first(), getXxxx(), getXxxx(), getConcurrency(), getRow(), getType(), insertRow(), isAfterLast(), isBeforeFirst(), isFirst(), isLast(), last(), moveToInsertRow(), next(), previous(), relative(), updateXxx(), updateRow()


JDBC规范（3.0）的数据类型转换
| JDBC类型 | Java类型 | JDBC类型 | Java类型 |
|----------|---------|---------|----------|
| CHAR | java.lang.String | BINARY | byte[] |
| VARCHAR | java.lang.String | VARBINARY | byte[] |
| LONGVARCHAR | java.lang.String | LONGVARBINARY | byte[] |
| NUMERIC | java.math.BigDecimal | DATE | java.sql.Date |
| DECIMAL | java.math.BigDecimal | TIME | java.sql.Time |
| BIT | boolean | TIMESTAMP | java.sql.Timestamp |
| BOOLEAN | boolean | CLOB | java.sql.Clob |
| TINYINT | byte | BLOB | java.sql.Blob |
| SMALLINT | short | ARRAY | java.sql.Array |
| INTEGER | int | DISTINCT | 基础类型映射 |
| BIGINT | long | STRUCT | java.sql.Struct |
| REAL | float | REF | java.sql.Ref |
| FLOAT | double | DATALINK | java.net.URL |
| DOUBLE | double | JAVA_OBJECT | 基础Java类 |


数据库连接池负责分配、管理和释放数据库连接，允许重复使用一个现有的数据库连接。

Tomcat7内置的数据库连接池是 DBCP（Database Connection Pool），DBCP 是 jakarata Commons 的一个子项目。DBCP的组件包是 tomcat-dbcp.jar，位于 Tomcat7 的lib目录。

JNDI(Java Naming and Directory Interface)

在 Tomcat7 的 conf/server.xml 文件的 Host 部分加入 Resource 配置连接池。
```xml
<Resource name="" auth="" type="" url="" username="" password="" maxActive="" maxIdle="" maxWait="">
```


DatabaseMetaData用于得到有关数据库的信息。不同的数据库实现的方式不同。有些数据库以 ResultSet 对象的形式返回信息列表，有些使用 String 。  
DatabaseMetaData 的常用方法： getURL(), getUserName(), getTables() 。

ResultSetMetaData 用于获得 ResultSet 的类型和属性信息。  
常用方法有： getColumnCount(), getColumnName(), getColumnTypeName() 。



JavaBean 是一种组件技术。
> 注：如果没记错的话，这种技术应该已经落伍了，因为需要写大量的 getter/setter 。可能当初出现的原因，是为了可以方便使用某些工具来处理吧。

JavaBean分为两种：可视化JavaBean、非可视化JavaBean。可视化JavaBean是指带有界面的类，如按钮、文本框等，CS模式多用这种，类似控件；非可视化就是没有界面，Web开发多用这种。

JavaBean类的方法命名规则：
- getXXX()/setXXX() 获取和设置变量值
- 如果是 boolean 数据，多一种 isXXX()
- 访问方法和构造方法，都是 public

很多IDE能自动完成编译。

部署JavaBean
- 部署其class，将class文件复制到当前 Web应用的 WEB_INFO/classes 目录下，如果该class属于某个包，则按包的路径存放。
- 部署jar。将jar复制到 WEB-INFO/lib 目录。

在JSP中使用JavaBean，使用 useBean 指令。
```jsp
<jsp:useBean id="JavaBean实例的名称" class="Javabean类名" scope="">
    <jsp:getProperty name="JavaBean实例的名称" property="属性名称" />
    <jsp:setProperty name="JavaBean实例的名称" property="属性名称" value="">
</jsp:useBean>
```


Servlet是用Java编写的运行在Web服务器中的程序，也是一个类。
Servlet 由Web服务器引擎负责编译执行。当客户端访问 Servlet 时，服务器将启动一个线程，加载该 Servlet ，由 Servlet 接受 HTTP 请求并做出响应。

Servlet的生命周期有3个过程：
- Servlet 的初始化。Servlet 实际是一个类，需要由Web服务器引擎生成这个 Servlet 类的对象，并加载这个对象，通过这个对象的 init() 完成初始化工作。
- 生成的 Servlet 类的对象调用 service() 方法来响应请求。每发生一次请求，service()都会被调用一次。
- Servlet 类的对象常驻内存直至Web服务器关闭。当Web服务器关闭时，将调用 Servlet 类的对象的 destory() 方法来消除此对象。

JSP页面对应的 Servlet 继承了 org.apache.jasper.runtime.HttpJspBase 类。（注：应该是因为使用的是 Tomcat的原因，JSP才继承 HttpJspBase。如果是WebLogic之类，应该不是。）
```
java.lang.Object
--javax.servlet.GenericServlet
----javax.servlet.http.HttpServlet
------org.apache.jasper.runtime.HttpJspBase
```

javax.servlet.http.HttpServlet 类位于 servlet-api.jar 包，servlet-api.jar 包位于 Tomcat/lib 目录。  
Tomcat7使用的Servlet有两个包：javax.servlet、javax.servlet.http 包。javax.servlet包提供了实现 Servlet类的基类和接口，并与Servlet容器相容。javax.servlet.http包提供了基于HTTP协议实现Servlet类的基类与接口，并与Servlet容器相容。

重点是 javax.servlet.http.HttpServlet类（抽象）。  
HttpServlet用于创建一个基于HTTP协议的Servlet，继承它的类需要实现这些方法：doGet(), doPost(), doPut(), doDelete(), init()/destory(), getServletInfo()。此外，还可以重载 service()，此方法处理标准的HTTP请求，并转发需求到各个 doXXX() 方法。
```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp)

protected void doPost(HttpServletRequest req, HttpServletResponse resp)

protected void service(HttpServletRequest req, HttpServletResponse resp)

protected void init(ServletConfig config)

public void destory()
```

部署Servlet前，先编译。编译完成后，将 class文件放入 WEB-INFO/classes 目录。之后，还需要在 WEB-INFO/web.xml 中加入Servlet的配置。
```xml
<!-- 注意：这不是两种格式！一个servlet的配置，就需要2个部分。 -->
<servlet>
    <servlet-name>Servlet Name</servlet-name>
    <!-- 这是class文件的名称 -->
    <servlet-class>Servlet class</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>Servlet Name</servlet-name>
    <!-- 这是class文件的路径，相对于当前应用的路径 -->
    <url-pattern>Servlet path</url-pattern>
</servlet-mapping>
```


JSTL(JSP Standard Tag Library,JSP标准标签库)专为Web开发定制，可用于页面代码的基本输入/输出、流程控制、XML文件处理及SQL处理、格式化等。
> 注：应该就是JSP的模板引擎了，类似PHP的smarty。简单了解下即可。

JSTL由Apache的jakarata小组开发，是开源的标准标签库。
JSTL中的标签有五类：
- 核心标签，包是 jstl/core，页面中的标签 c
- XML操作标签，包是 jstl/xml，页面中的标签 x
- 格式化/国际化标签，包是 jstl/fmt，页面中的标签 fmt
- 数据库操作标签，包是 jstl/sql，页面中的标签 sql
- 函数标签，包是 jstl/functions，页面中的标签 fn

下载JSTL的压缩包，解压后得到 jstl.jar 和 standard.jar 包。将这两个文件复制到 WEB-INFO/lib 目录下。
> 注：很典型的早期开发风格，必须自行获取库，并手动安装或拷贝。而现在的风格时，借助工具，如 node的npm，不知道Java的是什么 。

使用JSTL标签，需要在页面顶部引入，然后按需使用各个标签。
```jsp
<!-- 例如：使用核心库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 输出表达式的结果 -->
<c:out value="2*2" default="0" />
```


在JSP2.0之后，引入了 EL（Expression Language，表达式），它既可以与JSP中的Java代码结合使用，也可以和JSTL结合使用。 
> 注：EL 应该是 Apache 出的？百度了一下，发现“java el”这个组合，在2018年很少有新的文章出现，应该是淘汰了。

使用EL的格式 `${xxx}`，例如： `${sessionScope.user["name"]}` 。

EL表达式中的隐含对象有11个，包括：
- pageContext，对应 javax.servlet.Servletcontext，表示JSP页面的上下文
- pageScope，对应 java.util.Map，表示 page 范围内的属性
- requestScope，对应 java.util.Map，表示 request 范围内的属性
- sessionScope，对应 java.util.Map，表示 session 范围内的属性
- applicationScope，对应 java.util.Map，表示 application 范围内的属性
- param，对应 java.util.Map，等同 getParameter()
- paramValues，对应 java.util.Map，等同 getParameter()
- header，对应 java.util.Map，等同 getHeader()
- headerValues，对应 java.util.Map，等同 getHeader()
- cookie，对应 java.util.Map，等同 getCookies()
- initParam，对应 java.util.Map，等同 getInitParameter()


XML描述数据的结构和语义。要展示在浏览器中，可结合 XSL（eXtensible Stylesheet Language，可扩展样式语言）来实现。如果XML的数据格式要求严格，可用 DTD 或 XML Schema来约束。
> 注：XML内容，留待以后遇到再说。日常很少见到，除了Java相关的配置。

DTD文档对XML文件定义一些约束，如XML文件中的元素、元素的属性、元素的排列方式和顺序、元素能包含的内容等。DTD通常是以某种应用领域为定义的范围，如医学、建筑、工商、行政等。DTD可以是独立的文件（称为外部DTD）或在XML文件中设定（称为内部DTD）。
```xml
<!-- XML文件 -->
<?xml version="1.0" encoding="gb2312">
<users>
    <user>
        <id>1</id>
        <name>Iceman</name>
        <age ageunit="岁">20</age>
    </user>
</users>
```
```xml
<!-- DTD文件 -->
<xml version="1.0" encoding="gb2312">
<!ELEMENT users (user+)>
<!ELEMENT user (id, name, age)>
<!ELEMENT id (#PCDATA)>
<!ELEMENT name (#PCDATA)>
<!ELEMENT age (#PCDATA)>
<!ELEMENT age ageunit CDATA "岁">
```

XML Schema也成为XSD（Xml Schema Definition），文件扩展名是 .xsd 。与DTD相比，XML Schema可以使用XML作为描述手段，具有很强的描述能力、扩展能力和处理维护能力。

```xml
<users xmlns="" xmlns:xsi="" xsi:shemaLocation="">
```

解析XML文件的接口技术有很多，常见的有SAX、DOM、JDOM。  
SAX是 Simple API for XML的缩写，是来自网络社区的标准，但几乎所有XML解析器都支持。  
DOM解析XML就是将XML读入内存，建立文档树，通过对文档树进行操作来完成对XML的操作。缺点是，可能会占用大量内存。  
JDOM是基于Java技术的开源项目，遵循80/20规则，用DOM和SAX 20%的功能来满足80%的需求。JDOM的内部逻辑基本与DOM相同。

JDOM的包结构
- org.jdom 包含所有的xml文档要素的Java类
- org.jdom.adapters 包含了与dom适配的Java类
- org.jdom.filter 包含了xml文档的过滤器类，可基于类型、名称、值等过滤
- org.jdom.input 包含了读取xml文档的类
- org.jdom.output 包含了写入xml文档的类
- org.jdom.transform 包含了将jdom xml文档接口转换为其他xml文档接口
- org.jdom.xpath 包含了对xml文档xpath操作的类

安装JDOM的过程，也是下载压缩包，解压后获得 jar文件，移入 WEB-INF/lib 目录。


jspSmartUpload组件处理文件上传。（第三方组件）
jspSmartUpload有4个主要的类：File, Files, Request, SmartUpload 。
- File包装了上传文件的所有信息，常用方法： saveAs(), isMissing(), getFieldName(), getFileName(), getFilePathName(), getFileExt(), getSize(), getBinaryData(),
- Files类表示所有上传文件的集合，可以获得上传文件的数目、大小等信息，常用方法：getCount(), getFile(), getSize(), getCollection(), getEnumeration()
- Request类功能等同内置对象request。常用方法：getParameter(), getParameterValues(), getParameterNames()
- SmartUpload类，完成上传和下载。常用方法：initialize(), upload(), save(), getSize(), getFiles(), getRequest(), setAllowedFilesList(), setDeniedFilesList(), setMaxFileSize(), setTotalMaxFileSize(), setContentDisposition(), downloadFile()


验证码图片，JavaBean的例子中使用的包有：java.awt.Color, java.awt.Font, java.awt.Graphics, java.awt.image.BufferedImage, javax.imageio.ImageIO 。
```java
// 大致过程
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics g = image.getGraphics()；
g.setColor(new Color(oxDCDCDC));
g.drawRect(0,0,width-1,height-1);

g.setColor(Color.black)
g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
g.drawString("h", 8, 17);
g.drawString("l", 20, 15);
g.drawString("o", 35, 18);
g.drawString("e", 45, 15);

g.dispose();

ImageIO.write(image, "JPEG“， system.out);
```


JavaMail对SMTP、POP3、IMAP提供支持。常用的几个类：
- Session，对会话的封装，常用方法：getDefaultInstance(), getInstance(), getProperties(), getStore(), getTransport()
- InternetAddress代表邮件地址，主要用到构造方法和getAddress()
- MimeMessage代表MIMIE邮件，实现了Message抽象类和MimePart接口，用到的方法：MimeMessage(), addRecipients(), getAllRecipinents(), getContent(), getContentType(), getFrom(), getSubject(), isMimeType
- Transport是邮件发送类，通过Session.getTransport()生成。常用方法：connect(), sendMessage()
- Store用于从邮件服务器接收邮件，常用方法是 getFolder()
- Folder是邮件文件夹类，常用方法：exists(), fetch(), getMessage()



Struts是实现了MVC的框架。
Struts2将一个Web系统的程序分为模型、视图、控制器三部分：模型由JavaBean、EJB组件等完成具体业务的组件构成；视图由JSP文件、POJO对象（可有可无）组成；控制器由Action实现。
> POJO对象仅是一个拥有若干属性及属性对应的 getXXX/setXXX 方法的对象，不做数据校验。数据教研可以在 Action 的 validate() 方法完成。

Struts上手容易；使用自带的RequestAware、SessionAware等接口代替Servlet接口；标签库强大；支持POJO（Plain Ordinary Java Object/Plain Old Java Object）表单；支持其他开源组建和技术扩展，如使用Spring管理Struts的action的创建，通过使用Spring可以充分利用Spring的依赖功能，并能很好地集成其他框架，如 Hibernate，iBatis 等。

Struts使用 struts.xml 作为配置文件，struts.xml 必须位于 CLASSPATH 中，实际工程中一般放于 WEB-INF/classes 目录。

一个请求在Struts框架中的处理有几个步骤：
- 客户端初始化一个指向Servlet容器（如Tomcat）的请求，这个请求经过一系列的过滤器（filter），接着 FilterDispatcher 被调用，FilterDispatcher 询问 ActionMapper 来决定这个请求是否需要调用某个 Action 。其中 ActionMapper 在Web应用启动时根据配置信息加载生成。
- 如果 ActionMapper 决定需要调用某个 Action，FilterDispatcher 把请求的处理交给 ActionProxy，ActionProxy 通过 Configuration Manager 询问框架的配置文件，找到需要调用的 Action 类，在 ActionProxy 创建一个 ActionInvocation 的实例。ActionInvocation 实例使用命名模式来调用，在调用Action的过程前后，涉及相关拦截器(intercepter)的调用。
- 一旦 Action 执行完毕，ActionInvocation 负责根据 struts.xml 的配置找到对应的返回结果。返回结果通常是（但不总是，也可能是另一个Action链）一个需要被表示的JSP。


Struts，一样是下载压缩包，解压后得到所有资料。包括4个目录：
- apps:5个war包实例应用，附带源码（war包可以用 jar xvf xx.war 解压）
- docs 文档
- lib 核心类库和依赖包
- src 源代码

使用 Struts 时，只要将 Struts/lib 目录的相关 jar 包复制到 webapp/WebContent/WEB-INF/lib 目录（其中 webapp 是项目名称）
10个重要的包：
- struts2-core-xxx.jar 核心包
- xwork-xxx.jar 核心包将其作为底层库存在
- ognl-xxx.jar Object Graph Navigation Language(OGNL)
- freemarker-xxx.jar 模板编写
- commons-logging-xxx.jar/commons-logging-api-xxx.jar 日志接口，可自动调用 Log4j 或 util.logging 
- common-fileupload-xxx.jar 文件上传
- commons-io-xxx.jar 补充类
- commons-lang-xxx.jar 工具集合
- javassist-xxx.GA.jar 操作字节码的组件包

Struts 允许在JSP中使用标签。
```jsp
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div>
${requestScope.message}
<s:form action="Login" method="POST">
    <s:textfield name="username" label="用户名">
    <s:password name="adminUserPassword" size="21" label="密码">
    <s:submit value="提交">
</s:form>
</div>
```

在 Struts 使用资源文件的做法
- 使用全局的资源文件。
- 使用包范围内的资源文件，在包的根目录下新建 package.properties 和 package_xx_XX.properties 文件
- 使用 Action 范围内的资源文件。在 Action 的包下新建与 Action类名相同的资源文件。
- 使用 `<s:i18n>`标志访问特定路径的 properties 文件。

当调用某个资源时，如`getText("user.message")`。Struts查找顺序时
- 查找 Action_xx_XX.properties 文件或 Action.properties
- 查找 Action 实现的接口，查找与接口同名的资源文件 MyInterface.properties
- 查找 Action父类的 properties 文件
- 判断当前 Action 是否实现接口 ModelDriven。如果是，调用 getModel() 获得对象，查找与其同名的资源文件
- 查找当前包下的 package.properties 文件
- 查找当前包的父包，直至最顶层包
- 在 Value Stack中，查找名为 user 的属性，转到 user 类型同名的资源文件，查找键为 message 的资源
- 查找在 struts.properties 配置的默认资源文件
- 如果还是没找到，输出 user.message 

Struts支持几种表达式语言
- OGNL，一种可以方便操作对象属性的开源表达式语言
- EL：JSP2.0集成的标准表达式语言
- Groovy：基于Java的动态语言，具有流行动态语言的一些特征
- Velocity：基于Java的模板匹配引擎

XWork在OGNL之上提供的最大改进就是支持值堆栈，OGNL允许上下文保存多个对象。在Xwork中，整个值堆栈就是上下文的根对象，不仅仅只根据表达式从栈中获取对象，还从对象中获取属性。

Struts标签分为通用标签和用户标签两类。通用标签用于控制页面的流程，用户标签用于显示数据。

Struts提供了许多进行数据校验的方法。其中，服务器端校验分为 Java注解教研（使用Annotation语法在源码上标记需要校验的内容），XML配置校验（使用XML文件配置需要校验的内容）。
> 注：一个简单的页面，被Java搞的好复杂。前端页面用了带标签的JSP，后端是使用了xwork的Java文件，中间还有数个配置文件，指明前后端的对应关系。
> 话说回来，现在主流的，应该是在前端完成校验吧？一方面避免将错误数据传给后端，另一方面真出错了的话，去服务器转一圈回来也是浪费时间。所以，校验这一块应该也是可以跳过。
```jsp
<!-- 客户端页面，终于看到激动人心的 xxx.action -->
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form method="post" action="questionAction.action" validate="true">
    <s:textfield name="name">
    <s:textfield name="age">
    <s:submit>
</s:form>
```
然后在 struts.xml 中的 `<struts>--<package>` 中加入 `<action>` 配置
```xml
<!-- questionAction对应的Action类是 action包中的 QuestionAction -->4
<!-- 同时,指定了成功和失败后的处理页面 -->
<struts>
    <package name="example" namespace="" extends="struts-default">
        <action name="questionAction" class="action.QuestionAction">
            <result name="success">success.jsp</result>
            <result name="input">basicInput.jsp</result>
        </action>
    </package>
</struts>
```
Action的处理代码
```java
package action;
import com.opensymphony.xwork2.ActionSupport;

public class QuestionAction extends ActionSupport {
    String name;
    int age;

    // 省略了 setXXX(), getXXX()
}
```
在编辑数据校验规则。XML配置文件需要存放在与Action类相同的目录中。本例的配置文件是 QuestionAction-validation.xml 。
```xml
<xml>
<!DICTYPE validators PUBLIC "xxxx" "http://www.opensymphony/xwork/xwork-validator-xx.xx.xx.dtd">
<validators>
    <field name="name">
        <field-validator type="requiredstring">
            <message>必须输入姓名</message>
        </field-validator>
    </field>
    <field name="age">
        <field-validator type="int">
            <param name="min">18</param>
            <param name="max">80</param>
            <message>年龄只能是18-80</message>
        </field-validator>
    </field>
</validators>
```


Struts内建支持一些数据类型的自动转换，包括：String，boolean/Boolean, char/Character, int/Integer, float/Float, long/Long, double/Double, date, arrays, collections。


又一个例子，从前端接受“姓名,年龄”的数据，并转为后端Person对象的姓名和年龄。
整个过程包括：（1）前端JSP页面 personinput.jsp；（2）Action类 PersonAction.java；（3）修改Web应用的 struts.xml 文件，增加这个应用的 action 配置；（4）编写 Person 类的程序；（5）编写 PersonConvert类（数据转换）程序；（6）编写转换对应关系的配置文件 PersonAction-conversation.properties 。
> 注：写类，都可以理解，哪怕是Person与PersonConvert分离，但动不动就配置，有点烦。



Hibernate是一种ORM（Object-Relation Mapping，对象-关系映射）中间件，可以将数据库表中的数据包装为Java对象，又可以将Java对象映射到数据库表的数据。Hibernate在Java对象与数据库中的关系型数据之间架起了桥梁。
Hibernate 还可以用作持久化和反持久化。

以往都是数据库的数据都是通过JDBC操作，需要开发人员对Java编程、数据库结构都有深入了解，特别是要进行事务处理、数据完整性约束等复杂的数据处理时。

Hibernate 是个产品套件，最常用的是 Hibernate Core for Java。这是 Hibernate 的核心组件。

**开发人员要熟悉所有的API是比较困难的，但是要理解核心的API，就可以进行快速开发了。**

Hibernate的核心API包括：
- 数据库操作相关的API：主要有Session, Transaction 和 Query，用来完成基本的创建、读取、更新、删除操作已经查询条件
- 配置文件操作API：主要是 Configuration 类
- 回调功能API：允许应用程序对一些事情的发生做出相应的操作，包括 Intercepter, Lifecycle, Validatable 。
- 扩展功能API：一些可以用来扩展 Hibernate 的映射机制的接口，例如 UserType, CompositeUserType, IdentifierGenerator等，这些接口可由用户程序实现

Hibernate启动时，通过 Configuration 接口获取目前的配置（如数据源、连接用户名及密码、数据库方言以及最大连接数等）并将这些配置加载到内存，当执行到初始化一个 SessionFactory 接口时，便一次性创建这个线程安全的 SessionFactory，每当需要执行持久化操作时，应用程序就会从这个工厂中取一个Session，直至其生命周期结束。在持久化的过程中，可能会用到 Transaction 接口进行事务处理，也可能会通过 Query 和 Criteria 接口进行查询，Hibernate底层会自动实现 Callback 接口。

Hibernate的安装也是下载压缩包（可以只下载 Hibernate Core）。解压后复制到 WEB-INF/lib 目录。


使用Hibernate进行开发，开发者一项重要的任务就是修改配置文件。  
Hibernate配置文件主要用于配置数据库连接和 Hibernate 运行时所需的各种属性。Hibernate配置文件允许传统的 hibernate.properties 文件和 hibernate.cfg.xml 文件同时存在。  
Hibernate配置文件的存放位置为类文件的跟目录，即 WEB-INF 目录。Hibernate初始化到类文件的根目录下寻找这个文件并读取其中的配置信息，其中 xml 格式文件的优先级高于 properties 格式文件，也就是说，如果两个文件同时存在，则 hibernate.cfg.xml 优先级更高。（Hibernate在 etc 目录中提供了常用的配置文件示例。）
```xml
<hibernate-configuration>
    <!-- SessionFactory配置 -->
    <session-factory>
        <!-- SQL方言 -->
        <property name="dialect">
            org.hibernate.dialect.SQLServerDialect
        </property>
        <!-- 数据库连接设置 -->
        <property name="connection.username">sa</property>
        <property name="connection.password"></property>
        <property name="connection.url">
            jdbc:sqlserver://localhost:1433;DatabaseName=testDatabase
        </property>
        <!-- JDBC连接池 -->
        <property name="connection.pool_size">10</property>
        <!-- 是否将SQL输出到日志 -->
        <property name="show_sql">true</property>
        <!-- 映射资源，配置文件必须包含其相对的全路径 -->
        <mapping resource="notebook/Notebook.hbm.xml">
    </session-factory>
</hibernate-configuration>
```
`<hibernate-configuration>`中可以包含多个用于创建 SessionFactory 示例的 `<session-factory>` 标记，每一个 SessionFactory 可以链接不同的数据源。建议每一个配置文件中只定义一个 SessionFactory，然后在主配置文件 hibernate.cfg.xml 中动态加载多个配置。  
`<session-factory>`标记中可以定义若干常用的属性标记`<property>`，包括：hibernate.connection.url（连接字符串）；，包括：hibernate.connection.username（用户名）；，包括：hibernate.connection.password（密码）；hibernate.dialect（方言）；hibernate.show_sql（是否在控制台输出SQL语句）。


一个例子。
src目录下有2个包，一个是hibernate包，其中仅有一个类 HibernateUtil，用于生成 Hibernate 会话；另一个是 notebook 包，对应数据库的 Notebook表的 Notebook类，表示对应关系的 Notebook.hbm.xml 配置文件。
WebContent目录下是所有的Web程序，其中 WEB-INF/lib 目录中有所有 Hibernate 的 jar包和数据库驱动程序的 jar 包，还需要 slf4j-log4j-xxx.jar 和 log4j-xxx.jar 包。
- 数据库建表 Notebook，包括 NoteId, Title, Content, Noter
- 建立表对应的持久化对象和配置文件。
  - Note表对应的持久化对象 `Notebook implements Serializable` ，只含有与表对应的属性。
  - 持久化配置 Notebook.hbm.xml 。根元素 `<hibernate-mapping>`，下含子元素`<class name="notebook.Notebook" table="Notebook" schema="dbo" lazy="false">`，再含孙元素 `<id name="noteId" type="java.lang.String" column="NoteId">`指定主键，其他孙元素`<property name="Title" type="java.lang.String" column="Title" not-null="true" length="">`
- 编写 hibernate.cfg.xml 配置文件。参见上文的`<hibernate-configuration>`的xml文件。其中，`<mapping>`标签表示映射资源，在 resource 中给出xml配置文件相对 WEB-INF/classes 的目录。
- 编写生成会话的类 HibernateUtil.java 。包括私有静态成员 SessionFactory，表示会话工厂；公有静态方法 getSession() 获取一个新的会话。
- 编写测试用的JSP页面。主要代码是：` Session sh = HibernateUtil.getSession(); NoteBook nb = new Notebook(); nb.setXXX(); sh.beginTransaction(); sh.save(noteBook); sh.getTransaction().commit(); `
> 注：重新梳理一下（假设Hibernate的数据库基本参数已经配置完成）：
> - 设置过程
>   - 数据库表 Notebook 建表；
>   - 表对应的对象 Notebook，属性与数据库表一一对应；
>   - 持久化配置 Notebook.hbm.xml 指定 Notebook 类与 Notebook 表的对应关系；
>   - 将 Notebook.hbm.xml 引入 Hibernate 的全局配置 hibernate.cfg.xml；
> - 执行过程
>   - 新建一个 Notebook 类实例
>   - 通过工厂方法新建一个 Hibernate Session 实例
>   - 将 Notebook 赋值给 Session，由 Session 代为完成 Notebook 的数据的持久化和反持久化。


持久化对象无非是简单的POJO对象，一个与数据库表对应的包含若干属性及setter/getter方法的类的实例。这个对象由 Hibernate 管理，存在不同的状态，并可互相转换。
(瞬间状态是指对象已经创建，但未与 Hibernate Session关联，无法持久化。)
- 瞬间状态-->持久状态：save(), saveOrUpdate()
- 持久状态-->瞬间状态：delete()
- 持久状态-->脱管状态：close(), evict(), clear()
- 脱管状态-->持久状态：update(), saveOfUpdate()
- 脱管状态-->瞬间状态：delete()

Session接口的常用方法：beginTransaction(), clear(), close(), createQuery(), createSQLQuert(), delete(), evict(), flush(), get(), load(), persist(), save(), update(), saveOrUpdate() 。

在采用 Hibernate 开发的应用系统中一个数据库的表就对应着一个映射配置文件和一个 JavaBean类（实际上是POJO类）。JavaBean与数据库表的对应关系通过映射配置文件来定义，即 .hbm.xml 文件。
- `<hibernate-mapping schema="数据库Schema名称" catelog="数据库catalog名称" default-cascade="级联风格" default-access="属性访问策略" default-lazy="true|false" auto-import="true|false" package="持久化类的包路径"> ... </hibernate-mapping>`
- `<class name="持久化类名" table="数据库表名" descriminator-value="用于区分不同子类的标志值" mutable="是否可被更新或删除选项：true|false" schema="数据库模式名称" catalog="数据库catalog名称" proxy="延迟装载的代理类" dynamic-update="动态更新选项：true|false" dynamic-insert="动态插入选项：true|false" select-before-update="true|false" polymorphism="多态选项：implicit|explicit" where="附加的where子句" persister="对象持久化的实现类的类名" batch-size="抓取数量" optimistic-lock="乐观锁定选项：none|version|dirty|all" lazy="延迟抓取选项：true|false" / >`
- `<id name="属性名" type="类型名" column="字段名" unsaved-value="any|none|null|id_value" access="属性访问策略：field|property|ClassName"> <generator class="持久化标识符生成策略" /> </id>`
- `<property name="属性名称" column="数据库表字段的名称" type="类型" update="是否可更新：true|false" insert="是否可插入：true|false" formula="属性值计算表达式" access="属性值访问策略：field|property|ClassName" lazy="是否使用延迟加载：true|false" unique="属性值是否唯一：true|false" not-null="是否非空：true|false" optimistic-lock="是否采用乐观锁：true|false" generated="是否由数据库生成：never|insert|always" />`
- 其他还可以包含的元素：discriminator, many-to-one, many-to-many, component, subclass, join, version 等，用于实现关联关系映射、继承关系映射、组件映射、事务控制等。


Java的数据类型与数据库的数据类型是不一致的，为了解决这个问题，Hibernate对数据类型做了定义：
| 序号 | Hibernate数据类型 | 数据库数据类型 | Java数据类型 |
|------|-----------------|--------------|------------|
| 1 | integer | int | int, java.lang.Integer |
| 2 | yes_no | char | boolean, java.lang.Boolean |
| 3 | big_decimal | numeric | java.math.BigDecimal |
| 4 | big_integer | numeric | java.math.BigInteger |
| 5 | string | varchar | java.lang.String |
| 6 | date | datetime, timestamp | java.util.Date |
| 7 | time | datetime, timestamp | java.util.Date |
| 8 | timestamp | datetime, timestamp | java.util.Date |
| 9 | calendar | datetime, timestamp | java.util.Calendar |
| 10 | calendar_date | datetime, timestamp | java.util.Calendar |
| 11 | class | varchar(类全路径)) | java.lang.Class |
| 12 | locale | varchar(ISO代码) | java.util.Locale |
| 13 | timezone | varchar(ID) | java.util.TimeZone |
| 14 | currency | varchar(ISO代码) | java.util.Currency |
| 15 | binary | binary | byte[] |
| 16 | text | text | java.lang.String |
| 17 | clob | clob | java.sql.Clob |
| 18 | blob | blob | java.sql.Blob |

关联关系指的是多对一、一对多、多对多关系；关联还分为单向关联、双向。
- 单向多对一：但在关系型数据库中，只有一对一、一对多，而且都是单向的。而在 Hibernate 中，为了保证双方的映射可以通过多种方式进行，“单向一对多”关联与“单向多对一”关联被认为是不同的关联，区别在于哪个表的映射文件进行 `<many-to-one>`配置，进行配置的一方就是“多”。
- 双向多对一：双向多对一经常需要用到集合类。many和one双方必须声明能够导航到对象的属性，一般在 many 方声明以 one 方持久化类为类型的树形，而在 one 方则声明集合属性，并应用集合类的实现类将该属性初始化。
- 一对一：包括两种形式：基于外键（一个表的外键是另一个表的主键），基于主键（一个表的主键也是另一个表的主键）。基于外键的一对一关系也要用到`<many-to-one>`，只是unique设置为true。基于主键的通过 `<one-to-one>` 进行。
- 多对多。双方都需要进行 `<many-to-many>` 定义，关联方之一的 `<set>` 的 inverse 属性需要设为 true，这样就将主控权交给对象持久化类。


HQL（Hibernate Query Language）是 Hibernate 专用的面向对象的查询语言，类似SQL。`[select | update | delete] [from 类名列表] [where 子句] [group by 子句] [order by 子句]`。



Struts长处在于MVC模式，在用户交互式系统中，可以对数据检验、处理流程进行很好的控制，但是数据操作仍然要依靠JDBC；Hibrenate长处在于O-R映射，改操作数据库为操作Java对象，更符合Java编程习惯，更易于实现业务逻辑与数据操作逻辑。  
Struts和 Hibernate的整合策略有两种。  
一种是直接将 Hibernate 的POJO对象作为Struts的Action，在POJO对象中在加入 execute() 之类的具有 Struts 特性的方法。这种方法很简单，但不提倡。因为POJO承载了过多的任务，既要作为Action类来执行业务逻辑，也要作为POJO来持久化对象。  
另一种策略是可以将POJO类和Action类分开，POJO只需要考虑对数据库表的封装，如何操作则在Action类内部定义，并且Action还封装JSP页面的表单输入数据。



Sprint框架，要掌握其 IoC 核心技术。

Spring是一个开源框架，从实现了MVC的 Spring Web MVC、表现层的 Spring 标签，到 Spring AOP、核心的 IoC 等。可以把 Spring 看成是各种开源技术的粘合剂，用它把各种组件组合在一起，又不互相影响。

![Spring体系结构](https://atts.w3cschool.cn/attachments/image/wk/wkspring/arch1.png)

Spring的主要模块：
- 核心容器。核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 BeanFactory，它是工厂模式的实现。BeanFactory 使用控制反转 (IoC) 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
- Context。即Spring上下文，它实际上是一个配置文件，向Spring框架提供上下文信息。Spring上下文包括企业服务，例如JNDI、EJB、电子邮件、国际化、校验和调度功能。SPring还提供了新的表达式语言。
- AOP。AOP模块直接将面向方面的编程功能集成到了 Spring 框架中，可以使用 Spring 框架管理的任何对象支持AOP。 Spring AOP模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖于EJB组建，就可以将声明性事务管理集成到应用程序中。
- Aspects。Spring提供对 AspectJ框架的整合和应用。
- Instrumentation。Instrumentation提供了对类装载的支持，以及用于实现到某些应用的服务器中的类装载器。
- Test。该模块提供与测试有关的组件集成，如Junit，TestNG。
- 数据输入输出与集成。Spring DAO提供了JDBC的抽象层，它可消除冗长的JDBC编码和解析数据库厂商所特有的错误代码。并且JDBC封装包还提供了一种比编程性更好的声明性事务管理方法，不仅仅是实现了特定接口，而且对所有的POJOs都适用。Spring框架插入了若干个ORM框架，包括JDO、Hibernate、iBatis SQL Map，Spring和Hibernate的结合相当良好。OXM提供了对象与XML映射的抽象，比如 JaxB、Castor等。
- Web。Web上下文模块建立在应用程序上下文模块之上，为基于Web的应用程序提供了上下文，Spring框架支持与Struts的集成。Spring Web MVC提供了Web应用的MVC实现，其中容纳了大量的视图技术，其中包括JSP、Velocity、Tiles、iText和POI等。

IoC(Inversion of Control，控制反转)，又称DI（Dependency Injection，依赖注入）。IoC是 Spring框架的核心技术。
> 注：参见某书的理念：所谓反转，是指代码的依赖关系，与系统的控制流方向相反。需要注意的是，并不是讨论类的继承依赖，而是对象的实现机制依赖，与工厂方法有关。


org.springframework.beans 及 org.springframework.context 包是 Spring IoC 容器的基础。BeanFactory 提供的高级配置机制，使得管理任何性质的对象成为可能。ApplicationContext是BeanFactory的扩展，功能进一步增强。

org.springframework.beans.factory.BeanFactory 是 Spring IoC 容器的核心接口，它的职责包括：实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。IoC容器负责容纳 bean，并对 bean 进行管理。
Spring提供了许多易用的 BeanFactory 实现，XmlBeanFactory就是最常用的一个。该实现以XML方式描述组成应用的对象以及对象间的依赖关系。
开发人员用配置文件给出 Spring IoC容器中对象的属性值，以及对象之间的依赖关系；Spring IoC容器负责管理这些对象和对象之间的依赖关系；当应用程序需要使用对象时，就通过Spring Ioc容器来得到对象。

Spring主要支持三种配置格式：XML格式、Java属性文件格式、Spring公共API编程实现。其中，XML是主要形式。
```xml
<!-- 一个配置的例子，一个bean表示一个Bean对象的配置 -->
<!-- bean的定义包括：服务层对象、数据访问层对象（DAO）、类似Struts Action的表示层对象，Hibernate SessionFactory对象、JMS Queue对象等 -->
<?xml version="1.0" encoding="UTF-8">
<beans xmlns="" xmlns:xsi="" xsi:schemaLocation="">
    <bean id="" class="">
    </bean>
</beans>
```

实例化容器有多种方法
```java
Resource resource = new FileSystemResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);
```

XML文件需要放置在 CLASSPATH 目录中，也就是Java程序能找到的类路径，再以此来配置路径。在Web应用中，一般 CLASSPATH 会指向 WEB-INF/classes 目录。

Spring的安装也是下载压缩包，在压缩包的 dist 目录下的全部 jar 包。


实例化bean的方法有三种：用构造器来实例化，使用静态工厂方法实例化和使用实例工厂方法实例化。
- 使用构造器来创建bean实例时，Spring对class没有任何特殊要求。不过根据所采用的IoC类型，class可能需要一个默认的空构造器。
- 当采用静态工厂方法创建bean时，除了需要指定class属性外，还需要通过 factory-method属性来指定创建bean实例的工厂方法。Spring将调用此方法返回实例对象。`<bean id="exampleBean" class="examples.ExampleBean2" factory-method="createInstance" />`
- 用来进行实例化的实例工厂方法位于另外一个已有的bean中，容器将调用该bean的工厂方法来创建一个新的bean实例。为使用此机制，class属性必须为空，而factory-bean属性必须指定为当前（或其祖先）容器中包含工厂方法的 bean 的名称，而该工厂 bean 的工厂方法本身必须通过 factory-method 属性来设定。 `<bean id="exampleBean" factory-bean="myFactoryBean" factory-method="createInstance" />`


BeanFactory提供的方法比较简单：
- `boolean containsBean(String)`
- `Object getBean(String)` 返回给定名字注册的Bean实例。根据bean的配置情况，可能返回一个singleton或一个新实例。
- `Object getBean(String, Class)` 返回以给定名称注册的 bean 实例，并转换为给定 class 类型的实例。
- `Class getType(String name)` 返回给定名称的bean的class。
- `boolean isSingleton(String)` 判断给定名称的 bean 定义（或bean实例）是否为 singleton 模式。
- `String[] getAlias(String)` 返回给定bean名称的所有别名。



集成Struts、Hibernate和Spring是相当简单的。
Spring核心技术是 Ioc，整合时需要充分利用。整合 Struts和Spring有两种策略（也可以结合）。
Spring就像一个生产组件对象的工厂，可以源源不断地输出组件对象，注入到需要之处，而开发人员需要做的就是编写组件类的代码和编写配置文件。
- Spring管理Struts的控制器，也就是说，Action交由Sring来管理，利用Ioc的特性为Action注入业务逻辑组件
- Spring管理业务逻辑组件，需要时即注入。

