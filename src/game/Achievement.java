package game;

public class Achievement {
    private String name;
    private String description;
    private boolean unlocked;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
        this.unlocked = false;
    }

    public void unlock() {
        this.unlocked = true;
        System.out.println("Achievement Unlocked: " + name + " - " + description);
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}
