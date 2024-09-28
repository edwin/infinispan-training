package com.redhat.infinispan.training.helper;

import com.redhat.infinispan.training.model.Account;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  com.redhat.infinispan.training.helper.CacheHelper
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 9:12
 */
@Service
public class CacheHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RemoteCacheManager cacheManager;

    @Autowired
    public CacheHelper (RemoteCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void generate() {
        final RemoteCache cache = cacheManager.getCache("account");

        Map<String, Account> accountsMap = new HashMap<>();
        accountsMap.put("1", new Account("1", "edwin-satu"));
        accountsMap.put("2", new Account("2", "dua-edwin"));
        accountsMap.put("3", new Account("3", "tiga-edwin-tiga"));
        accountsMap.put("4", new Account("4", "edward"));
        accountsMap.put("5", new Account("5", "edmund"));

        cache.putAll(accountsMap);
    }


    public List<Account> query() {
        final RemoteCache cache = cacheManager.getCache("account");
        QueryFactory queryFactory = Search.getQueryFactory(cache);
        Query<Account> query = queryFactory.create("FROM tutorial.Account WHERE accountName like :accountName order by accountNumber ASC");

        // set the parameter value
        query.setParameter("accountName", "%edwin%");

        // execute the query
        return query.execute().list();
    }
}
