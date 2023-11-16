package com.peshkoff;
// ________________________________ AWS
/**
 *   Public_Internet Zone <----> AWS_Public Zone <-- InternetGateWay --> AWS_Private Zone
 *                        <------------------------- ( IGW)          --> VPCloud
 *                                  S3                                   EC2
 *
 *   Internet <- InternetGateWay -> |                    VPC
 *                                  |    PublicSubnet              PrivateSubnet
 *                                  |    ELB, NAT        <--->        EC2, DB
 *
 *   HybridConnectivity:    VPC  <- VPN, DirectConnect -> CompanyOnPremise_DataCenter, RemoteWorkers
 *   VPC <-- VPC_Peering( traffic in AWS, 1x1 ONLY!) --> VPC
 *   multiple_VPCs  <--> TransitGateway <- VPN, DirectConnect -> CompanyOnPremise_DataCenter, RemoteWorkers
 *
 *   S3, DynamoDB  <-- | VPC_Endpoint/Gateway( traffic in AWS),| --> EC2
 *   SNS,SQS,SES   <-- | ElasticNetworkInterface               |
 *   CloudWatch    <-- |                                       |
 *     // S3, DynamoDB, SNS,SQS,SES, CloudWatch  and EC2 - in same region !
 *
 *   VPC_Endpoint/Gateway  <-- PrivateLinc( traffic in AWS) --> 3-d party SaaS
 *
 *  SaaS - Soft As A Service
 *  PaaS - platform As A Service ( installed OS + Application)
 *  IaaS - Infrastructure As A Service ( hardware servers + network)
 *
 * Global Services:
 *   Route53( DNS server),
 *   IAM - Identity Access Management
 *      ARN - Amazon Resource Name
 *   Billing
 *   ElasticBeanstalk - WEB Application ( Tomcat, Java, Docker,..); create EC2 instance; autoConfigure by script file
 * Region Services:
 *   SES - SimpleEmailService
 *   SQS - SimpleQueueService: no guarantees FIFO;     PullNotification; 256KB - limit;
 *           RetentionPeriod: [1 min..14days],4days-def
 *           VisibilityTimeOut:[30sek..12Hours],30sek-def; implicit call DeleteMessage
 *         FIFO SQS - guarantees FIFO( more expensive)
 *   SNS - SimpleNotificationService: Pub/Sub, Topics, PushNotifications; Sub-rs: HTTP,SMS,eMail,Lambda,SQS,..
 *
 *   CloudFront - CDN - Content Delivery Network; Cache for statical data; Public entryPoint to S3?
 *   CloudWatch - monitoring of our services/infrastructure
 *   CloudFormation - language to describe/manage AWS resources; infrastructure in code; analog Terraform
 *
 *   RDS - RelationalDB Service ( endPoint to) Oracle, MsSQL, MySQL(noReplication), PostgeSQL(noReplication), Avrora - AmazonPostgres+Replication
 *   DynamoDB - NoSQL DB
 *
 *   VPC - VirtualPrivateCloud
 *   IGW - InternetGateway for PublicSubnets;
 *         from PublicSubnets access to Internet - allowed, from Internet to PrivateSubnet - allowed too
 *   NAT - NetworkAddressTranslation gateway for PrivateSubnets;
 *         from PrivateSubnet access to Internet - allowed, from Internet to PrivateSubnet - disallowed
 *   VGW - VirtualGateway entry point for VPN
 *   Network ACL - Network AccessControlList
 *   CIDR - ClasslessInterDomainRouting ( 10.10.0.0/16 - 10.10-fixed for subnet)
 *
 *   ECR - Elastic Container Registry: analog for Docker Hub
 * AvailabilityZoneServices:
 *   EC2 - Elastic Compute Cloud
 *     AMI - Amazon Machine Image: AmazonLinux, RedHat, Win,.., userImage
 *     SecurityGroup - like AmazonFireWall
 *   RDS
 *   ELB - ElasticLoadBalancer
 *   ALB - Application LoadBalancer
 *
 *
 *
 *  Geographic Areas: America, Canada, Africa, India, Europe
 *  Regions in Europe: Frankfurt, Ireland, London, Milan, Paris, Stockholm
 *  Region contains at least 2 AvailabilityZones( DataCenters) backing up each other
 *
 *  AWS_Account - container for
 *     RootUser(eMail, cardInfo) has FULL control of Users, Resources
 *   - IdentityAccessManagement( IAM): Users( programmaticAccess, AWS_Console_Access), userGroups,
 *                                     Policies/Permissions,
 *                                     Roles: Permissions for entities/instances/servers
 *      - all permissions (full or limited) must be granted directly
 *      - external Identities can be granted access to that Account as well
 *   - Resources: S3_Buckets, EC2, ..
 *
 *  VPCloud - autocreated for account, 1 region, access to all AvailabilityZones in that Region
 *  VPC components: SubNet, RouteTable, FireWall( SecurityGroup + Network AccessControlList)
 *     NetworkAddressTranslation( NAT)
 *
 * AWS Storage
 *   EBS - Elastic Block Storage ( Volume) - lowLatency nonShared storage for EC2, RDS; AvailabilityZone
 *   S3 - Simple Storage Service ( Bucket) - unlimSpace; standalone accessible from Internet by HTTP/HTTPS; Global/Region
 *   EFS - Elastic File System ( FileSystem) - shared for EC2; Region
 *   FSx - EFS for Win ( File System) - shared for EC2; SMB(Samba)Protocol; Region
 *
 * AWS S3
 *     - Standard - data in two dataCenters
 *     - Infrequently Access - data in two dataCenters
 *     - Reduced Redundancy - data in one dataCenter
 *     - AmazonGlacier - archive/fileStorage with big latency (3-5 hours)
 *
 * AWS ELB - Elastic Load Balancer
 *  - ALB - Application Load Balancer: HTTP,HTTPS <--> Lambda, EC2, ECS; NO VPC
 *  - NLB - Network Load Balancer:     TCP, UDP   <--> elastic=static_IP, EC2; ultra-high performance, TLS offloading, centralized certificate deployment
 *  - GWLB - Gateway Load Balancer: ?
 *  - CLB - Classic Load Balancer: for EC2 network
 *  LoadBalancer:
 *  Listener get request ( Protocol:Port) and redirect to TargetGroup( EC2,ECS,IP,Lambda) accordingly to RoutingRules
 *    HTTPS_Listener - can offload TLS encryption/decryption( HTTPS -> HTTP)
 *
 *
 * AWS EC2 - ElasticComputeCloud
 *    - HHS_Client( analog Putty) - MobaXterm
 *    - LinuxCommands:
 *        echo "File content" > /var/www/html/index.html
 *      ls;  ls -l
 *        sudo -i                         // goto root dir
 *        java -version
 *        yum install java-1.8.0-openjdk
 *        alternatives --config java
 *        wget https://res_name           // get file from S3
 *        wget https://s3.eu-north-1.amazonaws.com/first.java.bucket/dock-test.jar
 *        java -jar jar_name.jar
 *      sudo -y yum install httpd         // answer: -y
 *      sudo service httpd start
 *      chkconfig httpd on                // autostart httpd
 *        cd /var/www/html                // index.html
 *        sudo vi index.html
 *        wq!
 *
 * AWS ECS - Elastic Container Service ( EC2 + Docker):
 *           orchestration service to -deploy, -manage, -scale containerized app;
 *           allows to focus on app not environment;
 *           send your container instance log information to CloudWatch Logs
 *  Cluster - logical and regional group of Tasks or ECS Instances; intention - isolate our app;
 *  Instance - EC2 + DockerServer
 *  Task Definition - JSON doc, describes containers (up to 10 containers); analogue of DockerCompose
 *                    DockerContainer/Image  + DRAM + CPU + Ports
 *  Task - instantiation of TaskDefinition; DockerContainer ( runned)
 *  Service - Tasks manager: maintain desired Tasks number, reload terminated/failed Tasks, terminate redundant Tasks; AutoScalingGroup for containers;
 *            creates Tasks on basis: TaskDefinition + serviceDescription
 *  Launch types:
 *  - EC2 LaunchType: EC2 + Docker in Cluster;                        - payment for EC2
 *  - Fargate LaunchType: serverless: WITHOUT EC2 and infrastructure; - payment for runningContainers;
 *                                    NetworkMode for each Container: "AWSVPC"( new NetworkInterface/ip)
 *   TaskDefinition +     |  -> {Task + ElasticNetworkInterface(ip)}_1; ..
 *   Sevice (optionally)  |  -> {Task + ElasticNetworkInterface(ip)}_N
 *
 * AWS ECR - Elastic Container Registry: DockerImages storage; analog for Docker Hub
 *           use aws_cli to login to AWS + docker push imageName:version
 * AWS EKS - Elastic Container Service for Kubernetes
 *
 *
 * AWS Lambda - serverless event-driven compute service;
 *              zero administration, managing, servers for user;
 *              automatic administration, scaling, logging
 *              charging based on request number and time duration of code exec-n( no code running - no charge)
 *
 * DeploymentPackage - Function( Code) - zip/jar or OCI_containerImage ( OpenContainerInitiative)
 * Function URL - dedicated HTTP/S endpoint to my Lambda
 *     Function local storage: "/tmp" directory
 *     Function logs -> CloudWatch Logs; "AWSLambdaBasicExecutionRole" need for that
 * SynchronousInvocation - Trigger( AWS_Service)   -Event( JSON)->   Function( ourCode)
 * AsynchronousInvocation - Trigger( AWS_Service)   -Event( JSON)->  EventQueue  -Event( JSON)->  Function( ourCode)
 * ExecutionEnvironment - isolated runtime env + lifecycle for Function + ExternalExtension
 *             ExecutionEnvironment
 *     Processes          API Endpoints
 *      Function     <---> RuntimeAPI                  <--->  Lambda
 *      ExtExtension <---> ExtensionAPI, TelemetryAPI  <--->  Service
 *  ExecutionEnvironmentLifecycle
 *
 * Layer - additional zip's with lib/dependency/data; yp to 5 per Function;
 *         content -> "/opt" dir in the execution environment
 *         N/A for containers
 * Extension - 3-d_party soft/tools;
 *             - AWS_provided, - own_Lambda_extensions
 *             - internals: in same lifecycle; - externals: separate processes
 * Concurrency - concurrent request - concurrent LambdaInstances; sequent request - same LambdaInstance
 * Qualifier - version/alias "my-func:1" or "my-func:PROD"
 * Destination - AWS resource for messages from Lambda ( errors or success)
 *
 * LambdaFunctionHandlers:
 * import com.amazonaws.services.lambda.runtime.Context, RequestHandler, LambdaLogger
 * public class Handler implements RequestHandler<Map<String,String>, String>    {
 *   public String handleRequest(Map<String,String> event, Context context)  {..}}
 *
 * public class HandlerS3 implements RequestHandler<S3Event, String> {
 *   public String handleRequest(S3Event event, Context context) {..}}
 *
 * public class HandlerStream implements RequestStreamHandler {
 *   public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
 *   {..}                                                     }
 *
 * LambdaFunction by Container
 * - Use BaseLambdaImage( myLambda <-HTTP interface-> AWS Lambda) to build myImage
 * - Upload myImage int ECR
 * - Create/update myFunction with myImage
 *
 * AWS API Gateway - publish, monitor( CloudWatch), secure: AWS Authorization( IAM), AWS Keys(KMS),
 *                   Cache for REST only, trafficManagement - throttling
 *                   payment = callsNumber + traffic;
 *                   for - REST API, - WebSocket API
 *  Req  <->  API Gateway[ Cache, CloudWatch]  <->  API: EC2, ECS, Lambda, anyEndpoints
 *
 * AWS CloudWatch Logs - centralize all logs
 *    LogEvent - Timestamp + eventMessage
 *    LogStream - List<LogEvents> from single logSource: app, resource, ..
 *    LogGroup - List<LogStream>
 *    LogEvents -MetricFilter-> CloudWatch metric
 *    Retention settings - expiration settings for logs
 *
 * AWS CloudFormation ?
 * AWS KMS - Key Management Service
 *
 *
 * **/

public class AWS {
}
