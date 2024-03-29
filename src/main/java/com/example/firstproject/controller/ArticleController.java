package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j // 로깅 기능을 위한 어노테이션
@Controller
public class ArticleController {
    // ArticleRepository 인터페이스의 구현 객체를 new 키워드로 만든 적이 없는데도 articleRepository 객체 사용이 가능함
    @Autowired // 스프링부트가 미리 생성해 놓은 리파지터리 객체 주입 (DI)
    private ArticleRepository articleRepository; // articleRepository 객체 선언
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 폼 데이터를 dto로 받기
        log.info(form.toString());
        // System.out.println(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        /* findById()는 특정 엔티티의 id값을 기준으로 데이터를 찾은 다음, Optional 타입으로 반환함
        Optional<Article> articleEntity = articleRepository.findById(id); */
        // orElse(null): id값으로 데이터를 찾을 때, 해당 id 값이 없으면 null을 반환함. (Optional 대신 사용)
        Article articleEntity = articleRepository.findById(id).orElse(null);

        List<CommentDto> commentsDtos = commentService.comments(id); // 조회한 댓글 목록을 변수에 저장

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        model.addAttribute("commentDtos", commentsDtos); // 댓글 목록 모델에 등록

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        // findAll()은 해당 리퍼지터리에 있는 모든 데이터를 가져옴
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 아이디 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { // 매개변수로 dto 받아오기
        log.info(form.toString());
        // 1. dto를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔티티를 db에 저장하기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null) { // 입력 대상의 존재 여부 검증 (디비에 기존 데이터가 있는지)
            articleRepository.save(articleEntity); // 엔티티를 db에 저장(갱신)
        }

        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상 엔티티 삭제하기
        if(target != null) { // 삭제할 대상이 있는지 확인
            articleRepository.delete(target); // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
