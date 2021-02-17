public class Entity2 extends Entity
{
    private int[] cost = new int[] { 3, 1, 0, 2 };
    private int[] directNeighbour = new int[] {0,1,3};

    // initialization
    public Entity2()
    {

	    super();
        for (int i = 0; i < cost.length; i++) {
            distanceTable[i][i]=cost[i];
        }
        int[] minCost = computeMinCost();
        for (int value : directNeighbour) {
            sendToLayer2(2, value, minCost);
        }
        printDT();
		System.out.println("Table 2 initialized");
    }
    
    // Handle updates
    public void update(Packet p)
    {
        boolean isChanged = updateTable(p);
        if(isChanged){
            int[] minCost = computeMinCost();
            for (int value : directNeighbour) {
                sendToLayer2(2, value, minCost);
            }
            printDT();
            System.out.println("Table 2 updated\n");
        }
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   3");
        System.out.println("----+------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 2)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (j == 2)
                {
                    continue;
                }
                
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
