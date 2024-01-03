import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    static Tamagotchi tamagotchi;
    private static JLabel nameLabel;
    private static TamagotchiPanel tamagotchiPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame startFrame = new JFrame("Start Game");
            startFrame.setSize(400, 300);
            startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Načtení pozice okna
            Point windowLocation = loadWindowLocation();
            if (windowLocation != null) {
                startFrame.setLocation(windowLocation);
            } else {
                startFrame.setLocationRelativeTo(null);
            }

            JTextField nameField = new JTextField("Enter name");
            JButton newGameButton = new JButton("New Game");
            JButton loadGameButton = new JButton("Load Game");
            JButton quitButton = new JButton("Quit");

            nameLabel = new JLabel("Tamagotchi");
            nameLabel.setHorizontalAlignment(JLabel.CENTER);

            newGameButton.addActionListener(e -> {
                startFrame.dispose();
                startNewGame(nameField.getText());
            });

            loadGameButton.addActionListener(e -> {
                startFrame.dispose();
                loadGame(nameField.getText());
            });

            quitButton.addActionListener(e -> System.exit(0));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(nameField);
            panel.add(newGameButton);
            panel.add(loadGameButton);
            panel.add(quitButton);

            startFrame.add(nameLabel, BorderLayout.NORTH);
            startFrame.add(panel, BorderLayout.CENTER);

            startFrame.setResizable(false);
            startFrame.setVisible(true);
        });
    }

    private static void startNewGame(String petName) {
        tamagotchi = new Tamagotchi(petName);
        startGame();
    }

    private static void loadGame(String petName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tamagotchi_save.dat"))) {
            tamagotchi = (Tamagotchi) ois.readObject();
            startGame();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading game. Starting new game instead.");
            startNewGame(petName);
        }
    }

    private static void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("Tamagotchi Game");
            gameFrame.setSize(400, 300);

            // Načtení pozice okna
            Point windowLocation = loadWindowLocation();
            if (windowLocation != null) {
                gameFrame.setLocation(windowLocation);
            } else {
                gameFrame.setLocationRelativeTo(null);
            }

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            nameLabel = new JLabel("Tamagotchi: " + tamagotchi.getName());
            nameLabel.setHorizontalAlignment(JLabel.CENTER);

            tamagotchiPanel = new TamagotchiPanel();

            gameFrame.add(nameLabel, BorderLayout.NORTH);
            gameFrame.add(tamagotchiPanel, BorderLayout.CENTER);

            gameFrame.setResizable(false);
            gameFrame.setVisible(true);

            Timer statusTimer = new Timer(1000, e -> updateStatus());
            statusTimer.start();

            gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    saveGame();
                    // Uložení pozice okna
                    saveWindowLocation(gameFrame.getLocation());
                }
            });
        });
    }

    private static void updateStatus() {
        updateTime();
        tamagotchi.changeStressBasedOnNeeds();
        tamagotchi.changeStressRandomly();
        tamagotchi.increaseNeedsPeriodically();
        tamagotchi.updateHappiness();
        tamagotchiPanel.updateStatus();
    }

    private static void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = dateFormat.format(new Date());
        nameLabel.setText("Tamagotchi: " + tamagotchi.getName() + " | Time: " + formattedTime);
    }

    private static void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tamagotchi_save.dat"))) {
            oos.writeObject(tamagotchi);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving game.");
        }
    }

    private static void saveWindowLocation(Point location) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("window_location.dat"))) {
            oos.writeObject(location);
            System.out.println("Window location saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving window location.");
        }
    }

    private static Point loadWindowLocation() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("window_location.dat"))) {
            return (Point) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading window location.");
            return null;
        }
    }
}
