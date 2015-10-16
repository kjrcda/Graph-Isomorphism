
public class GraphGenerator {
	
	public static void main(String[] args)
	{
		int num = 20;
		boolean same = true;
		if(args.length!=0)
		{
			try{
				num = Integer.parseInt(args[0]);
			}
			catch(Exception e){
				;//do nothing
			}
			try{
				if(args[1].compareToIgnoreCase("y")!=0)
					same = false;
			}
			catch(Exception e){
				;//do nothing
			}
		}
		generate(num,same);
	}

	public static void generate(int numNodes, boolean same)
	{
		String name1 = "first", name2 = "second";//, input;
		int length1=numNodes;//, length2=0;
		boolean iso=same;//, done=false;
		
		/** Allows for customizable graphs -- not needed though
		//get graph information
		java.util.Scanner in = new java.util.Scanner(System.in);
		System.out.print("Enter the name of graph 1: ");
		name1 = in.nextLine();
		System.out.print("Enter the name of graph 2: ");
		name2 = in.nextLine();
		
		while(!done)
		{
			System.out.print("Will these graphs be isomorphic? (Y/N): ");
			input = in.nextLine();
			if(input.compareToIgnoreCase("y")!=0 && input.compareToIgnoreCase("yes")!=0 &&
					input.compareToIgnoreCase("n")!=0 && input.compareToIgnoreCase("no")!=0)
				System.out.println("Pick yes or no");
			else if(input.compareToIgnoreCase("y")==0 || input.compareToIgnoreCase("yes")==0)
				iso = done = true;
			else if(input.compareToIgnoreCase("n")==0 || input.compareToIgnoreCase("no")==0)
			{
				iso = false;
				done = true;
				System.out.println("NOTE: there is a slight chance the graphs will be isomorphic still");
			}		
		}
		
		done = false;
		while(!done)
		{
			int nodes;
			System.out.print("How many nodes in the graph? ");
			input = in.next();
			try {
				nodes = Integer.parseInt(input);
				if(nodes<=0)
					System.out.println("Enter positive number (0+)");
				else
				{
					length1 = nodes;
					done = true;
				}
			} catch (Exception e)
				System.out.println("That was not a number");	
		}
		
		if(iso)
			length2 = length1;
		else
		{
			done = false;
			while(!done)
			{
				int nodes;
				System.out.print("How many nodes in graph 2? ");
				input = in.next();
				try {
					nodes = Integer.parseInt(input);
					if(nodes<=0)
						System.out.println("Enter positive number (0+)");
					else
					{
						length2 = nodes;
						done = true;
					}
				} catch (Exception e)
					System.out.println("That was not a number");				
			}
		}
		in.close();
		*/
		
		//generate random graphs
		try {
			System.out.println("Generating graphs...");
			
			java.util.Random rand = new java.util.Random();
			int[][] graph = new int[length1][length1];
			
			for(int i=0; i<length1; i++)
			{
				for(int j=i+1; j<length1; j++)
				{
					if(rand.nextBoolean())
					{
						graph[i][j] = 1;
						graph[j][i] = 1;
					}
					else
					{
						if(rand.nextBoolean())
						{
							graph[i][j] = 1;
							graph[j][i] = 1;
						}
					}
				}
			}
			
			//graph 1
			java.io.PrintWriter g1out = new java.io.PrintWriter((name1+".txt"), "UTF-8");
			g1out.println(name1);
			g1out.println(length1);
			for(int i=0; i<length1; i++)
			{
				g1out.print((-(i+1)));
				for(int j=i+1; j<length1; j++)
					if(graph[i][j]==1)
						g1out.print(" "+(j+1));
				if(i!=length1-1)
					g1out.println();
			}
			g1out.close();
			
			//graph 2
			java.io.PrintWriter g2out = new java.io.PrintWriter((name2+".txt"), "UTF-8");
			g2out.println(name2);
			g2out.println(length1);
			if(!iso)
			{
				for(int i=0; i<length1; i++)
				{
					g2out.print((-(i+1)));
					for(int j=i+1; j<length1; j++)
						if(rand.nextBoolean())
							g2out.print(" "+(j+1));
					if(i!=length1-1)
						g2out.println();
				}
			}
			else
			{
				int[] mapping = CreateMapping(length1);
				Isomorph.PrintMapping(mapping);
				for(int i=0; i<length1; i++)
				{
					g2out.print((-(mapping[i]+1)));
					for(int j=i+1; j<length1; j++)
						if(graph[i][j]==1)
							g2out.print(" "+(mapping[j]+1));
					if(i!=length1-1)
						g2out.println();
				}
			}
			g2out.close();
			System.out.println("Finished Generating Graphs\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static int[] CreateMapping(int length)
	{
		java.util.Random randomMapper = new java.util.Random();
		int[] map = new int[length];
		boolean [] picked = new boolean[length];
		
		for(int i=0; i<length; i++)
		{
			while(true)
			{
				int pick = (int) (randomMapper.nextDouble()*length);
				if(!picked[pick])
				{
					map[i] = pick;
					picked[pick] = true;
					break;
				}
			}
		}
		return map;
	}

}
