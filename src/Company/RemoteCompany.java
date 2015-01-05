package Company;

import Meter.RemoteMeter;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Mark on 04/01/2015.
 */
public interface RemoteCompany extends Remote
{
    public void registerMeter(String meter) throws RemoteException;
    public void unregisterMeter(String meter) throws RemoteException;
}
