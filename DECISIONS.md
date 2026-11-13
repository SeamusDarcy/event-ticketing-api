# Design Decisions

## Stage 1: Spring Fundamentals

### @Service vs @Bean
- both are used to create a bean from a class
- @Service goes on at the class level and is good for when you write your own classes
- @Bean goes in its own config file at the method level and is good for when you are working with a class you can't edit
- @Bean is also good for objects that need setup

### @Value vs @ConfigurationProperties
- both read settings from the application.yml file into the code
- @Value reads one at a time, using a "${}" string each time it's used
- @ConfigurationProperties reads a group of related settings that share a prefix in the yml into one record
- @Value can be used for quick standalone settings
- @ConfigurationProperties is used for multiple settings together

### What happens between running main() and Tomcat accepting requests
- main() calls SpringApplication.run, which creates the container (the place to store beans), scans for beans and applies auto-configuration
- Spring then creates and wires every bean and runs their @PostConstruct methods
- Then Tomcat starts accepting requests

### What is a bean
- an object created and managed by Spring instead of writing new in code
- beans are stored in a container and handed to any class that asks for one
- a class becomes a bean through an annotation like @Service or @RestController, or through a @Bean method in a @Configuration class

### What auto-configuration does
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

## Stage 2: Basic REST API for Events

## API Basics
- RequestMapping at class level used as a shortcut for all Mapping methods in class as they build on from the initial RequestMapping
- Every Mapping method in Controller returns Http status 200 by default
- @RequestBody tells Spring to read the JSON in request body and convert it into the parameters type e.g. request record
- @Valid placed before @RequestBody parameter, it makes Spring check the records constraint (@NotNull) annotation before method runs, failure produces a 400 error and method never called
- Create in EventController returns a ResponseEntity<EventResponse> which is the staus code (201), the headers (location) and the body (eventResponse). to create the ResponseEntity we create a URI with the location path of new created event
- 

### Instant vs LocalDateTime
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