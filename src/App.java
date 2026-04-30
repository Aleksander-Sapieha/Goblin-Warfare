import java.util.Scanner;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.File;

import game.Player;
import game.Goblin;
import game.Shop;
import game.Sandbox;
import game.Save;
import game.Stats;
import game.Achievement;

public class App {
    static Achievement achievement_level_5 = new Achievement("Novice Adventurer", "Reach level 5.");
    static Achievement achievement_score_1000 = new Achievement("Rising Star", "Reach a score of 1000.");
    static Achievement achievement_gold_500 = new Achievement("Wealthy", "Accumulate 500 gold.");
    static Achievement achievement_goblin_10 = new Achievement("Goblin Slayer", "Defeat 10 goblins.");
    static Achievement achievement_goblin_5 = new Achievement("Goblin Hunter", "Defeat 5 goblins.");
    static Achievement achievement_goblin_1 = new Achievement("Goblin Killer", "Defeat 1 goblin.");
    static Achievement achievement_multiplayer_win = new Achievement("Multiplayer Champion", "Win a multiplayer match.");
    static Save save = new Save();
    static Player player = null;
    static Stats stats = null;
    public static void main(String[] args) throws Exception {
        if(!new File(System.getenv().get("APPDATA") + File.separator + ".goblin").exists()) {
            new File(System.getenv().get("APPDATA") + File.separator + ".goblin").mkdirs();
            new File(System.getenv().get("APPDATA") + File.separator + ".goblin" + File.separator + "mods").mkdirs();
        }
        Scanner scanner = new Scanner(System.in);
        Stats loadedStats = save.loadGame();
        if (loadedStats != null) {
            try {
                player = new Player("name", loadedStats);
            } catch (Exception e) {
                System.out.println("Error creating player from save: " + e.getMessage());
                player = new Player(null, loadedStats); // fallback
            }
        }else{
            System.out.println("No save file found. Starting a new game.");
            System.out.println("Enter your character's name:");
            String name = scanner.nextLine();
            stats = new Stats();
            player = new Player(name, stats); // fallback

        }
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Starting game...");
                    while (true) {
                        // Game loop
                        clearScreen();
                        if (player.gameOver) {
                            System.out.println("Game Over! Your final score: " + player.score);
                            System.out.println("Final XP: " + player.xp);
                            pause(2000);
                            break;
                        }
                        System.out.println("Your current health: " + player.health);
                        System.out.println("What do you want to do?");
                        System.out.println("1. Look around");
                        System.out.println("2. Purchase items");
                        System.out.println("3. Exit");
                        String action = scanner.nextLine();
                        if (action.equals("1")) {
                            boolean playing = true;
                            while (playing) {
                                int minLevel = player.level;
                                Random rand = new Random();
                                int goblinLevel = rand.nextInt(3) + minLevel; // Goblin level between player level and player level + 2
                                Goblin goblin = new Goblin(goblinLevel, goblinLevel * 100, goblinLevel * 10, goblinLevel * 2);
                                System.out.println("You encounter a goblin!");
                                while (goblin.health > 0 && player.health > 0) {
                                    player.attack(goblin, achievement_goblin_10, achievement_goblin_5, achievement_goblin_1);
                                    pause(660);
                                    if (goblin.health > 0) {
                                        goblin.attack(player);
                                        pause(660);
                                    }
                                }
                                if (player.gameOver) {
                                    break; // Exit game loop if player is defeated
                                }
                                if (player.gold >= 500 && !achievement_gold_500.isUnlocked()) {
                                    achievement_gold_500.unlock();
                                }
                                if (player.score >= 1000 && !achievement_score_1000.isUnlocked()) {
                                    achievement_score_1000.unlock();
                                }
                                if (player.level >= 5 && !achievement_level_5.isUnlocked()) {
                                    achievement_level_5.unlock();
                                }
                                System.out.println("Your current score: " + player.score);
                                System.out.println("Your current XP: " + player.xp);
                                System.out.println("XP to next level: " + player.xpToNextLevel);
                                System.out.println("Your current gold: " + player.gold);
                                System.out.println("Your current level: " + player.level);
                                System.out.println("Your current health: " + player.health);
                                System.out.println("Do you want to continue looking around? (1 for yes, 2 for no)");
                                int continueAction = scanner.nextInt();
                                if (continueAction != 1) {
                                    playing = false;
                                    save.saveGame(stats);
                                }else {
                                    if (player.hasStrengthElixir) {
                                        player.strengthElixirTurns--;
                                        if (player.strengthElixirTurns <= 0) {
                                            player.hasStrengthElixir = false;
                                            System.out.println("Your strength elixir has worn off.");
                                        }
                                    }
                                }
                            }
                            continue;
                        } else if (action.equals("2")) {
                            Shop shop = new Shop();
                            System.out.println("Gold: " + player.gold);
                            System.out.println("XP: " + player.xp);
                            System.out.println("XP to next level: " + player.xpToNextLevel);
                            shop.displayItems();
                            int itemChoice = scanner.nextInt();
                            shop.purchaseItem(itemChoice, player);
                        } else if (action.equals("3")) {
                            System.out.println("Exiting game...");
                            Stats stats = new Stats(player);
                            save.saveGame(stats);
                            break;
                            
                        }else {
                            System.out.println("Invalid action.");
                            continue;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Opening settings...");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    Stats stats = new Stats(player);
                    save.saveGame(stats);
                    scanner.close();
                    System.exit(0);
                    break;
                case 4:
                    System.out.println("Sandbox Mode is currently under development. May contain bugs and incomplete features.");
                    System.out.println("Entering Sandbox Mode...");
                    clearScreen();
                    Sandbox sandbox = new Sandbox();
                    Player sandboxPlayer = null;
                    sandboxPlayer = new Player("SandboxPlayer", new Stats(sandboxPlayer));
                    while (true) {
                        System.out.println("Enter a command (or 'exit' to leave sandbox mode):");
                        String command = scanner.nextLine();
                        if (command.equalsIgnoreCase("exit")) {
                            System.out.println("Exiting Sandbox Mode...");
                            break;
                        }
                        sandbox.parseCommand(command, sandboxPlayer);
                    }
                    break;
                case 5:
                    System.out.println("Enter server IP (default: localhost):");
                    String serverIp = scanner.nextLine().trim();
                    if (serverIp.isEmpty()) {
                        serverIp = "localhost";
                    }
                    runMultiplayer(scanner, serverIp);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
    }

    }

    private static void printMenu() {
        System.out.println("1. Play");
        System.out.println("2. Settings");
        System.out.println("3. Exit");
        System.out.println("4. Sandbox Mode");
        System.out.println("5. Multiplayer Mode");
    }

    private static void runMultiplayer(Scanner scanner, String serverIp) {
        try (Socket socket = new Socket(serverIp, 12345);
             InputStreamReader reader = new InputStreamReader(socket.getInputStream());
             OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
             BufferedReader bufferedReader = new BufferedReader(reader);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            System.out.println("Connected to multiplayer server.");
            System.out.println("Available commands: attack, heal, status, exit");

            String serverResponse = bufferedReader.readLine();
            if (serverResponse != null) {
                System.out.println(serverResponse);
            }
            while (bufferedReader.ready()) {
                serverResponse = bufferedReader.readLine();
                if (serverResponse == null) break;
                System.out.println(serverResponse);
            }

            while (true) {
                System.out.print("> ");
                String messageToServer = scanner.nextLine().trim();
                if (messageToServer.isEmpty()) {
                    continue;
                }

                bufferedWriter.write(messageToServer);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                serverResponse = bufferedReader.readLine();
                if (serverResponse == null) {
                    System.out.println("Server disconnected.");
                    break;
                }
                System.out.println(serverResponse);

                while (bufferedReader.ready()) {
                    serverResponse = bufferedReader.readLine();
                    if (serverResponse == null) break;
                    if (serverResponse.trim().equalsIgnoreCase("win")){
                        achievement_multiplayer_win.unlock();
                        stats = new Stats(player);
                        save.saveGame(stats);
                    
                    }
                    System.out.println(serverResponse);
                }

                if (messageToServer.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }
    private static void pause(int milliseconds) {
        try {
            Thread.sleep((milliseconds > 0) ? milliseconds : 2000); // Pause for specified milliseconds or default to 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
