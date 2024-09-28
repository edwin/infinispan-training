package com.redhat.infinispan.training;

import com.redhat.infinispan.training.model.Balance;
import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.IndexStorage;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;

import java.util.List;

/**
 * <pre>
 *  com.redhat.infinispan.training.Main
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 13:53
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
        builder.indexing()
                .enable()
                .storage(IndexStorage.LOCAL_HEAP)
                .addIndexedEntity(Balance.class);

        // build the cache
        Cache<String, Balance> cache = cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("balance_cache", builder.build());

        // add some entries
        cache.put("1", new Balance("Edwin", 10000L));
        cache.put("2", new Balance("Edwin", 2000L));
        cache.put("3", new Balance("Someone Else", 500000L));

        // prepare query
        QueryFactory queryFactory = Search.getQueryFactory(cache);

        // create query
        Query<Balance> query = queryFactory.create("from com.redhat.infinispan.training.model.Balance where id like 'Edwin'");

        // execute
        List<Balance> balanceList = query.execute().list();

        // print it
        balanceList.forEach(balance -> System.out.printf("Match: %s%n", balance));

        // stop it
        cacheManager.stop();
    }

}
