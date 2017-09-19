package com.lovecws.mumu;

import com.datastax.driver.core.Session;
import com.lovecws.mumu.cassandra.CassandraConfiguration;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 配置测试
 * @date 2017-09-19 14:47
 */
public class CassandraConfigurationTest {

    @Test
    public void connect(){
        Session session = new CassandraConfiguration().connect();
        System.out.println(session);
    }

    @Test
    public void pool(){
        Session session = new CassandraConfiguration().pool();
        System.out.println(session);
    }
}
