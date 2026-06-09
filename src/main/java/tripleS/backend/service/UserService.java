package tripleS.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripleS.backend.entity.Mentor;
import tripleS.backend.entity.User;
import tripleS.backend.repository.userRepo;
import tripleS.backend.repository.userRepo;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    private final userRepo userRepository;


    public UserService(userRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Set<Mentor> getMentorsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        // Triggering the collection initialization within the active transaction
        return user.getMentors();
    }
}