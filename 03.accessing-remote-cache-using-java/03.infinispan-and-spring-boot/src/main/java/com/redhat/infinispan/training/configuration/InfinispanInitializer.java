package com.redhat.infinispan.training.configuration;

import com.redhat.infinispan.training.model.Account;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.marshall.MarshallerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;

/**
 * <pre>
 *  com.redhat.infinispan.training.configuration.InfinispanInitializer
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Sep 2024 10:44
 */
@Component
public class InfinispanInitializer implements CommandLineRunner {

    private RemoteCacheManager cacheManager;

    @Autowired
    public InfinispanInitializer (RemoteCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void run(String...args) throws Exception {
        // put schema on server
        SerializationContext ctx = MarshallerUtil.getSerializationContext(cacheManager);
        RemoteCache<String, String> metadataCache = cacheManager.getCache(org.infinispan.query.remote.client.ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String msgSchemaFile = protoSchemaBuilder.fileName("account.proto").packageName("tutorial").addClass(Account.class).build(ctx);
        metadataCache.put("account.proto", msgSchemaFile);
    }

}
