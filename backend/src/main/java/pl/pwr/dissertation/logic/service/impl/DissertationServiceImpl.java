package pl.pwr.dissertation.logic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwr.dissertation.api.exception.BadRequestException;
import pl.pwr.dissertation.logic.domain.FileWrapper;
import pl.pwr.dissertation.logic.domain.Message;
import pl.pwr.dissertation.logic.service.DissertationService;
import pl.pwr.dissertation.persistance.entity.MessageEntity;
import pl.pwr.dissertation.persistance.entity.TopicEntity;
import pl.pwr.dissertation.persistance.repository.MessageRepository;
import pl.pwr.dissertation.persistance.repository.TopicRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DissertationServiceImpl implements DissertationService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    @Override
    public void sendMessage(FileWrapper file, Message message) {

        TopicEntity topicEntity = topicRepository.findById(message.getTopicId()).orElseThrow(BadRequestException::new);

        boolean isPromoter = topicEntity.getPromoter().getId() == message.getAuthorId();
        boolean isStudent = topicEntity.getStudent().getId() == message.getAuthorId();

        if (!isPromoter && !isStudent) {
            throw new BadRequestException();
        }

        MessageEntity messageEntity = MessageEntity.builder()
                .text(message.getText())
                .topic(topicEntity)
                .build();

        messageRepository.save(messageEntity);
    }

    @Override
    public List<Message> getMessages(int dissertationId) {
        return this.messageRepository.findAllByTopicId(dissertationId).stream().map(this::mapToDomain).toList();
    }

    private Message mapToDomain(MessageEntity messageEntity) {

        return Message.builder()
                .authorId(messageEntity.getCreatedBy().getId())
                .topicId(messageEntity.getTopic().getId())
                .timeUTC(messageEntity.getCreatedDate())
                .text(messageEntity.getText())
                .filename("")
                .fileId(0)
                .build();
    }

    @Override
    public FileWrapper getFile(int dissertationId, int fileId) {
        return null;
    }
}
