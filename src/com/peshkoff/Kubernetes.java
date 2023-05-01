package com.peshkoff;
// ________________________________ Kubernetes
// https://www.youtube.com/watch?v=gMmcRbd8L5Y  JavaTechie K8s article
// https://www.youtube.com/watch?v=jfqa7lRXDBI   Kubernetes: Ingress, Service, PV, PVC, ConfigMap, Secret. Вечерняя школа Слёрма по Kubernetes.
/** Kubernetes - opensource platform to automatically deploy, manage, scale containers in high-loaded distributed system.;
 *               facilitate declarative config and automation;
 *               Kubernetes originates from Greek, meaning helmsman(рулевой) or pilot
 *   + effective using of hardwareResources and infrastructure ( Up/DownContainers ~ load)
 *   + less human resources - less admins needed due to automation
 *  K8s provides:
 *  - ServiceDiscovery_LoadBalancer using DNS names or IPs
 *  - StorageOrchestration - automatically mount storages: local, cloud, ..
 *  - Automated Rollout and Rollback - actualState -> desiredState at controlledRate
 *  - Automated Bin packing - spec CPU, RAM fo containers
 *  - SelfHealing - restart, stop containers, healthCheck
 *  - Secret and ConfigManagement - store, manage sensitive info: passwords, OAuthTokens, SSH keys
 *  - Horizontal scaling
 *
 *  Kubernetes Cluster Components:
 *  - Cluster: set of worker machines/Nodes + MasterNode
 *  - Node/Worker - VM/HardwareServer + Docker + [KUBELET(agent) + KUBE-PROXY]; hosts Pods
 *     - kubelet - agent runs on each Node; manages( runs, healthChecks) Containers described in PodSpecs; can self-register in ControlPlane
 *     - KUBE-PROXY - network proxy runs on each Node, load balancing between Pods in Node, routing - traffic forward between Pods and outside
 *     - ContainerRuntime - soft runs Containers( typically -Docker); RuntimeClass defunes particular container runtime
 *  - ControlPlane - (MasterNode) manages the K-sCluster; orchestration layer to manage WorkerNodes and Pods;
 *                   ControlPlaneComponents - scheduling, starting new Pods, ..
 *                   AdminUI, CLI -REST-> ControlPlane -> Nodes:[ Pod1[ Con-r1, Con-r2], .., Podn[ Con-r]]
 *    ControlPlaneComponents started on separate machine:
 *    - apiServer - API to change Cluster state (HTTP + JSON/PROTOBUF); horizontally scalable
 *    - ETCD - key-value DB persists state of system; Secrets&ConfigMap stored here as well
 *    - SCHEDULER - select Node( by CPU/DRAM usage) to start new Pods;
 *    - CONTROLLER-MANAGER - track K8sCluster state, runs Controllers:
 *             NodeController, ReplicationController, JobController( Pods, Tasks),
 *             EndPointSliceController(Service <-Link-> Pods),
 *             ServiceAccountController( create serviceAccount for new NameSpaces), ..
 *             Controller - makes change curState -> desiredState according apiServer info
 *    - cloudController-Manager - cloud specific logic of Cluster: link Cluster to cloudAPI, ..
 *    ETCD, SCHEDULER, CONTROLLER-MANAGER   <-  GRPC/PROTOBUF  ->  API_Server
 *
 * Objects - needed to detach of infrastructure; "recordOfIntent";
 *           Object = { spec:   desiredState( description/characteristic),
 *                      status: currentState( description/characteristic)}
 * - Pod - "logical host"; group 1..n logically tied Containers with shared storage and network resources;
 *         all Containers co-located and co-scheduled to run on same node in shared context;
 *         has clusterIP "IP-per-pod" model.; min controllable entity
 * - ReplicaSet - (obsolete ReplicationController) POD + Container(POD)Number(autoscale system)
 * - Dwployment - to manage Pods, scale/update runningApp; for stateless/interchangeable Pods/workload
 *                REPLICASET = strategy of PODs update:
 *                -recreate: delete all old PODs then create new PODs,
 *                -rollingUpdate: { delete old POD, create new POD} - update one after one
 *                -customStrategy
 *                 kubectl create deployment my-depl --image=doc-image --port=8080 --replicas=4
 * - StatefulSet - for Pods with persistence; Pods matches PersistentVolume
 * - PersistentVolume - independent resource like DockerVolume;
 * - PersistentVolumeClaim - request to mount PV;
 *                           accessModes : [ReadWriteOnce(single POD),ReadWriteMany(many PODs)]
 * - StorageClass - parameters to access to storage ( URL, login/password,..); StorageClass: NFS, RBD,..
 * - PV_Provisioner - by PVClain autoCreates PVs of specific size and type and accessModes:ReadWriteMany
 * - Service - netInterface, loadBalancer, serverSideServiceDiscovery for PODs; APIGateway;
 *             outside router for PODs: HTTP <-> POD <-> outsideDB
 *             parameters: REPLICA SET + portMapping for input traffic
 *             ServiceTypes:
 *             -ClusterIP( def) - IP inside K8sCluster,
 *             -NodePort - somePort for all workerNodes,
 *             -ExternalName - DNS CNAME Record
 *             -LoadBalancer - for cloudCluster only(AWS, AZURE,GC); able provide outside DNS name
 * - Ingress - API object(configuration) maps external HTTP/HTTPS requests to Services;
 *             act in connection with CloudLoadBalancer;
 *           - accepts HTTP/HTTPS traffic and dispatch it to Services; reverseProxy?
 *           - URL-specific dispatcher of requests + TLS/HTTPS description/termination
 *   - IngressController - real Software fulfilling Ingress functionality;
 *                         usually based on proxy-server(Enginx,..) or CloudLoadBalancer;
 *                         in reality direct traffic on PODs on basis of ServiceInfo + IngressRules
 *    Request -> Ingress -> Sewrvice_i(APIGateway) -> Pod_j
 *    // qwe.com:80        -> serviceName_1:port_1        - IngressRules examples
 *    // qwe.com:80/path   -> serviceName_2:port_2/path
 *    // qwe.com:80/path_1 -> serviceName_2:port_2/path_2 - redirect
 *                          Provider( AWS)                      Our_K8s_Cluster
 *    Request -> SecurityGateWay -> LoadBalancer ->  Ingress -> Sewrvice_i(APIGateway) -> Pod_j
 * - HorizontalPodAutoscaler - autoScale Deployment.name (or Pods ?)
 *                             in range [ minReplicas:2; maxreplicas:6]
 *                             according to metrics [ cpu : 80% or memory : 70%]
 * - Job - runs Pod(s) to carry out task and then stop. Job is one-off task
 * - CronJob - Job with schedule
 * - Secret, ConfigMap - K8s stores sensitive info; data stored in ETCD; all - key-value view
 * - Secret - encryptedText( passwords),
 * - ConfigMap - plain text;
 * - DaemonSet
 * - Lease // https://kubernetes.io/docs/concepts/architecture/leases/
 * - apiVersion - way to categorize K8s api
 *
 * Volumes - basically is a directory; to spec volumes: ".spec.volumes" + ".spec.containers[*].volumeMounts"
 *  - ephemeral - default, lifetime as its Pod( not Container)
 *  - PersistentVolume - K8sObject wih independent lifecycle; NFS, iSCSI, cloudProvider-specific( AWS EBS)
 *  - PersistentVolumeClaim - request for storage/partOfPV of Cluster; specific size, accessMode [ReadWriteOnce, ReadOnlyMany,ReadWriteMany]
 *
 * Namespace - to isolate groups of resources in Cluster. Names in Namespace are unique. Need in BIG projects
 *   NamespacedObjs - Deployment, Service, Pod, ReplicationController
 *   Cluster-wideObjs - StorageClass, Node, PersistentVolume,..
 *
 * Manifest - YAML/JSON doc to describe Objects;
 *            Deployment.yml - infrastructure description; IaaC - Infrastructure as a Code
 *   + infrastructure versions and change tracking
 *   + fast and reliable deploy
 * metadata:
 *     name: API_group/resType/NameSpace/Name - unique for all API_versions;
 *           each name: 256 symbol lim, lowerCase + "-"/".", start/end with alphanumeric only.
 *    label: "env : production" - key/val for grouping, UI/CLI;
 *           "=(==)"/"!=" - aplicable; nodeSelector:
 *                                      accelerator: nvidia-tesla-p100
 *           "in"/"notin"/"exists": "env in ( prod, dev)"
 *           kubectl get pods -l environment=production,tier=frontend
 *           kubectl get pods -l 'environment in (production),tier in (frontend)'
 *    annotations:      metadata like a labels but for 3-d party tools
 *         key: value
 *
 * K8s API access - kubectl (CLI), ClientLibraries for diff languages.
 *   kubectl - console util to control Objects and Manifests
 *
 * Minikube - appl runs single-node Cluster on local PC.
 *
 * Management techniques:
 * - Imperative control of LiveObject using cubectl (create, replace, delete,..)
 *    kubectl create -f nginx.yaml
 * - Declarative control of cfgFiles or Directories of cfgFiles
 *    kubectl diff -f -R(ecursively) configs/
 *    kubectl apply -f -R(ecursively) configs/
 *    kubectl apply -f Service.yaml --record
 *
 *   kubectl get nodes -o wide                   // print nodes
 * kubectl apply -f deploymentFileName.yaml      // run deployment from file
 * kubectl create deployment my-depl --image=doc-image --port=8080 --replicas=2 // create&run deployment
 * kubectl scale deployment  my-depl --replicas=4
 * kubectl get deployments                       // print deployments
 * kubectl get pods                              // print pods
 * kubectl logs <podName>
 *     kubectl apply -f serviceFileName.yaml     // run service from file
 *        // createService to get loadBalancedAccess to all Nodes in my-depl: serviceIP:port
 *     kubectl expose deployment my-depl --type=ClusterIP --port 80
 *       // createService to open somePort on all Nodes in my-depl: nodeIP:somePort
 *     kubectl expose deployment my-depl --type=NodePort --port 80
 *       // createService to get AWSLoadBalancerAcess to all Nodes in my-depl: externalIP:port
 *     kubectl expose deployment my-depl --type=LoadBalancer --port 80
 *     keubectl get services
 *     keubectl get services -n namespaceName
 * kubectl exec -it my-pod /bin/bash             // run integeratedTerminal on my-pod and connect to it
 *     kubectl apply -f my-configMap.yaml        // deploy configMap or secrets values
 *     kubectl get configmap
 *     kubectl get secrets
 * kubectl delete service srv-name
 * kubectl delete deployment dpl-name
 *    kubectl get ingress
 *    kubectl describe ingress                   // get with details
 *
 *   Pod Manifest
 * apiVersion: v1
 * kind: Pod
 * metadata:
 *   name: nginx-demo
 * spec:
 *   containers:
 *   - name: nginx
 *     image: nginx:1.14.2
 *     ports:
 *     - containerPort: 80
 *
 *    Deployment.yaml Manifest
 * apiVersion: apps/v1
 * kind: Deployment # Kubernetes resource kind we are creating
 * metadata:
 *   name: spring-boot-k8s
 * spec:
 *   selector:
 *     matchLabels:
 *       app: spring-boot-k8s
 *   replicas: 2 # Number of replicas that will be created for this deployment
 *   template:
 *     metadata:
 *       labels:
 *         app: spring-boot-k8s
 *     spec:
 *       containers:
 *         - name: spring-boot-k8s
 *           image: springboot-k8s-example:1.0 # Image that will be used to containers in the cluster
 *           imagePullPolicy: IfNotPresent
 *           ports:
 *             - containerPort: 8080 # The port that the container is running on in the cluster
 *
 *    Service.yaml Manifest
 * apiVersion: v1 # Kubernetes API version
 * kind: Service  # Kubernetes resource kind we are creating
 * metadata:
 *   name: springboot-k8s-svc
 * spec:
 *   selector:
 *     app: spring-boot-k8s  # for PODs( not Deployment)  !!!
 *   ports:
 *     - protocol: "TCP"
 *       port: 8080       # The port that the service is running on in the cluster
 *       targetPort: 8080 # The port exposed by the service
 *   type: NodePort       # type of the service.
 *
 *   Job Manifest example
 * apiVersion: batch/v1
 * kind: Job
 * metadata:
 *   name: hello
 * spec:
 *   template:
 *                  # This is the pod template
 *     spec:
 *       containers:
 *       - name: hello
 *         image: busybox:1.28
 *         command: ['sh', '-c', 'echo "Hello, Kubernetes!" && sleep 3600']
 *       restartPolicy: OnFailure
 *                 # The pod template ends here
 *
 *
 * HELM - package manager for K8s; CLI; + variables for Manifests in separate file(s)
 */
public class Kubernetes {
}
