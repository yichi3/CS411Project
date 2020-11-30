//package crawler;
//
//import entity.Game;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//
//public class TeamMatchCrawler {
//    private static final String fileDirectory = "DataSource/";
//    private static final int numberOfMatch = 114;
//    private static final String victory = "VICTORY";
//    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < numberOfMatch; i++) {
//            String filePath = fileDirectory + "match" + i + ".html";
//            File file = new File(filePath);
//            Document doc = Jsoup.parse(file, "UTF-8");
//            // first find the date and time for the match
//            String duration = doc.getElementById("binding-698").text();
//            String date = doc.getElementById("binding-699").text();
//            String gameConclusion = doc.getElementsByClass("game-conclusion").first().text();
//            boolean isBlueTeamWin = gameConclusion.equals(victory);
//            String redTeamName = "", blueTeamName = "";
//            String[] playerIds = new String[10];
//            String[] picks = new String[10];
//            int[] kills = new int[10];
//            int[] deaths = new int[10];
//            int[] assists = new int[10];
//            int[] goldEarned = new int[10];
//            int[] totalMinionsKilled = new int[10];
//
//            Elements players = doc.getElementsByClass("classic player");
//            int playerIndex = 0;
//            for (Element p : players) {
//                // every player id and champion pick
//                String[] teamPlayerId = p.select(".champion-nameplate-name > .binding > span")
//                                    .text()
//                                    .split(" ");
//                if (playerIndex == 0) {
//                    blueTeamName = teamPlayerId[0];
//                }
//                if (playerIndex == 5) {
//                    redTeamName = teamPlayerId[0];
//                }
//                playerIds[playerIndex] = teamPlayerId[1];
//                String pick = p.select("div[data-rg-id]").first().attr("data-rg-id");
//                picks[playerIndex] = pick;
//                // kda for every player
//                Element kda = p.select(".kda-kda").first();
//                int kill = Integer.parseInt(kda.select(".binding:eq(0)").first().text());
//                int death = Integer.parseInt(kda.select(".binding:eq(1)").first().text());
//                int assist = Integer.parseInt(kda.select(".binding:eq(2)").first().text());
//                kills[playerIndex] = kill;
//                deaths[playerIndex] = death;
//                assists[playerIndex] = assist;
//                // gold and cs for every player
//                Element minionKill = p.getElementsByClass("minions-col cs").first();
//                int cs = Integer.parseInt(minionKill.select(".binding").first().text());
//                totalMinionsKilled[playerIndex] = cs;
//                Element gold = p.getElementsByClass("gold-col gold").first();
//                int goldEarn = parseInt(gold.select(".binding").first().text());
//                goldEarned[playerIndex] = goldEarn;
//                playerIndex++;
//            }
//            String winningTeamName = isBlueTeamWin ? blueTeamName : redTeamName;
//            String losingTeamName = isBlueTeamWin ? redTeamName : blueTeamName;
//
//            // what is left here:
//            String[] redTeamBans = new String[5];
//            String[] blueTeamBans = new String[5];
//            Elements bans = doc.select(".bans-container");
//            int teamIndex = 0;
//            for (Element b : bans) {
//                String[] currentBans = teamIndex == 0 ? blueTeamBans : redTeamBans;
//                playerIndex = 0;
//                for (Element eachBan : b.getElementsByClass("champion-icon binding")) {
//                    currentBans[playerIndex] = eachBan.select("div[data-rg-id]").first().attr("data-rg-id");
//                    playerIndex++;
//                }
//                teamIndex++;
//            }
//            int redTeamTotalTowerKills = 0;
//            int redTeamTotalBaronKills = 0;
//            int redTeamTotalDragonKills = 0;
//            int redTeamTotalRiftHeraldKills = 0;
//            int blueTeamTotalTowerKills = 0;
//            int blueTeamTotalBaronKills = 0;
//            int blueTeamTotalDragonKills = 0;
//            int blueTeamTotalRiftHeraldKills = 0;
//            Elements towerDestroyed = doc.select(".tower-kills > span");
//            teamIndex = 0;
//            for (Element t : towerDestroyed) {
//                if (teamIndex == 0) {
//                    blueTeamTotalTowerKills = Integer.parseInt(t.text());
//                } else {
//                    redTeamTotalTowerKills = Integer.parseInt(t.text());
//                }
//                teamIndex++;
//            }
//            Elements baronDestroyed = doc.select(".baron-kills > span");
//            teamIndex = 0;
//            for (Element b : baronDestroyed) {
//                if (teamIndex == 0) {
//                    blueTeamTotalBaronKills = Integer.parseInt(b.text());
//                } else {
//                    redTeamTotalBaronKills = Integer.parseInt(b.text());
//                }
//                teamIndex++;
//            }
//            Elements dragonDestroyed = doc.select(".dragon-kills > span");
//            teamIndex = 0;
//            for (Element b : dragonDestroyed) {
//                if (teamIndex == 0) {
//                    blueTeamTotalDragonKills = Integer.parseInt(b.text());
//                } else {
//                    redTeamTotalDragonKills = Integer.parseInt(b.text());
//                }
//                teamIndex++;
//            }
//            Elements riftHeraldDestroyed = doc.select(".rift-herald-kills > span");
//            teamIndex = 0;
//            for (Element b : riftHeraldDestroyed) {
//                if (teamIndex == 0) {
//                    blueTeamTotalRiftHeraldKills = Integer.parseInt(b.text());
//                } else {
//                    redTeamTotalRiftHeraldKills = Integer.parseInt(b.text());
//                }
//                teamIndex++;
//            }
//            int[] totalDamageDealt = new int[10];
//            int[] totalDamageToChampion = new int[10];
//            int[] totalDamageTaken = new int[10];
//            Element totalDamage = doc.getElementById("grid-row-414");
//            Element totalDamageC = doc.getElementById("grid-row-362");
//            Element totalDamageT = doc.getElementById("grid-row-519");
//            for (int j = 1; j <= 10; j++) {
//                totalDamageDealt[j-1] = parseInt(totalDamage.select("td:eq("+j+")").first().text());
//                totalDamageToChampion[j-1] = parseInt(totalDamageC.select("td:eq("+j+")").first().text());
//                totalDamageTaken[j-1] = parseInt(totalDamageT.select("td:eq("+j+")").first().text());
//            }
//
//            // assemble everything here to form a match information
//            Game game = new Game.GameBuilder().setBlueTeamName(blueTeamName)
//                    .setBlueTeamPlayerIds(Arrays.copyOfRange(playerIds, 0, 5))
//                    .setBlueTeamPicks(Arrays.copyOfRange(picks, 0, 5))
//                    .setBlueTeamBans(redTeamBans)
//                    .setBlueTeamKills(Arrays.copyOfRange(kills, 0, 5))
//                    .setBlueTeamDeaths(Arrays.copyOfRange(deaths, 0, 5))
//                    .setBlueTeamAssists(Arrays.copyOfRange(assists, 0, 5))
//                    .setBlueTeamTotalDamageDealt(Arrays.copyOfRange(totalDamageDealt, 0, 5))
//                    .setBlueTeamTotalDamageToChampion(Arrays.copyOfRange(totalDamageToChampion, 0, 5))
//                    .setBlueTeamTotalDamageTaken(Arrays.copyOfRange(totalDamageTaken, 0, 5))
//                    .setBlueTeamGoldEarned(Arrays.copyOfRange(goldEarned, 0, 5))
//                    .setBlueTeamTotalMinionsKilled(Arrays.copyOfRange(totalMinionsKilled, 0, 5))
//                    .setBlueTeamTotalTowerKills(blueTeamTotalTowerKills)
//                    .setBlueTeamTotalBaronKills(blueTeamTotalBaronKills)
//                    .setBlueTeamTotalDragonKills(blueTeamTotalDragonKills)
//                    .setBlueTeamTotalRiftHeraldKills(blueTeamTotalRiftHeraldKills)
//                    .setRedTeamName(redTeamName)
//                    .setRedTeamPlayerIds(Arrays.copyOfRange(playerIds, 5, 10))
//                    .setRedTeamPicks(Arrays.copyOfRange(picks, 5, 10))
//                    .setRedTeamBans(redTeamBans)
//                    .setRedTeamKills(Arrays.copyOfRange(kills, 5, 10))
//                    .setRedTeamDeaths(Arrays.copyOfRange(deaths, 5, 10))
//                    .setRedTeamAssists(Arrays.copyOfRange(assists, 5, 10))
//                    .setRedTeamTotalDamageDealt(Arrays.copyOfRange(totalDamageDealt, 5, 10))
//                    .setRedTeamTotalDamageToChampion(Arrays.copyOfRange(totalDamageToChampion, 5, 10))
//                    .setRedTeamTotalDamageTaken(Arrays.copyOfRange(totalDamageTaken, 5, 10))
//                    .setRedTeamGoldEarned(Arrays.copyOfRange(goldEarned, 5, 10))
//                    .setRedTeamTotalMinionsKilled(Arrays.copyOfRange(totalMinionsKilled, 5, 10))
//                    .setRedTeamTotalTowerKills(redTeamTotalTowerKills)
//                    .setRedTeamTotalBaronKills(redTeamTotalBaronKills)
//                    .setRedTeamTotalDragonKills(redTeamTotalDragonKills)
//                    .setRedTeamTotalRiftHeraldKills(redTeamTotalRiftHeraldKills)
//                    .setDate(date)
//                    .setDuration(duration)
//                    .setWinningTeamName(winningTeamName)
//                    .setLosingTeamName(losingTeamName)
//                    .build();
//            System.out.println(game.toString());
//
//            // TODO: remove this break in the future
////            break;
//        }
//    }
//
//    private static int parseInt(String s) {
//        double number = Double.parseDouble(s.substring(0, s.length() - 1));
//        return (int)(number * 1000);
//    }
//}
//
//
package crawler;

import db.MySQLConnection;
import entity.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TeamMatchCrawler {
    private static final String fileDirectory = "DataSource/";
    private static final int numberOfMatch = 114;
    private static final String victory = "VICTORY";

    // TODO: May need improvement
    private static int matchID = 0;

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < numberOfMatch; i++) {
            String filePath = fileDirectory + "match" + i + ".html";
            File file = new File(filePath);
            Document doc = Jsoup.parse(file, "UTF-8");
            // first find the date and time for the match
            String duration = doc.getElementById("binding-698").text();
            String date = doc.getElementById("binding-699").text();
            String gameConclusion = doc.getElementsByClass("game-conclusion").first().text();
            boolean isBlueTeamWin = gameConclusion.equals(victory);
            String redTeamName = "", blueTeamName = "";
            String[] playerIds = new String[10];
            String[] picks = new String[10];
            int[] kills = new int[10];
            int[] deaths = new int[10];
            int[] assists = new int[10];
            int[] goldEarned = new int[10];
            int[] totalMinionsKilled = new int[10];

            Elements players = doc.getElementsByClass("classic player");
            int playerIndex = 0;
            for (Element p : players) {
                // every player id and champion pick
                String[] teamPlayerId = p.select(".champion-nameplate-name > .binding > span")
                        .text()
                        .split(" ");
                if (playerIndex == 0) {
                    blueTeamName = teamPlayerId[0];
                }
                if (playerIndex == 5) {
                    redTeamName = teamPlayerId[0];
                }
                playerIds[playerIndex] = teamPlayerId[1];
                String pick = p.select("div[data-rg-id]").first().attr("data-rg-id");
                picks[playerIndex] = pick;
                // kda for every player
                Element kda = p.select(".kda-kda").first();
                int kill = Integer.parseInt(kda.select(".binding:eq(0)").first().text());
                int death = Integer.parseInt(kda.select(".binding:eq(1)").first().text());
                int assist = Integer.parseInt(kda.select(".binding:eq(2)").first().text());
                kills[playerIndex] = kill;
                deaths[playerIndex] = death;
                assists[playerIndex] = assist;
                // gold and cs for every player
                Element minionKill = p.getElementsByClass("minions-col cs").first();
                int cs = Integer.parseInt(minionKill.select(".binding").first().text());
                totalMinionsKilled[playerIndex] = cs;
                Element gold = p.getElementsByClass("gold-col gold").first();
                int goldEarn = parseInt(gold.select(".binding").first().text());
                goldEarned[playerIndex] = goldEarn;
                playerIndex++;
            }
            String winningTeamName = isBlueTeamWin ? blueTeamName : redTeamName;
            String losingTeamName = isBlueTeamWin ? redTeamName : blueTeamName;

            // what is left here:
            String[] redTeamBans = new String[5];
            String[] blueTeamBans = new String[5];
            Elements bans = doc.select(".bans-container");
            int teamIndex = 0;
            for (Element b : bans) {
                String[] currentBans = teamIndex == 0 ? blueTeamBans : redTeamBans;
                playerIndex = 0;
                for (Element eachBan : b.getElementsByClass("champion-icon binding")) {
                    currentBans[playerIndex] = eachBan.select("div[data-rg-id]").first().attr("data-rg-id");
                    playerIndex++;
                }
                teamIndex++;
            }
            int redTeamTotalTowerKills = 0;
            int redTeamTotalBaronKills = 0;
            int redTeamTotalDragonKills = 0;
            int redTeamTotalRiftHeraldKills = 0;
            int blueTeamTotalTowerKills = 0;
            int blueTeamTotalBaronKills = 0;
            int blueTeamTotalDragonKills = 0;
            int blueTeamTotalRiftHeraldKills = 0;
            Elements towerDestroyed = doc.select(".tower-kills > span");
            teamIndex = 0;
            for (Element t : towerDestroyed) {
                if (teamIndex == 0) {
                    blueTeamTotalTowerKills = Integer.parseInt(t.text());
                } else {
                    redTeamTotalTowerKills = Integer.parseInt(t.text());
                }
                teamIndex++;
            }
            Elements baronDestroyed = doc.select(".baron-kills > span");
            teamIndex = 0;
            for (Element b : baronDestroyed) {
                if (teamIndex == 0) {
                    blueTeamTotalBaronKills = Integer.parseInt(b.text());
                } else {
                    redTeamTotalBaronKills = Integer.parseInt(b.text());
                }
                teamIndex++;
            }
            Elements dragonDestroyed = doc.select(".dragon-kills > span");
            teamIndex = 0;
            for (Element b : dragonDestroyed) {
                if (teamIndex == 0) {
                    blueTeamTotalDragonKills = Integer.parseInt(b.text());
                } else {
                    redTeamTotalDragonKills = Integer.parseInt(b.text());
                }
                teamIndex++;
            }
            Elements riftHeraldDestroyed = doc.select(".rift-herald-kills > span");
            teamIndex = 0;
            for (Element b : riftHeraldDestroyed) {
                if (teamIndex == 0) {
                    blueTeamTotalRiftHeraldKills = Integer.parseInt(b.text());
                } else {
                    redTeamTotalRiftHeraldKills = Integer.parseInt(b.text());
                }
                teamIndex++;
            }
            int[] totalDamageDealt = new int[10];
            int[] totalDamageToChampion = new int[10];
            int[] totalDamageTaken = new int[10];
            Element totalDamage = doc.getElementById("grid-row-414");
            Element totalDamageC = doc.getElementById("grid-row-362");
            Element totalDamageT = doc.getElementById("grid-row-519");
            for (int j = 1; j <= 10; j++) {
                totalDamageDealt[j-1] = parseInt(totalDamage.select("td:eq("+j+")").first().text());
                totalDamageToChampion[j-1] = parseInt(totalDamageC.select("td:eq("+j+")").first().text());
                totalDamageTaken[j-1] = parseInt(totalDamageT.select("td:eq("+j+")").first().text());
            }

            // assemble everything here to form a match information
            Game game = new Game.GameBuilder().setBlueTeamName(blueTeamName)
                    .setBlueTeamPlayerIds(Arrays.copyOfRange(playerIds, 0, 5))
                    .setBlueTeamPicks(Arrays.copyOfRange(picks, 0, 5))
                    .setBlueTeamBans(redTeamBans)
                    .setBlueTeamKills(Arrays.copyOfRange(kills, 0, 5))
                    .setBlueTeamDeaths(Arrays.copyOfRange(deaths, 0, 5))
                    .setBlueTeamAssists(Arrays.copyOfRange(assists, 0, 5))
                    .setBlueTeamTotalDamageDealt(Arrays.copyOfRange(totalDamageDealt, 0, 5))
                    .setBlueTeamTotalDamageToChampion(Arrays.copyOfRange(totalDamageToChampion, 0, 5))
                    .setBlueTeamTotalDamageTaken(Arrays.copyOfRange(totalDamageTaken, 0, 5))
                    .setBlueTeamGoldEarned(Arrays.copyOfRange(goldEarned, 0, 5))
                    .setBlueTeamTotalMinionsKilled(Arrays.copyOfRange(totalMinionsKilled, 0, 5))
                    .setBlueTeamTotalTowerKills(blueTeamTotalTowerKills)
                    .setBlueTeamTotalBaronKills(blueTeamTotalBaronKills)
                    .setBlueTeamTotalDragonKills(blueTeamTotalDragonKills)
                    .setBlueTeamTotalRiftHeraldKills(blueTeamTotalRiftHeraldKills)
                    .setRedTeamName(redTeamName)
                    .setRedTeamPlayerIds(Arrays.copyOfRange(playerIds, 5, 10))
                    .setRedTeamPicks(Arrays.copyOfRange(picks, 5, 10))
                    .setRedTeamBans(redTeamBans)
                    .setRedTeamKills(Arrays.copyOfRange(kills, 5, 10))
                    .setRedTeamDeaths(Arrays.copyOfRange(deaths, 5, 10))
                    .setRedTeamAssists(Arrays.copyOfRange(assists, 5, 10))
                    .setRedTeamTotalDamageDealt(Arrays.copyOfRange(totalDamageDealt, 5, 10))
                    .setRedTeamTotalDamageToChampion(Arrays.copyOfRange(totalDamageToChampion, 5, 10))
                    .setRedTeamTotalDamageTaken(Arrays.copyOfRange(totalDamageTaken, 5, 10))
                    .setRedTeamGoldEarned(Arrays.copyOfRange(goldEarned, 5, 10))
                    .setRedTeamTotalMinionsKilled(Arrays.copyOfRange(totalMinionsKilled, 5, 10))
                    .setRedTeamTotalTowerKills(redTeamTotalTowerKills)
                    .setRedTeamTotalBaronKills(redTeamTotalBaronKills)
                    .setRedTeamTotalDragonKills(redTeamTotalDragonKills)
                    .setRedTeamTotalRiftHeraldKills(redTeamTotalRiftHeraldKills)
                    .setDate(date)
                    .setDuration(duration)
                    .setWinningTeamName(winningTeamName)
                    .setLosingTeamName(losingTeamName)
                    .build();
            System.out.println(game.toString());


            // Lines added
            MySQLConnection con = new MySQLConnection();
            if (con.isGameExist(matchID)) {
                continue;
            }

            Team redTeam  = new Team(game.redTeamName);
            Team blueTeam = new Team(game.blueTeamName);
            con.setTeamInfo(redTeam);
            con.setTeamInfo(blueTeam);

            for (String player : game.redTeamPlayerIds) {
                Player.PlayerBuilder playerBuilder = new Player.PlayerBuilder();
                Player newPlayer = playerBuilder
                        .setCommonName(player)
                        .setTeamName(game.redTeamName)
                        .build();
                if (con.getPlayerInfo(player).isEmpty()) {
                    con.setPlayerInfo(newPlayer, game.redTeamName);
                } else {
                    con.setPlayerInfoHard(newPlayer, game.redTeamName);
                }
            }

            for (String player : game.blueTeamPlayerIds) {
                Player.PlayerBuilder playerBuilder = new Player.PlayerBuilder();
                Player newPlayer = playerBuilder
                        .setCommonName(player)
                        .setTeamName(game.blueTeamName)
                        .build();
                if (con.getPlayerInfo(player).isEmpty()) {
                    con.setPlayerInfo(newPlayer, game.blueTeamName);
                } else {
                    con.setPlayerInfoHard(newPlayer, game.blueTeamName);
                }
            }

            for (String champ : game.redTeamPicks) {
                Champion newChamp = new Champion(champ);
                con.setChampionInfo(newChamp);
            }
            for (String champ : game.redTeamBans) {
                Champion newChamp = new Champion(champ);
                con.setChampionInfo(newChamp);
            }
            for (String champ : game.blueTeamPicks) {
                Champion newChamp = new Champion(champ);
                con.setChampionInfo(newChamp);
            }
            for (String champ : game.blueTeamBans) {
                Champion newChamp = new Champion(champ);
                con.setChampionInfo(newChamp);
            }

            con.setGameInfo(game, matchID);
            matchID ++;
            con.close();

        }
    }

    private static int parseInt(String s) {
        double number = Double.parseDouble(s.substring(0, s.length() - 1));
        return (int)(number * 1000);
    }
}