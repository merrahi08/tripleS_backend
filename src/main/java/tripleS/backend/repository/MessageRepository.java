package tripleS.backend.repository;

import tripleS.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Récupère l'historique complet des échanges entre deux utilisateurs (A vers B ET B vers A)
     * Trié par date pour garder le fil de la conversation.
     */
    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :user1 AND m.receiverId = :user2) OR " +
            "(m.senderId = :user2 AND m.receiverId = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(
            @Param("user1") Long user1,
            @Param("user2") Long user2
    );
}