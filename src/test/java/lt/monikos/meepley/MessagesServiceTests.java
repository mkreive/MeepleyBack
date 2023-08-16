package lt.monikos.meepley;

import lt.monikos.meepley.entity.Message;
import lt.monikos.meepley.repository.MessageRepository;
import lt.monikos.meepley.requestModels.AdminQuestionRequest;
import lt.monikos.meepley.service.MessagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessagesServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessagesService messagesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostMessage() {
        String userEmail = "user@example.com";
        Message messageRequest = new Message();
        messageRequest.setId(1L);
        messageRequest.setUserEmail(userEmail);
        messageRequest.setTitle("Message title");
        messageRequest.setQuestion("Message question");
        messageRequest.setAdminEmail(null);
        messageRequest.setResponse(null);
        messageRequest.setClosed(false);

        messagesService.postMessage(messageRequest, userEmail);

        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void testPutMessage() throws Exception {
        String userEmail = "admin@example.com";
        AdminQuestionRequest adminQuestionRequest = new AdminQuestionRequest();
        adminQuestionRequest.setId(1L);
        adminQuestionRequest.setResponse("Admin response");
        Long messageId = 1L;

        Message existingMessage = new Message();
        existingMessage.setId(messageId);
        existingMessage.setUserEmail(userEmail);
        existingMessage.setTitle("Message title");
        existingMessage.setQuestion("Message question");
        existingMessage.setAdminEmail(null);
        existingMessage.setResponse(null);
        existingMessage.setClosed(false);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));

        messagesService.putMessage(adminQuestionRequest, userEmail);

        assertTrue(existingMessage.isClosed());
        assertEquals(adminQuestionRequest.getResponse(), existingMessage.getResponse());
        assertEquals(userEmail, existingMessage.getAdminEmail());
        verify(messageRepository).save(existingMessage);
    }

    @Test
    void testPutMessage_MessageNotFound() {
        String userEmail = "admin@example.com";
        AdminQuestionRequest adminQuestionRequest = new AdminQuestionRequest();
        adminQuestionRequest.setId(1L);
        adminQuestionRequest.setResponse("Admin response");
        Long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> messagesService.putMessage(adminQuestionRequest, userEmail));
    }
}