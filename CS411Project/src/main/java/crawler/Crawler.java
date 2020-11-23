package crawler;

import entity.Player;
import entity.Player.PlayerBuilder;
import entity.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {
    private static final String url = "https://lol.gamepedia.com";
    public static void main(String[] args) throws IOException {
        String tournamentUrl = url + "/2020_Season_World_Championship";
        TotalCrawler(tournamentUrl);
//        TestMethod("https://lol.gamepedia.com/MagiFelix");
    }

    private static void TotalCrawler(String tournamentUrl) throws IOException {
        Document doc = Jsoup.connect(tournamentUrl).get();
        Elements teams = doc.select(".tournament-results-team > .team > .teamname");
        for (Element team : teams) {
            String teamName = team.text();
            Team newTeam = new Team(teamName);
            // now for each team, we need to find all the active players for the team
            // new url is given by the previous url append with the team url
            String teamUrl = team.select("a").attr("href");
//            if (teamUrl.equals("/Suning")) {
//            System.out.println(teamName);
            TeamCrawler(url + teamUrl, teamName);
//            System.out.println("-----------------------");
//            }
//            System.out.println(team.attr("href"));
//            System.out.println(newTeam.toString());
        }
    }

    private static void TeamCrawler(String teamUrl, String teamName) throws IOException{
        Document doc = Jsoup.connect(teamUrl).get();
        Elements players = doc.select(".team-members-current > tbody > tr > td > a");
        for (Element player :players) {
            String playerUrl = url + player.attr("href");
            PlayerCrawler(playerUrl, teamName);
        }
    }

    private static void PlayerCrawler(String playerUrl, String teamName) throws IOException {
        Document doc = Jsoup.connect(playerUrl).get();
        Element player = doc.select("#infoboxPlayer").first();
        // for each player, we need to get the following information
        // 1. commonID, 2. Name (first + last or just the full name?) 3. Country 4. Birthday
        // 5. position (role) 6. gender (Male in default) 7. teamName, could derived from previous part
        // 8. imageUrl
//        System.out.println(player.html());
        PlayerBuilder builder = new PlayerBuilder();
        String commonName = player.select("tr > .infobox-title").text();
        String imageUrl = player.select(".floatnone > a > img").attr("src");
        String name = player.select(".infobox-label + td").first().text();
        String nationality = player.select(".infobox-label + td > span.country-object").text();
        // for birthdate, need to check if the source has this field or not
        boolean hasBirthDate = player.select(".infobox-label").eq(2).text().equals("Birthday");
        String birthDate = hasBirthDate ? player.select(".infobox-label + td").eq(2).text() : "";
        String position = player.select("span.markup-object > .role-sprite").attr("title");
        String gender = "Male";
        Player newPlayer = builder.setTeamName(teamName)
                                    .setCommonName(commonName)
                                    .setImageUrl(imageUrl)
                                    .setFirstName(name)
                                    .setLastName("")
                                    .setNationality(nationality)
                                    .setBirthDate(birthDate)
                                    .setPosition(position)
                                    .setGender(gender)
                                    .build();
        System.out.println(newPlayer.toString());

    }

    private static void TestMethod(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element player = doc.select("#infoboxPlayer").first();
        System.out.println(player.html());
        String commonName = player.select("tr > .infobox-title").text();
        System.out.println(commonName);
    }
}
