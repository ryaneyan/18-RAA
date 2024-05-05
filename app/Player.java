package app;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static int playerCount = 0;
    private int score = 0;
    private String name;
    private List<String> actions = new ArrayList<>();
   
    public Player(String name) {
        this.name = name;
    }

    public List<String> getActions() {
        return actions;
    }

    public static void incrementPlayerCount() {
        playerCount++;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
}
