package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.data.Config;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Intake implements Mechanism {
    // hardware components
    private final NextMotor intakeMotor = new NextMotor(
            Config.intakeMotorModule,
            Config.intakeMotorPort
    );
    private final NextServo rampServo = new NextServo(
            Config.intakeRampServoModule,
            Config.intakeRampServoPort
    );

    public enum IntakeState {
        FORWARD, REVERSE, OFF, CALIBRATION // calib to be implemented later
    }

    // constants
    private final double FORWARD_POWER = 1.0;
    private final double REVERSE_POWER = -1.0;
    private final double OFF_POWER = 0.0;

    private final double RAMP_UP_POS = 0.0;
    private final double RAMP_DOWN_POS = 1.0;

    // variables used throughout code
    private IntakeState intakeState = IntakeState.OFF;
    private double currentPower;
    private double rampPosition;

    public Intake(){
        intakeMotor.setDirection(NextMotor.Direction.FORWARD);
    }


    public void setState(IntakeState state){
        intakeState = state;
    }

    public void forward(){
        setState(IntakeState.FORWARD);
    }

    public void reverse(){
        setState(IntakeState.REVERSE);
    }

    public void off(){
        setState(IntakeState.OFF);
    }

    public void rampUp(){
        rampPosition = RAMP_UP_POS;
    }

    public void rampDown(){
        rampPosition = RAMP_DOWN_POS;
    }

    // Command versions of the aforementioned functions
    public Command setForward(){
        return instant(this::forward);
    }

    public Command setReverse(){
        return instant(this::reverse);
    }

    public Command setOff(){
        return instant(this::off);
    }

    public Command toggleRamp(){
        if(rampPosition == RAMP_UP_POS){
            return instant(this::rampDown);
        }else{
            return instant(this::rampUp);
        }
    }

    // getters
    public IntakeState getIntakeState(){
        return this.intakeState;
    }

    @Override
    public void periodic(){
        switch(intakeState) {
            case FORWARD:
                currentPower = FORWARD_POWER;
                break;
            case REVERSE:
                currentPower = REVERSE_POWER;
                break;
            case OFF:
                currentPower = OFF_POWER;
                break;
        }

        intakeMotor.setThrottle(currentPower);
        rampServo.setPosition(rampPosition);
    }
}
