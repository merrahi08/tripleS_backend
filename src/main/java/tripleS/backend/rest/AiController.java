package tripleS.backend.rest;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripleS.backend.dto.AiRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/audit")
    public ResponseEntity<Map<String, String>> analyzeStartupIdea(@RequestBody AiRequest request) {
        try {
            String finalPrompt = """
                Be brief.
                Do not exceed 10 lines.
                Answer clearly.
                

                User prompt:
                """ + request.prompt();

            String aiResponse = this.chatClient.prompt()
                    .user(finalPrompt)
                    .call()
                    .content();

            return ResponseEntity.ok(Map.of("analysis", aiResponse));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getClass().getSimpleName() + ": " + e.getMessage()
            ));
        }
    }
}