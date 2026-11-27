package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.data.Calc;
import org.firstinspires.ftc.teamcode.mechanisms.Chassis;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.mechanisms.TransferSensors;
import org.firstinspires.ftc.teamcode.mechanisms.Turret;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;

import java.util.Set;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class ViperRobot implements NextRobot {
    private Follower follower;
    public Chassis chassis = new Chassis();
    public Intake intake = new Intake();
    public TransferSensors transferSensors = new TransferSensors();
    public Launcher launcher = new Launcher();

    public Turret turret = new Turret();

    @Override
    public Set<Mechanism> getMechanisms(){
        return Set.of(chassis, turret);
    }


    public Chassis getChassis() {
        return chassis;
    }

    public Intake getIntake() {
        return intake;
    }

    public Turret getTurret() {
        return turret;
    }

    public TransferSensors getTransferSensors() {
        return transferSensors;
    }

    public Launcher getLauncher() {
        return launcher;
    }


    public Follower getFollower(){
        if(follower == null){
            follower = Constants.create(RobotController.hardwareMap());
        }

        return follower;
    }
    @Override
    public void periodic(){
        if (turret.getAutoAim()){
            getTurret().track(Calc.calcCurrentGoal(
                    getFollower().pose()
            ), getFollower().pose());
        }
    }
}
