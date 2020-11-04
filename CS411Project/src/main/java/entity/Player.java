package entity;

public class Player {
    private int playerID;
    private int teamID;
    private String firstName;
    private String lastName;
    private String commonName;
    private String gender;
    private String position;
    private String birthDate;
    private String nationality;

    private Player(PlayerBuilder builder) {
        this.playerID = builder.playerID;
        this.teamID = builder.teamID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.commonName = builder.commonName;
        this.gender = builder.gender;
        this.position = builder.position;
        this.birthDate = builder.birthDate;
        this.nationality = builder.nationality;
    }

    public int getPlayerID(int playerID) {
        return this.playerID;
    }

    public int getTeamID(int teamID) {
        return this.teamID;
    }

    public String getFirstName(String firstName) {
        return this.firstName;
    }

    public String getlastName(String lastName) {
        return this.lastName;
    }

    public String getCommonName(String commonName) {
        return this.commonName;
    }

    public String getGender(String gender) {
        return this.gender;
    }

    public String getPosition(String position) {
        return this.position;
    }

    public String getBirthDate(String birthDate) {
        return this.birthDate;
    }

    public String getNationality(String birtnationalityhDate) {
        return this.nationality;
    }

    public static class PlayerBuilder {
        private int playerID;
        private int teamID;
        private String firstName;
        private String lastName;
        private String commonName;
        private String gender;
        private String position;
        private String birthDate;
        private String nationality;

        public void setPlayerID(int playerID) {
            this.playerID = playerID;
        }

        public void setTeamID(int teamID) {
            this.teamID = teamID;
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
