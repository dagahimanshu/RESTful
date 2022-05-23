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
