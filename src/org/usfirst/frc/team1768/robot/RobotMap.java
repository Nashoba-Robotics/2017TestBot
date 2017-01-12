package org.usfirst.frc.team1768.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// TODO: Get actual numbers
	public static final int joystickLeftPort = 0;
	public static final int joystickRightPort = 1;

	// Talon ports
	public static final int talonLF = 0;
	public static final int talonRF = 2;
	public static final int talonLB = 1;
	public static final int talonRB = 3;
	
	public static final double WHEEL_DIAMETER = 3.5;
	public static final double MAX_RPM = 1300;
}
