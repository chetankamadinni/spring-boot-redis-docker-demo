## Welcome to GitHub Pages

You can use the [editor on GitHub](https://github.com/chetankamadinni/spring-boot-redis-docker-demo/edit/master/docs/index.md) to maintain and preview the content for your website in Markdown files.

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.

```markdown
# spring-boot-redis-docker-demo
Demo of Spring boot app( docker container) connecting to redis(docker container)

This is a simple spring-boot app that connects to redis to store and retrive the data.
Here Redis is used both as database and as well as caching system.
## Step1 Create spring-boot-app
Create a spring-boot app with `spring-boot-starter-web`, `spring-boot-starter-data-redis`, `jedis`, `lombok` dependencies.
**Jedis** is the java client used to connect to redis instance.

Add the below properties to application.properties file

```
server.port=8085
spring.redis.host=redis
spring.redis.port=6379
```

Provide a JedisConnectionFactory which is used for RedisTemplate as shown below.
```java
@Bean
public JedisConnectionFactory jedisConnectionFactory() {
	RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
	config.setHostName(host);
	config.setPort(port);
	return new JedisConnectionFactory(config);
}
	
@Bean
public RedisTemplate<String, Object> redisTemplate(){
	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(jedisConnectionFactory());
	redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
	return redisTemplate;
}
```
 Use this redisTemplate to do all the hashOperations like put, get etc
 
 ## Step2 Create a Dockerfile for spring-boot-app
 Create a Dockerfile as shown below
```
FROM openjdk:8
ADD target/spring-boot-redis.jar spring-boot-redis.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "spring-boot-redis.jar"]
```
 
 ## Step3 Create a docker-compose file
 Create a docker-compose.yml file as shown below. Here the app is binded to redis instance
```yml
 version: '3'
services:
  app:
    build: .
    ports:
     - "8085:8085"
    links:
      - redis
  redis:
    image: redis
    ports:
     - "6379:6379"
```

## Step 4 Execute
To execute this app<br/>
  
    mvn clean install
  
    docker-compose build
  
    docker-compose up
  
Hit the following endpoint to post a record
POST http://localhost:8085/api/employee
```json
{
   "id": 1000,
   "firstName": "Chetan",
   "lastName": "Kamadinni"
}
```
To get the record do
GET http://localhost:8085/api/employee/1000

## Useful docker-compose commands
- `docker-compose build`
- `docker-compose up -d` (Detached)
- `docker-compose down` (stop)
- `docker-compose restart` (restart the services)

```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/chetankamadinni/spring-boot-redis-docker-demo/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://support.github.com/contact) and we’ll help you sort it out.
