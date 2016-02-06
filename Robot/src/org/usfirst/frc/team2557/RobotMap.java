package org.usfirst.frc.team2557;

import edu.wpi.first.wpilibj.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static CANTalon frontLeft;
	public static CANTalon frontRight;
	public static CANTalon backLeft;
	public static CANTalon backRight;
	public static CANTalon intakeMotor;
	public static CANTalon winchMotor;
	
	public static DoubleSolenoid intakeArm;
	public static DoubleSolenoid winchSolenoid;
	public static DoubleSolenoid driveShift;
	
	public static DigitalInput hallEffect;
	public static DigitalInput lightSensor;
	public static Counter leftDriveEncoder;
	public static Counter rightDriveEncoder;
	public static Counter winchEncoder;
	
	public static RobotDrive robotDrive;
	
	public static int winchEncoderCount;
	public static double rightEncoderRate;
	public static double leftEncoderRate;
	
	public static Timer time;
	public static double timerValue;
	public static double oldTime;
	public static double oldThrottle;
	
	public static boolean hallValue;
	public static boolean lightValue;
	
	public static double jx;
	public static double jy;
	public static double x;
	public static double y;
	public static double c;
	
	public static double frontLeftVoltage;
	public static double frontRightVoltage;
	public static double rearLeftVoltage;
	public static double rearRightVoltage;
	public static double lowSpeed;
	
	public static double briefSave;
	
	
	public static void init(){
		frontRight = new CANTalon(0);
		
		
	}
	
	
	
	
}