package com.example.board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Comment {

    @Id
    private Integer comment_no;

    private Integer post_id;
    private String member_id;
    private String comment_content;
    private Date comment_date;
}
