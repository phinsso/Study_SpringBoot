package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 스프링부트에 서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; // 게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) { // 이미 존재하는 id값의 데이터를 post 했을 때 기존 데이터가 수정되는 현상을 방지하기 위함
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. dto -> entity 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리
        if(target == null || id != article.getId()) { // 대상 엔티티가 없거나, 수정 요청 id와 본문 id가 다를 경우
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            // HttpStatus: HTTP 상태 코드를 관리하는 클래스
            return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // 수정 내용 db에 최종 저장
        return updated; // 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
    }

    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리
        if(target == null) {
            return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        // 3. 대상 삭제
        articleRepository.delete(target);
        return target; // 삭제한 대상을 보내줘야 하므로 target 반환
    }

    @Transactional // 트랜잭션을 선언할 경우, 코드 내부에서 실행에 실패하면 롤백(변경된 데이터를 모두 이전 값으로 되돌림)이 된다
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = dtos.stream() // dtos를 스트림화
                .map(dto  -> dto.toEntity()) // map()으로 dto가 하나하나 올 때마다 toEntity를 수행해 매핑
                .collect(Collectors.toList()); // 매핑한 것을 리스트로 묶음

        // 2. 엔티티 묶음을 db에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L) // id가 -1인 데이터 찾기
                // orElseThrow(): 값이 존재하면 그 값을 반환, 아니면 전달값으로 보낸 예외 발생
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!")); // 찾는 데이터가 없으면 예외 발생

        // 4. 결과값 반환하기
        return articleList;
    }
}