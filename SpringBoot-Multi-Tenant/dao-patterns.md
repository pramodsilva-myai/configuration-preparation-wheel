# Practical DAO Implementation Guide

## 1. Core DAO Patterns

### Traditional DAO Pattern
```java
// Interface
public interface UserDao {
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    void delete(Long id);
}

// Implementation
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }
    
    @Override
    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }
}
```

### Generic DAO Pattern
```java
// Generic Interface
public interface GenericDao<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void delete(ID id);
}

// Generic Implementation
@Repository
public abstract class GenericDaoImpl<T, ID> implements GenericDao<T, ID> {
    @PersistenceContext
    protected EntityManager em;
    
    private final Class<T> entityClass;
    
    protected GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }
    
    @Override
    @Transactional
    public T save(T entity) {
        em.persist(entity);
        return entity;
    }
}

// Specific Implementation
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> {
    public UserDaoImpl() {
        super(User.class);
    }
    
    // Specific methods for User
    public List<User> findByRole(String role) {
        return em.createQuery(
            "SELECT u FROM User u WHERE u.role = :role", User.class)
            .setParameter("role", role)
            .getResultList();
    }
}
```

## 2. Practical Query Methods

### Common JPQL Queries
```java
@Repository
public class ProductDaoImpl {
    @PersistenceContext
    private EntityManager em;
    
    // Basic search with pagination
    public List<Product> findByCategory(String category, int page, int size) {
        return em.createQuery(
            "SELECT p FROM Product p WHERE p.category = :category", Product.class)
            .setParameter("category", category)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .getResultList();
    }
    
    // Aggregation query
    public BigDecimal calculateTotalRevenue() {
        return em.createQuery(
            "SELECT SUM(p.price * p.quantity) FROM Product p", BigDecimal.class)
            .getSingleResult();
    }
    
    // Join query
    public List<OrderDTO> findOrdersWithDetails() {
        return em.createQuery(
            "SELECT NEW com.example.OrderDTO(o.id, o.date, c.name) " +
            "FROM Order o JOIN o.customer c", OrderDTO.class)
            .getResultList();
    }
}
```

### Using Criteria API
```java
@Repository
public class UserDaoImpl {
    @PersistenceContext
    private EntityManager em;
    
    // Dynamic search
    public List<User> searchUsers(UserSearchCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (criteria.getName() != null) {
            predicates.add(cb.like(root.get("name"), 
                "%" + criteria.getName() + "%"));
        }
        
        if (criteria.getRole() != null) {
            predicates.add(cb.equal(root.get("role"), 
                criteria.getRole()));
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        return em.createQuery(query)
            .setMaxResults(criteria.getLimit())
            .getResultList();
    }
}
```

## 3. Performance Optimizations

### Batch Processing
```java
@Repository
public class BulkOperationDao {
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void batchInsert(List<User> users, int batchSize) {
        for (int i = 0; i < users.size(); i++) {
            em.persist(users.get(i));
            if (i % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }
    }
    
    @Transactional
    public int batchUpdate(String role, boolean active) {
        return em.createQuery(
            "UPDATE User u SET u.active = :active " +
            "WHERE u.role = :role")
            .setParameter("active", active)
            .setParameter("role", role)
            .executeUpdate();
    }
}
```

### Caching
```java
@Repository
public class CacheAwareDao {
    @PersistenceContext
    private EntityManager em;
    
    @Cacheable("users")
    public User findByIdCached(Long id) {
        return em.find(User.class, id);
    }
    
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<User> findAllCached() {
        return em.createQuery("SELECT u FROM User u", User.class)
            .getResultList();
    }
}
```

## 4. Transaction Management

### Practical Transaction Examples
```java
@Repository
public class TransactionAwareDao {
    @PersistenceContext
    private EntityManager em;
    
    // Read-only transaction
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    // Required transaction
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        return em.merge(user);
    }
    
    // Nested transaction
    @Transactional(propagation = Propagation.NESTED)
    public void updateUserDetails(Long id, UserDetails details) {
        User user = em.find(User.class, id);
        user.setDetails(details);
    }
}
```

## 5. Best Practices

### Exception Handling
```java
@Repository
public class BestPracticeDao {
    @PersistenceContext
    private EntityManager em;
    
    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public User save(User user) {
        try {
            return em.merge(user);
        } catch (PersistenceException e) {
            throw new DaoException("Failed to save user", e);
        }
    }
}
```

### Performance Tips
1. Use `@BatchSize` for collections
2. Implement pagination for large result sets
3. Use projections instead of full entities when possible
4. Avoid N+1 queries with join fetches
5. Use query hints for performance tuning

### Naming Conventions
```java
public interface UserDao {
    // Find methods return Optional or null
    Optional<User> findByEmail(String email);
    
    // Get methods throw exception if not found
    User getByEmail(String email);
    
    // Search methods return collections
    List<User> searchByNamePattern(String pattern);
    
    // Count methods return numbers
    long countByRole(String role);
    
    // Update methods return affected rows
    int updateStatus(Long id, String status);
}
```

### Daily Use Tips
1. Always use parameterized queries to prevent SQL injection
2. Use `Optional` for single result queries
3. Implement proper transaction boundaries
4. Use appropriate fetch types (LAZY/EAGER)
5. Implement proper exception handling
6. Use batch operations for bulk updates
7. Cache frequently accessed data
8. Use query hints for performance optimization

Want me to expand on any of these sections or add more practical examples?