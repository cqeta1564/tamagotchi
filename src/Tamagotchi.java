import java.io.Serializable;
import java.util.Random;

class Tamagotchi implements Serializable {

    private String name;
    private int hunger;
    private int thirst;
    private int fatigue;
    private int stress;
    private int happiness;

    public Tamagotchi(String name) {
        this.name = name;
        this.hunger = 5;
        this.thirst = 5;
        this.fatigue = 5;
        this.stress = 5;
        this.happiness = 5;
    }

    public void feed() {
        hunger = Math.max(0, hunger - 1);
        increaseFatigueRandomly();
        increaseThirstRandomly();
    }

    private void increaseThirstRandomly() {
        int randomValue = new Random().nextInt(3);
        if (randomValue == 0) {
            increaseThirst();
        }
    }

    private void increaseFatigueRandomly() {
        int randomValue = new Random().nextInt(3);
        if (randomValue == 0) {
            increaseFatigue();
        }
    }

    private void increaseHungerRandomly() {
        int randomValue = new Random().nextInt(3);
        if (randomValue == 0) {
            increaseHunger();
        }
    }

    public void water() {
        thirst = Math.max(0, thirst - 1);
        increaseFatigueRandomly();
        increaseHungerRandomly();
    }

    public void rest() {
        fatigue = Math.max(0, fatigue - 1);
        increaseHungerRandomly();
        increaseThirstRandomly();
    }

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getStress() {
        return stress;
    }

    public int getHappiness() {
        return happiness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeStress(int delta) {
        stress = Math.max(0, Math.min(10, stress + delta));
    }

    public void changeHappiness(int delta) {
        happiness = Math.max(0, Math.min(10, happiness + delta));
    }

    public void increaseHunger() {
        hunger = Math.min(10, hunger + 1);
    }

    public void increaseThirst() {
        thirst = Math.min(10, thirst + 1);
    }

    public void increaseFatigue() {
        fatigue = Math.min(10, fatigue + 1);
    }

    public void increaseNeedsPeriodically() {
        if (System.currentTimeMillis() % 15000 < 1000) {
            increaseHunger();
            increaseThirst();
        }

        if (System.currentTimeMillis() % 15000 < 1000) {
            increaseFatigue();
        }
    }

    public void changeStressBasedOnNeeds() {
        int stressChange = 0;

        if (hunger <= 2) {
            stressChange -= 2;
        } else if (hunger <= 5) {
            stressChange -= 1;
        } else if (hunger >= 8) {
            stressChange += 2;
        } else if (hunger >= 5) {
            stressChange += 1;
        }

        if (thirst <= 2) {
            stressChange -= 2;
        } else if (thirst <= 5) {
            stressChange -= 1;
        } else if (thirst >= 8) {
            stressChange += 2;
        } else if (thirst >= 5) {
            stressChange += 1;
        }

        if (fatigue <= 2) {
            stressChange -= 2;
        } else if (fatigue <= 5) {
            stressChange -= 1;
        } else if (fatigue >= 8) {
            stressChange += 2;
        } else if (fatigue >= 5) {
            stressChange += 1;
        }

        changeStress(stressChange);
    }

    public void changeStressRandomly() {
        int randomChange = new Random().nextInt(3) - 1;
        changeStress(randomChange);
    }

    public void updateHappiness() {
        if (stress <= 3) {
            changeHappiness(1);
        } else if (stress >= 7) {
            changeHappiness(-1);
        }
    }
}