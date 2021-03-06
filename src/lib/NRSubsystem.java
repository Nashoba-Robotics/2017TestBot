package lib;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class NRSubsystem extends Subsystem {

	public static ArrayList<NRSubsystem> subsystems = new ArrayList<>();

	public abstract void disable();
	
	public NRSubsystem() {
		NRSubsystem.subsystems.add(this);
	}

}
