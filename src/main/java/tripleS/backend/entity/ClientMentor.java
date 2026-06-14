package tripleS.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_mentor")
public class ClientMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "mentor_id", nullable = false)
    private Long mentorId;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    // No-arg constructor required by JPA
    public ClientMentor() {}

    // Convenience constructor matching your
    public ClientMentor(Long clientId, Long mentorId, LocalDateTime assignedAt) {
        this.clientId = clientId;
        this.mentorId = mentorId;
        this.assignedAt = assignedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getMentorId() { return mentorId; }
    public void setMentorId(Long mentorId) { this.mentorId = mentorId; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
}