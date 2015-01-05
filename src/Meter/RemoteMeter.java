package Meter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Mark on 04/01/2015.
 */
public interface RemoteMeter extends Remote
{
    //public void registerWithCompany(String company) throws RemoteException;
    //public void unregisterWithCompany() throws RemoteException;
    public String getReadings() throws RemoteException;
    //public String queryBroker() throws RemoteException;
    //public void acceptBrokerDeal() throws RemoteException;
}
