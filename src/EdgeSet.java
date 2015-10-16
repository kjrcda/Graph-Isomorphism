//Set implementation of 2 PathLists. Acts as a set for edges.
public class EdgeSet
{
	private PathList u = new PathList();
	private PathList v = new PathList();
	private int num = 0;
	
	public int size()
	{
		return num;
	}
	
	public boolean contains(int u, int v)
	{
		for(int i=0; i<num; i++)
		{
			if((this.u.get(i)==u && this.v.get(i)==v) || (this.u.get(i)==v && this.v.get(i)==u))
				return true;
		}
		return false;
	}
	
	public void add(int u, int v)
	{
		if(!this.contains(u,v))
		{
			this.u.add(u);
			this.v.add(v);
			num++;
		}
	}
	
	public void print()
	{
		for(int i=0; i<num; i++)
		{
			System.out.print((u.get(i)+1) +"-"+ (v.get(i)+1));
			if(i!=(num-1))
				System.out.print(", ");
			else
				System.out.println();
		}
	}
	
	public void clear()
	{
		u.clear();
		v.clear();
		num=0;
	}
}
