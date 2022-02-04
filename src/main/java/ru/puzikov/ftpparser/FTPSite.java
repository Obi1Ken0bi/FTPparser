package ru.puzikov.ftpparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FTPSite {
    final
    GamesRepository gamesRepository;

    final GameServiceImpl gameService;
    final
    WriteRequester writeRequester;


    public FTPSite(GamesRepository gamesRepository, GameServiceImpl gameService, WriteRequester writeRequester) {

        this.gamesRepository = gamesRepository;
        this.gameService = gameService;
        this.writeRequester = writeRequester;
    }


    public boolean GetGames(String link1) throws IOException {
        try {


            Document doc = Jsoup.connect(link1).get();
            Elements games = doc.select(".base");
            Elements nameAndLink = games.select(".header-h1");
            Elements links = nameAndLink.select("a[href]");
            for (Element info1 : games) {
                //version
                Element info = info1.select("a[href]").first();
                Element versionEl = info1.select("span[style]").first();
                String version = "first";
                if (versionEl != null) {

                    Pattern vPattern = Pattern.compile("&gt;&gt;&gt;.*");
                    Matcher vMatcher = vPattern.matcher(versionEl.toString());
                    if (vMatcher.find()) {
                        version = vMatcher.group().replaceFirst("&gt;&gt;&gt;", "");
                        version = version.replaceFirst("</span>", "").trim();

                    }
                }
                System.out.println(version);

                //link
                String infoS = info.toString();
                Pattern linkPattern = Pattern.compile("\".*?\"");
                Matcher matcher = linkPattern.matcher(infoS);
                String link = "";
                if (matcher.find()) {
                    link = matcher.group().replaceAll("\"", "");
                    System.out.println(link);

                }
                //name
                Element name = info.select("h1").first();
                String clearName = name.text()
                        .trim()
                        .toLowerCase(Locale.ROOT)
                        .replaceFirst("((<h1>)|(<h1/>)|( играть по сети и интернету онлайн( / лан)?))", "")
                        .replaceAll("\\s[а-яА-я]+", "");
                System.out.println(clearName);
                if (!gamesRepository.existsByName(clearName)) {
                    gamesRepository.save(new Game(clearName, link, version));
                    System.out.println("added to DB!");
                    writeRequester.send("Новая игра на FreeTP: " + clearName + System.lineSeparator() + link);
                } else {
                    if (!gamesRepository.existsByNameAndVersion(clearName, version)) {
                        Game game = gamesRepository.findByName(clearName);
                        game.setVersion(version);
                        gameService.update(game, game.getId());
                        System.out.println("version updated");
                        writeRequester.send("Новая версия на FreeTP: " + clearName + " " + version + System.lineSeparator() + link);
                    } else System.out.println("not added");
                }

            }


            return false;
        }catch (UnknownHostException e){
            e.printStackTrace();
            return false;
        }
    }

    public void getAllGames() {
        try {
            String staticLink = "https://freetp.org/";
            GetGames(staticLink);
            for (int i = 2; i < 179; i++) {
                GetGames(staticLink + "/page/" + i);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
