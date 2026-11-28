package org.firstinspires.ftc.teamcode.mechanisms;

import static dev.nextftc.units.Units.Degrees;
import static dev.nextftc.units.Units.RotationsPerMinute;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.data.BangBang;
import org.firstinspires.ftc.teamcode.data.Calc;
import org.firstinspires.ftc.teamcode.data.Config;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.telemetry.Telemetry;
public class Launcher implements Mechanism {

    public enum LauncherState {
        IDLE, SPINUP, READY
    }

    private final NextMotor rightLauncher = new NextMotor(
            Config.rightLauncherMotorModule, Config.rightLauncherMotorPort, Degrees.of(360.0 / 28.0)
    );
    private final NextMotor leftLauncher = new NextMotor(
            Config.leftLauncherMotorModule, Config.leftLauncherMotorPort, Degrees.of(360.0 / 28.0)
    );
    private final NextServo launcherGate = new NextServo(
            Config.launcherGateServoModule, Config.launcherGateServoPort, 0.0
    );

    private final double READY_TOLERANCE = 25.0;
    private final double EXIT_TOLERANCE = 75.0;

    private final double IDLE_SPEED = 4200;
    private final double CLOSE_SPEED = 5000.0;
    private final double FAR_SPEED = 7000.0;

    private final double OPEN_POS = 0.31;
    private final double CLOSE_POS = 0.05;
    private final double BANG_THRESHOLD = 1000;
    private final double BANG_POWER = 1.0;
    private final double BANG_HYSTERESIS = 10.0;
    private boolean bangBangEnabled = true;
    private final BangBang bangBang = new BangBang(BANG_POWER, BANG_HYSTERESIS);

    private double kV = 0.00017;
    private double kS = 0.113;
    private double kP = 0.0057;

    private double targetVelocity = 0.0;
    private double offset = 0.0;
    private double error = 0.0;

    private boolean powerMode = false;
    private boolean bangActive = false;
    public LauncherState launcherState = LauncherState.IDLE;

    public Launcher() {
        setIdle();

        leftLauncher.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.FLOAT);
        rightLauncher.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.FLOAT);

        leftLauncher.follow(rightLauncher, NextMotor.Direction.REVERSE);

        rightLauncher.getVelocityConstants().setKV(kV);
        rightLauncher.getVelocityConstants().setKS(kS);
        rightLauncher.getVelocityConstants().setKP(kP);
    }

    // setters

    public void setKV(double kV) {
        this.kV = kV;
        rightLauncher.getVelocityConstants().setKV(kV);
    }

    public void setKS(double kS) {
        this.kS = kS;
        rightLauncher.getVelocityConstants().setKS(kS);
    }

    public void setKP(double kP) {
        this.kP = kP;
        rightLauncher.getVelocityConstants().setKP(kP);
    }

    public void setTargetVelocity(double RPM) {
        setPowerMode(false);
        if (RPM == targetVelocity)
            return;
        targetVelocity = RPM;
        rightLauncher.setVelocitySetpoint(RotationsPerMinute.of(RPM + offset));
        if (RPM == IDLE_SPEED) {
            setIdle();
            return;
        }
        setSpinUp();
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void setPowerMode(boolean powerMode) {
        this.powerMode = powerMode;
    }

    public void setBangActive(boolean bangActive) {
        this.bangActive = bangActive;
    }

    public void setBangBangEnabled(boolean bangBangEnabled) {
        this.bangBangEnabled = bangBangEnabled;
    }

    public void setLauncherState(LauncherState launcherState) {
        this.launcherState = launcherState;
    }

    public void setIdle() {
        this.launcherState = LauncherState.IDLE;
    }

    public void setSpinUp() {
        this.launcherState = LauncherState.SPINUP;
    }

    public void setReady() {
        this.launcherState = LauncherState.READY;
    }


    // getters

    public double getKV() {
        return kV;
    }

    public double getKS() {
        return kS;
    }

    public double getKP() {
        return kP;
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public double getOffset() {
        return offset;
    }

    public double getError() {
        return error;
    }

    public double getCurrentRPM(){
        return rightLauncher.getEncoderVelocity().into(RotationsPerMinute);
    }

    // getters for bools
    public boolean isPowerMode() {
        return powerMode;
    }

    public boolean isBangActive() {
        return bangActive;
    }

    public boolean isBangBangEnabled() {
        return bangBangEnabled;
    }

    public LauncherState getLauncherState() {
        return launcherState;
    }

    public boolean isIdle() {
        return launcherState == LauncherState.IDLE;
    }

    public boolean isSpinUp() {
        return launcherState == LauncherState.SPINUP;
    }

    public boolean isReady() {
        return launcherState == LauncherState.READY;
    }

    public double getServoPos(){
        return launcherGate.getPosition();
    }

    // commands
    public Command setGatePosition(double pos){
        return instant(() -> launcherGate.setPosition(pos));
    }

    public Command openGate(){
        return instant(() -> launcherGate.setPosition(OPEN_POS));
    }

    public Command closeGate(){
        return instant(() -> launcherGate.setPosition(CLOSE_POS));
    }

    public Command increment(double val){
        return instant(() -> launcherGate.setPosition(
                Math.min(1.0, Math.max(getServoPos() + val, 0))
        ));
    }

    public Command setGateZero(){
        return instant(() -> launcherGate.setPosition(0));
    }

    // some PID functions
    public void increaseKS() {
        setKS(kS + 0.01);
    }

    public void decreaseKS() {
        setKS(kS - 0.01);
    }

    public void increaseKP(){
        setKP(kP + 0.01);
    }

    public void decreaseKP(){
        setKP(kP - 0.01);
    }

    private void increaseOffset() {
        this.offset = offset + 25;
    }

    private void decreaseOffset() {
        this.offset = offset - 25;
    }

    public void adjustOffset(boolean increase) {
        if (increase) {
            increaseOffset();
        } else {
            decreaseOffset();
        }
    }

    public void incrementRPM(double val){
        setTargetVelocity(getTargetVelocity() + val);
    }

    public void decrementRPM(double val){
        setTargetVelocity(getTargetVelocity() - val);
    }

    // TODO create power mode functions

    private void updateState(){
        if(isIdle()){
            return;
        }

        error = Math.abs(getCurrentRPM() - (targetVelocity + offset));

        if(isReady()){
            if(error > EXIT_TOLERANCE){
                setSpinUp();
            } else {
                if (error <= READY_TOLERANCE){
                    setReady();
                }
            }
        }
    }

    private void applyControl() {
        double goal = targetVelocity + offset;
        double rpm = getCurrentRPM();

        if(bangBangEnabled && goal - rpm > BANG_THRESHOLD) {
            rightLauncher.setThrottle(bangBang.calculate(goal, rpm));
            bangActive = true;
        } else if (bangActive) {
            rightLauncher.setVelocitySetpoint(RotationsPerMinute.of(goal));
            bangActive = false;
        }
    }


    @Override
    public void periodic(){
        updateState();

        if(isIdle() && !powerMode){
            setTargetVelocity(IDLE_SPEED);
        }

        if(!powerMode){
            applyControl();
        }

        rightLauncher.update();
    }
}