> 考虑到效率问题，资料来源是Java文档的中文版，最新的是[Java8](http://www.matools.com/api/java8)。从之前的资料可知，Java的最新几个长期版本正好是8，11。尤其是8，出现了大幅更新。BTW 最新版已经到13了。

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
