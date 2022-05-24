# Building Restful Webservices using Spring Boot

Banuprakash C

Full Stack Architect, Co-founder Lucida Technologies Pvt Ltd., Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/RESTful

===================================

Softwares Required:
1) Java 8+
	https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html

2) Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2020-03/m1/eclipse-ide-enterprise-java-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i <container_name> /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```
=================================

RESTful Web Services

REST ==> REprsentational State Transfer; archtectural style for distributed hypermedia systems
Roy Fielding -- 2000

Resource?
document, image, services, collection of other services ,printer

REST APIs use Uniform Resource Identifier to address resources.

* Singleton and Collection Resources
	* "customers" is a collection resources; "customer" is a singleton resource

* Collections and sub-collections resources
	"/customers/{customerId}/accounts"

* The state of the resource at any particular time ==> Resource Representation

* Content Negotiation
=> client asks for suitable presentation [HTTP headers ==> Accept : application/json; Content-type: text/xml]

* ContentNegotionHandlers ==> HttpMessageHandlers

Accept: application/json
	==> server reads this header and decides which handler has to be used
	a) JSON ==> Jackson, jettison, GSON [ Java <--> JSON]
	b) XML ==> JAXB [ XML < -- > Java]

Best Practices:
* use nouns to represent resources
* Collection
	A collection resource is a server managed directory of resources
	clients may propose new resources to be added to a collection.
* Store
	client-managed resource repository
	/playlists
	/wishlist
	/cart
* Controller
	==> procedural concept ==> executable functions
	/checkout
	/play	
* use lowercase letters in URIs
* use hyphens (-) to improve readablity [ avoid (_)] health-users instead of healthUsers or health_users
* Never use CRUD functions names for URIs [ avoid /createUser] ==> HTTP verbs ==> GET, POST, PUT and DELETE
GET ==> READ
POST ==> CREATE
PUT ==> UPDATE a resource if exists else create
DELETE ==> remove a resource

* use query component to filter URI collections [ http://server.com:8080/products?category=mobiles]
* use pathparameter for singleton resources
	http://server.com/products/3 ==> id
	or
	sub-collections
	"/customers/{customerId}/accounts"

---

Resource Representation consists of:
* data
* the metadata 
* hypermedia links ==> can help the clients in transition to next desired state

Guiding Principles of REST:
1) Uniform Interface
2) Client-server
	Seperatation of concerns
3) Stateless
	* each request from client to the server must contain all of the information necessary to understand the complete
	request
	Server cannot take advantage of any previous stored context info on server
	* No Session Tracking [ no cookie for JSESSIONID and HttpSession]
4) Cachable
	Cache-Control, Expires, ETag
5)  Layered System
	==> each component cannot see beyond the immediate layer they are interacting with

=========================================================================

Spring Boot for building Restful web services

------------------

Spring Boot 2.7.x ==> Framework built on Spring Framework 5.x

* SOLID Design Principle 
Single Responsigility, Open Close Principle , Interface segregartion, Dependency Injection

Dependency Injection done using IoC

Spring provides:
1) Dependency Injection
2) Life cycle management
3) EAI ==> Spring Data , MongoRepo, Mailing Service, Messaging Services, Naming Service
4) AOP
5) Declarative Transaction Support

Spring Boot?
	==> Highly opiniated framework where lots of configuration comes out of the box; makes development easy

* IF we use RDBMS config;
	==> spring boot creates DataSource [ pool of database connections using HikariCP DB]

	If not spring boot:

	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
		
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}

* If we use ORM; ==> Spring boot configures Hibernate out of the Box
	
	if not Spring boot:

	public LocalContainerEntityManagerFactory emf(DataSource ds) {
			LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
			emf.setDataSource(ds);
			emf.setJPAVendor(new HibernateJPAVendor());
			..


			return emf;	
	}

* Configure Transaction Mananger

--

If we develop web applications:
configures ==> Tomcat or Netty out of the box

==============

interface BookDao {
	addBook(Book);
}

public class BookDaoMongoImpl implements BookDao {
	..
}

public class BookDaoMySqlImpl implements BookDao {
	..
}

public class MyService {
	private BookDao dao;
	public MyService(BookDao dao) {
		this.dao = dao;
	}

	doTask() { dao.addBook(..)}
}


Constructor DI: 
beans.xml
<bean id="mongoDao" class="pkg.BookDaoMongoImpl" /> 
<bean id="mysqlDao" class="pkg.BookDaoMySQLImpl" /> 
<!-- create an instance of BookDaoMongoImpl by name "mongoDao" -->

<bean id="service" class="pkg.MyService"> 
	<constructor index="0" ref="mysqlDao" />
</bean>

==========


public class MyService {
	private BookDao dao;
	public void setRepo(BookDao dao) {
		this.dao = dao;
	}

	doTask() { dao.addBook(..)}
}

Setter DI:

beans.xml
<bean id="mongoDao" class="pkg.BookDaoMongoImpl" /> 
<bean id="mysqlDao" class="pkg.BookDaoMySQLImpl" /> 
<!-- create an instance of BookDaoMongoImpl by name "mongoDao" -->

<bean id="service" class="pkg.MyService"> 
	<property name="repo" ref="mysqlDao" />
</bean>

=======================

Annotation 


interface BookDao {
	addBook(Book);
}

@Repository
public class BookDaoMongoImpl implements BookDao {
	..
}

public class BookDaoMySqlImpl implements BookDao {
	..
}

@Service
public class MyService {
	@Autowired
	private BookDao dao; 
	 
	doTask() { dao.addBook(..)}
}

-------------

Spring instaniates objects of classes which has one of these annotations at class-level:
1) @Component ==> utility classes / helper classes
2) @Repository ==> Persistence tier code

	try {

	} catch(SQLException ex) {
		if(ex.getErrorCode() == 301) {
			throw new DuplicateKeyCodesExeption();
		}
	}

3) @Service
4) @Controller
5) @RestController
6) @Configuration


@Autowired ==> wiring happens using ByteCode instrumentation
* CGLib
* JavaAssist
* ByteBuddy

Book b = jpa.getById(4); ==> Proxy object instead of actual object [ CGLib creates proxy]
	==> not hit the DB

for(int i = 1; i <= 100; i ++) {
	Book[] books = ..
	books[i] = jpa.getById(i); // here we have 100 proxy objects
}

books[23].getTitle();  // hit the db and get book from DB; can't use proxy

books[45].getTitle();  // hit the db and get book from DB; can't use proxy

============================================

https://start.spring.io

====================================

For Standalone as well as web application ==> this is an entry point

```
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```
BeanFactory, ApplicationContext are interfaces for SpringContainer
run(); creates a SpringContainer

SpringApplication.run(DemoApplication.class, args); ==> returns an ApplicationContext [ interface for Spring Container]


@SpringBootApplication
* @ComponentScan(basePackages="com.adobe.demo")
	==> scan com.adobe.demo and sub packages for any of the above mentioned (6) annoations and instanite

* @EnableAutoConfiguration
	==> looks in dependenies and instantiates configuration objects
	like Tomcat, DB pool, Hibernate , ...

* @Configuration

===

Problem:

***************************
APPLICATION FAILED TO START
***************************

Description:

Field bookDao in com.adobe.demo.service.SampleService required a single bean, but 2 were found:
	- bookDaoMongoImpl: defined in file [C:\Trainings WS\Adobe\MayRestfulWS\RESTful\demo\target\classes\com\adobe\demo\repo\BookDaoMongoImpl.class]
	- bookDaoMySQLImpl: defined in file [C:\Trainings WS\Adobe\MayRestfulWS\RESTful\demo\target\classes\com\adobe\demo\repo\BookDaoMySQLImpl.class]


* Solution 1:
* marking one of the beans as @Primary

@Repository
@Primary
public class BookDaoMySQLImpl implements BookDao {

@Repository
public class BookDaoMongoImpl implements BookDao {

* Solution 2:
* different uses cases needs different instances @Qualifier

@Repository
public class BookDaoMySQLImpl implements BookDao {

@Repository
public class BookDaoMongoImpl implements BookDao {


@Service
public class SampleService {
	@Autowired
	@Qualifier("bookDaoMongoImpl")
	private BookDao bookDao;

* Solution 3:

using @Profile


@Repository
@Profile("prod")
public class BookDaoMongoImpl implements BookDao {


@Repository
@Profile("dev")
public class BookDaoMySQLImpl implements BookDao {

@Service
public class SampleService {
	@Autowired
	private BookDao bookDao;

src/main/resources/application.properties
spring.profiles.active=dev

OR
Commandline argument

Run As ==> Run Configurations ==> Program Arguments
--spring.profiles.active=dev

How it resolves?
Command Line Arguments ==> System properties ==> application.properties

* Other ways to resolve

@ConditionalOnProperty

application.properties
dao=mysql

@ConditionalOnProperty(name = "dao", havingValue = "mongo")
public class BookDaoMongoImpl implements BookDao {

@ConditionalOnProperty(name = "dao", havingValue = "mysql")
public class BookDaoMySQLImpl implements BookDao {

---

@Repository
@ConditionalOnMissingBean(type = "BookDao.class")
public class BookDaoMongoImpl implements BookDao {

---------

@ConditionalOnBean(type = "MongoDBConnection.class")

=========================================================


Spring instantiates objects of classes which has any of the above 6 annoations.
* What about 3rd party classes to be used in Spring Container
* Spring Container by default uses default constructor for instantiatiting beans

@Bean ==> factory method

@Service
public class MyService {
	@Autowired
	DataSource ds; // c3p0 datasource

	doTask() {
		ds.getConnection()...
	}
}

@Configuration
public class AppConfig {

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("dbuser");                                  
		cpds.setPassword("dbpassword"); 
		return cpds;
	}
}


===

@Order(100)
@Service
public class Comp1 {

}


@Order(-3)
@Service
public class Comp2 {

}

=====================

Spring Data JPA with RESTful Web services

============================================

Day 2

Spring Boot ==> Dependency Injection ==> provided by Core Modules
ApplicationContext ==> XML based or Annotation based metadata

@Component
@Repository
@Service
@Controller
@RestController
@Configuration
@Bean
@SpringBootApplication [ @ComponentScan, @EnableAutoConfiguration, @Configuration]

To control bean instantiations based on different requirements:
@Primary
@Qualifier
@Profile
@ConditionalOnProperty
@ConditionalOnBean
@ConditionalOnMissingBean

=================================

RESTful Webservices and JavaPersistence API


JPA ==> is a specification for using ORM

ORM ==> Object Relational Mapping

class <--> RDBMS

	ORM frameworks: Hibernate, JDO, KODO, TopLink, OpenJPA, ...

	JPA ==> Specifiation 

=============

Mapping class to table

@Entity
public class Product{}

@Entity
public class Vehicle{}

@Entity
public class Book {}

========

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("dbuser");                                  
		cpds.setPassword("dbpassword"); 
		return cpds;
	}

	@Bean
	public LocalContainerEntityManagerFactory emf(DataSource ds) {
		LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
		emf.setDataSource(ds);
		emf.setJpaProvider(new HibernateJpaVendor());
		emf.setBasePackagesToScan("com.adobe.prj.entity");
		return emf;
	}


=============



spring.jpa.hibernate.ddl-auto=update

Hibernate to DDL mapping
* update ==> TOP Down Approach
for a class mapped to table; if table exists use it else create the table

* create
for a class mapped to table; always drop the table and re-create

* verify ==> Bottom to Top Approach
for a class mapped to table; check if table exists; if exists use it else throw exception

===
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

Inform ORM to generate SQL matchin MySQL8

========


Spring Data JPA simplifies JPA with ORM operations

Part 1 with JDBC:
@Override
	public void addProduct(Product p) throws DaoException {
		String SQL = "INSERT INTO products (id, name, price, quantity) VALUES (0, ?, ?, ?)";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER, PWD);
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.executeUpdate(); // INSERT, DELETE, UPDATE
		} catch (SQLException e) {
			throw new DaoException("unable to add product", e);
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Product> getProducts() throws DaoException {
		List<Product> products = new ArrayList<>();
		String SQL = "SELECT id, name, price, quantity FROM products";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER, PWD);
			Statement stmt = con.createStatement();
			ResultSet rs  = stmt.executeQuery(SQL); // SELECT
			while(rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("NAME"));
				p.setPrice(rs.getDouble("PRICE"));
				p.setQuantity(rs.getInt("QUANTITY"));
				products.add(p);
			}
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			throw new DaoException("unable to get products", e);
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

PART 2: using JPA with ORM

@Override
	public class ProductDaoJpaImpl .. {
	@PersistenceContext
	EntityManager em;

	public void addProduct(Product p) throws DaoException {
		em.persist(p);	 
	}

	@Override
	public List<Product> getProducts() throws DaoException {
		 TypedQuery<Product> query = ...
		 return query.getResultList();
	}


PART 3: using Spring Data JPA

public interface ProductDao extends JpaRepository<Product, Integer> {

}

==========

@Query("") can be SQL or JP-QL

exeucteQuery() ; select

executeUpdate(); update, delete and insert

===============================================

RESTful Web services 

 
 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>
	==> gives:
	1) Tomcat as Servlet Container / web container [ Undertow, Netty, Jetty, --> need custom config]
	2) Spring Web MVC
	3) jackson [ Java <--> JSON ] [ XML, CSV, RSS --> need custom config]

	@Controller ==> to return SSR pages [ dynmaic HTML]
	@RestController ==> to return presentation of representation

	@RestController
	@RequestMapping("api/products")
	public class ProductController {
		@GetMapping()
		m1()

		@PostMapping()
		m2()
	}

===========

@Query("select name, price from Product p where p.price >= :l and p.price <= :h")
List<Object[]> queryByRange(@Param("l") double low, @Param("h") double high);

Object[0] ==> name
Object[1] ==> price

----
package com.adobe.prj.dto;
class ProductDTO {
	name
	price
	constuctors
	setters, getters
}


@Query("select new com.adobe.prj.dto.ProductDTO(name, price) from Product p where p.price >= :l and p.price <= :h")
List<ProductDTO> queryByRange(@Param("l") double low, @Param("h") double high);

-------------

POST http://localhost:8080/api/products

Headers:

Accept: application/json
content-type:application/json

Body [ raw is selected]
{
    "name":"LG Inverter AC",
    "price":49999.0,
    "category":"Electronics",
    "quantity":100
}

============

PUT http://localhost:8080/api/products/6

Accept: application/json
content-type:application/json

Body [ raw is selected]
{
   "quantity":200
}

javax.persistence.TransactionRequiredException: Executing an update/delete query

===============================


Programmatic Transaction

public void addProduct(Product p) {
	Connection con =. ..
	try {
		con.setAutoCommit(false);
			...
		con.commit();
	} catch(SQLException ex) {
		con.rollback();
	}
}

--


public void addProduct(Product p) {
	EntityManager em = ..
	Transaction tx = null;
	try {
		Transaction tx = em.beginTransaction();
			...
		tx.commit();
	} catch(SQLException ex) {
		tx.rollback();
	}
}

---

Declarative Transaction

@Transactional
public void addProduct(Product p) {
}

=============

i don't have below method:
@Modifying
@Query("update Product set quantity = :qty where id = :id")
void updateProduct(@Param("qty") int quantity, @Param("id") int id);


In Service:
	DIRTY Checking [ within PersistencContext / Trnsactional Boundary ]
	within transactional boundary if entity becomes dirty ==> automatic UPDATE is triggred
	@Transactional
	public void updateProduct(int qty, int id) {
		 productDao.findById(id).get();
		  productDao.findById(id).get();
		Product p =  productDao.findById(id).get();
		p.setQuantity(qty);
		// throw new IllegalStateException();
	}

===

Instead of Mutation with @Transactional; entity is given to View Tier [ Controller or Presentation page]
where data gets changed ==> No concept of DirtyChecking


===============================================================

AOP ==> Aspect Oriented Programming

* cross-cutting concerns leads to code tangling and code scatterning

Examples of cross-cutting concerns:
* Logging
* Security
* Profile
* Transaction

public void transferFunds(Account fa, Account to, double amt) {
	profile.startTime();
	log.debug("transaction started!!!")
	if(securityCtx.isAuthenticated()) {
		beginTX;
		log.debug("valid user");
		debit(amt);
		log.debug("amount debited");
		credit(amt);
		log.debug("amount credited!!1");
		commitTX;
	}
	profile.endTime();
}

---

AOP
* Aspect ==> bit of concern which can be used along with main logic ==> LogAspect, SecurityAspect, TransactionAspect
* JoinPoint ==> place in code where Aspect can be applied [ any method or any exception]
* PointCut ==> selected JoinPoint for weaving Aspect
* Advice ==> BeforeAdvice, AfterAdvice, AfterReturningAdvice, AroundAdvice, AfterThrowingAdvice


===========================

Customer 					<==> customers
	email 					<--> email [PK]
	firstName				<--> first_name
	lastName;				<--> last_name

CustomerDao
	==> No extra methods

OrderService
@Autowired
CustomerDao
	

CustomerController ==> OrderService ==> CustomerDao

===============================================================
