package simsimbooks.couponserver.config;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestConfiguration
public class TestDatabaseConfig {
    @Container
    private static final MySQLContainer<?> mysqlContainer;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0.41")
                .withDatabaseName("test_DB")
                .withUsername("test")
                .withPassword("test");
        mysqlContainer.start();

        // A) 기본 설정 + rewriteBatchedStatements 옵션
        // 배치(insert) 시 성능 최적화를 위해 JDBC 드라이버 옵션 추가
        String originalJdbcUrl = mysqlContainer.getJdbcUrl() + "?rewriteBatchedStatements=true";

        //Java 프로그래밍에서 JVM 전체에 적용되는 시스템 프로퍼티를 런타임에 설정하는 메서드
        System.setProperty("spring.datasource.url", originalJdbcUrl);
        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());
    }

    @Bean
    public MySQLContainer<?> mySQLContainer() {
        return mysqlContainer;
    }

    @PreDestroy
    public void stop() {
        if (mysqlContainer != null && mysqlContainer.isRunning()) {
            mysqlContainer.stop();
        }
    }
}
