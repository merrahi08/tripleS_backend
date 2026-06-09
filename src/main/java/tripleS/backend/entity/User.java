package tripleS.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // Maps this class to the 'users' table in PostgreSQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Matches PostgreSQL BIGSERIAL
    private Long id;

    public User(String name, String email, String password, String projectIdea, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.projectIdea = projectIdea;
        this.createdAt = createdAt;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String projectIdea;

    @Column(name = "selected_tier")
    private String selectedTier = "GRATUIT";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String role = "USER"; // This sets a default value of 'USER' for new accounts

    // Add these getter and setter methods at the bottom of the class:
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "client_mentor",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "mentor_id")
    )
    @JsonIgnoreProperties("clients") // Prevents infinite recursion loops when converting to JSON
    private Set<Mentor> mentors = new HashSet<>();

    // --- Add Getter and Setter at the bottom ---
    public Set<Mentor> getMentors() { return mentors; }
    public void setMentors(Set<Mentor> mentors) { this.mentors = mentors; }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Lifecycle hook to automatically set the date before inserting into DB
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Generate Getters and Setters below ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User() {
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProjectIdea() { return projectIdea; }
    public void setProjectIdea(String projectIdea) { this.projectIdea = projectIdea; }

    public String getSelectedTier() { return selectedTier; }
    public void setSelectedTier(String selectedTier) { this.selectedTier = selectedTier; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}