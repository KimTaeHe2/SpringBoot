package org.zerock.gestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
// @NoArgsConstructor 현재 기본생성자를 수동으로 만들었음.
@Data
public class PageRequestDTO {
    // 리스트 요청시 페이징 처리 사용하는 데이터를 재사용하기 위한
    // 페이지 번호, 목록의 갯수, 검색 조건등

    private int page;
    private int size;

    public PageRequestDTO() { // 기본생성자
        this.page = 1;  // 기본 페이지 번호
        this.size = 10; // 출력 게시물 수
    }

    public Pageable getpage(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
        // Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());
    }
}
