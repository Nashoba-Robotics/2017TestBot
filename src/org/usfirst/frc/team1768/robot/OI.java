package org.usfirst.frc.team1768.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public double speedMultiplier = 1.0;
	
	public double[] motorSpeedValue = new double[2];

	public final static double JOYSTICK_DEAD_ZONE = 0.15;

	private static OI singleton;

	public Joystick stickLeft, stickRight;

	private OI() {

		SmartDashboard.putNumber("Speed Multiplier", speedMultiplier);
		SmartDashboard.putNumber("Motor Speed", 0);
		SmartDashboard.putNumber("Goal Shooter Speed", 0);

		stickLeft = new Joystick(RobotMap.joystickLeftPort);
		stickRight = new Joystick(RobotMap.joystickRightPort);
	}

	public static OI getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new OI();
		}
	}

	// Motor joystick
	public double[] getMotorSpeedValues() {
		return motorSpeedValue;
	}
}
