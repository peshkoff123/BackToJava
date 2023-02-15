package com.peshkoff;
// ________________________________ WEB
/* HTTP Session: Establish TCP connection; 1-N. HTTP_request <-> HTTP_response;
*
*  request, session, application, websocket ???????
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
*
*  DAO,DTO, POJO, JavaBean
* */
// ________________________________ Spring
/* Spring - modular openSource for enterprise. Core - DI(IoC),MVC,AOP
*                                   AspectOrientedProgramming
*  Proxy(Cglib proxy) - wrapper for user POJOs for transaction functionality.
*  @Transactional - marks method works with Connection/Transaction, informs Spring it need to
*                   open and then commit it.
*                                   DI = IoC
*  POJO_Classes + ConfigMetaData( XML, annotation)  =>  DIContainer => InstantiatedApp
*  ApplicationContext interface - representation of DIContainer.
*
*  main( String[] arg) {
*    ctx = new AnnotationConfigApplicationContext( myConfigClass.class);
*        //new ClassPathXmlApplicationContext("properties.xml", "daos.xml");
*    MyBean b = ctx.getBean( MyBean.class or String beanName);
*
*    Resource r = ctx.getResource("classpath:/com/app.properties");// file://, http://
*             r.getFile(); r.getInputStream();
*    Environment env = ctx.getEnvironment();
*    env.getProperty("db.url");
*  }
*
* - @Component - basic, for all classes
*    @Service - for classes of business logic layer (service layer of app)
*    @Repository - for classes of persistence layer
*
*  @Configuration
*  @PropertySources({@PropertySource("classpath:/com/app.properties"),
*                    @PropertySource("file://app.properties")})
*  @Import( OtherConfig.class)
*  @ComponentScan("com.peshkoff.app")   // search for BeanClasses in thisPackage and nested
*  class myConfigClass{
*    @Bean
*    @Bean("beanName")
*    @Bean(init/destroyMethod="methodName")
*    @Scope("singleton")
*    @Scope("prototype")
*            request, session( HTTP_session), globalSession( Portlet),
*            application,
*            webSocket - WebSocket - applicationLayerProtocol HTTP/1.1 done by TomCat or Jetty
*    pub userDao getUserDao() { ret new userDao();}
*  }
*
*  @Component      // to be found by @ComponentScan
*  class userDao {
*   @Autowired
*   @Qualifier("Component/BeanName")
*   private DataSource dataSource1;   // if No @Qualifier Spring search Bean wih name "dataSource1"
*        @Autowired( required=false)
*        pub void setDataSource( @Nullable  DataSource dataSource) {...;}
*
*                //@Autowired could be missed in constructor
*   pub userDao( @Autowired  DataSource dataSource) {...;}
*
*   @Value("${db.pass}")  // Property injection
*   private String pass;
*   public userDao( @Value("${db.pass}") String pass) {...;}
*
*   @EventListener //get events from appContext
*   public void prosessAppContextEvent( someEvent) {...;}
*  }
*
*                                   MVC
*  DispatcherServlet extends HttpServlet {
*   protected doGet( HttpServletRequest req, HttpServletResponse resp) {
*     req.getReqURL();
*     resp.setContentType("text/html"); resp.getWriter().print("<html>..");
*   }
*   }
*   HttpRequest   -> | DispatcherServlet | -> | @Controller            |
*   HTML_resp     <- | ViewResolver      | <- | "myHtmlTemplate"+Model |
*
*   @Controller
*   @ResponseBody // Render @Controller into @RestController, return XML/JSON instead Html
*   class MyController {
*     @Put|Get|Post|DeleteMapping("/update")
*     @RequestMapping("/") // for anyType requests
*     pub String myDoPost( @RequestParam(required=true) Integer id,
*                          @RequestParam String name,
*                          org.spring.ui.Model model  ) {
*         User user = new User( id, name);
*         model.addAttribute( "user", user);
*         return "myHtmlTemplateName";
*     }
*     pub String MyDoPost( UserDto user){...} // UserDto must have getters/setters
*
*     @GetMapping("/users/{userID}")
*     pub String MyDoPost( @PathVariable(required=false) userID){...}
*     pub String MyDoPost( @RequestBody UserDto user){...}
*   }
*
*   HttpRequest:   GET htp://localhost:8080/userlist
*        header    "content-type": "application/json; charset=UTF-8"
*                  "accept" :      "application/json"
*   @RestController
*   class MyRestController {
*     @Autowired  HttpSession session;
*     @Aoutowired HttpServletRequest req;
*
*     @GetMapping( "/userlist")
*     pub List<User> getUserList( @RequestBody UserDto user,
*                                 @RequestParam MultipartFile file,
*                    HttpSession session, HttpServletRequest req,
*                    HttpHeaders headers, @RequestHeader("x-forwarded-for")String forward,
*                    @CookieValue("cook") String val) {
*         throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "blabla")
*
*         return new ArrayList<User>();
*     }
*   HttpResponse:   "content-type":"application/json"  [{"id":1,"name":"hghg"},{}]
*   }
*
*                                   Boot
*  Intention - to simplify configuration and start of SpringApp.
*    It analyzes classpath, beans, properties to make assumption about app configuration.
*  Boot does:
*  - Provides "Starters" - add jars to classpath (add dependencies to pom.xml):
*    spring-boot-starter-web, spring-boot-starter-data-jdbc,...
*  - Auto-registered @PropertySources for hardcoded places :
*    commandLine, classpath and our jars, OS_environment variables,...
*    @PropertySource( value="classpath:myres.properties", ignoreResourceNotFound=true)
*  - Read spring.factories file from spring-boot-autoconfigure.jar - bunch of @Configuration files.
*  - Enhanced @Conditional support - many conditions to guess which 3d party soft to configure.
*
*  Boot create @Beans if some @Conditions are true:
*    @ConditionalOnClass({className.class,...}) if class exists @Bean created or Tomcat bootes up
*    @ConditionalOnProperty( name="spring.datasource.url(type)")
*    @ConditionalOnMissingBean( DataSource.class) if user specify own @Bean - don't override it
*
*  @Conditional( SomeCondition.class implements Condition :: bool matches( ConditionContext c))
*      // @Conditional applicable to @Configuration, @Bean, @Component
*
*  @SpringBootApplication // @Configuration, @EnableAutoConfiguration, @ComponentScan
*  @SpringBootApplication( scanBasePackages={"com.peshkoff"}) // scan from .. and everithing under
*  public class MyApp {
*  public static void main( String... agrs) {
*    // @Bean Definitions here...
*    org.springframework.boot.SpringApplication.run( MyApp.class, args);
*  }
*  }
* */
// ________________________________ Spring Security
/* springSecurityFilterChain (org.springframework.web.filter.DelegatingFilterProxy)
           register as Filter in ServletContainer for every request
*  UserDetailsService
* */
// ________________________________ ApacheMaven
/* Maven - framework, build tool, everything do plugins,
*  Artifact - anyLib(jar) in repository.
*  Goal - special task, could be connected to a phase or not.
*  Scope - compile, runTime, test,...
*  Repository - local (~/.m2/repository/..), global https://repo.maven.apache.org/maven2/, remote
*  ProjectObjectModel - pom.xml
*  <project ...>
*      <groupId>com.peshkoff</groupId>
*      <artifactId>myProject</artifactId>
*      <version>1.0-SNAPSHOT</version>
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
*/
// ________________________________ Hibernate
/*                            SessionFactory->
*  Code <=> PersistObject <=> Session(JDBC_Connection)->Transaction,   <=> JDBC <=> DataBase
*           (JavaBean)               (firstLevelCash)   Query,Criteria
*                             hibernate.cfg.xml(DB_Info, mappings)
*
*  SessionFactory sFactory = new Configuration("employee.hbm.xml").createFactory(); // notEXACT
*  Session s = sFactory.openSession();
*  Transaction t;
*  try{ t = s.beginTransaction();
*       s.get( Class c, Serializable Id);
*       s.save( obj);
*       s.saveOrUpdate( obj);
*       s.persist( obj);
*       s.delete( obj);
*       s.flush();          t.commit();
*  } catch( Exception ex) { t.rollback();
*  } finally {              t.close();   s.close();
*  }
*
*  // PersistenceContext: Bean has states:
*  //   transient - not associated with context,
*  //   persisted - associated and has ID
*  //   detached  - context was closed or bean was evicted
*  //   removed   - associated with context but scheduled to remove from DB
*
*  // FlushMode.ALWAYS - before each Query
*  // FlushMode.AUTO   - sometimes before Query
*  // FlushMode.COMMIT - before session.commit()
*  // FlushMode.MANUAL - only when session.flush()
*
*  // LockMode - lock one RowOfTable - pessimisticLocking
*
*  // CacheMode - type of interaction with 2-levelCache
*
*  @Entity(name="User")  // class/table
*  @Table(name="\"name\"")
*
*  @Embeddable           // class/parentTable, synonym - Composition,
*    @Parent             // field - reference on parent class for @Embeddable class
*
*  @Embedded             // for field in the @Entity class
*  @Target( className.class)
*
*                        // Inheritance
*  @MappedSuperClass     // Has no table
*  class BaseClass { @Id ...}
*  @Entity               // Tables only for SubClasses
*  class SubClass extends BaseClass {..}
*
*  @Entity               // SINGLE_TABLE with @Discriminator
*  @Inheritance( strategy=InheritanceType.SINGLE_TABLE)
*  @DiscriminatorValue( value="NULL")
*  @DiscriminatorColumn( name="discriminator")
*  class BaseClass { @Id ...}
*  @Entity
*  @DiscriminatorValue( value="SubClass")
*  class SubClass extends BaseClass { ...}
*
*  @Entity               // JOINED tables
*  @Inheritance( strategy=InheritanceType.JOINED)
*  class Base { @ID...}
*  @Entity
*  @PrimaryKeyJoinColumn( name="CHILD_ID") // PrimaryKey+ForeignKey constraint
*  class SubClass extends Base { ...}
*
*  @Entity               // TABLE_PER_CLASS
*  @Inheritance( strategy=InheritanceType.TABLE_PER_CLASS)
*  class Base { @Id...}
*  @Entity               // fields of Base duplicated + new fields in separate table
*  class SubClass extends Base { ...}
*
*                        // Associations
*  @ManyToOne
*  @JoinColumn( name="PARENT_ID", foreignKey=@ForeignKey(name="PARENT_ID_FK"))
*  private parentClass parent;
*
*  @OneToMany             // bidirectional association must exists corresponding @ManyToOne
*  @Entity(name="Parent") // Parent class
*  @OneToMany( mappedBy="parent", cascade = cascadeType.ALL, orphanRemoval=true)
*  @JoinColumn( name="PARENT_ID")
*  @OrderColumn( name="...")
*  private List<Child> li = new ArrayList<>();
*
*  @Entity(name="Child")  // Child class PARENT_ID in that table
*  @ManyToOne
*  @JoinColumn( name="PARENT_ID")
*  private Parent parent;
*
*                         // unidirectional - less efficient - additional table with IDs
*  @ManyToOne( cascade = cascadeType.ALL, orphanRemoval = true)
*  private List<Child> li;
*                         // Child class - nothing
*
*  @OneToOne              // Unidirectional - Parent class, nothing in Child
*  @JoinColumn( name="CHILD_ID")
*  private Child child;
*
*  @OneToOne             // Bidirectional - Parent class
*   (mappedBy="parent", cascade=cascadeType.ALL, orphanRemoval=true, fetch=fetchType.LAZY)
*  private Child child;
*  @OneToOne(fetch=fetchType.LAZY)     // - Child class
*  @JoinColumn( name="PARENT_ID")
*  private Parent parent;
*
*  @ManyToMany            // Unidirectional, Bi - Parent class only
*   (cascade={cascadeType.PERSIST,cascadeType.MERGE}) // No cascadeType.ALL !!
*  @JoinTable( name="Parent_Child", joinColumns={ @JoinColumn="PARENT_ID"},
*                             inverseJoinColumn={ @JoinColumn="CHILD_ID"} )
*  private List<Child> li;
*
*  @Id
*  @GeneratedValue
*  @Generated(value=GenerationTime.INSERT(ALWAYS))
*  @NaturalId            //add constraint UNIQUE( fieldName)
*  @Column( name="NotJavaFieldName", length=100)
*  @Basic( nullable=true, fetch=LAZY(EAGER))
*  @Formula( value="beginVal-endVal")
*  @Enumerated( EnumType.ORDINAL(STRING))
*  @Convert( converter=MyConverter.class impl AttributeConverter)
*  @Type()  ?
*  @Lob
*  @CurrentTimeStamp( timing=GenerationTiming.INSERT(ALWAYS)) - autogenerated field
*  @UpdateTimeStamp - autogenerated time
*  @CreationTimeStamp
*
*  boolean        - BOOLEAN, BIT, INT
*  byte           - TINYINT
*  byte[], Byte[] - VARBINARY (JDBC type), BLOB use @Lob
*  char           - CHAR
*  String         - VARCHAR, CLOB use @Lob
*  short          - SMALLINT
*  int            - INTEGER
*  long           - BIGINT
*  BigInteger     - NUMERIC
*  double, float  - DOUBLE, FLOAT, REAL
*  char[], Char[] - VARCHAR, CLOB use @Lob
*  Instant        - TIMESTAMP
*  LocalDate      - DATE
*  LocalDataTime  - TIMESTAMP
*  LocalTime      - TIME
*  OffsetDateTime - TIMESTAMP
*  OffsetTime     - TIME
*  Class          - VARCHAR
*                                 // Fetching
*  Query q = s.createQuery("select t from Table t where t.ID=:id");
*  List<Obj> li = q.setLong( "id", 1).list();
*
*  - @ManyToOne( fetch=FetchType.EAGER)
*    SomeBean b = session.find( SomeBean.class, 123);
*        SELECT * FROM t1 LEFT OUTER JOIN t2 ON t1.id=t2.id WHERE t1.id=:id
*    SomeBean b = session.createQuery( "SELECT t FROM t WHERE t.id=:id", SomeBean.class).
*                         setLong(123).getSingleResult();
*        SQL - my SELECT + more for EAGER entities
*  - Jakarta persistence Entity graph
*    @NamedEntityGraph( name="Ent1.Ent2",
*        attributeNodes=@NamedAttributeNode("Ent2")) // EAGER (as mentioned in graph)
*  - Hibernate Profile
*    @FetchProfile( name="Ent1.Ent2" .. FetchMode.JOIN)
*  - @LazyCollection( LazyCollection.True|FALSE)
*    List<Ent2> l;
*
*                                 //Caches:
*   FirstLevelCache  = SessionCache/persistentContextCache: always ON
*   SecondLevelCache = SessionFactoryCache/JVMLevelCache: byDef OFF; to turn it on:
*                      in config.xml <cache_provider_class=...>; @cache(read_write|read_nly|..) - for entity
*   QueryCache: by def OFF; to turn On:
*                      in config.xml <query_cache=true>; Query q.setCacheable( true);
*
*                                 // PessimisticLocking DB locking system is used always
*  @Version                       // OptimisticLocking
*  @Source( value=SourceType.DB)  // field generated by DB otherwise Hibernate
*  private int|short|long|TimeStamp|Date version;
*
*  @OptimisticLocking( type=OptimisticLockType. ..)
*  OptimisticLockType.NONE
*  OptimisticLockType.VERSION // UPDATE .. SET .. WHERE ID=1 AND version=1
*  OptimisticLockType.ALL     // UPDATE .. SET .. WHERE ID=1 AND ALL_FIELDS
*  OptimisticLockType.DIRTY   // UPDATE .. SET .. WHERE ID=1 AND ONLY_CHANGED_FIELDS
* */
// ________________________________ JMS specification
/* PointToPoint messaging domain: OneToOne, warranted delivery - noTimingDependency sender/receiver
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
* */
// ________________________________ MicroServices
/* MicroService - independently deployed service ( own JVM, project).
*  Monolith - all services in same JVM, same project.
*  Synchronous communication HTTP( REST)
*  Asynchronous - messaging ( JMS, eMail)
* */
// ________________________________ JUnit 5
/* Annotations org.junit.jupiter.api.*
*  Assertions  org.junit.jupiter.Assertions
*  Assumptions org.junit.jupiter.api.Assumptions
*                                   JUnit 4
*  Annotations org.junit.*
*  Assertions  org.junit.Assert.*
*  Assumptions org.junit.Assume.*
*
*  JUnit create new instance of testClass for each test.
*  TestMethods - declared or inherited, should not be abstract, private, return value
*
*                        Main Annotations
*  @Test - testMethod
*  @ParameterizedTest + @ValueSource(strings={"a","b"}) - multiple testMethod execution with input parameter
*      void palindromes(String candidate) { assertTrue( StringUtils.isPalindrome( candidate));}
*  @RepeatedTest(10)
*  @TestMethodOrder + @Test + @Order(1)
*  @DisplayName("name") - for all class and each test
*  @BeforeEach, @AfterEach
*  @BeforeAll, @AfterAll
*  @Nested - nested class is test class
*  @Tag - to mark test class or method
*  @Disabled - for test class or method
*  @Timeout - check the duration
*                       Assertions
*  assertEquals( allType expected, allType act, String message)
*  assertArrayEquals( attType[] exp, allType[] act, String message)
*  assertAll( Executable[] assertArray, String mess) - all assertions be executed and all failures reported
*  assertFalse( bool condition, String mess)
*  assertTrue( bool condition, String mess)
*  assertNull( Object obj, Str mess)
*  assertNotNull( Object obj, Str mess)
*  assertThrows( Class<T> classException, Executable execCode, String mess)
*  assertTimeOut( Duration timeOut, Executable execCode, String mess)
*  fail( String mess)
*
*                       Assumptions
*  assumeTrue( bool assumption, String mess) throws TestAbortedException
*  assumeFalse( bool assumption, String mess)
*  assumingThat( bool assumption, Executable execCode);// if( assumption) execCode;
*
*  Failed assertion throws AssertionFailedError.
*  Failed assumption throws TestAbortedException.
*  Failed assertion - test failed, failed assumption - test aborted.
*  Executable :: void execute() throws Throwable
* */
public class ThirdParty {
}
