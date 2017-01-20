package org.usfirst.frc.team1768.robot.commands;

import org.usfirst.frc.team1768.robot.OI;
import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.subsystems.PureVoltageControl;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.NRCommand;

public class SetVoltageCommand extends NRCommand{

	public SetVoltageCommand() {
		requires(PureVoltageControl.getInstance());
	}

	protected void onExecute() {
		switch (Robot.getInstance().controlChooser.getSelected()) {
		case shooter:
			break;
		case voltage:
			switch(PureVoltageControl.getInstance().motorChooser.getSelected()) {
			case motorLF:
				//PureVoltageControl.getInstance().setMotorSpeed(SmartDashboard.getNumber("Voltage Percentage", 0), PureVoltageControl.getInstance().talonLF);
				PureVoltageControl.getInstance().setMotorSpeed(OI.getInstance().getMotorSpeedValue(), PureVoltageControl.getInstance().talonLF);
				break;
			case motorLB:
				//PureVoltageControl.getInstance().setMotorSpeed(SmartDashboard.getNumber("Voltage Percentage", 0), PureVoltageControl.getInstance().talonLB);
				PureVoltageControl.getInstance().setMotorSpeed(OI.getInstance().getMotorSpeedValue(), PureVoltageControl.getInstance().talonLB);
				break;
			case motorRF:
				//PureVoltageControl.getInstance().setMotorSpeed(SmartDashboard.getNumber("Voltage Percentage", 0), PureVoltageControl.getInstance().talonRF);
				PureVoltageControl.getInstance().setMotorSpeed(OI.getInstance().getMotorSpeedValue(), PureVoltageControl.getInstance().talonRF);
				break;
			case motorRB:
				//PureVoltageControl.getInstance().setMotorSpeed(SmartDashboard.getNumber("Voltage Percentage", 0), PureVoltageControl.getInstance().talonRB);
				PureVoltageControl.getInstance().setMotorSpeed(OI.getInstance().getMotorSpeedValue(), PureVoltageControl.getInstance().talonRB);
				break;
			}
			break;
		}
	}
}
