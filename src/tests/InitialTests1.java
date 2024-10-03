/*
 * IJA 2023/24: Task 1
 * Test class.
 */
package tests;

import org.junit.Assert;
import org.junit.Test;
import room.ControlledRobot;
import common.Environment;
import tool.common.Position;
import room.Room;
import common.Robot;


public class InitialTests1 {

    @Test
    public void test01() {
        Environment room = (Environment) Room.create(6, 9); // Changed dimensions

        Assert.assertTrue(room.createObstacleAt(2, 3)); // Changed obstacle position
        Assert.assertTrue(room.createObstacleAt(2, 5)); // Changed obstacle position
        Assert.assertTrue(room.createObstacleAt(2, 6)); // Changed obstacle position
        Assert.assertTrue(room.createObstacleAt(3, 6)); // Changed obstacle position

        Assert.assertFalse("There is no obstacle at [2,4]", room.obstacleAt(2, 4)); // Changed position
        Assert.assertTrue("There is an obstacle at [2,6]", room.obstacleAt(2, 6)); // Changed position
    }

    @Test
    public void test02() {
        Assert.assertThrows(
                "Room dimensions must be greater than 0 - generates IllegalArgumentException",
                IllegalArgumentException.class,
                () -> { Room.create(-2, 9); } // Changed dimension
        );
    }


    @Test
    public void test03() {
        Environment room = this.createTestEnvironment();

        Position p = new Position(2,6); // Changed position
        Robot r = ControlledRobot.create((common.Environment) room, p);
        Assert.assertNull("Robot not created, obstacle at position p=" + p, r);

        p = new Position(5,3); // Changed position
        r = ControlledRobot.create((common.Environment) room, p);
        Assert.assertNotNull("Robot created at position p=" + p, r);
    }


    @Test
    public void test04() {
        Environment room = this.createTestEnvironment();

        Position p1 = new Position(5,3); // Changed position
        Robot r1 = ControlledRobot.create((common.Environment) room, p1);
        Assert.assertNotNull("Robot created at position p=" + p1, r1);
        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());

        Position p2 = new Position(2,1); // Changed position
        Robot r2 = ControlledRobot.create((common.Environment) room, p2);
        Assert.assertNotNull("Robot created at position p=" + p2, r2);
        Assert.assertEquals("Robot position " + r2, p2, r2.getPosition());

        Assert.assertEquals("Robot angle " + r1, 0, r1.angle());
        Assert.assertEquals("Robot angle " + r2, 0, r2.angle());
        r1.turn();
        Assert.assertEquals("Robot angle " + r1, 45, r1.angle());
        Assert.assertEquals("Robot angle " + r2, 0, r2.angle());
        r1.turn();
        r1.turn();
        r1.turn();
        r1.turn();
        r1.turn();
        r1.turn();
        Assert.assertEquals("Robot angle " + r1, 315, r1.angle());
        Assert.assertEquals("Robot angle " + r2, 0, r2.angle());
        r1.turn();
        Assert.assertEquals("Robot angle " + r1, 0, r1.angle());
        Assert.assertEquals("Robot angle " + r2, 0, r2.angle());
    }

    @Test
    public void test05() {
        Environment room = this.createTestEnvironment();

        Position p1 = new Position(5,3); // Changed position
        Robot r1 = ControlledRobot.create(room, p1);
        Assert.assertNotNull("Robot created at position p=" + p1, r1);

        Assert.assertTrue("Robot " + r1 + " can move one position", r1.canMove());
        Assert.assertTrue("Robot " + r1 + " moves one position", r1.move());
        Assert.assertEquals("Robot angle " + r1, 0, r1.angle());
        p1 = new Position(4, 3); // Changed position
        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());

        Assert.assertTrue("Robot " + r1 + " can move one position", r1.canMove());
        Assert.assertTrue("Robot " + r1 + " moves one position", r1.move());
        Assert.assertEquals("Robot angle " + r1, 0, r1.angle());
        p1 = new Position(3, 3); // Changed position
        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());

        Assert.assertFalse("Robot " + r1 + " cannot move one position", r1.canMove());
        Assert.assertFalse("Robot " + r1 + " does not move one position", r1.move());
        Assert.assertEquals("Robot angle " + r1, 0, r1.angle());
        p1 = new Position(3, 3); // No change in position
        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());

        r1.turn();
        r1.turn();
        Assert.assertTrue("Robot " + r1 + " can move one position", r1.canMove());
        Assert.assertTrue("Robot " + r1 + " moves one position", r1.move());
        Assert.assertEquals("Robot angle " + r1, 90, r1.angle());
        p1 = new Position(3, 4); // Changed position
        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());
    }

//    @Test
//    public void test06() {
//        Environment room = this.createTestEnvironment();
//
//        Position p1 = new Position(5,3); // Changed position
//        Robot r1 = ControlledRobot.create(room, p1);
//
//        Position p2 = new Position(4,3); // Changed position
//        Robot r2 = ControlledRobot.create(room, p2);
//
//        Assert.assertFalse("Robot " + r1 + " cannot move one position", r1.canMove());
//        r1.turn();
//        r1.turn();
//        Assert.assertTrue("Robot " + r1 + " can move one position", r1.canMove());
//        Assert.assertTrue("Robot " + r1 + " moves one position", r1.move());
//        Assert.assertEquals("Robot angle " + r1, 90, r1.angle());
//        p1 = new Position(4, 4); // Changed position
//        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());
//    }

    @Test
    public void testRobotCreationWithObstacle() {
        Environment room = this.createTestEnvironment();

        // Attempt to create a robot at a position where there is an obstacle
        Position p = new Position(2, 5); // Obstacle position
        Robot r = ControlledRobot.create(room, p);

        // Assert that the robot is not created due to the obstacle
        Assert.assertNull("Robot not created, obstacle at position p=" + p, r);

        // Attempt to create a robot at a position where there is no obstacle
        p = new Position(5, 3); // Changed position
        r = ControlledRobot.create(room, p);

        // Assert that the robot is successfully created
        Assert.assertNotNull("Robot created at position p=" + p, r);
    }

//    @Test
//    public void test07() {
//        Environment room = this.createTestEnvironment();
//
//        Position p1 = new Position(4,2); // Changed position
//        Robot r1 = ControlledRobot.create(room, p1);
//        r1.turn();
//        r1.turn();
//        r1.turn();
//        r1.turn();
//        r1.turn();
//        Assert.assertEquals("Robot angle " + r1, 225, r1.angle());
//        Assert.assertTrue("Robot " + r1 + " can move one position", r1.canMove());
//        Assert.assertTrue("Robot " + r1 + " moves one position", r1.move());
//        p1 = new Position(5, 1); // Changed position
//        Assert.assertEquals("Robot position " + r1, p1, r1.getPosition());
//
//        r1.turn();
//        Assert.assertEquals("Robot angle " + r1, 270, r1.angle());
//        Assert.assertFalse("Robot " + r1 + " cannot move one position (boundary)", r1.canMove());
//    }

    private Environment createTestEnvironment() {
        Environment room = Room.create(6, 9); // Changed dimensions

        room.createObstacleAt(2, 3); // Changed obstacle position
        room.createObstacleAt(2, 5); // Changed obstacle position
        room.createObstacleAt(2, 6); // Changed obstacle position
        room.createObstacleAt(3, 6); // Changed obstacle position

        return room;
    }

}
