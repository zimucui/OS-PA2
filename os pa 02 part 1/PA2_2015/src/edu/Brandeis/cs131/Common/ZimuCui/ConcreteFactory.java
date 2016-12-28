package edu.Brandeis.cs131.Common.ZimuCui;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Factory;
import edu.Brandeis.cs131.Common.Abstract.Industry;
import edu.Brandeis.cs131.Common.Abstract.Server;
import edu.Brandeis.cs131.Common.Abstract.Log.Log;
import java.util.Collection;


public class ConcreteFactory implements Factory {

    @Override
    public Server createNewBasicServer(String label){
        BasicServer basicS = new BasicServer(label);
        return basicS;
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Client createNewSharedClient(String label, Industry industry){
        SharedClient shared = new SharedClient(label, industry);
        return shared;
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Client createNewBasicClient(String label, Industry industry){
    	 BasicClient basicC = new BasicClient(label, industry);
         return basicC;
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Server createNewMasterServer(String label, Collection<Server> servers, Log log){
    	 MasterServer masterS = new MasterServer(label, servers, log);
         return masterS;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
