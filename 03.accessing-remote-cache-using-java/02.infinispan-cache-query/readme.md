# Simple Indexed Query to a Remote Infinispan Server 

## Cache Configuration
```xml
<distributed-cache statistics="true">
    <encoding>
        <key media-type="application/x-protostream"/>
        <value media-type="application/x-protostream"/>
    </encoding>
    <indexing enabled="true" storage="filesystem" startup-mode="AUTO">
        <indexed-entities>
            <indexed-entity>tutorial.Account</indexed-entity>
        </indexed-entities>
    </indexing>
</distributed-cache>
```

## Version
- Java version 11
- Infinispan client and server both are at version 14.0.31.Final