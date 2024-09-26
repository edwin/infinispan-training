package com.redhat.infinispan.training;

import com.redhat.infinispan.training.model.Account;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.infinispan.commons.configuration.XMLStringConfiguration;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;

/**
 * <pre>
 *  com.redhat.infinispan.training.Main
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 26 Sep 2024 20:01
 */

public class Main {

    public static final String USER = "admin";
    public static final String PASSWORD = "password";
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 11222;
    public static final String CACHE_NAME = "account";

    public static void main(String[] args) {
        new Main().process();
    }

    private void process() {
        try {
            // initialize
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.addServer()
                    .host(HOST)
                    .port(PORT)
                    .security()
                    .authentication()
                    .username(USER)
                    .password(PASSWORD);

            RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

            // success
            System.out.println("successfully connect to : " + cacheManager.getServers()[0]);

            // connect to remote cache
            RemoteCache<Object, Object> cache = cacheManager.administration().getOrCreateCache(CACHE_NAME,
                        new XMLStringConfiguration("<distributed-cache statistics=\"true\">\n" +
                                                    "    <encoding>\n" +
                                                    "        <key media-type=\"application/x-protostream\"/>\n" +
                                                    "        <value media-type=\"application/x-protostream\"/>\n" +
                                                    "    </encoding>\n" +
                                                    "    <indexing enabled=\"true\" storage=\"filesystem\" startup-mode=\"AUTO\">\n" +
                                                    "        <indexed-entities>\n" +
                                                    "            <indexed-entity>tutorial.Account</indexed-entity>\n" +
                                                    "        </indexed-entities>\n" +
                                                    "    </indexing>\n" +
                                                    "</distributed-cache>"));

            // put schema on server
            SerializationContext ctx = MarshallerUtil.getSerializationContext(cacheManager);
            RemoteCache<String, String> metadataCache = cacheManager.getCache(PROTOBUF_METADATA_CACHE_NAME);
            ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
            String msgSchemaFile = protoSchemaBuilder.fileName("account.proto").packageName("tutorial").addClass(Account.class).build(ctx);
            metadataCache.put("account.proto", msgSchemaFile);

            // put some data on cache
            Map<String, Account> accountsMap = new HashMap<>();
            accountsMap.put("1", new Account("1", "edwin-satu"));
            accountsMap.put("2", new Account("2", "dua-edwin"));
            accountsMap.put("3", new Account("3", "tiga-edwin-tiga"));
            accountsMap.put("4", new Account("4", "edward"));
            accountsMap.put("5", new Account("5", "edmund"));

            cache.putAll(accountsMap);

            // get cache content
            if (cache != null) {
                QueryFactory queryFactory = Search.getQueryFactory(cache);
                Query<Account> query = queryFactory.create("FROM tutorial.Account WHERE accountName like :accountName");

                // set the parameter value
                query.setParameter("accountName", "%edwin%");

                // execute the query
                List<Account> accounts = query.execute().list();

                // print the results
                for (Account account : accounts) {
                    System.out.println(account);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
