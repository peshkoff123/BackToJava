package com.peshkoff;
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
// ________________________________ WEB
/* HTTP Session: Establish TCP connection; 1-N. HTTP_request <-> HTTP_response;
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
