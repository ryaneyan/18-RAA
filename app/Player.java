package app;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public Player(String name) {
        this.name = name;
    }
    private int score;
    private String name;

    //list of actions done by player
    private List<String> actions = new ArrayList<>();

    public List<String> getActions() {
        return actions;
    }
}
