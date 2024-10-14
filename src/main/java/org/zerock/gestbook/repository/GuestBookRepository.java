package org.zerock.gestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.gestbook.entity.GuestBook;

public interface GuestBookRepository
        extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
    // QuerydslPredicateExecutor<GuestBook> = Q 도메인을 활용하여 동적 쿼리 처리용
}
