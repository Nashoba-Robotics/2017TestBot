package org.usfirst.frc.team1768.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public final static double JOYSTICK_DEAD_ZONE = 0.15;

	private static OI singleton;

	public Joystick stickLeft;

	private OI() {

		stickLeft = new Joystick(RobotMap.joystickLeftPort);
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
	public double getMotorSpeedValue() {
		return stickLeft.getY();
	}
}
