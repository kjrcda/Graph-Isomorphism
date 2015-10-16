
public class CutVertices
{
	static final int NULL = -1;
	static int vertAdj[][];
	static boolean visited[];
	static int numVert;
	static int DFNum[];
	static int DFCount;
	static int DFParent[];
	static int lowPt[];
	static NumberSet vert = new NumberSet();
	static EdgeSet comp = new EdgeSet();

	public static void main(String[] args) 
	{
		String files[] = {"Cutpt1.txt", "Cutpt2.txt", "Cutpt3.txt"};
		
		for(int i=0; i<3; i++)
		{
			SolveCut(files[i]);
			System.out.println();
		}

	}
	
	public static void SolveCut(String filename)
	{
		GraphInput input = new GraphInput(filename);
		GraphInput.GraphResult res = input.readInput();
		if(res == null)
			return;
		
		numVert = res.numVert;
		vertAdj = res.matrix;
		visited = res.visited;
		DFNum = new int[numVert];
		DFParent = new int[numVert];
		lowPt = new int[numVert];
		
		vert.clear();
		DFCount = 0;
		for(int i=0; i<numVert; i++)
		{
			DFNum[i] = NULL;
			DFParent[i] = NULL;
		}
		
		for(int i=0; i<numVert; i++)
		{
			if(!visited[i])
				DFS(i);
		}
		System.out.print("Cut Vertices: ");
		vert.printVertices();
		printBlocks();
	}
	
	public static void DFS(int u)
	{
		int child = 0;
		visited[u] = true;
		DFCount++;
		DFNum[u] = DFCount;
		lowPt[u] = DFCount;
		
		for(int v=0; v<numVert; v++)
		{
			if(vertAdj[u][v] == 0)
				continue;
			
			if(!visited[v])
			{
				child++;
				DFParent[v] = u;
				DFS(v);
				lowPt[u] = Min(lowPt[u], lowPt[v]);
				
				if(DFParent[u] == NULL && child >1)
					vert.add(u);
				if(DFParent[u] != NULL && lowPt[v] >= DFNum[u])
					vert.add(u);
			}
			else if(v != DFParent[u])
				lowPt[u] = Min(lowPt[u], DFNum[v]);
		}
	}
	
	public static int Min(int num1, int num2)
	{
		if(num1<=num2)
			return num1;
		return num2;
	}
	
	public static void printBlocks()
	{
		//mark cut vertices as visited
		for(int i=0; i<numVert; i++)
		{
			if(vert.contains(i))
				visited[i] = true;
			else
				visited[i] = false;
		}
		
		//find connected components
		int block = 0;
		for(int i=0; i<numVert; i++)
		{
			if(!visited[i])
			{
				comp.clear();
				visited[i] = true;
				System.out.print("Block "+ (++block)+": ");
				nextComponent(i);
				comp.print();
			}
		}
	}
	
	private static void nextComponent(int i)
	{
		for(int k=0; k<numVert; k++)
		{
			if(vertAdj[i][k]==1)
			{
				comp.add(i, k);
				if(!visited[k])
				{
					visited[k] = true;
					nextComponent(k);
				}
			}
		}
	}
}
