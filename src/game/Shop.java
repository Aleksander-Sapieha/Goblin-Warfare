package game;

public class Shop {
    public void displayItems() {
        System.out.println("Welcome to the shop! Here are the items available for purchase:");
        System.out.println("1. Health Potion - Restores 50 health (Cost: 100 gold)");
        System.out.println("2. Strength Elixir - Increases attack power by 5 for the next battle (Cost: 150 gold)");
        System.out.println("3. Level Up Scroll - Instantly level up (Cost: 500 gold)");
        System.out.println("4. Reset Stats - Resets your stats to their base values (Cost: 300 gold)");
        System.out.println("5. Exit Shop");
    }
    public void purchaseItem(int itemNumber, Player player) {
        switch (itemNumber) {
            case 1:
                if (player.gold >= 100) {
                    player.gold -= 100;
                    player.health += 50;
                    System.out.println("You purchased a Health Potion! Your health is now " + player.health);
                    pause();
                } else {
                    System.out.println("You don't have enough gold to purchase this item.");
                    pause();
                }
                break;
            case 2:
                if (player.gold >= 150) {
                    player.gold -= 150;
                    // Implement strength elixir effect in the game loop
                    System.out.println("You purchased a Strength Elixir! Your attack power will be increased for the next battle.");
                    player.hasStrengthElixir = true;
                    player.strengthElixirTurns++; // Add one turn of strength elixir effect
                    pause();
                } else {
                    System.out.println("You don't have enough gold to purchase this item.");
                    pause();
                }
                break;
            case 3:
                if (player.gold >= 500) {
                    player.gold -= 500;
                    player.levelUp();
                } else {
                    System.out.println("You don't have enough gold to purchase this item.");
                    pause();
                    
                }
                break;

            case 4:
                if (player.gold >= 300) {
                    player.gold -= 300;
                    player.resetStats();
                } else {
                    System.out.println("You don't have enough gold to purchase this item.");
                    pause();
                }
            case 5:
                System.out.println("Exiting shop...");
                pause();
                break;
            default:
                System.out.println("Invalid item number.");
                pause();
        }
    }
    private void pause() {
        try {
            Thread.sleep(2000); // Pause for 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
