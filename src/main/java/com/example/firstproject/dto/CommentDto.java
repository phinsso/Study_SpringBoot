package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;

    // @JsonProperty("article_id") // JSON 데이터의 key 이름과 저장하는 dto에 선언된 필드의 변수명이 다를 경우 어노테이션을 이용하여 키 이름을 작성하면 자동으로 매핑됨
    private Long articleId;

    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(), // 댓글 엔티티가 속한 부모 게시글의 id
                comment.getNickname(),
                comment.getBody()
        );
    }
}
