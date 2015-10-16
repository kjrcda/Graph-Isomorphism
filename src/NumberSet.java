//Set implementation of PathList. Acts as a set.
public class NumberSet 
{
	PathList set = new PathList();
	
	public int size()
	{
		return set.size();
	}
	
	public boolean contains(int vertex)
	{
		return set.contains(vertex);
	}
	
	public void add(int number)
	{
		if(!set.contains(number))
			set.add(number);
	}
	
	public void print()
	{
		set.print();
	}
	
	public void printVertices()
	{
		set.printVertices();
	}
	
	public void clear()
	{
		set.clear();
	}
	
	public int[] toArray()
	{
		return set.toArray();
	}
}
