package tripleS.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripleS.backend.entity.Mentor;
import tripleS.backend.entity.User;
import tripleS.backend.repository.mentorRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
@CrossOrigin(origins = "http://localhost:5173")
public class MentorController {
    @Autowired
    private mentorRepo mentorRepository;
    MentorController(mentorRepo mr){
        this.mentorRepository = mr;
    }
    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    // 2. Get a single mentor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        return mentorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // 3. Fetch all assigned clients for a specific mentor by their User ID
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getMyClients(@RequestParam Long userId) {
        List<User> clients = mentorRepository.findClientsByMentorUserId(userId);
        return ResponseEntity.ok(clients);
    }


}
