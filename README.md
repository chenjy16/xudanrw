# xudanrw
A flexible read/write splitting component

Current Production Version: 1.0.1

![Image text](https://github.com/chenjy16/xudanrw/blob/master/xudanrw.png)





1. Component Description
This component is designed for one-master, multiple-slave database read/write separation with the following features:

MySQL Replication Support:
Works directly with the MySQL replication mechanism by using group datasources to support read/write separation. You can set different weights for each database.

Failover on Fatal Exceptions:
If one of the databases goes down (resulting in a defined fatal exception), the component will switch to a read-retry mode to ensure that as many data accesses as possible can be served by the healthy databases.

Thread Protection via Try-Lock Mechanism:
Once a fatal exception is caught for the first time, only one thread is allowed to access the database until it returns to normal operation.

Traffic Control & Database Protection:
Manages overall access to prevent overload and protect the databases.

Specifying Database Access (ThreadLocal):
In a group of equivalent databases (usually only one primary write database with the rest being slaves), there may be master-slave key delays due to replication. For various types of reads (immediate and delayed), you can use GroupDataSourceRouteHelper.executeByGroupDataSourceIndex(int dataSourceIndex) to specify which database should be accessed.

Specifying Database Access (Hint):
This is an alternative way to specify which database to access. By placing a comment before the SQL query, you can instruct the TDDL dynamic datasource which database to use. For example:
/*+TDDL_GROUP({groupIndex:12})*/select * from tab;


2. Dependencies
You will need to include the following dependencies in your project:


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


3. Component Configuration
DataSource Configuration
First, configure both the write (primary) and read (slave) datasources. For example:

<!-- Write DataSource -->
<bean id="writeDataSource" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
    <property name="driverClassName" value="${database.core.write.driverClassName}" />
    <property name="url" value="${database.core.write.url}" />
    <property name="username" value="${database.core.write.username}" />
    <property name="password" value="${database.core.write.password}" />
    <property name="maxActive"><value>20</value></property>
    <property name="initialSize"><value>5</value></property>
    <property name="maxWait"><value>30000</value></property> <!-- Wait timeout in ms -->
    <property name="maxIdle"><value>20</value></property>       <!-- Maximum idle connections -->
    <property name="minIdle"><value>5</value></property>        <!-- Minimum idle connections -->
    <property name="removeAbandoned"><value>true</value></property> <!-- Auto reclaim timed-out connections -->
    <property name="removeAbandonedTimeout"><value>30</value></property> <!-- Timeout in seconds -->
    <property name="testWhileIdle"><value>true</value></property>   <!-- Enable async check using eviction thread -->
    <property name="testOnBorrow"><value>true</value></property>
    <property name="testOnReturn"><value>false</value></property>
    <property name="validationQuery"><value>select 1</value></property>
    <property name="numTestsPerEvictionRun"><value>20</value></property>
    <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>
</bean>

<!-- Read DataSource 1 -->
<bean id="readDataSource1" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
    <property name="driverClassName" value="${database.core.read1.driverClassName}" />
    <property name="url" value="${database.core.read1.url}" />
    <property name="username" value="${database.core.read1.username}" />
    <property name="password" value="${database.core.read1.password}" />
    <property name="maxActive"><value>20</value></property>
    <property name="initialSize"><value>5</value></property>
    <property name="maxWait"><value>30000</value></property>
    <property name="maxIdle"><value>20</value></property>
    <property name="minIdle"><value>5</value></property>
    <property name="removeAbandoned"><value>true</value></property>
    <property name="removeAbandonedTimeout"><value>30</value></property>
    <property name="testWhileIdle"><value>true</value></property>
    <property name="testOnBorrow"><value>true</value></property>
    <property name="testOnReturn"><value>false</value></property>
    <property name="validationQuery"><value>select 1</value></property>
    <property name="numTestsPerEvictionRun"><value>20</value></property>
    <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>
</bean>

<!-- Read DataSource 2 -->
<bean id="readDataSource2" class="org.apache.tomcat.jdbc.pool.DataSource" destroy-method="close">
    <property name="driverClassName" value="${database.core.read2.driverClassName}" />
    <property name="url" value="${database.core.read2.url}" />
    <property name="username" value="${database.core.read2.username}" />
    <property name="password" value="${database.core.read2.password}" />
    <property name="maxActive"><value>20</value></property>
    <property name="initialSize"><value>5</value></property>
    <property name="maxWait"><value>30000</value></property>
    <property name="maxIdle"><value>20</value></property>
    <property name="minIdle"><value>5</value></property>
    <property name="removeAbandoned"><value>true</value></property>
    <property name="removeAbandonedTimeout"><value>30</value></property>
    <property name="testWhileIdle"><value>true</value></property>
    <property name="testOnBorrow"><value>true</value></property>
    <property name="testOnReturn"><value>false</value></property>
    <property name="validationQuery"><value>select 1</value></property>
    <property name="numTestsPerEvictionRun"><value>20</value></property>
    <property name="minEvictableIdleTimeMillis"><value>1800000</value></property>
</bean>

        
Injection into the Dynamic DataSource
Next, inject the above datasources into the dynamic datasource:


<!-- Read/Write Separation Configuration -->
<bean id="dsconfDO" class="com.midea.trade.rws.util.DsConfDO">
    <property name="writeRestrictTimes" value="0"/> <!-- Write limit count within time range -->
    <property name="readRestrictTimes" value="0"/>  <!-- Read limit count within time range -->
    <property name="timeSliceInMillis" value="0"/>   <!-- Time slice (must be no less than 1000ms) -->
    <property name="maxConcurrentReadRestrict" value="0"/>  <!-- Max concurrent read limit -->
    <property name="maxConcurrentWriteRestrict" value="0"/> <!-- Max concurrent write limit -->
</bean>

<bean id="fetcher" class="com.midea.trade.rws.util.SpringDataSourceFetcher"/>
<bean id="groupDataSource" class="com.midea.trade.rws.group.TGroupDataSource">
    <constructor-arg name="dsKeyAndWeightCommaArray" value="writeDataSource:wq1,readDataSource1:rp3,readDataSource2:rp3"/>
    <constructor-arg ref="fetcher"/>
    <constructor-arg ref="dsconfDO"/>
</bean>


Configuration Rules
The configuration rules are as follows:

Determining DataSource for Reads and Writes:
The selection of a datasource is based on the priority indicated by the letters p (for priority) or q, which decide whether the datasource is used for read, write, or both. Then the letters r or w determine the probability (weight) with which the corresponding read or write datasource is selected.

Weight Example:
For example, given the configurations:

db1: r10w10p2

db2: r20p2

db3: rp3

The resulting weights would be:

db1: Weight (r10, w10, p2)

db2: Weight (r20, p2)

db3: Weight (rp3)

Here, for read operations, there are two priority groups:

p3: Contains [db3]

p2: Contains [db1, db2]

When performing a read, the datasources in the highest priority group (p3) are attempted first (in this case, db3). If db3 cannot handle the read operation, a datasource is randomly chosen from db1 and db2. Given that db2 has a read weight of 20 versus db1’s 10, db2 has a higher probability of being selected.

Letter Meanings:

r/R: Indicates that the database can be used for read operations. The subsequent number specifies the read weight; if omitted, it defaults to 10.

w/W: Indicates that the database can be used for write operations. The subsequent number specifies the write weight; if omitted, it defaults to 10.

p/P: Indicates the read operation priority. A higher number means a higher priority. If omitted, the default priority is 0.

q/Q: Indicates the write operation priority. A higher number means a higher priority. If omitted, the default priority is 0.

i/I: Indicates the dynamic DBIndex. This works together with the DB index specified by the user via ThreadLocal, enabling more flexible routing on top of the read/write separation.
For example, if configured as:

db0:i0i2

db1:i1

db2:i1

db3:i2

When the user specifies dbIndex=0, routing will select db0 (only db0 has i0);
When dbIndex=1 is specified, it will randomly select between db1 and db2 (both have i1);
When dbIndex=2 is specified, it will randomly select between db0 and db3 (both have i2).










