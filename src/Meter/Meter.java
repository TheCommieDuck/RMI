package Meter;

import Company.RemoteCompany;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * Created by Mark on 04/01/2015.
 */
public class Meter extends UnicastRemoteObject implements RemoteMeter
{
    public static Random Random = new Random();

    public String getName()
    {
        return name;
    }

    private String name;
    private String registeredCompany;

    public Meter(String name) throws RemoteException, MalformedURLException
    {
        this.name = name;
        Naming.rebind(name, this);
        System.out.println(name+ " registered.");
    }

    public void run() throws RemoteException
    {
        while(true)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e) { return; }
            if(registeredCompany == null)
                initialRegisterWithCompany();
            else
            {
                int choice = Random.nextInt(10);
                if(choice > 8)
                    queryBroker();
            }
        }
    }

    public void registerWithCompany(String company)
    {
        //if we use a while loop, it'll keep trying to reconnect in the case of an error.
        while(registeredCompany != null)
        {
            unregisterWithCompany();
        }

        try
        {
            RemoteCompany c = (RemoteCompany)Naming.lookup("rmi://localhost/"+company);
            c.registerMeter(name);
        }
        catch (NotBoundException e)
        {
            System.out.println(name+": Could not find company with name "+company);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }

    public void unregisterWithCompany()
    {
        if(registeredCompany != null)
        {
            try
            {
                RemoteCompany c = (RemoteCompany)Naming.lookup("rmi://localhost/"+registeredCompany);
                c.unregisterMeter(name);
                this.registeredCompany = null;
            }
            catch (NotBoundException e)
            {
                e.printStackTrace();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void initialRegisterWithCompany()
    {
            queryBroker();
            acceptBrokerDeal();
    }

    public void queryBroker()
    {

    }

    public void acceptBrokerDeal()
    {

    }

    public String getReadings() throws RemoteException
    {
        return "Readings: Lots of energy used";
    }
}
