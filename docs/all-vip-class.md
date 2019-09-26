> 考虑到效率问题，资料来源是Java文档的中文版，最新的是[Java8](http://www.matools.com/api/java8)（实话说，这一版的翻译真不行）。从之前的资料可知，Java的最新几个长期版本正好是8，11。尤其是8，出现了大幅更新。BTW 最新版已经到13了。  
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

RandomAccessFile(随机读写)
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
