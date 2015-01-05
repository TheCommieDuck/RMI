import Broker.Broker;
import Company.Company;
import Meter.Meter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main
{
    //all run methods are designed to simulate the agents in an exaggerated day-to-day fashion.
    public static void main(String[] args)
    {
        try
        {
            LocateRegistry.createRegistry(1099);
        }
        catch (RemoteException e)
        {
            System.out.println("Unable to start registry." );
            e.printStackTrace();
        }

        List<Thread> meters = createMeters();
        List<Thread> companies = createCompanies();
        Thread broker = createBroker();
        System.out.println("SIMULATION:Setup agents.");
        meters.forEach(t -> t.start());
        companies.forEach(t -> t.start());
        broker.start();
        System.out.println("SIMULATION:Agents are active.");
    }

    private static List<Thread> createMeters()
    {
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < 5; ++i)
        {
            threads.add(new Thread(new Runnable()
            {
                public void run()
                {
                    String meterName = "Meter"+ UUID.randomUUID();
                    try
                    {
                        Meter meter = new Meter(meterName);
                        meter.run();
                    }
                    catch(RemoteException e)
                    {
                        System.out.println(meterName+": RemoteException. " + e.getMessage());
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }));
        }
        return threads;
    }

    private static Thread createBroker()
    {
        return new Thread(new Runnable()
        {
            public void run()
            {

                try
                {
                    //the sleep is to avoid race conditions if possible
                    Thread.sleep(1000);
                    Broker broker = new Broker();
                    broker.run();
                }
                catch(RemoteException e)
                {
                    System.out.println("BROKER: RemoteException. " + e.getMessage());
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private static List<Thread> createCompanies()
    {
        ArrayList<Thread> threads = new ArrayList<Thread>();
        final List<String> names = Arrays.asList("Power R Us", "Energy People", "Fossil Fuels Ltd");
        for(int i = 0; i < 3; ++i)
        {
            final int j = i;
            threads.add(new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        Company company = new Company(names.get(j));
                        company.run();
                    }
                    catch (RemoteException e)
                    {
                        System.out.println("Company"+names.get(j)+": RemoteException. " + e.getMessage());
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }

                }
            }));
        }
        return threads;
    }
}
