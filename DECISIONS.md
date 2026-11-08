## @Service vs @Bean
- both are used to create a bean from a class
- Service goes on at the class level and is good for when you write your own classes 
- bean goes in its own config file at the method level and is good for when you are working with a file you cant edit
- bean is also good for objects that need setup 

## @Value vs @ConfigurationProperties
- Both read setting from the applications.yml file into the code
- Value reads one at a time using a "${}" string each time its used
- ConfigurationProperties reads a group of related settings that share a prefix in yml into one record 
- Value can be used for quick Standalone settings 
- ConfigurationProperties used for multiple settings together 

## What happens between running main() and tomcat accepting requests
- main() calls SpringApplication.run which creates the container (place to store beans) and scans for beans and applies auto config
- Spring then creates and wires every bean and runs their @PostConstructor methods 
- Then TomCat starts accepting requests

## What is a bean
- object created and managed by Spring instead of writing new object in code
- Beans are stored in a container and handed to any class that asks for one
- a class becomes a bean through an annotation like @Service or @RestController, or through a @Bean method in a @Configuration class

## What autoconfiguration does
- Spring boot checks which libraries project has and sets up a working default for each one
- e.g. the web starter brings in Tomcat, so boot starts a web server 
- it only does this when right conditions are met like libraries being present 
- if you create your own bean it will be made instead of springs default one 
- "debug: true" in .yml prints a report of what it turned on and what it skipped 

## Constructor injection vs field injection 
- Constructor injection passes dependencies in as constructor parameter
- fields can be final, so dependencies never change or left null after creation 
- the class cant be created without its dependencies 
- field injection hides dependencies and needs Spring or reflection to fill them out 