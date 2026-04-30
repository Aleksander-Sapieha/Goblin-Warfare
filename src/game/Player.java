package game;

public class Player {
    public String name;
    public int health;
    public int score;
    public int level;
    public int gold;
    public boolean hasStrengthElixir;
    public int strengthElixirTurns;
    public int xp;
    public int xpToNextLevel;
    public boolean gameOver;
    public int goblinsDefeated;

    public Player(String name, Stats stats) {
        this.name = name != null ? name : "Player";
        this.health = stats.health != 0 ? stats.health : 100;
        this.score = stats.score != 0 ? stats.score : 0;
        this.level = stats.level != 0 ? stats.level : 1;
        this.gold = stats.gold != 0 ? stats.gold : 0;
        this.hasStrengthElixir = stats.hasStrengthElixir != false ? stats.hasStrengthElixir : false;
        this.strengthElixirTurns = stats.strengthElixirTurns != 0 ? stats.strengthElixirTurns : 0;
        this.xp = stats.xp != 0 ? stats.xp : 0;
        this.xpToNextLevel = stats.xpToNextLevel != 0 ? stats.xpToNextLevel : 100;
        this.goblinsDefeated = stats.goblinsDefeated != 0 ? stats.goblinsDefeated : 0;
        this.gameOver = false;
    }

    public void levelUp() {
        level++;
        xpToNextLevel *= 5;
        health += 20;
        System.out.println("Congratulations! You've leveled up to level " + level + "!");
    }

    public String getName() {
        return name;
    }

    public void attack(Goblin goblin, Achievement achievement_goblin_10, Achievement achievement_goblin_5, Achievement achievement_goblin_1) {
        int damage = 10 + (level * 2);
        goblin.health -= damage;
        System.out.println("You attack the goblin for " + damage + " damage!");
        if (goblin.health <= 0) {
            System.out.println("You defeated the goblin!");
            score += goblin.scoreValue;
            gold += goblin.goldReward;
            xp += goblin.scoreValue;
            goblinsDefeated++;
            if (goblinsDefeated >= 10) {
                achievement_goblin_10.unlock();
            }else if(goblinsDefeated == 5){
                achievement_goblin_5.unlock();
            }else if(goblinsDefeated == 1){
                achievement_goblin_1.unlock();
            }
            if (xp >= xpToNextLevel) {
                levelUp();
            }
        } else {
            System.out.println("Goblin's remaining health: " + goblin.health);
        }
    }

    public void resetStats() {
        health = 100;
        score = 0;
        level = 1;
        gold = 0;
        hasStrengthElixir = false;
        strengthElixirTurns = 0;
        xp = 0;
        xpToNextLevel = 100;
        System.out.println("Your stats have been reset to their base values.");
    }

    public Stats GetStats() {
        return (new Stats(this));
    }
}


