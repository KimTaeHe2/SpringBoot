package org.zerock.gestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.gestbook.dto.GuestBookDTO;
import org.zerock.gestbook.dto.PageRequestDTO;
import org.zerock.gestbook.dto.PageResultDTO;
import org.zerock.gestbook.entity.GuestBook;
import org.zerock.gestbook.repository.GuestBookRepository;

import java.util.function.Function;

@Service // 서비스 계층임을 명시
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestBookServiceImpl implements GuestBookService{

    private final GuestBookRepository repository;

    @Override
    public Long register(GuestBookDTO dto) { // 리턴은 gno 입력은 DTO
        log.info("GuestBookServiceImpl.register 메서드 실행...");
        log.info("DTO : "+dto);

        GuestBook entity = dtoTOEntity(dto); // 화면에서 받은 객체를 db로 전달용
        log.info("Entity : "+entity);
        repository.save(entity); // jpa 로 insert 처리

        return entity.getGno(); // insert 방명록 번호가 리턴
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getpage(Sort.by("gno").descending());
        //public Pageable getpage(Sort sort) {
        //        return PageRequest.of(page - 1, size, sort);
        //    }

        Page<GuestBook> result = repository.findAll(pageable);

        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));
        // 함수 생성<Entity, dto> fn 이름으로 결과가 들어감.
        return new PageResultDTO<>(result, fn);
        // public PageResultDTO(Page<EN> result, Function<EN,DTO> fn)
    }
}
