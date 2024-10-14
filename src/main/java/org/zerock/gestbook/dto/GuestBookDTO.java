package org.zerock.gestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder // Setter 대체용
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestBookDTO {
    // Entity 에 있는 정보를 객체화 시킨다.
    private Long gno;
    private String title, content, writer;
    private LocalDateTime regDate, modDate;
}
