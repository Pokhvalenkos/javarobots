import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import room.ControlledRobot;
import room.Room;
import tool.EnvPresenter;
import tool.common.Position;
import common.Robot;
import common.Environment;

import javax.swing.*;

public class ProjectMain {

    private static final int GRID_SIZE = 10;
    private static final String CONFIG_FILE = "config.txt";

    private static boolean[][] obstacleGrid;
    private static Point[] robotPositions;
    private static int robotCount;
    private static boolean placingObstacle = false; // Flag to track whether obstacle placement is selected

    private static void createInteractiveWindow() {
        JFrame frame = new JFrame("Interactive Window");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton obstacleButton = new JButton("Place Obstacle");
        JButton robotButton = new JButton("Place Robot");
        JButton saveButton = new JButton("Save Configuration");

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setBackground(Color.RED);
                button.setPreferredSize(new Dimension(50, 50));
                final int x = i, y = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (placingObstacle) {
                            obstacleGrid[x][y] = true;
                            button.setBackground(Color.BLACK);
                        } else {
                            robotPositions[robotCount++] = new Point(x, y);
                            button.setBackground(Color.RED);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                button.revalidate();
                                button.repaint();
                            }
                        });
                    }
                });
                gridPanel.add(button);
            }
        }

        obstacleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placingObstacle = true;
            }
        });

        robotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placingObstacle = false;
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfiguration();
                // Redirect to presenter
                frame.dispose();
                Environment room = createRoom();
                loadConfigurationFromFile(room);
                EnvPresenter presenter = new EnvPresenter(room);
                presenter.open();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(obstacleButton);
        buttonPanel.add(robotButton);
        buttonPanel.add(saveButton);

        frame.setLayout(new BorderLayout());
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static Environment createRoom() {
        Environment room = Room.create(GRID_SIZE, GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (obstacleGrid[i][j]) {
                    room.createObstacleAt(i, j);
                }
            }
        }

        for (int i = 0; i < robotCount; i++) {
            ControlledRobot.create(room, new Position(robotPositions[i].x, robotPositions[i].y));
        }

        return room;
    }

    private static void saveConfiguration() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (obstacleGrid[i][j]) {
                        writer.println("Obstacle," + i + "," + j);
                    }
                }
            }
            for (int i = 0; i < robotCount; i++) {
                writer.println("Robot," + robotPositions[i].x + "," + robotPositions[i].y);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String... args) {
        obstacleGrid = new boolean[GRID_SIZE][GRID_SIZE]; // Initialize the obstacleGrid array
        robotPositions = new Point[10]; // Assuming maximum 10 robots for now
        robotCount = 0;

        createInteractiveWindow();
    }

    public static void loadConfigurationFromFile(Environment room) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Obstacle")) {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    room.createObstacleAt(x, y);
                } else if (parts[0].equals("Robot")) {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int orientation = Integer.parseInt(parts[3]);
                    Robot robot = ControlledRobot.create(room, new Position(x, y));
                    robot.turn(orientation);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(ProjectMain.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void saveConfigurationToFile(Environment room) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            for (int x = 0; x < room.getWidth(); x++) {
                for (int y = 0; y < room.getHeight(); y++) {
                    if (room.obstacleAt(x, y)) {
                        writer.write(String.format("Obstacle,%d,%d\n", x, y));
                    }
                    Robot robot = ControlledRobot.create(room, new Position(x, y));
                    if (robot != null) {
                        writer.write(String.format("Robot,%d,%d,%d\n", x, y, robot.angle()));
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(ProjectMain.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Uspani vlakna na zadany pocet ms.
     * @param ms Pocet ms pro uspani vlakna.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProjectMain.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
