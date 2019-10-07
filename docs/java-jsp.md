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
- [框架](#%e6%a1%86%e6%9e%b6)

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

支持JSP的服务器 Tomcat,WebLogic,WebSphere, JBoss 。

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

默认情况下，jsp会导入的类： java.lang, javax.servlet, javax.servlet.jsp, javax.servlet.http 。

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


request对象的常用方法：`getAttrbute()`获取属性 / `removeAttribute()`删除属性 / `setAttribute()`设置属性 / `getAttributeNames()`获取属性枚举, `getCharacterEncoding()`获取请求的字符编码 / `setCharacterEncoding()`设置请求的字符编码, `getContentType()`获取内容的MIME, `getContextPath()`获取相对ROOT的应用的位置, `getLocalAddr()`获取服务器地址, `getLocalName()`获取服务器名称, `getMethod()`获取请求类型, `getParameter()`获取参数值, `getParameterMap()`获取参数值的映射, `getParameterNames()`获取参数名称的枚举, `getParameterValues()`获取参数值的数组, `getProtocol()`获取协议及版本号, `getQueryString()`获取查询字符串, `getRemoteAddr()`获取客户地址, `getRemoteHost()`获取客户名称, `getRemotePort()`获取客户端口, `getRequestURI()`获取请求路径, `getRequestURL()`获取标准全路径, `getServerPort()`获取服务端端口, `getServletPath()`获取servlet路径（JSP路径）, `getSession()`获得关联的会话。


ServletRequest(接口)
- `AsyncContext getAsyncContext()` / `boolean isAsyncStarted()` / `boolean isAsyncSupported()` / `AsyncContext startAsync()` / `AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)`
- 属性：`Enumeration<String> getAttributeNames()` / `Object getAttribute(String name)` / `void setAttribute(String name, Object o)` / `void removeAttribute(String name)`
- 字符集：`void setCharacterEncoding(String env)` / `String getCharacterEncoding()`
- 内容：`String getContentType()` / `int getContentLength()` / `long getContentLengthLong()`
- `DispatcherType getDispatcherType()` / `RequestDispatcher getRequestDispatcher(String path)`
- 流：`ServletInputStream getInputStream()` / `BufferedReader getReader()`
- 请求信息：`String getProtocol()` / `String getLocalAddr()` / `String getLocalName()` / `int getLocalPort()` / `String getRealPath(String path)` / `String getRemoteAddr()` / `String getRemoteHost()` / `int getRemotePort()` / `String getScheme()` / `String getServerName()` / `int getServerPort()` / `ServletContext getServletContext()` / `boolean isSecure()`
- `Enumeration<Locale> getLocales()` / `Locale getLocale()`
- 参数：`Enumeration<String> getParameterNames()` / `String getParameter(String name)` / `Map<String,String[]> getParameterMap()` / `String[] getParameterValues(String name)`

HttpServletRequest（接口，继承 ServletRequest）
- 方法：`String getMethod()`
- Cookie：`Cookie[] getCookies()` 
- Session:`String changeSessionId()` / `String getRequestedSessionId()` / `HttpSession getSession()` / `HttpSession getSession(boolean create)` / `boolean isRequestedSessionIdFromCookie()` / `boolean isRequestedSessionIdFromURL()` / `boolean isRequestedSessionIdValid()`
- 路径：`String getContextPath()` / `String getServletPath()` / `String getRequestURI()` / `StringBuffer getRequestURL()` / `String getQueryString()` / `String getPathInfo()` / `String getPathTranslated()`
- 授权：`boolean authenticate(HttpServletResponse response)` / `String getAuthType()` / `String getRemoteUser()` / `Principal getUserPrincipal()` / `void login(String username, String password)` / `void logout()` / `boolean isUserInRole(String role)`
- 头部：`Enumeration<String> getHeaderNames()` / `Enumeration<String> getHeaders(String name)` / `String getHeader(String name)` / `long getDateHeader(String name)` / `int getIntHeader(String name)`
- `Collection<Part> getParts()` / `Part getPart(String name)`
- `<T extends HttpUpgradeHandler>T upgrade(Class<T> handlerClass)`

## Response

response 对象的常用方法：`flushBuffer()`强制输出缓冲, `getBufferSize()`获得缓冲区大小 / `setBufferSize()`设置缓冲区大小, `getCharacterEncoding()`获得响应的字符编码 / `setCharacterEncoding()`设置响应的字符编码, `getContentType()`获得响应的MIME / `setContentType()`设置响应的MIME, `getOutputStream()`获得输出流, `getWriter()`获得PrintWriter对象, `sendRedirect()`重定向, `setHeader()`设置头部。


ServletResponse（接口）
- `void flushBuffer()` / `void reset()` / `void resetBuffer()`
- `int getBufferSize()` / `void setBufferSize(int size)`
- `String getCharacterEncoding()` / `void setCharacterEncoding(String charset)`
- `String getContentType()` / `void setContentType(String type)` / `void setContentLength(int len)` / `void setContentLengthLong(long len)`
- `Locale getLocale()` / `void setLocale(Locale loc)`
- `ServletOutputStream getOutputStream()` / `PrintWriter getWriter()`
- `boolean isCommitted()`

HttpServletResponse（接口，继承ServletResponse）
- 若干 `SC_XX` 常量表示 status code
- `void addCookie(Cookie cookie)`
- `void addHeader(String name, String value)` / `void addDateHeader(String name, long date)` / `void addIntHeader(String name, int value)` / `void setHeader(String name, String value)` / `void setDateHeader(String name, long date)` / `void setIntHeader(String name, int value)` / `boolean containsHeader(String name)`
- `String encodeRedirectURL(String url)` / `String encodeURL(String url)`
- `Collection<String> getHeaders(String name)` / `Collection<String> getHeaderNames()` / `String getHeader(String name)`
- `int getStatus()` / `void setStatus(int sc)`
- `void sendError(int sc)` / `void sendError(int sc, String msg)`
- `void sendRedirect(String location)`

## Cookie

Cookie在安全性较高的场合不要使用。浏览器只允许存放300个Cookie，每个站点最多20个，每个Cookie最大4KB。

Cookie的使用，先创建一个 Cookie 对象，然后通过 response.addCookie() 将其发送到客户端，通过 request.getCookies() 方法查找获取。

Cookie对象自身也有一些方法可以设置属性，如 `getComment()`/`setComment()`获取/设置注释, `getDomain()`/`setDomian()`获取/设置域, `getMaxAge()`/`setMaxAge()`获取/设置有效时间, `getName()`/`setName()`获取设置名称, `getPath()`/`getPath()`获取/设置路径, `getSecure()`/`setSecure()`获取设置SSL, `getValue()`/`setValue()`获取/设置值。


Cookie(类)
- `Cookie(String name, String value)`
- `Object clone()`
- `String getComment()` / `void setComment(String purpose)`
- `String getDomain()` / `void setDomain(String domain)`
- `int getMaxAge()` / `void setMaxAge(int expiry)`
- `String getName()`
- `String getPath()` / `void setPath(String uri)`
- `boolean getSecure()` / `void setSecure(boolean flag)`
- `String getValue()` / `void setValue(String newValue)`
- `int getVersion()` / `void setVersion(int v)`
- `boolean isHttpOnly()` / `void setHttpOnly(boolean isHttpOnly)`

## Session

session对象的常用方法有：`getAttribute()`获取属性 / `removeAttribute()`删除属性 / `getAttributeNames()`获取属性的枚举, `getMaxInactiveInterval()`获取有效时间 / `setMaxInactiveInterval()`设置有效时间, `getServletContext()`获得会话所属上下文, `invalidate()`使会话失效, `isNew()`是否新会话。


HttpSession（接口）
- `void removeAttribute(String name)` / `Object getAttribute(String name)` / `void setAttribute(String name, Object value)` / `Enumeration<String> getAttributeNames()`
- `long getCreationTime()` / `long getLastAccessedTime()`
- `String getId()`
- `int getMaxInactiveInterval()` / `void setMaxInactiveInterval(int interval)`
- `ServletContext getServletContext()`
- `void invalidate()`
- `boolean isNew()`

## application

application对象在多个程序或多个用户之间共享数据。服务器一启动，就会自动创建 application 对象，会一直持续到服务器关闭。

application对象的常用方法：`getAttribute()`获得属性 / `removeAttribute()`删除属性 / `setAttribute()`设置属性 / `getAttributeNames()`获得属性的枚举, `getContext()`获得指定URI的上下文, `getInitParameter()`获得默认参数值 /  `getInitParameterName()`获得默认参数名称, `getRealPath()`返回path的物理路径, `getServletContextName()`获得上下文的显示名称。 


ServletContext(接口)
- `<T extends Filter>T createFilter(Class<T> clazz)` / `FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass)` / `FilterRegistration.Dynamic addFilter(String filterName, Filter filter)` / `FilterRegistration.Dynamic addFilter(String filterName, String className)`
- `<T extends EventListener>T createListener(Class<T> clazz)` / `void addListener(Class<? extends EventListener> listenerClass)` / `void addListener(String className)` / `<T extends EventListener>void addListener(T t)`
- `<T extends Servlet>T createServlet(Class<T> clazz)` / `ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass)` / `ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet)` / `ServletRegistration.Dynamic addServlet(String servletName, String className)`
- `void declareRoles(String... roleNames)`
- `Enumeration<String> getAttributeNames()` / `Object getAttribute(String name)` / `void removeAttribute(String name)` / `void setAttribute(String name, Object object)`
- `ClassLoader getClassLoader()`
- `ServletContext getContext(String uripath)` / `String getContextPath()`
- `int getEffectiveMajorVersion()` / `int getEffectiveMinorVersion()` / `int getMajorVersion()` / `int getMinorVersion()`
- `Set<SessionTrackingMode> getDefaultSessionTrackingModes()` / `Set<SessionTrackingMode> getEffectiveSessionTrackingModes()` / `void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes)`
- `Map<String,? extends FilterRegistration> getFilterRegistrations()` / `FilterRegistration getFilterRegistration(String filterName)`
- `Enumeration<String> getInitParameterNames()` / `String getInitParameter(String name)` / `boolean setInitParameter(String name, String value)`
- `JspConfigDescriptor getJspConfigDescriptor()`
- `String getMimeType(String file)`
- `RequestDispatcher getNamedDispatcher(String name)` / `RequestDispatcher getRequestDispatcher(String path)`
- `String getRealPath(String path)`
- `URL getResource(String path)` / `InputStream getResourceAsStream(String path)` / `Set<String> getResourcePaths(String path)`
- `String getServerInfo()`
- `Servlet getServlet(String name)` / `String getServletContextName()` / `Enumeration<String> getServletNames()` / `ServletRegistration getServletRegistration(String servletName)` / `Map<String,? extends ServletRegistration> getServletRegistrations()` / `Enumeration<Servlet> getServlets()`
- `SessionCookieConfig getSessionCookieConfig()`
- `String getVirtualServerName()`
- `void log(Exception exception, String msg)` / `void log(String msg)` / `void log(String message, Throwable throwable)`

## out

> 注：发现 out 在页面可以直接使用，但是，一旦放入自定义方法中即会找不到变量。

out对象向客户端输出内容，由 Web容器指定为 javax.servlet.jsp.JspWriter 类的一个子类。

out对象的常用方法包括：`clear()`清空缓冲区, `clearBuffer()`清空缓冲区, `close()`刷新缓冲区后关闭, `flush()`刷新缓冲区, `getBufferSize()`获得缓冲区大小, `isAutoFlush()`是否自动刷新, `print()`打印 。


JspWriter(抽象)
- `protected JspWriter(int bufferSize, boolean autoFlush)`
- `abstract void clear()`
- `abstract void clearBuffer()`
- `abstract void close()`
- `abstract void flush()`
- `int getBufferSize()`
- `abstract int getRemaining()`
- `boolean isAutoFlush()`
- `abstract void newLine()`
- `abstract void print(boolean b)` / `abstract void print(char c)` / `abstract void print(char[] s)` / `abstract void print(double d)` / `abstract void print(float f)` / `abstract void print(int i)` / `abstract void print(long l)` / `abstract void print(Object obj)` / `abstract void print(String s)`
-  `abstract void println()` / `abstract void println(boolean x)` / `abstract void println(char x)` / `abstract void println(char[] x)` / `abstract void println(double x)` / `abstract void println(float x)` / `abstract void println(int x)` / `abstract void println(long x)` / `abstract void println(Object x)` / `abstract void println(String x)`

## config

config对象是 servletConfig 类的一个对象，用于处理当前页面的句柄（？），仅在页面范围内有效，使用的较少。

# JDBC



# 框架

SSH 通常指的是 Struts2 做控制器(controller)，spring 管理各层的组件，hibernate 负责持久化层。  
SSM 则指的是 SpringMVC 做控制器(controller)，Spring 管理各层的组件，MyBatis 负责持久化层。  
共同点：Spring依赖注入DI来管理各层的组件；用面向切面编程AOP来管理事物、日志、权限等。


