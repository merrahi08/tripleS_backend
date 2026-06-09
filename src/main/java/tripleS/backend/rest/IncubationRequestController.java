package tripleS.backend.rest;

import tripleS.backend.entity.IncubationRequest;
import tripleS.backend.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripleS.backend.service.IncubationRequestService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class IncubationRequestController {

    private final IncubationRequestService requestService;


    public IncubationRequestController(IncubationRequestService requestService) {
        this.requestService = requestService;
    }

    // 1. Clients hit this to post a new business incubation need
    @PostMapping("/create")
    public ResponseEntity<IncubationRequest> createRequest(@RequestBody IncubationRequest request) {
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    // 2. Mentors hit this to see the active "Hub" of open proposals
    @GetMapping("/open")
    public ResponseEntity<List<IncubationRequest>> getOpenRequests() {
        return ResponseEntity.ok(requestService.getOpenRequests());
    }
    @GetMapping("/pending")
    public ResponseEntity<List<IncubationRequest>> getMyPendingRequests(@RequestParam Long userId) {
        List<IncubationRequest> requests = requestService.getPendingRequestsByMentorUserId(userId);
        return ResponseEntity.ok(requests);
    }

    // 3. Mentors hit this to claim an open client demand
    @PutMapping("/{id}/claim")
    public ResponseEntity<?> claimRequest(@PathVariable Long id, @RequestParam Long mentorId) {
        try {
            IncubationRequest updated = requestService.claimRequest(id, mentorId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}