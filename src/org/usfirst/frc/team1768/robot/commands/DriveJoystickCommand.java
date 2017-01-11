package org.usfirst.frc.team1768.robot.commands;

import lib.NRCommand;

import org.usfirst.frc.team1768.robot.OI;
import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.subsystems.Drive;

public class DriveJoystickCommand extends NRCommand {

	public DriveJoystickCommand() {
		requires(Drive.getInstance());
	}

	@Override
	protected void initialize() {
		// Nothing needs to be done on start
	}

	@Override
	protected void onExecute() {
		double[]motorSpeedValues = OI.getInstance().getMotorSpeedValues();
		switch (Robot.getInstance().modeChooser.getSelected()) {
		case manualInput:
			//motorSpeedValues = OI.getInstance().getMotorSpeedValues();
			Drive.getInstance().setMotorSpeed(motorSpeedValues[0], motorSpeedValues[1]);
			break;
		case joystick:
			//motorSpeedValues = OI.getInstance().getMotorSpeedValues();
			Drive.getInstance().setMotorSpeed(motorSpeedValues[0], motorSpeedValues[1]);
			break;
		case tankDrive:
			//motorSpeedValues = OI.getInstance().getMotorSpeedValues();
			Drive.getInstance().setMotorSpeed(motorSpeedValues[0], motorSpeedValues[1]);
			break;
		case arcadeDrive:
			Drive.getInstance().arcadeDrive(OI.getInstance().getMotorSpeedValues()[0], OI.getInstance().getMotorSpeedValues()[1], false);
		}
		
	}

}
