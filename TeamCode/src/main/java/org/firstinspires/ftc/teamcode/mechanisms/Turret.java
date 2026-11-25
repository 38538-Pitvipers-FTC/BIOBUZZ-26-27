package org.firstinspires.ftc.teamcode.mechanisms;

import org.firstinspires.ftc.teamcode.data.Config;

import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Turret implements Mechanism {
    private double targetAngle;
    private boolean AUTO_AIM = false;

    private final NextServo leftTurretServo = new NextServo(
            Config.leftTurretServoModule, Config.leftTurretServoPort, 0.001
    );

    private final NextServo rightTurretServo = new NextServo(
            Config.rightTurretServoModule, Config.rightTurretServoPort, 0.001
    );

    private final double GEAR_RATIO = 0.67; // this is a temporary value
    private final double SERVO_RANGE = 300; // according to google for Dual Mode Servo need to check acutal servo

    public Turret(){
        AUTO_AIM = true;
        leftTurretServo.setPwmRange(550, 2450);
        rightTurretServo.setPwmRange(550, 2450);
    }

    // TODO will complete later am tired
}
