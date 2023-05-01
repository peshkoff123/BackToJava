package com.peshkoff;
// ________________________________ Theory
/**
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
 * - CAP - ConsistensyAvailabilityPartitionTolerance распредСистема в любое время может обеспечить не более 2-ч из 3-х
 *
 * CI/CD:
 *               ContinuousIntegration                           ContinuousDelivery
 * Commit-Build-Unit/IntegrTest-AppImage - FuncTest-UserAcceptanceTest-ConfigurationAutomation-LoadTests-Deploy
 *
 *
 *
 * */
// ________________________________ HTTP
/** https://developer.mozilla.org/ru/docs/Glossary/safe
 * - Идемпотентный метод - не меняет состояние сервера при множестве запросов но возврат может
 *       отличаться - получение данных или статистики
 *   POST/create - NO_idempotent: N identicalRequest = N new records
 *   PUT/update  - idempotent:  N identicalRequest = same result
 *   GET, HEAD, PUT, DELETE - idempotent
 *
 * - HTTP retCodes
 *   - 100-199 - info
 *   - 200-299 - success:    200 - OK
 *   - 300-399 - redirect
 *   - 400-499 - client error: 400 badRequest; 403 forbidden; 404 NotFound
 *   - 500-599 - server error
 *
 * - HTTP/2.0:
 *  - Binary -
 *  - Frames - HTTP_message = [1..n] Frames;
 *     Header{ Length, Type, Flags, StreamID} PayLoad{..}
 *      Type: Data, Headers, Priority, Rst_Stream, Settings, PushPromise, Ping, GoAway, Window_Update, Continuation
 *  - Stream - set of Frames with same FrameID; Connection = [1..n] Streams;
 *  - ServerPush - push data to client cache; 1 request - many response
 *  - Switch to HTTP 2.0:
 *      UpgradeRequest: HTTP/1.1_Request              Headers "Connection:Upgrade", "Upgrade:HTTP/2.0"
 *                      HTTP/1.1_Response retCode=101 Headers "Connection:Upgrade", "Upgrade:HTTP/2.0"
 * - HTTP/3.0 changes: UDP instead TCP
 *
 * - HTTPS = HTTP + TLS( SSL)
 * - TLS - Transport Layer Security - former SecureSocketLayer;
 *                                    provides: Encryption + Authentication + Integrity( packageHashCode) of data
 *    TLS/SSL_Certificate = ServerPublicKey; IssuedTo: Company/Person; IssuedBy: AuthorityCompany; ValidityPeriod;
 *    SessionKey - symmetricKey much faster than asymmetricKeys; encrypt/decrypt session data exchange
 *    PublicKey+PrivateKey - asymmetricKeys for initial Handshaking = client checks serverCertificate + generate SessionKey
 *
 * - gRPC - binary protocol for microServices faster that JSON/REST; uses ProtoBuf; based on HTTP/2;
 *          supported by many languages; not supported by browsers yet
 *
 * - ProtocolBuffers - binary encoding data format for serialization; rival for JSON;
 *                     language to describe data format; uses encoded/binary data
 *    - protofile - data schema description doc
 *       publisher.proto
 *         service Publisher { rpc SignBook (SignRequest) ret (SignResponse) }
 *         message SignRequest { string name=1;}
 *         message SignResponse { string signature=1;}
 *
 * - SSO - Single Sign-On - authentication schema to access multiple trusted but independent app using single ID
 *   - SAML - SecurityAssertionMarkupLanguage XML protocol;
 *            SAML_Assertion: encrypted XML{ UserDetails, Athorities} - analog JWT
 *            Identity providers: Okta, Auth0, oneLogin
 *   - OpenID connect - Google JWT protocol
 *
 * - JWT - JSON WebToken
 *    header:  { "typ":"JWT", "alg":"HS256"}
 *    payload: { "id","userName","role","inspire",..}
 *    signature:"hashCode( header.payload)"
 *
 * - CAP - ConsistensyAvailabilityPartitionTolerance распредСистема в любое время может обеспечить не более 2-ч из 3-х
 * */
// ________________________________ WEB
/** HTTP Session: Establish TCP connection; 1-N. HTTP_request <-> HTTP_response;
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
// ________________________________ JMS specification
/** PointToPoint messaging domain: OneToOne, warranted delivery - noTimingDependency sender/receiver
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
*
*  Open source messaging systems:  RabbitMQ, Apache Kafka, Apache ActiveMQ, and NSQ
* */
// ________________________________ ApacheMaven
/** Maven - framework, build tool, everything do plugins,
 *    - mvn install -DskipTests        // to skip running the tests( unit + integration) but still we want to compile them
 *   - mvn install -DskipITs          // to skip running the integration_tests only
 *   - mvn -Dmaven.test.skip package  // to skip the compilation phase; overrides property from POM
 *     <properties>                   // same in POM
 *         <tests.skip>true</tests.skip>
 *     </properties>
 *
 *  Artifact - anyLib(jar) in repository.
 *  Goal - special task, could be connected to a phase or not.
 *  Scope - compile, runTime, test,...
 *  Repository - local (~/.m2/repository/..), global https://repo.maven.apache.org/maven2/, remote
 *  ProjectObjectModel - pom.xml
 *  <project ...>
 *      <groupId>com.peshkoff</groupId>
 *      <artifactId>myProject</artifactId>
 *      <version>1.0-SNAPSHOT</version>
 *      <packaging>pom</packaging>
 *      <name>name</name>
 *    <modules>
 *        <module>InnerModule</module>
 *    <modules>
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
 **/
// ________________________________ Git
/** Git - for maintaining fileSystemSnapshots at specific time moments
 * Main notions
 * SHA-1 HASH - unique key/REF for BLOB, TREE, COMMIT; depends of content/body
 * REF - HASH
 * BLOB - body of file container (not metedata/fileAttributes)
 * TREE - directory/folder; contains REFs on subTREEs + BLOBs in that COMMIT or parent
 * COMMIT - snapshot of WORKING DIRECTORY + PARENT_COMMIT_REF+ Message+Date+Author
 * BRANCH - sequence of COMMITs/snapshots/changes dependent to each other;
 *        - named REF to last COMMIT
 *        - MASTER/MAIN; TRUNC
 *        - HEAD - special REF/pointer on current/working branch
 * WORKING DIRECTORY/TREE - project dir + .git folder
 * INDEX/STAGING AREA - register changes here and then push to COMMIT; to verify/confirm changes before commit them
 * REPOSITORY - collection of COMMITs, BRANCHES, other Git stuff
 * TAG - special name for commit
 * PATCH - enhanced diff; asymmetric
 *
 *  HEAD -> MAIN -> COMMIT_i
 *  HEAD -> BRANCH_k -> COMMIT_j
 *
 *  Main commands Git
 *  - init - create REPOSITORY, MASTER/MAIN branch, .git folder
 *      git init my_repo
 *  - add <file.name> - add file to INDEX/STAGING
 *      git add a.txt
 *  - commit - save snapshot into HEAD
 *      git commit -m "My first commit"
 *
 *  - checkout - re-point HEAD
 *  - reset <commit.REF> - switch to one of previous COMMITs = re-point commit.REF!
 *                         to replace last mistakable commit; should NOT be used after PUSH COMMIT !
 *      --soft   discard-LastCommit;
 *      --mixed  discard-LastCommit+AddIndex; def behaviour ( git reset=git reset --mixed);
 *      --hard   discard-LastCommit+AddIndex+LocalFileChanges;
 *      git reset --soft commit.REF
 *  - revert - reverse changes of last COMMIT by NEW_COMMIT
 *      git revert HEAD
 *  - merge - merges two branches, result - new commit
 *                         - com_N+1 - .. - com_M                 <- branch
 *          com_1 - com_N  - com_N+1 - com_merge := com_M         <- HEAD
 *     git checkout <brach_1>  // <- HEAD
 *     git merge <brach_1>
 *  - rebase - move baseCommit of branch to another commit of MASTER branch
 *                         - -moveBase-> - com_N+1 - .. - com_M                 <- branch
 *                  from com_N to com_N+1
 *          com_1 - com_N  - com_N+1           <- HEAD
 *     git checkout <brach_1>  // <- HEAD
 *     git rebase <brach_1(MASTER)>
 *  - cherry-pick <some.commit> - commit changes from <some.commit> into HEAD
 *                         - com_N+1 - .. - com_M                 <- branch
 *          com_1 - com_N  - com_N+1 - com_cherry-pick := com_N+2 <- HEAD
 *     git checkout <some.brach(MASTER)>
 *     git cherry-pick <some.commit>
 *
 *  - log
 *  - status - current state ( branch, STAGING, untrackedFiles)
 *      git status
 *  - diff <commit_1> <commit_2>
 *      git diff HEAD~1 HEAD; git diff HEAD~2 HEAD~1
 *  - show <some.commit>
 *  - reflog - show gits log
 *
 *  - push - to remote REPOSITORY
 *  - pull - from remote REPOSITORY
 *
 *  - stash CTRL_X - place uncommited changes on shelve
 *     git stash                       // CTRL_X
 *     git stash save "stash comment"
 *     git stash list                  // see all stack of stashes
 *     git stash apply <number( 0,1)>  // CTRL_V; def number = last
 *     git stash pop                   // CTRL_V + remove that stash
 *  - Itellij/Shelve - direct analogue os git stash
**/
// ________________________________ Docker
/** Docker - tool to deploy and run app using container. DockerClient(CLI) <- REST_API -> DockerServer
 *
 *  Virtualization: OS :: Hypervisor :: GuestOS_1 :: App_1           // overhead - GuestOS
 *                                   :: GuestOS_N :: App_N
 *
 *  Containerization: OS :: ContainerEngine :: Container_1 :: App_1  // light weight containers
 *                                          :: Container_1 :: App_1
 *
 *  + lightwaight, dynamicalMemoryConsumption
 *  - less isolation
 *  Container - process in HostOS ( dynamicalMemoryConsumption)
 *            - package contains app + libs + dependencies + envSettings.
 *   FilesInContainer
 *    - WritableContainerLayer( def) though "storageDriver" ( slow, notAccessible outside)
 *    - Volumes - part of HostFileSystem created/managed by Docker (/var/lib/docker/volumes/ on Linux);
 *                can be mounted into multiple containers simultaneously; Volume drivers allow you to store your data
 *                on remote hosts or cloud providers. Allows Linux fileSyst behavior on Win PC.
 *    - BindMounts - shared part of HostFileSystem ( anywhere); very performant, but they rely on the
 *               host machine’s filesystem
 *    - inMemory files: - tmpfs for Linux, - namedPipe for Win
 *
 *  Dockerfile - txtFile - commands to create DockerImage
 *    Dockerfile -> DockerImage -> DockerHub
 *                              -> DockerContainer
 *   Dockerfile:
 *    FROM openjdk:8
 *    WORKDIR /app
 *    ADD
 *    COPY
 *    RUN
 *    EXPOSE 8080
 *    ENTRYPOINT ["java","-jar","myjar.jar"]
 *
 *
 *  docker build -t dock-test-jar .    // Dockerfile in this folder
 *  docker build -t dock-test-jar:1.0 .
 *  mvn spring-boot:build-image        // NO Dockerfile needed - looks better
 *    //https://www.youtube.com/watch?v=1w1Jv9qssqg - Spring Boot 2.3, a new feature was added that enables you
 *    to create Docker Images from your application using Cloud Native Buildpacks
 *
 *  docker run -d -p 8080:8080 --name mycont1 docker-rest-test:0.0.1-SNAPSHOT
 *                                       //curl 192.168.99.100:8080/
 *
 *  docker ps                            // runned container list
 *  docker ps -a                         // runned and stopped container list
 *  docker logs contName
 *  docker inspect contName              // get the internal container ip
 *  docker exec -it contName bash        // exec command in runned container
 *
 *  docker images
 *  docker image ls
 *  docker stop contName
 *  docker rm -f <container>
 *  docker rmi -f <image>
 *
 *  docker pull ubuntu/chiselled-jre:8-22.04_edge
 *
 *  docker volume create myVolume                 // Volume
 *  docker volume list
 *  docker volume inspect myVolume
 *  docker volume rm myVolume
 *  docker run --name imageName -p 3306:3306 -d   // Run with Volume
 *             -e MYSQL_ROOT_PASSWORD=my-secret-pw
 *             --mount source=myVolume,target=/var/lib/mysql
 *         mysql
 *
 *  docker run --name imageName -p 3306:3306 -d   // Run with BindMount
 *             -e MYSQL_ROOT_PASSWORD=my-secret-pw
 *             --mount type=bind,source="$(pwd)"/mysql_bind_folder,target=/var/lib/mysql
 *         mysql
 *  // Win specifically:
 *  docker run --name db
 *    -e MYSQL_ROOT_PASSWORD=somewordpress -e MYSQL_PASSWORD=wordpress -e MYSQL_DATABASE=wordpress -e MYSQL_USER=wordpress
 *    --mount type=bind,source=%cd%/mariadb_data,target=/var/lib/mysql
 *    -d
 *    mariadb:10.6.4-focal
 *    --default-authentication-plugin=mysql_native_password
 *
 *  // Difference between -v and --mount is that :
 *    -v can create a directory if it didn't exist,
 *       -v "$(pwd)"/mariadb_data:/var/lib/mysql
 *    --mount gives an error if the directory doesn't exist
 *       --mount type=bind,source="$(pwd)"mariadb_data,target=/var/lib/mysql
 *
 *
 *  DockerCompose - tool to create/manage/cleanUp multiContainer/multiVolumes/networking apps
 *  DockerComposeFile: compose.yaml(yml) or docker-compose.yaml(yml)
 *  docker compose up - compose.yml -> DockerDemon -run/update-> containers/networking/volumes of App
 *  docker compose up -d   // start containers from compose.yml
 *  docker compose down    // stop containers from compose.yml
 *
 *             compose.yml
 *  services:
 *   db:
 *     image: mariadb:10.6.4-focal
 *     command: '--default-authentication-plugin=mysql_native_password'
 *     volumes:
 *       - mariadb_data:/var/lib/mysql
 *     restart: always
 *     environment:
 *       - MYSQL_ROOT_PASSWORD=somewordpress
 *       - MYSQL_DATABASE=wordpress
 *       - MYSQL_USER=wordpress
 *       - MYSQL_PASSWORD=wordpress
 *     expose:
 *       - 3306
 *       - 33060
 *   wordpress:
 *     image: wordpress:latest
 *     volumes:
 *       - wordpress_data:/var/www/html
 *     ports:
 *       - 8081:80
 *     restart: always
 *     environment:
 *       - WORDPRESS_DB_HOST=db
 *       - WORDPRESS_DB_USER=wordpress
 *       - WORDPRESS_DB_PASSWORD=wordpress
 *       - WORDPRESS_DB_NAME=wordpress
 * volumes:
 *   mariadb_data:
 *   wordpress_data:
 */

 public class ThirdParty {
}
