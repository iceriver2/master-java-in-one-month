> 2019-09-25 考虑到效率问题，资料来源是Java文档的中文版，最新的是[Java8](http://www.matools.com/api/java8)（实话说，这一版的翻译真不行）。从之前的资料可知，Java的最新几个长期版本正好是8，11。尤其是8，出现了大幅更新。BTW 最新版已经到13了。分析核心库的主要目的，不是抄书，而是对核心类的使用有个基本了解，知道在什么时候需要使用什么类。其间，可能会可以忽略某些类，例如，大部分接口，安全类等。

> 2019-09-26 抄书一天半下来，发现：抄录全部方法，有一些效果，但效果的是在归类方法时发生的。整体来说，效率还是偏慢。后面打算采取的策略是：先过一遍类，对于重要的类再抄录方法，不重要的类只保留名称。有些包可能整体被忽略，如 java.nio.channels, java.nio.charset 。

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
- 转为对象：`static Integer decode(String nm)` / `static Integer getInteger(String nm)` / `static Integer getInteger(String nm, int val)` / `static Integer getInteger(String nm, Integer val)`
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

URL
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
  - `static <V extends FileAttributeView>V getFileAttributeView(Path path, 类<V> type, LinkOption... options)`
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
