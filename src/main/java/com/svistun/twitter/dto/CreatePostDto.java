package com.svistun.twitter.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreatePostDto {

    @Size(max = 50)
    private String title;
    private String destruction;
}
