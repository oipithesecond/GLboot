package com.oipithesecond.glboot.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="gameList")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private GameStatus status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private Session gameList;

    public Game() {
    }

    public Game(UUID id, String title, String description, GameStatus status, Session gameList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.gameList = gameList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Session getGameList() {
        return gameList;
    }

    public void setGameList(Session gameList) {
        this.gameList = gameList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(title, game.title) && Objects.equals(description, game.description) && status == game.status && Objects.equals(gameList, game.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, gameList);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", gameList=" + gameList +
                '}';
    }
}
