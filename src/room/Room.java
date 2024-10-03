package room;

import common.Environment;
import common.Obstacle;
import common.Robot;
import tool.common.Position;
import tool.common.ToolRobot;

import java.util.ArrayList;
import java.util.List;

public class Room implements Environment {
    private int rows;
    private int cols;
    private Object[][] grid; // Grid representing the room
    private List<Robot> robots; // List of robots in the room

    private Room(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Object[rows][cols]; // Initialize the grid
        this.robots = new ArrayList<>(); // Initialize the list of robots
    }

    public static Room create(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid room dimensions: rows and cols must be positive.");
        }
        return new Room(rows, cols);
    }

    @Override
    public boolean addRobot(Robot robot) {
        Position p = robot.getPosition();
        if (!containsPosition(p) || grid[p.getRow()][p.getCol()] != null) {
            return false; // Cannot add robot if position is out of bounds or occupied
        }
        grid[p.getRow()][p.getCol()] = robot;
        robots.add(robot); // Add the robot to the list of robots
        return true;
    }

    public boolean containsPosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    @Override
    public boolean createObstacleAt(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false; // Position is out of bounds
        }
        if (grid[row][col] != null) {
            return false; // Obstacle already exists at the position
        }
        grid[row][col] = new Obstacle(this, new Position(row, col));
        return true;
    }

    @Override
    public boolean obstacleAt(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false; // Position is out of bounds
        }
        return grid[row][col] instanceof Obstacle;
    }

    @Override
    public boolean obstacleAt(Position p) {
        return obstacleAt(p.getRow(), p.getCol());
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public List<ToolRobot> robots() {
        // Convert the list of robots to a list of ToolRobots
        List<ToolRobot> toolRobots = new ArrayList<>();
        for (Robot robot : robots) {
            toolRobots.add(robot);
        }
        return toolRobots;
    }

    public boolean robotAt(Position p) {
        if (!containsPosition(p)) {
            return false;
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] instanceof Robot) {
                    Position robotPosition = new Position(row, col);
                    if (robotPosition.equals(p)) {
                        return true; // Robot found at the given position
                    }
                }
            }
        }
        return false; // No robot found at the given position
    }

    @Override
    public int getHeight() {
        return rows;
    }

    @Override
    public int getWidth() {
        return cols;
    }
}
