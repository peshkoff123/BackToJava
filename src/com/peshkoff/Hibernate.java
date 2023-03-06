package com.peshkoff;

public class Hibernate {}

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
 *  @GeneratedValue( strategy=GenerationType.AUTO      // Hibernate generated ID ("hybernate_sequence" table)
 *                                          .IDENTITY  // DB generate ID
 *                                          .SEQUENCE
 *                                          .TABLE
 *                                          .class )
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

// ________________________________ Hibernate Q_And_A
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
 * - JPA::EntityManager - analogue of Hibernate::Session
 * - Session.load( Class, id) - ret proxy, loadLazy, if noObjID - exception
 *          .get( Class, id)  - hit DB immediately, if noObjID - ret null
 * - Session.merge( Obj) - merge Obj with sameID_Obj in session and save to DB
 *          .update( Obj) - if sameID_Obj is in session already - exception, otherwise - same as merge()
 * */
