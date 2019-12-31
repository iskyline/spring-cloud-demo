# SpringCloud系列(Greenwich.SR4)之二：服务注册与发现Eureka(上)

[源码 Gitee：https://gitee.com/iskyline/spring-cloud-demo](https://gitee.com/iskyline/spring-cloud-demo)

[源码GitHub：https://github.com/iskyline/spring-cloud-demo](https://github.com/iskyline/spring-cloud-demo)

#### 1. 版本环境
1. Spring Cloud： Greenwich.SR4
2. JDK：1.8 
3. Spring Boot：2.1.5.RELEASE
4. 开发工具： IntelliJ idea
5. Windows10 

#### 2. 详细说明

###### 2.1. 背景介绍
微服务的第一步是根据需求拆分服务，降低各个服务间的耦合。微服务中的服务会拆分的很`微小`，这样产生一些需要解决的问题：
- 各个微小的服务之间如何通信
- 如何找到需要的服务
- 服务不可用了怎么办
- 子服务部署成集群了怎么调用
- 如果调用的子服务还调用另外的服务，调用路径怎么跟踪
- 服务可以给任何发现的人调用吗
- 。。。等其他问题

那么先来学习下拆分出来的微服务的一个集中注册中心吧。所有的服务都注册到这里，其他服务从注册中心各取所需。这个像不像菜市场。^_^

###### 2.2. 简介Eureka



#### 3. 目录结构
项目采用模块方式管理各个子服务

本分支中会有两部分：服务发现和注册Eureka和子服务。
子服务会有两个，一个提供方法(服务提供者provider)，一个去调用(customer)。provider和customer并不是绝对的，每个服务都可能调用其他服务，或者提供给其他服务调用。

1. 注册中心，本分支使用Eureka
    
    模块名称：spring-discover-eureka
    
2. 拆分的微服务
    
    模块-提供者：spring-provider 
    
    模块-消费者：spring-customer


**结构如下：**

#### 4. 创建项目和模块
idea中创建项目以及项目下的模块非常方便。不详细介绍了，如果有需求可以私信我。

从上面的结构中可以看出, spring-cloud-demo中的pom.xml是父pom，子模块去继承

**完整代码请参照 开头写明的Gitee或者Github的仓库地址**

> spring-cloud-demo中项目父POM文件
- 因为spring cloud使用的版本是 `Greenwich.SR4`，所以spring boot 选择了2.1.5
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/>
    </parent>
```

- 编译和统一版本配置信息
``` 
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

- 子模块管理
``` 
<modules>
    <module>spring-discover-eureka</module>
    <module>spring-provider</module>
    <module>spring-customer</module>
</modules>
```

- 依赖包
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

- 父pom中引入plugin，这样不用在每个子服务中添加了
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

> 服务注册与发现spring-discover-eureka 的pom文件主要内容
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```
这个配置就是这么简单，注意`artifactId`,其他spring cloud版本可能不是这个名称。
这其实也是一个子服务，作用就是服务的注册和发现。当然也要高可用，可以集群部署。下次会详细说集群中的配置。

> 子服务provider和customer的pom文件主要内容
``` 
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

