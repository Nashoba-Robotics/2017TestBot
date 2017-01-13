package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import lib.NRSubsystem;
import lib.Periodic;
import lib.SmartDashboardSource;

public class Shooter extends NRSubsystem implements SmartDashboardSource, Periodic {

	//TODO: MAKE TRUE!!!!!!!!!!!!!!!!!
	public static boolean shooterEnabled = false;
	
	private static Shooter singleton;
	
	public CANTalon shooterTalon;
	
	double shooterMotorSetPoint = 0;
	
	public double turn_F_SHOOTER = 1.0;
	public double turn_P_SHOOTER = 1.0;
	public double turn_I_SHOOTER = 1.0;
	public double turn_D_SHOOTER = 1.0;
	
	private static final int ticksPerRev = 1024;
	
	private Shooter() {
		if (shooterEnabled) {
			shooterTalon = new CANTalon(RobotMap.shooterTalon);
			shooterTalon.enableBrakeMode(false);
			shooterTalon.changeControlMode(TalonControlMode.Speed);
			shooterTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			shooterTalon.setF(turn_F_SHOOTER);
			shooterTalon.setP(turn_P_SHOOTER);
			shooterTalon.setI(turn_I_SHOOTER);
			shooterTalon.setD(turn_D_SHOOTER);
			shooterTalon.configEncoderCodesPerRev(ticksPerRev);
			
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
	
	@Override
	public void periodic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void smartDashboardInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
