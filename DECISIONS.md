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