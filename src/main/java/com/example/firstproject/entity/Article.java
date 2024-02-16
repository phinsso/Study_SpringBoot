package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // 이 클래스가 엔티티임을 선언하는 어노테이션. 해당 클래스를 기반으로 db에 테이블이 생성됨
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가 어노테이션
@ToString
@Getter // 게터 메서드 추가 어노테이션
public class Article {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 기능 추가 (== auto_increment), DB가 자동으로 id 생성
    private Long id;
    @Column // title 필드 선언 (db 테이블의 title 열과 연결됨)
    private String title;
    @Column // content 필드 선언 (db 테이블의 content 열과 연결됨)
    private String content;

    // 일부 데이터 수정 시, 기존 값이 날아가는 현상 방지를 위한 메서드
    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}
