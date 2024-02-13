package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

// dto: 컨트롤러에서 폼 데이터를 받을 때 사용하는 객체
@AllArgsConstructor // 클래스 안쪽의 모든 필드를 매개변수로 하는 생성자 자동 생성
@ToString // toString() 메서드를 사용하는 것과 같은 효과
public class ArticleForm {
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    public Article toEntity() {
        return new Article(null, title, content);
    }
}
