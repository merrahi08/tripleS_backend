package tripleS.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import tripleS.backend.dto.MentorRegisterDTO;
import tripleS.backend.entity.Mentor;
import tripleS.backend.entity.User;
import tripleS.backend.repository.mentorRepo;
import tripleS.backend.repository.userRepo;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private mentorRepo mentorRepository;

    @Autowired
    private userRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    MentorController(mentorRepo mr, userRepo ur) {
        this.mentorRepository = mr;
        this.userRepository = ur;
    }

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable Long id) {
        return mentorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clients")
    public ResponseEntity<List<User>> getMyClients(@RequestParam Long userId) {
        List<User> clients = mentorRepository.findClientsByMentorUserId(userId);
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/register-full")
    public ResponseEntity<?> registerMentor(
            @RequestBody MentorRegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Cet email existe déjà.");
        }

        // Create User
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());


        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashedPassword);

        user.setRole("MENTOR");
        user.setSelectedTier("GRATUIT");

        User savedUser = userRepository.save(user);

        // Create Mentor
        Mentor mentor = new Mentor();

        mentor.setUser(savedUser);


        mentor.setName(dto.getName());
        mentor.setEmail(dto.getEmail());

        mentor.setTitle(dto.getTitle());
        mentor.setExpertise(dto.getExpertise());
        mentor.setBio(dto.getBio());

        mentor.setLinkedinUrl(dto.getLinkedinUrl());
        mentor.setImageUrl(dto.getImageUrl());

        mentorRepository.save(mentor);

        return ResponseEntity.ok(savedUser);
    }
}