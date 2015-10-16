import java.util.*;

public class Crossover 
{
	static int vertAdj[][];
	static boolean visited[];
	static int numVert;
	static PathList path = new PathList();
	
	public static void main(String args[])
	{
		String files[] = {"Thomassen.txt", "NonHam24.txt", "Mid7.txt", "Mid9.txt" };
		
		for(int i=0; i<files.length; i++)
			SolvePath(files[i]);		
	}
	
	public static void SolvePath(String filename)
	{
		final int TIMES = 10;
		PathList finalPath = new PathList();
		int loop=0;
		boolean done = false;
		Random rand = new Random();
		GraphInput input = new GraphInput(filename);
		
		//read in graph
		GraphInput.GraphResult res = input.readInput();
		if(res == null)
			return;
		numVert = res.numVert;
		vertAdj = res.matrix;
		visited = res.visited;
		
		//start of code to find path
		while(loop<TIMES)
		{
			loop++;
			path.clear();
			for(int i=0; i<numVert; i++)
				visited[i] = false;
			
			//random starting point
			int start = rand.nextInt(numVert); //point-1 (dealing with indices here)
			path.add(start);
			visited[start] = true;
			
			//find uv path
			finduv(start, true); //extend
			finduv(start, false); //add from front

			//crossover algorithm
			do
			{
				int u = path.get(0), v = path.get(path.size()-1);
				done = false;
				boolean found = false;
				
				for(int w=0; w<numVert && !found; w++)
				{
					if(w==u || vertAdj[u][w]==0 || !path.contains(w))
						continue;
					
					int x = path.get(path.indexOf(w)-1);
					for(int z=0; z<numVert && !found; z++)
					{
						if(z==x || vertAdj[x][z]==0)
							continue;
						
						if(!visited[z])
						{
							//found, extend path
							found = true;
							PathList newPath = new PathList();
							visited[z] = true;
							newPath.add(z);
							for(int i=path.indexOf(x); i>=0; i--)
								newPath.add(path.get(i));
							for(int i=path.indexOf(w); i<path.size(); i++)
								newPath.add(path.get(i));
							path = newPath;
						}
					}
				}
				if(found)
					continue;
				
				for(int w=0; w<numVert && !found; w++)
				{
					if(w==v || vertAdj[v][w]==0 || !path.contains(w))
						continue;
					
					int y = path.get(path.indexOf(w)+1);
					for(int z=0; z<numVert && !found; z++)
					{
						if(z==y || vertAdj[y][z]==0)
							continue;
						
						if(!visited[z])
						{
							//found, extend path
							found = true;
							PathList newPath = new PathList();
							visited[z] = true;
							newPath.add(z);
							for(int i=path.indexOf(y); i<path.size(); i++)
								newPath.add(path.get(i));
							for(int i=path.indexOf(w); i>=0; i--)
								newPath.add(path.get(i));
							path = newPath;
						}
					}
				}
				if(found)
					continue;

				for(int crossCheck=1; crossCheck<path.size() && !found; crossCheck++)
				{
					int y = path.get(crossCheck);
					if(vertAdj[u][y]!=1)
						continue;
					
					int x = path.get(crossCheck-1);
					if(vertAdj[v][x]!=1)
						continue;
					
					//otherwise crossover has been found
					//check for vertex not on path
					for(int i=0; i<path.size() && !found; i++)
					{
						int p = path.get(i);
						for(int z=0; z<numVert && !found; z++)
						{
							if(z==p || vertAdj[p][z]==0)
								continue;
							
							if(!visited[z])
							{
								//found vertex not on path, extend path
								found = true;
								PathList newPath = new PathList();
								visited[z] = true;
								newPath.add(z);
								if(path.indexOf(p)<=path.indexOf(x))
								{
									//z is on the left half
									for(int j=path.indexOf(p); j>=0; j--) //go from z->p->u
										newPath.add(path.get(j));
									for(int j=path.indexOf(y); j<path.size(); j++) //go from u->y->v
										newPath.add(path.get(j));
									
									if(p!=x)
										for(int j=path.indexOf(x); j>path.indexOf(p); j--) //if p!=x, go from v->x->(p+1)
											newPath.add(path.get(j));
									path = newPath;
								}
								else if(path.indexOf(p)>=path.indexOf(y))
								{
									//z is on the right half
									for(int j=path.indexOf(p); j<path.size(); j++) //go from z->p->v
										newPath.add(path.get(j));
									for(int j=path.indexOf(x); j>=0; j--) //go from v->x->u
										newPath.add(path.get(j));
									
									if(p!=y)
										for(int j=path.indexOf(y); j<path.indexOf(p); j++) //if p!=y, go from u->y->(p-1)
											newPath.add(path.get(j));
									path = newPath;
								}
								else
									System.out.println("HOW DID WE GET HERE??");
							}
						}
					}
				}
				if(found)
					continue;
				
				//makes it here if no extension is found
				done = true;
			} while(!done);
			
			//finds longest path of the 10 iterations
			if(path.size()>finalPath.size())
				finalPath = path;
		}
		
		PrintPath(finalPath);
	}
	
	public static int finduv(int vertex, boolean findu)
	{
		int temp;
		int newTemp = vertex;
		do
		{
			temp = newTemp;
			newTemp = findBlock(temp);
			if(newTemp != temp)
			{
				if(findu)
					path.add(newTemp); //extend
				else
					path.addFront(newTemp); //add at beginning
				visited[newTemp] = true;
			}
		} while(newTemp!= temp);
		return newTemp;
	}
	
	public static int findBlock(int vertex)
	{
		for(int i=0; i<numVert; i++)
		{
			int num = (i+vertex)%numVert; //will start at point after vertex
			if(num!=vertex) //do not care about connections to itself
			{
				if(vertAdj[vertex][num] ==1 && !visited[num])
				{
					vertex = num;
					break;
				}
			}
		}
		return vertex;
	}
	
	public static void PrintPath(PathList print)
	{
		int size = print.size();
		System.out.println("Length of Path: "+size);
		for(int i=0; i<size; i++)
		{
			System.out.print(print.get(i)+1);
			if(i!=size-1)
				System.out.print(" -> ");
		}
		System.out.println("\n");
	}
}
