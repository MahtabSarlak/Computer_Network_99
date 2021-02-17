public class Entity0 extends Entity
{
	private int[] cost = new int[] { 0, 1, 3, 7 };
	private int[] directNeighbour = new int[] { 1, 2, 3};

	// initialization
	public Entity0()
	{
		super();
		for (int i = 0; i < cost.length; i++) {
			distanceTable[i][i]=cost[i];
		}
		int[] minimumCost = computeMinCost();
		for (int value : directNeighbour) {
			sendToLayer2(0, value, minimumCost);
		}
		printDT();
		System.out.println("Table 0 initialized");
	}

	// Handle updates
	public void update(Packet p)
	{        
		boolean isChanged = updateTable(p);
		if(isChanged){
			int[] minCost = computeMinCost();
			for (int value : directNeighbour) {
				sendToLayer2(0, value, minCost);
			}
			printDT();
			System.out.println("Table 0 updated\n");
		}
	}

	public void printDT()
	{
		System.out.println();
		System.out.println("           via");
		System.out.println(" D0 |   1   2   3");
		System.out.println("----+------------");
		for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
		{
			System.out.print("   " + i + "|");
			for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
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
