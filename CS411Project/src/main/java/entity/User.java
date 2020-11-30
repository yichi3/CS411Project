package entity;

import org.json.JSONObject;

public class User {
    private final String gameID;
    private final String password;
    private final String email;
    private final String phone;
    private final String favoritePosition;
    private final String favoriteChampion;

    public User() {
        gameID = "";
        password = "";
        email = "";
        phone = "";
        favoritePosition = "";
        favoriteChampion = "";
    }

    private User(UserBuilder builder) {
        this.gameID = builder.gameID;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.favoritePosition = builder.favoritePosition;
        this.favoriteChampion = builder.favoriteChampion;
    }

    public String getGameID() {
        return gameID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getFavoritePosition() {
        return favoritePosition;
    }

    public String getFavoriteChampion() {
        return favoriteChampion;
    }

    @Override
    public String toString() {
        return "User{" +
                "gameID='" + gameID + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", favoritePosition='" + favoritePosition + '\'' +
                ", favoriteChampion='" + favoriteChampion + '\'' +
                '}';
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("user_name", gameID);
        obj.put("email", email);
        obj.put("phone", phone);
        obj.put("position", favoritePosition);
        obj.put("fav_champ", favoriteChampion);
        return obj;
    }

    public static class UserBuilder {
        private String gameID;
        private String email;
        private String phone;
        private String password;
        private String favoritePosition;
        private String favoriteChampion;

        public UserBuilder setGameID(String gameID) {
            this.gameID = gameID;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder setFavoritePosition(String position) {
            this.favoritePosition = position;
            return this;
        }

        public UserBuilder setFavoriteChampion(String champion) {
            this.favoriteChampion = champion;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
