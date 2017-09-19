package com.lovecws.mumu.cassandra.quickstart;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.lovecws.mumu.cassandra.CassandraConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 快速入门
 * @date 2017-09-19 13:49
 */
public class CassandraQuickStart {

    String columnfamily = "babyMmTable";
    String PK = "filename";

    /**
     * 创建列族
     */
    Session session = new CassandraConfiguration().connect();

    public void create() {
        String loggedKeyspace = session.getLoggedKeyspace();
        System.out.println(loggedKeyspace);
        String cqlStatement = "CREATE TABLE " + columnfamily + " (" + PK
                + " varchar PRIMARY KEY," + " password varchar ,"
                + " file blob" + ");";
        ResultSet execute = session.execute(cqlStatement);
        System.out.println(execute);
        session.close();
    }

    /**
     * 添加数据
     */
    public void insert() {
        Session session = new CassandraConfiguration().connect();
        String cqlStatement = "INSERT INTO " + CassandraConfiguration.KEYSPACE + "." + columnfamily
                + " (" + PK + ", password) VALUES ('gt', '123456')";
        ResultSet execute = session.execute(cqlStatement);
        System.out.println(execute);
        session.close();
    }

    /**
     * 查询
     */
    public void select() {
        Session session = new CassandraConfiguration().connect();
        String cqlStatement = "SELECT * FROM " + CassandraConfiguration.KEYSPACE + "." + columnfamily;
        for (Row row : session.execute(cqlStatement)) {
            System.out.println(row.toString());
        }
        session.close();
    }

    /**
     * 删除
     */
    public void delete() {
        Session session = new CassandraConfiguration().connect();
        String cqlStatement = "DELETE password FROM " + CassandraConfiguration.KEYSPACE + "." + columnfamily + "  WHERE filename='D:\\logs\\error.log' ";
        ResultSet execute = session.execute(cqlStatement);
        System.out.println(execute);
        session.close();
    }

    /**
     * 更新
     */
    public void update() {
        Session session = new CassandraConfiguration().connect();
        String cqlStatement = "UPDATE " + CassandraConfiguration.KEYSPACE + "." + columnfamily + " SET password='123' WHERE filename='123456' ";
        ResultSet execute = session.execute(cqlStatement);
        System.out.println(execute);
        session.close();
    }

    /**
     * 添加文件
     *
     * @param fileName
     */
    public void insertFile(String fileName) {
        Session session = new CassandraConfiguration().connect();
        FileInputStream fileInputStream = null;
        FileChannel channel = null;
        try {
            fileInputStream = new FileInputStream(new File(fileName));
            ByteBuffer fileByteBuffer = ByteBuffer.allocate(fileInputStream.available());
            channel = fileInputStream.getChannel();
            channel.read(fileByteBuffer);
            fileByteBuffer.flip();
            System.out.println(fileByteBuffer);

            Statement insertFile = QueryBuilder.insertInto(CassandraConfiguration.KEYSPACE, columnfamily)
                    .value(PK, fileName)
                    .value("file", fileByteBuffer)
                    .value("password", "654321");
            ResultSet execute = session.execute(insertFile);
            System.out.println(execute);
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

    /**
     * 读取文件内容
     *
     * @param fileName
     */
    public void readFile(String fileName) {
        Session session = new CassandraConfiguration().connect();
        Statement readFile = QueryBuilder.select("file")
                .from(CassandraConfiguration.KEYSPACE, columnfamily)
                .where(QueryBuilder.eq(PK, fileName));
        Row fileRow = session.execute(readFile).one();
        if (fileRow != null) {
            ByteBuffer fileByteBuffer = fileRow.getBytes("file");
            byte[] bs = null;
            bs = fileByteBuffer.array();
            System.out.println(new String(bs));
        }
    }
}
