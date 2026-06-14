package tripleS.backend.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tripleS.backend.entity.IncubationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tripleS.backend.entity.RequestStatus;

import java.util.List;

@Repository
public interface IncubationRequestRepository extends JpaRepository<IncubationRequest, Long> {

    // Spring Data automatically generates the SQL to find requests by status
    List<IncubationRequest> findByStatus(RequestStatus status);

    // Optional: Find requests created by a specific client
    List<IncubationRequest> findByUserId(Long userId);
    List<IncubationRequest> findByUser_Id(Long userId);
    List<IncubationRequest> findByUser_IdAndStatus(
            Long userId,
            RequestStatus status
    );
    List<IncubationRequest> findByMentorId(Long mentorId);
//    @Query("SELECT r FROM IncubationRequest r, Mentor m WHERE r.mentorId = m.id AND m.user.id = :userId AND r.status = tripleS.backend.entity.RequestStatus.PENDING")
//    List<IncubationRequest> findPendingRequestsByMentorUserId(@Param("userId") Long userId);
}