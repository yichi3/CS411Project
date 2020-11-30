package entity;

public class Champion {
    private final String championName;

    public Champion(String championName) {
        this.championName = championName;
    }

    public String getChampionName() {
        return championName;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "championName='" + championName + '\'' +
                '}';
    }
}
