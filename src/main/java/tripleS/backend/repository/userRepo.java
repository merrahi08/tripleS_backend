package tripleS.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripleS.backend.entity.User;
import java.lang.*;
import java.util.Optional;

public interface userRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String mail);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.mentors WHERE u.id = :userId")
    Optional<User> findByIdWithMentors(@Param("userId") Long userId);
}
