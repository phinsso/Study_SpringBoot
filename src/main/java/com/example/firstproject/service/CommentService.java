package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        /* // 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 2. 엔티티 -> dto 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i); // 조회한 댓글 엔티티 하나씩 가져오기
            CommentDto dto = CommentDto.createCommentDto(c); // 엔티티를 dto로 변환
            dtos.add(dto); // 변환한 dto를 dtos 리스트에 삽입
        } */

        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                // map(a -> b): 스트림의 각 요소(a)를 b를 수행한 결과로 매핑
                .map(comment -> CommentDto.createCommentDto(comment))// 엔티티를 dto로 매핑
                .collect(Collectors.toList()); // 스트림 데이터를 리스트 자료형으로 변환
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. db에서 부모 게시글을 조회해 가져오고 없을 경우 예외 발생시키기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시물이 없습니다."));
        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        // 3. 댓글 엔티티를 db에 저장
        Comment created = commentRepository.save(comment);
        // 4. dto로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 2. 댓글 수정
        target.patch(dto);
        // 3. db에 갱신
        Comment updated = commentRepository.save(target);
        // 4. 댓글 엔티티를 dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 2. 댓글 삭제
        commentRepository.delete(target);
        // 3. 삭제 댓글을 dto로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
