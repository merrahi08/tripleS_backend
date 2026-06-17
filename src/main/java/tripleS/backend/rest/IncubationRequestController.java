package tripleS.backend.rest;

import tripleS.backend.dto.CreateRequestDTO;
import tripleS.backend.entity.IncubationRequest;
import tripleS.backend.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripleS.backend.service.IncubationRequestService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class IncubationRequestController {

    private final IncubationRequestService requestService;

    public IncubationRequestController(
            IncubationRequestService requestService) {

        this.requestService = requestService;
    }

    @PostMapping("/create")
    public ResponseEntity<IncubationRequest> createRequest(
            @RequestBody CreateRequestDTO dto) {

        return ResponseEntity.ok(
                requestService.createRequest(dto)
        );
    }

    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<IncubationRequest>>
    getUserPendingRequests(@PathVariable Long userId) {

        return ResponseEntity.ok(
                requestService.getUserPendingRequests(userId)
        );
    }
    @GetMapping("/mentor/{userId}/pending")
    public ResponseEntity<List<IncubationRequest>> getMentorPendingRequests(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                requestService.getMentorPendingRequests(userId)
        );
    }

    @PutMapping("/{requestId}/claim")
    public ResponseEntity<?> claimRequest(
            @PathVariable Long requestId) {

        try {
            IncubationRequest updated =
                    requestService.claimRequest(requestId);

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}