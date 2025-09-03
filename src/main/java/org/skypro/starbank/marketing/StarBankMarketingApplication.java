package org.skypro.starbank.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StarBankMarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarBankMarketingApplication.class, args);
    }

}
