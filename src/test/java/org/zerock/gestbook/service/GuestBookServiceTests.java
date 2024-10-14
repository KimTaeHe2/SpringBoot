package org.zerock.gestbook.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.gestbook.dto.GuestBookDTO;
import org.zerock.gestbook.dto.PageRequestDTO;
import org.zerock.gestbook.dto.PageResultDTO;
import org.zerock.gestbook.entity.GuestBook;

@SpringBootTest
@Log4j2
public class GuestBookServiceTests {
    @Autowired // 생성자 자동 주입
    private GuestBookService service;

    @Test
    public void testRegister(){
        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
                .title("서비스 테스트용 제목")
                .content("서비스 테스트용 내용")
                .writer("서비스 사용자")
                .build();
        log.info("서비스 테스트중 : Entity 출력 -> " + service.register(guestBookDTO));
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(60)
                .size(10)
                .build();

        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = service.getList(pageRequestDTO);

        for(GuestBookDTO guestBookDTO : resultDTO.getDtoList()){
            System.out.println("방명록 리스트 : "+guestBookDTO);
        }

        System.out.println("페이징처리 : "+pageRequestDTO);
        System.out.println("이전 : " + resultDTO.isPrev());
        System.out.println("다음 : " + resultDTO.isNext());
        System.out.println("총 페이지 : " + resultDTO.getTotalPage());
        System.out.println("------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
