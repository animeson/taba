package com.svistun.twitter.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String title;

    @Size(max = 255)
    private String destruction;

    private LocalDateTime creationTime;
    private PersonDto creator;

    private List<CommentDto> comments;

}
