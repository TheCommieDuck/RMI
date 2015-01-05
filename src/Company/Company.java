package Company;

import Meter.Meter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Mark on 04/01/2015.
 */
public class Company extends UnicastRemoteObject implements RemoteCompany
{
    public String getName() throws RemoteException
    {
        return name;
    }

    private String name;
    private HashSet<String> meters;

    public Company(String name) throws RemoteException, MalformedURLException
    {
        this.name = name;
        Naming.rebind("Company" + name.replace(" ", "_"), this);
        System.out.println(name + " registered.");
        this.meters = new HashSet<>();
    }

    public void run()
    {

    }

    public void registerMeter(String meter) throws RemoteException
    {
        meters.add(meter);
        System.out.println(name+" registered meter "+meter);
    }

    public void unregisterMeter(String meter) throws RemoteException
    {
        meters.remove(meter);
        System.out.println(name+" unregistered meter "+meter);
    }
}
