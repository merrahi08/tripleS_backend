package tripleS.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import tripleS.backend.dto.CreateRequestDTO;
import tripleS.backend.entity.*;
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
     @Autowired
     private  userRepo userRepository;
     @Autowired
     private  mentorRepo mentorRepository;

    public IncubationRequestService(IncubationRequestRepository requestRepository, ClientMentorRepository clientMentorRepository1) {
        this.requestRepository = requestRepository;
        this.clientMentorRepository = clientMentorRepository1;
    }

    public IncubationRequest createRequest(CreateRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow();

        Mentor mentor = mentorRepository.findById(dto.getMentorId())
                .orElseThrow();

        IncubationRequest request = new IncubationRequest();

        request.setUser(user);
        request.setMentor(mentor);
        request.setSubject(dto.getSubject());
        request.setDescription(dto.getDescription());
        request.setStatus(RequestStatus.PENDING);

        return requestRepository.save(request);
    }
    public List<IncubationRequest> getUserPendingRequests(Long userId) {
        return requestRepository.findByUser_IdAndStatus(
                userId,
                RequestStatus.PENDING
        );
    }

    @Transactional
    public IncubationRequest claimRequest(Long requestId) {
        IncubationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("This request has already been claimed.");
        }

        // 1. Update the request marketplace state
        request.setStatus(RequestStatus.ASSIGNED);
        IncubationRequest updatedRequest = requestRepository.save(request);

        // 2. TODO: Insert into your existing client_mentor table here
         ClientMentor link = new ClientMentor(request.getUser().getId(),request.getMentor().getId(), LocalDateTime.now());
         clientMentorRepository.save(link);

        return updatedRequest;
    }
}