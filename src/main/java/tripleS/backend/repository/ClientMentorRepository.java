package tripleS.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tripleS.backend.entity.ClientMentor;

@Repository
public interface ClientMentorRepository extends JpaRepository<ClientMentor, Long> {
    // Standard CRUD save operational hooks are instantly available
}