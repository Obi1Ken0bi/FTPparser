package ru.puzikov.ftpparser;

import javax.persistence.*;

@Entity(name = "game")
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "version")
    private String version;

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public Game(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Game(String name, String link, String version) {
        this.name = name;
        this.link = link;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
