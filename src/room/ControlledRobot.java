package room;


import common.Environment;
import common.Robot;
import tool.common.Position;

import java.util.ArrayList;
import java.util.List;

public class ControlledRobot implements Robot {
    private Environment environment;
    private Position position;
    private int angle;
    private List<Observer> observers;

    private ControlledRobot(Environment environment, Position position) {
        this.environment = environment;
        this.position = position;
        this.angle = 0; // default angle is set to 0
        this.observers = new ArrayList<>();
    }

    /**
     * Factory method to create a ControlledRobot instance and place it into the environment at the specified position.
     * @param env The environment.
     * @param pos The position.
     * @return The created robot, or null if the position is occupied by an obstacle.
     */
    public static ControlledRobot create(Environment env, Position pos) {
        if (env.obstacleAt(pos)) {
            return null; // Cannot create robot at position occupied by obstacle
        }
        ControlledRobot robot = new ControlledRobot(env, pos);
        env.addRobot(robot); // Add the robot to the environment
        return robot;
    }

    @Override
    public int angle() {
        return angle;
    }

    @Override
    public void turn(int i) {
        // Calculate the angle to turn based on the number of times and turn clockwise
        angle = (angle + (i * 45)) % 360;

        // Notify observers
        notifyObservers();
    }


    @Override
    public boolean canMove() {
        // Get the current position of the robot
        int row = position.getRow();
        int col = position.getCol();

        // Calculate the next position based on the direction the robot is facing
        int deltaRow = 0;
        int deltaCol = 0;
        if (angle == 0) {
            // Move up
            deltaRow = -1;
        } else if (angle == 45) {
            // Move up-right
            deltaRow = -1;
            deltaCol = 1;
        } else if (angle == 90) {
            // Move right
            deltaCol = 1;
        } else if (angle == 135) {
            // Move down-right
            deltaRow = 1;
            deltaCol = 1;
        } else if (angle == 180) {
            // Move down
            deltaRow = 1;
        } else if (angle == 225) {
            // Move down-left
            deltaRow = 1;
            deltaCol = -1;
        } else if (angle == 270) {
            // Move left
            deltaCol = -1;
        } else if (angle == 315) {
            // Move up-left
            deltaRow = -1;
            deltaCol = -1;
        }

        // Calculate the next position
        int nextRow = row + deltaRow;
        int nextCol = col + deltaCol;

        // Check if the next position is within the boundaries of the room
        if (nextRow < 0 || nextRow >= environment.getHeight() || nextCol < 0 || nextCol >= environment.getWidth()) {
            return false; // Next position is out of bounds
        }

        // Create the next position
        Position nextPosition = new Position(nextRow, nextCol);

        // Check if the next position is occupied by an obstacle or another robot
        if (environment.obstacleAt(nextPosition) || environment.robotAt(nextPosition)) {
            return false; // Next position is occupied by an obstacle or another robot
        }

        return true; // Robot can move to the next position
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean move() {
        // Check if the robot can move to the next position
        if (canMove()) {
            // Calculate the change in position based on the current angle
            int deltaRow = 0;
            int deltaCol = 0;
            if (angle == 0) {
                // Move up
                deltaRow = -1;
            } else if (angle == 45) {
                // Move up-right
                deltaRow = -1;
                deltaCol = 1;
            } else if (angle == 90) {
                // Move right
                deltaCol = 1;
            } else if (angle == 135) {
                // Move down-right
                deltaRow = 1;
                deltaCol = 1;
            } else if (angle == 180) {
                // Move down
                deltaRow = 1;
            } else if (angle == 225) {
                // Move down-left
                deltaRow = 1;
                deltaCol = -1;
            } else if (angle == 270) {
                // Move left
                deltaCol = -1;
            } else if (angle == 315) {
                // Move up-left
                deltaRow = -1;
                deltaCol = -1;
            }

            // Calculate the next position
            int nextRow = position.getRow() + deltaRow;
            int nextCol = position.getCol() + deltaCol;

            // Create the next position
            Position nextPosition = new Position(nextRow, nextCol);

            // Check if the next position is within the bounds of the environment and not occupied by an obstacle
            if (nextRow >= 0 && nextRow < environment.getHeight() && nextCol >= 0 && nextCol < environment.getWidth() && !environment.obstacleAt(nextPosition)) {
                // Update the move
                position = nextPosition;

                // Notify observers
                notifyObservers();

                return true;
            }
        }
        return false; // Robot cannot move to the next position
    }

    @Override
    public void turn() {
        // Turn the robot by 45 degrees clockwise
        angle = (angle + 45) % 360;
        // Notify observers
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}