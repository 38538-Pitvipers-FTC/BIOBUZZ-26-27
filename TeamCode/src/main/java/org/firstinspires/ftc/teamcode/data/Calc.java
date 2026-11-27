package org.firstinspires.ftc.teamcode.data;

import com.pedropathing.math.Pose;

public class Calc {
    // Biobuzz Field Goal/Hive Poses
    public static final Pose RED_AUDIENCE_HIVE = new Pose(58.0, 53.0, 0.0);
    public static final Pose RED_FAR_HIVE = new Pose(58.0, 88.0, 0.0);
    public static final Pose BLUE_AUDIENCE_HIVE = new Pose(84.0, 53.0, 0.0);
    public static final Pose BLUE_FAR_HIVE = new Pose(84.0, 88.0, 0.0);

    // Turret angle calculation
    public static double turretMath(Pose goal, Pose robot) {
        double heading = robot.heading();
        double y_offset = goal.y() - robot.y();
        double x_offset = goal.x() - robot.x();

        return Math.atan2(y_offset, x_offset);
    }

    public static double turretNormalizeAngle(double angDeg) {
        return ((angDeg % 360.0) + 360.0) % 360.0;
    }

    public static Pose calcCurrentGoal(Pose robot) {
        return calcCurrentGoal(Alliance.currentAlliance, robot);
    }

    public static Pose calcCurrentGoal(Alliance alliance, Pose robot) {
        // Center line of the field in Y is ~70.5 inches (141" total width)
        boolean audienceSide = (robot == null) || (robot.y() < 70.5);
        if (alliance == Alliance.RED) {
            return audienceSide ? RED_AUDIENCE_HIVE : RED_FAR_HIVE;
        } else {
            return audienceSide ? BLUE_AUDIENCE_HIVE : BLUE_FAR_HIVE;
        }
    }
}