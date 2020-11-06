package entity;

public class Team {
    private static int teamIDCounter = 22;
    private final int teamID;
    private final String name;

    public Team(String name) {
        teamID = teamIDCounter++;
        this.name = name;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getName() {
        return name;
    }

    public static void setTeamIDCounter(int count) {
        teamIDCounter = count;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", name='" + name + '\'' +
                '}';
    }
}
