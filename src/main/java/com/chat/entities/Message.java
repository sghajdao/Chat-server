package com.chat.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", allocationSize = 1)
    private Long id;

    private String type;

    @Column(length = 1024)
    private String content;
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonBackReference("sender-messages")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonBackReference("receiver-messages")
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference("conversation-messages")
    private Conversation conversation;
}
