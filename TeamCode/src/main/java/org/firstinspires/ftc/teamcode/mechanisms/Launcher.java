package org.firstinspires.ftc.teamcode.mechanisms;

import org.firstinspires.ftc.teamcode.data.Config;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Launcher implements Mechanism {
    public enum LauncherState {
        IDLE, SPINNINGUP, READY
    }

    private final NextMotor rightLauncher = new NextMotor(
            Config.rightLauncherMotorModule, Config.rightLauncherMotorPort
    );

    private final NextMotor leftLauncher = new NextMotor(
            Config.leftLauncherMotorModule, Config.leftLauncherMotorPort
    );

    private final NextServo launcherGate = new NextServo(
            Config.launcherGateServoModule, Config.launcherGateServoPort
    );
    // TODO this mechanism is going to be a pain to implement
}
