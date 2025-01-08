# Essential Spring Data JPA Repository Guide

## 1. Most Used Default Methods
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Most commonly used default methods:
    Product save(Product entity);                    // Create or Update
    Optional<Product> findById(Long id);             // Read
    List<Product> findAll();                         // Read all
    void deleteById(Long id);                        // Delete
    boolean existsById(Long id);                     // Check existence
    Page<Product> findAll(Pageable pageable);        // Pagination
}
```

## 2. Essential Query Methods
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Basic finder methods
    Optional<Product> findByName(String name);
    List<Product> findByCategory(String category);
    
    // Multiple conditions
    List<Product> findByCategoryAndPriceGreaterThan(String category, BigDecimal price);
    
    // Ordering
    List<Product> findByCategoryOrderByPriceDesc(String category);
    
    // Like/Contains queries
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Pagination
    Page<Product> findByCategory(String category, Pageable pageable);
}
```

## 3. Important Custom Queries
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JPQL query with named parameters
    @Query("SELECT p FROM Product p WHERE p.price > :minPrice AND p.category = :category")
    List<Product> findExpensiveProducts(
        @Param("minPrice") BigDecimal minPrice, 
        @Param("category") String category
    );
    
    // Native SQL query
    @Query(value = "SELECT * FROM products WHERE YEAR(created_date) = :year", 
           nativeQuery = true)
    List<Product> findByYear(@Param("year") int year);
    
    // Update query
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.price = p.price * :factor WHERE p.category = :category")
    int updatePricesByCategory(@Param("factor") BigDecimal factor, @Param("category") String category);
}
```

## 4. Practical Pagination
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Method with pagination
    Page<Product> findByCategory(String category, Pageable pageable);
}

// Usage example:
public Page<Product> getProductsByCategory(String category, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size, 
        Sort.by("price").descending());
    return productRepository.findByCategory(category, pageRequest);
}
```

## 5. Essential Specifications (for Dynamic Queries)
```java
public class ProductSpecs {
    public static Specification<Product> withCategory(String category) {
        return (root, query, cb) -> {
            if (category == null) return null;
            return cb.equal(root.get("category"), category);
        };
    }
    
    public static Specification<Product> priceBetween(
            BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("price"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.between(root.get("price"), min, max);
        };
    }
}

// Usage
public interface ProductRepository extends JpaRepository<Product, Long>, 
    JpaSpecificationExecutor<Product> {
}

// Service layer
public List<Product> findProducts(String category, BigDecimal minPrice, 
    BigDecimal maxPrice) {
    return productRepository.findAll(
        where(withCategory(category))
        .and(priceBetween(minPrice, maxPrice))
    );
}
```

## 6. Important Projections
```java
// For better performance when you don't need all fields
public interface ProductSummary {
    String getName();
    BigDecimal getPrice();
    String getCategory();
}

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductSummary> findByCategory(String category);
}
```

## 7. Best Practices & Tips

### Performance
```java
// 1. Use projections for partial data
List<ProductSummary> findByCategory(String category);

// 2. Always use pagination for large datasets
Page<Product> findAll(Pageable pageable);

// 3. Avoid N+1 problems with fetch joins
@Query("SELECT p FROM Product p LEFT JOIN FETCH p.reviews WHERE p.category = :category")
List<Product> findByCategoryWithReviews(@Param("category") String category);
```

### Query Writing
```java
// 1. Use named parameters instead of positional parameters
@Query("SELECT p FROM Product p WHERE p.price > :price")  // Good
@Query("SELECT p FROM Product p WHERE p.price > ?1")      // Avoid

// 2. Keep queries readable with line breaks
@Query("SELECT p FROM Product p " +
       "WHERE p.price > :minPrice " +
       "AND p.category = :category " +
       "ORDER BY p.name")
List<Product> findProducts(...);

// 3. Use optional parameters with specifications instead of multiple methods
Specification<Product> spec = Specification.where(null);
if (category != null) {
    spec = spec.and(withCategory(category));
}
if (minPrice != null) {
    spec = spec.and(priceGreaterThan(minPrice));
}
```

### Common Gotchas
1. Always use `@Modifying` with `@Query` for UPDATE/DELETE
2. Remember `@Transactional` for modifying operations
3. Clear first-level cache with `@Modifying(clearAutomatically = true)`
4. Use `Optional<>` for single result queries that might return null

This covers the most important and frequently used features of Spring Data JPA repositories. These are the features you'll use in 90% of your applications.

Would you like me to:
1. Add examples for specific common use cases?
2. Include transaction management examples?
3. Add more performance optimization tips?