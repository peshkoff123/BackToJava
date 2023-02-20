package com.peshkoff;
public class Spring {}
// ________________________________ Spring
/** Spring - modular openSource for enterprise. Core - DI(IoC),MVC,AOP
 *
 *                             AspectOrientedProgramming
 *
 *  Proxy(Cglib proxy) - wrapper for user POJOs for transaction functionality.
 *  @Transactional - marks method works with Connection/Transaction, informs Spring it need to
 *                   open and then commit it.
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
 * Stereotypes
 * - @Component - basic, for all Stereotypes classes
 * - @Configuration - JavaConfiguration + @Bean_methods
 * - @Contoller
 * - @RestController - @Contoller + @ResponseBody
 * - @Service - for classes of business logic layer (service layer of app)
 * - @Repository - for classes of persistence layer
 *
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
 *   @EventListener //get events from appContext
 *   public void prosessAppContextEvent( someEvent) {...;}
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
 * - ApplicationContext  types
 *    - FileSystemXmlApplicationContext
 *    - ClassPathXmlApplicationContext
 *    - AnnotationConfigApplicationContext
 *    - XmlWebApplicationContext
 *    - AnnotationConfigWebApplicationContext
 * - Как получить ApplicationContext в интеграционном тесте?
 *   @ExtendWith(TestClass.class) — используется для указания тестового класса
 *   @ContextConfoguration(classes = JavaConfig.class) — загружает java/xml конфигурацию для создания контекста в тесте
 *   Можно использовать аннотацию @SpringJUnitConfig, которая сочетает обе эти аннотации.
 *   Для теста веб-слоя можно использовать аннотацию @SpringJUnitWebConfig.
 *
 * - @Bean( initMethod, destroyMethod, name( by def methodName), value — алиас для name)
 * - LifeCycle of Bean
 *   - Загрузка описаний бинов, создание графа зависимостей
 *     - BeanFactoryPostProcessors: override bean definitions configured in the application context
 *   - Создание бинов внедряет значения и зависимости
 *     - BeanPostProcessor.postProcessAfterInitialization( Object newBean, String beanName)
 *                        .postProcessBeforeInitialization()
 *    - NameBeanAware.setBeanName( newName)
 *      BeanFactoryAware.setBeanFactory( beanFactory)
 *      ApplicationContextAware.setApplicationContext( applicationContext)
 *      @PostConstruct methodDescription
 *      @Bean( initMethod="..")
 *   - Теперь бин готов к использованию. ApplicationContext.getBean()
 *   - После того как контекст будет закрыт( ApplicationContext.close()), бин уничтожается.
 *      @PreDestroy methodDescription  перед уничтожением вызовется этот метод.
 *      DisposibleBean.destroy(), чтобы очистить ресурсы или убить процессы в приложении.
 *      @Bean( destroyMethod="..") то вызовется и он.
 * - Как создаются бины: сразу или лениво? Как изменить это поведение?
 *    - Singleton-создаются сразу при сканировании.
 *    - Prototype-бины обычно создаются только после запроса.
 *    - @Lazy = @Lazy(true) Чтобы указать способ инициализации, можно использовать аннотацию @Lazy.
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
 * - javax.sql.DataSource interface; source of JDBC_connection; use @Bean + manual settings to configure it
 *
 * - @Autowired,  @Autowired( required = false)
 *   @Qualifier( "exactClassName/BeanName")
 *
 * // for @Controller, @RestController
 * - @ExceptionHandler(RuntimeException.class)
 *     ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
 *        return new ResponseEntity<Exception>( ex, HttpStatus.INTERNAL_SERVER_ERROR);
 *     }
 * - @ResponseStatus( HttpStatus.NOT_FOUND)  // for @Controller, @RestController
 *   - Exception
 *   - mapping method
 *   - HttpServletResponse.sendError( HttpServletResponse.SC_BAD_REQUEST)
 *
 * - Spring Web/Rest/Http_Client : RestTemplate( based on Apache Http Client)  ( WebClient - newer)
 *    TestRestTemplate - wrapper for RestTemplate; differ: 4xx and 5xx do not result in an exception
 *    HttpEntity<T>; RequestEntity<T>, ResponseEntity<T> ext HttpEntity
 *
 * - ProxyObjects - wrapper for anyObject to add separated logic: transactions, security, logging,..
 *   JDK-proxy — dynamic proxy. based on separate interface. API встроены в JDK.
 *   CGLib proxy — third party. based on inheritance
 *
 * - @Transaction propagation уровни в транзакциях
 * - can we delete bean from container
 *   ((BeanDefinitionRegistry) beanFactory).removeBeanDefinition( "myBean");
 *   ((DefaultListableBeanFactory) beanFactory).destroySingleton( "myBean");
 *   ((SingletonBeanRegistry) beanFactory).registerSingleton( "myBean", myBeanInstance);
 * - SpringCloud?
 * */
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
 * */
