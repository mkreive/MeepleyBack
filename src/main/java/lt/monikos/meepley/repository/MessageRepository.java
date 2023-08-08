package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByUserEmail(@RequestParam("user_email") String userEmail);
}
