package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TeamMatchCrawler {
    private static final String url = "https://lol.gamepedia.com";
    public static void main(String[] args) throws IOException {
        String teamMatchUrl = url + "/Match_History_Index";
        Document doc = Jsoup.connect(teamMatchUrl).get();
        Elements matchups = doc.select("td:has([title=\"2020 Season World Championship/Main Event\"]) + td");
        for (Element matchup: matchups) {
//            System.out.println(matchup.html());
            String matchupUrl = matchup.select("a").attr("href");
            matchCrawler(matchupUrl);
        }
    }

    private static void matchCrawler(String matchupUrl) throws IOException {
        Document doc = Jsoup.connect(matchupUrl).get();
        Elements matchups = doc.select("td:has([title=\"2020 Season World Championship/Main Event\"]) + td");
        for (Element matchup: matchups) {
            System.out.println(matchup.html());
        }
    }
}
