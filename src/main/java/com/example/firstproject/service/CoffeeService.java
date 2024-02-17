package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        if(coffee.getId() != null) {
            return null;
        }

        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto dto) {
        // dto -> entity
        Coffee coffee = dto.toEntity();
        //타깃 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null || id != coffee.getId()) {
            return null;
        }
        // 업데이트 및 정상 응답
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return updated;
    }

    public Coffee delete(Long id) {
        // 대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null) {
            return null;
        }
        // 대상 삭제
        coffeeRepository.delete(target);

        return target;
    }
}
