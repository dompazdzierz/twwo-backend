package pl.pwr.dissertation.logic.service;

import pl.pwr.dissertation.logic.domain.FileWrapper;
import pl.pwr.dissertation.logic.domain.Message;

import java.util.List;

public interface DissertationService {

    void sendMessage(FileWrapper file, Message message);

    List<Message> getMessages(int dissertationId);

    FileWrapper getFile(int dissertationId, int fileId);

}
