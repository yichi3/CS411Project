//package crawler;
//
//import db.MySQLConnection;
//import entity.Player;
//import entity.Player.PlayerBuilder;
//import entity.Team;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class Crawler {
//    private static final String url = "https://lol.gamepedia.com";
//    public static void main(String[] args) throws IOException {
//        String tournamentUrl = url + "/2020_Season_World_Championship";
//        TeamPlayerCrawler(tournamentUrl);
//    }
//
//    private static void TeamPlayerCrawler(String tournamentUrl) throws IOException {
//        MySQLConnection con = new MySQLConnection();
//        int playerCount = con.getPlayerCount();
//        int teamCount = con.getTeamCount();
//        con.close();
//        Player.setPlayerIDCounter(playerCount);
//        Team.setTeamIDCounter(teamCount);
////        LoadTeam("Invictus Gaming", "/Invictus_Gaming");
//        Document doc = Jsoup.connect(tournamentUrl).get();
//        Elements teams = doc.select(".tournament-results-team > .team > .teamname");
//        for (Element team : teams) {
//            String teamName = team.text();
//            Team newTeam = new Team(teamName);
//            con = new MySQLConnection();
//            con.setTeamInfo(newTeam);
//            con.close();
//            System.out.println("Current team is: " + newTeam.getName());
//            // now for each team, we need to find all the active players for the team
//            // new url is given by the previous url append with the team url
//            String teamUrl = team.select("a").attr("href");
//            TeamCrawler(url + teamUrl, newTeam);
//        }
//    }
//
//    private static void TeamCrawler(String teamUrl, Team team) throws IOException{
//        Document doc = Jsoup.connect(teamUrl).get();
//        Elements players = doc.select(".team-members-current > tbody > tr > td > a");
//        for (Element player :players) {
//            String playerUrl = url + player.attr("href");
//            PlayerCrawler(playerUrl, team);
//        }
//    }
//
//    private static void PlayerCrawler(String playerUrl, Team team) throws IOException {
//        Document doc = Jsoup.connect(playerUrl).get();
//        Element player = doc.select("#infoboxPlayer").first();
//        // for each player, we need to get the following information
//        // 1. commonID, 2. Name (first + last or just the full name?) 3. Country 4. Birthday
//        // 5. position (role) 6. gender (Male in default) 7. teamName, could derived from previous part
//        // 8. imageUrl
////        System.out.println(player.html());
//        PlayerBuilder builder = new PlayerBuilder();
//        String commonName = player.select("tr > .infobox-title").text();
//        String imageUrl = player.select(".floatnone > a > img").attr("src");
//        String fullName = player.select(".infobox-label + td").first().text();
//        String nationality = player.select(".infobox-label + td > span.country-object").text();
//        // for birthdate, need to check if the source has this field or not
//        boolean hasBirthDate = player.select(".infobox-label").eq(2).text().equals("Birthday");
//        String birthDate = hasBirthDate ? player.select(".infobox-label + td").eq(2).text() : "";
//        String position = player.select("span.markup-object > .role-sprite").attr("title");
//        Player newPlayer = builder.setTeamName(team.getName())
//                                    .setCommonName(commonName)
//                                    .setImageUrl(imageUrl)
//                                    .setFullName(fullName)
//                                    .setNationality(nationality)
//                                    .setBirthDate(birthDate)
//                                    .setPosition(position)
//                                    .build();
////        System.out.println(newPlayer.toString());
//        MySQLConnection con = new MySQLConnection();
//        con.setPlayerInfo(newPlayer, team.getTeamID());
//        con.close();
//        System.out.println(newPlayer.getCommonName());
//    }
//
//    private static void LoadTeam(String teamName, String teamUrl) throws IOException{
//        Team newTeam = new Team(teamName);
//        MySQLConnection con = new MySQLConnection();
//        con.setTeamInfo(newTeam);
//        con.close();
//        TeamCrawler(url + teamUrl, newTeam);
//    }
//}
package crawler;

import db.MySQLConnection;
import entity.Player;
import entity.Player.PlayerBuilder;
import entity.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Crawler {
    private static final String url = "https://lol.gamepedia.com";
    private static final HashMap<String, String> teamNameMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        teamNameMap.put("DAMWON","DWG");
        teamNameMap.put("Suning","SN");
        teamNameMap.put("G2 Esports","G2");
        teamNameMap.put("Top Esports","TES");
        teamNameMap.put("DRX","DRX");
        teamNameMap.put("JD Gaming","JDG");
        teamNameMap.put("Fnatic","FNC");
        teamNameMap.put("Gen.G","GEN");
        teamNameMap.put("Team Liquid","TL");
        teamNameMap.put("PSG Talon","PSG");
        teamNameMap.put("LGD Gaming","LGD");
        teamNameMap.put("FlyQuest","FLY");
        teamNameMap.put("Machi Esports","MCX");
        teamNameMap.put("Rogue","RGE");
        teamNameMap.put("TSM","TSM");
        teamNameMap.put("Unicorns Of Love","UOL");
        teamNameMap.put("Legacy Esports","LGC");
        teamNameMap.put("SuperMassive","SUP");
        teamNameMap.put("Rainbow7","R7");
        teamNameMap.put("MAD Lions","MAD");
        teamNameMap.put("V3 Esports","V3");
        teamNameMap.put("INTZ","ITZ");
        String tournamentUrl = url + "/2020_Season_World_Championship";
        TeamPlayerCrawler(tournamentUrl);
    }

    private static void TeamPlayerCrawler(String tournamentUrl) throws IOException {
//        MySQLConnection con = new MySQLConnection();
//        int playerCount = con.getPlayerCount();
//        int teamCount = con.getTeamCount();
//        con.close();
//        Player.setPlayerIDCounter(playerCount);
//        Team.setTeamIDCounter(teamCount);
//        LoadTeam("Invictus Gaming", "/Invictus_Gaming");
        Document doc = Jsoup.connect(tournamentUrl).get();
        Elements teams = doc.select(".tournament-results-team > .team > .teamname");
        for (Element team : teams) {
            String teamName = team.text();
            Team newTeam = new Team(teamNameMap.get(teamName));
            MySQLConnection con = new MySQLConnection();
            con.setTeamInfo(newTeam);
            con.close();
            System.out.println("Current team is: " + newTeam.getTeamName());
            // now for each team, we need to find all the active players for the team
            // new url is given by the previous url append with the team url
            String teamUrl = team.select("a").attr("href");
            TeamCrawler(url + teamUrl, newTeam);
        }
    }

    private static void TeamCrawler(String teamUrl, Team team) throws IOException{
        Document doc = Jsoup.connect(teamUrl).get();
        Elements players = doc.select(".team-members-current > tbody > tr > td > a");
        for (Element player :players) {
            String playerUrl = url + player.attr("href");
            PlayerCrawler(playerUrl, team);
        }
    }

    private static void PlayerCrawler(String playerUrl, Team team) throws IOException {
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
        String fullName = player.select(".infobox-label + td").first().text();
        String nationality = player.select(".infobox-label + td > span.country-object").text();
        // for birthdate, need to check if the source has this field or not
        boolean hasBirthDate = player.select(".infobox-label").eq(2).text().equals("Birthday");
        String birthDate = hasBirthDate ? player.select(".infobox-label + td").eq(2).text() : "";
        String position = player.select("span.markup-object > .role-sprite").attr("title");
        Player newPlayer = builder.setTeamName(team.getTeamName())
                .setCommonName(commonName)
                .setImageUrl(imageUrl)
                .setFullName(fullName)
                .setNationality(nationality)
                .setBirthDate(birthDate)
                .setPosition(position)
                .build();
//        System.out.println(newPlayer.toString());
        MySQLConnection con = new MySQLConnection();
        con.setPlayerInfo(newPlayer, team.getTeamName());
        con.close();
        System.out.println(newPlayer.getCommonName());
    }

    private static void LoadTeam(String teamName, String teamUrl) throws IOException{
        Team newTeam = new Team(teamName);
        MySQLConnection con = new MySQLConnection();
        con.setTeamInfo(newTeam);
        con.close();
        TeamCrawler(url + teamUrl, newTeam);
    }
}