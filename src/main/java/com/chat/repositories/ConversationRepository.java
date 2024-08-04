package com.chat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.entities.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c JOIN c.messages m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR (m.sender.id = :receiverId AND m.receiver.id = :senderId)")
    Optional<Conversation> findConversationBySenderAndReceiver(@Param("senderId") Long senderId,
            @Param("receiverId") Long receiverId);

    @Query("SELECT c FROM Conversation c JOIN c.messages m WHERE m.sender.id = :id OR m.receiver.id = :id")
    List<Conversation> findConversationsByUserId(@Param("id") Long id);
}
