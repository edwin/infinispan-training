package com.redhat.infinispan.training;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

/**
 * <pre>
 *  com.redhat.infinispan.training.Main
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 26 Sep 2024 19:33
 */

public class Main {

    public static final String USER = "admin";
    public static final String PASSWORD = "password";
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 11222;
    public static final String CACHE_NAME = "balance";

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
                    .password(PASSWORD)
                    .remoteCache(CACHE_NAME);

            RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

            // success
            System.out.println("successfully connect to : " + cacheManager.getServers()[0]);

            // connect to remote cache
            RemoteCache<Object, Object> cache = cacheManager.getCache(CACHE_NAME);

            // get cache content
            if (cache != null) {
                Object value = cache.get("account01");

                // success get cache value from key
                System.out.println("cache value is : " + value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
