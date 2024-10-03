package tool.common;

import java.util.List;

public interface Environment extends ToolEnvironment {

    int getHeight();
    int getWidth();

    /**
     * Adds a robot to its position in the environment.
     * Returns true if the operation is successful.
     * @param robot The robot to be inserted.
     * @return True if the operation is successful, false otherwise.
     */
    boolean addRobot(Robot robot);


    /**
     * Verifies if the given position is inside the environment.
     * @param pos The position to be checked.
     * @return True if the position is within the environment, false otherwise.
     */
    boolean containsPosition(Position pos);


    /**
     * Creates an obstacle at the specified position.
     * Returns true if the operation is successful.
     * @param row The row of the position.
     * @param col The column of the position.
     * @return True if the operation is successful, false otherwise.
     */
    boolean createObstacleAt(int row, int col);


    /**
     * Verifies if there is an obstacle at the specified position.
     * Returns true if there is an obstacle at the position.
     * @param row The row of the position.
     * @param col The column of the position.
     * @return True if there is an obstacle at the position, false otherwise.
     */
    boolean obstacleAt(int row, int col);


    /**
     * Verifies if there is an obstacle at the specified position.
     * Returns true if there is an obstacle at the position.
     * @param p The position to be checked.
     * @return True if there is an obstacle at the position, false otherwise.
     */
    boolean obstacleAt(Position p);


    /**
     * Verifies if there is a robot at the specified position.
     * Returns true if there is a robot at the position.
     * @param p The position to be checked.
     * @return True if there is a robot at the position, false otherwise.
     */
    boolean robotAt(Position p);

}