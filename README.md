# ls
locationservice
FLUME是什么
Flume是一个分布式的、可靠的采集、聚合、移动日志的系统，它有一个简单灵活的基于数据流的系统架构，并且具有可调节的可靠性的能力。

Flume核心概念
Agent: 一个运行着的Flume进程就叫做一个Agent
Source: source运行在Agent内部，负责从外部系统拉取日志数据，封装成Event，每个Event由日志数据和可选的header数据组成，header是一组key-value映射，source拉取日志封装成Event后，再将Event送入channel中
Channel: channel是Event的临时存储空间，可以把它想象成一个FIFO的队列，当Event被sink取走后，该Event会从channel中移除掉
Sink: sink从channel中拉取Event，将Event中的数据写入外部存储或者写入下一个Agent的source

Flume拓展概念
ChannelSelector: channelSelector作用于source和channel之间，一个source的数据可以写入多个channel中，可以通过配置channelSelector来控制source里的数据按怎样的逻辑写入channel，比如可以从多个channel中选取某些channel写入数据，也可以将数据往每个channel都写入
Interceptor: interceptor作用于source和channelSelector之间，用来过滤掉一些不符合要求的数据
SinkProcessor:sinkProcessor是sink的执行器，有三种类型的sinkProcessor，分别是FailoverSinkProcessor、LoadBalancingSinkProcessor和DefaultSinkProcessor，默认采用DefaultSinkProcessor.


Flume架构
Flume并不像我们所熟知的其他分布式系统，有着各种各样的角色，角色之间又有着各种从属关系，比如Master-Slave,Master-Master结构，Flume本质上就是一个单机程序，它的分布式体现在数据流设计人员可以根据业务需要将各个单机Flume程序组成一个层次模型；每个Flume进程都是同构的，内部都运行着source、channel和sink，我们可以将一个Flume的sink设计成写入另一个flume的source，当然也可以直接写入到外部的存储系统，比如HDFS、Hbase、Kafka等，下面是Flume的常用部署模式：

1.直接从数据源采集到外部存储系统中























2.多个agent组成数据流，由最后一个agent写入外部存储


















3.多对一的合并，由一个agent收集多个agent的写入数据

































4.一对多输出模型，可以将一份数据写入多种外部存储系统









































Flume 运行时线层模型


常用Source介绍
SPOOLDIR: spoolDir Source监控一个目录下的文件，读取文件里的数据，按照一定的逻辑将数据封装成Event对象传给channel，spoolDir只能处理不可改变的、在该目录下唯一命名的文件。

EXEC：Exec Source执行一个指定的unix命令，并且把该命令的输出转化为Event传给channel，由于flume不能反过来通知unix命令进程，所以当channel满了后，再往channel中送的数据就会丢失掉，ExecSource是不可靠的。

AVRO:  Avro Source 监听一个端口，该端口只能监听avro类型的数据，通过该端口收集Avro Sink发过来的Event数据。

SYSLOGTCP: 读取syslog数据并转化为Event对象，每行产生一个Event对象。
SYSLOGUDP：读取syslog数据并转化为Event对象，整个消息数据产生一个Event对象。
NETCAT：NetCat Source打开一个指定的端口监听数据，将收集到的数据的每行转化为一个Event对象。
HTTP：Http Source接受HTTP POST和GET请求并转化为Event对象。
KAFAK: Kafka Source读取kafka集群的数据并转化为Event对象。


常用channel介绍
MEMORY: Event数据存放在内存中，优点是吞吐量高，缺点是可能丢失数据。

FILE：将Event数据存放在磁盘上，优点是容量大，不会丢失数据，但吞吐量不高。

SPILLABLEMEMORY：采用内存+磁盘的方式存储Event，当内存满了后才会写磁盘，当前只是实验性的channel，不建议生产使用。



常用sink介绍
HDFS：将采集到的数据直接写到HDFS上。
HBASE：将采集到的数据直接写到HBASE上的表中。
KAFKA：将采集到的数据写到kafak集群。
AVRO: 将数据传给下一跳的Agent，下一跳的Agent使用Avro source接收。


Flume常用使用方式

SPOOLDIR->MEMORY/FILE->HDFS

SPOOLDIR->MEMORY/FILE->HBASE

SPOOLDIR->MEMORY/FILE->KAFKA

SPOOLDIR->MEMORY/FILE->HDFS





自定义Source

自定义Sink
