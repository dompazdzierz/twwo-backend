package pl.pwr.dissertation.api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import pl.pwr.dissertation.logic.domain.Message;

import java.util.List;

public interface DissertationController {

    @PostMapping("/message")
    void sendMessage(@RequestPart(required = false) MultipartFile file, @RequestPart Message message);

    @GetMapping("{dissertationId}")
    List<Message> getMessages(@PathVariable int dissertationId);

    @GetMapping("{dissertationId}/file/{fileId}")
    ResponseEntity<byte[]> getFile(@PathVariable int dissertationId, @PathVariable int fileId);

}
