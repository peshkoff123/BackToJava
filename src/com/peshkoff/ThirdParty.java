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
 *
 * - CI/CD:      ContinuousIntegration                           ContinuousDelivery
 *   Commit-Build-Unit/IntegrTest-AppImage  -  FuncTest-UserAcceptanceTest-ConfigurationAutomation-LoadTests-Deploy
 *
 * - Client/Server data exchange:
 *   - Request/Response API(REST)
 *   - WebHook  Client    -register_URL->     Server
 *                 URL  <-events(HTTP_POST)-
 *     WebHook: PUSH, EventDriven; no need Poll Server all time
 *     REST/WebPOLLING: POLL, timeDriven;
 *   - WebSockets  Client      -HandShake(HTTP)->      Server
 *                           <-Upgrade_to_WebSocket-
 *                        <-BidirectionalCommunication->
 *     constantly open connection
 *   - HTTPStreaming  Client      -Request->       Server
 *                           <-Multiple_Responses-
 *
 * - Scrum:
 *    Roles: - ProductOwner - interface to Client, plans Sprints, priorytizes Tasks; resp for Product
 *           - ScrumMaster - herds DailyMitings, helps in planning, prioryties; resp for DevProcess
 *           - Team;
 *           - ScrumTeam - ProductOwner + ScrumMaster + Team
 *    Mitings: - SprintPlanning, Grooming(estimation); before Sprint
 *             - DailyScrum_N, 
 *             - SprintReview( digEstim), Retrospective; after Sprint
 *    Artifacts: - ProductBacklog( all tasks); 
 *               - SprintBacklog
 *               - ProductIncrement( BurnDown chart) - chart of progress  
 * - Kanban: TeamLead + Team; mitings are optional; SprintBacklog - flexible( Tasks assign/reassing,..)
 * */
//_________________________________ BeanUtils
/** Apache Commons BeansUtils contains all tools for working with Java beans
 * <dependency>
 *     <groupId>commons-beanutils</groupId>
 *     <artifactId>commons-beanutils</artifactId>
 *     <version>1.9.4</version>
 * </dependency>
 * public class Course {
 *     private String name;
 *     private List<String> codes;
 *     private Map<String, Student> enrolledStudent = new HashMap<>();
 * }
 * Course course = new Course();
 * String name = "Computer Science";
 * List<String> codes = Arrays.asList("CS", "CS01");
 * PropertyUtils.setSimpleProperty(course, "name", name);
 * PropertyUtils.setSimpleProperty(course, "codes", codes);
 * PropertyUtils.setIndexedProperty(course, "codes[1]", "CS02");
 * PropertyUtils.setMappedProperty(course, "enrolledStudent(ST-1)", student);
 * String nameValue = (String) PropertyUtils.getNestedProperty( course, "enrolledStudent(ST-1).name");
 *
 * CourseEntity courseEntity = new CourseEntity();
 * BeanUtils.copyProperties(courseEntity, course);// copy the properties with the same name only
 */
// ________________________________ ElasticStack( ELK)/Logging
/** ElasticStack( ELK):
 *   ElasticSearch
 *   Kibana - UI graphs and interaction with data for ElasticSearch,
 *   Logstash - input/transform/stash data to -> ElasticSearch;
 *              input from <fileName> -> aggr/processing/filtering -> <ElasticSearchURL>
 *   Beats - data delivery? Beats -> Logstash -> ElasticSearch
 *   X-pack - monitor, notify, protect
 *
 *  Logs/files -> input->filter->output    ->   ElasticSearch
 *                  LogStash Pipeline    Index
 *  ElasticSearch -
 *    - JSON_based( NoSQL) DB to store inputs/logs + search engine
 *    - REST API <-> myApp
 *    - Data: logs, metrics, traceFata
 *    - Notions: - Index: analog of DB
 *               - Type: table
 *               - Document: record/row in DB_Table
 *               - Field - in Document
 *   - ElasticSearch Cluster secures scalability, reliability
 *   - for analyses bigData
 *   - MySQL much slowly then ElasticSearch
 *
 *  Full text search - glossary: { word_0 - { docRef_0,..,docRef_m},
 *                                 ..,
 *                                 word_n - { docRef_0,..,docRef_m} }
 * **/
// ________________________________ Monitoring
/** Spring Boot Actuator - to expose operational information about the running application — health, metrics,
 *    info, dump, env, etc.:
 *       /actuator list of provided endpoints:
 *       /beans returns all available beans in our BeanFactory
 *       /env returns the current environment properties
 *       /heapdump builds and returns a heap dump from the JVM
 *       /info returns general information
 *       /logfile returns ordinary application logs
 *       /threaddump dumps the thread information of the underlying JVM
 *       ...
 *       /prometheus returns metrics like the previous one, but formatted to work with a Prometheus server
 *    Uses HTTP endpoints or JMX beans to interact with it.
 *    Micrometer is now part of Actuator's dependencies.
 *    To enable it:  <dependency>
 *                      <groupId>org.springframework.boot</groupId>
 *                      <artifactId>spring-boot-starter-actuator</artifactId>
 *                   </dependency>  //versions are specified in the Spring Boot Bill of Materials (BOM).
 *    By def available endpoints: /health and /info;
 *    Enable all endpoints: management.endpoints.web.exposure.include=*. (in application.properties)
 *                          management.endpoints.web.base-path=/actuator // by def, can be tweaked
 *    Actuator security - same as any other endpoints:
 *    @Bean public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
 *              return http.authorizeExchange()
 *                         .pathMatchers("/actuator/**").permitAll()
 *                         .anyExchange().authenticated()
 *                         .and().build();                                                 }
 *
 * Micrometer - to monitor JVM( mem, GC, Threads?), caches, ExecutorService, and logging services and
 *              export data to monitoring system: AWS_Cloudwatch, Prometheus, Grafana, ...
 *
 * Prometheus( localhost:8080/actuator/prometheus) - openSource monitoring system:
 *  - JVM_state: mem, CPU usage, GC, loadedClasses, Threads
 *  - prometheus.yml - config
 *  - HTTP_periodical_PULL -> TimeSeriesDB -> Web_UI to visualize, query, monitor all metrics
 *
 *  Grafana - visualize data with graphs, send conditional alerts
 *  Elasticsearch, Prometheus, Graphite, InfluxDB, PostgreSQL, MySQL..
 *          ->  Grafana: graphs, alert rules to notify by eMail, Slack,..
 * **/
// ________________________________ Reactor
/**
 * - Core features of Reactive approach
 *   - asynchronous/unblocking
 *   - dataStream - eventDrivenStream
 *   - backPressure in dataStream
 *   - functional code style
 * - Browser( subscriber)                          <->  Server( publisher)
 *   can stop data loading( Subscription.cancel())
 * - There are two ways to declare endPoints:
 *   REST=annotation: Browser <-> Controller <-> Service
 *   Functional:      Browser <-> Router     <-> Handler // alternative way to describe endPoins
 *      RouterFunctions.route().GET( "/api", handler::handleMethod)
 *                            .PUT( "/api/{input}", handler::put).build();
 * - Reactive Streams Specification:
 *   interface Publisher<T> {
 *       void subscribe( Subscriber<T> s)
 *   }
 *   interface Subscriber<T> {
 *       void onSubscribe( Subscription s);
 *       void onNext( T t);
 *       void onError( Throwable t);
 *       void onComplete();
 *   }
 *   interface Subscription<T> {
 *       void request( long l);  // BackPressure
 *       void cancel();
 *   }
 *   interface Processor<T,R> ext Publisher<T>, Subscriber<R> {}
 *
 * - Project Reactor: implem-n of Reactive Streams Specification + Reactive Lib
 *   ReactiveTypes/asynchronous sequence: Mono<T>, Flux<T> implement Publisher; work AFTER call
 *     Disposable subscribe()/subscribe(..) only.
 *   - Disposable.dispose()   // to cancel the subscription
 *   - BaseSubscriber         // alternative to lambdas
 *     Flux.range(1, 10)
 *     .doOnRequest( r -> System.out.println("request of " + r))
 *     .subscribe( new BaseSubscriber<Integer>() {
 *       @Override
 *       public void hookOnSubscribe(Subscription subscription) {
 * 		  System.out.println("Subscribed");
 * 		  request(1);         // Long.MAX_VALUE - unbounded request (meaning “produce as fast as you can” —  disabling backpressure)
 *       }
 *       @Override
 *       public void hookOnNext(Integer integer) {
 *         System.out.println( integer);
 *         cancel();
 *       }
 *     });
 *   - Mono [0, 1];
 *       Mono<String> m = Mono.just("Only").log
 *       Mono<Boolean> m = Flux.any( s->s.equals(1))
 *       Mono<Integer> m = Flux.elementAt( 1).subscribe()
 *   - Flux[0, n]; abstract class Flux<T> ext CorePublisher<T>
 *       Flux<String> f = Flux.fromIterable( List.of( "1","2","3"))
 *                            .map(String::toUpperCase())             // map values + ret Flux
 *                            .flatMap( s-> Flux.just( s.split("")))  // flatMap + ret Flux
 *                            .filter( s-> s.length() > 0)            // filter + ret Flux
 *                            .delayElements( Duration.ofMillis(1000))
 *                            .log()                                  // log + ret Flux
 *    Flux<String> f = Flux.fromArray( arr)
 *       Flux<Integer> f = mono.flux()
 *    Flux.range( 1, 5).subscribe( s->System.out::println)
 *       Flux.<String>generate( sink ->sink.next("hello"))
 *           .delayElements( Duration.ofMillis( 500))                 // make to use another Thread
 *           .take( 4)                                                // 4 iterations
 *           .subscribe( System.out::println);
 *    Flux.range(1, 4)
 *        .map(i -> { if (i <= 3) return i;
 *                    throw new RuntimeException("Got to 4");  })
 *        .subscribe( i -> System.out.println(i),                     // onNext
 *                error -> System.err.println("Error: " + error),     // onError handler
 *                   () -> System.out.println("Done"));               // omComplete or onError - never both can end the stream!
 *       Thread.sleep( 4000);
 *    Flux<String> f1 = Flux.Just( "1", "2").repeat();
 *    Flux<String> sum = Flux.Just( "A", "B", "C", "D", "E").zipWith( f1, (f,s) -> f+s)
 *    sum.delayElements( Duration.ofMillis( 1500))
 *       .timeout( Duration.ofSeconds( 1))
 *       .retry( 3)                                                   // 3 retry in case of mistake
 *       .onErrorReturn( "TooSlow")
 *       .onErrorResume( throwable -> Flux.interval( Duration.ofMillis(300)))
 *                                        .map( String::valueOf)
 *       .skip( 2)                                                     // skip first 2 items
 *       .take( 3)                                                     // stop after next 3 items
 *       .subscribe( System.out::println)
 *
 *  - Synchronous generate:
 *    public static <T,S> Flux<T> generate(Callable<S> stateSupplier,  BiFunction<S,SynchronousSink<T>,S> generator)
 *    public interface SynchronousSink<T> {
 *        void	complete()
 *        void	error(Throwable e)
 *        void	next(T t)
 *    }
 *    Flux.generate( () -> 0,                          //  Callable<S> stateSupplier
 *        (state, sink) -> {                // BiFunction<S,SynchronousSink<T>,S> generator)
 *                           sink.next("3 x " + state + " = " + 3*state);
 *                           if (state == 10) sink.complete();
 *                           return state + 1;                            });
 *  - Asynchronous and Multi-threaded: create - for multiple emissions per round, even from multiple threads.
 *    public static <T> Flux<T> create(Consumer<? super FluxSink<T>> emitter)
 *    public interface FluxSink<T>{ methods next, error, complete, + ...}
 *    Flux.create( sink -> { sink.onRequest( r-> sink.next( "generate/load next value")); })
 *  - Asynchronous but single-threaded: only one producing thread may invoke next, complete or error at a time.
 *    public static <T> Flux<T> push(Consumer<? super FluxSink<T>> emitter)
 *
 *  - Threading and Schedulers
 *    By def all works in same Thread until that directly changed:
 *    - manually
 *      final Mono<String> mono = Mono.just("hello ");
 *      Thread t = new Thread( () -> mono
 *         .map(msg -> msg + "thread ")
 *         .subscribe( v->System.out.println(v + Thread.currentThread().getName()))  )
 *      t.start();
 *      t.join();
 *    - Scheduler similar to an ExecutorService
 *      Schedulers.immediate() - current Thread
 *      Schedulers.single() - single, reusable thread
 *      Schedulers.boundedElastic() - worker pools and reuses idle ones
 *        Mono blockingWrapper = Mono.fromCallable( () -> { //make a remote synchronous call;..  return ..; })
 *        blockingWrapper = blockingWrapper.subscribeOn( Schedulers.boundedElastic());
 *      Schedulers.fromExecutorService(ExecutorService)
 *      Schedulers.parallel() - fixed pool of workers as you have CPU cores
 *        Flux.interval( Duration.ofMillis(300))                               // by def - Schedulers.parallel()
 *        Flux.interval( Duration.ofMillis(300), Schedulers.newSingle("test")) // in new Thread
 *      publishOn and subscribeOn: Both take a Scheduler and let you switch the execution context to that scheduler
 *
 *  - Testing of Flux and Mono:
 *    StepVerifier.create( f).expectNext( "1","2","3")
 *                           .expectNextCount( 3)
 *                           .verifyComplete()
 * **/
// ________________________________ HTTP
/** https://developer.mozilla.org/ru/docs/Glossary/safe
 * - Идемпотентный метод - не меняет состояние сервера при множестве запросов но возврат может
 *       отличаться - получение данных или статистики
 *   POST/create - NO_idempotent: N identicalRequest = N new records
 *   PUT/update  - idempotent: N identicalRequest = same result; change whole resource
 *   PATCH/update - can be idempotent or NO_idempotent; change resource partially
 *   GET, HEAD, PUT, DELETE, OPTIONS, HEAD, TRACE - idempotent
 *   OPTIONS - get parameters connection to resource including list of available methods; no input params
 *             "HTTP/1.1 200 OK   Allow: OPTIONS, GET, HEAD, POST   Cache-Control: max-age=604800" - answer example
 *   HEAD - ret headers same as GET request ( Content-Length, Content-Type,..); no input body, no output body;
 *   TRACE - for debug; ret 200 (ok); no inp/output body
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
 * - HTTPS = HTTP + TLS( SSL)
 * - TLS - Transport Layer Security - former SecureSocketLayer;
 *                                    provides: Encryption + Authentication + Integrity( packageHashCode) of data
 *    TLS/SSL_Certificate = ServerPublicKey; IssuedTo: Company/Person; IssuedBy: AuthorityCompany; ValidityPeriod;
 *    SessionKey - symmetricKey much faster than asymmetricKeys; encrypt/decrypt session data exchange
 *    PublicKey+PrivateKey - asymmetricKeys for initial Handshaking = client checks serverCertificate + generate SessionKey
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
// ________________________________ ApacheMaven
/** Maven - framework, build tool, everything do plugins,
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
 *      <packaging>pom</packaging>      <!--for modules-->
 *      <!--packaging>jar</packaging-->
 *      <name>name</name>
 *    <modules>
 *        <module>kafka-producer</module>
 *        <module>kafka-consumer</module>
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
 *
 *  BOM - Bill Off Materials - special kind of POM to control the versions in central place.
 *  Precedence of artifact versions:
 *   1. direct declaration in POM
 *   2. inheritance from parent BOM
 *   3. import from BOM; first find version is used
 *   4. dependency mediation:  A -> B -> C -> D 1.4 : A -> E -> D 1.0 => D 1.0
 *
 * BOM description:
 * <project ...>
 *     <modelVersion>4.0.0</modelVersion>
 *     <groupId>baeldung</groupId>
 *     <artifactId>Baeldung-BOM</artifactId>
 *     <version>0.0.1-SNAPSHOT</version>
 *     <packaging>pom</packaging>
 *     <name>BaelDung-BOM</name>
 *     <description>parent pom - BOM</description>
 *     <dependencyManagement>
 *         <dependencies>
 *             <dependency><groupId>test</groupId>
 *                         <artifactId>b</artifactId>
 *                         <version>1.0</version>
 *                         <scope>compile</scope>
 *      ...
 *
 *  BOM usage in POM:
 *  <project ...>
 *     <modelVersion>4.0.0</modelVersion>
 *     <groupId>baeldung</groupId>
 *     <artifactId>Test</artifactId>
 *     <version>0.0.1-SNAPSHOT</version>
 *     <packaging>pom</packaging>
 *     <name>Test</name>
 *     <parent>                        <! Inheritance - only from one parent >
 *         <groupId>baeldung</groupId>
 *         <artifactId>Baeldung-BOM</artifactId>
 *         <version>0.0.1-SNAPSHOT</version>
 *     </parent>
 *
 *     <dependencyManagement>
 *         <dependencies>
 *             <dependency>            <! Importing - many BOM available>
 *                 <groupId>baeldung</groupId>
 *                 <artifactId>Baeldung-BOM</artifactId>
 *                 <version>0.0.1-SNAPSHOT</version>
 *                 <type>pom</type>
 *                 <scope>import</scope>
 *     ...
 *
 *  Import the spring-framework-bom to ensure all Spring dependencies are at the same version:
 *  <dependencyManagement>
 *     <dependencies>
 *         <dependency>
 *             <groupId>org.springframework</groupId>
 *             <artifactId>spring-framework-bom</artifactId>
 *             <version>4.3.8.RELEASE</version>
 *             <type>pom</type>
 *             <scope>import</scope>
 *
 * 
 * - Surefire Plugin runs the unit tests
 *   "**\Test*", "**\*Test", "**\*Tests", "**\*TestCase"
 *   mvn test
 * - Failsafe Plugin runs the Integration Tests
 *   "**\IT*", "**\*IT", "**\*ITCase"
 *   mvn integration-test  // just run IT; build - successful regardless IT fails or not
 *   mvn verify            // right way; if IT fails - stop(fail) build
 *
 *   - mvn install -DskipTests        // to skip all tests( unit + integration) but still we want to compile them
 *   - mvn install -DskipITs          // to skip IntegrationTests only
 *     mvn install -DskipITs=true
 *   - mvn -Dmaven.test.skip package  // to skip the compilation phase; overrides property from POM
 *     <properties>                   // same in POM
 *         <tests.skip>true</tests.skip>
 *     </properties>
 *   - mvn -Dskip.ut=true verify     // way to skip UT only +
 *     <build><plugins><plugin>
 *       <groupId>org.apache.maven.plugins</groupId>
 *       <artifactId>maven-surefire-plugin</artifactId>
 *       <configuration>     <skipTests>${skip.ut}</skipTests>
 *       </configuration>
 *     </plugin></plugins></build>
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
 *  - branch  - create, del, list,..branches
 *      git branch <newBranchName>
 *      git branch -d <deleteBranchName>
 *  - checkout - re-point HEAD
 *      git checkout -b <newBranchName> = git branch <newBranchName> + git checkout <newBranchName>
 *  - reset <commit.REF> - switch to one of previous COMMITs = re-point commit.REF!
 *                         to replace last mistakable commit; should NOT be used after PUSH COMMIT !
 *      --soft   discard-LastCommit;
 *      --mixed  discard-LastCommit+AddIndex; def behaviour ( git reset=git reset --mixed);
 *      --hard   discard-LastCommit+AddIndex+LocalFileChanges;
 *      git reset --soft commit.REF
 *  - revert - reverse changes of last COMMIT by NEW_COMMIT
 *      git revert HEAD
 *  - merge - merges branch into HEAD, result - new commit, or just move HEAD (Fast-forward merge) if possible
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
 *  - stash CTRL_X - place uncommitted changes on shelve
 *     git stash                       // CTRL_X
 *     git stash save "stash comment"
 *     git stash list                  // see all stack of stashes
 *     git stash apply <number( 0,1)>  // CTRL_V; def number = last
 *     git stash pop                   // CTRL_V + remove that stash
 *  - Itellij/Shelve - direct analogue os git stash
 *
 *  - TRUNK( based Dev): feature toggle(тумблер)
 *     - MASTER/TRUNK/MAIN -> localCopy
 *     - localCopy -> TRUNK
 *  - FeatureBranching: short Delivery(Features)
 *     - MAIN/MAINLINE
 *     - Feature(s) - one branch per feature: MAIN->F1->PULL_Req->F1->MAIN
 *  - ForkingStrategy: for openSource Proj; only Proj owners can write into MAIN, anyone can Fork from MAIN
 *     - MAIN -> fork myMAIN -> do any changes -> PULL_Req -> MAIN
 *  - ReleaseBranching: waterFlow; noCI/CD; one branch for release( long);
 *                      low frequency of deploys; support previous releases
 *     - MAIN - for Production; Release->MASTER
 *     - HotFixes: MAIN,Release->HotFixes->MAIN,Release
 *     - Release(s): MAIN->Release->MASTER
 *     - Feature(s)
 *  - GitFlow
 *     - MASTER - for Production; Release->MASTER
 *     - HotFixes: MASTER->HotFixes->MASTER, DEVELOP
 *     - Release(s): DEVELOP->Release->MASTER,DEVELOP
 *     - DEVELOP - contain stable features; Release,MASTER->DEVELOP->Release->MASTER
 *     - Feature(s) - for newDevelopment;  DEVELOP->featureBranch->DEVELOP->Release->MASTER
 *  - GitHubFlow=FeatureBranching: for quick releases and CD
 *     - MAIN -> Feature
 *     - Feature -> MAIN
 *  - EnvironmentBranching: separate branch for Testing, Staging, Prod environments? Useless!
 *
 *  - CI/CD: GitHub Actions (analog for Jenkins):
 *    CI: Build - Test - CodeCoverage - DockerImage - PushToDockerHub - CD: Deploy 
 *    - create folder ".github/workflows" in GitHub repository
 *    - create file github-actions-demo.yml(maven.yml) script autorun on PUSH/MERGE_Request into Git or PULL_Request
 *    - PUSH/MERGE, PULL_Request - events/triggers to run CI/CD pipeline/scripts
 *  - PULL_Request - ask other people(s) to get my Repo:branch to his Repo:branch
 *    Logic: 1. create myCode in newBranch locally
 *           2. push newBranch to Repository
 *           3. create PULL_Request (in Bitbucket, GitHub,..)
 *           4. team check/review code and make changes
 *           5. teamAdmin MERGEs newBranch into Release/Master and close PULL_Request
**/
// ________________________________ GitLab
/** GitLab - opensource: can host src on premise and in cloud version: GitLab.com;
 *     has all GitLab functions + localDockerRegistry, deploy, releaseManagement, monitoring,..
 *  GitLabRunner - standalone? App for any OS/containers: executes our scripts/pipelines
 *     sharedRunners, projectRunners, groupRunners;
 *     Runner must be assigned(connected) to project in GitLab
 *     Executor - where CI_script be runned: shell(myPC), docker, ..
 *  .gitlab-ci.yml - in myProject root dir
 *    build-job:
 *        stage: build
 *       script:
 *         - echo "Hi $GITLAB_USER_LOGIN"
 *    test-job1:
 *        stage: test
 *       script:
 *         - echo "Do some tests"
 *         - sleep 20
 *    test-job2:
 *        ..
 *    deploy-prod:
 *          stage: deploy
 *         script:
*            - echo "Deploy from $CI_COMMIT_BRANCH"
 *    environment: production
 *
 *  CI/CD: purpose: automation, tests/checks/codeInspection before pull/merge request to MASTER
 *               <-   CI  <-      <-   CD    <-
 *  Plan -> Code -> Build -> Test -> Release -> Deploy -> Operate ->
 * **/
// ________________________________ Docker
// C:\Program Files\Docker Toolbox\mongo-docker
// docker run -d -p 27017:27017 --name mymongo mongo:3.6
// docker run -d -p 27017:27017 -v "$(pwd)"/mongo-docker:/data/db --name mymongo mongo:3.6
/* If we are talking about Docker on Windows then we have to take in account the fact that all containers are run on VirtualBox.
   Before mounting volume to a container we have to be sure that particular folder is available for VirtualBox.
   Firstly, to define the name of the current running Docker machine, run
        $ docker-machine.exe  active
        default
   Secondly, add shared folder to VirtualBox:
        $ VBoxManage sharedfolder add default --name "some_project" --hostpath D:\Projects\some_project
   Thirdly, create the folder
        $ docker-machine.exe ssh default 'sudo mkdir --parents /d/projects/some_project'
   Fourthly, mount it:
        $ docker-machine.exe ssh default 'sudo mount -t vboxsf some_project /d/projects/some_project'
   Finally, create a container:
        $ docker run -v //d/projects/some_project://d/projects/some_project -d some-image_name
 */
// curl 192.168.99.100:27017
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
 *  Create Docker Image:
 *  - docker build -t dock-test-jar .    // Dockerfile in this folder
 *    docker build -t dock-test-jar:1.0 .
 *
 *  - mvn spring-boot:build-image        // NO Dockerfile needed - looks better
 *    //https://www.youtube.com/watch?v=1w1Jv9qssqg - Spring Boot 2.3, a new feature was added that enables you
 *    to create Docker Images from your application using Cloud Native Buildpacks
 *    There are different maven plugins for createing Docker images:
 *       //https://www.youtube.com/watch?v=Kx4KQcCNuz8
 *     - <groupId>org.springframework.boot</groupId> <!-- ? Can specify params as in Dockerfile in pom.xml ?-->
 * 		 <artifactId>spring-boot-maven-plugin</artifactId>
 *     - <groupId>io.fabric8<groupId/>               <!-- Can specify params as in Dockerfile in pom.xml -->
 *       <artifactId>docker-maven-plugin</artifactId>
 *     - <groupId>com.google.cloud.tools<groupId/>   <!-- Can specify params as in Dockerfile in pom.xml -->
 *       <artifactId>jib-maven-plugin</artifactId>
 *         <!-- No need Docker on your PC; Can push images into registry.hub.docker.com/myRepo -->
 *     Utility to explore DockerImages files: "dive <imageName>"
 *
 *  Pull to DockerHub
 *  - DockerHub account : log=peshkoffyahoo; pass="=5ecssQxafmmG+A"
 *  - logIn from local Docker:
 *    docker login    //Your password will be stored unencrypted in C:\Users\peshkoff\.docker\config.json
 *  - docker tag imageName peshkoffyahoo/imageName
 *    docker tag docker-rest-test:0.0.1-SNAPSHOT peshkoffyahoo/docker-rest-test:1.0 // rename - works
 *  - docker push peshkoffyahoo/docker-rest-test:1.0 //repository [docker.io/peshkoffyahoo/docker-rest-test]
 *  - docker pull peshkoffyahoo/docker-rest-test:1.0 // Image is up to date ...
 *
 *  docker images
 *  docker image ls
 *  docker rmi -f <image>
 *  docker pull <image>:<version>
 *
 *  docker run --name mycont1 imageName -p 3306:3306 -d
 *  docker run -d -p 8080:8080 --name mycont1 docker-rest-test:0.0.1-SNAPSHOT
 *                                       //curl 192.168.99.100:8080/
 *  docker ps                            // runned container list
 *  docker ps -a                         // runned and stopped container list
 *  docker logs contName
 *  docker inspect contName              // get the internal container ip
 *  docker exec -it contName bash        // exec command in runned container
 *  docker stop contName
 *  docker restart contName
 *  docker rm -f <container>
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
 *                 yt_postgres:
 *                       image: postgres:15
 *              container_name: yt_postgres
 *                    env_file:
 *                     - .env   // envVariables in separate file ".env" - real file name
 *               yt_project:
 *                    image: yt_project:latest
 *           container_name: yt_project_container
 *               depends_on:  // !
 *                  - yt_postgres
 *                 env_file:
 *                  - $env   // envVariables in separate file: $env; env - configurable variable
 *                  command: >
 *                   bash -c "sleep 10 && python manage.py test"
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
// ________________________________ Kafka
/**
 *  Kafka - distributed, scalable, reliable log( FIFO)
 *    Applicable for cases when latest data most valuable and old data - non-relevant any more.
 *  NOT allow delete/change record, search by record number ONLY
 *  KafkaCluster - KafkaNodes + ZookeeperNodes // Obsolete ?
 *
 *  Kafka entities:
 *  Cluster - set of Brokers for scaling and replication
 *  Broker - accept, save, deliver messages; Broker file system:
 *           ./logs/<TopicName>-<Partition_i>/*.log, *.index, *.timeindex
 *           *.log:  Offset   Position    Timestamp   Message
 *                     0         0          ...       Bla
 *                     1         67         ..        Bla2
 *           *.index: Offset   Position
 *                     0         0
 *                     1         67
 *           *.timeindex: Timestamp  Offset
 *                          ..        0
 *                          ..        1
 *    Segments: Active - last; files name - start offset( of first record in file); segment timestamp - max message timestamp
 *              Segment_0/Active/last:{ *.log, *.index, *.timeindex},
 *              Segment_1:{ *<offs>.log, *<offs>.index, *<offs>.timeindex}, ..,
 *      Delete by Segments: if( Segment.Timestamp > TTL) delete( Segment)
 *      Retention by size or date:
 *      Deleted by retention       _______Alive_________
 *      Segment_1 <- Segment_2 <- Segment_3 <- Segment_4
 *  Controller - special Broker( Master) - chooses LeaderReplicas
 *  Zookeeper - keep up state/work of Cluster; it's a DB for Cluster metadata: Brokers, Partiotions,.. info;
 *              chooses Controller;
 *  Topic - logical group of events in natural order (FIFO) for Partition!; [0..n] Partiotions
 *    Compacted Topics: Kafka deletes/compacts all records for specific Key except last message.
 *  Partition - for paralleling; configuring parameter; [0..M] Messages;
 *              Logic: evenly spread all Partitions of all Topics between all Brokers/Servers!
 *  Message - { Topic, Partition}: spec by Producer;
 *            Key( optional), Value( byte[]), Timestamp, Headers{[Key:Value]}
 *            Partition = hash( Key) % NumberOfPartitions
 *            // Message( Key) -> same Partition + even distribution of load between Partitions/Brokers
 *            Number of Partitions - level of parallelism
 *  Batch - package of Message before write - for speed
 *
 *  Replication: for reliability EXCEPTIONALLY; LeaderPartition -> FollowerPartition
 *    KafkaController chooses LeaderReplica ( can be configured manually)
 *                producer -> LeaderReplica -> Consumer  // read/write <-> LeaderReplica ONLY!:
 *   Follower_i  <-asyncPull- LeaderReplica  // pulls Leader for update - Not new Leader( may miss some data)
 *     Follower_i <-syncPush- LeaderReplica  // In-SyncReplica( ISR Follower) - new Leader; slower record
 *                                           // min.insync.replicas=3 = replication-factor - 1
 *    replication-factor=3:
 *      Broker 1               Broker 2                Broker 3
 *    Partition 1 (master)   Partition 2 (master)    Partition 3 (master)
 *    Partition 3 (copy)     Partition 1(copy)       Partition 2 (copy)
 *
 *  Send/Write Message:
 *    Producer.send( message, acks,) {
 *     - fetch metadata( Cluster state, Topic placement - BrokerId) from Zookeeper;
 *       expensive and block operation with timeout=60 sec
 *     - serialize message: key.serializer, value.serializer (StringSerializer)
 *     - define Partition: -explicit(manual); -round-robin; - key_hash % n( all mess with same key - in same Partition)
 *     - compress/zip message
 *     - accumulate batch;  batch.size=16kB(def),linger.ms(timeout)
 *   }
 *   acks = 0  (fireAndForget) - no reception acknowledge - loss of Message acceptable; small reliability
 *   acks = 1  acknowledge after write in Leader Partition only; asynchronous replication later; mess lost if Leader falls
 *   acks = -1 acknowledge after write in Leader+In-SyncReplica( ISR Follower) Partitions; long; most reliable;
 *
 *  Read/Poll Messages: polling by package of mess
 *   Consumer( ConsumerGroup).poll() {
 *     - fetch metadata( Cluster state, Topic placement, Offset) from Zookeeper;
 *       expensive and block operation with timeout=60 sec
 *     - connect to (all) LeaderPartition(s) on (all)Brokers; may be slow in single Thread;
 *   }
 *
 *   ConsumerGroup - to read messages in parallel( singleApp)
 *   OnePartition - only oneConsumer
 *   Automatical Rebalance when add/remove Consumer to Group
 *      Topic        ConsumerGroup    or  ConsumerGroup  or  ConsumerGroup
 *    Partition 1      Consumer            Consumer 1          Consumer 1
 *    Partition 2    (read all mess)       Consumer 2          Consumer 2
 *    Partition 3                                              Consumer 3 // Max 1 consumer per Partition!
 *
 *  OffsetCommit - way of reliable reading; number/offset of consumed and processed Messages;
 *    ConsumerGroup  -commit offset_of_last_read_message-> Kafka Topic __consumer_offsets
 *    OffsetCommit - record in special Kafka Topic: __consumer_offsets
 *    __consumer_offsets:[{ Topic/Partition, Group, Offset}]
 *    Delivery semantics = Commit types:
 *      - at most once  - autoCommit
 *      - at least once - manualCommit
 *      - exactly once  - customCommit - idempotence
 *    Commit types:
 *     - autoCommit at most 1 miss mess; commit before data processing; if we CAN loose mess
 *     - manualCommit at least 1 duplicate mess; if we CAN process 1 mess several times( idempotence needed)
 *     - customCommit management; control OffsetCommit manually; exactly once not missed, not duplicate;
 *
 *  KafkaProducer:
 *  Properties props = new Properties();
 *  props.put("bootstrap.servers", "localhost:9092");
 *  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 *  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 *  Producer<K,V> producer = new KafkaProducer<>(props);
 *  // send - async, aggregate msg in buffer - then send in batch
 *  producer.send( new ProducerRecord<K,V>(String topic, Integer partition, K key, V value);
 *     producer.beginTransaction();  // support transactional send
 *     producer.send(..);
 *     producer.commitTransaction(); // or  producer.abortTransaction();
 *  producer.close();
 *
 *  Spring KafkaProducer:
 *  kafkaTemplate = new KafkaTemplate<>( producerFactory( (HashMap)props));
 *  CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send( topicName, key, msg);
 *  future.whenComplete((result, ex) -> {
 *      if (ex == null)
 *         LOGGER.info("Kafka.sendMessage:=[" + msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
 *      else LOGGER.error("Kafka.sendMessageError:=[" + msg + "] : " + ex.getMessage());
 *  });
 *
 *  KafkaConsumer:
 *      Properties props = new Properties();
 *      props.setProperty("bootstrap.servers", "localhost:9092");
 *      props.setProperty("group.id", "test");
 *      props.setProperty("enable.auto.commit", "true");
 *      props.setProperty("auto.commit.interval.ms", "1000");
 *      props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
 *      props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
 *      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
 *      consumer.subscribe(Arrays.asList("foo", "bar"));
 *      while (true) {
 *          ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
 *          for (ConsumerRecord<String, String> record : records)
 *              System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
 *      }
 *
 *  Spring KafkaConsumer:
 *  @KafkaListener( topicPartitions = @TopicPartition(topic = topicName,
 *                     partitionOffsets = { @PartitionOffset(partition = "0", initialOffset = "0")}))
 *  public void quoteListener( @Payload Quote quote,
 *                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
 *      LOGGER.info( String.format( "Kafka.quoteListener( %s); partition=%d", quote, partition));
 *  }
 *
 *  KafkaStreams application structure:
 *   CollectionTier -> Kafka -> AnalysisTier -> Kafka   -> DB/InMemoryDB -> DataAccessTier
 *                             (KafkaStreams)
 *   Kafka -> KafkaStreams -> Kafka( DB/InMemoryDB/File also possible)
 *
 *  Spring-KafkaStream application:
 *  @Service class myService {
 *    @Bean KafkaStreamsConfiguration
 *    @Bean Topology
 *    ..
 *  }
 *
 *  KStreams Java  application:
 *  Properties conf = new Propertes();
 *  conf.put( StreamsConfig.APPLICATION_ID_CONFIG,"my-kafka");   // unique name in Kafka Cluster
 *  conf.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"broker1:9092,broker2:9092");
 *  conf.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
 *  conf.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
 *  StreamsConfig config = ..;                 // Setup options
 *
 *  StreamsBuilder kBuilder = new StreamsBuilder();
 *  KStream<String, PlayEvent> kStream = kBuilder.stream( Serdes.String(), playEventSerde, "play-events");
 *  KTable kTable = kBuilder.table( .., Topic); // KTable - changeLog with only latest value for given key
 *
 *  KStream<Long, PlayEvent> playBySongID = kStream
 *     .filter( (region,event) -> event.getDuration()>someValue)
 *     .map((key,val) -> KeyValue.pair( val.getID(), val)); // repartition with new key and value
 *  //  KStream = map,filter,join => KStream
 *  //  KStream = reduce,aggregate => KTable
 *  //  KTable  = mapValues,filter,join => KTable
 *  //  KTable  = toStream => KStream
 *  //  KStream + KTable =merge,join=> KStream
 *
 *  // leftJoin by rule: if( playBySongID.key == songTable.key) use song as a value
 *  KStream<Long, Song> songPlays = playBySongID.leftJoin( songTable, (playEvent,song)->song,
 *                                                         Serdes.Long(), playEventSerde/songSerde(?));
 *  // Count - stateful operation - save data (KTable) to Kafka
 *  KGroupedTable<Long,Long> gTableByID = songPlays.groupBy((singID,song) -> songID, Serdes.Long(),Serdes.Long());
 *  gTableByID.count("song-play-count");
 *  //Reduce or aggregate
 *  KTable<Song,Long> songPlayCounts = gTableByID.aggregate( TopFiveSongs::new,
 *    (aggKey,val, aggr)->{ aggr.add(val);return aggr;},
 *    (aggKey,val, aggr)->{ aggr.remove(val);return aggr;},  // remove data from KGroupedTable - changelogs!
 *    topFiveserde,
 *    "top-five-songs"                                      );
 *  // Windowing by time
 *  gTableByID.count( TimeWindows.of( TimeUnits.MINUTES.toMillis(5)), "song-play-count-windowed");
 *
 *  Topology topology = new StreamsBuilder();  // Topology - sequence of data handlers
 *   ...build();
 *                                             // Than are done by Spring-Kafka
 *  KafkaStreams kStreams = new KafkaStreams( config, topology); //KStream - endless sequence of events
 *  kStreams.start();
 *  ...
 *  kStreams.close();
 *
 *  KafkaStreams:
 *  - per-record stream processing
 *  - windowing operations
 *  - stateless( inpMessage -map-> outputMessage) processing;
 *    KafkaStreams Split:
 *     KStream<> sour = ..
 *     KStream<> out1 = sour.mapValues(..).to(..);                  // Split sor to several outStreams
 *     KStream<> out2 = sour.filter(..).mapValues(..).forEach(..);  // in distract to Java Streams API
 *        gain().split().branch( (key,val)->key.contains("A"), Branched.withConsumer( ks->ks.to("A"))).
 *                      .branch( (key,val)->key.contains("B"), Branched.withConsumer( ks->ks.to("B"))).
 *   KafkaStreams Merge:
 *     KStream<String,Integer> s1 = ..; KStream<String,Integer> s2 = ..; // <String,Integer>!
 *     KStream<String,Integer> merge = s1.merge( s2);                    // No order of message
 *  - statefull/localState processing: based on RocksDB under the hood;
 *     Facebooks's RocksDB - embedded key/value storage with persistent; LogStructuredMergeTree;
 *                           similar to TreeMap<K,V>, Keys are sorted, Itertor<Key>, remove range of Keys
 *    SourceStream -> LocalStore( RocksDB) + ChangeLog( Kafka create additional Stream)?
 *    Partitioning + localState?
 *
 *
 *
 *
 *
 *  DeadLetterQueue - Topic to store bad(wrong format) messages - to investigate them latter
 *
 *  kcat                                  // CLI for Kafka
 *  kcat -L -b broker:29092 |grep topic   // Metadata for all topics
 *
 *  Kafka testing:
 *  TopologyTestDriver( Topology t, KafkaStreamsConfiguration);  // Special class for testing Topologies and Configuration
 *  TopologyTestDriver => inputTopic, outputTopic                // Not need KafkaCluster
 *
 *
 * Apache Kafka® is an event streaming platform; EventStreaming - capturing data in real-time
 * Key capabilities ( implem in distributed, highly scalable, elastic, fault-tolerant, and secure manner.):
 * - To publish (write) and subscribe to (read) streams of events
 * - To store streams of events durably and reliably for as long as you want.
 * - To process streams of events as they occur or retrospectively.
 *
 * Kafka is a distributed system of Servers/KafkaCluster and clients, communicate by TCP
 * Kafka cluster is highly scalable and fault-tolerant: if any of its servers fails, the other servers will take over
 *   their work to ensure continuous operations without any data loss.
 *
 * Servers/KafkaCluster: StorageLayer_Servers/Brokers + KafkaConnect_Servers( import/export data with my DB or other Kafka clusters)
 * Clients: Kafka Streams library, REST API: allow you to write distributed applications and microservices that read,
 *   write, and process streams of events in parallel, at scale, and in a fault-tolerant manner even in the case of
 *   network problems or machine failures.
 *
 * Event=message=record - fact that "something happens"
 *   Event: key, value, timestamp,  metadata headers( optional).
 *     example: key: "Alice"; value: "Made a payment of $200 to Bob"; timestamp: "Jun. 25, 2020 at 2:06 p.m."
 *   Same event key  are written to the same partition.
 *   Kafka guarantees order in partition.
 *
 * Producers( client applications) publish/write -events-> Kafka -events-> Consumers subscribe to (read and process)
 *
 * Topics - like a folder. Events in a topic can be read as often as needed—unlike traditional messaging systems,
 *   events are not deleted after consumption. Instead, you define for how long Kafka should retain your events
 *   through a per-topic configuration setting, after which old events will be discarded.
 * Topics are Partitioned - topic is spread over a number of different Kafka brokers (for scalability).
 *   For fault-tolerant and highly-available, Topic can be replicated, even across geo-regions or datacenters.
 *     A common production setting is a replication factor of 3.
 *
 * Kafka API: (+ command line tool)
 *  - Admin API to manage and inspect topics, brokers, and other Kafka objects.
 *  - Producer API to publish (write) a stream of events to one or more Kafka topics.
 *  - Consumer API to subscribe to (read) one or more topics and to process the stream of events produced to them.
 *  - Kafka Streams API to implement stream processing applications and microservices. It provides higher-level functions
 *    to process event streams: transformations, stateful operations like aggregations and joins, windowing, processing
 *    based on event-time, and more. Input is read from one or more topics in order to generate output to one or more
 *    topics, effectively transforming the input streams to output streams.
 *  - Kafka Connect API to build and run reusable data import/export connectors that consume (read) or produce (write)
 *    streams of events from and to external systems and applications so they can integrate with Kafka.
 *    For example, a connector to a relational database like PostgreSQL. already exist hundreds of ready-to-use connectors
 *
 * **/

 public class ThirdParty {
}
