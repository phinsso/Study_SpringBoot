package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // GET
    // 전체 조회
    @GetMapping("api/coffees")
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    // 단일 조회
    @GetMapping("api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto) {
        // dto -> entity
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    // PATCH
    @PatchMapping("api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto) {
        // dto -> entity
        Coffee coffee = dto.toEntity();
        //타깃 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null || id != coffee.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 업데이트 및 정상 응답
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        // 대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 대상 삭제
        coffeeRepository.delete(target);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
