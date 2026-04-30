package game;

import java.io.*;

public class Sandbox {
    public void parseCommand(String command, Player player) {
        switch (command.toLowerCase().split(" ")[0]) {
            case "message":
                // Code to display a message
                String message = command.substring(command.indexOf(" ") + 1);
                System.out.println(message);
                break;
            case "addgold":
                // Code to add gold to the player
                player.gold += 100;
                break;
            case "heal":
                // Code to heal the player
                player.health = 100;
                break;

            case "spawngoblin_easy":
                // Code to spawn a goblin
                Goblin goblin = new Goblin(player.level, player.level * 100, player.level * 10, player.level * 2);
                while (goblin.health > 0 && player.health > 0) {
                    player.attack(goblin, null, null, null); // Pass null for achievements since this is just a sandbox command
                    if (goblin.health > 0) {
                        goblin.attack(player);
                    }
                }
                break;
            case "spawngoblin_hard":
                // Code to spawn a stronger goblin
                Goblin hardGoblin = new Goblin(player.level + 5, (player.level + 5) * 100, (player.level + 5) * 10, (player.level + 5) * 2);
                hardGoblin.attack(player);
                break;

            case "levelup":
                // Code to level up the player
                player.levelUp();
                break;

            case "reset":
                // Code to reset the player's stats
                player.health = 100;
                player.gold = 0;
                player.score = 0;
                player.level = 1;
                break;


            case "loadscript":
                // Code to load and execute a script file
                String[] parts = command.split(" ", 2);
                if (parts.length < 2) {
                    System.out.println("Usage: loadscript <path>");
                    break;
                }
                String actualPath = parts[1].replaceAll("\"", ""); // Remove quotes if present
                // Implement script loading and execution logic here
                 try (BufferedReader br = new BufferedReader(new FileReader(actualPath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if(line.trim().toLowerCase().startsWith("if ")){
                            // Implement simple if condition parsing and execution
                            String ifLine = line.trim().substring(3).trim();
                            int thenIndex = ifLine.toLowerCase().indexOf(" then ");
                            if(thenIndex != -1){
                                String condition = ifLine.substring(0, thenIndex).trim();
                                String commandToExecute = ifLine.substring(thenIndex + 6).trim();
                                boolean conditionMet = false;
                                // Simple condition parsing (e.g., health<50)
                                if (condition.contains("<")) {
                                    String[] conditionParts = condition.split("<", 2);
                                    String stat = conditionParts[0].trim();
                                    int value = Integer.parseInt(conditionParts[1].trim());
                                    if (stat.equalsIgnoreCase("health") && player.health < value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("gold") && player.gold < value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("level") && player.level < value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("score") && player.score < value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("xp") && player.xp < value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("strengthelixirturns") && player.strengthElixirTurns < value) {
                                        conditionMet = true;
                                    }
                                } else if (condition.contains(">")) {
                                    String[] conditionParts = condition.split(">", 2);
                                    String stat = conditionParts[0].trim();
                                    int value = Integer.parseInt(conditionParts[1].trim());
                                    if (stat.equalsIgnoreCase("health") && player.health > value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("gold") && player.gold > value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("level") && player.level > value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("score") && player.score > value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("xp") && player.xp > value) {
                                        conditionMet = true;
                                    }else if (stat.equalsIgnoreCase("strengthelixirturns") && player.strengthElixirTurns > value) {
                                        conditionMet = true;
                                    }
                                } else if (condition.contains("==")) {
                                    String[] conditionParts = condition.split("==", 2);
                                    String stat = conditionParts[0].trim();
                                    String val = conditionParts[1].trim();
                                    if (stat.equalsIgnoreCase("hasstrengthelixir")) {
                                        boolean boolVal = Boolean.parseBoolean(val);
                                        if (player.hasStrengthElixir == boolVal) {
                                            conditionMet = true;
                                        }
                                    }
                                }
                                if (conditionMet) {
                                    parseCommand(commandToExecute, player);
                                }
                            }
                        }else {
                            parseCommand(line, player);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error loading script: " + e.getMessage());
                }
                break;
            case "help":
                // Code to display available commands
                System.out.println("Available commands:");
                System.out.println("addgold - Add 100 gold to the player");
                System.out.println("heal - Restore player's health to 100");
                System.out.println("spawngoblin_easy - Spawn a goblin with level equal to the player's level");
                System.out.println("spawngoblin_hard - Spawn a goblin with level 5 levels higher than the player");
                System.out.println("levelup - Level up the player");
                System.out.println("reset - Reset the player's stats");
                System.out.println("help - Display available commands");
                System.out.println("loadscript <path> - Load and execute a script file");
                System.out.println("Script files can contain commands and simple if conditions (e.g., 'if health<50 then addgold')");
                System.out.println("----------------------------------------");
                System.out.println("Scripts include if statements:");
                System.out.println("if <statistic> > <value> then <command>");
                System.out.println("if <statistic> < <value> then <command>");
                System.out.println("if <statistic> == <value> then <command>");
                 System.out.println("Supported statistics: health, gold, level, score, xp, strengthelixirturns, hasStrengthElixir");
                break;
            default:
                System.out.println("Unknown command.");
        }
    }
}
