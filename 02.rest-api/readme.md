# Using Infinispan with Rest API

We can access Infinispan directly by using a Rest API. It able to provide all the cache management and its lifecycle such as PUT and GET data from a specific caches.     

```
By default Data Grid REST API operations return 200 (OK) when successful. However, when some operations are processed successfully, they return an HTTP status code such as 204 or 202 instead of 200.
```

## Rest URL and Authentication
Default URL for `Infinispan` REST API would be 
```
http://localhost:11222/rest/v2/
```

but almost all of provided APIs need a specific credentials to access it, which is can be defined when doing a curl request
```
$ curl -kv http://localhost:11222/rest/v2/caches/  \ 
        --digest -u admin:password
```

## Cache Operations
We can start by trying to add some data into existing `balance` cache,
```
$ curl -X POST -kv http://localhost:11222/rest/v2/caches/balance/account01  \ 
        --digest  -u admin:password   \ 
        -d '10000000'
```

a successful request would give this result,
```
* Connection #0 to host localhost left intact
* Issue another request to this URL: 'http://localhost:11222/rest/v2/caches/balance/account01'
* Found bundle for host: 0x20452bf8 [serially]
* Re-using existing connection with host localhost
* Server auth using Digest with user 'admin'
> POST /rest/v2/caches/balance/account01 HTTP/1.1
> Host: localhost:11222
> Authorization: Digest username="admin", realm="default", nonce="AAAAFAAAIFYVaTr44dUm7dLbdlnslo2QPWLSrc8kzClfHhnHts/0dWZE4Cs=", uri="/rest/v2/caches/balance/account01", cnonce="ZmFmNjYxZTAzMmM0MWE1ZWIzOTkwYzFmMWM4MzgzYzE=", nc=00000001, qop=auth, response="2bd14b4029f5e67adb5e35a3a3412e8d", opaque="00000000000000000000000000000000", algorithm=MD5
> User-Agent: curl/8.4.0
> Accept: */*
> Content-Length: 6
> Content-Type: application/x-www-form-urlencoded
>
< HTTP/1.1 204 No Content
< etag: -1842881760
<
* Connection #0 to host localhost left intact
```

To validate whether we are able to insert our data to cache successfully, we can run below Rest API request with our specific cache key.
```
$ curl -X GET -kv http://localhost:11222/rest/v2/caches/balance/account01 \
        --digest  -u admin:password
```

with the result like this, which is going to display the value of the requested `account01` key.
```
* Connection #0 to host localhost left intact
* Issue another request to this URL: 'http://localhost:11222/rest/v2/caches/balance/account01'
* Found bundle for host: 0x20452be8 [serially]
* Re-using existing connection with host localhost
* Server auth using Digest with user 'admin'
> GET /rest/v2/caches/balance/account01 HTTP/1.1
> Host: localhost:11222
> Authorization: Digest username="admin", realm="default", nonce="AAAAGQAAIJlsUDJI7ZcKNudzgRq5+NyyL6ZaWCgYQnUWTIXwubjdrC8k4CU=", uri="/rest/v2/caches/balance/account01", cnonce="ZDQwMjIyODU1ZGE5NmIzODNiN2NmZjY4OWMwODQ1YWE=", nc=00000001, qop=auth, response="f1ca45ece2eab2574e956fa8dd67d892", opaque="00000000000000000000000000000000", algorithm=MD5
> User-Agent: curl/8.4.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Etag: -1842881760
< Content-Type: text/plain
< content-length: 6
<
* Connection #0 to host localhost left intact
100000                       
```

To display all the data within one specific Cache store, we can use below `curl` command
```
$ curl -X GET -kv http://localhost:11222/rest/v2/caches/balance?action=entries \ 
        --digest  -u admin:password
```

it will generate below result
```
* Connection #0 to host localhost left intact
* Issue another request to this URL: 'http://localhost:11222/rest/v2/caches/balance?action=entries'
* Found bundle for host: 0x20452c10 [serially]
* Re-using existing connection with host localhost
* Server auth using Digest with user 'admin'
> GET /rest/v2/caches/balance?action=entries HTTP/1.1
> Host: localhost:11222
> Authorization: Digest username="admin", realm="default", nonce="AAAAGAAAIG4JvxDAKSobH8/A9aLAbGQYu6GOLz/aS/hS4ku9ucL6Ns7CJPc=", uri="/rest/v2/caches/balance?action=entries", cnonce="OWY1ZmNmZjM0YzUzOGI2ZjdkMjQyNmY3Y2QzZjNmMDc=", nc=00000001, qop=auth, response="0f6f2ea131753a5eabf471043ef9631e", opaque="00000000000000000000000000000000", algorithm=MD5
> User-Agent: curl/8.4.0
> Accept: */*
>
< HTTP/1.1 200 OK
< value-content-type: text/plain
< key-content-type: text/plain
< Content-Type: application/json
< transfer-encoding: chunked
< connection: keep-alive
<
* Connection #0 to host localhost left intact
[{"key":"account01","value":"100000"}]           
```