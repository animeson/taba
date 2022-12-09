package com.svistun.twitter.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String comment;
    private LocalDateTime dataTimeCreateComment;
    private PersonDto create;


}
