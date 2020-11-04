package entity;

import org.json.JSONObject;

public class Player {
    private final String teamName;
    private final String firstName;
    private final String lastName;
    private final String commonName;
    private final String gender;
    private final String position;
    private final String birthDate;
    private final String nationality;

    private Player(PlayerBuilder builder) {
        this.teamName = builder.teamName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.commonName = builder.commonName;
        this.gender = builder.gender;
        this.position = builder.position;
        this.birthDate = builder.birthDate;
        this.nationality = builder.nationality;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("team_name", teamName);
        obj.put("first_name", firstName);
        obj.put("last_name", lastName);
        obj.put("game_id", commonName);
        obj.put("gender", gender);
        obj.put("position", position);
        obj.put("birth_date", birthDate);
        obj.put("nationality", nationality);
        return obj;
    }

    public static class PlayerBuilder {
        private String teamName;
        private String firstName;
        private String lastName;
        private String commonName;
        private String gender;
        private String position;
        private String birthDate;
        private String nationality;

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setCommonName(String commonName) {
            this.commonName = commonName;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }


        public Player build() {
            return new Player(this);
        }

    }

}