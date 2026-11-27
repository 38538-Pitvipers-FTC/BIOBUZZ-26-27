package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.data.Calc;
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

    private final double GEAR_RATIO = 0.533333; // this is a temporary value
    private final double SERVO_RANGE = 300; // according to google for Dual Mode Servo need to check acutal servo

    private double currentAngle = 0.0;
    private double currentServoPos = 0.0;

    public Turret(){
        AUTO_AIM = true;
        leftTurretServo.setPwmRange(550, 2450);
        rightTurretServo.setPwmRange(550, 2450);
    }

    public double getAngle(){
        return currentAngle;
    }

    public double getRightTurretServoPos(){
        return rightTurretServo.getPosition();
    }

    public double getLeftTurretServoPos(){
        return leftTurretServo.getPosition();
    }

    public void setAutoAim(boolean mode){
        AUTO_AIM = mode;
    }
    public boolean getAutoAim(){return AUTO_AIM;}

    public void setPositions(double pos){
        // TODO create a clamp method and a Math file
        pos = Math.min(1, Math.max(pos, 0));

        leftTurretServo.setPosition(pos);
        rightTurretServo.setPosition(pos);
    }

    public Command incrementPos(double increment){
        return instant(() -> {
            leftTurretServo.setPosition(getLeftTurretServoPos() + increment);
            rightTurretServo.setPosition(getRightTurretServoPos() + increment);
        });
    }

    public void track(Pose goal, Pose robot){
        // TODO am too lazy to do the math right now
        targetAngle = Calc.turretNormalizeAngle(
                Calc.turretMath(goal, robot)
        );
    }

    public double angleToServo(double angle){
        return angle * (GEAR_RATIO / SERVO_RANGE);
    }

    @Override
    public void periodic(){
        currentServoPos = angleToServo(targetAngle);
        setPositions(currentServoPos);
    }

    // TODO will complete later am tired
}
