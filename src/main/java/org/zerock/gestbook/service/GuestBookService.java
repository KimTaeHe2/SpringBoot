package org.zerock.gestbook.service;


import org.zerock.gestbook.dto.GuestBookDTO;
import org.zerock.gestbook.dto.PageRequestDTO;
import org.zerock.gestbook.dto.PageResultDTO;
import org.zerock.gestbook.entity.GuestBook;

public interface GuestBookService {
    // C R U D 추상메서드 생성

    // 등록
    Long register(GuestBookDTO dto); // DTO 를 받아 처리함.

    PageResultDTO<GuestBookDTO, GuestBook>getList(PageRequestDTO requestDTO);
    // PageRequestDTO 요청을 받아서 PageResultDTO 결과를 출력
    // dto 를 Entity 로 변환, Entity 를 DTO 로 변환하는 코드를 추가
    // public PageResultDTO(Page<EN> result, Function<EN,DTO> fn)
    default GuestBook dtoTOEntity(GuestBookDTO dto){// default = 추상메서드로 처리 안함
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestBookDTO entityToDto(GuestBook entity){
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate()) // db에 있는 날짜 정보를 가져와야 함.
                .modDate(entity.getModDate()) // db에 있는 날짜 정보를 가져와야 함.
                .build();
        return dto;
    }
}
