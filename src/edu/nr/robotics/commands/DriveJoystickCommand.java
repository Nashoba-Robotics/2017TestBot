package edu.nr.robotics.commands;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.subsystems.Drive;

public class DriveJoystickCommand extends NRCommand {
	
	public DriveJoystickCommand() {
		requires(Drive.getInstance());
	}

	@Override
	protected void onStart() {
		
	}

	@Override
	protected void onExecute() {
		//System.out.println(Drive.getInstance().talonLB.getSpeed());
		double[]motorSpeedValues = OI.getInstance().getMotorSpeedValues();
		switch (Robot.getInstance().modeChooser.getSelected()) {
		case manualInput:
		case tankDrive:
			Drive.getInstance().setMotorSpeed(motorSpeedValues[0], motorSpeedValues[1]);
			break;
		case arcadeDrive:
			Drive.getInstance().arcadeDrive(OI.getInstance().getMotorSpeedValues()[0], OI.getInstance().getMotorSpeedValues()[1], true);
		}
		
	}

}
