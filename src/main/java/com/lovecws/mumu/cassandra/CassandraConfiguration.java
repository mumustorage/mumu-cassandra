package com.lovecws.mumu.cassandra;

import com.datastax.driver.core.*;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: cassandra配置信息
 * @date 2017-09-19 14:41
 */
public class CassandraConfiguration {

    public static String CASSANDRA_HOST = "192.168.11.25";
    public static int CASSANDRA_PORT = 9042;
    public static String KEYSPACE = "system_auth";
    public Cluster cluster;
    public Session session;

    //从环境变量中获取cassandra配置信息
    static {
        String cassandra_host = System.getenv("CASSANDRA_HOST");
        String cassandra_port = System.getenv("CASSANDRA_PORT");
        if (cassandra_host != null) {
            CASSANDRA_HOST = cassandra_host;
        }
        if (cassandra_port != null) {
            CASSANDRA_PORT = Integer.parseInt(cassandra_port);
        }
    }

    /**
     * 获取cassandra连接
     */
    public Session connect() {
        cluster = Cluster.builder()
                .addContactPoints(CASSANDRA_HOST)
                .withPort(CASSANDRA_PORT)
                //.withCredentials("cassandra", "cassandra")
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect(KEYSPACE);
        return session;
    }

    /**
     * 使用连接池的方式获取cassandra连接
     */
    public Session pool() {
        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 32);
        poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 2)
                .setMaxConnectionsPerHost(HostDistance.LOCAL, 4)
                .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
                .setMaxConnectionsPerHost(HostDistance.REMOTE, 4);

        cluster = Cluster.builder()
                .addContactPoints(CASSANDRA_HOST)
                .withPort(CASSANDRA_PORT)
                //.withCredentials("cassandra", "cassandra")
                .withPoolingOptions(poolingOptions).build();

        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
        }
        // 建立连接
        session = cluster.connect(KEYSPACE);
        return session;
    }
}
