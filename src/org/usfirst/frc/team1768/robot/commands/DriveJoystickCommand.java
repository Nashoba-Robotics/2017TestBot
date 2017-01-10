package org.usfirst.frc.team1768.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1768.robot.OI;
import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.subsystems.Drive;

public class DriveJoystickCommand extends Command {

	public DriveJoystickCommand() {
		requires(Drive.getInstance());
	}

	@Override
	protected void initialize() {
		// Nothing needs to be done on start
	}

	@Override
	protected void execute() {
		switch ((Robot.mode) Robot.getInstance().modeChooser.getSelected()) {
		case manualInput:
		case joystick:
		case tankDrive:
			Drive.getInstance().setMotorSpeed(OI.getInstance().getMotorSpeedValues()[0], OI.getInstance().getMotorSpeedValues()[1]);
			break;
		case arcadeDrive:
			Drive.getInstance().arcadeDrive(OI.getInstance().getMotorSpeedValues()[0], OI.getInstance().getMotorSpeedValues()[1], false);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
