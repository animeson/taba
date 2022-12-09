package com.svistun.twitter.facade.comment;

import com.svistun.twitter.dto.CommentDto;
import com.svistun.twitter.entity.Comment;
import com.svistun.twitter.facade.person.PersonFacadeImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CommentFacadeImpl implements CommentFacade {
    private final PersonFacadeImpl personFacade;

    @Override
    public List<CommentDto> transactionFromEntityToDTO(List<Comment> comments) {
        List<CommentDto> commentsDto = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setCreate(personFacade.transactionFromEntityToDTO(comment.getPerson()));
            commentsDto.add(commentDto);
        }
        return commentsDto;
    }
}
