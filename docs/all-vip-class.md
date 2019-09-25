> 考虑到效率问题，资料来源是Java文档的中文版，最新的是[Java8](http://www.matools.com/api/java8)。从之前的资料可知，Java的最新几个长期版本正好是8，11。尤其是8，出现了大幅更新。BTW 最新版已经到13了。  
> 分析核心库的主要目的，不是抄书，而是对核心类的使用有个基本了解，知道在什么时候需要使用什么类。其间，可能会可以忽略某些类，例如，大部分接口，安全类等。

- [profile](#profile)
- [Compact1](#compact1)
  - [`java.io`](#javaio)
  - [`java.lang`](#javalang)
  - [`java.lang.annotation`](#javalangannotation)
  - [`java.lang.invoke`](#javalanginvoke)
  - [`java.lang.ref`](#javalangref)
  - [`java.lang.reflect`](#javalangreflect)
  - [`java.math`](#javamath)
  - [`java.net`](#javanet)
  - [`java.nio`](#javanio)
  - [`java.nio.channels`](#javaniochannels)
  - [`java.nio.channels.spi`](#javaniochannelsspi)
  - [`java.nio.charset`](#javaniocharset)
  - [`java.nio.charset.spi`](#javaniocharsetspi)
  - [`java.nio.file`](#javaniofile)
  - [`java.nio.file.attribute`](#javaniofileattribute)
  - [`java.nio.file.spi`](#javaniofilespi)
  - [`java.security`](#javasecurity)
  - [`java.security.cert`](#javasecuritycert)
  - [`java.security.interfaces`](#javasecurityinterfaces)
  - [`java.security.spec`](#javasecurityspec)
  - [`java.text`](#javatext)
  - [`java.text.spi`](#javatextspi)
  - [`java.time`](#javatime)
  - [`java.time.chrono`](#javatimechrono)
  - [`java.time.format`](#javatimeformat)
  - [`java.time.temporal`](#javatimetemporal)
  - [`java.time.zone`](#javatimezone)
  - [`java.util`](#javautil)
  - [`java.util.concurrent`](#javautilconcurrent)
  - [`java.util.concurrent.atomic`](#javautilconcurrentatomic)
  - [`java.util.concurrent.locks`](#javautilconcurrentlocks)
  - [`java.util.function`](#javautilfunction)
  - [`java.util.jar`](#javautiljar)
  - [`java.util.logging`](#javautillogging)
  - [`java.util.regex`](#javautilregex)
  - [`java.util.spi`](#javautilspi)
  - [`java.util.stream`](#javautilstream)
  - [`java.util.zip`](#javautilzip)
  - [`javax.crypto`](#javaxcrypto)
  - [`javax.crypto.interfaces`](#javaxcryptointerfaces)
  - [`javax.crypto.spec`](#javaxcryptospec)
  - [`javax.net`](#javaxnet)
  - [`javax.net.ssl`](#javaxnetssl)
  - [`javax.script`](#javaxscript)
  - [`javax.security.auth`](#javaxsecurityauth)
  - [`javax.security.auth.callback`](#javaxsecurityauthcallback)
  - [`javax.security.auth.login`](#javaxsecurityauthlogin)
  - [`javax.security.auth.spi`](#javaxsecurityauthspi)
  - [`javax.security.auth.x500`](#javaxsecurityauthx500)
  - [`javax.security.cert`](#javaxsecuritycert)
- [Compact2](#compact2)
- [Compact3](#compact3)
- [Full](#full)

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

BufferedInputStream
- `protected byte[] buf` / `protected int count` / `protected int pos`
- `BufferedInputStream(InputStream in)` / `BufferedInputStream(InputStream in, int size)`
- `int available()`
- `int read()` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- `void close()`

BufferedOutputStream
- `protected byte[] buf` / `protected int count`
- `BufferedOutputStream(OutputStream out)` / `BufferedOutputStream(OutputStream out, int size)`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void flush()`

BufferedReader
- `BufferedReader(Reader in)` / `BufferedReader(Reader in, int sz)`
- `boolean ready()`
- `Stream<String> lines()`
- `int read()` / `int read(char[] cbuf, int off, int len)`
- `String readLine()`
- `long skip(long n)`
- `void close()`

BufferedWriter
- `BufferedWriter(Writer out)` / `BufferedWriter(Writer out, int sz)`
- `void newLine()`
- `void write(char[] cbuf, int off, int len)` / `void write(int c)` / `void write(String s, int off, int len)`
- `void flush()`
- `void close()`

ByteArrayInputStream(类似BufferedInputStream)
- `protected byte[] buf` / `protected int count` / `protected int pos`
- `ByteArrayInputStream(byte[] buf)` / `ByteArrayInputStream(byte[] buf, int offset, int length)`
- `int available()`
- `int read()` / `int read(byte[] b, int off, int len)`
- `long skip(long n)`
- `void close()`

ByteArrayOutputStream(类似BufferedOutputStream)
- `protected byte[] buf` / `protected int count`
- `ByteArrayOutputStream()` / `ByteArrayOutputStream(int size)`
- `void write(byte[] b, int off, int len)` / `void write(int b)`
- `void writeTo(OutputStream out)`
- `byte[] toByteArray()`
- `int size()`
- `void close()`
- `String toString()` / `String toString(String charsetName)`

CharArrayReader
- `protected char[] buf` / `protected int count` / `protected int pos`
- `CharArrayReader(char[] buf)` / `CharArrayReader(char[] buf, int offset, int length)`
- `boolean ready()`
- `int read()` / `int read(char[] b, int off, int len)`
- `long skip(long n)`
- `void close()`

CharArrayWriter
- `protected char[] buf` / `protected int count`
- `CharArrayWriter()` / `CharArrayWriter(int initialSize)`
- `CharArrayWriter append(char c)` / `CharArrayWriter append(CharSequence csq)` / `CharArrayWriter append(CharSequence csq, int start, int end)`
- `void write(char[] c, int off, int len)` / `void write(int c)` / `void write(String str, int off, int len)`
- `void writeTo(Writer out)`
- `int size()`
- `char[] toCharArray()`
- `String toString()`
- `void flush()`
- `void close()`

DataInputStream
DataOutputStream

File
FileDescriptor
FilePermission

FileInputStream
FileOutputStream

FileReader
FileWriter

FilterInputStream
FilterOutputStream

FilterReader
FilterWriter

InputStream
OutputStream

InputStreamReader
OutputStreamWriter

ObjectInputStream
ObjectInputStream.GetField
ObjectOutputStream
ObjectOutputStream.PutField

ObjectStreamClass
ObjectStreamField

PipedInputStream
PipedOutputStream

PipedReader
PipedWriter

PrintStream
PrintWriter

PushbackInputStream
PushbackReader

RandomAccessFile

SequenceInputStream
SerializablePermission
StreamTokenizer

StringReader
StringWriter

Reader
Writer

## `java.lang`


## `java.lang.annotation`


## `java.lang.invoke`


## `java.lang.ref`


## `java.lang.reflect`


## `java.math`


## `java.net`


## `java.nio`


## `java.nio.channels`


## `java.nio.channels.spi`


## `java.nio.charset`


## `java.nio.charset.spi`


## `java.nio.file`


## `java.nio.file.attribute`


## `java.nio.file.spi`


## `java.security`


## `java.security.cert`


## `java.security.interfaces`


## `java.security.spec`


## `java.text`


## `java.text.spi`


## `java.time`


## `java.time.chrono`


## `java.time.format`


## `java.time.temporal`


## `java.time.zone`


## `java.util`


## `java.util.concurrent`


## `java.util.concurrent.atomic`


## `java.util.concurrent.locks`


## `java.util.function`


## `java.util.jar`


## `java.util.logging`


## `java.util.regex`


## `java.util.spi`


## `java.util.stream`


## `java.util.zip`


## `javax.crypto`


## `javax.crypto.interfaces`


## `javax.crypto.spec`


## `javax.net`


## `javax.net.ssl`


## `javax.script`


## `javax.security.auth`


## `javax.security.auth.callback`


## `javax.security.auth.login`


## `javax.security.auth.spi`


## `javax.security.auth.x500`


## `javax.security.cert`




# Compact2

`java.io`


`java.lang`


`java.lang.annotation`


`java.lang.invoke`


`java.lang.ref`


`java.lang.reflect`


`java.math`


`java.net`


`java.nio`


`java.nio.channels`


`java.nio.channels.spi`


`java.nio.charset`


`java.nio.charset.spi`


`java.nio.file`


`java.nio.file.attribute`


`java.nio.file.spi`


`java.security`


`java.security.cert`


`java.security.interfaces`


`java.security.spec`


`java.text`


`java.text.spi`


`java.time`


`java.time.chrono`


`java.time.format`


`java.time.temporal`


`java.time.zone`


`java.util`


`java.util.concurrent`


`java.util.concurrent.atomic`


`java.util.concurrent.locks`


`java.util.function`


`java.util.jar`


`java.util.logging`


`java.util.regex`


`java.util.spi`


`java.util.stream`


`java.util.zip`


`javax.crypto`


`javax.crypto.interfaces`


`javax.crypto.spec`


`javax.net`


`javax.net.ssl`


`javax.script`


`javax.security.auth`


`javax.security.auth.callback`


`javax.security.auth.login`


`javax.security.auth.spi`


`javax.security.auth.x500`


`javax.security.cert`



# Compact3

`java.lang.instrument`


`java.lang.management`


`java.security.acl`


`java.util.prefs`


`javax.annotation.processing`


`javax.lang.model`


`javax.lang.model.element`


`javax.lang.model.type`


`javax.lang.model.util`


`javax.management`


`javax.management.loading`


`javax.management.modelmbean`


`javax.management.monitor`


`javax.management.openmbean`


`javax.management.relation`


`javax.management.remote`


`javax.management.remote.rmi`


`javax.management.timer`


`javax.naming`


`javax.naming.directory`


`javax.naming.event`


`javax.naming.ldap`


`javax.naming.spi`


`javax.security.auth.kerberos`


`javax.security.sasl`


`javax.sql.rowset`


`javax.sql.rowset.serial`


`javax.sql.rowset.spi`


`javax.tools`


`javax.xml.crypto`


`javax.xml.crypto.dom`


`javax.xml.crypto.dsig`


`javax.xml.crypto.dsig.dom`


`javax.xml.crypto.dsig.keyinfo`


`javax.xml.crypto.dsig.spec`


`org.ietf.jgss`



# Full
