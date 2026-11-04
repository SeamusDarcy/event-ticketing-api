## @Service vs @Bean
- both are used to create a bean from a class
- Service goes on at the class level and is good for when you write your own classes 
- bean goes in its own config file at the method level and is good for when you are working with a file you cant edit
- bean is also good for objects that need setup 