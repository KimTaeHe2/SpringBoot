package org.zerock.gestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.gestbook.entity.GuestBook;
import org.zerock.gestbook.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestBookTests {
    @Autowired
    private GuestBookRepository test;

    @Test
    public void insertDummies(){
        // 300개 입력 테스트
        IntStream.range(1,300).forEach(i->{
            GuestBook guestBook = GuestBook.builder()
                    .title("테스트 제목" + i)
                    .content("테스트 내용" + i)
                    .writer("test user" + (i%10)) // user0....user1.....
                    .build();
            System.out.println(test.save(guestBook));
            // 리포지토리에 jpa 내장된 메서드인 save 로 insert 처리함
        });
    }

    @Test
    public void updateTest(){
        Optional<GuestBook> result = test.findById(200L);
        // .findbyId(200L) = select * from guestbook where gno = 200

        if(result.isPresent()){ // Optional 값이 null 이 아니면
            GuestBook guestBook = result.get(); // 검색한 객체를 가져와서 guestbook에 넣음
            guestBook.changeTitle("수정된 제목...");
            guestBook.changeContent("수정된 내용...");
            test.save(guestBook);
            //Hibernate:
            //    update
            //        guest_book
            //    set
            //        content=?,
            //        moddate=?,
            //        title=?,
            //        writer=?
            //    where
            //        gno=?
        }
    }

    @Test
    public void testQuery1(){
        // 쿼리 dsl 을 이용해서 단일 검색용 -> 0페이지, 10개, 정렬(gno.내림차순), 제목이 1이 들어있는 조건
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; // 쿼리 dsl 객체 생성

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // where 조건이 있냐 없냐

        BooleanExpression expression = qGuestBook.title.contains(keyword); // 제목에 1을 넣음

        builder.and(expression);

        Page<GuestBook> result = test.findAll(builder, pageable); // 찾은 값을 적용
        //builder -> where, pageble -> 페이징+정렬

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        }); // 결과 콘솔 출력
        //GuestBook(gno=590, title=title...291, content=content...291, writer=writer...1)
        //GuestBook(gno=580, title=title...281, content=content...281, writer=writer...1)
        //GuestBook(gno=570, title=title...271, content=content...271, writer=writer...1)
        //GuestBook(gno=560, title=title...261, content=content...261, writer=writer...1)
        //GuestBook(gno=550, title=title...251, content=content...251, writer=writer...1)
        //GuestBook(gno=540, title=title...241, content=content...241, writer=writer...1)
        //GuestBook(gno=530, title=title...231, content=content...231, writer=writer...1)
        //GuestBook(gno=520, title=title...221, content=content...221, writer=writer...1)
        //GuestBook(gno=518, title=title...219, content=content...219, writer=writer...9)
        //GuestBook(gno=517, title=title...218, content=content...218, writer=writer...8)
    }

    @Test
    public void testQueryMulti(){
        // 제목과 내용을 where 문으로 다중검색

        Pageable pageable = PageRequest.of(0,7,Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "2";

        BooleanBuilder builder = new BooleanBuilder(); // 조건이 있냐 없냐 where 생성용

        BooleanExpression exTitle = qGuestBook.title.contains(keyword); // 조건1 (제목검색)
        BooleanExpression exContent = qGuestBook.content.contains(keyword); // 조건 2 (내용검색)

        // 조건들을 합체
        BooleanExpression exAll = exTitle.or(exContent); // where title or content

        builder.and(exAll); // 조건문 합체

        builder.and(qGuestBook.gno.gt(0L)); // pk의 인덱스를 활용해서 빠른 추출 가능

        Page<GuestBook> result = test.findAll(builder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
        //GuestBook(gno=598, title=title...299, content=content...299, writer=writer...9)
        //GuestBook(gno=597, title=title...298, content=content...298, writer=writer...8)
        //GuestBook(gno=596, title=title...297, content=content...297, writer=writer...7)
        //GuestBook(gno=595, title=title...296, content=content...296, writer=writer...6)
        //GuestBook(gno=594, title=title...295, content=content...295, writer=writer...5)
        //GuestBook(gno=593, title=title...294, content=content...294, writer=writer...4)
        //GuestBook(gno=592, title=title...293, content=content...293, writer=writer...3)
    }
}
