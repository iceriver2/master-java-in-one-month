> 2019-10-06 关于JSP的单独整理，暂时只打算涉及 JEE 关于jsp的部分，不涉及SSH/SSM等。那些需要专门的文档来讲解。不过，需要声明的是：JSP的天下已经没有了，一如PHP。现在是前后端分离的天下。

- [JSP与Servlet](#jsp%e4%b8%8eservlet)
  - [JSP页面](#jsp%e9%a1%b5%e9%9d%a2)
  - [指令](#%e6%8c%87%e4%bb%a4)
- [内置对象](#%e5%86%85%e7%bd%ae%e5%af%b9%e8%b1%a1)
  - [Request](#request)
  - [Response](#response)
  - [Cookie](#cookie)
  - [Session](#session)
  - [application](#application)
  - [out](#out)
  - [config](#config)
- [JDBC](#jdbc)
  - [Connection](#connection)
  - [Statement](#statement)
  - [ResultSet](#resultset)
  - [DatabaseMetaData](#databasemetadata)
  - [ResultSetMetaData](#resultsetmetadata)
- [框架](#%e6%a1%86%e6%9e%b6)
- [服务器](#%e6%9c%8d%e5%8a%a1%e5%99%a8)

- JavaWeb编程
  - Web编程基础：Tomcat服务器、EL、Servlet API、Listener和Filter
  - Web编程进阶：自定义标签库、MVC和DAO、JSTL／DisplayTag等常见标签库的用法
  - Web编程原理：深刻掌握JSP运行原理、掌握Web容器底层的线程池、Socket通信、调用Servlet的命令模式
- JDBC编程
  - JDBC基础：常见数据库操作、JDBC操作常见数据库、RowSet与离线结果集、数据库连接池、事务管理、批处理
  - JDBC进阶：存储过程、函数、触发器、理解JDBC的不足、掌握ORM工具优势和设计
  - DHTML编程：HTML、Javascript、DOM和事件机制
- XML编程
  - XML基础：XML基本规则、DTD和Schema、XML和样式单
  - XML进阶：DOM和SAX、dom4j、JDOM等工具、XQuery和XQJ、基于XML的数据交换
  - WebService：掌握JAX-WS2、SAAJ规范、WSDL和SOAP协议、CXF框架／拦截器、CXF整合Spring
- 轻量级JavaEE
  - Struts2：MVC模式与Struts2体系、开发／管理Action、国际化和标签库、文件上传下载、类型转换和输入校验、Sttucts拦截器
  - Hibernate：ORM与持久化映射、关系映射、继承映射、延迟加载、性能优化、HQL查询、条件查询、SQL查询、二级缓存与查询缓存
  - Spring：IoC与Bean配置／管理、SpEL、AOP与事务、权限控制、S2SH整合开发、Spring／JPS整合
- 经典JavaEE
  - JSF：MVC与JSF设计架构、脱光Bean与导航模型、JSF流程与事件机制、JSF标签库、类型转换与输入校验
  - EJB及相关技术：JNDI与RMI、IoC与EJB拦截器、会话Bean及其生命周期、JMS与MDB、会话Bean和WebService
  - JPA：ORM框架与JPA规范、JPA注解与常用API、JTA事务与事务管理、JPQL查询、EJB／JPA整合
- 企业相关
  - JavaEE实战与架构：Ant+Ivy或Maven、SVN／CVS、10种以上设计模式、各种Java应用架构及其优劣
  - 企业应用：Workflow规范和功能、jBPM等Workflow框架、重构与思考、大型项目经验
  - JavaEE进阶：MVC原理、Spring／HiveMind／AspectJ等框架原理、Hibernate／iBATIS等框架、EJS机制及服务器实现

----------------------------------------------

# JSP与Servlet

JSP本质上是一个Servlet，编译后会变成一个HttpJspPage类（HttpServlet的一个子类）。

## JSP页面

JSP页面(`.jsp`)支持混写。

指令的几种写法
```jsp
<%@ page contentType="text/html;charset=utf8" %> <!--JSP指令-->
<%! String s = null; %> <!-- 变量声明或方法定义，不能定义类 -->
<% out.print("yes") %> <!-- 语句 -->
<jsp:include page="path/to/something"> <!-- 标签 -->
<%="23"%> <!-- 表达式 -->
```

**jsp默认导入的类： java.lang, javax.servlet, javax.servlet.jsp, javax.servlet.http 。**

一个相对较全的例子，包括2个页面：`/a/a.jsp`, `/b/b.jsp`，其中 `/a/a.jsp` 向 `/b/b.jsp?id=a&date=xxx` 提交表单，`b.jsp` 将结果打印到页面。
```jsp
<!-- /a/a.jsp -->
<%@ page contentType="text/html;charset=UTF-8" session="true" %>
<%
    session.setAttribute("useMaven", true);
%>
<form method="post" action="../b/b.jsp?id=a&date=<%=LocalDate.now().format(DateTimeFormatter.ISO_DATE)%>">
    参数1: <input type="text" name="arg1" value="25"> <br>
    参数2: <input type="text" name="arg2" value="20"> <br>
    运算符: <input type="text" name="operator" value="+"> <br>
    <input type="submit">
</form>

<!-- /b/b.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="java.net.HttpCookie" %>
<%@ page import="java.util.*" %>

<%
    // getCharacterEncoding(): null
    // getContentType(): application/x-www-form-urlencoded
    // getProtocol(): HTTP/1.1
    // getLocalAddr(): 0:0:0:0:0:0:0:1
    // getLocalName(): ip6-localhost
    // getLocalPort(): 8080
    // getRemoteAddr(): 0:0:0:0:0:0:0:1
    // getRemoteHost(): 0:0:0:0:0:0:0:1
    // getRemotePort(): 53638
    // getScheme(): http
    // getServerName(): localhost
    // getServerPort(): 8080
    // getLocale(): English (United States)
    // getParameterMap(): arg1:[iceman, 25];id:[a];date:[2019-10-07];arg2:[20];operator:[+];
    // getMethod(): POST
    // getContextPath(): /testWebApp_war
    // getServletPath(): /b/b.jsp
    // getRequestURI(): /testWebApp_war/b/b.jsp
    // getRequestURL(): http://localhost:8080/testWebApp_war/b/b.jsp
    // getQueryString(): arg1=iceman&id=a&date=2019-10-07
    // getPathInfo(): null
    // getPathTranslated(): null
    // getCookies(): JSESSIONID:487B310D186615084B9E017C326AA7D8; _ga:GA1.1.1821587037.1569812744; _gid:GA1.1.430560287.1570139104;
    // getSession(): useMaven:true;

    out.println("<h2>Request</h2>");
    out.print("getCharacterEncoding(): ");
    out.println(request.getCharacterEncoding() + "<br>");
    out.print("getContentType(): ");
    out.println(request.getContentType() + "<br>");
    out.print("getProtocol(): ");
    out.println(request.getProtocol() + "<br>");
    out.print("getLocalAddr(): ");
    out.println(request.getLocalAddr() + "<br>");
    out.print("getLocalName(): ");
    out.println(request.getLocalName() + "<br>");
    out.print("getLocalPort(): ");
    out.println(request.getLocalPort() + "<br>");
    out.print("getRemoteAddr(): ");
    out.println(request.getRemoteAddr() + "<br>");
    out.print("getRemoteHost(): ");
    out.println(request.getRemoteHost() + "<br>");
    out.print("getRemotePort(): ");
    out.println(request.getRemotePort() + "<br>");
    out.print("getScheme(): ");
    out.println(request.getScheme() + "<br>");
    out.print("getServerName(): ");
    out.println(request.getServerName() + "<br>");
    out.print("getServerPort(): ");
    out.println(request.getServerPort() + "<br>");
    out.print("getLocale(): ");
    out.println(request.getLocale().getDisplayName() + "<br>");
    out.print("getParameterMap(): ");
    Map<String, String[]> kvs = request.getParameterMap();
    for(String k: kvs.keySet()) { // 有一个参数同时出现在 get 和 post，会以数组形式存在
        out.print(k + ":" + Arrays.toString(kvs.get(k)) + ";");
    }
    out.println("<br>");
    out.print("getMethod(): ");
    out.println(request.getMethod() + "<br>");
    out.print("getContextPath(): ");
    out.println(request.getContextPath() + "<br>");
    out.print("getServletPath(): ");
    out.println(request.getServletPath() + "<br>");
    out.print("getRequestURI(): ");
    out.println(request.getRequestURI() + "<br>");
    out.print("getRequestURL(): ");
    out.println(request.getRequestURL() + "<br>");
    out.print("getQueryString(): ");
    out.println(request.getQueryString() + "<br>");
    out.print("getPathInfo(): ");
    out.println(request.getPathInfo() + "<br>");
    out.print("getPathTranslated(): ");
    out.println(request.getPathTranslated() + "<br>");

    out.print("getCookies(): ");
    Cookie[] cks = request.getCookies();
    for(Cookie c: cks) {
        out.println(c.getName() + ":" + c.getValue() + ";");
    }
    out.println("<br>");
    out.print("getSession(): ");
    HttpSession ses = request.getSession();
    Enumeration<String> sesk = ses.getAttributeNames();
    while(sesk.hasMoreElements()) {
        String k = sesk.nextElement();
        out.println(k + ":" + ses.getAttribute(k) + ";");
    }
    out.println("<br>");
%>
```

## 指令

> 注：目前尚不可知， `<jsp:xxx>`的语法是从哪里来的，不确定是不是某个类包。而`<% %>`应该是系统定义。

JSP指令
```jsp
<!-- page指令，针对本页的设置。包括 language（默认java）, import, contentType, session（是否使用session，默认true）, buffer（是否使用缓冲，none或kb大小）, autoFlash（是否自动刷新缓冲区）, isThreadSafe（是否只同时响应一个客户）, info（页面的信息，可以通过getServerInfo()获取）, errorPage（出现错误时的转向页面）, isErrorPage（是否为错误页面，true时可以使用Exception） -->
<%@ page contentType="text/html;charset=utf8" lang="java" %>
<%@ page import="java.util.*", "java.awt.*" %>

<!-- include指令，静态包含文件 -->
<%@ include file="path/to/something"  %>

<!-- taglib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- include动作指令，动态包含文件 -->
<jsp:include page="path/to/something" />
<jsp:include page="path/to/other">
    <jsp:param name="key" value="value">
</jsp:include>

<!-- forward动作指令，重定向(也可以使用 response.sendRedirect -->
<jsp:forward page="somepage">
<jsp:forward page="somepage">
    <jsp:param name="key" value="value">
</jsp:forward>

<!-- useBean动作指令，创建并使用一个JavaBean组件 -->
<jsp:useBean id="beanId" scope="page（默认）/request/session/application" class="package.classname">
    <jsp:setProperty name="beanName" property="*" />
</jsp:useBean>

<!-- setProperty动作指令，设定Bean中属性的值。可以在 useBean 中使用或之后使用 -->
<jsp:setProperty name="beanName" property="*" /><!--bean与request匹配 -->
<jsp:setProperty name="beanName" property="property" /><!--只设置一个bean属性-->
<jsp:setProperty name="beanName" property="property" param="" /><!--request参数与bean匹配-->
<jsp:setProperty name="beanName" property="property" value="" /> <!--bean属性赋值，需要转换值-->

<!-- getProperty动作指令，获得Bean中属性的值，再输出 -->
<jsp:getProperty name="beanName" property="property">
```

使用 `<jsp:include>` 或 `<jsp:forward>`时，如果 param 中出现中文值，被包含或转向的页面会出现乱码。  
解决的方法是：在当前页面，设置 `request.setCharacterEncoding("UTF-8")` 。
> 猜测：虽然jsp页面本身的编码是utf8，但jsp指令的内部实现可能不是，因此，设置 request 的字符集，可能影响了jsp内部。暂时没时间去深究了。
> 但正常的 form 提交，不会有乱码问题。

# 内置对象

JSP的内置对象：**request**、**response**、**session**、**application**、**out**、config、pageContext 。
- request对象与 HttpServletRequest 类关联，用于处理请求数据。
- response对象与 HttpServletResponse 类关联，用于处理响应数据。
- session对象与 HttpSession 类关联，用于处理会话数据。
- application对象与 ServletContext 类关联，一旦创建就一直存在，用于处理全局的数据和对象。
- out对象使用 PrintWriter 类向客户端输出数据。
- config对象是 ServletConfig 类的一个对象，是JSP配置处理程序的句柄，在JSP页面范围内有效。
- pageContext 用来管理属于 JSP 中特殊可见部分中已命名对象的访问。

## Request

> 注：当一个变量同时出现在get/post方式中时，会以数组形式保存。

request是 HttpServletRequest/ServletRequest 的一个实例。

request对象的常用方法：
- `getAttrbute()`获取属性 / `removeAttribute()`删除属性 / `setAttribute()`设置属性 / `getAttributeNames()`获取属性枚举
- `getCharacterEncoding()`获取请求的字符编码 / `setCharacterEncoding()`设置请求的字符编码
- `getContentType()`获取内容的MIME
- `getContextPath()`获取相对ROOT的应用的位置
- `getLocalAddr()`获取服务器地址 / `getLocalName()`获取服务器名称
- `getMethod()`获取请求类型
- `getParameter()`获取参数值 / `getParameterMap()`获取参数值的映射 / `getParameterNames()`获取参数名称的枚举 / `getParameterValues()`获取参数值的数组
- `getProtocol()`获取协议及版本号
- `getQueryString()`获取查询字符串
- `getRemoteAddr()`获取客户地址 / `getRemoteHost()`获取客户名称 / `getRemotePort()`获取客户端口
- `getRequestURI()`获取请求路径 / `getRequestURL()`获取标准全路径
- `getServerPort()`获取服务端端口
- `getServletPath()`获取servlet路径（JSP路径）
- `getSession()`获得关联的会话

## Response

response 是 HttpServletResponse/ServletResponse 的一个实例。

response 对象的常用方法：
- `flushBuffer()`强制输出缓冲
- `getBufferSize()`获得缓冲区大小 / `setBufferSize()`设置缓冲区大小
- `getCharacterEncoding()`获得响应的字符编码 / `setCharacterEncoding()`设置响应的字符编码
- `getContentType()`获得响应的MIME / `setContentType()`设置响应的MIME
- `getOutputStream()`获得输出流
- `getWriter()`获得PrintWriter对象
- `sendRedirect()`重定向
- `setHeader()`设置头部

## Cookie

Cookie在安全性较高的场合不要使用。浏览器只允许存放300个Cookie，每个站点最多20个，每个Cookie最大4KB。

Cookie的使用，先创建一个 Cookie 对象，然后通过 response.addCookie() 将其发送到客户端，通过 request.getCookies() 方法查找获取。

Cookie对象自身也有一些方法可以设置属性，如:
- `getComment()`/`setComment()`获取/设置注释
- `getDomain()`/`setDomian()`获取/设置域
- `getMaxAge()`/`setMaxAge()`获取/设置有效时间
- `getName()`/`setName()`获取设置名称
- `getPath()`/`getPath()`获取/设置路径
- `getSecure()`/`setSecure()`获取设置SSL
- `getValue()`/`setValue()`获取/设置值

## Session

session对象的常用方法有：
- `getAttribute()`获取属性 / `removeAttribute()`删除属性 / `getAttributeNames()`获取属性的枚举
- `getMaxInactiveInterval()`获取有效时间 / `setMaxInactiveInterval()`设置有效时间
- `getServletContext()`获得会话所属上下文
- `invalidate()`使会话失效
- `isNew()`是否新会话。

## application

application对象在多个程序或多个用户之间共享数据。服务器一启动，就会自动创建 application 对象，会一直持续到服务器关闭。

application对象的常用方法：
- `getAttribute()`获得属性 / `removeAttribute()`删除属性 / `setAttribute()`设置属性 / `getAttributeNames()`获得属性的枚举
- `getContext()`获得指定URI的上下文
- `getInitParameter()`获得默认参数值 /  `getInitParameterName()`获得默认参数名称
- `getRealPath()`返回path的物理路径
- `getServletContextName()`获得上下文的显示名称。 


## out

> 注：发现 out 在页面可以直接使用，但是，一旦放入自定义方法中即会找不到变量。

out对象向客户端输出内容，由 Web容器指定为 javax.servlet.jsp.JspWriter 类的一个子类。

out对象的常用方法包括：
- `clear()`清空缓冲区, `clearBuffer()`清空缓冲区, `close()`刷新缓冲区后关闭, `flush()`刷新缓冲区, `getBufferSize()`获得缓冲区大小
- `isAutoFlush()`是否自动刷新
- `print()`打印

## config

config对象是 servletConfig 类的一个对象，用于处理当前页面的句柄（？），仅在页面范围内有效，使用的较少。

# JDBC

使用方法：
- 下载JDBC驱动文件，将jar文件放入应用的 `WEB-INF/lib` 目录（JEE应用中的标准三方包路径）。
- 在使用页面，引入SQL包： `import java.sql.*`
- 在使用页面，注册驱动 `Class.forName("com.mysql.jdbc.Driver");`
- 获取数据库连接 `Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root")`。连接数据库时需要捕获异常。

> 注：书上说，放入 `WEB-INF/lib` 的驱动会被DriverManager自动搜索，但并没有。Stackoverflow说，应该放入 `$CATALINA_HOME/lib` ，但也有说没用的。只有 `Class.forName()` 最保险。

如果在 Maven 中使用，应该在 pom.xml 的 `<dependencies>` 中加入依赖关系。


数据库连接池负责分配、管理和释放数据库连接，允许重复使用一个现有的数据库连接。

Tomcat内置的数据库连接池是 DBCP（Database Connection Pool），DBCP 是 jakarata Commons 的一个子项目。DBCP的组件包是 tomcat-dbcp.jar，位于 Tomcat/lib 目录。


一个完整的例子
```java
try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dwing", "root", "root")) {
    try {
        String sql = "select * from d_city where code = ? limit 1";
        PreparedStatement s = conn.prepareStatement(sql); // 预编译语句
        s.setString(1, "XXX"); // 设置参数
        ResultSet rs = s.executeQuery(); // 查询得到ResultSet，指针 beforeFirst
        rs.next(); // 移动指针到first
        return rs.getString(1); // 通过 getXXX(index) 获得字段值
    } catch (SQLException e) {
        return null;
    }
    out.print(a.toString());
} catch (Exception e) {
    out.print(e.getMessage());
}
```


一些注意点：
- JDBC使用`getConnection()`连接数据库，使用`close()`释放连接。操作期间会保持长连接。
- 基本步骤是：`getConnection()`, `createStatement()`, `executeQuery()`, `close()`。
- ResultSet指针默认beforeFirst，而不是first。需要通过`next()`获得数据。
- ResultSet的行数，通过 `rs.last()` 移动到最后一行后，使用 `rs.getRow()` 读取行号。
- 插入/更新/删除语句，Statement的`executeQuery()`会返回受到影响的行数。
- 在`createStatement`时，可以指定标志位`ResultSet.CONCUR_UPDATABLE`，允许对ResultSet只读或读写。使用ResultSet的`moveToInsertRow()`后，对内存中的数据进行更新，连接关闭时，会自动写入数据库。


PHP和JS中使用数据库短连接，查询结果立即断开连接。因此，总是把查询结果作为数组（对象数组或二维数组）一次性返回。分页需要两步查询：查询全部数量，查询本页数据。  
JDBC使用数据库长连接，只在需要时读取相应数据。因此，分页可以只需一步查询，通过`last()`获得总数，通过`absolute()`控制指针移动到需要读取的本页数据。当然，更好的方法，仍然是两步查询：查询全部数量，查询本页数据。

事务的处理也比较简单，先设置不自动提交 `setAutoCommit(false)`，然后，正常执行各种`executeQuery()`，最后再`commit()`或`rollback()`。

## Connection

一个Connection对象表示一个连接。Connection默认自动提交。  
Connection的常用方法：
- `close()` / `isClosed()` 释放连接
- `commit()`提交, `rollback()`回滚
- `createStatement()`创建Statement对象, `prepareStatement()`创建PreparedStatement对象, `prepareCall()`创建CallableStatement对象
- `getAutoCommit()` / `setAutoCommit()` 自动提交
- `getMetaData()` 获取DatabaseMetaData对象，关于数据库的信息
- `getTransationIsolation()` / `setTransactionIsolation()` 事务隔离级别
- `isReadOnly()` / `setReadonly()` 只读
- `setHoldability()` 可保存性

## Statement

Statement对象表示SQL语句。存在三种Statement对象
- Statement，用于执行基本的SQL语句；
- PreparedStatement，继承Statement，用于提供可以与查询信息一起预编译的语句
- CallableStatement，继承PreparedStatement，用于执行数据库中的存储过程。

Statement用于执行静态SQL语句并返回它所生成的对象。默认情况下，同一时刻每个Statement对象只能打开一个 Result 对象。  
Statement接口的常用方法：
- `addBatch()`添加批处理 / `executeBatch()`执行批处理 / `clearBatch()`清除批处理
- `cancel()`取消
- `close()` / `isClosed()` 释放连接
- `execute()`执行（返回boolean）, `executeQuery()`执行（有返回ResultSet）, `executeUpdate()`执行（返回int）
- `getConnection()`
- `getMaxFieldSize()` / `setMaxFieldSize()` 最大字节数
- `getMaxRows()` / `setMaxRows()` 最大行数
- `getMoreResults()` 是否还有下一个
- `getResultSet()` 获得当前ResultSet
- `getUpdateCount()` 更新结果计数器

PreparedStatement 用于表示预编译SQL语句的对象。SQL语句被预编译并且存储在 PreparedStatement 对象中，然后多次被执行。  
PreparedStatement 扩展了 Statement 接口，PreparedStatement 增加了一系列 setXXX 方法。（注意：setXXX的索引从1开始。）

CallableStatement 是用于执行SQL存储过程的接口。存储过程的输入参数值，必须在执行前注册类型，其值是通过本类的 get 方法获取的。注册输出参数用 registerParameter() 方法。  
CallableStatement 返回一个 ResultSet 对象或多个 ResultSet 对象。多个 Result 对象是使用从 Statement 中继承的操作处理的。

## ResultSet

ResultSet 表示数据库结果记录集。ResultSet对象具有指向其当前数据行的指针，通过 next() 返回下一个结果。

ResultSet 接口提供从当前行返回值的方法（如 getBoolean(), getLong() ）。  
ResultSet的常用方法：
- `absolute()`移动到某行, `relative()`移动多少行
- `afterLast()`移动到最后 / `isAfterLast()`, `beforeFirst()`移动到最前 / `isBeforeFirst()`
- `first()`移动到第一行 / `isFirst()`, `last()`移动到最后一行 / `isLast()`
- `next()`下一行, `previous()`上一行
- `moveToInsertRow()`移动到插入行
- `cancelRowUpdates()`取消更新
- `close()`释放连接
- `deleteRow()`删除行, `insertRow()`插入行, `updateRow()`更新行
- getXxxx() 获得某个列的值，按序号或名称
- `getRow()`获得行号
- `getType()`获得ResultSet类型
- updateXxx() 更新某个列的值，按序号或名称

getXXX 和 updateXXX，需要注意数据库字段类型与Java数据类型的对应与转换。  
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


## DatabaseMetaData

DatabaseMetaData用于得到有关数据库的信息。通过 Connection 的 `getMetaData()` 获得一个实例。

不同的数据库实现的方式不同。有些数据库以 ResultSet 对象的形式返回信息列表，有些使用 String 。  

DatabaseMetaData 的常用方法： `getURL()`, `getUserName()`, `getTables()` 。

## ResultSetMetaData

ResultSetMetaData 用于获得 ResultSet 的类型和属性信息。通过 ResultSet 的 `getMetaData()` 获得一个实例。

常用方法有： `getColumnCount()`, `getColumnName()`, `getColumnTypeName()` 。

# 框架

SSH 通常指的是 Struts2 做控制器(controller)，spring 管理各层的组件，hibernate 负责持久化层。  
SSM 则指的是 SpringMVC 做控制器(controller)，Spring 管理各层的组件，MyBatis 负责持久化层。  
共同点：Spring依赖注入DI来管理各层的组件；用面向切面编程AOP来管理事物、日志、权限等。

# 服务器

支持JSP的服务器 Tomcat,WebLogic,WebSphere, JBoss 。
- Tomcat，是支持JSP和Servlet的容器，也是Web服务器。占用资源小，扩展性好。Tomcat小型轻量，在中小型系统和并发量不大时实用。（为什么跟Apache Http一个尿性？） Tomcat 需要JDK支持。最新版本 9.0 。
- WebLogic原是BEA公司产品，被Oracle买了。WebLogic Server支持企业级、分布式的Web应用，支持包括JSP、Servlet、EJB在内的JEE体系。WebLogic功能强大，操作简单，大量用于电子商务。最新版本 12c 。
- IBM WebSphere是一系列产品，包括 WebSphere Performance Pack、Cache Manager、Studio、Web应用开发工具以及WebSphere Application Server。其中 Application Server基于Java的应用环境。WebSphere Application Server是行业领先的企业级Web应用服务器，支持行业内最广泛的平台，能够为不同类型的应用提供不同的解决方案。它使用基于开放标准的编程模型，包括 JavaEE, OGSi应用，Web2.0和Mobile、Java Batch、XML、Service Component Architecture(SCA)、Communications Enabled Applications(CEA)、Session Initiation Protocol(SIP)和动态脚本。

