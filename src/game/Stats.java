package game;

import java.io.Serializable;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int health;
    public int score;
    public int level;
    public int gold;
    public boolean hasStrengthElixir;
    public int strengthElixirTurns;
    public int xp;
    public int xpToNextLevel;
    public int goblinsDefeated;

    // Default constructor for deserialization
    public Stats() {
        this.health = 100;
        this.score = 0;
        this.level = 1;
        this.gold = 0;
        this.hasStrengthElixir = false;
        this.strengthElixirTurns = 0;
        this.xp = 0;
        this.xpToNextLevel = 100;
        this.goblinsDefeated = 0;
    }

    public Stats(Player player) {
        this.health = player.health;
        this.score = player.score;
        this.level = player.level;
        this.gold = player.gold;
        this.hasStrengthElixir = player.hasStrengthElixir;
        this.strengthElixirTurns = player.strengthElixirTurns;
        this.xp = player.xp;
        this.xpToNextLevel = player.xpToNextLevel;
        this.goblinsDefeated = player.goblinsDefeated;
    }
}
