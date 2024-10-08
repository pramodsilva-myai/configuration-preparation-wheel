// 1. Bidirectional One-to-One Relationship
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmployeeDetail employeeDetail;
    
    // Helper method for bidirectional relationship
    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
        if (employeeDetail == null) {
            if (this.employeeDetail != null) {
                this.employeeDetail.setEmployee(null);
            }
        } else {
            employeeDetail.setEmployee(this);
        }
        this.employeeDetail = employeeDetail;
    }
}

@Entity
public class EmployeeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;
}

// 2. Unidirectional One-to-Many with Join Table
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "department_employees",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees = new ArrayList<>();
}

// 3. Bidirectional Many-to-Many with Additional Attributes
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseEnrollment> enrollments = new HashSet<>();
    
    public void enrollInCourse(Course course, String grade) {
        CourseEnrollment enrollment = new CourseEnrollment(this, course, grade);
        enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseEnrollment> enrollments = new HashSet<>();
}

@Entity
public class CourseEnrollment {
    @EmbeddedId
    private CourseEnrollmentId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;
    
    private String grade;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentDate;
    
    public CourseEnrollment(Student student, Course course, String grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.id = new CourseEnrollmentId(student.getId(), course.getId());
        this.enrollmentDate = new Date();
    }
}

@Embeddable
public class CourseEnrollmentId implements Serializable {
    private Long studentId;
    private Long courseId;
    
    // Constructors, equals, and hashCode
}

// 4. Embeddable Type Example
@Embeddable
public class Address {
    private String street;
    private String city;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Enumerated(EnumType.STRING)
    private CountryCode country;
}

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Embedded
    private Address address;
    
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "shipping_country"))
    })
    @Embedded
    private Address shippingAddress;
}

// 5. Element Collection Example
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ElementCollection
    @CollectionTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "category")
    private Set<String> categories = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(
        name = "product_ratings",
        joinColumns = @JoinColumn(name = "product_id")
    )
    private List<Rating> ratings = new ArrayList<>();
}

@Embeddable
public class Rating {
    private Integer score;
    private String comment;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date ratedAt;
}

// 6. Enum Handling
public enum EmployeeStatus {
    FULL_TIME, PART_TIME, CONTRACT, INTERN
}

@Entity
public class EmployeePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    
    @ElementCollection
    @CollectionTable(
        name = "employee_responsibilities",
        joinColumns = @JoinColumn(name = "position_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<Responsibility> responsibilities = EnumSet.noneOf(Responsibility.class);
}

public enum Responsibility {
    TEAM_LEAD, 
    PROJECT_MANAGEMENT, 
    CODING, 
    TESTING, 
    DEPLOYMENT, 
    MENTORING
}

// 7. Best Practices for Using These Relationships

/*
1. Bidirectional Relationship Management:
   - Always use helper methods to manage both sides of bidirectional relationships
   - Consider using @ToString.Exclude (Lombok) to prevent infinite recursion in toString()
   
2. Performance Considerations:
   - Use fetch = FetchType.LAZY for most relationships to prevent unnecessary data loading
   - Consider using @BatchSize for collections to optimize batch fetching
   
3. Cascade Operations:
   - Be careful with CascadeType.ALL, especially in bidirectional relationships
   - Consider using more specific cascade types (PERSIST, MERGE) when appropriate
   
4. Element Collections:
   - Use when you need to store simple collections of values or embeddable objects
   - Be aware that updates to element collections replace all entries
   
5. Embeddable Types:
   - Use to group related fields together
   - Consider using AttributeOverride when reusing the same embeddable type
   
6. Enums:
   - Prefer EnumType.STRING for better readability and maintainability
   - Use when you have a fixed set of values that rarely change

7. Indexing:
   @Index(name = "idx_employee_email", columnList = "email")
   - Add appropriate indexes for columns used in queries
*/
