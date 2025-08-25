package org.skypro.starbank.marketing.configuration.recommendation;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class RecommendationDataSourceConfiguration {

    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}") String recommendationsUrl) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver"); // Убедитесь, что у вас есть зависимость H2 в pom.xml
        dataSource.setReadOnly(true); // Если вы не планируете изменять данные, это правильно
        return dataSource;
    }

    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(@Qualifier("recommendationsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100));
        return cacheManager;
    }
}
