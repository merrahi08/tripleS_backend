package tripleS.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tripleS.backend.entity.Mentor;
import tripleS.backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface mentorRepo extends JpaRepository<Mentor,Long> {
    Optional<Mentor> findByUserId(Long userId);

    // Custom query to fetch all active clients assigned to a specific mentor's user ID
    @Query("SELECT m.clients FROM Mentor m WHERE m.user.id = :userId")
    List<User> findClientsByMentorUserId(@Param("userId") Long userId);
}
