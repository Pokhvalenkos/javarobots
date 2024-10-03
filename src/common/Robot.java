package common;

import tool.common.Position;
import tool.common.ToolRobot;

public interface Robot extends ToolRobot {
    /**
     * Returns the current angle of the robot.
     * @return The angle of the robot.
     */
    int angle();

    /**
     * Checks if the robot can move to the adjacent cell in the direction it's facing.
     * @return True if the adjacent cell exists (i.e., is part of the environment) and is empty.
     */
    boolean canMove();

    /**
     * Returns the current position of the robot in the environment.
     * @return The current position of the robot.
     */
    Position getPosition();

    /**
     * Moves the robot to the adjacent cell in the direction it's facing.
     * @return True if the move is successful, false otherwise.
     */
    boolean move();

    /**
     * Turns the robot by 45 degrees clockwise.
     */
    void turn();
}