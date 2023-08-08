package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Message;
import lt.monikos.meepley.entity.Token;
import lt.monikos.meepley.service.MessagesService;
import lt.monikos.meepley.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        messagesService.postMessage(messageRequest, extracted.getSub());
    }

//    @PutMapping("/secure/admin/message")
//    public void putMessage(@RequestHeader(value="Authorization") String token,
//                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
//        if (admin == null || !admin.equals("admin")) {
//            throw new Exception("Administration page only.");
//        }
//        messagesService.putMessage(adminQuestionRequest, userEmail);
//    }
}
