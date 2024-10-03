package tool.common;

public class Obstacle {
    private Environment environment;
    private Position position;

    /**
     * Constructor setting the environment and position of the obstacle in this environment.
     * @param env The environment.
     * @param pos The position.
     */
    public Obstacle(Environment env, Position pos) {
        this.environment = env;
        this.position = pos;
    }

    /**
     * Returns the position of the obstacle in the environment.
     * @return The position of the obstacle.
     */
    public Position getPosition() {
        return this.position;
    }
}