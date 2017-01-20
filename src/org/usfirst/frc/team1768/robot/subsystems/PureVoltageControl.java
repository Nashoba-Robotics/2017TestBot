package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.commands.SetVoltageCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.NRSubsystem;
import lib.Periodic;
import lib.SmartDashboardSource;

public class PureVoltageControl extends NRSubsystem implements SmartDashboardSource, Periodic{
	
	public static boolean voltageEnabled = true;
	
	private static PureVoltageControl singleton;
	
	public CANTalon talonLF, talonLB, talonRF, talonRB;
	
	private double motorSetPoint = 0;
	
	public SendableChooser<motorSelected> motorChooser;
	
	public enum motorSelected {
		motorLF, motorRF, motorLB, motorRB
	}
	
	private PureVoltageControl() {
		if (voltageEnabled) {
			talonLF = new CANTalon(RobotMap.shooterTalon);
			talonLF.enableBrakeMode(false);
			talonLF.changeControlMode(TalonControlMode.PercentVbus);
			
			talonLB = new CANTalon(RobotMap.shooterTalon);
			talonLB.enableBrakeMode(false);
			talonLB.changeControlMode(TalonControlMode.PercentVbus);
			
			talonRF = new CANTalon(RobotMap.shooterTalon);
			talonRF.enableBrakeMode(false);
			talonRF.changeControlMode(TalonControlMode.PercentVbus);
			
			talonRB = new CANTalon(RobotMap.shooterTalon);
			talonRB.enableBrakeMode(false);
			talonRB.changeControlMode(TalonControlMode.PercentVbus);
		
			SmartDashboard.putNumber("Voltage Percentage", 0);
		
			motorChooser = new SendableChooser<motorSelected>();
			motorChooser.addDefault("Left Front Motor", motorSelected.motorLF);
			motorChooser.addObject("Left Back Motor", motorSelected.motorLB);
			motorChooser.addObject("Right Front Motor", motorSelected.motorRF);
			motorChooser.addObject("Right Back Motor", motorSelected.motorRB);
			SmartDashboard.putData("Choose motor to control", motorChooser);
		}
	}

	public static PureVoltageControl getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new PureVoltageControl();
		}
	}

	public void setMotorSpeed(double voltage, CANTalon talon) {
		motorSetPoint = voltage;
		talon.set(voltage);
	}

	@Override
	public void periodic() {
		
	}

	@Override
	public void smartDashboardInfo() {
		
	}

	@Override
	public void disable() {
		PureVoltageControl.getInstance().setMotorSpeed(0, talonLF);
		PureVoltageControl.getInstance().setMotorSpeed(0, talonRF);
		PureVoltageControl.getInstance().setMotorSpeed(0, talonLB);
		PureVoltageControl.getInstance().setMotorSpeed(0, talonRB);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new SetVoltageCommand());
		
	}
}
