package com.peshkoff;
public class DB {}

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
 * - QueryPlan - way to analyze SQL request: EXPLAIN, HINT
 *     EXPLAIN [ ANALYZE  plan + realExecute
 *               VERBOSE,      ..]
 *   PostreSQL example:  EXPLAIN SELECT * FROM tenk1 WHERE unique1 = 42;
 *     QUERY PLAN:
 *     Index Scan using tenk1_unique1 on tenk1  (cost=0.29..8.30 rows=1 width=244)
 *     Index Cond: (unique1 = 42)
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
 *     Read uncommitted is the default isolation level and applies to mongoDB standalone instances as well
 *     as to replica sets and sharded clusters.
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


