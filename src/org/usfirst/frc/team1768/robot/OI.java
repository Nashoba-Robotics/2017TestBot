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
		
		Robot.mode selected = Robot.getInstance().modeChooser.getSelected();
		switch (selected) {
		case manualInput:
			motorSpeedValue[0] = -SmartDashboard.getNumber("Motor Speed", 0);
			motorSpeedValue[1] = motorSpeedValue[0];
			break;
		case tankDrive:
			motorSpeedValue[0] = snapDriveJoysticks(stickLeft.getY());
			motorSpeedValue[1] = snapDriveJoysticks(stickRight.getY());
			break;
		case arcadeDrive:
			motorSpeedValue[0] = snapDriveJoysticks(stickLeft.getY());
			motorSpeedValue[1] = snapDriveJoysticks(stickRight.getX());
			break;
		}
		return motorSpeedValue;
	}

	private static double snapDriveJoysticks(double value) {
		if (Math.abs(value) < JOYSTICK_DEAD_ZONE) {
			value = 0;
		} else if (value > 0) {
			value -= JOYSTICK_DEAD_ZONE;
		} else {
			value += JOYSTICK_DEAD_ZONE;
		}
		value /= 1 - JOYSTICK_DEAD_ZONE;

		return value;
	}
}
