package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.sensors.NextIMU;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.drive.DriveCommands;

public class Chassis implements Mechanism {
    public final NextMotor frontLeft = new NextMotor("frontLeft");
    public final NextMotor frontRight = new NextMotor("frontRight");
    public final NextMotor backLeft = new NextMotor("backLeft");
    public final NextMotor backRight = new NextMotor("backRight");

    public final NextIMU imu = new NextIMU("imu");

    public Chassis() {
        frontLeft.setDirection(NextMotor.Direction.REVERSE);
        backLeft.setDirection(NextMotor.Direction.REVERSE);
    }

    public void initImu(RevHubOrientationOnRobot orientation) {
        imu.initialize(new IMU.Parameters(orientation));
    }

    public void startDriveFieldCentric(Gamepad gamepad) {
        DriveCommands.mecanumDriveFieldCentric(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                gamepad,
                imu::getYaw
        ).schedule();
    }
    public void startDrive(Gamepad gamepad) {
        DriveCommands.mecanumDrive(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                gamepad
        ).schedule();
    }

    public void resetHeading() {
        imu.resetYaw();
    }
}