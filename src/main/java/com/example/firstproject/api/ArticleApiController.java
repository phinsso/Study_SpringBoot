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

    /*
    // POST (생성)
    @PostMapping("api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH (수정)
    @PatchMapping("api/articles/{id}")
    // 매개변수로 요청 url의 id 와 요청 메세지의 본문 데이터 받아오기
    // 반환하는 데이터에 상태 코드도 실어 보내기 위해 반환형을 ResponseEntity로 함.
    // ResponseEntity: REST API의 응답을 위해 사용하는 클래스. HTTP 상태 코드, 헤더, 본문을 실어 보낼 수 있음.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        // 1. dto -> entity 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리
        if(target == null || id != article.getId()) { // 대상 엔티티가 없거나, 수정 요청 id와 본문 id가 다를 경우
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            // HttpStatus: HTTP 상태 코드를 관리하는 클래스
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 반환할 데이터가 없으므로 null
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // 수정 내용 db에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated); // 본문에는 반환할 데이터를 싣는다
    }

    // DELETE
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 3. 대상 삭제
        articleRepository.delete(target);
        // build(): body가 없는 ResponseEntity 객체 생성 (== body(null))
        return ResponseEntity.status(HttpStatus.OK).build();
    }
     */

}
