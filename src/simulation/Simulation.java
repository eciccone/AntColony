package simulation;

import simulation.model.Colony;
import simulation.view.AntSimGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Simulation class controls the simulation. The gui actions are controlled here
 * and is responsible for executing the steps of the simulation.
 * 
 * @author eddie ciccone
 *
 */
public class Simulation implements SimulationEventListener, ActionListener {

	private int turns;
	private Colony colony;
	private AntSimGUI antSimGUI;
	private boolean initializedColony;
	private Timer timer;

	public Simulation() {
		turns = 0;
		colony = new Colony();
		antSimGUI = new AntSimGUI();
		initializedColony = false;
		timer = new Timer(2, this);
		antSimGUI.addSimulationEventListener(this);
		antSimGUI.initGUI(colony.getColonyView());
	}

	/**
	 * Updates the colony and increments the step. Each step each ant performs their
	 * appropriate action.
	 */
	public void step() {
		turns++;
		colony.update(turns);
		antSimGUI.setTime("Turns: " + turns + " (" + (turns / 10) + " Days)");
		System.out.println("Update colony --- Turn Count: " + turns);
	}

	/**
	 * Handles events depending on which button is clicked.
	 */
	@Override
	public void simulationEventOccurred(SimulationEvent simEvent) {
		if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
			if (timer.isRunning()) {
				timer.stop();
			}

			if (initializedColony) {
				colony.reset();
				turns = 0;
			}

			colony.normalSetup();
			initializedColony = true;
			antSimGUI.setTime("Turns: " + turns + " (" + (turns / 10) + " Days)");
		} else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
			if (initializedColony) {
				if (timer.isRunning()) {
					timer.stop();
				} else {
					timer.start();
				}
			}
		} else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
			if (initializedColony && !timer.isRunning()) {
				if (colony.getQueenAnt().isAlive()) {
					step();
				} else {
					System.out.println("Queen is dead --- Cannot step.");
				}
			}
		}
	}

	/**
	 * Executes the step method on each iteration of the timer class.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (colony.getQueenAnt().isAlive()) {
			step();
		} else {
			timer.stop();
			System.out.println("Queen is dead --- Cannot run.");
		}
	}

}
