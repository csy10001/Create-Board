package com.example.board.model;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer post_id;

    private String title;
    private String content;
    private String authorEmail;

    // 기본 생성자
    public Post() {
    }

    // Getter 및 Setter
    public int getId() {
        return post_id;
    }

    public void setId(int id) {
        this.post_id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
