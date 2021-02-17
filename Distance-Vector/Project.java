import java.io.*;

public class Project
{
    public final static void main(String[] argv)
    {
        NetworkSimulator simulator;
                                   
        System.out.println("Network Simulator v1.0");

        simulator = new NetworkSimulator();
                                                
        simulator.runSimulator();
    }
}
