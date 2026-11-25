package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.data.Alliance;
import org.firstinspires.ftc.teamcode.data.Config;

import java.util.function.Supplier;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.sensors.NextPinpoint;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.drive.DriveCommands;

public class Chassis implements Mechanism {
    public final NextMotor frontLeft = new NextMotor(Config.frontLeft);
    public final NextMotor frontRight = new NextMotor(Config.frontRight);
    public final NextMotor backLeft = new NextMotor(Config.backLeft);
    public final NextMotor backRight = new NextMotor(Config.backRight);

    public final NextPinpoint pinpoint = new NextPinpoint("pinpoint"); // TODO get switched to pinpoint

    public Chassis() {
        frontLeft.setDirection(NextMotor.Direction.REVERSE);
        backLeft.setDirection(NextMotor.Direction.REVERSE);
    }

    public void initPinpoint(RevHubOrientationOnRobot orientation) {
        pinpoint.resetPosAndIMU();
    }

    public void startDrive(Gamepad gamepad, Supplier<Double> heading) {
        double offset = Alliance.currentAlliance == Alliance.RED ? Math.PI / 2 : Math.PI * 1.5;

        DriveCommands.mecanumDriveFieldCentric(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                gamepad,
                () -> heading.get() + offset
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
}