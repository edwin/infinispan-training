package com.redhat.infinispan.training;

import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * <pre>
 *  com.redhat.infinispan.training.Main
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 11:43
 */
public class Main {

    private EmbeddedCacheManager cacheManager;
    private Cache<String, String> cache;

    public static void main(String[] args) {
        new Main().process();
    }

    private void process() {
        // initialize
        cacheManager = new DefaultCacheManager();

        // config
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.simpleCache(true);

        // create
        cache = cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("my-default-cache", builder.build());

        // generate data
        generate();

        // print result
        printAll();
    }

    private void generate() {
        for (int i = 0 ; i < 100 ; i++) {
            cache.put("key" + i, "value" + i);
        }
    }

    private void printAll() {
        cache.entrySet()
                .forEach(entry -> System.out.printf("%s = %s\n", entry.getKey(), entry.getValue()));
    }

}
