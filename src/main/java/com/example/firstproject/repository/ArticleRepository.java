package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

// CrudRepository<T, ID>: JPA에서 제공하는 리파지터리 인터페이스 (crud 작업이 가능)
// 제네릭 요소 T: 관리 대상 엔티티의 클래스 타입을 작성
// 제네릭 요소 ID: 관리 대상 엔티티의 대푯값(id값) 타입을 작성
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
