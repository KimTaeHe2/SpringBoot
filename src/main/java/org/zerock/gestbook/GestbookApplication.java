package org.zerock.gestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 감시체제용 코드 추가 필수(Base Entity 용)
public class GestbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestbookApplication.class, args);
    }

}
