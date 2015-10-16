
public class Isomorph {

	private static int[] mapping;
	private static Graph g1;
	private static Graph g2;
	private static int checkMapCounter =0,mappings=0;
	
	public static void main(String[] args) {
		boolean iso;
		long endTime, startTime;
		//int num = 20;
		//graph with 25 nodes takes:
		//500 seconds with a bad case, average time is not bad to find all mappings
		//can change it to take first mapping, fast if lower vertices map together
		//0.1 seconds with Ullmans
		//GraphGenerator.generate(num, true); //937 max with Ullman, 25 max with own
		g1 = new Graph("first.txt");
		g2 = new Graph("second.txt");

		//ullmans
		startTime= System.currentTimeMillis(); 
		iso = g1.UllmanIsomorphicTest(g2);
		endTime = System.currentTimeMillis();
		
		if(iso)
			System.out.println(g1.getName()+" IS isomorphic to "+g2.getName());
		else
			System.out.println(g1.getName()+" IS NOT isomorphic to "+g2.getName());
		System.out.println("Number of Nodes: "+g1.nodeCount()+ "\nNumber of Edges: "+g1.edgeCount());
		System.out.println("Completion Time: "+ (endTime - startTime)/1000.0 +"s");
		
		//my implementation
		startTime= System.currentTimeMillis(); 
		MyIsomorphicTest();
		endTime = System.currentTimeMillis();
		
		if(mappings>0)
			System.out.println(g1.getName()+" IS isomorphic to "+g2.getName());
		else
			System.out.println(g1.getName()+" IS NOT isomorphic to "+g2.getName());
		System.out.println("Completion Time: "+ (endTime - startTime)/1000.0 +"s");
		System.out.println("Check Mapping Counter: "+checkMapCounter);
		System.out.println("Total number of mappings: "+mappings+"\n");
		
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
	public static boolean MyIsomorphicTest()
	{
		//O(2)
		System.out.println("\nCounting nodes and edges...");
		if(g1.nodeCount() != g2.nodeCount())
			return false;
		if(g1.edgeCount()!=g2.edgeCount())
			return false;
		System.out.println("Number of Nodes: "+g1.nodeCount()+ "\nNumber of Edges: "+g1.edgeCount());
		System.out.println("Finished counting\n");
		
		//O(n^2)
		System.out.println("Counting degrees...");
		if(!MatchingDegrees(g1, g2))
			return false;
		System.out.println("Finished counting\n");
		
		System.out.println("Finding mappings...");
		//n!
		if(!PerformMapping())
			return false;
		
		return true;
	}
	
	public static boolean MatchingDegrees(Graph one, Graph two) //O(2n^2) + O(3n) 
	{
		int[] degList1 = one.countDegrees(); //O(n^2) + O(n)
		int[] degList2 = two.countDegrees(); //O(n^2) + O(n)
	
		if(degList1.length != degList2.length) //O(2)
			return false;
		
		for(int i=0; i<degList1.length; i++) //O(n)
		{
			if(degList1[i] != degList2[i])
				return false;
		}
		return true;
	}

	public static boolean PerformMapping() //N!
	{
		boolean found = false;
		mapping = new int[g1.nodeCount()];
		
		for(int i=0; i<mapping.length && !found; i++) //O(n)
		{
			if(g1.getDegree(0) == g2.getDegree(i))
			{
				mapping[0] = i;
				g1.visit(i);
				found = FindMapping(1); //T(n-1) + .5n^2
				g1.unvisit(i);
			}
		}
		
		return found;
	}
	
	private static boolean FindMapping(int currNode) //T(n-1) + .5n^2
	{
		boolean found = false;
		int length = g1.nodeCount();
		
		//check to see if we are at the bottom of the recursion
		if(g1.numVisited() == length)
			found = true;
		
		//if all vertices have been visited, check the mapping
		if(found)
		{
			found = false;
			if(CheckMapping()) //O(.5n^2)
			{
				System.out.println(g1.getName()+" vs "+g2.getName());
				PrintMapping(mapping);
				mappings++;
				found = true;
			}
		}
		else
		{
			//otherwise keep going down recursive mapping
			for(int i=0; i<length && !found; i++) //(n-1)
			{
				if(!g1.isVisited(i) && g1.getDegree(currNode) == g2.getDegree(i)) //O(3)
				{
					mapping[currNode] = i;
					g1.visit(i);
					found = FindMapping(currNode+1); //T(n-1) + 2n^2
					g1.unvisit(i);
				}
			}
		}
		
		return found;
	}
	
	//should only ever get here with full mapping
	private static boolean CheckMapping() //down to O(.5n^2)
	{
		checkMapCounter++;
		return g1.compareMapping(g2.getAdjacencyMatrix(), mapping); //O(.5n^2)
	}
	
	public static int[] InvertMapping(int[] map) //O(n)
	{
		int[] reverse = new int[map.length];
		
		for(int i=0; i<reverse.length; i++)
			reverse[map[i]] = i;
		
		return reverse;
	}
	
	public static void PrintMapping(int[] map) //O(n)
	{
		for(int i=0; i<map.length; i++)
			System.out.println((i+1)+" maps to "+(map[i]+1));
		System.out.println();
	}
	
	//USEFUL CALLS	
	public static void PrintDegrees(Graph one, Graph two)
	{
		int degCount1[] = one.countDegrees();
		int degCount2[] = two.countDegrees();
		int nodes = one.nodeCount();
		if(two.nodeCount()>nodes)
			nodes = two.nodeCount();
		
		System.out.println("Degrees of A vs B");
		for(int i=0; i<nodes; i++)
		{
			System.out.print((i+1)+":\t");
			if(i<degCount1.length)
				System.out.print(degCount1[i]);
			else
				System.out.print("0");
			System.out.print("\t");
			if(i<degCount2.length)
				System.out.println(degCount2[i]);
			else
				System.out.println("0");
		}
	}
}
