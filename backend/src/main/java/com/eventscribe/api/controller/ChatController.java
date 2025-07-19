
package com.eventscribe.api.controller;

import com.eventscribe.api.dto.ChatRequest;
import com.eventscribe.api.dto.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping
    public ResponseEntity<ChatResponse> processChat(@RequestBody ChatRequest chatRequest) {
        // Simple echo response for now
        String response = "I received your message: " + chatRequest.getMessage();
        
        return ResponseEntity.ok(new ChatResponse(response));
    }
}
