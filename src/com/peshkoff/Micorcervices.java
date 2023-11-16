package com.peshkoff;
// ________________________________ MicroServices
//https://www.nginx.com/blog/introduction-to-microservices/
/** MicroService - independently deployed service ( own JVM, project).
 *                 это воплощение паттернов High Cohesion и Low Coupling
 *   Monolith - all services in same JVM, same project.
 *   Synchronous communication HTTP( REST)
 *   Asynchronous - messaging ( JMS, eMail)
 *
 *   Deployment environments:  Kubernetes and Marathon
 * */
/**  Microservices - architecture pattern/template; goal - to sufficiently decompose the application
 *                   to facilitate development and deployment.
 *  - Advantages/Benefits
 *     reduce AppComplexity ??
 *     independent deployment ??
 *     different Stack/Language ??
 *     each service to be scaled independently to satisfy its capacity and availability constraints.
 *  - Disadvantages
 *     total amount of functionality and complexity increases:
 *      - network data exchange
 *      - handle partial failure due to slow or unavailable of services
 *      - partitioned database architecture and distributedTransactions ( CAP theorem -> eventual consistency based approach)
 *      - more complex testing
 *      - changes involves many services
 *      - configure, deploy, scale, and monitor is much more complex
 *      - additional services: ServiceDiscovery, LoadBalancer mechanism
 *      - more automation
 **/
/**
 *  API_Gateway - analogue Facade pattern: security, routing, composition/aggregation, protocol translation,
 *                                         device specific, request caching to hide failures..
 *   benefits for clients: single entry point, hide big inner infrastructure
 *   drawbacks/disadvantages: more complexity, worse request time
 *
 *  MobClient  -> | API_Gateway       |  ->  REST Api_1
 *  WebClient  -> | DiscoveryService  |  ->  REST Api_N
 *                |                   | <->  DiscoveryService
 *
 *  JVM NIO‑based frameworks: Netty, Vertx, Spring Reactor, or JBoss Undertow.
 *  NonJava: NGINX Plus can manage authentication, access control, load balancing requests, caching responses,
 *           and provides application‑aware health checks and monitoring.
 **/
/** ServerSideLoadBalancer:  NGINX server-side discovery load balancer,
 *       AWS ElasticLoadBalancer (ELB) - balances the traffic among ElasticComputeCloud (EC2) instances or
 *                                       ElasticContainerService (ECS) containers
 *  Benefits:  simple clientServices ( all work on 3dParty LoadBalancer)
 *  Drawbacks: more longer requests, yet another service to deploy/maintain
 *                   |              |  <- Register -  Service_B
 *   Service_A  ---> | LoadBalancer |   - Request ->
 *              ---> |              |  <- Register -  Service_C
 *                   |              |   - Request ->
 *
 *  ServerSideLoadBalancer + ServiceDiscovery
 *                   |              |  - Request ->  Service_B   - Register ->
 *   Service_A  ---> | LoadBalancer |  --------------------------- Register ->  ServiceDiscovery
 *              ---> |              |  - Request ->  Service_C   - Register ->
 *                   |              |
 *
 *  ClientSideLoadBalancer: SpringCloudLoadBalancer, Ribbon( Netflix, obsolete)
 *                          FeignClient - web client can work with SpringCloudLoadBalancer or Ribbon( Netflix)
 *  Benefits: client knows available services - makes intelligent, application‑specific load‑balancing
 *  Drawbacks: need implement client‑side service discovery logic for EACH clientService
 *
 *  ClientSideLoadBalancer + ServiceDiscovery
 *    Service_A     |  - Request ->  Service_B   - Register ->
 *     LoadBalancer |  --------------------------- Register ->  ServiceDiscovery
 *                  |  - Request ->  Service_C   - Register ->
 *                  |
 *
 *
 *  ServiceDiscovery:  Netflix EURECA, Apache ZOOKEEPER, CONSUL, etcd
 *
 *  ServiceDiscovery( Eureca)                          <- Service_A ip1 :: RegistryCache
 *    Registry  Key          InstanceList              <- Service_A ip2 :: RegistryCache
 *              Service_A    ip1:port, ip2:port
 *              ..
 *              Service_B    ip3:port                  <- Service_B ip3 :: RegistryCache
 *
 *
 *  ServiceDiscoveryCluster (replication):
 *  ServiceDiscovery_1    <= SharedRegistry =>   ServiceDiscovery_N
 *
 *  Eureca provides REST API for registering and querying
 *  EurecaClient_1 has serviceRegistryCache                 EurecaServer has serviceRegistry; has EurecaClient for replication
 *    - Register in serviceRegistry                     ->  // def = "localhost:8761/eureca"
 *    - eureca/app: GET serviceRegistry                 ->
 *    - eureca/delta: GET serviceRegistryChanges( 30ms) ->
 *    - HeartBeat( def = 30 ms)                         ->   no heartBeat - remove ( def=90 ms)
 *      ..
 *    - DeRegister        ->
 *  ..
 *  EurecaClient_N
 *
 *
 * Self-Registration pattern
 *  Benefit: no need 3dParty components
 *  Drawbacks: need implement registration logic for EACH clientService
 *  Service  -register  -     ServiceDiscovery
 *           -heartBeat -
 *           -unregister-
 *
 * Third-Party Registration pattern ( NetflixOSS Prana, open source Registrator project)
 *  Benefit: no need implement registration logic for EACH clientService
 *  Drawbacks: need 3dParty component: yet another service to deploy/maintain
 *     Service            <-HealthCheck         -   Registrar
 *     ServiceDiscovery   <-RegisterNewServices -
 *
 * **/
/**  Handling partial failures
 *  - Java Netflix Hystrix implements circuitBreaker and other FaultTolerance patterns;
 *  - Resilience4J - alternative of Hystrix;
 *    - CircuitBreaker - FaultTolerance
 *    - RateLimiter - block too frequent request
 *    - TimeLimiter - time lim for calling remote operation
 *    - RetryMechanism - AUTO retry failed remote operation
 *    - Bulkhead - avoid too many concurrent requests
 *    - Cache - caching of costly remote operations
 *
 *  Partial failures dealing strategies:
 *   - network timeouts ( connectionTimeout, requestTimeout)
 *   - limited number of outstanding requests
 *   - fallback(запасной вариант):
 *      - return cachedData/defValue/emptyValue if realtimeResponse impossible
 *      - eventual consistency: put updateRequest into queue while dependency unavailable
 *   - circuitBreaker
 *
 *  StabilityPatterns:
 *  - RetryPattern
 *  - TimeOutPattern
 *  - CircuitBreaker ( Closed, Open, HalfOpen); similar to Decorator
 *  - HandShakingPattern - inform clientService to reduce requests number
 *  - BulkHeadsPattern: - ResourcePerClient ( WebClient, MobClient);
 *                      - ResourcePerApplication = ServicePerContainer/VM
 *                      - ResourcePerEndPoint separateWebClient for eachOutsideResource
 **/
/** Distributed Data management:
 *  - Polyglot persistence approach: Service <-> PrivateDB: encapsulated data, loosely coupled services,
 *                                   better performance and scalability;
 *    diff_DBTypes for services:
 *    - Elasticsearch - text search engine( text data only)
 *    - Neo4j - graph database to store social graph data
 *
 *  Distributed System Data problem:
 *  - Put data into Distributed system
 *    - CAP theorem: or availability/eventualConsistency:
 *      - EventDrivenArchitecture(=Saga) - Transactions-Events are consequences of Model changes;
 *        Transaction -> transaction_1( ModelChange, Event)->..->transaction_N( ModelChange, Event) -> Success
 *               fail <- compensationTran-n_1              <-..<-compensationTran-n_N
 *          // fail any of consecutive transaction -> series of compensationTransaction !
 *          // - loose Isolation!
 *        - Shared DB Table change   -> Event
 *        - DB TransactionLog change -> Event
 *      - EventSourceArchitecture - Events are Model
 *      - ACID-style consistency = Distributed Transactions = Two‑phase commit (2PC)
 *
 *  - Queries data from Distributed system
 *      Solution: MaterializedView - some service aggregate data from multiple services
 * **/
/** Microservices Deployment Strategies
 *  - Multiple Services per Host
 *    Benefit: - effective using of resources ( DB, JVM, Tomcat)
 *             - simple/fast deploy and start
 *    Drawback: - no isolation between services
 *              - no resource limitation
 *              - deploy team must know each service deploy process
 *  - Service per VM ( Amazon EC2 AMI)
 *    Benefit: - isolation between services
 *             - resource limitation
 *             - easy/typical deployment
 *             - AWS and others provide load balancing and autoscaling.
 *    Drawback: - overhead: VM + operationSystem
 *              - VM has static characteristics Memory-CPU-IO - could be underutilized
 *              - hard/long to create new VM image
 *              - long start of new image
 *  - Service per Container - AWS EC2 Container Service (ECS);  might use a cluster manager: Kubernetes or Marathon to manage your containers
 *    Benefit: - same as in VM
 *             - there is no overhead: VM + operationSystem
 *             - easy to create new image
 *    Drawback: - less secure then VM as use same OS_kernel
 *  - Serverless Deploy - AWS Lambda
 *    Benefit: - easy deploy: ZIP + metadata
 *             - easy to start - automatically runs enough instances for requests
 *             - no work for infrastructure
 *    Drawback: - request handling in 300 sec
 *              - stateless services only as may be run many instances concurrently
 *
 * **/
public class Micorcervices {
}
