package org.demo.dao;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * MongoDB in memory for tests factory.
 */
public class MongodFactory {

    private static class Instance {
        public MongodExecutable mongodExe;
        public MongodProcess mongod;
        public int counter = 0;
    }

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private static Map<Integer, Instance> instanceByPorts = new HashMap<Integer, Instance>();

    public static synchronized void newMongo(Integer port) {
        if(instanceByPorts.get(port) == null) {
            try {
                Instance instance = new Instance();
                instance.mongodExe = starter.prepare(new MongodConfigBuilder()
                        .version(Version.Main.PRODUCTION)
                        .net(new Net(12345, Network.localhostIsIPv6()))
                        .build());
                instance.mongod = instance.mongodExe.start();

                instance.counter++;

            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static synchronized void shutdown(Integer port) {
        Instance instance = instanceByPorts.get(port);
        instance.counter--;
        if(instance.counter <= 0) {
            instance.mongod.stop();
            instance.mongodExe.stop();
        }
    }

}
