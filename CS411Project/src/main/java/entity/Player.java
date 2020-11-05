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
    private final String imageUrl;

    private Player(PlayerBuilder builder) {
        this.teamName = builder.teamName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.commonName = builder.commonName;
        this.gender = builder.gender;
        this.position = builder.position;
        this.birthDate = builder.birthDate;
        this.nationality = builder.nationality;
        this.imageUrl = builder.imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
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
        obj.put("image_url", imageUrl);
        return obj;
    }

    @Override
    public String toString() {
        return "Player{" +
                "teamName='" + teamName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", gender='" + gender + '\'' +
                ", position='" + position + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
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
        private String imageUrl;

        public PlayerBuilder setTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public PlayerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PlayerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PlayerBuilder setCommonName(String commonName) {
            this.commonName = commonName;
            return this;
        }

        public PlayerBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public PlayerBuilder setPosition(String position) {
            this.position = position;
            return this;
        }

        public PlayerBuilder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PlayerBuilder setNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public PlayerBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Player build() {
            return new Player(this);
        }

    }

}