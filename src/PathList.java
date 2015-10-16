//Class representing a list or vertices or in some
//cases a path list. Acts as an ArrayList.
public class PathList
{
	private int[] path = new int[0];
	private int length = 0;
	
	public PathList() {}
	
	public PathList(int[] list)
	{
		path = list;
		length = list.length;
	}
	
	public int size()
	{
		return length;
	}
	
	public int get(int index)
	{
		if(length==0 || index>=length)
			return -1;
		return path[index];
	}
	
	public void add(int vertex)
	{
		int[] newPath = new int[length+1];
		for(int i=0; i<length; i++)
			newPath[i] = path[i];
		newPath[length] = vertex;
		path = newPath;
		length++;
	}
	
	public void addFront(int vertex)
	{
		int[] newPath = new int[length+1];
		newPath[0] = vertex;
		for(int i=0; i<length; i++)
			newPath[i+1] = path[i];
		path = newPath;
		length++;
	}
	
	public int indexOf(int vertex)
	{
		if(length!=0)
		{
			for(int i=0; i<path.length; i++)
			{
				if(path[i]==vertex)
					return i;
			}
		}
		return -1;
	}
	
	public boolean contains(int vertex)
	{
		if(length!=0)
		{
			for(int i=0; i<path.length; i++)
			{
				if(path[i]==vertex)
					return true;;
			}
		}
		return false;
	}
	
	public void clear()
	{
		path = new int[0];
		length=0;
	}
	
	public void printPath()
	{
		printToConsole(" -> ");
	}
	
	public void print()
	{
		printToConsole(" ");
	}
	
	public void printVertices()
	{
		printToConsole(", ");
	}
	
	public void printBits()
	{
		for(int i=0; i<length; i++)
			System.out.print(path[i]);
		System.out.println();
	}
	
	public void printToConsole(String between)
	{
		for(int i=0; i<length; i++)
		{
			System.out.print(path[i]+1);
			if(i!=length-1)
				System.out.print(between);
		}
		System.out.println();
	}
	
	public int[] toArray()
	{
		return path;
	}
}