package com.peshkoff;

// ________________________________ Test
/**- Testing types
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
// ________________________________ JUnit 5
/** Annotations org.junit.jupiter.api.*
 *   Assertions  org.junit.jupiter.Assertions
 *   Assumptions org.junit.jupiter.api.Assumptions
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
 *    @Execution(CONCURRENT)
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
public class Testing {}
