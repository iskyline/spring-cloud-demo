# SpringCloud系列(Greenwich.SR4)之二：服务注册与发现Eureka(上)

[源码 Gitee：https://gitee.com/iskyline/spring-cloud-demo](https://gitee.com/iskyline/spring-cloud-demo)

[源码GitHub：https://github.com/iskyline/spring-cloud-demo](https://github.com/iskyline/spring-cloud-demo)

#### 1. 版本环境
1. Spring Cloud： Greenwich.SR4
2. JDK：1.8 
3. Spring Boot：2.1.5.RELEASE
4. 开发工具： IntelliJ idea
5. Windows10 
6. 构件工具：Maven

#### 2. 详细说明
本节目标是搭建带有两个子服务，有注册中心，子服务能够互相调用的微服务群。

###### 2.1. 背景介绍
微服务的第一步是根据需求拆分服务，降低各个服务间的耦合。微服务中的服务会拆分的很`微小`，这样产生一些需要解决的问题：
- 各个微小的服务之间如何通信
- 如何找到需要的服务
- 服务不可用了怎么办
- 子服务部署成集群了怎么调用
- 如果调用的子服务还调用另外的服务，调用路径怎么跟踪
- 服务可以给任何发现的人调用吗
- 。。。等其他问题

那么先来学习下拆分出来的微服务的注册中心吧。所有的服务都注册到这里，其他服务从注册中心各取所需。这个像不像菜市场。^_^
Spring Cloud的服务注册与发现能够支持Eureka、Zookeeper、Consul等。

###### 2.2. 简介Eureka
Eureka是`Spring Cloud Netflix`众多组件中的一个。
`Spring Cloud Netflix`是在Netflix公司经过实战检验的Netflix组件开源项目基础上封装而成，
依托spring boot环境，可以快速启用和配置应用程序中的常见模式，构建大型分布式系统。
`Spring Cloud Netflix`提供的模式包括服务注册与发现(Eureka)、断路器(Hystrix)、智能路由(Zuul)和客户端负载平衡(Ribbon)等。

Eureka分两部分：
- Service Discovery: Eureka Server
    
  负责子服务的管理、提供了REST服务
  
- Service Discovery: Eureka Clients

  负责将服务注册到server端

实际来写一个Demo，看看基于Spring Cloud Netflix搭建服务注册发现、服务通信的微服务有多简单。

#### 3. 项目目录结构
项目采用模块方式管理各个子服务

本分支中会有两部分：服务发现和注册Eureka和子服务。
子服务会有两个，一个提供方法(服务提供者provider)，一个去调用(customer)。provider和customer并不是绝对的，每个服务都可能调用其他服务，或者提供给其他服务调用。

1. 注册中心，本分支使用Eureka
    
    模块名称：spring-discover-eureka
    
2. 拆分的微服务
    
    模块-提供者：spring-provider 
    
    模块-消费者：spring-customer

``` 
│  .gitignore
│  LICENSE
│  pom.xml
│  README.md
│  spring-cloud-demo.iml
│
├─.idea
│  │  compiler.xml
│  │  encodings.xml
│  │  misc.xml
│  │  modules.xml
│  │  vcs.xml
│  │  workspace.xml
│  │
│  ├─artifacts
│  │      spring_discover_eureka_war.xml
│  │      spring_discover_eureka_war_exploded.xml
│  │
│  └─libraries
│          Maven__antlr_antlr_2_7_7.xml
│          ***
│
├─spring-customer
│  │  pom.xml
│  │  spring-customer.iml
│  │
│  └─src
│      └─main
│          ├─java
│          │  └─com
│          │      └─iskyline
│          │          └─demo
│          │              └─springcloud
│          │                  └─customer
│          │                      │  SpringCustomerApplication.java
│          │                      │
│          │                      └─controller
│          │                              package-info.java
│          │
│          └─resources
│                  application.yml
│
├─spring-discover-eureka
│  │  pom.xml
│  │  spring-discover-eureka.iml
│  │
│  └─src
│      └─main
│          ├─java
│          │  └─com
│          │      └─iskyline
│          │          └─demo
│          │              └─springcloud
│          │                  └─eureka
│          │                          SpringEurekaApplication.java
│          │
│          └─resources
│                  application.yml
│
└─spring-provider
    │  pom.xml
    │  spring-provider.iml
    │
    └─src
        └─main
            ├─java
            │  └─com
            │      └─iskyline
            │          └─demo
            │              └─springcloud
            │                  └─provider
            │                      │  SpringProviderApplication.java
            │                      │
            │                      └─controller
            │                              package-info.java
            │
            └─resources
                    application.yml

```

#### 4. 创建项目和模块
idea中创建项目以及项目下的模块有多重方法，非常方便。不详细介绍了，如果有需求可以私信我。

从上面项目结构介绍中可以知道, spring-cloud-demo中的pom.xml是父pom，子模块去继承

**完整代码请参照Gitee或者Github仓库中spring-cloud-demo项目**

###### 4.1. 最外层：spring-cloud-demo

> spring cloud依赖

注意`spring-cloud-dependencies`的版本是`Greenwich.SR4`
```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <spring-cloud-dependencies.version>Greenwich.SR4</spring-cloud-dependencies.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud-dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

> 因为spring cloud使用的版本是 `Greenwich.SR4`,根据官网版本关系可知，spring boot 应该选择2.1.*
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.5.RELEASE</version>
    <relativePath/>
</parent>
```

> 子模块管理
``` 
<modules>
    <module>spring-discover-eureka</module>
    <module>spring-provider</module>
    <module>spring-customer</module>
</modules>
```

- pom中引入`spring-boot-maven-plugin`，这样不用在每个子服务中添加了
```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

###### 4.2. 模块：服务注册与发现 spring-discover-eureka

> pom文件主要内容
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

> Application类
``` 
@EnableEurekaServer
@SpringBootApplication
public class SpringEurekaApplication {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        SpringApplication.run(SpringEurekaApplication.class,args);
    }

}
```

> application.yml配置
``` 
server:
  port: 6000  # 端口

spring:
  application:
    #如果不指定本名称，Eureka Server的管理页面显示UNKNOWN
    name: spring-discovery-eureka

eureka:
  instance:
    hostname: localhost
  client:
    #自身是否作为子服务进行注册
    registerWithEureka: false
    #是否获取注册列表
    fetchRegistry: false
    serviceUrl:
      #注册地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

到这一步可以启动`SpringEurekaApplication`，正常启动信息如下：
``` 
Connected to the target VM, address: '127.0.0.1:53571', transport: 'socket'
[]
2019-12-31 20:19:04.174  INFO 73924 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration' of type [org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration$$EnhancerBySpringCGLIB$$23387db4] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.5.RELEASE)

2019-12-31 20:19:04.480  INFO 73924 --- [           main] c.i.d.s.eureka.SpringEurekaApplication   : No active profile set, falling back to default profiles: default
2019-12-31 20:19:05.052  WARN 73924 --- [           main] o.s.boot.actuate.endpoint.EndpointId     : Endpoint ID 'service-registry' contains invalid characters, please migrate to a valid format.
2019-12-31 20:19:05.231  INFO 73924 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=53e4d3fd-6df2-3971-b4e4-4217dc3dca51
2019-12-31 20:19:05.330  INFO 73924 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration' of type [org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration$$EnhancerBySpringCGLIB$$23387db4] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2019-12-31 20:19:05.543  INFO 73924 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 6000 (http)
2019-12-31 20:19:05.565  INFO 73924 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-12-31 20:19:05.565  INFO 73924 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.19]
2019-12-31 20:19:05.697  INFO 73924 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-12-31 20:19:05.697  INFO 73924 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1207 ms
2019-12-31 20:19:05.778  WARN 73924 --- [           main] c.n.c.sources.URLConfigurationSource     : No URLs will be polled as dynamic configuration sources.
2019-12-31 20:19:05.778  INFO 73924 --- [           main] c.n.c.sources.URLConfigurationSource     : To enable URLs as dynamic configuration sources, define System property archaius.configurationSource.additionalUrls or make config.properties available on classpath.
2019-12-31 20:19:05.787  INFO 73924 --- [           main] c.netflix.config.DynamicPropertyFactory  : DynamicPropertyFactory is initialized with configuration sources: com.netflix.config.ConcurrentCompositeConfiguration@3b009e7b
2019-12-31 20:19:06.521  INFO 73924 --- [           main] c.s.j.s.i.a.WebApplicationImpl           : Initiating Jersey application, version 'Jersey: 1.19.1 03/11/2016 02:08 PM'
2019-12-31 20:19:06.576  INFO 73924 --- [           main] c.n.d.provider.DiscoveryJerseyProvider   : Using JSON encoding codec LegacyJacksonJson
2019-12-31 20:19:06.576  INFO 73924 --- [           main] c.n.d.provider.DiscoveryJerseyProvider   : Using JSON decoding codec LegacyJacksonJson
2019-12-31 20:19:06.663  INFO 73924 --- [           main] c.n.d.provider.DiscoveryJerseyProvider   : Using XML encoding codec XStreamXml
2019-12-31 20:19:06.663  INFO 73924 --- [           main] c.n.d.provider.DiscoveryJerseyProvider   : Using XML decoding codec XStreamXml
2019-12-31 20:19:06.965  WARN 73924 --- [           main] c.n.c.sources.URLConfigurationSource     : No URLs will be polled as dynamic configuration sources.
2019-12-31 20:19:06.965  INFO 73924 --- [           main] c.n.c.sources.URLConfigurationSource     : To enable URLs as dynamic configuration sources, define System property archaius.configurationSource.additionalUrls or make config.properties available on classpath.
2019-12-31 20:19:07.111  INFO 73924 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-12-31 20:19:07.657  INFO 73924 --- [           main] o.s.c.n.eureka.InstanceInfoFactory       : Setting initial instance status as: STARTING
2019-12-31 20:19:07.680  INFO 73924 --- [           main] com.netflix.discovery.DiscoveryClient    : Initializing Eureka in region us-east-1
2019-12-31 20:19:07.680  INFO 73924 --- [           main] com.netflix.discovery.DiscoveryClient    : Client configured to neither register nor query for data.
2019-12-31 20:19:07.686  INFO 73924 --- [           main] com.netflix.discovery.DiscoveryClient    : Discovery Client initialized at timestamp 1577794747685 with initial instances count: 0
2019-12-31 20:19:07.740  INFO 73924 --- [           main] c.n.eureka.DefaultEurekaServerContext    : Initializing ...
2019-12-31 20:19:07.742  WARN 73924 --- [           main] c.n.eureka.cluster.PeerEurekaNodes       : The replica size seems to be empty. Check the route 53 DNS Registry
2019-12-31 20:19:07.756  INFO 73924 --- [           main] c.n.e.registry.AbstractInstanceRegistry  : Finished initializing remote region registries. All known remote regions: []
2019-12-31 20:19:07.756  INFO 73924 --- [           main] c.n.eureka.DefaultEurekaServerContext    : Initialized
2019-12-31 20:19:07.764  INFO 73924 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 2 endpoint(s) beneath base path '/actuator'
2019-12-31 20:19:07.832  INFO 73924 --- [           main] o.s.c.n.e.s.EurekaServiceRegistry        : Registering application SPRING-DISCOVERY-EUREKA with eureka with status UP
2019-12-31 20:19:07.834  INFO 73924 --- [      Thread-19] o.s.c.n.e.server.EurekaServerBootstrap   : Setting the eureka configuration..
2019-12-31 20:19:07.834  INFO 73924 --- [      Thread-19] o.s.c.n.e.server.EurekaServerBootstrap   : Eureka data center value eureka.datacenter is not set, defaulting to default
2019-12-31 20:19:07.835  INFO 73924 --- [      Thread-19] o.s.c.n.e.server.EurekaServerBootstrap   : Eureka environment value eureka.environment is not set, defaulting to test
2019-12-31 20:19:07.843  INFO 73924 --- [      Thread-19] o.s.c.n.e.server.EurekaServerBootstrap   : isAws returned false
2019-12-31 20:19:07.843  INFO 73924 --- [      Thread-19] o.s.c.n.e.server.EurekaServerBootstrap   : Initialized server context
2019-12-31 20:19:07.843  INFO 73924 --- [      Thread-19] c.n.e.r.PeerAwareInstanceRegistryImpl    : Got 1 instances from neighboring DS node
2019-12-31 20:19:07.843  INFO 73924 --- [      Thread-19] c.n.e.r.PeerAwareInstanceRegistryImpl    : Renew threshold is: 1
2019-12-31 20:19:07.843  INFO 73924 --- [      Thread-19] c.n.e.r.PeerAwareInstanceRegistryImpl    : Changing status to UP
2019-12-31 20:19:07.851  INFO 73924 --- [      Thread-19] e.s.EurekaServerInitializerConfiguration : Started Eureka Server
2019-12-31 20:19:07.869  INFO 73924 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 6000 (http) with context path ''
2019-12-31 20:19:07.870  INFO 73924 --- [           main] .s.c.n.e.s.EurekaAutoServiceRegistration : Updating port to 6000
2019-12-31 20:19:08.040  INFO 73924 --- [           main] c.i.d.s.eureka.SpringEurekaApplication   : Started SpringEurekaApplication in 4.682 seconds (JVM running for 6.67)
2019-12-31 20:19:08.485  INFO 73924 --- [3)-192.168.56.1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2019-12-31 20:19:08.485  INFO 73924 --- [3)-192.168.56.1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2019-12-31 20:19:08.493  INFO 73924 --- [3)-192.168.56.1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 8 ms
```

此时浏览器中输入：http://localhost:6000/ ，可以看到server的状态页面


###### 4.3. 模块：spring-provider

> pom.xml
``` 
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

> Application启动类

> application.yml 配置

> api接口

###### 4.4. 模块：spring-customer

> pom.xml
``` 
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

> Application启动类

> application.yml 配置

> api接口

#### 5. 总结
Spring Cloud Netflix 中提供了多个组件：服务发现(Eureka)、断路器(Hystrix)、智能路由(Zuul)和客户端负载平衡(Ribbon)。用这些可以快速构件一个大型微服务。
但是有一个问题：为什么需要Eureka，服务之间直接通过智能路由Zuul或者Gateway进行服务的路由，或者Ribbon进行负载均衡不就行了吗？就好像服务+Nginx