package org.firstinspires.ftc.teamcode.data;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.lynx.NextLynxModule;

public class Config {

    // Use strings because pedro wants
    public static String frontLeft = "frontLeft";
    public static String frontRight = "frontRight";
    public static String backLeft = "backLeft";
    public static String backRight = "backRight";

    /* CONTROL HUB MOTORS */
    public static NextLynxModule leftLauncherMotorModule = RobotController.controlHub();
    public static int leftLauncherMotorPort = 1;
    public static NextLynxModule rightLauncherMotorModule = RobotController.controlHub();
    public static int rightLauncherMotorPort = 2;

    /* EXPANSION HUB MOTORS */
    public static NextLynxModule intakeMotorModule = RobotController.expansionHub();
    public static int intakeMotorPort = 1;
    public static NextLynxModule transferMotorModule = RobotController.expansionHub();
    public static int transferMotorPort = 2;

    /* CONTROL HUB SERVOS */
    public static NextLynxModule launcherBackPlateServoModule = RobotController.controlHub();
    public static int launcherBackPlateServoPort = 0;
    public static NextLynxModule intakeRampServoModule = RobotController.controlHub();
    public static int intakeRampServoPort = 2;
    public static NextLynxModule launcherGateServoModule = RobotController.expansionHub();
    public static int launcherGateServoPort = 5;

    /* EXPANSION HUB SERVOS */
    public static NextLynxModule leftTurretServoModule = RobotController.expansionHub();
    public static int leftTurretServoPort = 0;
    public static NextLynxModule rightTurretServoModule = RobotController.expansionHub();
    public static int rightTurretServoPort = 3;

    /* CONTROL HUB DIGITAL SENSORS */
    // theoretically we need no color sensor if the robot cant intake in first place
    public static NextLynxModule intakeBeamBreakModule = RobotController.controlHub();
    public static int intakeBeamBreakPort = 3;

    public static NextLynxModule launcherBeamBreakModule = RobotController.controlHub();
    public static int launcherBeamBreakPort = 5;

}