package jadeCW;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class RequestAppointment extends Behaviour {

	private int step = 1;
	private PatientAgent parentAgent; 

	@Override
	public void action() {
		this.parentAgent = ((PatientAgent) this.myAgent);
		switch (step) {
		case 1:
			if (parentAgent.getHospitalAgent() != null) {
				step++;
			} else {
				block();
			}
			break;
		case 2:
			if (((PatientAgent) this.myAgent).getAppointment() != 0) {
				step +=2;				
			} else {
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.addReceiver(this.parentAgent.getHospitalAgent().getName());
				msg.setLanguage("English");
				msg.setContent("Appointment Request");
				this.myAgent.send(msg);				
				step++;
			}
			break;
		case 3:
			ACLMessage receivedMsg = this.parentAgent.receive();
			if(receivedMsg != null){
				String allocation = receivedMsg.getContent();
				if(receivedMsg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL){
					this.parentAgent.setAppointment(Integer.parseInt(allocation));
					System.out.println(this.parentAgent.getLocalName() + " has been allocated into appointment : " + this.parentAgent.getAppointment());
				} else {
					System.out.println(this.parentAgent.getLocalName() + " has been rejected");
				}
				step++;
			} else {
				block();
			}
			break;
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return step == 4;
	}

}
