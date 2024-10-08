# Spring Boot REST Exception Handling Guide

## 1. Global Exception Handler using @ControllerAdvice

```java
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Error details DTO
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // getters
}

// Custom exception
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

## 2. ResponseStatusException Approach

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", ex);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid user data", ex);
        }
    }
}
```

## Common HTTP Status Codes

```java
// 200 OK - Successful request
return ResponseEntity.ok(resource);

// 201 Created - Resource created successfully
return new ResponseEntity<>(newResource, HttpStatus.CREATED);

// 204 No Content - Successful request with no content to return
return ResponseEntity.noContent().build();

// 400 Bad Request - Invalid request
throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");

// 401 Unauthorized - Authentication required
throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");

// 403 Forbidden - Authenticated but not authorized
throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");

// 404 Not Found - Resource not found
throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");

// 409 Conflict - Request conflicts with current state
throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource already exists");

// 500 Internal Server Error - Unexpected server error
throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
```

## Best Practices

1. Use specific exceptions for different scenarios
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("User already exists with email: " + email);
    }
}
```

2. Implement a consistent error response structure
```java
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // getters
}
```

3. Log exceptions appropriately
```java
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unexpected error occurred", ex);
        
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "An unexpected error occurred",
            request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

4. Include validation handling
```java
@PostMapping
public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    return ResponseEntity.ok(userService.save(user));
}

// In the entity
public class User {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;
}
```

## Testing Exception Handling

```java
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetNonExistingUser_thenReturns404() throws Exception {
        mockMvc.perform(get("/api/users/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("User not found with id: 999"));
    }

    @Test
    public void whenCreateInvalidUser_thenReturns400() throws Exception {
        String user = "{\"name\":\"\",\"email\":\"invalid-email\"}";

        mockMvc.perform(post("/api/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(user))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.name").value("Name must be between 2 and 50 characters"))
               .andExpect(jsonPath("$.email").value("Email should be valid"));
    }
}
```
