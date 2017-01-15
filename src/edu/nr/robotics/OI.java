package edu.nr.robotics;

import edu.nr.robotics.subsystems.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public double[] motorSpeedValue = new double[2];

	public final static double JOYSTICK_DEAD_ZONE = 0.15;

	private static OI singleton;

	public Joystick stickLeft, stickRight;

	private OI() {

		SmartDashboard.putNumber("ka", Drive.getInstance().ka);
		SmartDashboard.putNumber("kp", Drive.getInstance().kp);
		SmartDashboard.putNumber("kd", Drive.getInstance().kd);
		SmartDashboard.putNumber("kv", Drive.getInstance().kv);
		SmartDashboard.putNumber("kp theta", Drive.getInstance().kp_theta);
		SmartDashboard.putNumber("Distance to travel", 180);//inches
		
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
		
		motorSpeedValue[0] = snapDriveJoysticks(stickLeft.getY());
		motorSpeedValue[1] = snapDriveJoysticks(stickRight.getX());
		
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
