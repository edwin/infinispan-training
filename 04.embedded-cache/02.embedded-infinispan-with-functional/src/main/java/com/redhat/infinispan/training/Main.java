package com.redhat.infinispan.training;

import org.infinispan.AdvancedCache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.functional.EntryView;
import org.infinispan.functional.FunctionalMap;
import org.infinispan.functional.impl.FunctionalMapImpl;
import org.infinispan.functional.impl.ReadOnlyMapImpl;
import org.infinispan.functional.impl.WriteOnlyMapImpl;
import org.infinispan.manager.DefaultCacheManager;

import java.util.concurrent.CompletableFuture;


/**
 * <pre>
 *  com.redhat.infinispan.training.Main
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 13:27
 */
public class Main {

    private DefaultCacheManager cacheManager;
    private AdvancedCache<String, String> cache;
    private FunctionalMapImpl<String, String> functionalMap;
    private FunctionalMap.WriteOnlyMap<String, String> writeOnlyMap;
    private FunctionalMap.ReadOnlyMap<String, String> readOnlyMap;

    public static void main(String[] args) throws Exception {
        new Main().process();
    }

    private void process() throws Exception {
        // initialize
        cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration("my-local-cache", new ConfigurationBuilder().build());
        cache = cacheManager.<String, String>getCache("my-local-cache").getAdvancedCache();
        functionalMap = FunctionalMapImpl.create(cache);
        writeOnlyMap = WriteOnlyMapImpl.create(functionalMap);
        readOnlyMap = ReadOnlyMapImpl.create(functionalMap);

        // two parallel process
        CompletableFuture<Void> writeFuture1 = writeOnlyMap.eval("key1", "value1",
                (v, writeView) -> writeView.set(v));
        CompletableFuture<Void> writeFuture2 = writeOnlyMap.eval("key2", "value2",
                (v, writeView) -> writeView.set(v));

        // read after write
        CompletableFuture<String> readFuture1 =
                writeFuture1.thenCompose(r -> readOnlyMap.eval("key1", EntryView.ReadEntryView::get));
        CompletableFuture<String> readFuture2 =
                writeFuture2.thenCompose(r -> readOnlyMap.eval("key2", EntryView.ReadEntryView::get));

        // when the read-only operation completes, print it out
        System.out.printf("Created entries: %n");
        CompletableFuture<Void> end = readFuture1.thenAcceptBoth(readFuture2, (v1, v2) ->
                System.out.printf("key1 = %s%nkey2 = %s%n", v1, v2));

        // wait for this read/write combination to finish
        end.get();
    }

}
