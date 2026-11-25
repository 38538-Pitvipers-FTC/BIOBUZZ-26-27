package org.firstinspires.ftc.teamcode.mechanisms;

import org.firstinspires.ftc.teamcode.data.Config;

import dev.nextftc.hardware.sensors.NextDigitalSensor;
import dev.nextftc.robot.Mechanism;

/**
 * Mechanism is implemented to have the periodic method
 */
public class TransferSensors implements Mechanism {
    NextDigitalSensor intakeBeamBreak = new NextDigitalSensor(
            Config.intakeBeamBreakModule,
            Config.intakeBeamBreakPort
    );

    NextDigitalSensor launcherBeamBreak = new NextDigitalSensor(
            Config.launcherBeamBreakModule,
            Config.launcherBeamBreakPort
    );

    // state variables
    private boolean lastIntakeBeamBreakState = false;
    private boolean lastLauncherBeamBreakState = false;
    private int numElements = 0;

    public void init(){
        numElements = 0;
        lastIntakeBeamBreakState = false;
        lastLauncherBeamBreakState = false;
    }


    @Override
    public void periodic(){
        boolean currentIntakeBeamBreakState = intakeBeamBreak.isTriggered();
        boolean currentLauncherBeamBreakState = launcherBeamBreak.isTriggered();

        if(currentIntakeBeamBreakState && !lastIntakeBeamBreakState){
            numElements++;
            // this is just a basic assumption for now
            // add intake reversing removing later
        }

        if(!currentLauncherBeamBreakState && lastLauncherBeamBreakState){
            numElements--;
        }

        lastIntakeBeamBreakState = currentIntakeBeamBreakState;
        lastLauncherBeamBreakState = currentLauncherBeamBreakState;
    }
}
