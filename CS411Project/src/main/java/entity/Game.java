package entity;

import org.json.JSONObject;

import java.util.Arrays;

public class Game {
    public final String redTeamName;
    public final String[] redTeamPlayerIds;
    public final String[] redTeamPicks;
    public final String[] redTeamBans;
    public final int[] redTeamKills;
    public final int[] redTeamDeaths;
    public final int[] redTeamAssists;
    public final int[] redTeamTotalDamageDealt;
    public final int[] redTeamTotalDamageToChampion;
    public final int[] redTeamTotalDamageTaken;
    public final int[] redTeamGoldEarned;
    public final int[] redTeamTotalMinionsKilled;
    public final int redTeamTotalTowerKills;
    public final int redTeamTotalBaronKills;
    public final int redTeamTotalDragonKills;
    public final int redTeamTotalRiftHeraldKills;

    public final String blueTeamName;
    public final String[] blueTeamPlayerIds;
    public final String[] blueTeamPicks;
    public final String[] blueTeamBans;
    public final int[] blueTeamKills;
    public final int[] blueTeamDeaths;
    public final int[] blueTeamAssists;
    public final int[] blueTeamTotalDamageDealt;
    public final int[] blueTeamTotalDamageToChampion;
    public final int[] blueTeamTotalDamageTaken;
    public final int[] blueTeamGoldEarned;
    public final int[] blueTeamTotalMinionsKilled;
    public final int blueTeamTotalTowerKills;
    public final int blueTeamTotalBaronKills;
    public final int blueTeamTotalDragonKills;
    public final int blueTeamTotalRiftHeraldKills;

    public final String winningTeamName;
    public final String losingTeamName;
    public final String date;
    public final String duration;


    private Game(GameBuilder builder) {
        this.redTeamName = builder.redTeamName;
        this.redTeamPlayerIds = builder.redTeamPlayerIds;
        this.redTeamPicks = builder.redTeamPicks;
        this.redTeamBans = builder.redTeamBans;
        this.redTeamKills = builder.redTeamKills;
        this.redTeamDeaths = builder.redTeamDeaths;
        this.redTeamAssists = builder.redTeamAssists;
        this.redTeamTotalDamageDealt = builder.redTeamTotalDamageDealt;
        this.redTeamTotalDamageToChampion = builder.redTeamTotalDamageToChampion;
        this.redTeamTotalDamageTaken = builder.redTeamTotalDamageTaken;
        this.redTeamGoldEarned = builder.redTeamGoldEarned;
        this.redTeamTotalMinionsKilled = builder.redTeamTotalMinionsKilled;
        this.redTeamTotalTowerKills = builder.redTeamTotalTowerKills;
        this.redTeamTotalBaronKills = builder.redTeamTotalBaronKills;
        this.redTeamTotalDragonKills = builder.redTeamTotalDragonKills;
        this.redTeamTotalRiftHeraldKills = builder.redTeamTotalRiftHeraldKills;

        this.blueTeamName = builder.blueTeamName;
        this.blueTeamPlayerIds = builder.blueTeamPlayerIds;
        this.blueTeamPicks = builder.blueTeamPicks;
        this.blueTeamBans = builder.blueTeamBans;
        this.blueTeamKills = builder.blueTeamKills;
        this.blueTeamDeaths = builder.blueTeamDeaths;
        this.blueTeamAssists = builder.blueTeamAssists;
        this.blueTeamTotalDamageDealt = builder.blueTeamTotalDamageDealt;
        this.blueTeamTotalDamageToChampion = builder.blueTeamTotalDamageToChampion;
        this.blueTeamTotalDamageTaken = builder.blueTeamTotalDamageTaken;
        this.blueTeamGoldEarned = builder.blueTeamGoldEarned;
        this.blueTeamTotalMinionsKilled = builder.blueTeamTotalMinionsKilled;
        this.blueTeamTotalTowerKills = builder.blueTeamTotalTowerKills;
        this.blueTeamTotalBaronKills = builder.blueTeamTotalBaronKills;
        this.blueTeamTotalDragonKills = builder.blueTeamTotalDragonKills;
        this.blueTeamTotalRiftHeraldKills = builder.blueTeamTotalRiftHeraldKills;

        this.winningTeamName = builder.winningTeamName;
        this.losingTeamName = builder.losingTeamName;
        this.date = builder.date;
        this.duration = builder.duration;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("red_team_name", redTeamName);
        obj.put("blue_team_name", blueTeamName);
        return obj;
    }

    @Override
    public String toString() {
        return "Game{" +
                "redTeamName='" + redTeamName + '\'' +
                ", redTeamPlayerIds=" + Arrays.toString(redTeamPlayerIds) +
                ", redTeamPicks=" + Arrays.toString(redTeamPicks) +
                ", redTeamBans=" + Arrays.toString(redTeamBans) +
                ", redTeamKills=" + Arrays.toString(redTeamKills) +
                ", redTeamDeaths=" + Arrays.toString(redTeamDeaths) +
                ", redTeamAssists=" + Arrays.toString(redTeamAssists) +
                ", redTeamTotalDamageDealt=" + Arrays.toString(redTeamTotalDamageDealt) +
                ", redTeamTotalDamageToChampion=" + Arrays.toString(redTeamTotalDamageToChampion) +
                ", redTeamTotalDamageTaken=" + Arrays.toString(redTeamTotalDamageTaken) +
                ", redTeamGoldEarned=" + Arrays.toString(redTeamGoldEarned) +
                ", redTeamTotalMinionsKilled=" + Arrays.toString(redTeamTotalMinionsKilled) +
                ", redTeamTotalTowerKills=" + redTeamTotalTowerKills +
                ", redTeamTotalBaronKills=" + redTeamTotalBaronKills +
                ", redTeamTotalDragonKills=" + redTeamTotalDragonKills +
                ", redTeamTotalRiftHeraldKills=" + redTeamTotalRiftHeraldKills +
                ", blueTeamName='" + blueTeamName + '\'' +
                ", blueTeamPlayerIds=" + Arrays.toString(blueTeamPlayerIds) +
                ", blueTeamPicks=" + Arrays.toString(blueTeamPicks) +
                ", blueTeamBans=" + Arrays.toString(blueTeamBans) +
                ", blueTeamKills=" + Arrays.toString(blueTeamKills) +
                ", blueTeamDeaths=" + Arrays.toString(blueTeamDeaths) +
                ", blueTeamAssists=" + Arrays.toString(blueTeamAssists) +
                ", blueTeamTotalDamageDealt=" + Arrays.toString(blueTeamTotalDamageDealt) +
                ", blueTeamTotalDamageToChampion=" + Arrays.toString(blueTeamTotalDamageToChampion) +
                ", blueTeamTotalDamageTaken=" + Arrays.toString(blueTeamTotalDamageTaken) +
                ", blueTeamGoldEarned=" + Arrays.toString(blueTeamGoldEarned) +
                ", blueTeamTotalMinionsKilled=" + Arrays.toString(blueTeamTotalMinionsKilled) +
                ", blueTeamTotalTowerKills=" + blueTeamTotalTowerKills +
                ", blueTeamTotalBaronKills=" + blueTeamTotalBaronKills +
                ", blueTeamTotalDragonKills=" + blueTeamTotalDragonKills +
                ", blueTeamTotalRiftHeraldKills=" + blueTeamTotalRiftHeraldKills +
                ", winningTeamName='" + winningTeamName + '\'' +
                ", losingTeamName='" + losingTeamName + '\'' +
                ", date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public static class GameBuilder {
        private String redTeamName;
        private String[] redTeamPlayerIds;
        private String[] redTeamPicks;
        private String[] redTeamBans;
        private int[] redTeamKills;
        private int[] redTeamDeaths;
        private int[] redTeamAssists;
        private int[] redTeamTotalDamageDealt;
        private int[] redTeamTotalDamageToChampion;
        private int[] redTeamTotalDamageTaken;
        private int[] redTeamGoldEarned;
        private int[] redTeamTotalMinionsKilled;
        private int redTeamTotalTowerKills;
        private int redTeamTotalBaronKills;
        private int redTeamTotalDragonKills;
        private int redTeamTotalRiftHeraldKills;

        private String blueTeamName;
        private String[] blueTeamPlayerIds;
        private String[] blueTeamPicks;
        private String[] blueTeamBans;
        private int[] blueTeamKills;
        private int[] blueTeamDeaths;
        private int[] blueTeamAssists;
        private int[] blueTeamTotalDamageDealt;
        private int[] blueTeamTotalDamageToChampion;
        private int[] blueTeamTotalDamageTaken;
        private int[] blueTeamGoldEarned;
        private int[] blueTeamTotalMinionsKilled;
        private int blueTeamTotalTowerKills;
        private int blueTeamTotalBaronKills;
        private int blueTeamTotalDragonKills;
        private int blueTeamTotalRiftHeraldKills;

        private String winningTeamName;
        private String losingTeamName;
        private String date;
        private String duration;

        public GameBuilder setRedTeamName(String redTeamName) {
            this.redTeamName = redTeamName;
            return this;
        }

        public GameBuilder setRedTeamPlayerIds(String[] ids) {
            this.redTeamPlayerIds = new String[ids.length];
            System.arraycopy(ids, 0, this.redTeamPlayerIds, 0, ids.length);
            return this;
        }

        public GameBuilder setRedTeamPicks(String[] picks) {
            this.redTeamPicks = new String[picks.length];
            System.arraycopy(picks, 0, this.redTeamPicks, 0, picks.length);
            return this;
        }

        public GameBuilder setRedTeamBans(String[] bans) {
            this.redTeamBans = new String[bans.length];
            System.arraycopy(bans, 0, this.redTeamBans, 0, bans.length);
            return this;
        }

        public GameBuilder setRedTeamKills(int[] kills) {
            this.redTeamKills = new int[kills.length];
            System.arraycopy(kills, 0, this.redTeamKills, 0, kills.length);
            return this;
        }

        public GameBuilder setRedTeamDeaths(int[] deaths) {
            this.redTeamDeaths = new int[deaths.length];
            System.arraycopy(deaths, 0, this.redTeamDeaths, 0, deaths.length);
            return this;
        }

        public GameBuilder setRedTeamAssists(int[] assists) {
            this.redTeamAssists = new int[assists.length];
            System.arraycopy(assists, 0, this.redTeamAssists, 0, assists.length);
            return this;
        }

        public GameBuilder setRedTeamTotalDamageDealt(int[] totalDamageDealt) {
            this.redTeamTotalDamageDealt = new int[totalDamageDealt.length];
            System.arraycopy(totalDamageDealt, 0, this.redTeamTotalDamageDealt, 0, totalDamageDealt.length);
            return this;
        }

        public GameBuilder setRedTeamTotalDamageToChampion(int[] totalDamageToChampion) {
            this.redTeamTotalDamageToChampion = new int[totalDamageToChampion.length];
            System.arraycopy(totalDamageToChampion, 0, this.redTeamTotalDamageToChampion, 0, totalDamageToChampion.length);
            return this;
        }

        public GameBuilder setRedTeamTotalDamageTaken(int[] teamTotalDamageTaken) {
            this.redTeamTotalDamageTaken = new int[teamTotalDamageTaken.length];
            System.arraycopy(teamTotalDamageTaken, 0, this.redTeamTotalDamageTaken, 0, teamTotalDamageTaken.length);
            return this;
        }

        public GameBuilder setRedTeamGoldEarned(int[] goldEarned) {
            this.redTeamGoldEarned = new int[goldEarned.length];
            System.arraycopy(goldEarned, 0, this.redTeamGoldEarned, 0, goldEarned.length);
            return this;
        }

        public GameBuilder setRedTeamTotalMinionsKilled(int[] totalMinionsKilled) {
            this.redTeamTotalMinionsKilled = new int[totalMinionsKilled.length];
            System.arraycopy(totalMinionsKilled, 0, this.redTeamTotalMinionsKilled, 0, totalMinionsKilled.length);
            return this;
        }

        public GameBuilder setRedTeamTotalTowerKills(int totalTowerKills) {
            this.redTeamTotalTowerKills = totalTowerKills;
            return this;
        }

        public GameBuilder setRedTeamTotalBaronKills(int totalBaronKills) {
            this.redTeamTotalBaronKills = totalBaronKills;
            return this;
        }

        public GameBuilder setRedTeamTotalDragonKills(int totalDragonKills) {
            this.redTeamTotalDragonKills = totalDragonKills;
            return this;
        }

        public GameBuilder setRedTeamTotalRiftHeraldKills(int totalRiftHeraldKills) {
            this.redTeamTotalRiftHeraldKills = totalRiftHeraldKills;
            return this;
        }

        public GameBuilder setBlueTeamName(String blueTeamName) {
            this.blueTeamName = blueTeamName;
            return this;
        }

        public GameBuilder setBlueTeamPlayerIds(String[] ids) {
            this.blueTeamPlayerIds = new String[ids.length];
            System.arraycopy(ids, 0, this.blueTeamPlayerIds, 0, ids.length);
            return this;
        }

        public GameBuilder setBlueTeamPicks(String[] picks) {
            this.blueTeamPicks = new String[picks.length];
            System.arraycopy(picks, 0, this.blueTeamPicks, 0, picks.length);
            return this;
        }

        public GameBuilder setBlueTeamBans(String[] bans) {
            this.blueTeamBans = new String[bans.length];
            System.arraycopy(bans, 0, this.blueTeamBans, 0, bans.length);
            return this;
        }

        public GameBuilder setBlueTeamKills(int[] kills) {
            this.blueTeamKills = new int[kills.length];
            System.arraycopy(kills, 0, this.blueTeamKills, 0, kills.length);
            return this;
        }

        public GameBuilder setBlueTeamDeaths(int[] deaths) {
            this.blueTeamDeaths = new int[deaths.length];
            System.arraycopy(deaths, 0, this.blueTeamDeaths, 0, deaths.length);
            return this;
        }

        public GameBuilder setBlueTeamAssists(int[] assists) {
            this.blueTeamAssists = new int[assists.length];
            System.arraycopy(assists, 0, this.blueTeamAssists, 0, assists.length);
            return this;
        }

        public GameBuilder setBlueTeamTotalDamageDealt(int[] totalDamageDealt) {
            this.blueTeamTotalDamageDealt = new int[totalDamageDealt.length];
            System.arraycopy(totalDamageDealt, 0, this.blueTeamTotalDamageDealt, 0, totalDamageDealt.length);
            return this;
        }

        public GameBuilder setBlueTeamTotalDamageToChampion(int[] totalDamageToChampion) {
            this.blueTeamTotalDamageToChampion = new int[totalDamageToChampion.length];
            System.arraycopy(totalDamageToChampion, 0, this.blueTeamTotalDamageToChampion, 0, totalDamageToChampion.length);
            return this;
        }

        public GameBuilder setBlueTeamTotalDamageTaken(int[] teamTotalDamageTaken) {
            this.blueTeamTotalDamageTaken = new int[teamTotalDamageTaken.length];
            System.arraycopy(teamTotalDamageTaken, 0, this.blueTeamTotalDamageTaken, 0, teamTotalDamageTaken.length);
            return this;
        }

        public GameBuilder setBlueTeamGoldEarned(int[] goldEarned) {
            this.blueTeamGoldEarned = new int[goldEarned.length];
            System.arraycopy(goldEarned, 0, this.blueTeamGoldEarned, 0, goldEarned.length);
            return this;
        }

        public GameBuilder setBlueTeamTotalMinionsKilled(int[] totalMinionsKilled) {
            this.blueTeamTotalMinionsKilled = new int[totalMinionsKilled.length];
            System.arraycopy(totalMinionsKilled, 0, this.blueTeamTotalMinionsKilled, 0, totalMinionsKilled.length);
            return this;
        }

        public GameBuilder setBlueTeamTotalTowerKills(int totalTowerKills) {
            this.blueTeamTotalTowerKills = totalTowerKills;
            return this;
        }

        public GameBuilder setBlueTeamTotalBaronKills(int totalBaronKills) {
            this.blueTeamTotalBaronKills = totalBaronKills;
            return this;
        }

        public GameBuilder setBlueTeamTotalDragonKills(int totalDragonKills) {
            this.blueTeamTotalDragonKills = totalDragonKills;
            return this;
        }

        public GameBuilder setBlueTeamTotalRiftHeraldKills(int totalRiftHeraldKills) {
            this.blueTeamTotalRiftHeraldKills = totalRiftHeraldKills;
            return this;
        }

        public GameBuilder setWinningTeamName(String winningTeamName) {
            this.winningTeamName = winningTeamName;
            return this;
        }

        public GameBuilder setLosingTeamName(String losingTeamName) {
            this.losingTeamName = losingTeamName;
            return this;
        }

        public GameBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public GameBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }


}
