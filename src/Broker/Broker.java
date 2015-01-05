package Broker;

import Company.RemoteCompany;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by Mark on 04/01/2015.
 */
public class Broker extends UnicastRemoteObject implements RemoteBroker
{
    private HashSet<String> companies;

    public Broker() throws RemoteException, MalformedURLException
    {
        companies = new HashSet<>();
        Naming.rebind("Broker", this);
        System.out.println("Broker registered.");
        List<String> companyList = Arrays.asList(Naming.list("rmi://localhost"));
        for(String comp : companyList)
        {
            //removeIf doesn't seem to work with abstractlist..
            if(!comp.startsWith("//localhost:1099/Company"))
            {
                System.out.println(comp);
                continue;
            }
            companies.add(comp);
        }
        System.out.println("BROKER: Found " + companies.size() + " companies");
    }

    public void run() throws RemoteException
    {

    }

    public String findOptimalTariff() throws RemoteException
    {
        return null;
    }
}
