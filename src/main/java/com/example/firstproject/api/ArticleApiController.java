package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService; // 서비스 객체 주입

    // GET (조회)
    // 모든 게시글 조회
    @GetMapping("api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    // 단일 게시글 조회
    @GetMapping("api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST (생성)
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // 반환형 수정
        Article created = articleService.create(dto);
        return (created != null) ? // 생성이 되면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH (수정)
    @PatchMapping("api/articles/{id}")
    // 매개변수로 요청 url의 id 와 요청 메세지의 본문 데이터 받아오기
    // 반환하는 데이터에 상태 코드도 실어 보내기 위해 반환형을 ResponseEntity로 함.
    // ResponseEntity: REST API의 응답을 위해 사용하는 클래스. HTTP 상태 코드, 헤더, 본문을 실어 보낼 수 있음.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto); // 서비스를 통해 게시글 수정
        return (updated != null) ? // 수정되면 정상, 안 되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE (삭제)
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ? // deleted에 내용이 있다면 삭제되었다는 뜻
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : // 삭제되어서 내용이 없음
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 삭제하지 못함
    }

}
