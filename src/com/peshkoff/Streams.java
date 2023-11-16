package com.peshkoff;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

/* Stream notion - sequence of elements from a outer source ( collections, arrays, I/O resources)
*           that supports aggregate operations ( filter, map, sorted,  find, reduce, match).
*  Abstraction called Stream that lets you process data in a declarative way.
*  In a nutshell, collections are about data and streams are about computations.
*  Two fundamental difference:
*   - pipelining - to make chain of operations
*   - internal iteration - iteration behind the scenes
*  Stream pipeline:  stream.intermediateOperation()...intermediateOperation().TerminalOperation()
*  Streams can leverage multi-core architectures
*  A stream should be operated on (invoking an intermediate or terminal stream operation) only once.
*
*  java.util.stream.BaseStream
*  java.util.stream.Stream
*  IntStream, LongStream, DoubleStream
*
*     StreamCreation
*  Collection.stream(), parallelStream()
*  Stream.of( T... values) = Arrays.stream( T[] arr)
*  Files.lines( Paths.get( "stuff.txt"))
*
*     IntermediateOperations
*  .filter( Predicate<T>::bool test(T t))
*  .distinct()
*  .limit( int size), .skip( int firstN)
*
*  .sorted() - for Comparable elements,
*  .sorted( Comparator::int compare(a,b))
*
*  .map( Function<T,R>::R apply(T t)),
*  .mapToInt( IntFunction<T>::int apply(T t)), .mapToDouble(), .mapToLong()
*  .flatMap( Function) - Stream<Stream<...>> => Stream<...>
*
*  .peek(Consumer<T>::accept( T t))
*
*     TerminalOperation
*  void forEach( Consumer<T>::void accept(T t) )  .forEach( System.out::println)
*  Object[] toArray(),  <A> A[] toArray( IntFunction<T>::T apply( int val))
*  List<>, Set<>, Map<> collect( Collectors.toList()), Collectors.toSet()
*  bool anyMatch( Predicate<T> :: bool test(t)), bool allMatch(...), bool noneMatch(...)
*  T res = reduce( T res, BinaryOperator<T> :: T apply( T a, T b))
*
*  - Parallel streams - sequential streams are DEFAULT
*       .parallel() or .parallelStream();
*       overhead of threads, splits, results collection; Array( ArrayList) - better then other Collections
*       Arrays.stream( new int[] {1,2,3,4}).parallel().reduce( 5, Integer::sum); // thread1(5+1+2) + thread2(5+3+4) !!!
*    used ForkJoin.commonPool(); // -D java.util.concurrent.ForkJoinPool.common.parallelism=4
*
*    ForkJoinPool customThreadPool = new ForkJoinPool(4);
*    int sum = customThreadPool.submit( () -> listOfNumbers.parallelStream().reduce(0, Integer::sum)).get();
*    customThreadPool.shutdown();
*
 * */
public class Streams {
    static void runAllTests() {
        runAlbumTests();
        runPersonTests();
    }
//_________________________
    static class Person {
        public enum Sex { MALE, FEMALE}
        public String name;
        public Sex gender;
        LocalDate birthday;
        Person( String name, Sex gender) { this.name = name; this.gender = gender;   }

        @Override
        public String toString() { return name+"; "+gender; }
    }
static void runPersonTests() {
    // Write the following enhanced for statement as a pipeline with lambda expressions
    // Use the filter intermediate operation and the forEach terminal operation.
    Person[] roster = new Person[ 10];
    for( int i=0; i<roster.length; i++)
        if( i%2 == 0)
             roster[ i] = new Person( Integer.toString( i), Person.Sex.MALE);
        else roster[ i] = new Person( Integer.toString( i), Person.Sex.FEMALE);

    for( Person p : roster)
       if( p.gender == Person.Sex.MALE)
                System.out.println(p.name);

    Stream firstStream = Stream.of( roster);
    System.out.println( "firstStream="+firstStream);
    firstStream.filter( p -> ((Person)p).gender == Person.Sex.MALE).forEach(  System.out::println);;
    //firstStream.forEach( next -> { System.out.println( next+", "+((Person)next).gender);});
    //firstStream.forEach(  System.out::println);
    }
//_________________________
static class Album{
  public String name;
  public int rating;
  public List<Track> trackList;
  Album( String name) { this( name, 0);  }
  Album( String name, int rating) {
      this.name = name; this.rating = rating;
      trackList = Arrays.asList( new Track( "Track 1", 1),
                                 new Track( "Track 2", 4),
                                 new Track( "Track 3", 2));
  }
  public String toString() { return name+"; "+rating; }
}
static class Track{
  public String name;
  public int rating;
  Track( String name, int rating) { this.name = name; this.rating = rating;   }
  public String toString() { return name+"; "+rating; }
}
static void runAlbumTests() {
/*List<Album> favs = new ArrayList<>();
for (Album a : albums) {
        boolean hasFavorite = false;
        for (Track t : a.tracks) {
            if (t.rating >= 4) {
                hasFavorite = true;
                break;
            }
        }
        if (hasFavorite)
            favs.add(a);
    }
Collections.sort(favs, new Comparator<Album>() {
        public int compare(Album a1, Album a2) {
            return a1.name.compareTo(a2.name);
        }});
 */
   Album[] albums = { new Album( "Album 3"), new Album( "Album 2"), new Album( "Album 1")};
   /*Predicate isFavourite = a -> {
       for (Track t : ((Album)a).trackList)
           if (t.rating >= 4)
               return true;
       return false;
   };*/
   Predicate<Album> isFavourite = a -> {
        for (Track t : a.trackList)
            if (t.rating >= 4)
                return true;
        return false;
   };

   List<Album> listOfAlbum =
   Arrays.stream( albums)
         //.filter( isFavourite)
         .filter( a -> a.trackList.stream().anyMatch( track -> track.rating >=4))
         .sorted( ( a1, a2) -> a1.name.compareTo( a2.name))
         .collect( Collectors.toList());

   String s = "listOfAlbum : ";
   s = listOfAlbum.stream().map( alb_i -> alb_i.name).reduce( s, ( a, b) -> a+b+" ,");

   System.out.println( "l.stream().toString()="+s);

}
}
