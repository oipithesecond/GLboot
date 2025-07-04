package com.oipithesecond.glboot.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="sessions")
public class Session {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id", updatable=false, nullable=false)
    private UUID id;
    @ManyToOne(mappedBy="gameList", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Game> games;

    @Column(name="start_time", nullable=false)
    private LocalDateTime startTime;
    @Column(name="end_time" , nullable=false)
    private LocalDateTime endTime;

    public Session() {
    }

    public Session(UUID id, List<Game> games, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.games = games;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(games, session.games) && Objects.equals(startTime, session.startTime) && Objects.equals(endTime, session.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, games, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", games=" + games +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
