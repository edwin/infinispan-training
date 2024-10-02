# Connecting Spring Boot to Infinispan using Redis Client 

## Version
- Java 17
- Spring Boot 3.3.4
- Infinispan Server 15.0.7.Final

## Concept
Using `Infinispan` as a cache server, while connecting to it by using `Spring Boot` and `Redis Client`. Showing the capability of `Infinispan` in providing a `Redis` compliant API (https://infinispan.org/docs/stable/titles/resp/resp-endpoint.html)

## How to Test
Adding an `Account` data
```
$ curl -kv http://localhost:8080/accounts -X POST \ 
    -d '{"accountNumber":"ryok002","accountName":"Ryoko Hirosue"}' \
    -H "Content-Type: application/json"
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> POST /accounts HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 57
>
< HTTP/1.1 201
< Content-Length: 0
< Date: Wed, 02 Oct 2024 05:25:47 GMT
<
* Connection #0 to host localhost left intact
```

Getting all `Accounts`
```
$ curl -kv http://localhost:8080/accounts
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /accounts HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.4.0
> Accept: */*
>
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 02 Oct 2024 05:25:50 GMT
<
* Connection #0 to host localhost left intact
[{"accountNumber":"ryo002","accountName":"Ryoko Hirosue"},{"accountNumber":"yui003","accountName":"Yui Aragaki"},{"accountNumber":"sat001","accountName":"Satomi Ishihara"}] 
```