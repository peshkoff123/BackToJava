package com.peshkoff;
// ________________________________ Theory
/*
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
*
* */
// ________________________________ CoreJava
/* - switch( type): byte, short, int, and wrappers, String, Enum
 * - Wrappers implement Serializable, Comparable; Number - super for all digitals
 * - Arrays.asList( T[] tArr)
 * - java.lang.reflect. Method, Constructor, Field, Modifier, Parameter
 * - Clone obj:-impl Cloneable + Object.clone()-shallowCloning;- copyConstructor;-Serialization
 * - Marshalling - passing code( Class) + Serialization (fieldValues)
 * - abstract Class without methods - Yes
 * - MultyThreads (AtomicityVisibilityOrdering)
 *   volatile - ensure only propagation of variableValue for all CPU_cores, does not use CPU cache
 *     and use main memory. Guarantees visibility and ordering - prevents compiler of reordering code.
 *     NOT guarantees atomicity. Applicable for all vars and refs.
 *   CompareAndSwap - java.util.concurrent.atomic
 *     uses special native code activate CPU lock, it makes criticalSection shorter.
 * - LinkedHashSet - doubleLinked HashSet to keep elements order
 * - LinkedHashMap - doubleLinked HashMap to keep elements order
 * - Immutable obj: final class, private final fields, getters ret copy of fields( not refs);
 *                  good for HashMap(Table) due to unchanged hashCode, thread-safe
 * - Optional - objWrapper, rid of checks "== null", used in Streams
 * - Boxing/Unboxing - only for exact correspondence ( byte<->byte, int<->Integer)
 * - array[] extends Object
 * - Templates/Patterns
 *   <>-- aggregation/ManyToMany; <*>-- composition/OneToMany; <-- dependency/OneToOne; <<-- inheritance
 *   - Creational:
 *      AbstractFactory: ProductInterface(N) ->> ProductClass(N*M); FactoryInterface(1) ->> FactoryClass(M)
 *      Builder:         BuilderInterface --<> Obj; DirectorClass --<*> BuilderInterface
 *                       Obj; Builder.setProp( Obj); Director.setPackProp( Builder)
 *      FactoryMethod:   AbstractFactory with single method
 *      Prototype:       copy = obj.clone()
 *      Singletone:
 *   - Structural:
 *      Adapter:   AbstractAdapter ext Obj_Result -->> AdapterClass ext Obj_Result,-->Obj_Source
 *      Proxy:     AbstractService -->> Service
 *                                 -->> Proxy --> Service
 *      Bridge:    Replace inheritance with aggregation/composition
 *      Composite: Tree: ComponentInterface -->> CompositeComponent
 *                                          -->> LeafComponent
 *      Decorator: Wrapper: example - clothes: Decor_N --> ... --> Decor_0 --> BaseObj
 *      Facade:    ComplicatedFramework <-> Facade <-> myApp; easy/restricted access to difficult subSystem
 *      Flyweight: ObjState: flyweight/OutsideState( changeable) + heavyweight/InnerState( unchangeable/Cache)
 *   - Behavioral
 *      ChainOfResponsibility/Command: RequestObj -> Handler_1 --> ..--> Handler_N
 *      Command/Action:      command/methodCall => RequestObject( CommandObj, EventObj)
 *      Iterator:            to implement a way of iteration of collection separately from the collection
 *      Mediator/Dispatcher: example - DispatcherServlet
 *      Memento/Snapshot/Version:
 *      Observer/Publisher-Subscriber/EventListener/ChangeListener
 *      State:               MainClass::State <-- AbstractStateDependentClass + implementations
 *      Strategy:            MainClass <-- AbstractStrategy + implementations
 *      TemplateMethod:      MainClass::BaseAlgorithm <-- AbstractClassesUsedInBaseAlgorithm
 * - XML SAX SimpleApiXml - event based: tagStart(),tagEnd(); forward only; not editable
 *       DOM DocObjModel - load all Doc into treeStructure; editable; navigable
 *
 *
 * - ComplitableFuture
 */
// ________________________________ HTTP
/* https://developer.mozilla.org/ru/docs/Glossary/safe
* - Идемпотентный метод - не меняет состояние сервера при множестве запросов но возврат может
*       отличаться - получение данных или статистики
* - HTTP retCodes
*   - 100-199 - info
*   - 200-299 - success:    200 - OK
*   - 300-399 - redirect
*   - 400-499 - client error: 400 badRequest; 403 forbidden; 404 NotFound
*   - 500-599 - server error
* - JWT - JSON WebToken
*    header:  { "typ":"JWT", "alg":"HS256"}
*    payload: { "id","userName","role","inspire",..}
*    signature:"hashCode( header.payload)"
* - WebSocket - applicationLayerProtocol HTTP/1.1 done by TomCat or Jetty
* - CAP - ConsistensyAvailabilityPartitionTolerance распредСистема в любое время может обеспечить не более 2-ч из 3-х
* */
// ________________________________ Test
/* - Testing types
 *   Functional Testing:
 *   - UnitTesting - unit/method that should be tested separately
 *   - Integration/FunctionalTesting - many units/methods + outSide dependencies: DB, WebServices
 *   - SystemTesting - functionalTesting of ALL system together
 *   - AcceptanceTesting - customer satisfaction
 *   - Regression(Smoke)Testing - functional + nonFunctional testing after changing of system
 *   NonFunctional:
 *   - PerformanceTesting,
 *   - SecurityTesting,
 *   - UsabilityTesting,
 *   - CompatibilityTesting
 * - TestStages: - inTeam Functional/nonFunctional( Dev,TestingEnv)->UserAcceptanceTesting(StagingEnv)->
 *               - outsideTeam Alpha(StagingEnv)->Beta(ProductionEnv)
 * - TestEnvironment - hardWare + softWare + data + configuration for tests running
 *    - Development
 *    - Testing
 *    - Staging - small copy of Production; sense - test system in particular environment; smokeTesting, loadTesting
 *    - Production - still keep testing on some selected users
 * - Mokito
 * * - TestContainers - liba for integratingTesting has Doker_Obrazy for TomCat, JMS, Kafka?
 * */
// ________________________________ DB
/* - IndexTypes: ClusterIndex( ordered Table); NonClusterIndex( separate struct with rowReference)
 * - UNION (no duplicate rows), UNION ALL, INTERSECT:
 *   for all SELECTS - same number of columns and similar types
 * - SELECT * FROM t1 INNER JOIN t2 ON t1.id=t2.id
 *   INNER JOIN        - intersection ( only rows are met condition)
 *   LEFT (OUTER) JOIN - all rows from left + rows from right + NULLs for missed rights rows
 *   RIGHT (OUTER) JOIN
 *   FULL (OUTER) JOIN - all rows from both + met what possible
 * - SELECT ID, COUNT(*) .. GROUP BY ID,.. HAVING COUNT(*) < 3   //COUNT, MIN, MAX, AVR for groups
 * - SELECT DISTINCT ..
 * - Нормализация БД нормальные формы  - 6 items of bullSheet
 * - PreparedStatement - SQL_req in DB SQL_cache
 * - Transaction Isolation - mutual impact of concurrentTransactions. There are levels:
 *     - ReadUncommitted (грязное чтение)
 *     - ReadCommitted (неповторяющееся чтение)
 *     - RepeatableRead блокировка строк, (фантомные записи) change/delete - can't see; insert - can
 * - Transaction ACID - Atomicity Consistency Isolation Durability
 * - SQL DataDefLang            CREATE, DROP, ALTER, TRUNCATE
 *       DataManipLang          INSERT, UPDATE, DELETE, CALL, LOCK
 *       DataQueryLang          SELECT
 *       TransactionControlLang TRANSACTION, COMMIT, ROLLBACK
 *       DataControlLang        GRANT, REVOKE
 * - Replication - synchronous/async copy of DB: master(s)->slave(s); master-change/update, slave-read data
 * - Partitioning -
 *    - Vertical - independent tables in different instances
 *    - Horizontal(Sharding) - partsOfBigTable in different instances/servers; all data connected to
 *                             same KeySharding(someEntityID) on the same server;
 *             - division table by logical principe(by date), access to parts in || in same DB_instance,
 *    Partitioning criteria: RoundRobin - recordId/serverNumber; HashCode - HashCode/serverNumber;
 *                           by logical( geographical, profession) principal
 * - SELECT * FROM table ORDER BY ID LIMIT 5 OFFSET 5
 *                                   ROWNUM >5 AND ROWNUM<=10 - Oracle only
 *    Cursor client(Java)-side - all fetch data load to clien;
 *    Cursor server(DB)-side: use STORED PROCEDURE + CallableStatement -> ResultSet
 * -
 * -  Possible troubles with Vertical, Horizontal Partitioning ?
 * -  NormalForms (totally - 6)
 *     1. Atomic fields
 *     2. 1. + ID (natural ID)
 *     3. 2. + ForeignKey
 * */
// ________________________________ NoSQL
/* Distributed (replication), Intended for big data, massively parallel, NoRelational,
*   build for specific data models, have flexible schema, open source
*  NoSQL database types:
*   - key-value store, most scalable, implements HashTable, search by Key ONLY, used as Cache, Session
*   - document store, key-value data in docs: YAML, JSON, BSON(binary), PDF
*   - in-memory, data in DRAM for fast access, on HDD logs of changes or snapshots
*   - column-oriented database, each column is stored separately - flexible and quick scan
*   - graph database for data with big number of relations( socialNetworks, scientificGraphs)
*
*  CacheInvalidationPolicies: LeastRecentlyUsed(LRU), LeastFrequentlyUsed(LFU), FIFO, LIFO
*  CachingStrategies:
*   - ReadThrough: search in Cache if no - read from DB into Cache; "warm" Cache after start App;
*                  often Cache/DB work hidden from BusinessLogic; (Redisson - JavaClient for Redis)
*   - WriteThrough: update/delete->Cache->synchronousUpdate_DB directly - guarantee consistence Cache-DB
*   - WriteBehind:  update/delete->Cache->delay->asynchronousUpdate_DB - for write-heavy workload
*  CacheImplementations: Redis, SpringCache, JCache
*
*  SpringCaching:
*   - @EnableCaching - in ConfigurationClass
*   - @Cacheable("books") - Spring uses proxy to autoCache result of public method return value
*   - @CachePut and @CacheEvict
*
*  Redis
*   - key-value data store, could be DB, Cache, MassageBroker
*   - RESP REdisSerializationProtocol over TCP
*   - in-memory + Disc persistence
*  RedisDataTypes
*   - String: byte[]
*   - List( Queue, Stack): LinkedList<String>; Blocking commands
*   - Set: unordered Set of unique Strings
*   - SortedSet
*   - Hash: HashMap_analogue
*   - Streams: appendOnlyLog
*   - GeoSpatial: work with coordinates
*   - BitMap: bit operations with String
*   - BitField: arbitrary length int ( 1..63 bit) signed/unsigned
*
*  RedisJSON - document store + searchingIndexes, in-memory work with JSON
*  RedisEnterprise - in-memory data store
*  RedisTimeSeries - time-series DB ( with TimeStamp - telemetry, stockPrises)
*  RedisGraph
*  RedisSearch - searchEngine DB
*/
/* ________________________________ MongoDB
*  MongoDB - document DB. Types: JSON, BSON( to store data); "_id" - reserved for DocId
*   Document:   Object/record/row; with references( DB.Collection._id) and EmbededObjects/Docs.
*   Collection: set of Docs( analog of table) but Docs may have different structure
*   DataBase:   set of Collections
*   NameSpase:  DataBase_Name+"."+Collection_Name (BooksDB.FirstBook)
*   Instance:   set of DataBases
*
*   Datatypes:
*     Object ID − This datatype is used to store the document’s ID.
*     String - UTF-8.
*     Integer − 32 bit or 64 bit depending upon your server.
*     Double
*     Boolean
*     Arrays − This type is used to store arrays or list or multiple values into one key.
*     Timestamp − ctimestamp. This can be handy for recording when a document has been modified or added.
*     Date −  current date or time in UNIX time format. Date day, month, year.
*     Object − This datatype is used for embedded documents.
*     Null − to store a Null value.
*     Binary data
*     Code − JavaScript code.
*   Two methods to relate documents:
*    - ManualReferences;
*    - DBRef { "$ref" : "collection", "$id" : ObjectId("..."),, "$db" : "dbName" }
*
*   Find: select  - what documents, - what fields, -sort the results
*   Aggregation: ( analog SQL select)
*       - allFind operations, - rename fields, - calculate fields, - summarize data, - group values
*       Returned documents must not violate the BSON document size limit  16 megabytes.
*   Covered Query - all the search and return fields in the query are part of an index.
*   AnalyzingQuery - db.users.find(..).explaine(); - db.users.find(..).hint( indexName).explain()
*   Collation - character ordering and matching rules  specific for language and locale
*   Pagination - db.COLLECTION_NAME.find().limit( NUMBER).skip( NUMBER)
*   Projection - specifies set of field
*
*   Transaction
*     Read/Write access to single Doc is serializable.
*     Transactions for multyDoc access ( in single collection or different).
*     Write operation is atomic for a single document coll.updateOne() but not for coll.updateMany()
*   Multi-Document Transaction
*    supported multi-document transactions on replica sets and sharded clusters
*   Compound/AtomicOperation - "Avoiding a Race Condition"; write lock on the document
*     Find and update one document   // Document result = collection.findOneAndUpdate(filter, update, options);
*     Find and replace one document  // findOneAndReplace()
*     Find and delete one document   // findOneAndDelete()
*
*   Indexes:            //indexes are present in RAM
*     SingleField Index //String resultCreateIndex = collection.createIndex( Indexes.ascending("title"));
*     CompoundIndex     //String resultCreateIndex = collection.createIndex( Indexes.ascending("type", "rated"));
*     MultikeyIndex     //Indexes on Array Fields
*     TextIndex         //String resultCreateIndex = collection.createIndex( Indexes.text("plot"));
*                       //collection.createIndex( Indexes.compoundIndex( Indexes.text("title"), Indexes.text("genre")));
*                       //Text indexes do not contain sort order.
*                       //Applicable to astring or string array fields.
*     Geospatial Index
*     UniqueIndex       //IndexOptions indexOptions = new IndexOptions().unique(true);
*                       //String resultCreateIndex = collection.createIndex( Indexes.descending("theaterId"), indexOptions);
*     ClusteredIndex    //instruct a collection to store documents ordered by a key value
*                       //MongoDatabase database = mongoClient.getDatabase("tea");
*                       //ClusteredIndexOptions clusteredIndexOptions = new ClusteredIndexOptions(new Document("_id", 1), true);
*                       //CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().clusteredIndexOptions(clusteredIndexOptions);
*                       //database.createCollection("vendors", createCollectionOptions);
*     Drop              //collection.dropIndex(Indexes.ascending("title"));
*                       //collection.dropIndex("title_text");
*
*  Write Concern   - acknowledgement when write to replicaSet/shardedCluster
*  Read Preference - for replicaSet/shardedCluster
* */
// ________________________________ Hibernate
/* - HQL - HibernateQL: SQL but Objects instead of Tables
 *   - Expressions: arithmetic (+, -, *, /), comparison (=, >=, <=, <>, !=, like), logical (and, or, not)
 *   - all types of Joins: inner, left outer join, right outer join and full join
 *   - aggreagate functions: count(*), count(distinct x), min(), max(), avg() and sum()
 *   - ORDER BY, GROUP BY clauses
 *   - subQueries
 *   - DDL, DML and executing store procedures
 *
 *     from Employee where id= :id
 *     select e.name, a.city from Employee e INNER JOIN e.address a
 *     select e.name, sum(e.salary), count(e) from Employee e where e.name like '%i%' group by e.name
 *     update Employee set name= :name where id= :id
 *     delete from Employee where id= :id
 *
 *       	Configuration configuration = new Configuration();
 *        	configuration.configure("hibernate.cfg.xml");
 *        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
 *        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
 * 		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
 *		Session session = sessionFactory.getCurrentSession();
 *		Transaction tx = session.beginTransaction();
 *		Query query = session.createQuery("from Employee");
 *		List<Employee> empList = query.list();
 *
 * - NamedQuery - validated when SessionFactory started
 *   @NamedQuery, @NamedNativeQuery( SQL + store procedures) - is attached to exactly one entity class
 *   @NamedQueries({@NamedQuery(name = "DeptEmployee_FindByEmployeeNumber",
 *                      query = "from DeptEmployee where employeeNumber = :employeeNo"),
 *                    @NamedQuery(name = "DeptEmployee_UpdateEmployeeDepartment",
 *                      query = "Update DeptEmployee set department = :newDepartment where employeeNumber = :employeeNo"),
 *                })
 *   Query<DeptEmployee> query = session.createNamedQuery("DeptEmployee_FindByEmployeeNumber",  DeptEmployee.class);
 *   query.setParameter("employeeNo", "001");
 *   DeptEmployee result = query.getSingleResult();
 *
 * - OptimisticLock - read + verifyVersion(date,hash) before Update; Pessimistic - lock record
 * - N+1 problem + solutions( JOINs, SubSelects, Batch, JoinFetch)?
 * - @EntityGraph - LAZY->EAGER
 *
 * */
// ________________________________ Spring
/* - @Autowired - как указать точный класс для интерфейса - @Qualifier
 * - @ExceptionHandler(RuntimeException.class)  ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
 *        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);                              }
 * - @SpringBootApplication(scanBasePackages = {"com.example.joy"})
 * - Scopes::GlobalSession - Portlets
 * - javax.sql.DataSource interface; source of JDBC_connection; use @Bean + manual settings to configure it
 * - @Component - basic, for all classes
 *    @Service - for classes of business logic layer (service layer of app)
 *    @Repository - for classes of persistence layer
 *
 *  @ResponseStatus( HttpStatus.NOT_FOUND)  // for @Controller, @RestController
 *   - Exception
 *   - mapping method
 *   - HttpServletResponse.sendError( HttpServletResponse.SC_BAD_REQUEST)
 *
 *
 * - Spring Web/Rest/Http_Client : RestTemplate( based on Apache Http Client)  ( WebClient - newer)
 *    TestRestTemplate - wrapper for RestTemplate; differ: 4xx and 5xx do not result in an exception
 *    HttpEntity<T>; RequestEntity<T>, ResponseEntity<T> ext HttpEntity
 *
 * - @Transaction propagation уровни в транзакциях
 * - LifeCycle of Bean; can we delete bean from container
 * - SpringCloud?
 * */
// ________________________________ Spring Security
//https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
/* @EnableWebSecurity
*  @EnableGlobalMethodSecurity( prePostEnabled = true)
*  public class SecurityConfig..
*
*  To secure methods in our classes/Controllers:
*  @PreAuthorize( "hasRole( 'ADMIN')")
*  @PreAuthorize( "hasAnyRole('ADMIN','SUPER_ADMIN')")
*  @PreAuthorize( "hasAuthority('ROLE_ADMIN')")
*  @PreAuthorize( "hasAuthority('DELETE_AUTHORITY')")
*  @PreAuthorize( "hasAnyAuthoirity('DELETE_AUTHORITY','ADD_AUTHORITY')")
*  @PostAuthorize( "hasRole( 'ADMIN') or returnObject.userId == principal.userId")
*
*  Client <-->  Filter_0<->Filter_Spring<->Filter_Mine<->Filter_N <--> Servlet( DispatcherServlet)
*  FilterMine ext javax.servlet.Filter
*   .doFilter( ServletRequest request, ServletResponse response,...)
*
*  SecurityContextHolder.getContext().set/getAuthentication( Authentication);
*
*  interface Authentication ext Principal // represent currently authenticated user
*   principal   userName or instance of UserDetails  // Object getPrincipal();
*   credentials password                             // Object getCredentials();
*   authorities permissions(roles)                   // Collection<? extends GrantedAuthority> getAuthorities();
*
* interface GrantedAuthority extends Serializable {  String getAuthority();}
* */
import java.util.*;

public class Q_And_A {
    abstract class ABC {  }  // abstract Class without methods - Yes

    interface C { default void c() { int i = 6; System.out.println("default C.c()"+this.toString()); }    }

    interface A { default void a() { System.out.println("default A.a()"); }    }
    interface B { default void a() { System.out.println("default B.a()"); }    }
    static class AandB implements A, B {
        public void a() { System.out.println("default B.a()"); } //MUST be overridden !!!
    }

    static class privConstr {
        int a;
        private privConstr( int a) { super(); this.a = a;}
    }
    static class privConstr1 extends privConstr { private privConstr1() {    super( 1);}    }
    public static void privConstrTest() {
        privConstr privConstr_= new privConstr1();
    }

    public static void hashTest(){
      HashMap<Integer,String> map = new HashMap<>();
      map.put(1,"1_Val");
      map.put(2,"2_Val");

      Object o = map.clone();
      System.out.println("map.containsKey(1)=" + map.containsKey(1));
      System.out.println("map.containsKey(1)=" + map.containsValue( "1_Val"));
      String s = map.get( 1);
      System.out.println("map.get( 1)=" + s);
      Set keySet = map.keySet();
      Set<Integer> keySet1 = map.keySet();
      Set<Map.Entry<Integer,String>> entrySet = map.entrySet();
      Collection colValues = map.values();
      Collection<String> colValues1 = map.values();
      for( Map.Entry me : entrySet)
          System.out.println( me.getKey()+" : " + me.getValue());
    }


    }

