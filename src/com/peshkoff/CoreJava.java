package com.peshkoff;

public class CoreJava {}
//_________________________________ GENERICS
/* Generics allows replace hardCoded types with parameterTypes(like a variable types)
 *  Userd in classes, interfaces and methods( virtual, constructors, static)
 *  Benefits: - type check at compile time
 *            - no need type cast
 *            - ability to use same code for wider range of types ( higher level of abstraction)
 *  <BoundedType  extends Class1 & Class2 & Interf1 & Interf2>
 *  Wildcards (?)  -  List<? extends Number>
 *                 -  Lisy<? super Double>
 *                 -  List<?>  // List of Objects ot
 *                             // for invocation of typeIndependemt methods (List.size, list.clear)
 *  Raw type List l = new List();
 * */
//_________________________________ ENUM
// set of constants( objects)
// extends of "abstract class Enum extends Object implements Comparable<E>, Serializable"
// constructor - private only
/*enum myEnum {
    //SUNDAY, MONDAY, TUESDAY, WEDNESDA
    val1( "nameVal1", 1), val2( "nameVal2", 2);

    static final public void staticMetTest() { System.out.println("myEnum.valueOf( \"val1\")="+myEnum.valueOf( "val1"));    }
    String name;
    int ordinal;
    myEnum( String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
}*/
// ________________________________ LambdaExpr-s
/* Implementation of single abstract method of funcInterface
 *  lambda expression does not introduce a new level of scoping
 *  Runnable :: void run()
 *  Comparable :: int compareTo(T o)
 *  Comparator :: int compare( a, b)
 *  ActionListener.actionPerformed()
 *  Consumer<T> :: void accept(T t)
 *  Supplier<T> :: T get()
 *  Predicate<T> :: boolean test(T t)
 *  Function<T, R>    :: R apply(T t);
 *  Function<T, U, R> :: R apply(T t, U u);
 *  IntFunction<T> :: T apply( int val)
 *  BiFunction<T, U, R> :: R apply( T t, U u)
 *  BinaryOperator<T> :: T apply( T t, T u) ext BiFunction
 * */
// ________________________________ Method references ::
/* LambdaExpression consists of single method may use ::
 * Class :: static method
 * Obj   :: method
 * Class :: NonStatic method     // String::compareToIgnoreCase
 * Class :: new                  // HashSet<Person>::new
 * */
// ________________________________ Annotations
/* Annotation - additional metaData stored in class. Usually metaData - for compiler ot 3-d party.
 *  @Entity, @Override, @SuppressWarnings("unchecked"), @Author( name = "Benjamin Franklin", date = "3/27/2003")
 *  Can be applied to class, fields, methods, types
 *  @interface ClassPreamble {
 *   int currentRevision() default 1;
 *   String lastModified() default "N/A";
 *   String[] reviewers();  }
 *  To retrieve annotation used java.lang.reflect.AnnotatedElement
 * */
// ________________________________ Exceptions
/* Exception - event that disrupts normal execution of program.
 *    CheckedExceptions - must handled
 *    Errors - exceptional situation external to program (java.io.IOError)
 *    RuntimeException -
 *  Object -> Throwable -> Error|Exception -> RuntimeException
 *
 * try{
 * } catch( IndexOutOfBoundsException  ex) { ;
 * } catch( IOException  ex) { ;
 * } catch( IndexOutOfBoundsException | IOException  ex) { ;
 * } finally {
 * }
 *   Try-with-resources
 * try( FileReader fr = new FileReader(path);
 *	   BufferedReader br = new BufferedReader(fr)) {
 *         return br.readLine();
 *      } catch( IOException ex) {  // performed AFTER the resources are closed
 *         ex.printStacktrace();
 *      } finally {                 // performed AFTER the resources are closed
 *      }
 *  java.lang.AutoCloseable
 *  java.io.Closeable extends AutoCloseable
 *  FileReader, BufferedReader - AutoCloseable and declared in Try-with-resources -
 *                               be closed regardless of success of try body.
 * */
// ________________________________ Collections
/* Collection - group of elements.
 *      add,addAll, remove,removeAll, iterator, clear, size, toArray
 *  Set - Collection which doesn't have duplications. No additional methods.
 *     Implementations: HashSet - best speed any order, TreeSet, LinkedHashSet
 *  List - Collection with order. AddMethods - add,remove(position), get,set, sort
 *      indexOf,lastIndexOf, listIterator, sublist(fromInd,toInd)
 *      ListIterator - bidirectional iterator: hasNext,hasPrev, next,prev,
 *         remove,set(last obj returned by next,prev), nextIndex,prevIndex
 *  Implementations ArrayList(resizeArray, nullAllowed), LinkedList(double-linked null_allowed),
 *  Queue - Collection with some order FIFO/LIFO/other and extra access to head item:
 *                  Exception   null/bool
 *     Insert       add,        offer
 *     Get          element,    peek
 *     Get/Remove   remove,     poll
 *  Implementations LinkedList(double-linked null_allowed),...
 *  Deque - doubleEndQueue - linear collection, allows add/remove items from both ends.
 *     Insert       AddFirst,  offerFirst        addLast,  offerLast
 *     Get          getFirst,  peekFirst         getLast,  peekLast
 *     Get/remove   remFirst,  pollFirst         remLast,  pollLast
 *  Implementations LinkedList(double-linked nullAllowed),
 *                  ArrayDeque(resizeArray - faster, nullDisAllowed)
 *  Map - collection of key-value. No duplicate keys, one key-one value.
 *     Methods put,putAll, get, remove, containsKey,containsValue, size.
 *  Implementation - HashMap; LinkedHashMap: double linked HashMap + order of elements addition;
 *     TreeMap impl NavigableMap, SortedMap: based on BlackRedTree
 *
 *  Collections - static methods to work with Collections: sort, search, transform one to other,
 *     replace, reverse, synchronized,
 * */
// ________________________________ IO Streams
/* InputStream, OutputStream - abstract base classes to read and write bytes or byte[], AutoCloseable.
*  Reader, Writer - abstract base classes for chars or char[] or String, AutoCloseable.
*  // while( ( i = in.read()) != -1) { out.write( i);}
*  InputStreamReader - ext Reader, InputStream -> Reader converter, new InputStreamReader(System.in)
*  OutputStreamWriter - ext Writer, OutputStream -> Writer wrapper, new OutputStreamWriter(System.out)
*  BufferedInput(Output)Stream - buffered InputStream, OutputStream
*  BufferedReader - ext Reader, read String,char,char[]. new BufferedReader( new FileReader("1.txt"))
*  BufferedWriter - ext Writer, write String,..          new BufferedWrite( newFileWriter("1.txt"))
*  Scanner - split String, InputStream, File, Reader with delimeter (" "-def), impl Iterator
*  PrintWriter - ext Writer, formatting print/ln( anyTypes), printf( locale, format, Object[])
*  PrintStream - ext OutputStream, print/ln( anyTypes), printf( locale, format, Object...),
*                System.out, System.err
*  DataInputStream - read all primitive types from InputStream
*  DataOutputStream - write all primitive types into OutputStream
// ________________________________ Serialization
/* ObjectOutputStream - ext OutputStream, writeObject( obj impl Serializable), write( anyTypes)
*  ObjectInputStream - ext InputStream, readObject( obj), read( anyTypes)
*  Serializable - sing that object can be serialized, private writeObject(), private readObject()
*  transient - sign to miss the field during serialization
*  Externalizable - ext Serializable, readExternal(), writeExternal()
* */
// ________________________________ Files NIO
/* Paths - factory fo Path. // Path p = Paths.get("1.txt");
 *  Path - interface represent full fileName or URI, analog of old class Path.
 *  Files - static methods for all file operations create, delete, read, write, size, exist,...
 *  FileChannel - analog of RandomAccessFile.//FileChannel fc = FileChannel.open(file, READ, WRITE)
 * */
// ________________________________ NIO
/* Buffer
 *    abstract Buffer     [..,limit,..,position,..,capacity]
 *    ByteBuffer  direct(native IO operations, not GC)/indirect; convert <=> anyOtherTypeBuffers;
 *                get/putAnyOtherType( AnyOther value);
 *                create: static .allocate/allocateDirect( capacity); .wrap( byte[])
 *    Char/Int/Short/Long/Float/Double Buffers( except boolean)
 *  Charset static .defCharset(), .forName( "US-ASCII/ISO-8859-1/UTF-8/UTF-16") // create Charset
 *          ByteBuffer .encode( CharBuffer);
 *          CharBuffer .decode( ByteBuffer)
 *  Channel - analog of IOStream, Reader/Writer
 *    Channels - class with static methods Readable/WritableByteChannel <=> IOStream, Reader/Writer
 *
 *    interface ReadableByteChannel:: int read( ByteBuffer b);  // Channel => buffer
 *    interface WritableByteChannel:: int write( ByteBuffer b); // buffer  => Channel
 *    interface ByteChannel :: ReadableByteChannel + WritableByteChannel
 *        FileChannel, DatagramChannel, SocketChannel extends SelectableChannel,.. impl ByteChannel,..
 *
 *    SelectableChannel :: configureBlocking( bool)              // Blocking/Selectable mode of channel
 *                      :: register( Selector s, int ops)        // get served by Selector class
 *       DatagramChannel, ServerSocketChannel, SocketChannel, Pipe.Sink/SourceChannel ext SelectableChannel
 *       Pipe.SinkChannel,Pipe.SourceChannel                     // synchronous exchange between concurrent threads
 *    SelectionKey :: int OP_CONNECT/OP_ACCEPT, OP_READ/OP_WRITE // ops
 *                 :: SelectableChannel channel()
 *    Selector :: static Selector open()
 *             :: select(), select( timeOut)                      // Blocks and listen registered SelectableChannels
 *             :: Set<SelectedKey> selectedKeys()                 // IO happens in this channels
 *
 *    FileChannel :: read/write( ByteBuffer b)
 *                :: MappedBuffer map()                           // to map into memory big files
 *                :: FileLock lock(), lock(int pos, size)         // acquires exclusive lock for threads
 *
 *    interface AsynchronousChannel                               // contract: return Future and use CompletionHandler
 *    implementations:
 *    AsynchronousFileChannel
 *      :: Future<Integer> read/write( ByteBuffer b)
 *      :: A read/write( ByteBuffer b, A attachment, CompletionHandler c)
 *    AsynchronousServerSocketChannel
 *      :: Future<AsynchronousSocketChannel> accept()
 *      :: A accept( A attachment, CompletionHandler c)
 *    AsynchronousSocketChannel
 *      :: Future<Void> accept( SocketAddress addr)
 *      :: void accept( SocketAddress addr, A attachment, CompletionHandler c)
 *      :: read/write( ByteBuffer b)
 *      :: read/write( ByteBuffer b, A attachment, CompletionHandler c),
 *
 *    AsynchronousChannelGroup // threadPools for AsynchronousChannels
 *
 *    CompletionHandler :: completed( V result, A attachment)
 *                      :: failed( Throwable ex, A attachment)
 *
 * */
// ________________________________ MultiThreads (AtomicityVisibilityOrdering)
/* java.lang.Runtime.availableProcessors()
 *                   .freeMemory()
 * Thread: Recommended not use wait()/notify() on Threads
 *          ReentrantSynchronization : synchronized( a) { synchronized(a) { ... }}
 *          synchronized( a) { synchronized(b) { ... }}
 *
 *  volatile - ensure only propagation of variableValue for all CPU_cores, does not use CPU cache
 *     and use main memory. Guarantees visibility and ordering - prevents compiler of reordering code.
 *     NOT guarantees atomicity. Applicable for all vars and refs.
 *
 *  CompareAndSwap - java.util.concurrent.atomic
 *     uses special native code activate CPU lock, it makes criticalSection shorter.
 *
 *  java.util.concurrent.locks.lock - control access to shared resources like synchronized;
 *    + blocking/nonBlocking attempt to get a lock
 *    + multiple locks acquired and released in any order
 *  Interface Lock:
 *    lock.lock();                   // blockingLock - waite for lock
 *      try {...} finally{ lock.unlock():}
 *    if( lock.tryLock( timeOut))    // nonblockingLock
 *      { ...; lock.unlock();} else {..}
 *    ReentrantLock - mutualExclusive impl of Lock
 *  Interface ReadWriteLock - for one write lock/thread and many read locks/threads.
 *  ReadWriteLock.WriteLock
 *  ReadWriteLock.ReadLock
 *  ReentrantReadWriteLock impl ReadWriteLock
 *  Interface Condition: analog of Obj-monitor
 *    lock.newCondition().await(), await( timeOut); // like Obj.waite();
 *                           // looses lock while THAT condition from other thread call signal()
 *                       .signal(), signalAll();    // Obj.notify()
 *
 *  java.util.concurrent ( Synchronizers)
 *  - Semaphore( int permits, bool fair)       // fair = true - guarantee FIFO for waiting threads
 *    .acquire( int permits)                   // with wait
 *    .tryAcquire()                            // no wait
 *    .tryAcquire( int permits, long timeOut)
 *    .release( int permits)                   // back permits to Semaphore
 *  - CountDownLatch( count)                   // single-time countDown; one thread control many other
 *    .await()                                 // thread call and sleep while count==0
 *    .await( long timeOut)
 *    .countDown()                             // dec count; count==0 : all threads notify/run
 *  - CyclicBarrier( int parties,              // sync-zes work of parties threads; reusable
 *                   Runnable barrierAction)   // run when all threads rich the barrier
 *    .wait( timeOut)                          // thread call it and wait for rest
 *    .reset()                                 // resetBarrier
 *  - Phaser( Phaser parent, int parties)      // phases 0->n "+1" for nextGenPhaser; manyPhases+manyThread
 *    .register(), bulkRegister( int n)        // register 1 or n parties
 *    .arrive(),arriveAndDeregister()          // don't wait - get phaseNumber
 *    .arriveAndWaitAdvance()                  // arrive the phaseEnd and wait for other threads
 *    .awaitAdvance( int phase)                // wait until phase happens
 *    .isTerminated()                          // this phase is finished
 *  - Exchanger()
 *    .exchange( V v)                          // wait for 2-nd thread to call exchange() to exchange objects
 *
 *  java.util.concurrent.                           // Executor interfaces
 *   Interface Executor.execute( Runnable r)        // add many tasks potentially
 *   Interface ExecutorService ext Executor
 *    .submit( Runnable, Callable)                  // => Future
 *    .invokeAll?/Any( Collection<Callable>)        // => List<Future>
 *    .shutdown()
 *   Interface ScheduledExecService ext ExecutorService
 *    .schedule( Runnable/Callable r, long delay)   // ScheduledFuture
 *    .scheduleWithFixedDelay( Runnable r, long initDelay, delay)
 *   Interface Future                               // Res of async computation
 *    .cancel()
 *    .get( timeOut)                                // wait and getResult of Callable
 *    .isCancelled(), isDone()
 *   Interface ScheduledFuture ext Future, Delayed
 *    .getDelay()                                   // remained delay for the task
 *
 *   java.util.concurrent.Executors                 // factory
 *    .newSingleThreadExecutor                      // ExecutorService : all tasks in same thread
 *    .newFixedThreadPool( threadNumber)            // -||- : fixed thread number
 *    .newCachedThreadPool()                        // -||- : var thread number
 *    .newWorkStealingPool( parallelismLevel)       // -||- :
 *    .newScheduledThreadPool( poolSize)            // ScheduledExecutorService
 *    .newSingleThreadScheduledExecutor()           // -||- : one thread
 *
 *   ForkJoinPool( parallelism)                     // for work can be broken to pieces recursively
 *                 impl ExecutorService             // uses work-stealing alg-m
 *    .submit( Callable, Runnable, ForkJoinTask)    // =>ForkJoinTask
 *   abstract ForkJoinTask impl Future              // intended to divide all work to || subtask if needed
 *    .fork()                                       // acyncExecute in current ForkJoinPool
 *    .invoke(), invokeAll( ForkJoinTask[])         // run in that pool child tasks
 *    .join()                                       //
 *   abstract RecursiveAction ext ForkJoinTask      // resultLess task
 *    .abstract void compute()                      // code of task
 *   abstract RecursiveTask<T> ext ForkJoinTask     // task with ret result
 *    .abstract T compute()                         // => T; code of task
 *
 *   ConcurrentCollections - on CAS/Snapshot principle
 *   Queue
 *     BlockingQueue.put(e),take()                                        - blocking;
 *                  .offer(e, timeOut),poll(timeOut)                      - timeOut
 *       ArrayBlockingQueue, LinkedBlockingQueue, ... impl BlockingQueue
 *     BlockingDeque.putFirst/Last(e),takeFirst/Last()                    - blocking;
 *                  .offerFirst/Last(e, timeOut),pollFirst/Last( timeOut) - timeOut
 *       LinkedBlockingDeque impl BlockingDeque
 *     ConcurrentLinkedQueue - nonBlocking thread-safe Queue
 *     ConcurrentLinkedDeque - nonBlocking thread-safe Deque
 *
 *   CopyOnWriteArrayList - mutative operations (add,set,remove) - create copy; iterator - not support remove()
 *   CopyOnWriteArraySet - based on CopyOnWriteArrayList
 *
 *   ConcurrentHashMap - nonBlocking thread-safe
 *   Map->SortedMap->NavigableMap.lowerEntry/Key( K key), higherEntry/Key( K key)
 *                                ->ConcurrentNavigableMap
 *   ConcurrentSkipListMap impl ConcurrentNavigableMap     // containsKey(),get(),put(),remove() : timeCost=log(n)
 *   ConcurrentSkipListSet based on  ConcurrentSkipListMap // contains(),add(),remove() : timeCost=log(n)
 *
 * */
// ________________________________ CoreJava Q_And_A
/* - JVM memory:    Heap                        |    PermGen/MetaSpace
 *     Eden -GC-> Survivor -GC-> OldGeneration  |  Classes + ClassLoaders( must be destroyed manually)
 * - switch( type): byte, short, int, and wrappers, String, Enum
 * - Wrappers implement Serializable, Comparable; Number - super for all digitals
 * - Arrays.asList( T[] tArr)
 * - java.lang.reflect. Method, Constructor, Field, Modifier, Parameter
 * - Clone obj:-impl Cloneable + Object.clone()-shallowCloning;- copyConstructor;-Serialization
 * - Marshalling - passing code( Class) + Serialization (fieldValues)
 * - serialVersionUID - added automatically at compile stage for class impl-s Serializable
 *    private static final long serialVersionUID; declare manually to control it
 * - abstract Class without methods - Yes
 * - MultyThreads (AtomicityVisibilityOrdering)
 *   volatile - ensure only propagation of variableValue for all CPU_cores, does not use CPU cache
 *     and use main memory. Guarantees visibility and ordering - prevents compiler of reordering code.
 *     NOT guarantees atomicity. Applicable for all vars and refs.
 *   CompareAndSwap - java.util.concurrent.atomic
 *     uses special native code activate CPU lock, it makes criticalSection shorter.
 * - LinkedHashSet - doubleLinked HashSet to keep elements order
 * - LinkedHashMap - doubleLinked HashMap to keep elements order
 * - FailFast Iterator throws ConcurrentModificationException during iterating ( Lists, HashMap,..); may use Iterator.remove()
 * - FailSafe Iterator - doesn't: Snapshots in CopyOnWriteArrayList; ConcurrentHashMap is weakly consistent
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
 * - String.length() - ret "number of Unicode code units"; some of chars consists of two "code units"
 *                     str.codePointCount( 0, str.length())
 *
 * - ComplitableFuture
 */