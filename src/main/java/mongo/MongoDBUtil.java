package mongo;

import com.mongodb.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * MongoDB工具类 Mongo实例代表了一个数据库连接池，即使在多线程的环境中，一个Mongo实例对我们来说已经足够了<br>
 * 注意Mongo已经实现了连接池，并且是线程安全的。 <br>
 * 设计为单例模式， 因 MongoDB的Java驱动是线程安全的，对于一般的应用，只要一个Mongo实例即可，<br>
 * Mongo有个内置的连接池（默认为10个） 对于有大量写和读的环境中，为了确保在一个Session中使用同一个DB时，<br>
 * DB和DBCollection是绝对线程安全的<br>
 *
 * @author zhoulingfei
 * @version 0.0.0
 * @date 2015-5-29 上午11:49:49
 * @Copyright (c)1997-2015 NavInfo Co.Ltd. All Rights Reserved.
 */
public class MongoDBUtil {

    private static MongoClient mongoClient = null;
    private static String db = "";
    private static String collect = "";

    private static MongoClientOptions getConfOptions() {
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        // 设置活跃连接数 默认为10
        build.connectionsPerHost(200);
        // 连接超时时间(毫秒)，默认为10000
        build.connectTimeout(1000 * 3600 * 24);
        // 线程等待连接可用的最大时间(毫秒)，默认为120000
        build.maxWaitTime(120000);
        build.socketTimeout(0);
        MongoClientOptions mongoClientOptions = build.build();
        return mongoClientOptions;
    }

    private static void connect(String cName, String mode) {
        if (mongoClient == null) {

            File f = null;
            if (!mode.equals("driver"))
                f = new File(SparkFiles.getRootDirectory() + "/" + cName);
            else
                f = new File(cName);

            Config c = ConfigFactory.load(ConfigFactory.parseFile(f));
            String mongoIp = c.getString("mongo.ip");
            String mongoAuth = c.getString("mongo.auth");
            String mongoUser = c.getString("mongo.user");
            String mongoPass = c.getString("mongo.pass");
            db = c.getString("mongo.db");
            collect = c.getString("mongo.collection");


//            List<ServerAddress> hosts = new ArrayList<>();
//            for (String address : mongoIp.split(",")) {
//                String host = address.split(":")[0];
//                int port = Integer.parseInt(address.split(":")[1]);
//                hosts.add(new ServerAddress(host, port));
//            }
//            morphia = new Morphia();
//            List<MongoCredential> mongoCredentials = null;
//            if (!StringUtils.isBlank(mongoUser)) {
//                mongoCredentials = new ArrayList<>();
//                mongoCredentials.add(MongoCredential.createScramSha1Credential(mongoUser, mongoAuth, mongoPass.toCharArray()));
//                mongoClient = new MongoClient(hosts, mongoCredentials, getConfOptions());
//            } else {
//                mongoClient = new MongoClient(hosts, getConfOptions());
//            }
            //datastore = morphia.createDatastore(mongoClient, mongoAuth);


            ArrayList mongoCredentials = new ArrayList<>();
            mongoCredentials.add(MongoCredential.createScramSha1Credential(mongoUser, mongoAuth, mongoPass.toCharArray()));
            List<ServerAddress> hosts = new ArrayList<>();
            hosts.add(new ServerAddress(mongoIp.split(":")[0], Integer.parseInt(mongoIp.split(":")[1])));
            mongoClient = new MongoClient(hosts, mongoCredentials, getConfOptions());
        }
    }


    /**
     * 获取collection对象 - 指定Collection
     *
     * @return
     */
    public static DBCollection getCollection(String cName, String mode) {
        connect(cName, mode);
        return mongoClient.getDB(db).getCollection(collect);

    }

    /**
     * 关闭Mongodb
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }


}