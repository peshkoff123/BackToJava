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
 *   GET, HEAD, PUT, DELETE - idempotent
 *
 * - HTTP retCodes
 *   - 100-199 - info
 *   - 200-299 - success:    200 - OK
 *   - 300-399 - redirect
 *   - 400-499 - client error: 400 badRequest; 403 forbidden; 404 NotFound
 *   - 500-599 - server error
 *
 * - HTTP/2.0:
 *  - Binary -
 *  - Frames - HTTP_message = [1..n] Frames;
 *     Header{ Length, Type, Flags, StreamID} PayLoad{..}
 *      Type: Data, Headers, Priority, Rst_Stream, Settings, PushPromise, Ping, GoAway, Window_Update, Continuation
 *  - Stream - set of Frames with same FrameID; Connection = [1..n] Streams;
 *  - ServerPush - push data to client cache; 1 request - many response
 *  - Switch to HTTP 2.0:
 *      UpgradeRequest: HTTP/1.1_Request              Headers "Connection:Upgrade", "Upgrade:HTTP/2.0"
 *                      HTTP/1.1_Response retCode=101 Headers "Connection:Upgrade", "Upgrade:HTTP/2.0"
 * - HTTP/3.0 changes: UDP instead TCP
 *
 * - gRPC - binary protocol for microServices faster that JSON/REST; uses ProtoBuf; based on HTTP/2;
 *          supported by many languages; not supported by browsers yet
 *
 * - ProtocolBuffers - binary encoding data format for serialization; rival for JSON;
 *                     language to describe data format; uses encoded/binary data
 *    - protofile - data schema description doc
 *       publisher.proto
 *         service Publisher { rpc SignBook (SignRequest) ret (SignResponse) }
 *         message SignRequest { string name=1;}
 *         message SignResponse { string signature=1;}
 *
 * - SSO - Single Sign-On - authentication schema to access multiple trusted but independent app using single ID
 *   - SAML - SecurityAssertionMarkupLanguage XML protocol;
 *            SAML_Assertion: encrypted XML{ UserDetails, Athorities} - analog JWT
 *            Identity providers: Okta, Auth0, oneLogin
 *   - OpenID connect - Google JWT protocol
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
 *
 *  Partial failures dealing strategies:
 *   - network timeouts ( connectionTimeout, requestTimeout)
 *   - limited number of outstanding requests
 *   - fallback(запасной вариант):
 *      - return cachedData/defValue/emptyValue if realtimeResponce impossible
 *      - eventual consistency: put updateRequest into queue while dependency unavailable
 *   - circuitBreaker
 *
 *  Java Netflix Hystrix implements circuitBreaker and all this patterns;
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
 *                      - EventDrivenArchitecture - Events are consequences of Model changes;
 *                         Transaction -> transaction_1( ModelChange, Event)+..+transaction_N( ModelChange, Event):
 *                         - Shared DB Table change   -> Event
 *                         - DB TransactionLog change -> Event
 *                       - EventSourceArchitecture - Events are Model
 *                   or ACID-style consistency = Distributed Transactions = Two‑phase commit (2PC)
 *
 *  - Queries data from Distributed system
 *      Solution: MaterializedView - some service aggregate data from multiple services
 * **/
/** Micorcervices Deployment Strategies
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
// ________________________________ Git
/** Git - for maintaining fileSystemSnapshots at specific time moments
 * Main notions
 * SHA-1 HASH - unique key/REF for BLOB, TREE, COMMIT; depends of content/body
 * REF - HASH
 * BLOB - body of file container (not metedata/fileAttributes)
 * TREE - directory/folder; contains REFs on subTREEs + BLOBs in that COMMIT or parent
 * COMMIT - snapshot of WORKING DIRECTORY + PARENT_COMMIT_REF+ Message+Date+Author
 * BRANCH - sequence of COMMITs/snapshots/changes dependent to each other;
 *        - named REF to last COMMIT
 *        - MASTER/MAIN; TRUNC
 *        - HEAD - special REF/pointer on current/working branch
 * WORKING DIRECTORY/TREE - project dir + .git folder
 * INDEX/STAGING AREA - register changes here and then push to COMMIT; to verify/confirm changes before commit them
 * REPOSITORY - collection of COMMITs, BRANCHES, other Git stuff
 * TAG - special name for commit
 * PATCH - enhanced diff; asymmetric
 *
 *  HEAD -> MAIN -> COMMIT_i
 *  HEAD -> BRANCH_k -> COMMIT_j
 *
 *  Main commands Git
 *  - init - create REPOSITORY, MASTER/MAIN branch, .git folder
 *      git init my_repo
 *  - add <file.name> - add file to INDEX/STAGING
 *      git add a.txt
 *  - commit - save snapshot into HEAD
 *      git commit -m "My first commit"
 *
 *  - checkout - re-point HEAD
 *  - reset <commit.REF> - switch to one of previous COMMITs = re-point commit.REF!
 *                         to replace last mistakable commit; should NOT be used after PUSH COMMIT !
 *      --soft   discard-LastCommit;
 *      --mixed  discard-LastCommit+AddIndex; def behaviour ( git reset=git reset --mixed);
 *      --hard   discard-LastCommit+AddIndex+LocalFileChanges;
 *      git reset --soft commit.REF
 *  - revert - reverse changes of last COMMIT by NEW_COMMIT
 *      git revert HEAD
 *  - merge - merges two branches, result - new commit
 *                         - com_N+1 - .. - com_M                 <- branch
 *          com_1 - com_N  - com_N+1 - com_merge := com_M         <- HEAD
 *     git checkout <brach_1>  // <- HEAD
 *     git merge <brach_1>
 *  - rebase - move baseCommit of branch to another commit of MASTER branch
 *                         - -moveBase-> - com_N+1 - .. - com_M                 <- branch
 *                  from com_N to com_N+1
 *          com_1 - com_N  - com_N+1           <- HEAD
 *     git checkout <brach_1>  // <- HEAD
 *     git rebase <brach_1(MASTER)>
 *  - cherry-pick <some.commit> - commit changes from <some.commit> into HEAD
 *                         - com_N+1 - .. - com_M                 <- branch
 *          com_1 - com_N  - com_N+1 - com_cherry-pick := com_N+2 <- HEAD
 *     git checkout <some.brach(MASTER)>
 *     git cherry-pick <some.commit>
 *
 *  - log
 *  - status - current state ( branch, STAGING, untrackedFiles)
 *      git status
 *  - diff <commit_1> <commit_2>
 *      git diff HEAD~1 HEAD; git diff HEAD~2 HEAD~1
 *  - show <some.commit>
 *  - reflog - show gits log
 *
 *  - push - to remote REPOSITORY
 *  - pull - from remote REPOSITORY
 *
 *  - stash CTRL_X - place uncommited changes on shelve
 *     git stash                       // CTRL_X
 *     git stash save "stash comment"
 *     git stash list                  // see all stack of stashes
 *     git stash apply <number( 0,1)>  // CTRL_V; def number = last
 *     git stash pop                   // CTRL_V + remove that stash
 *  - Itellij/Shelve - direct analogue os git stash
**/
// ________________________________ Docker
/** Docker - tool to deploy and run app using container. DockerClient(CLI) <- REST_API -> DockerServer
 *
 *  Virtualization: OS :: Hypervisor :: GuestOS_1 :: App_1           // overhead - GuestOS
 *                                   :: GuestOS_N :: App_N
 *
 *  Containerization: OS :: ContainerEngine :: Container_1 :: App_1  // light weight containers
 *                                          :: Container_1 :: App_1
 *
 *  + lightwaight, dynamicalMemoryConsumption
 *  - less isolation
 *  Container - process in HostOS ( dynamicalMemoryConsumption)
 *            - package contains app + libs + dependencies + envSettings.
 *   FilesInContainer
 *    - WritableContainerLayer( def) though "storageDriver" ( slow, notAccessible outside)
 *    - Volumes - part of HostFileSystem created/managed by Docker (/var/lib/docker/volumes/ on Linux);
 *                can be mounted into multiple containers simultaneously; Volume drivers allow you to store your data
 *                on remote hosts or cloud providers. Allows Linux fileSyst behavior on Win PC.
 *    - BindMounts - shared part of HostFileSystem ( anywhere); very performant, but they rely on the
 *               host machine’s filesystem
 *    - inMemory files: - tmpfs for Linux, - namedPipe for Win
 *
 *  Dockerfile - txtFile - commands to create DockerImage
 *    Dockerfile -> DockerImage -> DockerHub
 *                              -> DockerContainer
 *   Dockerfile:
 *    FROM openjdk:8
 *    WORKDIR /app
 *    ADD
 *    COPY
 *    RUN
 *    EXPOSE 8080
 *    ENTRYPOINT ["java","-jar","myjar.jar"]
 *
 *
 *  docker build -t dock-test-jar .    // Dockerfile in this folder
 *  docker build -t dock-test-jar:1.0 .
 *  mvn spring-boot:build-image        // NO Dockerfile needed - looks better
 *    //https://www.youtube.com/watch?v=1w1Jv9qssqg - Spring Boot 2.3, a new feature was added that enables you
 *    to create Docker Images from your application using Cloud Native Buildpacks
 *
 *  docker run -d -p 8080:8080 --name mycont1 docker-rest-test:0.0.1-SNAPSHOT
 *                                       //curl 192.168.99.100:8080/
 *
 *  docker ps                            // runned container list
 *  docker ps -a                         // runned and stopped container list
 *  docker logs contName
 *  docker inspect contName              // get the internal container ip
 *  docker exec -it contName bash        // exec command in runned container
 *
 *  docker images
 *  docker image ls
 *  docker stop contName
 *  docker rm -f <container>
 *  docker rmi -f <image>
 *
 *  docker pull ubuntu/chiselled-jre:8-22.04_edge
 *
 *  docker volume create myVolume                 // Volume
 *  docker volume list
 *  docker volume inspect myVolume
 *  docker volume rm myVolume
 *  docker run --name imageName -p 3306:3306 -d   // Run with Volume
 *             -e MYSQL_ROOT_PASSWORD=my-secret-pw
 *             --mount source=myVolume,target=/var/lib/mysql
 *         mysql
 *
 *  docker run --name imageName -p 3306:3306 -d   // Run with BindMount
 *             -e MYSQL_ROOT_PASSWORD=my-secret-pw
 *             --mount type=bind,source="$(pwd)"/mysql_bind_folder,target=/var/lib/mysql
 *         mysql
 *  // Win specifically:
 *  docker run --name db
 *    -e MYSQL_ROOT_PASSWORD=somewordpress -e MYSQL_PASSWORD=wordpress -e MYSQL_DATABASE=wordpress -e MYSQL_USER=wordpress
 *    --mount type=bind,source=%cd%/mariadb_data,target=/var/lib/mysql
 *    -d
 *    mariadb:10.6.4-focal
 *    --default-authentication-plugin=mysql_native_password
 *
 *  // Difference between -v and --mount is that :
 *    -v can create a directory if it didn't exist,
 *       -v "$(pwd)"/mariadb_data:/var/lib/mysql
 *    --mount gives an error if the directory doesn't exist
 *       --mount type=bind,source="$(pwd)"mariadb_data,target=/var/lib/mysql
 *
 *
 *  DockerCompose - tool to create/manage/cleanUp multiContainer/multiVolumes/networking apps
 *  DockerComposeFile: compose.yaml(yml) or docker-compose.yaml(yml)
 *  docker compose up - compose.yml -> DockerDemon -run/update-> containers/networking/volumes of App
 *  docker compose up -d   // start containers from compose.yml
 *  docker compose down    // stop containers from compose.yml
 *
 *             compose.yml
 *  services:
 *   db:
 *     image: mariadb:10.6.4-focal
 *     command: '--default-authentication-plugin=mysql_native_password'
 *     volumes:
 *       - mariadb_data:/var/lib/mysql
 *     restart: always
 *     environment:
 *       - MYSQL_ROOT_PASSWORD=somewordpress
 *       - MYSQL_DATABASE=wordpress
 *       - MYSQL_USER=wordpress
 *       - MYSQL_PASSWORD=wordpress
 *     expose:
 *       - 3306
 *       - 33060
 *   wordpress:
 *     image: wordpress:latest
 *     volumes:
 *       - wordpress_data:/var/www/html
 *     ports:
 *       - 8081:80
 *     restart: always
 *     environment:
 *       - WORDPRESS_DB_HOST=db
 *       - WORDPRESS_DB_USER=wordpress
 *       - WORDPRESS_DB_PASSWORD=wordpress
 *       - WORDPRESS_DB_NAME=wordpress
 * volumes:
 *   mariadb_data:
 *   wordpress_data:
 */
// ________________________________ Kubernetes
/** Kubernetes - opensource platform to automatically deploy, manage, scale containers in high-loaded distributed system.;
 *               facilitate declarative config and automation;
 *               Kubernetes originates from Greek, meaning helmsman(рулевой) or pilot
 *   + effective using of hardwareResources and infrastructure ( Up/DownContainers ~ load)
 *   + less human resources - less admins needed due to automation
 *  K8s provides:
 *  - ServiceDiscovery_LoadBalancer using DNS names or IPs
 *  - StorageOrchestration - automatically mount storages: local, cloud, ..
 *  - Automated Rollout and Rollback - actualState -> desiredState at controlledRate
 *  - Automated Bin packing - spec CPU, RAM fo containers
 *  - SelfHealing - restart, stop containers, healthCheck
 *  - Secret and ConfigManagement - store, manage sensitive info: passwords, OAuthTokens, SSH keys
 *
 *
 *  Deployment.yml - infrastructure description; IaaC - Infrastructure as a Code
 *   + infrastructure versions and change tracking
 *   + fast and reliable deploy
 *
 *  Kubernetes Cluster Components:
 *  - kubelet - agent runs on each Node; manages( runs, healthChecks) Containers described in PodSpecs
 *  - KUBE-PROXY - network proxy runs on each Node, load balancing, routing - traffic forward between Pods and outside
 *  - ContainerRuntime
 *
 *
 *  Kubernetes Cluster Components:
 *  - Cluster: set of worker machines/Nodes
 *  - Node/Worker - VM/HardwareServer + Docker + [KUBELET(agent) + KUBE-PROXY]; hosts Pods
 *  - ControlPlane - control all the K-sCluster; orchestration layer to manage WorkerNodes and Pods;
 *                   ControlPlaneComponents - scheduling, starting new Pods, ..
 *    ControlPlaneComponents started on separate machine:
 *    - kube-apiServer - API to change Cluster state (HTTP + JSON/PROTOBUF); horizontally scalable
 *    - ETCD - key-value DB persists state of system;
 *    - kube-SCHEDULER - select Node, start Pods;
 *    - kube-CONTROLLER-MANAGER - track K-sCluster state, runs Controllers:
 *             nodeController, jobController( Pods, Tasks), endPointSliceController(Service <-Link-> Pods),
 *             serviceAccountController( create serviceAccount for new NameSpaces), ..
 *             Controller - makes change curState -> desiredState according apiServer info
 *    - cloudController-Manager - cloud specific logic of Cluster: link Cluster to cloudAPI, ..
 *    ETCD, SCHEDULER, CONTROLLER-MANAGER   <-  GRPC/PROTOBUF  ->  API_Serever
 *
 * Objects ( need to detach of infrastructure):
 *  - Pod - group 1..n logically tied Containers - usually 1, if many - Containers on same server?;
 *          has privateIP; min controllable entity
 *    REPLICASET - POD + Container(POD)Number(autoscale system)
 *    DEPLOYMENT - REPLICASET = strategy of PODs update:
 *                 -recreate: delete all old PODs then create new PODs,
 *                 -rollingUpdate: { delete old POD, create new POD} - update one after one
 *                 -customStrategy
 *    SERVICE - netInterface to PODs; loadBalancer/serverSideServiceDiscovery for PODs;
 *              outside router for PODs: POD -> outsideDB
 *              parameters: REPLICA SET + portMapping for input traffic
 *    CONFIGMAP.yaml - yaml with settings( param.key1:value1) for Manifests( .yaml descriptions)
 *
 * Manifest - YAML/JSON doc to describe Objects
 *
 * kubectl - console util to control Objects and Manifests
 *   kubectl apply -f Service.yaml --record
 */
// ________________________________ AWS
/**
 *   Public_Internet Zone <----> AWS_Public Zone <-- InternetGateWay --> AWS_Private Zone
 *                        <------------------------- ( IGW)          --> VPCloud
 *                                  S3                                   EC2
 *
 *   Internet <- InternetGateWay -> |                    VPC
 *                                  |    PublicSubnet              PrivateSubnet
 *                                  |    ELB, NAT        <--->        EC2, DB
 *
 *   HybridConnectivity:    VPC  <- VPN, DirectConnect -> CompanyOnPremise_DataCenter, RemoteWorkers
 *   VPC <-- VPC_Peering( traffic in AWS, 1x1 ONLY!) --> VPC
 *   multiple_VPCs  <--> TransitGateway <- VPN, DirectConnect -> CompanyOnPremise_DataCenter, RemoteWorkers
 *
 *   S3, DynamoDB  <-- | VPC_Endpoint/Gateway( traffic in AWS),| --> EC2
 *   SNS,SQS,SES   <-- | ElasticNetworkInterface               |
 *   CloudWatch    <-- |                                       |
 *     // S3, DynamoDB, SNS,SQS,SES, CloudWatch  and EC2 - in same region !
 *
 *   VPC_Endpoint/Gateway  <-- PrivateLinc( traffic in AWS) --> 3-d party SaaS
 *
 *  SaaS - Soft As A Service
 *  PaaS - platform As A Service ( installed OS + Application)
 *  IaaS - Infrastructure As A Service ( hardware servers + network)
 *
 * Global Services:
 *   Route53( DNS server),
 *   IAM - Identity Access Management
 *      ARN - Amazon Resource Name
 *   Billing
 *   ElasticBeanstalk - WEB Application ( Tomcat, Java, Docker,..); create EC2 instance; autoConfigure by script file
 * Region Services:
 *   SES - SimpleEmailService
 *   SQS - SimpleQueueService: no guarantees FIFO;     PullNotification; 256KB - limit;
 *           RetentionPeriod: [1 min..14days],4days-def
 *           VisibilityTimeOut:[30sek..12Hours],30sek-def; implicit call DeleteMessage
 *         FIFO SQS - guarantees FIFO( more expensive)
 *   SNS - SimpleNotificationService: Pub/Sub, Topics, PushNotifications; Sub-rs: HTTP,SMS,eMail,Lambda,SQS,..
 *
 *   CloudFront - CDN - Content Delivery Network; Cache for statical data; Public entryPoint to S3?
 *   CloudWatch - monitoring of our services/infrastructure
 *   CloudFormation - language to describe/manage AWS resources; infrastructure in code; analog Terraform
 *
 *   RDS - RelationalDB Service ( endPoint to) Oracle, MsSQL, MySQL(noReplication), PostgeSQL(noReplication), Avrora - AmazonPostgres+Replication
 *   DynamoDB - NoSQL DB
 *
 *   VPC - VirtualPrivateCloud
 *   IGW - InternetGateway for PublicSubnets;
 *         from PublicSubnets access to Internet - allowed, from Internet to PrivateSubnet - allowed too
 *   NAT - NetworkAddressTranslation gateway for PrivateSubnets;
 *         from PrivateSubnet access to Internet - allowed, from Internet to PrivateSubnet - disallowed
 *   VGW - VirtualGateway entry point for VPN
 *   Network ACL - Network AccessControlList
 *   CIDR - ClasslessInterDomainRouting ( 10.10.0.0/16 - 10.10-fixed for subnet)
 *
 *   ECR - Elastic Container Registry: analog for Docker Hub
 * AvailabilityZoneServices:
 *   EC2 - Elastic Compute Cloud
 *     AMI - Amazon Machine Image: AmazonLinux, RedHat, Win,.., userImage
 *     SecurityGroup - like AmazonFireWall
 *   RDS
 *   ELB - ElasticLoadBalancer
 *   ALB - Application LoadBalancer
 *
 *
 *
 *  Geographic Areas: America, Canada, Africa, India, Europe
 *  Regions in Europe: Frankfurt, Ireland, London, Milan, Paris, Stockholm
 *  Region contains at least 2 AvailabilityZones( DataCenters) backing up each other
 *
 *  AWS_Account - container for
 *     RootUser(eMail, cardInfo) has FULL control of Users, Resources
 *   - IdentityAccessManagement( IAM): Users( programmaticAccess, AWS_Console_Access), userGroups,
 *                                     Policies/Permissions,
 *                                     Roles: Permissions for entities/instances/servers
 *      - all permissions (full or limited) must be granted directly
 *      - external Identities can be granted access to that Account as well
 *   - Resources: S3_Buckets, EC2, ..
 *
 *  VPCloud - autocreated for account, 1 region, access to all AvailabilityZones in that Region
 *  VPC components: SubNet, RouteTable, FireWall( SecurityGroup + Network AccessControlList)
 *     NetworkAddressTranslation( NAT)
 *
 * AWS Storage
 *   EBS - Elastic Block Storage ( Volume) - lowLatency nonShared storage for EC2, RDS; AvailabilityZone
 *   S3 - Simple Storage Service ( Bucket) - unlimSpace; standalone accessible from Internet by HTTP/HTTPS; Global/Region
 *   EFS - Elastic File System ( FileSystem) - shared for EC2; Region
 *   FSx - EFS for Win ( File System) - shared for EC2; SMB(Samba)Protocol; Region
 *
 * AWS S3
 *     - Standard - data in two dataCenters
 *     - Infrequently Access - data in two dataCenters
 *     - Reduced Redundancy - data in one dataCenter
 *     - AmazonGlacier - archive/fileStorage with big latency (3-5 hours)
 *
 * AWS ELB - Elastic Load Balancer
 *  - ALB - Application Load Balancer: HTTP,HTTPS <--> Lambda, EC2, ECS; NO VPC
 *  - NLB - Network Load Balancer:     TCP, UDP   <--> elastic=static_IP, EC2; ultra-high performance, TLS offloading, centralized certificate deployment
 *  - GWLB - Gateway Load Balancer: ?
 *  - CLB - Classic Load Balancer: for EC2 network
 *  LoadBalancer:
 *  Listener get request ( Protocol:Port) and redirect to TargetGroup( EC2,ECS,IP,Lambda) accordingly to RoutingRules
 *    HTTPS_Listener - can offload TLS encryption/decryption( HTTPS -> HTTP)
 *
 *
 * AWS EC2 - ElasticComputeCloud
 *    - HHS_Client( analog Putty) - MobaXterm
 *    - LinuxCommands:
 *        echo "File content" > /var/www/html/index.html
 *      ls;  ls -l
 *        sudo -i                         // goto root dir
 *        java -version
 *        yum install java-1.8.0-openjdk
 *        alternatives --config java
 *        wget https://res_name           // get file from S3
 *        wget https://s3.eu-north-1.amazonaws.com/first.java.bucket/dock-test.jar
 *        java -jar jar_name.jar
 *      sudo -y yum install httpd         // answer: -y
 *      sudo service httpd start
 *      chkconfig httpd on                // autostart httpd
 *        cd /var/www/html                // index.html
 *        sudo vi index.html
 *        wq!
 *
 * AWS ECS - Elastic Container Service ( EC2 + Docker):
 *           orchestration service to -deploy, -manage, -scale containerized app;
 *           allows to focus on app not environment;
 *           send your container instance log information to CloudWatch Logs
 *  Cluster - logical and regional group of Tasks or ECS Instances; intention - isolate our app;
 *  Instance - EC2 + DockerServer
 *  Task Definition - JSON doc, describes containers (up to 10 containers); analogue of DockerCompose
 *                    DockerContainer/Image  + DRAM + CPU + Ports
 *  Task - instantiation of TaskDefinition; DockerContainer ( runned)
 *  Service - Tasks manager: maintain desired Tasks number, reload terminated/failed Tasks, terminate redundant Tasks; AutoScalingGroup for containers;
 *            creates Tasks on basis: TaskDefinition + serviceDescription
 *  Launch types:
 *  - EC2 LaunchType: EC2 + Docker in Cluster;                        - payment for EC2
 *  - Fargate LaunchType: serverless: WITHOUT EC2 and infrastructure; - payment for runningContainers;
 *                                    NetworkMode for each Container: "AWSVPC"( new NetworkInterface/ip)
 *   TaskDefinition +     |  -> {Task + ElasticNetworkInterface(ip)}_1; ..
 *   Sevice (optionally)  |  -> {Task + ElasticNetworkInterface(ip)}_N
 *
 * AWS ECR - Elastic Container Registry: DockerImages storage; analog for Docker Hub
 *           use aws_cli to login to AWS + docker push imageName:version
 * AWS EKS - Elastic Container Service for Kubernetes
 *
 *
 * AWS Lambda - serverless event-driven compute service;
 *              zero administration, managing, servers for user;
 *              automatic administration, scaling, logging
 *              charging based on request number and time duration of code exec-n( no code running - no charge)
 *
 * DeploymentPackage - Function( Code) - zip/jar or OCI_containerImage ( OpenContainerInitiative)
 * Function URL - dedicated HTTP/S endpoint to my Lambda
 *     Function local storage: "/tmp" directory
 *     Function logs -> CloudWatch Logs; "AWSLambdaBasicExecutionRole" need for that
 * SynchronousInvocation - Trigger( AWS_Service)   -Event( JSON)->   Function( ourCode)
 * AsynchronousInvocation - Trigger( AWS_Service)   -Event( JSON)->  EventQueue  -Event( JSON)->  Function( ourCode)
 * ExecutionEnvironment - isolated runtime env + lifecycle for Function + ExternalExtension
 *             ExecutionEnvironment
 *     Processes          API Endpoints
 *      Function     <---> RuntimeAPI                  <--->  Lambda
 *      ExtExtension <---> ExtensionAPI, TelemetryAPI  <--->  Service
 *  ExecutionEnvironmentLifecycle
 *
 * Layer - additional zip's with lib/dependency/data; yp to 5 per Function;
 *         content -> "/opt" dir in the execution environment
 *         N/A for containers
 * Extension - 3-d_party soft/tools;
 *             - AWS_provided, - own_Lambda_extensions
 *             - internals: in same lifecycle; - externals: separate processes
 * Concurrency - concurrent request - concurrent LambdaInstances; sequent request - same LambdaInstance
 * Qualifier - version/alias "my-func:1" or "my-func:PROD"
 * Destination - AWS resource for messages from Lambda ( errors or success)
 *
 * LambdaFunctionHandlers:
 * import com.amazonaws.services.lambda.runtime.Context, RequestHandler, LambdaLogger
 * public class Handler implements RequestHandler<Map<String,String>, String>    {
 *   public String handleRequest(Map<String,String> event, Context context)  {..}}
 *
 * public class HandlerS3 implements RequestHandler<S3Event, String> {
 *   public String handleRequest(S3Event event, Context context) {..}}
 *
 * public class HandlerStream implements RequestStreamHandler {
 *   public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
 *   {..}                                                     }
 *
 * LambdaFunction by Container
 * - Use BaseLambdaImage( myLambda <-HTTP interface-> AWS Lambda) to build myImage
 * - Upload myImage int ECR
 * - Create/update myFunction with myImage
 *
 * AWS API Gateway - publish, monitor( CloudWatch), secure: AWS Authorization( IAM), AWS Keys(KMS),
 *                   Cache for REST only, trafficManagement - throttling
 *                   payment = callsNumber + traffic;
 *                   for - REST API, - WebSocket API
 *  Req  <->  API Gateway[ Cache, CloudWatch]  <->  API: EC2, ECS, Lambda, anyEndpoints
 *
 * AWS CloudWatch Logs - centralize all logs
 *    LogEvent - Timestamp + eventMessage
 *    LogStream - List<LogEvents> from single logSource: app, resource, ..
 *    LogGroup - List<LogStream>
 *    LogEvents -MetricFilter-> CloudWatch metric
 *    Retention settings - expiration settings for logs
 *
 * AWS CloudFormation ?
 * AWS KMS - Key Management Service
 *
 *
 * **/
 public class ThirdParty {
}
