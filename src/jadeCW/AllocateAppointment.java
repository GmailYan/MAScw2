package jadeCW;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AllocateAppointment extends CyclicBehaviour {

	private int step = 1;
	private HospitalAgent parentAgent;

	@Override
	public void action() {
		parentAgent = (HospitalAgent)this.myAgent;

		ACLMessage msg = this.parentAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		ACLMessage reply;
		
		int allocation = 0;
		for (int i = 1; i <= this.parentAgent.getNumAppointment(); i++){
			if(this.parentAgent.allocation.get(i) == null){
				allocation = i;
				this.parentAgent.allocation.put(i, msg.getSender());
				break;
			}
		}
		
		int perf = allocation != 0 ? ACLMessage.ACCEPT_PROPOSAL : ACLMessage.REJECT_PROPOSAL;
		reply = new ACLMessage(perf);
		reply.addReceiver(msg.getSender());
		reply.setContent(Integer.toString(allocation));
		this.parentAgent.send(reply);
	}
}