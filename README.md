# xudanrw
一个灵活的读写分离组件

![Image text](https://github.com/chenjy16/xudanrw/xudanrw.png)





1、组件说明：

本组件适用于一主多从数据库读写分离,功能如下:

    1)针对mysql replication机制进行的数据主备复制，可以直接使用group datasource来支持读写分离。读写分离支持权重设置，允许对不同库使用不同的权重；

    2)一台数据库挂掉后，如果是个fatal exception(有定义)，那么会进入读重试，以确保尽可能多的数据访问可以在正常数据库中访问；

    3)使用try – lock机制来进行线程保护，在第一次捕捉到fatal exception以后，只允许一个线程进入数据库进行数据访问，直到数据库可以正常的工作为止；

    4)流量控制,数据库保护;

    5) 指定数据库访问(ThreadLocal),一组对等数据库中,写库一般只配置一个,其余数据库都为备库,因为通过复制机制,所以主备主键有延迟,对于各种类型的读(实时读和延迟读),

     可以使用GroupDataSourceRouteHelper.executeByGroupDataSourceIndex(int dataSourceIndex)指定需要访问的数据库;

    6)指定数据库访问(Hint),这是指定数据库访问的另外一种方式. 这种方式是在sql之前加注释,告知tddl动态数据源该选择第几个数据库.类似:/*+TDDL_GROUP({groupIndex:12})*/select * from tab  ;




2、依赖组件:

  <properties>

    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    <spring.version>4.3.7.RELEASE</spring.version>

  </properties>

 <dependencies>

        <dependency>

            <groupId>org.springframework</groupId>

            <artifactId>spring-context-support</artifactId>

            <version>${spring.version}</version>

        </dependency>

        <dependency>

            <groupId>org.springframework</groupId>

            <artifactId>spring-aop</artifactId>

            <version>${spring.version}</version>

        </dependency>

        <dependency>

            <groupId>org.springframework</groupId>

            <artifactId>spring-tx</artifactId>

            <version>${spring.version}</version>

        </dependency>

        <dependency>

            <groupId>org.springframework</groupId>

            <artifactId>spring-jdbc</artifactId>

            <version>${spring.version}</version>

        </dependency>

        <dependency>

            <groupId>org.aspectj</groupId>

            <artifactId>aspectjweaver</artifactId>

            <version>1.7.2</version>

        </dependency>

        <dependency>

            <groupId>org.slf4j</groupId>

            <artifactId>slf4j-api</artifactId>

            <version>1.7.6</version>

        </dependency>

       <dependency>

            <groupId>ch.qos.logback</groupId>

            <artifactId>logback-classic</artifactId>

            <version>1.1.2</version>

        </dependency>

  </dependencies>


3、组件配置：

  首先配置好写库和读库的数据源:
            
              
               <bean id="writeDataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
                    <property name="driverClassName" value="${database.core.write.driverClassName}" />
                    <property name="url" value="${database.core.write.url}" />
                    <property name="username" value="${database.core.write.username}" />
                    <property name="password" value="${database.core.write.password}" />
                    <property name="maxActive"><value>20</value></property>   
                    <property name="initialSize"><value>5</value></property>   
                    <property name="maxWait"><value>30000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
                    <property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
                    <property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
                    <property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
                    <property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
                    <property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->   
                    <property name="testOnBorrow"><value>true</value></property>   
                    <property name="testOnReturn"><value>false</value></property>   
                    <property name="validationQuery"><value>select 1</value></property>          
                    <property name="numTestsPerEvictionRun"><value>20</value></property>  
                    <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>        
                </bean>
    
    
          
             <bean id="readDataSource1" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
              <property name="driverClassName" value="${database.core.read1.driverClassName}" />
              <property name="url" value="${database.core.read1.url}" />
              <property name="username" value="${database.core.read1.username}" />
              <property name="password" value="${database.core.read1.password}" />
              <property name="maxActive"><value>20</value></property>   
              <property name="initialSize"><value>5</value></property>   
              <property name="maxWait"><value>30000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
              <property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
              <property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
              <property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
              <property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
              <property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->   
              <property name="testOnBorrow"><value>true</value></property>   
              <property name="testOnReturn"><value>false</value></property>   
              <property name="validationQuery"><value>select 1</value></property>          
              <property name="numTestsPerEvictionRun"><value>20</value></property>  
              <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>        
          </bean>
    
    
                <bean id="readDataSource2" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
                 <property name="driverClassName" value="${database.core.read2.driverClassName}" />
                 <property name="url" value="${database.core.read2.url}" />
                 <property name="username" value="${database.core.read2.username}" />
                 <property name="password" value="${database.core.read2.password}" />
                 <property name="maxActive"><value>20</value></property>   
                 <property name="initialSize"><value>5</value></property>   
                 <property name="maxWait"><value>30000</value></property>        <!-- 超时等待时间  以毫秒为单位 -->
                 <property name="maxIdle"><value>20</value></property>            <!-- 最大空闲连接 -->
                 <property name="minIdle"><value>5</value></property>             <!-- 最小空闲连接 -->
                 <property name="removeAbandoned"><value>true</value></property>  <!-- 是否自动回收超时连接 -->
                 <property name="removeAbandonedTimeout"><value>30</value></property>  <!-- 超时时间(以秒数为单位) -->
                 <property name="testWhileIdle"><value>true</value></property>    <!-- 打开检查,用异步线程evict进行检查 -->   
                 <property name="testOnBorrow"><value>true</value></property>   
                 <property name="testOnReturn"><value>false</value></property>   
                 <property name="validationQuery"><value>select 1</value></property>          
                 <property name="numTestsPerEvictionRun"><value>20</value></property>  
                 <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>        
             </bean>
    


然后将这些数据源注入动态数据源：

            
             <!-- 读写分离配置 -->
             <bean id="dsconfDO" class="com.midea.trade.rws.util.DsConfDO">
                  <property name="writeRestrictTimes" value="0"/><!-- 时间范围内写限制次数 -->
                  <property name="readRestrictTimes" value="0"/><!-- 时间范围内读限制次数 -->
                  <property name="timeSliceInMillis" value="0"/><!-- 时间范围不能小于1000ms -->
                  <property name="maxConcurrentReadRestrict" value="0"/><!-- 最大并发读限制 -->
                  <property name="maxConcurrentWriteRestrict" value="0"/><!-- 最大并发写限制 -->
              </bean>  
                 <bean id="fetcher" class="com.midea.trade.rws.util.SpringDataSourceFetcher"/>
                 <bean id="groupDataSource" class="com.midea.trade.rws.group.TGroupDataSource">
                  <constructor-arg name="dsKeyAndWeightCommaArray" value="writeDataSource:wq1,readDataSource1:rp3,readDataSource2:rp3"/>  
                  <constructor-arg ref="fetcher"/>
                  <constructor-arg ref="dsconfDO"/>
             </bean>


 

配置的规则如下:

         首先根据p或q的优先级来决定是读库还是写库还是读写都有，然后根据r或w决定读库或写库被选中的概率。
      
         例子如下：
      
         如：db1: r10w10p2, db2: r20p2, db3: rp3，则对应如下三个Weight:
      
         db1: Weight(r10w10p2)
      
         db2: Weight(r20p2)
      
          db3: Weight(rp3)
      
           在这个例子中，对db1, db2，db3这三个数据库的读操作分成了两个优先级:
      
          p3->[db3]
      
         p2->[db1, db2]
      
         当进行读操作时，因为db3的优先级最高，所以优先从db3读，

     如果db3无法进行读操作，再从db1, db2中随机选一个，因为db2的读权重是20，而db1是10，所以db2被选中的机率比db1更大。




          注:字母r或R表示可以对数据库进行读操作, 后面跟一个数字表示读操作的权重，如果字母r或R后面没有数字，则默认是10;

           字母w或W表示可以对数据库进行写操作, 后面跟一个数字表示写操作的权重，如果字母w或W后面没有数字，则默认是10;

          字母p或P表示读操作的优先级, 数字越大优先级越高，读操作优先从优先级最高的数据库中读数据，如果字母p或P后面没有数字，则默认优先级是0;

          字母q或Q表示写操作的优先级, 数字越大优先级越高，写操作优先从优先级最高的数据库中写数据，如果字母q或Q后面没有数字，则默认优先级是0.

          字母i或I表示动态DBIndex, 和用户通过threadLocal指定的dbIndex结合，实现rw之上更灵活的路由 一个db可以同时配置多个i；不同的db可以配置相同的i，

          例如 db0:i0i2,db1:i1,db2:i1,db3:i2则用户指定dbIndex=0，路由到db0；（只有db0有i0） 用户指定dbIndex=1，随机路由到db1和db2；（db1和db2都有i1）
          用户指定dbIndex=2，随机路由到db0和db3；（db0和db3都有i2）



读写分离交流群：303216689








