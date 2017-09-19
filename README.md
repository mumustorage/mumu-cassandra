# mumu-cassandra 列族No-SQL数据库
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumucache/mumu-riak/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.weibo/motan.svg?label=Maven%20Central)](https://github.com/mumustorage/mumu-cassandra)
[![Build Status](https://travis-ci.org/mumustorage/mumu-cassandra.svg?branch=master)](https://travis-ci.org/mumustorage/mumu-cassandra)
[![codecov](https://codecov.io/gh/mumustorage/mumu-cassandra/branch/master/graph/badge.svg)](https://codecov.io/gh/mumustorage/mumu-cassandra)
[![OpenTracing-1.0 Badge](https://img.shields.io/badge/OpenTracing--1.0-enabled-blue.svg)](http://opentracing.io)  
**Cassandra 的数据模型是基于列族（Column Family）的四维或五维模型。它借鉴了 Amazon 的 Dynamo 和 Google's BigTable 的数据结构和功能特点，采用 Memtable 和 SSTable 的方式进行存储。在 Cassandra 写入数据之前，需要先记录日志 ( CommitLog )，然后数据开始写入到 Column Family 对应的 Memtable 中，Memtable 是一种按照 key 排序数据的内存结构，在满足一定条件时，再把 Memtable 的数据批量的刷新到磁盘上，存储为 SSTable 。***

## 简介  
Apache Cassandra™ is a massively scalable open source NoSQL database. Cassandra is perfect for managing large amounts of structured, semi-structured, and unstructured data across multiple data centers and the cloud. Cassandra delivers continuous availability, linear scalability, and operational simplicity across many commodity servers with no single point of failure, along with a powerful dynamic data model designed for maximum flexibility and fast response times.  
## 存储引擎
### CommitLog
CommitLog是一个文件追加日志文件，客户端的任何数据写入到cassandra都必须要先写入commitLog，然后在将数据写入到memtables中。以防止服务器宕机，而memtables只是保存在内存中，如果不先写入到cimmitLog，则会丢失数据。当重启的时候，cassandra从commitLog中读取数据写入到memtables中。   
CommitLog默认commitlog_segment_size_in_mb是32M，当文件超过这个大小的时候，cassandra会自动的进行创建新的日志文件，而以前的日志文件则可以归档、删除或者轮训一次。

### Memtables
Memtables是基于内存的数据结构，基本上每一个表都存在一个活跃的memtable。当memtables占用内存大小超过了memtables设置的阈值或者commitLog日志文件达到最大值的时候，cassandra会将memtables回写到SSTables中。

### SSTables
SSTables是保存在硬盘上的数据文件。当memtables回写到SSTables的时候，cassandra会将多个SSTable文件归并为一个SSTable,并且删除原来的SSTable文件。
每一个SSTable都压缩多个组件到一个文件上:
- Data.db  
The actual data, i.e. the contents of rows.
- Index.db  
An index from partition keys to positions in the Data.db file. For wide partitions, this may also include an index to rows within a partition.
- Summary.db  
A sampling of (by default) every 128th entry in the Index.db file.
- Filter.db  
A Bloom Filter of the partition keys in the SSTable.
- CompressionInfo.db  
Metadata about the offsets and lengths of compression chunks in the Data.db file.
- Statistics.db  
Stores metadata about the SSTable, including information about timestamps, tombstones, clustering keys, compaction, repair, compression, TTLs, and more.
- Digest.crc32  
A CRC-32 digest of the Data.db file.
- TOC.txt  
A plain text list of the component files for the SSTable.


## 相关阅读  
[Apache-cassandra官网](http://cassandra.apache.org)  
[Apache Cassandra架构理解](http://www.360doc.com/content/16/0624/06/6902273_570291132.shtml)

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**
