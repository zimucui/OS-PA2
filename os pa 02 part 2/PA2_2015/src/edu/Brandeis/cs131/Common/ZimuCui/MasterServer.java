package edu.Brandeis.cs131.Common.ZimuCui;

import java.util.HashMap;
import java.util.LinkedList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Log.Log;
import edu.Brandeis.cs131.Common.Abstract.Server;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class MasterServer extends Server {

    private final Map<Integer, List<Client>> mapQueues = new HashMap<Integer, List<Client>>();
    private final Map<Integer, Server> mapServers = new HashMap<Integer, Server>();
    
    final Lock lock = new ReentrantLock();
    
    final Condition hahaha  = lock.newCondition();      //use this Condition variable to wait and notify
   
    
    public MasterServer(String name, Collection<Server> servers, Log log) {
        super(name, log);
        Iterator<Server> iter = servers.iterator();
        while (iter.hasNext()) {
            this.addServer(iter.next());
        }
    }

    public void addServer(Server server) {
        int location = mapQueues.size();
        this.mapServers.put(location, server);
        this.mapQueues.put(location, new LinkedList<Client>());
    }

    @Override
    public boolean connectInner(Client client) {
       int getkey = getKey(client);      //give the number corresponding to this client 
       List<Client> thisClientList = mapQueues.get(getkey);         //point to the corresponding queue
       BasicServer thisServer = (BasicServer) mapServers.get(getkey);  //point to the corresponding basic server
       
       lock.lock();        //now, lock. Same as synchronized block
       try {
    	   if (thisServer.connect(client)) {   //when this server can connect the client
    		   if (!thisClientList.isEmpty() && thisClientList.get(0).equals(client)) {
    			   thisClientList.remove(0);     //when the list is not empty and the first one is this client
    		   }
    		   return true;
    	   } else {
    		   if (thisClientList.contains(client)) {      //if the list is not empty and contains the client
    			   try {
					hahaha.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
    		   } else {           //if the list is not empty and doesn't contain the client
    			   
    			   thisClientList.add(client);
    			   
    			   try {
					hahaha.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		   }
    	   }
       } finally {
    	   lock.unlock();     //now unlock
       }
       
        return false;     //all other cases return FALSE
    }

    @Override
    public void disconnectInner(Client client) {
    	int getkey = getKey(client);
        BasicServer thisServer = (BasicServer) mapServers.get(getkey);
        
        lock.lock();    //now lock
        try {
        	
        	thisServer.disconnect(client);   //disconnect itself from the BasicServer; releases the lock
        	hahaha.signalAll();    //now, wake up all the waiting threads     
        	
        } finally {
        	lock.unlock();    //now unlock
        }
        
    }

	//returns a number from 0- mapServers.size -1
    // MUST be used when calling get() on mapServers or mapQueues
    private int getKey(Client client) {
        return client.getSpeed() % mapServers.size();
    }
}
