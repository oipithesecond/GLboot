package com.oipithesecond.glboot.domain.entities;

import com.oipithesecond.glboot.domain.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "game_id", nullable = false)
    private Game game;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Column(nullable = false)
    private Duration SessionLength;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(game, session.game) && Objects.equals(startTime, session.startTime) && Objects.equals(endTime, session.endTime) && status == session.status && Objects.equals(SessionLength, session.SessionLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, game, startTime, endTime, status, SessionLength);
    }

    @PreUpdate
    protected void onUpdate() {
        this.endTime = LocalDateTime.now();
        this.SessionLength = Duration.between(startTime, endTime);
    }
}