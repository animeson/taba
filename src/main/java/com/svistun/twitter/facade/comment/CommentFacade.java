package com.svistun.twitter.facade.comment;

import com.svistun.twitter.dto.CommentDto;
import com.svistun.twitter.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentFacade {
    List<CommentDto> transactionFromEntityToDTO(List<Comment> comment);

}
