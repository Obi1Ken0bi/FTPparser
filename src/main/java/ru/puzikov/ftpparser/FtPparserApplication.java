package ru.puzikov.ftpparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class FtPparserApplication {
    private static final Logger log = LoggerFactory.getLogger(FtPparserApplication.class);
    final
    FTPSite ftpSite;
    boolean isFirst = true;


    public FtPparserApplication(FTPSite ftpSite) {
        this.ftpSite = ftpSite;

    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(FtPparserApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

    }

    @Bean
    CommandLineRunner parser(GamesRepository gamesRepository) {
        return (args) -> {
            while (true) {
                if (isFirst) {
                    ftpSite.getAllGames();
                    isFirst = false;
                }
                ftpSite.getGames("https://freetp.org/");
                TimeUnit.SECONDS.sleep(60);
                log.info("Parse completed");

            }

        };
    }

}
