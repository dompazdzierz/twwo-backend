package pl.pwr.dissertation.api.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.pwr.dissertation.api.controller.DissertationController;
import pl.pwr.dissertation.logic.domain.FileWrapper;
import pl.pwr.dissertation.logic.domain.Message;
import pl.pwr.dissertation.logic.domain.User;
import pl.pwr.dissertation.logic.service.DissertationService;

import java.util.List;

@RestController
@RequestMapping("dissertation")
@RequiredArgsConstructor
public class DissertationControllerImpl extends BaseController implements DissertationController {

    private final DissertationService dissertationService;

    @Override
    public void sendMessage(MultipartFile file, Message message) {
        User user = getCurrentUser();
        message.setAuthorId(user.getId());
        dissertationService.sendMessage(new FileWrapper(), message);
    }

    @Override
    public List<Message> getMessages(int dissertationId) {
        return dissertationService.getMessages(dissertationId);
    }

    @Override
    public ResponseEntity<byte[]> getFile(int dissertationId, int fileId) {
        return this.generateFileResponse(this.dissertationService.getFile(dissertationId, fileId), false);
    }
}
