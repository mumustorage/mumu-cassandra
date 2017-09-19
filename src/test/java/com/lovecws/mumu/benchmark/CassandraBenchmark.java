package com.lovecws.mumu.benchmark;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.lovecws.mumu.cassandra.CassandraConfiguration;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jmh并发测试
 * @date 2017-09-19 17:18
 */
public class CassandraBenchmark {

    private static final Session session = new CassandraConfiguration().connect();

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void read() {
        String cqlStatement = "SELECT * FROM " + CassandraConfiguration.KEYSPACE + ".babyMmTable";
        session.execute(cqlStatement);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void write() {
        String cqlStatement = "INSERT INTO " + CassandraConfiguration.KEYSPACE + ".babyMmTable (filename, password) VALUES ('" + System.currentTimeMillis() + "', '1234567890')";
        ResultSet execute = session.execute(cqlStatement);
    }

    @Test
    public void testRead() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(CassandraBenchmark.class.getSimpleName() + ".read$")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .threads(1)
                .shouldFailOnError(true)
                .shouldDoGC(false)
                .build();
        new Runner(options).run();
        session.close();
    }

    @Test
    public void testWrite() {
        Options options = new OptionsBuilder()
                .include(CassandraBenchmark.class.getSimpleName() + ".write$")
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(1)
                .threads(1)
                .shouldDoGC(false)
                .shouldFailOnError(true)
                .build();
        try {
            new Runner(options).run();
        } catch (RunnerException e) {
            //e.printStackTrace();
        }
        session.close();
    }
}
