package com.lovecws.mumu.quickstart;

import com.lovecws.mumu.cassandra.quickstart.CassandraQuickStart;
import org.junit.Test;

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
        cassandraQuickStart.insertFile("D:/logs/error.log");
    }

    @Test
    public void readFile() {
        cassandraQuickStart.readFile("D:/logs/error.log");
    }
}
