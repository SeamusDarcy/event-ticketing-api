## Design Decisions

## #Stage 1: Spring Fundamentals

## @Service vs @Bean
- both are used to create a bean from a class
- @Service goes on at the class level and is good for when you write your own classes
- @Bean goes in its own config file at the method level and is good for when you are working with a class you can't edit
- @Bean is also good for objects that need setup

## @Value vs @ConfigurationProperties
- both read settings from the application.yml file into the code
- @Value reads one at a time, using a "${}" string each time it's used
- @ConfigurationProperties reads a group of related settings that share a prefix in the yml into one record
- @Value can be used for quick standalone settings
- @ConfigurationProperties is used for multiple settings together

## What happens between running main() and Tomcat accepting requests
- main() calls SpringApplication.run, which creates the container (the place to store beans), scans for beans and applies auto-configuration
- Spring then creates and wires every bean and runs their @PostConstruct methods
- Then Tomcat starts accepting requests

## What is a bean
- an object created and managed by Spring instead of writing new in code
- beans are stored in a container and handed to any class that asks for one
- a class becomes a bean through an annotation like @Service or @RestController, or through a @Bean method in a @Configuration class

## What auto-configuration does
- Spring Boot checks which libraries the project has and sets up a working default for each one
- e.g. the web starter brings in Tomcat, so Boot starts a web server
- it only does this when the right conditions are met, like the library being present
- if you create your own bean, it will be used instead of Spring's default one
- "debug: true" in the yml prints a report of what it turned on and what it skipped

### Constructor injection vs field injection
- constructor injection passes dependencies in as constructor parameters
- fields can be final, so dependencies never change or get left null after creation
- the class can't be created without its dependencies
- field injection hides dependencies and needs Spring or reflection to fill them in

### Stage 2: Basic REST API for Events

## API Basics
- RequestMapping at class level used as a shortcut for all Mapping methods in class as they build on from the initial RequestMapping
- Every Mapping method in Controller returns Http status 200 by default
- @RequestBody tells Spring to read the JSON in request body and convert it into the parameters type e.g. request record
- @Valid placed before @RequestBody parameter, it makes Spring check the records constraint (@NotNull) annotation before method runs, failure produces a 400 error and method never called
- Create in EventController returns a ResponseEntity<EventResponse> which is the staus code (201), the headers (location) and the body (eventResponse). to create the ResponseEntity we create a URI with the location path of new created event


## Instant vs LocalDateTime
- for the Event object the time will be an Instant instead of a LocalDateTime
- LocalDateTime has no timezone attached, so one 7:30pm could be different from another 7:30pm
- Instant is a unique moment on the UTC timeline, so it can be converted to any timezone needed

### Event id
- in the Event object the id is final and has no setter
- the repository gives a new event its id when it builds the saved copy

## In Memory Storage 
- ConcurrentHashMap used for Id to event and AtomicLong for ID in repository both to avoid race condition 
- findById in repository return Optional<Event> so no unexpected null returns 
- findAll returns copy of maps values sorted via steam
- deleteById uses the result of remove instead of checking first so its one atomic step 

## @RequestParam vs @PathVariable
- both used inside the method parameters 
- PathVariable is used when the parameter is part of the path, the resource you mean 
- RequestParam is for anything after the ? in url for extra stuff like filters 

## ApiExceptionHandler
- ApiExceptionHandler in common package is used by spring as a global catch for all controllers
- it's marked with @RestControllerAdvice
- When EventService throws EventNotFoundException, it travels up through controller since nothing catchs it there
- Spring see's it and find method with @ExceptionHander(EventNotFoundException.class)
- for the EventNotFoundException we create our own custom exception since it happens at the service layer it shouldnt know about any HTTP status codes so we put it in custom terms and later turn its into a 404 with apiExceptionHandler

## ProblemDetail
- java object that represents an error response 
- when you return it from an @ExceptionHandler, Spring turns it into the JSON body and sets the HTTP status for it 
- its fields are Status (HTTP code), title (name for prob type), detail (readable explanation), instance (the request path that caused it), type (a URI identifying the kind of problem), extra Properties (any additional named fields you add with setProperty)
- setProperty takes two fields, name of the JSON field and the value to put there

## Http Errors 
- setProperty() can be used to assign an additional named field to a problemDetail (error response) to the JSON body
- BindingResult is the object that holds the outcome of validating the request, it contains one fieldError for each failed field
- FieldError is one field, and it shows this field failed and why 
- used a record for storing FieldValidationErrors with String for field and message as record is safer than a map here
- for 400 Bad Request response used custom small String for response since Spring default is long and technical 
- Jackson converts the JSON in request to our java objects 

## HttpMessageNotReadableException 
- during testing message not readable exception was returning before custom methodArgumentsNotValidException 
- added a new case to handle messageNotReadable to avoid unreadable errors
- added a log.warn to notReadable to have a log of what went wrong 

### Stage 3: PostgreSQL Persistence with Flyway and JPA

## Docker
- Lets you run the Postgres in a container so anyone else can run it using this container without needing system dependence 
- docker compose up : read compose.yaml and start everything
- docker compose ps : lists running containers 

## Image vs Container 
- Image : the blueprint "postgres:18" means the official Postgres image, Its a read only package you download once
- Container : a running instance of a copy made from the image

## Volumes 
- Used to keep data after container deleted. 
- Volume : separate storage area that Docker manages outside container 
- "pgdata:/var/lib/postgresql" in the compose.yaml means mount the volume called pgdata at that folder inside the container 

## Hibernate 
- lets java code talk to database without writing SQL by hand
- when you run save(object) Hibernate turns into into INSERT and turns SELECT results back into java object 

## Flyway 
- tool that builds and changes the DB's tables. 
- Write numbered SQL files "V1__create_venues.sql", "V2__create_venues.sql" 
- Flyway runs each one once in order and remembers which it has run 
- Each version is only ever ran once and on each new start it checks all existing file version and if they have been done it assumes the database was build off this 
- for this reason it is very important to never edit files once they have been run once 
- any files that it see that have not been ran yet it will run now in order 

## Spring Data JPA
- Spring library that writes your repository code for you 
- You make a interface and it extends JpaRepository 
- This allows it to automatically have save, find, delete, etc without writting the logic for it (SQL code & Java opening connections and reading each column back into an object)

## JPA
- Set of rules for how java code save and loads objects in a database 
- It defines the annotations like @Entity, @Id, @ManyToOne

## Data Flow 
- Service -> Repository (Spring Data JPA) -> Hibernate (JPA) -> HikariCP (Connection pool + Postgres Drivers) -> Popstgres

## DataSource 
- DataSource : The object spring creates from the spring.datasource setting that hands out database connections to app

## HikariCP
- Opens a whole group of connections to the database at run time and when spring needs one it just gets it from here then it hands it back 
- much faster than trying to connect to db every time app needs a db connection 

## ddl-auto in (Hibernate)
- setting that tell Hibernate what it is allowed to do to database tables at start time
- none : Hibernate doesn't check tables at all at start 
- validate : Hibernate fixes nothing, if a table or column is missing app refuses to start and tell you which one 
- create or update : Hibernate fixes any mismatches itself, app will start even if tables are missing 

## open-in-view (Hibernate)
- Be default it is true which allows lazy loading so Hibernate only get the exact data you request no related data at all 
- true : lazyloading on so queries are hidden which is convent but allows queries you didnt write to run in controller layer 
- false : session closes when service method ends so lazy data must be read inside the service or fetched upfront and controllers only get the DTO's 

