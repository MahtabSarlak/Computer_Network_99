public class Entity3 extends Entity
{
    private int[] cost = new int[] { 7, 999, 2, 0 };
    private int[] directNeighbour = new int[] { 0,2};

    // initialization
    public Entity3()
    {
		super();
        for (int i = 0; i < cost.length; i++) {
            distanceTable[i][i]=cost[i];
        }
        int[] minCost = computeMinCost();
        for (int value : directNeighbour) {
            sendToLayer2(3, value, minCost);
        }
        printDT();
		System.out.println("Table 3 initialized");
    }
    
    // Handle updates
    public void update(Packet p)
    {
        boolean isChanged = updateTable(p);
        if(isChanged){
            int[] minCost = computeMinCost();
            for (int value : directNeighbour) {
                sendToLayer2(3, value, minCost);
            }
            printDT();
            System.out.println("Table 3 updated\n");
        }
    }

    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
               
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
