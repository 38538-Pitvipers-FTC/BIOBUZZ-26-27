package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.Chassis;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class ViperRobot implements NextRobot {
    Chassis chassis = new Chassis();

    @Override
    public Set<Mechanism> getMechanisms(){
        return Set.of(chassis);
    }
}
