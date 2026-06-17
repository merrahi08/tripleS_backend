package tripleS.backend.rest;

import tripleS.backend.dto.LoginRequest;
import tripleS.backend.entity.User;
import tripleS.backend.repository.userRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final userRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // Injecting dependencies via constructor
    public AuthController(userRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        // 1. Look up the user by email
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect.");
        }

        User user = userOptional.get();

        // 2. Safely verify the password using matches()
        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect.");
        }
        // 3. Return the complete user entity (including name, plan, role) to React
        return ResponseEntity.ok(user);
    }
}