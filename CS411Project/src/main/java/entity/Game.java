package entity;

import org.json.JSONObject;

public class Game {
    private final String redTeamName;
    private final String blueTeamName;
    private final int redTeamKill;
    private final int blueTeamKill;

    private Game(GameBuilder builder) {
        this.redTeamName = builder.redTeamName;
        this.blueTeamName = builder.blueTeamName;
        this.redTeamKill = builder.redTeamKill;
        this.blueTeamKill = builder.blueTeamKill;
    }

    public String getRedTeamName() {
        return redTeamName;
    }

    public String getBlueTeamName() {
        return blueTeamName;
    }

    public int getBlueTeamKill() {
        return blueTeamKill;
    }

    public int getRedTeamKill() {
        return redTeamKill;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("red_team_name", redTeamName);
        obj.put("blue_team_name", blueTeamName);
        obj.put("red_team_kill", redTeamKill);
        obj.put("blue_team_kill", blueTeamKill);
        return obj;
    }

    public static class GameBuilder {
        private String redTeamName;
        private String blueTeamName;
        private int redTeamKill;
        private int blueTeamKill;

        public GameBuilder setRedTeamName(String redTeamName) {
            this.redTeamName = redTeamName;
            return this;
        }

        public GameBuilder setBlueTeamName(String blueTeamName) {
            this.blueTeamName = blueTeamName;
            return this;
        }

        public GameBuilder setRedTeamKill(int redTeamKill) {
            this.redTeamKill = redTeamKill;
            return this;
        }

        public GameBuilder setBlueTeamKill(int blueTeamKill) {
            this.blueTeamKill = blueTeamKill;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }


}
