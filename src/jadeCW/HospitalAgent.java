package jadeCW;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.*;

public class HospitalAgent extends Agent {

	private int numAppointment;
	public HashMap<Integer, AID> allocation = new HashMap<Integer, AID>(); 

	protected void setup() {
		System.out.println("This is hospital agent called " + getLocalName());
		Object[] arg = getArguments();

		try {
			this.numAppointment = Integer.parseInt(arg[0].toString());
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setName("hospital");
			sd.setType("allocate-appointments");
			// sd.addOntologies("allocate-appointments-ontology");
			// sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
			// sd.addProperties(new Property("country", "Uinted Kingdom"));
			dfd.addServices(sd);
			DFService.register(this, dfd);
			addBehaviour(new AllocateAppointment());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input for numeber of appointment : " + arg[0]);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		} 

		// Make this agent terminate
		//doDelete();
	}
	
	public int getNumAppointment(){
		return this.numAppointment;
	}
}
