package tripleS.backend.entity; // Adjust package name to match yours

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "mentors")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 150)
    private String expertise;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    // The inverse side of the Many-to-Many relationship
    // mappedBy points to the "mentors" field inside your User.java entity
    @ManyToMany(mappedBy = "mentors")
    @JsonIgnoreProperties("mentors") // Prevents infinite recursion loops when converting to JSON
    private Set<User> clients = new HashSet<>();


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"mentors", "password"}) // Protect security & avoid recursion loops
    private User user;

    // Add Getter and Setter
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // --- Constructors ---
    public Mentor() {}

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExpertise() { return expertise; }
    public void setExpertise(String expertise) { this.expertise = expertise; }

    public String getBio() { return bio; }
    public void setBio(String bio){
        this.bio=bio;
    }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public ZonedDateTime getCreatedAt() { return createdAt; }

    public Set<User> getClients() { return clients; }
    public void setClients(Set<User> clients) { this.clients = clients; }
}