import javax.swing.*;
import java.awt.*;

class TamagotchiPanel extends JPanel {

    private final JProgressBar hungerProgressBar;
    private final JProgressBar thirstProgressBar;
    private final JProgressBar fatigueProgressBar;
    private final JProgressBar stressProgressBar;
    private final JProgressBar happinessProgressBar;

    private final NonBlockingConsoleInputListener listener;

    public TamagotchiPanel() {
        setLayout(new GridLayout(0, 2));

        hungerProgressBar = createProgressBarFliped("Hunger");
        thirstProgressBar = createProgressBarFliped("Thirst");
        fatigueProgressBar = createProgressBarFliped("Fatigue");
        stressProgressBar = createProgressBarFliped("Stress");
        happinessProgressBar = createProgressBar("Happiness");

        JButton feedButton = new JButton("Feed");
        JButton waterButton = new JButton("Water");
        JButton restButton = new JButton("Rest");

        feedButton.addActionListener(e -> {
            Main.tamagotchi.feed();
            updateStatus();
        });

        waterButton.addActionListener(e -> {
            Main.tamagotchi.water();
            updateStatus();
        });

        restButton.addActionListener(e -> {
            Main.tamagotchi.rest();
            updateStatus();
        });

        add(feedButton);
        add(hungerProgressBar);
        add(waterButton);
        add(thirstProgressBar);
        add(restButton);
        add(fatigueProgressBar);
        add(stressProgressBar);
        add(happinessProgressBar);

        listener = new NonBlockingConsoleInputListener();
        addKeyListener(listener);
        setFocusable(true);

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> listener.stopListening(mainThread)));
    }

    private JProgressBar createProgressBar(String label) {
        JProgressBar progressBar = new JProgressBar(0, 10);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createTitledBorder(label));
        addColorChangeListener(progressBar);
        return progressBar;
    }

    private JProgressBar createProgressBarFliped(String label) {
        JProgressBar progressBar = new JProgressBar(0, 10);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createTitledBorder(label));
        addColorChangeListenerFliped(progressBar);
        return progressBar;
    }

    private void addColorChangeListener(JProgressBar progressBar) {
        progressBar.addChangeListener(e -> {
            int value = progressBar.getValue();
            Color color = getColorForValue(value);
            progressBar.setForeground(color);
        });
    }

    private void addColorChangeListenerFliped(JProgressBar progressBar) {
        progressBar.addChangeListener(e -> {
            int value = progressBar.getValue();
            Color color = getColorForValueFliped(value);
            progressBar.setForeground(color);
        });
    }

    private Color getColorForValue(int value) {
        float hue = (float) (value * 0.04);  // Map value to hue (0 to 1)
        return Color.getHSBColor(hue, 1, 1);
    }

    private Color getColorForValueFliped(int value) {
        float hue = (float) ((10 - value) * 0.04);  // Map fliped value to hue (0 to 1)

        return Color.getHSBColor(hue, 1, 1);
    }

    void updateStatus() {
        hungerProgressBar.setValue(Main.tamagotchi.getHunger());
        thirstProgressBar.setValue(Main.tamagotchi.getThirst());
        fatigueProgressBar.setValue(Main.tamagotchi.getFatigue());
        stressProgressBar.setValue(Main.tamagotchi.getStress());
        happinessProgressBar.setValue(Main.tamagotchi.getHappiness());
    }
}