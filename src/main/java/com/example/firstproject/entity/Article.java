package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity // 이 클래스가 엔티티임을 선언하는 어노테이션. 해당 클래스를 기반으로 db에 테이블이 생성됨
public class Article {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue // 자동 생성 기능 추가 (== auto_increment)
    private Long id;
    @Column // title 필드 선언 (db 테이블의 title 열과 연결됨)
    private String title;
    @Column // content 필드 선언 (db 테이블의 content 열과 연결됨)
    private String content;

    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
