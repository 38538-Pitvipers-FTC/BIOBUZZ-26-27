package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;

@NextTeleop(name = "Advik S is Fat")
public class FirstNextOp extends NextOpMode {
    private final ViperRobot robot;

    public FirstNextOp(ViperRobot robot) {
        super(robot);
        this.robot = robot;
    }

    @Override
    public void start() {
        robot.chassis.initImu(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));

        robot.chassis.startDriveFieldCentric(gamepad1);
    }
}