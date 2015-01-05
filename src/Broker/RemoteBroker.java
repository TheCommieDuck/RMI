package Broker;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Mark on 04/01/2015.
 */
public interface RemoteBroker extends Remote
{
    public String findOptimalTariff() throws RemoteException;
}
