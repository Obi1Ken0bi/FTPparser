package ru.puzikov.ftpparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class FtPparserApplication {
    private static final Logger log = LoggerFactory.getLogger(FtPparserApplication.class);
    final
    FTPSite ftpSite;

    @Value("${parser.first}")
    private boolean isFirst;


    public FtPparserApplication(FTPSite ftpSite) {
        this.ftpSite = ftpSite;

    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(FtPparserApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

    }

    @Scheduled(fixedDelay = 10000)
    void parser() {


        if (isFirst) {
            ftpSite.getAllGames();
            isFirst = false;
        }
        try {
            ftpSite.getGames("https://freetp.org/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Parse completed");


    }

}
