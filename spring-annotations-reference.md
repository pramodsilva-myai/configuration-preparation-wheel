# Spring Framework Annotations Quick Reference Guide

## Table of Contents
1. [REST Controller Annotations](#1-rest-controller-annotations)
2. [Request Handling Annotations](#2-request-handling-annotations)
3. [Response Handling Annotations](#3-response-handling-annotations)
4. [JPA & Database Annotations](#4-jpa--database-annotations)
5. [Spring Security Annotations](#5-spring-security-annotations)
6. [Transaction Management Annotations](#6-transaction-management-annotations)
7. [Dependency Injection Annotations](#7-dependency-injection-annotations)
8. [Configuration Annotations](#8-configuration-annotations)
9. [Testing Annotations](#9-testing-annotations)
10. [Logging Annotations](#10-logging-annotations)

## 1. REST Controller Annotations

```java
@RestController
public class UserController {
    // Indicates this is a REST controller, combines @Controller and @ResponseBody
}

@RequestMapping("/api/v1")
public class ApiController {
    // Maps web requests onto specific handler classes/methods
}

@ControllerAdvice
public class GlobalExceptionHandler {
    // Allows to handle exceptions across the whole application
}

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // Marks a method or exception class with the status code and reason that should be returned
}
```

## 2. Request Handling Annotations

```java
@GetMapping("/users")
public List<User> getUsers() {}

@PostMapping("/users")
public User createUser(@RequestBody User user) {}

@PutMapping("/users/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {}

@DeleteMapping("/users/{id}")
public void deleteUser(@PathVariable Long id) {}

@PatchMapping("/users/{id}")
public User partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {}

// Request Parameter Annotations
public User findUser(@RequestParam String username) {}
public User findUser(@RequestParam(required = false, defaultValue = "test") String username) {}
public User findUser(@PathVariable Long id) {}
public User createUser(@Valid @RequestBody UserDTO userDTO) {}
public User updateUser(@RequestHeader("User-Agent") String userAgent) {}
```

## 3. Response Handling Annotations

```java
@ResponseBody
public User getUser() {}

@ResponseStatus(HttpStatus.CREATED)
public User createUser(@RequestBody User user) {}

// Common return types
public ResponseEntity<User> getUser() {
    return ResponseEntity.ok(user);
}

public ResponseEntity<User> createUser() {
    return ResponseEntity.created(location).body(user);
}
```

## 4. JPA & Database Annotations

### Entity Annotations
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
```

### Relationship Annotations
```java
// One-to-One
@OneToOne
@JoinColumn(name = "address_id")
private Address address;

// One-to-Many
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Order> orders;

// Many-to-One
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;

// Many-to-Many
@ManyToMany
@JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
)
private Set<Role> roles;
```

### Repository Annotations
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA repository
}

@Query("SELECT u FROM User u WHERE u.status = ?1")
List<User> findByStatus(UserStatus status);
```

## 5. Spring Security Annotations

```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {}

// Method Security
@PreAuthorize("hasRole('ADMIN')")
public void adminMethod() {}

@PostAuthorize("returnObject.userId == authentication.principal.id")
public User getUser() {}

@Secured({"ROLE_ADMIN", "ROLE_USER"})
public void someMethod() {}

// Controller Security
@CrossOrigin(origins = "http://example.com")
public class ApiController {}
```

## 6. Transaction Management Annotations

```java
@Transactional
public void createUser() {}

@Transactional(readOnly = true)
public User getUser() {}

@Transactional(rollbackFor = Exception.class)
public void riskyOperation() {}

@Transactional(isolation = Isolation.SERIALIZABLE)
public void sensitiveOperation() {}

@Transactional(propagation = Propagation.REQUIRES_NEW)
public void independentOperation() {}
```

## 7. Dependency Injection Annotations

```java
@Autowired
private UserService userService;

@Qualifier("primaryDataSource")
@Autowired
private DataSource dataSource;

@Value("${app.config.value}")
private String configValue;

@Resource(name = "specificBean")
private SomeService someService;
```

## 8. Configuration Annotations

```java
@Configuration
public class AppConfig {}

@Bean
public UserService userService() {}

@Component
public class GenericComponent {}

@Service
public class UserService {}

@Profile("development")
@Configuration
public class DevConfig {}

@Conditional(SomeCondition.class)
@Bean
public SomeBean someBean() {}
```

## 9. Testing Annotations

```java
@SpringBootTest
public class IntegrationTest {}

@WebMvcTest(UserController.class)
public class UserControllerTest {}

@DataJpaTest
public class RepositoryTest {}

@MockBean
private UserService userService;

@Test
public void testSomething() {}
```

## 10. Logging Annotations

```java
// Using Lombok for automatic logger creation
@Slf4j
public class UserService {
    // Usage: log.info("User {} created", user.getId());
}

// Custom logging annotation
@LogExecutionTime
public void performOperation() {}
```

## Best Practices

1. **REST APIs**
   - Use appropriate HTTP methods
   - Return proper status codes
   - Implement pagination for list endpoints
   - Version your APIs

2. **Security**
   - Combine method-level and URL-based security
   - Use authentication and authorization
   - Implement CORS carefully

3. **Database**
   - Use lazy loading when appropriate
   - Be careful with bidirectional relationships
   - Consider using DTOs

4. **Transactions**
   - Keep transactions short
   - Use readOnly when possible
   - Be aware of transaction boundaries

