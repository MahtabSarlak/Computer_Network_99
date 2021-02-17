public abstract class Entity
{
	protected final int NOT_CONNECTED = 999;
	public Entity(){
		
		for(int to = 0; to < distanceTable.length; to++){
			for(int via = 0; via < distanceTable.length; via++){
				distanceTable[to][via] = NOT_CONNECTED;
			}
		}
	}
    // distance table
    protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES]
                                           [NetworkSimulator.NUMENTITIES];

    
    // The update function
    public abstract void update(Packet p);

    protected abstract void printDT();


	protected int[] computeMinCost(){
		int[] minCost = new int[4];
		for(int node = 0; node < 4; node++)
			minCost[node] = NOT_CONNECTED;

		for(int dest = 0; dest < distanceTable.length; dest++){
			for(int via = 0; via < distanceTable.length; via++)
					if(distanceTable[dest][via] < minCost[dest])
					minCost[dest] = distanceTable[dest][via];
		}
		return minCost;
	}
	
	protected void sendToLayer2(int source , int destination , int[] minCost){
		Packet p = new Packet(source , destination , minCost);
		NetworkSimulator.toLayer2(p);
	}

	protected boolean updateTable(Packet p){
		boolean isChanged = false;
		int src = p.getSource();
		for(int node = 0; node < 4; node++){
			if(distanceTable[node][src] > p.getMincost(node) + distanceTable[src][src]){
				isChanged = true;
				distanceTable[node][src] = p.getMincost(node) + distanceTable[src][src];
			}
		}
		return isChanged;
	}
}
