SpringJDBCTransactionManagement
-------------













Some of the benefits of using Spring Transaction Management are:
--------------------------------------------------------------------
1. Support for Declarative Transaction Management. In this model, Spring uses AOP over the transactional methods to provide data integrity. This is the preferred approach and works in most of the cases.
2. Support for most of the transaction APIs such as JDBC, Hibernate, JPA, JDO, JTA etc. All we need to do is use proper transaction manager implementation class. For example `org.springframework.jdbc.datasource.DriverManagerDataSource` for JDBC transaction management and `org.springframework.orm.hibernate3.HibernateTransactionManager` if we are using Hibernate as ORM tool.
3. Support for programmatic transaction management by using `TransactionTemplate` or `PlatformTransactionManager` implementation.

Most of the features that we would want in a transaction manager is supported by Declarative transaction management, so we would use this approach for our example project.


Spring Transaction Management JDBC Example
-----------------------------------------
We will create a simple Spring JDBC project where we will update multiple tables in a single transaction. The transaction should commit only when all the JDBC statements execute successfully otherwise it should rollback to avoid data inconsistency.

If you know JDBC transaction management, you might argue that we can get do it easily by setting auto-commit to false for the connection and based on the result of all the statements, either commit or rollback the transaction. Obviously we can do it, but that will result in a lot of boiler-plate code just for transaction management. Also the same code will present in all the places where we are looking for transaction management, causing tightly coupled and non-maintainable code.

Spring declarative transaction management addresses these concerns by using Aspect Oriented Programming to achieve loose coupling and avoid boiler-plate code in our application. Let’s see how Spring does it with a simple example.

Before we jump into our Spring project, let’s do some database setup for our use.

Spring Transaction Management – Database Setup
-------------------------------------------
We will create two tables for our use and update both of them in a single transaction.

```
CREATE TABLE `Customer` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Address` (
  `id` int(11) unsigned NOT NULL,
  `address` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

We could define foreign-key relationship here from Address id column to Customer id column, but for simplicity I am not having any constraint defined here.

Spring Transaction Management – Maven Dependencies
----------------------------------------------------
Since we are using JDBC API, we would have to include spring-jdbc dependency in our application. We would also need MySQL database driver to connect to mysql database, so we will include mysql-connector-java dependency too.

spring-tx artifact provides transaction management dependencies, usually it’s included automatically by STS but if it’s not then you need to include it too. You might see some other dependencies for logging and unit testing, however we will not be using any of them. Our final pom.xml file looks like below code.


Notice that CustomerDAO implementation is not taking care of transaction management. This way we are achieving separation of concerns because sometimes we get DAO implementations from third party and we don’t have control on these classes.

Spring Declarative Transaction Management – Service
---------------------------------------------------
Let’s create a Customer Service that will use the CustomerDAO implementation and provide transaction management when inserting records in the customer and address tables in a single method.

```
public class CustomerManagerImpl implements CustomerManager {

	private CustomerDAO customerDAO;

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Override
	@Transactional
	public void createCustomer(Customer cust) {
		customerDAO.create(cust);
	}
}
```

If you notice the CustomerManager implementation, it’s just using CustomerDAO implementation to create the customer but provide declarative transaction management through annotating createCustomer() method with @Transactional annotation. That’s all we need to do in our code to get the benefits of Spring transaction management.

@Transactional annotation can be applied over methods as well as whole class. If you want all your methods to have transaction management features, you should annotate your class with this annotation. Read more about annotations at Java Annotations Tutorial.
The only part remaining is wiring spring beans to get spring transaction management example to work.


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

	<context:property-placeholder location="classpath:database.properties" />

	<!-- Enable Annotation based Declarative Transaction Management -->
	<tx:annotation-driven proxy-target-class="true" transaction-manager="transactionManager" />


	<!-- Creating TransactionManager Bean, since JDBC we are creating of type DataSourceTransactionManager -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	
	<!-- MySQL DB DataSource -->
	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="${mysql.driver.class.name}" />
		<property name="url" value="${mysql.url}" />
		<property name="username" value="${mysql.username}" />
		<property name="password" value="${mysql.password}" />
	</bean>


	<bean id="customerDAO" class="com.journaldev.spring.jdbc.dao.CustomerDAOImpl">
		<property name="dataSource" ref="dataSource" />
	</bean>

	<bean id="customerManager" class="com.journaldev.spring.jdbc.service.CustomerManagerImpl">
		<property name="customerDAO" ref="customerDAO"></property>
	</bean>
</beans>
```

Important points to note in the spring bean configuration file are:
----------
- tx:annotation-driven element is used to tell Spring context that we are using annotation based transaction management configuration. transaction-manager attribute is used to provide the transaction manager bean name. transaction-manager default value is transactionManager but I am still having it to avoid confusion. proxy-target-class attribute is used to tell Spring context to use class based proxies, without it you will get runtime exception with message such as Exception in thread “main” org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named ‘customerManager’ must be of type [com.journaldev.spring.jdbc.service.CustomerManagerImpl], but was actually of type [com.sun.proxy.$Proxy6]
- Since we are using JDBC, we are creating transactionManager bean of type org.springframework.jdbc.datasource.DataSourceTransactionManager. This is very important and we should use proper transaction manager implementation class based on our transaction API use.
- dataSource bean is used to create the DataSource object and we are required to provide the database configuration properties such as driverClassName, url, username and password. Change these values based on your local settings.
- We are injecting dataSource into customerDAO bean. Similarly we are injecting customerDAO bean into customerManager bean definition.


Notice the log message says that data inserted into customer table successfully but exception thrown by MySQL database driver clearly says that value is too long for the address column. Now if you will check the Customer table, you won’t find any row there that means that transaction is rolled back completely.

If you are wondering where the transaction management magic is happening, look at the logs carefully and notice the AOP and Proxy classes created by Spring framework. Spring framework is using Around advice to generate a proxy class for CustomerManagerImpl and only committing the transaction if the method returns successfully. If there is any exception, it’s just rolling back the whole transaction. I would suggest you to read Spring AOP Example to learn more about Aspect Oriented Programming model.

That’s all for Spring Transaction Management Example, download the sample project from below link and play around with it to learn more.
