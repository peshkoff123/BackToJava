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

