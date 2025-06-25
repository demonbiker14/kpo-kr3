package payments.messaging.inbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepository extends JpaRepository<InboxMessage, String> {
}