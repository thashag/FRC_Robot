package org.usfirst.frc.team2557.subsystems;

import org.usfirst.frc.team2557.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WinchSolenoid extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void lock(){
    	if(RobotMap.hallValue == true && RobotMap.winchEncoderCount > 512){
    		RobotMap.winchSolenoid.set(Value.kForward);
    	}
    }
    public void launch(){
    	RobotMap.winchSolenoid.set(Value.kReverse);
    }
    public void lightLaunch(){
    	if(RobotMap.lightValue == true){
    		RobotMap.winchSolenoid.set(Value.kReverse);
    	}
    }
}
