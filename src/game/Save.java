package game;

import java.io.*;

public class Save {

    String appdata = System.getenv("APPDATA");
    String saveFilePath = appdata + File.separator + ".goblin" + File.separator + "goblin_warfare_save.dat";

    public void saveGame(Stats stats) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(stats);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game: " + e.getMessage());
        }
    }

    public Stats loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            Stats stats = (Stats) ois.readObject();
            System.out.println("Game loaded successfully!");
            return stats;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading the game: " + e.getMessage());
            return null;
        }
    }
}
