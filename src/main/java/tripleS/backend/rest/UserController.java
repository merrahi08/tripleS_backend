package tripleS.backend.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import tripleS.backend.entity.Mentor;
import tripleS.backend.service.UserService;
import tripleS.backend.entity.User;
import tripleS.backend.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:5174")
public class UserController {

    @Autowired
    private userRepo userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    public UserController(userRepo userRepo, PasswordEncoder passwordEncoder,UserService us) {
        this.userRepository = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = us;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // 1. Check if email already exists in the PostgreSQL database
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Cet email est déjà utilisé par un autre compte.");
            }

            // 2. Save the user object into PostgreSQL via JpaRepository
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            // 3. Replace the plain text password with the hashed one
            user.setPassword(hashedPassword);
            User savedUser = userRepository.save(user);

            // 3. Return the saved user data back to React with a 201 Created status
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur lors de l'inscription : " + e.getMessage());
        }
    }
    @PutMapping("/{id}/tier")
    public ResponseEntity<User> updateSubscriptionTier(@PathVariable Long id, @RequestParam String tier) {
        // Enforce upper-case to avoid mismatches (e.g., "STANDARD", "PREMIUM", "GRATUIT")
        String formattedTier = tier.toUpperCase();

        return userRepository.findById(id)
                .map(user -> {
                    user.setSelectedTier(formattedTier);
                    User updatedUser = userRepository.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{userId}/mentors")
    public ResponseEntity<Set<Mentor>> getUserMentors(@PathVariable Long userId) {
        Set<Mentor> mentors = userService.getMentorsByUserId(userId);
        return ResponseEntity.ok(mentors);
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }
}