# Spring Framework Best Practices Guide

## 1. Project Structure
- Follow a layered architecture
  - Controller layer for handling HTTP requests
  - Service layer for business logic
  - Repository layer for data access
- Use appropriate Spring stereotypes
  - `@Controller` or `@RestController` for web endpoints
  - `@Service` for business services
  - `@Repository` for data access objects

## 2. Dependency Injection
- Favor constructor injection over field injection
- Use `@Autowired` on constructors (optional in newer Spring versions)
- Keep component scanning narrow and explicit

```java
// Good
@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

// Avoid
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
```

## 3. Configuration
- Use Java-based configuration over XML
- Externalize configuration using `application.properties` or `application.yml`
- Use `@ConfigurationProperties` for type-safe configuration
- Utilize profiles for environment-specific configuration

## 4. Database Access
- Use Spring Data repositories when possible
- Implement auditing using `@EnableJpaAuditing`
- Use database migrations tools (e.g., Flyway or Liquibase)
- Implement proper transaction management

## 5. REST APIs
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Implement proper error handling using `@ControllerAdvice`
- Use DTOs to control API responses
- Implement pagination for large data sets
- Use proper HTTP status codes

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

## 6. Security
- Use Spring Security for authentication and authorization
- Implement CSRF protection
- Use secure session management
- Implement proper password encoding
- Use OAuth2 for third-party authentication

## 7. Testing
- Use `@SpringBootTest` for integration tests
- Use `@WebMvcTest` for controller tests
- Use `@DataJpaTest` for repository tests
- Utilize `TestRestTemplate` or `WebTestClient` for API tests

## 8. Caching
- Use Spring's caching abstraction
- Configure appropriate cache providers
- Set proper cache eviction policies
- Use cache annotations effectively

```java
@Service
public class UserService {
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
```

## 9. Logging
- Use SLF4J with Logback
- Configure appropriate log levels for different environments
- Include request IDs in logs for traceability
- Implement structured logging for better analysis

## 10. Monitoring and Actuator
- Enable appropriate actuator endpoints
- Implement health checks
- Use Micrometer for metrics collection
- Configure proper security for actuator endpoints

## 11. Exception Handling
- Create custom exceptions for your domain
- Use `@ExceptionHandler` and `@ControllerAdvice`
- Return appropriate error responses
- Log exceptions properly

## 12. Documentation
- Use Springdoc or Springfox for API documentation
- Document all public APIs
- Keep documentation up-to-date
- Include examples in API documentation
