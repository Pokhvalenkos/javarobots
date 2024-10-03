/*
 * IJA 2023/24: Task 2
 * Test class.
 */
package tests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import tool.EnvTester;
import common.Robot;
import room.ControlledRobot;
import tool.common.Position;
import tool.common.ToolEnvironment;

import room.Room;
import common.Environment;
import tool.common.ToolRobot;

import java.util.function.Function;


public class InitialTests2 {

    private ToolEnvironment room;
    private Robot r1, r2;

    @Before
    public void setUp() {
        Environment room = Room.create(6, 9); // Changed dimensions

        room.createObstacleAt(2, 3); // Changed obstacle position
        room.createObstacleAt(2, 5); // Changed obstacle position
        room.createObstacleAt(2, 6); // Changed obstacle position
        room.createObstacleAt(3, 6); // Changed obstacle position

        Position p1 = new Position(2,6); // Changed position
        r1 = ControlledRobot.create(room, p1);
        Position p2 = new Position(5,3); // Changed position
        r2 = ControlledRobot.create(room, p2);

        this.room = room;
    }

    @Test
    public void testEnvironment() {
        List<ToolRobot> robots = room.robots();
        Assert.assertEquals("Environment contains 2 robots", 2, robots.size());
        robots.remove(0);
        Assert.assertEquals("Environment still contains 2 robots", 2, room.robots().size());
    }

    @Test
    public void testRobotMeetsRobot() {
        r1.turn(2);
        Assert.assertTrue("Move r1 to [2,7] successful.", r1.move());
        Assert.assertEquals("r1 position = [2,7].", new Position(2,7), r1.getPosition());

        Assert.assertFalse("Move r1 to [2,6] unsuccessful.", r1.move());
        Assert.assertEquals("r1 position = [2,7].", new Position(2,7), r1.getPosition());

        r1.turn(6);
        Assert.assertTrue("Move r1 to [3,7] successful.", r1.move());
        Assert.assertEquals("r1 position = [3,7].", new Position(3,7), r1.getPosition());
    }

    @Test
    public void testRobotCanMoveToEmptySpace() {
        // Move robot r1 to an empty space [3, 6]
        r1.turn(6); // Turn to face downwards
        Assert.assertTrue("Move r1 to [3,6] successful.", r1.move());
        Assert.assertEquals("r1 position = [3,6].", new Position(3,6), r1.getPosition());

        // Move robot r2 to an empty space [4, 3]
        r2.turn(0); // Turn to face right
        Assert.assertTrue("Move r2 to [4,3] successful.", r2.move());
        Assert.assertEquals("r2 position = [4,3].", new Position(4,3), r2.getPosition());
    }

    @Test
    public void testNotificationGhostMoving() {
        EnvTester tester = new EnvTester(room);

        /* Tests when the move is successful.
         * When moving or turning, the robot will notify dependent objects exactly once.
         * Other objects will remain without notification.
         */
        testNotificationGhostMoving(tester, true, r1, (r) -> r.move());
        testNotificationGhostMoving(tester, true, r1, (r) -> r.move());
        testNotificationGhostMoving(tester, false, r1, (r) -> r.move());
        testNotificationGhostMoving(tester, true, r1, (r) -> {r.turn(2); return true;});
    }

    private void testNotificationGhostMoving(EnvTester tester, boolean success, Robot robot, Function<Robot, Boolean> action) {
        StringBuilder msg;
        boolean res;
        List<ToolRobot> notified;

        // No notifications exist yet
        notified = tester.checkEmptyNotification();
        Assert.assertTrue("No notifications expected, yet found: " + notified, notified.isEmpty());

        // If the action should succeed
        if (success) {
            Assert.assertTrue("Action on " + r1 + " should succeed.", action.apply(r1));
            msg = new StringBuilder();
            // Verify correct notifications
            res = tester.checkNotification(msg, robot);
            Assert.assertTrue("Notification test: " + msg, res);
        }
        // If the action should fail
        else {
            Assert.assertFalse("Action on " + r1 + " should fail.", action.apply(r1));
            // No notifications were sent
            notified = tester.checkEmptyNotification();
            Assert.assertTrue("No notifications expected, yet found: " + notified, notified.isEmpty());
        }
    }

}
