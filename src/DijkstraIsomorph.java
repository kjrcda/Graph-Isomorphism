
public class DijkstraIsomorph {

	private static Graph g1;
	private static Graph g2;
	
	public static void main(String[] args) 
	{
		long endTime, startTime;
		
		g1 = new Graph("TestA.txt");
		g2 = new Graph("TestB.txt");
		
		startTime= System.currentTimeMillis(); 
		boolean iso = IsomorphicTest();
		endTime = System.currentTimeMillis();
		if(iso)
		{
			System.out.println(g1.getName()+" IS isomorphic to "+g2.getName());
			//PrintMapping(mapping);
		}
		else
			System.out.println(g1.getName()+" IS NOT isomorphic to "+g2.getName());
		System.out.println("Completion Time: "+ (endTime - startTime)/1000.0 +"s");
	}
	
	public static boolean IsomorphicTest()
	{
		//O(2)
		System.out.println("Counting nodes...");
		if(g1.nodeCount() != g2.nodeCount())
			return false;
		System.out.println("Finished counting\n");
		
		//O(n^2)
		System.out.println("Counting degrees...");
		if(!MatchingDegrees(g1, g2))
			return false;
		System.out.println("Finished counting\n");
		
		
		
		return true;
	}
	
	public static boolean MatchingDegrees(Graph one, Graph two) //O(n^2)
	{
		int[] degList1 = one.countDegrees(); //O(n^2) + O(n)
		int[] degList2 = two.countDegrees(); //O(n)
	
		if(degList1.length != degList2.length) //O(2)
			return false;
		
		for(int i=0; i<degList1.length; i++) //O(n)
		{
			if(degList1[i] != degList2[i])
				return false;
		}
		return true;
	}
	
	public static void process()
	{
		//g1.computeSignMatrix();
		//g2.computeSignMatrix();		
		
	}
}
