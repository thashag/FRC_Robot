package org.usfirst.frc.team2557.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.arm.TeleopArmCommand;

public class Arm extends Subsystem {

    private CANTalon leftActuator = RobotMap.leftActuatorMotor;
    private CANTalon rightActuator = RobotMap.rightActuatorMotor;

    private AnalogInput leftPotentiometer = RobotMap.leftPotentiometer;
    private AnalogInput rightPotentiometer = RobotMap.rightPotentiometer;

    private double scaleFactor = 0.5;

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopArmCommand());
    }

    private final double Kp = 2.2;
    public void set(double speed) {
        if(leftActuator.isFwdLimitSwitchClosed()) {
            speed = Math.min(0, speed);
        }
        if(leftActuator.isRevLimitSwitchClosed()) {
            speed = Math.max(0, speed);
        }
//        if(leftPotentiometer.getAverageVoltage() >= 4.39) {
//            speed = Math.max(0, speed);
//        }

        speed *= scaleFactor;

        leftActuator.set(speed);
        // TODO: The potentiometer values might be inversed on the right, if so the right value needs to be inversed
        // TODO: This algorithm "works", but needs to be tweeked (itself and Kp) to work correctly
        // http://www.chiefdelphi.com/forums/showthread.php?t=134738
        // ^^ Uses encoder position instead of potentiometers and a lead screws instead of actuators,
        // but the logic is the same. There are several algorithms, this seemed like the easiest.
        rightActuator.set(-(speed + (leftPotentiometer.getAverageVoltage() - rightPotentiometer.getAverageVoltage()) * Kp));

        SmartDashboard.putNumber("Left Potentiometer", leftPotentiometer.getAverageVoltage());
        SmartDashboard.putNumber("Right Potentiometer", rightPotentiometer.getAverageVoltage());
        SmartDashboard.putNumber("Potentiometer Difference", leftPotentiometer.getAverageVoltage() - rightPotentiometer.getAverageVoltage());
    }

    public double getPotentiometerValue() {
        return this.leftPotentiometer.getValue();
    }

}
