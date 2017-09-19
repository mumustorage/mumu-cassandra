package com.lovecws.mumu.quickstart;

import com.lovecws.mumu.cassandra.quickstart.CassandraQuickStart;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 基本测试
 * @date 2017-09-19 14:57
 */
public class CassandraQuickStartTest {

    private CassandraQuickStart cassandraQuickStart = new CassandraQuickStart();

    @Test
    public void create() {
        cassandraQuickStart.create();
    }

    @Test
    public void insert() {
        cassandraQuickStart.insert();
    }

    @Test
    public void select() {
        cassandraQuickStart.select();
    }

    @Test
    public void delete() {
        cassandraQuickStart.delete();
    }

    @Test
    public void update() {
        cassandraQuickStart.update();
    }

    @Test
    public void insertFile() {
        File tempFile = new File("/tmp/cassandra");
        //文件不存在 则创建
        if (!tempFile.exists()) {
            FileInputStream fileInputStream = null;
            FileChannel channel = null;
            try {
                tempFile.createNewFile();
                //将数据写入到文件中
                fileInputStream = new FileInputStream(tempFile);
                channel = fileInputStream.getChannel();
                String content = "Cassandra 的数据模型是基于列族（Column Family）的四维或五维模型。它借鉴了 Amazon 的 Dynamo 和 Google's BigTable 的数据结构和功能特点，采用 Memtable 和 SSTable 的方式进行存储。在 Cassandra 写入数据之前，需要先记录日志 ( CommitLog )，然后数据开始写入到 Column Family 对应的 Memtable 中，Memtable 是一种按照 key 排序数据的内存结构，在满足一定条件时，再把 Memtable 的数据批量的刷新到磁盘上，存储为 SSTable 。";
                ByteBuffer byteBuffer = ByteBuffer.allocate(content.getBytes().length);
                byteBuffer.put(content.getBytes());
                channel.write(byteBuffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileInputStream.close();
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        cassandraQuickStart.insertFile("/tmp/cassandra");
    }

    @Test
    public void readFile() {
        cassandraQuickStart.readFile("/tmp/cassandra");
    }
}
