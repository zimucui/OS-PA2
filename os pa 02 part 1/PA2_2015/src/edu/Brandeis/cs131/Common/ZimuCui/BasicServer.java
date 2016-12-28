package edu.Brandeis.cs131.Common.ZimuCui;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Server;

public class BasicServer extends Server {
	
	private LinkedList<Client> allClients = new LinkedList<Client>();

	public BasicServer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized boolean connectInner(Client client) {
		/** Note: There are only three cases where TRUE will be returned:
		 * First: Two shared clients with different industry names;
		 * Second: One shared client
		 * Third: One basic client
		 */
		boolean testInput = (client instanceof BasicClient); //we want to see whether the input client is BasicClient or not
		boolean testFirstClient = false;
		
		if (!allClients.isEmpty()) {       //see whether the first element is BasicClient or not
			Client firstClient = allClients.getFirst();
			testFirstClient = (firstClient instanceof BasicClient); 
		}
		
		if (allClients.isEmpty()) {
			allClients.add(client);
			return true;
		} else if (!testFirstClient && !testInput && allClients.size()==1 && !client.getIndustry().equals(allClients.getFirst().getIndustry())) {       
			//It means when the input client is not basic client;
			//the Linked-list has already had one client in it;
			//this input client's industry is different from previous client's industry
			allClients.add(client);
			return true;
		}
		//all other cases must return false
		return false;
	}

	@Override
	public synchronized void disconnectInner(Client client) {
		allClients.remove(client);
	}
}
