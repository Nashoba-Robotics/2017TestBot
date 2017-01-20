package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.commands.ShooterConstantSpeedCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.NRSubsystem;
import lib.Periodic;
import lib.SmartDashboardSource;

public class Shooter extends NRSubsystem implements SmartDashboardSource, Periodic {

	public static boolean shooterEnabled = true;
	
	private static Shooter singleton;
	
	public CANTalon shooterTalon;

	private double shooterMotorSetPoint = 0;

	public double turn_F_SHOOTER = 2.3;
	public double turn_P_SHOOTER = 0;
	public double turn_I_SHOOTER = 0;
	public double turn_D_SHOOTER = 0;

	private static final int ticksPerRev = 256;

	private Shooter() {
		if (shooterEnabled) {
			shooterTalon = new CANTalon(RobotMap.shooterTalon);
			shooterTalon.enableBrakeMode(false);
			shooterTalon.changeControlMode(TalonControlMode.PercentVbus);
			shooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			shooterTalon.setF(turn_F_SHOOTER);
			shooterTalon.setP(turn_P_SHOOTER);
			shooterTalon.setI(turn_I_SHOOTER);
			shooterTalon.setD(turn_D_SHOOTER);
			shooterTalon.configEncoderCodesPerRev(ticksPerRev);
			SmartDashboard.putNumber("Goal Shooter Speed", 0);
		}
	}
	
	public static Shooter getInstance() {
		init();
		return singleton;
	}
	
	public static void init() {
		if (singleton == null) {
			singleton = new Shooter();
		}
	}
	
	public void setMotorSpeed(double speed) {
			shooterMotorSetPoint = speed;
			SmartDashboard.putString("Shooter Speed String",
					-shooterTalon.getSpeed() + "  :  " + shooterMotorSetPoint);
			shooterTalon.set(-shooterMotorSetPoint);
	}
	
	@Override
	public void periodic() {
		
	}

	@Override
	public void smartDashboardInfo() {
		
	}

	@Override
	public void disable() {
		Shooter.getInstance().setMotorSpeed(0);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ShooterConstantSpeedCommand());
	}

}
