package com.peshkoff;
// ________________________________ Theory
/**
 * - FunctionalProgramming - sequence of functions with local parameters;
 *      + more reliable; better ||-sm; easier testing and local changing
 *      - more work with memory for local vars; uncertain sequence of funcs bad for consecutive IO
 * - ImperativeProgramming - sequence of states of global vars
 *
 * - SOLID - Single responsibility; Open(inheritance)/closed(changing); Liskov(consequence of O);
 *           Interface segregation; Dependency inversion (abstraction)
 *
 * - DDD - Eric Evans "Domain-Driven Design: Tackling Complexity in the Heart of Software"
 *         Vaughn Vernon: https://github.com/VaughnVernon/IDDD_Samples
 *   - Goal - to solve domain-related problems
 *   - UbiquitosLanguage ( повсеместный)
 *   - Domain - formal description of businessDomain( area); may containe subDomains( Core, Supporting,..)
 *   - BoundedContext - logicaly separating business context; subDomain may has many boundedContexts;
 *                      piese of functionality may work separately;
 *   - LayeredArchitecture: Services, Repositories; Model
 *   - Model: Entities, ValueObjects( no ID), Aggregates( nasterEntity + slaveEntities), Factories
 *   - DomainEvent - significant for customer; reflects changes in domain model
 *
 * - ES - Event Sourcing( Storing)
 *   - Aplicable when: must not lose data; need reliable audit log; high level of scalability required.
 *   - EventRules: Events are immutable (never lose data); Chronological order of Events
 *   - TransactionalSystem
 *       insert -> EventList --synchronousUpdate-->  Projection/View/readDataModel -> read
 *   - EventuallyConsistentSystem
 *       insert -> EventList --asynchronousUpdate--> Projection/View/readDataModel -> read
 *
 * - CQRS - Command Query Responsibility Segregation; applicable for ES,
 *          scaling separately read and write, eventual data consistensy tolerant case
 *   - API level ( exception: pop())
 *   - App level with single data model
 *   - App level with two data models( ins/update/del -> writeDataModel -asyncUpdate-> readDataModel -> read)
 * - CQRS/ES vs CRUD
 *
 * - CAP - ConsistensyAvailabilityPartitionTolerance распредСистема в любое время может обеспечить не более 2-ч из 3-х
 * */
// ________________________________ HTTP
/** https://developer.mozilla.org/ru/docs/Glossary/safe
 * - Идемпотентный метод - не меняет состояние сервера при множестве запросов но возврат может
 *       отличаться - получение данных или статистики
 *   POST/create - NO_idempotent: N identicalRequest = N new records
 *   PUT/update  - idempotent:  N identicalRequest = same result
 *
 * - HTTP retCodes
 *   - 100-199 - info
 *   - 200-299 - success:    200 - OK
 *   - 300-399 - redirect
 *   - 400-499 - client error: 400 badRequest; 403 forbidden; 404 NotFound
 *   - 500-599 - server error
 *
 * - JWT - JSON WebToken
 *    header:  { "typ":"JWT", "alg":"HS256"}
 *    payload: { "id","userName","role","inspire",..}
 *    signature:"hashCode( header.payload)"
 *
 * - CAP - ConsistensyAvailabilityPartitionTolerance распредСистема в любое время может обеспечить не более 2-ч из 3-х
 * */
// ________________________________ WEB
/** HTTP Session: Establish TCP connection; 1-N. HTTP_request <-> HTTP_response;
 *
 *  SOAP - simple object access protocol, official standard, XML wrapped into HTTP, FTP, SMTP.
 *
 *  REST - stands for representational state transfer. Set of design principals( architecture):
 *  Client-server
 *  Resource - data or functionality accessible by static URI
 *             HTTP methods PUT, GET, POST, DELETE
 *             PDF, TEXT, JSON, XML wrapped into HTTP package.
 *  Stateless - server doesn't keep history/state of client, each request is standalone.
 *  JAX-RS to annotate Java classes to create RESTful web services
 *  http://localhost:8080/greeting?name=User  ->  {"id":1,"content":"Hello, User!"}
 * */
// ________________________________ ApacheMaven
/** Maven - framework, build tool, everything do plugins,
*    - mvn install -DskipTests        // to skip running the tests( unit + integration) but still we want to compile them
 *   - mvn install -DskipITs          // to skip running the integration_tests only
 *   - mvn -Dmaven.test.skip package  // to skip the compilation phase; overrides property from POM
 *     <properties>                   // same in POM
 *         <tests.skip>true</tests.skip>
 *     </properties>
 *
*  Artifact - anyLib(jar) in repository.
*  Goal - special task, could be connected to a phase or not.
*  Scope - compile, runTime, test,...
*  Repository - local (~/.m2/repository/..), global https://repo.maven.apache.org/maven2/, remote
*  ProjectObjectModel - pom.xml
*  <project ...>
*      <groupId>com.peshkoff</groupId>
*      <artifactId>myProject</artifactId>
*      <version>1.0-SNAPSHOT</version>
*      <packaging>pom</packaging>
*      <name>name</name>
*    <modules>
*        <module>InnerModule</module>
*    <modules>
*    <properties/>
*      <dependencies>
*         <dependency><groupId/><artifactId/><version/>
*                     <scope>test</scope>
*         </dependency>
*      </dependencies>
*      <plugins/>
*      <goals/>
*  </project>
*  Archetype - MavenFolderStructure for some type of project
*  project
*     pom.xml
*     src/main/java/*.java
*             /resources/
*             /webapp/
*        /test/java/
*             /resources/
*     target/classes
*           /*jar(*.war)
*  LifeCycle - sequential phases
*  validate - verify pom.xml
*  compile - *.java -> *.class
*  test - run unit tests
*  package - *.classes -> jar/war
*  verify - run integration test
*  install - copy jar/war into local repository
*  deploy - copy jar/war into remote Maven repository
*
*  clean - delete /target folder (all previously compiled classes, resources, packages)
*
*  site - generate docs and site
**/

// ________________________________ JMS specification
/** PointToPoint messaging domain: OneToOne, warranted delivery - noTimingDependency sender/receiver
*                |       |     -Msg->
*  Sender -Msg-> | Queue | <-Acknowledge- Receiver( onlyOne)
*
*  Publish/Subscribe messaging domain: ManyToMany, NOT warranted delivery - timingDependency sender/receiver
*                |       | <-Subscribe-
*  Sender -Msg-> | Topic |    -Msg->      Receives( Many)
*
*   DurableSubscription - way to rid of timingDependency
*   Messaging: asynchronous - MessageListeners
*              synchronous  - receive() - explicitly fetches the message as usual method.
*
*  ProgrammingModel( ParticipatingObjects)
*
*  ConnectionFactory( connectionConfigurationParameters) ->
*    Connection( TCP_ConnectionToProvider_wrapper)->
*      Session -> MessageConsumer( Destination d).receive( timeOut)
*                                                .setMessageListener( MessageListener l)
*              -> MessageProducer( Destination d).send( message)
*              -> Message ( create mess of specific Type)
*              -> Browser( Queue) ( brows messages in Queue)
*
*  Message: header ( ID, Type, Priority, Expiration, Destination,...),
*           properties ( user defined),
*           body ( TextMessage, MapMessage, BytesMessage, SerializableMessage,... )
*
*  Administered objects by sysAdmin, get from ApplicationServer though JNDI:
*     ConnectionFactory, Destinations( Queue, Topic)
*
*  Open source messaging systems:  RabbitMQ, Apache Kafka, Apache ActiveMQ, and NSQ
* */
// ________________________________ MicroServices
//https://www.nginx.com/blog/introduction-to-microservices/
/** MicroService - independently deployed service ( own JVM, project).
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
 *      - configure, deploy, scale, and monitore is much more complex
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
/**  Handling partial failures
 *
 *  Partial failures dealing strategies:
 *   - network timeouts ( connectionTimeout, requestTimeout)
 *   - limited number of outstanding requests
 *   - fallback(запасной вариант):
 *      - return cachedData/defValue/emptyValue if realtimeResponce impossible
 *      - eventual consistency: put updateRequest into queue while dependency unavailable
 *   - circuitBreaker
 *
 *  Java Netflix Hystrix implements all this patterns;
 **/
/**
 *  ServerSideLoadBalancer:  NGINX server-side discovery load balancer,
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

public class ThirdParty {
}
