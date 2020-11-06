package entity;

public class Team {
    private static int teamIDCounter = 0;
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

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", name='" + name + '\'' +
                '}';
    }
}
