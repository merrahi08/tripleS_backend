package tripleS.backend.service;

import tripleS.backend.entity.ClientMentor;
import tripleS.backend.entity.IncubationRequest;
import tripleS.backend.entity.RequestStatus;
import tripleS.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripleS.backend.repository.ClientMentorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncubationRequestService {

    private final IncubationRequestRepository requestRepository;
    // Inject your existing client_mentor repository here
     private final ClientMentorRepository clientMentorRepository;

    public IncubationRequestService(IncubationRequestRepository requestRepository, ClientMentorRepository clientMentorRepository1) {
        this.requestRepository = requestRepository;
        this.clientMentorRepository = clientMentorRepository1;
    }

    public IncubationRequest createRequest(IncubationRequest request) {
        request.setStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    public List<IncubationRequest> getOpenRequests() {
        return requestRepository.findByStatus(RequestStatus.PENDING);
    }
    public List<IncubationRequest> getPendingRequestsByMentorUserId(Long userId) {
        // Any extra business rules, validations, or loggers can be added here cleanly
        return requestRepository.findPendingRequestsByMentorUserId(userId);
    }

    @Transactional
    public IncubationRequest claimRequest(Long requestId, Long mentorId) {
        IncubationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("This request has already been claimed.");
        }

        // 1. Update the request marketplace state
        request.setMentorId(mentorId);
        request.setStatus(RequestStatus.ASSIGNED);
        IncubationRequest updatedRequest = requestRepository.save(request);

        // 2. TODO: Insert into your existing client_mentor table here
         ClientMentor link = new ClientMentor(request.getUserId(), mentorId, LocalDateTime.now());
         clientMentorRepository.save(link);

        return updatedRequest;
    }
}