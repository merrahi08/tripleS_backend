package tripleS.backend.rest;

import tripleS.backend.dto.SendMessageToMentorRequest;
import tripleS.backend.entity.Message;
import tripleS.backend.entity.Mentor;
import tripleS.backend.repository.MessageRepository;
import tripleS.backend.repository.mentorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private mentorRepo mentorRepository;

    /**
     * Normal send: senderId and receiverId are already user IDs.
     */
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        if (
                message.getSenderId() == null ||
                        message.getReceiverId() == null ||
                        message.getContent() == null ||
                        message.getContent().trim().isEmpty()
        ) {
            return ResponseEntity.badRequest().build();
        }

        message.setContent(message.getContent().trim());

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * Client sends message to a mentor using mentor.id.
     * Backend converts mentor.id to mentor.user.id before saving.
     */
    @PostMapping("/send-to-mentor")
    public ResponseEntity<?> sendMessageToMentor(@RequestBody SendMessageToMentorRequest request) {
        if (
                request.getSenderId() == null ||
                        request.getMentorId() == null ||
                        request.getContent() == null ||
                        request.getContent().trim().isEmpty()
        ) {
            return ResponseEntity.badRequest().body("senderId, mentorId and content are required.");
        }

        Mentor mentor = mentorRepository.findById(request.getMentorId()).orElse(null);

        if (mentor == null) {
            return ResponseEntity.badRequest().body("Mentor not found.");
        }

        if (mentor.getUser() == null || mentor.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("This mentor is not linked to a user account.");
        }

        Long mentorUserId = mentor.getUser().getId();

        Message message = new Message();
        message.setSenderId(request.getSenderId());
        message.setReceiverId(mentorUserId);
        message.setContent(request.getContent().trim());

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * Get conversation between two user IDs.
     */
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam("user1") Long user1,
            @RequestParam("user2") Long user2) {

        List<Message> conversation = messageRepository.findConversation(user1, user2);
        return ResponseEntity.ok(conversation);
    }
}