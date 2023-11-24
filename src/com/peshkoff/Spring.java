package com.peshkoff;
public class Spring {}
// ________________________________ Spring
/** Spring - modular openSource for enterprise. Core - DI(IoC),MVC,AOP
 *
 *                             AspectOrientedProgramming
 *   //https://habr.com/ru/post/428548/
 *   Point - separate auxiliary code/Cross-cutting concerns (logging, timing, transaction, security) in own unit.
 *   Cross-cutting concerns - aspects of program impacting different modules not being integral part of them.
 *   Aspect — class for Pointcut and Advice.
 *   Pointcut — what to proxy (Join point)
 *   Advice - proxy-code (Pointcut)
 *    Before — перед вызовом метода
 *    After — после вызова метода
 *    After returning — после возврата значения из функции
 *    After throwing — в случае exception
 *    After finally — в случае выполнения блока finally
 *    Around — можно сделать пред., пост., обработку перед вызовом метода, а также вообще обойти вызов метода.
 *
 *   AOP proxy: an object-wrapper to implement the aspect contracts
 *   (advise method executions and so on). AOP proxy will be a JDK dynamic proxy ( if exist any interface) or
 *   a CGLIB proxy.
 *   To force the use of CGLIB proxies set the value of the proxy-target-class attribute of the <aop:config> element to true
 *   CTW (compile-time weaving) и LTW (load-time weaving).
 *
 *   @Service   public class MyService {
 *      public void method1(List<String> list) {
 *         list.add("method1");
 *         System.out.println("MyService method1 list.size=" + list.size());
 *     }
 *     @AspectAnnotation public void method2() {
 *         System.out.println("MyService method2");
 *     }
 *  }
 *
 *  @Aspect @Component   public class MyAspect {
 *     private Logger logger = LoggerFactory.getLogger(this.getClass());
 *
 *     @Pointcut("execution(public * com.example.demoAspects.MyService.*(..))")
 *     public void callAtMyServicePublic() { }
 *
 *     @Before("callAtMyServicePublic()")
 *     public void beforeCallAtMethod1(JoinPoint jp) {
 *         String args = Arrays.stream(jp.getArgs()).map(a -> a.toString()).collect( Collectors.joining(","));
 *         logger.info("before " + jp.toString() + ", args=[" + args + "]");
 *     }
 *
 *     @After("callAtMyServicePublic()")
 *     public void afterCallAt(JoinPoint jp) {
 *         logger.info("after " + jp.toString());
 *     }
 *  }
 *
 *  //https://docs.spring.io/spring-framework/docs/4.1.0.RC2/spring-framework-reference/html/transaction.html
 *  Declarative transaction - @Transactional
 *  ProgrammaticTransaction - TransactionTemplate - intended Spring class.
 *                            TransactionTemplate::execute( ()-> Transactioncode )
 *  @Transactional - marks method works with Connection/Transaction, informs Spring it need to open and then commit it.
 *                   applied for a class - all methods @Transactional
 *  - propagation.
 *      MANDATORY - transaction must be already started, otherwise - Exception
 *      NESTED - выполняется внутри вложенной транзакции, если есть активная, если нет активной - то аналогично REQUIRED
 *      NEVER - выполняется вне транзакции, она приостанавливаетсяе
 *      NOT_SUPPORTED - выполняется вне транзакции - если есть активная, если есть активная - выбрасывается исключени
 *      REQUIRED - (по умолчанию) - если есть активная, то выполняется в ней, если нет, то создается новая транзакция
 *      REQUIRES_NEW- всегда создается новая транзакция, если есть активная - то она приостанавливается
 *      SUPPORTS - если есть активная - то выполняется в ней, если нет - то выполняется не транзакционно
 *  Правила управления откатом
 *  - noRollbackFor и noRollbackForClassName - определяет исключения, при которых транзакция НЕ будет откатана
 *  - rollbackFor и rollbackForClassName - определяет исключения, при которых транзакция БУДЕТ откатана
 *
 *  @Transactional void doTrans1() { ..; doTrans2(); ..}         // Only 1 transaction
 *  @Transactional( propagation=Propagation.REQURES_NEW) void doTrans2() { ..}
 *  // Nested transactions: - TransactionTemplate; - inject oneself if scope=singletone; - get ref on my proxy any other way
 *
 *  TransactionManager markInterface
 *  ReactiveTransactionManager impl TransactionManager
 *  SystemTransactionManager impl TransactionManager {
 *    TransactionStatus getTransaction()
 *    void commit(TransactionStatus status)
 *    void rollback(TransactionStatus status)
 *  }
 *
 *                                   DI = IoC
 *
 *  DI - specialPattern sort of universalFactory: organize app as set of Beans and explicit dependencies.
 *       + improve appStructure - easy to build and test; less cohesion
 *       + create/tune of Beans - less work; less boilerplate code
 *
 *  POJO_Classes + ConfigMetaData( XML, annotation)  =>  DIContainer => InstantiatedApp
 *  ApplicationContext interface - representation of DIContainer; wrapper for BeanFactory.
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
 *    ...
 *    ctx.close();  // destroys all the beans, releases the locks, and then closes the bean factory
 *  }
 *
 * Stereotypes( specific role)
 * - @Component - basic, for all Stereotypes classes
 * - @Configuration - JavaConfiguration + @Bean_methods
 * - @Contoller - @RequestMapping's allowed here ONLY
 * - @RestController - @Contoller + @ResponseBody
 * - @Service - for classes of business logic layer (service layer of app)
 * - @Repository - for classes of persistence layer; catch platform-specific Exceptions and re-thrown them as
 *                 Spring's unchecked data access Exception
 *
 *  @Configuration
 *  @Configuration( basePackages = {"soundsystem", "video"})  ?
 *  @Configuration( basePackageClasses = "MyClass.class")     ?
 *  @PropertySources({@PropertySource("classpath:/com/app.properties"),
 *                    @PropertySource("file://app.properties")})
 *  @Import( OtherConfig.class)
 *  @ComponentScan( "com.peshkoff.app")   // search for BeanClasses in thisPackage and nested
 *  class myConfigClass{
 *    @Bean
 *    @Bean("beanName")
 *    @Bean(init/destroyMethod="methodName")
 *    @Scope("singleton")
 *    pub userDao getUserDao() { ret new userDao();}
 *
 *  }
 *
 *
 *  @Component                          // to be found by @ComponentScan
 *  @Component( "myUserDao")
 *  @Profile( "dev")                    // "!dev" - for any profile except "dev"
 *  class userDao {
 *   @Autowired
 *   @Qualifier( "exactClassName/BeanName")
 *   private DataSource dataSource1;   // if No @Qualifier Spring search Bean wih name "dataSource1"
 *
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
 *   @EventListener        //get events from appContext
 *   public void prosesContextEvent( someEvent) {...;}
 *     @EventListener( classes={ContextRefreshedEvent.class,ContextStartedEvent.class})
 *     public void prosesContextEvent() { Logger..;}
 *  }
 *
 *
 *  @Configuration   // ways to set activeProfile
 *                      - Context parameter in web.xml // "spring.profiles.active"="dev"
 *                      - WebApplicationInitializer    // setInitParameter( "spring.profiles.active", "dev")
 *                      - JVM System parameter         // -Dspring.profiles.active=dev
 *                      - Environment variable         // export spring_profiles_active=dev (Unix)
 *                      - Maven profile
 *  class MyWebApplicationInitializer implements WebApplicationInitializer {
 *     public void onStartup( ServletContext servletContext) {  // 1-st way
 *        servletContext.setInitParameter( "spring.profiles.active", "dev");
 *     }
 *
 *     @Autowired ConfigurableEnvironment env;                 // 2-nd way
 *     @Autowired Environment environment;                     // to get activeProfiles
 *     @Value( "${spring.profiles.active:}") //  ".. :defValue"// to get activeProfiles
 *           String activeProfiles;
 *
 *     void main( Str[] pars) {
 *       env.setActiveProfiles( "someProfile");
 *       String[] s = environment.getActiveProfiles()
 *     }
 *  }
 *  // SpringBoot Profiles :
 *     - spring.profiles.active=dev // bydef
 *     - SpringApplication.setAdditionalProfiles("dev"); // to set Profile
 *  // Profile specific properties files:
 *     - oneProfile - oneFile: application-production.properties; application-dev.properties; ..
 *     - manyProfiles in same file:
 *        application.properties {
 *            my.prop=used-always-in-all-profiles
 *            #---
 *            spring.config.activate.on-profile=dev
 *            spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 *            #---
 *            spring.config.activate.on-profile=production
 *            spring.datasource.driver-class-name=org.h2.Driver
 *        }
 *
 *                                   MVC
 *
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
 *
 *     @ExceptionHandler(RuntimeException.class)
 *     ResponseEntity<Exception> handleAllExceptions( RuntimeException ex) {
 *          return new ResponseEntity<Exception>( ex, HttpStatus.INTERNAL_SERVER_ERROR);
 *     }
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
 *
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
 *
 *  @SpringBootApplication // @Configuration, @EnableAutoConfiguration, @ComponentScan
 *  @SpringBootApplication( scanBasePackages={"com.peshkoff"}) // scan from .. and everithing under
 *  public class MyApp {
 *    // @Bean Definitions here...
 *
 *    public static void main( String... agrs) {
 *        SpringApplication.run( MyApp.class, args);
 *        ...
 *        SpringApplication.exit( ctx, () -> 0);     // retCode = 0
 *    }
 *  }
 * */
// ________________________________ Spring Q_And_A
/**
 * - @Value("${valName}") - from properties, systemEnv, commandLine
 *   // spEL expressions:
 *   @Value("#{T(class).staticVar}") String s;
 *   @Value("#{T(class).staticMethod()}")  String s;
 *   @Value("#{@bean1.beanVar}")  String s;
 *   @Value("#{@bean1.beanMethod()}") Integer s;
 *   @Value("#{environment['app.name']}"); @Value("#{systemProperties['my.name']}"); @Value("#{systemEnvironment['my.name']}");
 * - @Autowired,  @Autowired( required = false) // can't find apropos Bean - UnsatisfiedDependencyExc
 *   @Qualifier( "exactClassName/BeanName")
 *       @Autowired List<String> list_1;        // Collections are injectable!
 *       @Bean List<String> list_1() { ...}
 *
 * - @Primary:  - @Bean @Primary; - @Component @Primary
 *   No @Primary - NoUniqueBeanDefinitionException - several sameClass @Beans declared
 *
 * - spring-boot-devtools - addLib of Spring for development only - fast restart of application during coding;
 *     reload app when classes in classPath are updated;
 *     uses BaseClassLoader (for unchangeable jar's) + RestartClassLoader (for our classes);
 *     restart application = reload RestartClassLoader+ourClasses
 * - ApplicationContext  types
 *    - FileSystemXmlApplicationContext
 *    - ClassPathXmlApplicationContext
 *    - AnnotationConfigApplicationContext
 *    - XmlWebApplicationContext
 *    - AnnotationConfigWebApplicationContext
 * - Как получить ApplicationContext?
 *    - ApplicationContext cont = SprigApplication.run( MyClass.class, args)
 *    - @Autowired ApplicationContext cont;
 *    - @Component class MyBean implements ApplicationContextAware {
 *         public void setApplicationContext( ApplicationContext applicationContext) {
 *           this.applicationContext=applicationContext;                            }
 *      }
 * - Как получить ApplicationContext в интеграционном тесте?
 *   @ExtendWith(TestClass.class) — используется для указания тестового класса
 *   @ContextConfoguration(classes = JavaConfig.class) — загружает java/xml конфигурацию для создания контекста в тесте
 *   Можно использовать аннотацию @SpringJUnitConfig, которая сочетает обе эти аннотации.
 *   Для теста веб-слоя можно использовать аннотацию @SpringJUnitWebConfig.
 *
 * - @Bean( initMethod, destroyMethod, name( by def methodName), value — алиас для name)
 *   @Bean( autowireCandidate=false)
 * - LifeCycle of Bean
 *   - SpringConfig loaded, beansClasses loaded + beansDefinitions created, создание графа зависимостей
 *     - BeanFactoryPostProcessors: override bean definitions configured in the application context
 *   - Instantiate Beans, inject @Value, @Autowired, Aware interfaces callbacks?
 *    - BeanPostProcessor.postProcessBeforeInitialization( Object newBean, String beanName) :: Object
 *    - NameBeanAware.setBeanName( newName)
 *      BeanFactoryAware.setBeanFactory( beanFactory)
 *      ApplicationContextAware.setApplicationContext( applicationContext)
 *      @PostConstruct methodDescription
 *      @Bean( initMethod="..")
 *    - BeanPostProcessor.postProcessAfterInitialization( Object newBean, String beanName) :: Object
 *   - Теперь бин готов к использованию. ApplicationContext.getBean()
 *   - После того как контекст будет закрыт( ApplicationContext.close()), бин уничтожается.
 *      @PreDestroy methodDescription  перед уничтожением вызовется этот метод.
 *      DisposibleBean.destroy(), чтобы очистить ресурсы или убить процессы в приложении.
 *      @Bean( destroyMethod="..") то вызовется и он.
 *
 * - Как создаются бины: сразу или лениво? Как изменить это поведение?
 *    - Singleton-создаются сразу при сканировании.
 *    - Prototype-бины обычно создаются только после запроса.
 *    - @Lazy = @Lazy(true) Create Proxy::bean=null; bean created by request/invoke to avoid circular dependency
 * - Singleton.Prototype - lookUp: multiple injections( appContext.getBean())
 * - Use Interfaces for Beans:
 *    - testing: use mock/stub
 *    - механизм динамических прокси из JDK(например, при создании репозитория через Spring Data)
 *    - Позволяет скрывать реализацию
 * - @Scope
 *    singleton
 *    prototype
 *    request
 *    session( HTTP_session)
 *    application
 *    globalSession( Portlet),
 *    webSocket - WebSocket: bidirectional, fullDuplex, persistent connection ( chat)
 *    Внедрять можно только singleton или prototype.
 * - javax.sql.DataSource interface; source of JDBC_connection/ConnectionPool; use @Bean + manual settings to configure it;
 *
 *
 * - Exception handling:
 *   - throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Foo Not Found", exc);
 *   - @ExceptionHandler(RuntimeException.class)     // for THIS_ONLY @Controller, @RestController
 *     @ExceptionHandler( { RuntimeException.class, InputOutputException.class})
 *     public void handleException() { ...   }
 *     ResponseEntity<Exception> handleAllExceptions( RuntimeException ex) {
 *        return new ResponseEntity<Exception>( ex, HttpStatus.INTERNAL_SERVER_ERROR);
 *     }
 *   - @ControllerAdvice                             // Global REST application exception handling
 *     public class MyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 *      @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
 *      protected ResponseEntity<Object> handleConflict( RuntimeException ex, WebRequest request) {
 *         String bodyOfResponse = "This should be application specific";
 *         return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
 *     }
 *      // to handle method-level security AccessDeniedException - result of annotations:
 *      // @PreAuthorize, @PostAuthorize, and @Secure.
 *     @ExceptionHandler({ AccessDeniedException.class }) //
 *     protected ResponseEntity<Object> handleConflict(...) {..}
 *     }
 *
 * - @ResponseStatus( HttpStatus.NOT_FOUND)  // for @Controller, @RestController
 *   - Exception
 *   - mapping method
 *   - HttpServletResponse.sendError( HttpServletResponse.SC_BAD_REQUEST)
 *
 * - Spring Web/Rest/Http_Client : RestTemplate( based on Apache Http Client)  ( WebClient - newer)
 *    TestRestTemplate - wrapper for RestTemplate; differ: 4xx and 5xx do not result in an exception
 *    HttpEntity<T>; RequestEntity<T>, ResponseEntity<T> ext HttpEntity
 * - FeignClient - web client like RestTemplate; sence to use - can work with SpringCloudLoadBalancer
 *                 or Ribbon( Netflix) to have clienSide load balancing
 *
 * - ProxyObjects - wrapper for anyObject to add separated logic: transactions, security, logging,..
 *   JDK-proxy — dynamic proxy. based on separate interface. API встроены в JDK.
 *   CGLib proxy — third party. based on inheritance
 *
 * - can we delete bean from container
 *   ((BeanDefinitionRegistry) beanFactory).removeBeanDefinition( "myBean");
 *   ((DefaultListableBeanFactory) beanFactory).destroySingleton( "myBean");
 *   ((SingletonBeanRegistry) beanFactory).registerSingleton( "myBean", myBeanInstance);
 *
 * - ConfigurableApplicationContext ext ApplicationContext
 *    .doClose()                     // publishes ContextClosedEvent, destoy singletons
 *    .close()                       // call .doClose(): destroy beans, close context syncronously
 *    .registerShutdownHook()        // call .doClose() when JVM shutdown
 *    .stop()                        // propagate stop signal to components - not close()!
 * - StandaloneAppContext need to be closed manually: call .close() or register shutdown hook with JVM
 *
 * - Versioning REST_Api
 *   - @GetMapping( value="/api", params="version=1")   // by reqParam
 *   - @GetMapping( value="/api", headers="version=1")  // by headers
 *   - @GetMapping( value="/api/V2")                    // by URL
 *
 * - REST security: session vs token - token( server - must be stateLess)
 *
 * - Content negotiation:
 *   @GetMapping( "/api", consumes={ MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
 *                         produce={ MediaType.APPLICATIO_JSON_VALUE} )
 * - How to create custom validators in spring: create appropriate annotation,
 *     class-validator impl ConstraineValidator
 *
 * - Spring main Exceptions:
 *    NoSuchBeanDefinitionException
 *    NonUniqueBeanDefinitionException  // @Bean @Primary <beanDef> - to fix it
 *    BeanCreationException             // @Bean @Lazy <beanDef> - to avoid/resolve circular dependency
 *    BeanInstantiationException        // if bean is abstract class or Exception in class constructor
 *    ApplicationContextException       // there is no @SpringBootApplication
 *
 * - @NoRepositoryBean                  // for basic interfaces/abstractClasses
 *
 * - Spring Data JPA def implem-n - Hibernate
 * - Interface Repository<T,ID>         // marker Repository interface;
 *    Interface CrudRepository<T,ID> ext Repository<T,ID>                 // @NoRepositoryBean
 *                                  .count()
 *                                  .delete()/.deleteAll( Iterable)
 *                                  .find()/.Iterable findAll()
 *                                  .save()/saveAll( Iterable)
 *      Interface ListCrudRepository<T,ID> ext CrudRepository<T,ID>       // @NoRepositoryBean
 *                                  .List<T> findAll()
 *                                  .saveAll( List<T>)
 *        Interface PagingAndSortingRepository<T,ID> ext Repository<T,ID> // @NoRepositoryBean
 *                                  .Page<T> findAll( Pageable pageable)
 *                                  .Iterable<T> findAll( Sort sort)
 *          Interface ListPagingAndSortingRepository<T,ID> ext PagingAndSortingRepository<T,ID>
 *                                  .List<T> findAll( Sort sort)
 *            Interface JpaRepository<T,ID> extends ListCrudRepository<T,ID>, ListPagingAndSortingRepository<T,ID>,...
 *                                  .deleteAllInBatch()
 *                                  .findAll( Example ex)
 *                                  .flush()
 *                                  .saveAndFlush()
 *   Spring Data MongoDB - MongoRepository
 *
 * - @Bean - + @Conditional..( @ConditionalOnMissingBean/Class);
 *           + manual/programmatic control in ..getBean() {..} method
 *   @Component - unconditional
 *
 * - SpringBoot load application.yaml first then application.properties; application.properties OVERRIDE yaml
 *
 * - @Autowired BeanFactory beanFactory;  ...; beanFactory.getBean( bean.class/"beanName");
 *
 * - Spring Testing:
 *   //https://rieckpil.de/spring-boot-unit-and-integration-testing-overview/
 *   Spring-Boot-Starter-Test includes all for UnitTests and IntegratedTests?:
 *                   JUnit 4/5, Mockito, asertionsLibs: AssertJ, Hamcrest, JsonPath,..
 * - UnitTests
 *   Spring Boot offers a lot of annotations to test various parts of your application in isolation:
 *    @JsonTest, @WebMvcTest, @DataMongoTest, @JdbcTest,..
 *   The two most important annotations are:
 *     @WebMvcTest to effectively test your web-layer with MockMvc
 *     @DataJpaTest to effectively test your persistence layer
 *   There are also annotations available for more niche-parts of your application:
 *     @JsonTest to verify JSON serialization and deserialization
 *     @RestClientTest to test the RestTemplate
 *     @DataMongoTest to test MongoDB-related code
 *
 *     @WebMvcTest( PublicController.class)
 *     class PublicControllerTest {
 *     @Autowired  private MockMvc mockMvc;
 *     @Autowired  private MeterRegistry meterRegistry;
 *     @MockBean   private UserService userService;
 *     @TestConfiguration
 *     static class TestConfig {
 *     @Bean public MeterRegistry meterRegistry() {
 *       return new SimpleMeterRegistry();       }
 *                            }
 *     }
 * - IntegratedTests: use the @SpringBootTest annotation for this purpose and access your application from
 *                    outside using either the WebTestClient or the TestRestTemplate.
 *   @SpringBootTest - populate the entire application context for your test and start TomCat if webEnvironment=..
 *   @SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT(DEFINED_PORT)) // webEnvironment - to start TomCat !
 *   class ApplicationTests {
 *     @LocalServerPort private Integer port;
 *     @Autowired private TestRestTemplate testRestTemplate;
 *     @Test void accessApplication() { System.out.println(port);  }
 *   }
 *
 *   @RunWith( SpringJUnit4ClassRunner.class)     // Class to run testCases
 *   @ContextConfiguration( class=MyConfig.class) // MyConfig - @Configuration Class with Beans creation
 *                                                // to avoid @ComponentScan
 *   @ContextConfiguration( classes={ Config1.class, Config2.class}, ... )
 *   @ContextConfiguration( classes=OrderServiceConfig.class, loader=AnnotationConfigContextLoader.class)
 *   @ContextConfiguration( "/app-config.xml")    // ApplicationContext loaded from "classpath:/app-config.xml"
 *
 *   @ActiveProfiles("dev")                       //
 *      @Configuration
 *      @Profile("dev")
 *      public class StandaloneDataConfig {
 *        @Bean
 *        public DataSource dataSource() {
 * 	    	return new EmbeddedDatabaseBuilder()
 * 		    	.setType(EmbeddedDatabaseType.HSQL)
 * 			    .addScript("classpath:com/bank/config/sql/schema.sql")
 * 			    .addScript("classpath:com/bank/config/sql/test-data.sql")
 * 			    .build();                } }
 *
 *  - @Testcontainers will manage the lifecycle of any Docker container for your test:
 *    @Testcontainers
 *    @SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT)
 *    public class ApplicationIT {
 *      @Container
 *      public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
 *                                                                  .withPassword("inmemory")
 *                                                                   .withUsername("inmemory");
 *      @DynamicPropertySource
 *      static void postgresqlProperties(DynamicPropertyRegistry registry) {
 *             registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
 *             registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
 *             registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
 *      }
 *      @Test  public void contextLoads() {..  }
 *   }
 * - End-to-End Tests: integration + UI( usually).As soon as you need to interact with a browser,
 *   Selenium (+ Selenide) is usually the default choice
 *
 * - @Async: applied to public(!) method make it execute in separate thread. Calling the async method from within
 *          the same class — won't work. Not use in @Transactional method!
 *     @Configuration
 *     @EnableAsync
 *     public class SpringAsyncConfig {            // def executor for @Async methods - SimpleAsyncTaskExecutor
 *       @Bean( name = "threadPoolTaskExecutor")   // way to define other executor
 *       public Executor threadPoolTaskExecutor() {  return new ThreadPoolTaskExecutor();  }
 *     }
 *   @Async                                        // @Async void method
 *   @Async( "threadPoolTaskExecutor")
 *   public void asyncMethodWithVoidReturnType() {
 *     System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
 *   }
 *   @Async                                        // @Async Future<> method
 *   public Future<String> asyncMethodWithReturnType() {
 *     System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
 *     try{ Thread.sleep(5000);
 *           return new AsyncResult<String>("hello world !!!!");
 *        } catch( InterruptedException e) { ..   }
 *     return null;
 *   }
 *
 * - @Scheduled(fixedDelay = 1000)       // delay after n millisecond of previous task over
 *   @Scheduled(fixedDelay = 1000, initialDelay = 1000)
 *   @Scheduled(fixedRate = 1000)        // fixedRate property runs the scheduled task at every n millisecond.
 *   @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
 *   @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
 *   @Scheduled(cron = "${cron.expression}")
 *   @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
 *   public VOID scheduleFixedRateWithInitialDelayTask( VOID) {..}
 *       @EnableScheduling  // !!!
 *       @EnableAsync       // to support parallel behavior in scheduled tasks
 *       public class ScheduledFixedRateExample {
 *       @Async
 *       @Scheduled(fixedRate = 1000)    public void scheduleFixedRateTaskAsync() {..}..
 *
 * - JpaRepository(PagingAndSortingRepository):
 *   Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
 *   Pageable sortedByName = PageRequest.of(0, 3, Sort.by("name"));
 *   Pageable sortedByPriceDesc = PageRequest.of(0, 3, Sort.by("price").descending());
 *   Pageable sortedByPriceDescNameAsc = PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
 *   publ interface PersRepository ext JpaRepository/PagingAndSortingRepository<Person, Integer> {
 *     @Modifying
 *     @Query("update Person set lastName= :lastName where id= :id")
 *     void changeLastName( Integer id, String lastName);
 *     @Query("from Person")
 *     List<Person> getList( PageRequest pageRequest);
 *   }
 * */
// ________________________________ Spring WebFlux framework
/**
 *   -Reactive RestController <-> WebClient,
 *   -Reactive WebSocket      <-> WebSocketClient for socket style streaming of Reactive Streams.
 *
 *   Work with DB: - WebFlux + JDBC; - WebFlux + R2DBC; - WebFlux + Spring_Data_R2DBC
 *
 *   Mono.just(EMPLOYEE_DATA.get(id));          // Mono<Employee>
 *   Flux.fromIterable(EMPLOYEE_DATA.values()); // Flux<Employee>
 *
 *   @EnableWebFluxSecurity
 *   public class EmployeeWebSecurityConfig {
 *     @Bean public SecurityWebFilterChain springSecurityFilterChain( ServerHttpSecurity http) {..}
 *
 *   @RestController
 *   @RequestMapping("/employees")
 *   public class EmployeeController {
 *     @GetMapping("/{id}")
 *     public Mono<Employee> getEmployeeById(@PathVariable String id) { return employeeRepository.findEmployeeById(id); }
 *     @GetMapping
 *     public Flux<Employee> getAllEmployees() { return employeeRepository.findAllEmployees(); }
 *
 *   WebClient client = WebClient.create("http://localhost:8080");
 *   Mono<Employee> employeeMono = client.get().uri("/employees/{id}", "1")
 *                                 .retrieve().bodyToMono(Employee.class);
 *   employeeMono.subscribe(System.out::println);
 *   Flux<Employee> employeeFlux = client.get().uri("/employees")
 *                                 .retrieve().bodyToFlux(Employee.class);
 *   employeeFlux.subscribe(System.out::println);
 *
 * - //https://www.youtube.com/watch?v=ECajRLPhVc8 - Почему мы решили переходить на R2DBC и чем это закончилось
 *    @Repository interface MyRep ext R2dbcRepository<Person, Long> {
 *     Flux<Person> findAllByAgeGreaterThan( Integer age);         }
 *   @Entity class Person {
 *     @Id @Column( "id") Long id;
 *     @Column( "age") Integer age;
 *   }
 *   Flux<String> res = myRep.findAllByAgeGreaterThan( 30).map( Person::getName)
 *
 *   R2dbcRepository ext ReactiveCRUDRepository<T,ID> {
 *       <S ext T> Mono<S> save( S s);
 *       <S ext T> Flux<S> saveAll( Iterable<S> sList);
 *       Mono<T> findById( ID id);
 *       ...
 *   }
 *
 *   - Exist @Transactional for SpringDataR2dbc
 *   - R2dbc doesn't provide OnToOne and OneToMany!
 *   - JDBC + R2dbc in single service is POSSIBLE
 *
 *   R2DBC not to make system faster but to improve scalability, reliability ( R2DBC - authors)
 *   - R2DBC slower than JDBC in general
 *   - R2DBC better in extremal load/sharp growth of load than JDBC
 *   - WebFlux + R2DBC - allow include clientSide into transaction( should be careful)
 *   - WebFlux + JDBC - doubtful solution
 * */
// ________________________________ Spring Cloud
/**  ClientSideLoadBalancer: SpringCloudLoadBalancer, Ribbon( Netflix, obsolete)
*     FeignClient - web client can work with SpringCloudLoadBalancer or Ribbon( Netflix)
**/
// ________________________________ Spring Security
//https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
/** @EnableWebSecurity
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
 *     .doFilter( ServletRequest request, ServletResponse response,...)
 *      register as Filter in ServletContainer for every request
 *
 *  SecurityContextHolder.getContext().set/getAuthentication( Authentication);
 *
 *  interface Authentication ext Principal // represent currently authenticated user
 *   principal   userName or instance of UserDetails  // Object getPrincipal();
 *   credentials password                             // Object getCredentials();
 *   authorities permissions(roles)                   // Collection<? extends GrantedAuthority> getAuthorities();
 *
 * interface GrantedAuthority extends Serializable {  String getAuthority();}
 *
 * - Def scope of security context is ThreadLocal
 * */
// ________________________________ Spring OpenAPI/Swagger
/** springdoc-openapi  library helps to automate the generation of API documentation using spring boot projects.
 *  springdoc-openapi works by examining an application at runtime to infer API semantics based on spring
 *  configurations, class structure and various annotations.
 *    <dependency>
 *       <groupId>org.springdoc</groupId>
 *       <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
 *       <version>2.2.0</version>
 *    </dependency>
 * 		<dependency>    <!-- for WebFlux-->
 * 			<groupId>org.springdoc</groupId>
 * 			<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
 * 			<version>2.2.0</version>
 * 		</dependency>
 *
 * 	@SpringBootApplication
 * 	// optional:
 *  @OpenAPIDefinition( info = @Info( title="OrdersService (WebFlux API)", version = "1.0"))
 *
 * http://localhost:8080/v3/api-docs
 * http://localhost:8080/v3/api-docs.yaml
 * http://localhost:8080/swagger-ui.html
 *
 **/
/*


* */