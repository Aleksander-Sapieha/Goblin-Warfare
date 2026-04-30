package game;

public class Goblin {
    public int health;
    public int attackPower;
    public int scoreValue;
    public int level;
    public int goldReward;

    public Goblin(int level, int scoreValue, int health, int attackPower) {
        this.health = health;
        this.attackPower = attackPower;
        this.scoreValue = scoreValue;
        this.level = level;
        this.goldReward = level * 10; // Gold reward is based on the goblin's level
    }

    public void attack(Player player) {
        player.health -= attackPower;
        System.out.println("The goblin attacks you for " + attackPower + " damage!");
        if (player.health <= 0) {
            System.out.println("You have been defeated by the goblin...");
            System.out.println("Game Over!");
            player.gameOver = true; // Set game over flag to true
        } else {
            System.out.println("Your remaining health: " + player.health);
        }
    }
}
