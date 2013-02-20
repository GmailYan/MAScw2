package jadeCW;

import java.util.HashMap;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class PatientAgent extends Agent {

	// Key = appointment, Value = preference level
	private HashMap<Integer, Integer> preferences = new HashMap<Integer, Integer>();
	private DFAgentDescription hospitalAgent = null;
	private int appointment = 0;

	protected void setup() {
		System.out.println("This is patient agent called " + getLocalName());
		Object[] arg = getArguments();

		try {

			int preference = 1;
			for (Object o : arg) {
				if (o.toString().equals("-")) {
					preference++;
				} else {
					preferences.put(Integer.parseInt(o.toString()), preference);
				}
			}

			// Build the description used as template for the subscription
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("allocate-appointments");
			// templateSd.addProperties(new Property("country",
			// "United Kingdom"));
			dfd.addServices(sd);
			
			DFAgentDescription[] descriptions = DFService.search(this, dfd);
			if(descriptions.length != 0){
				setHospitalAgent(descriptions[0]);
			}

			addBehaviour(new RequestAppointment());
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		// Make this agent terminate
		 //doDelete();
	}

	public DFAgentDescription getHospitalAgent() {
		return hospitalAgent;
	}

	public void setHospitalAgent(DFAgentDescription hospitalAgent) {
		this.hospitalAgent = hospitalAgent;
	}

	public int getAppointment() {
		return appointment;
	}

	public void setAppointment(int appointment) {
		this.appointment = appointment;
	}
}
