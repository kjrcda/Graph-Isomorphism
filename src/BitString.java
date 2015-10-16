
public class BitString 
{	
	private PathList list = new PathList();
	private String string = "";
	private int number = 0;
	private int vertex = -1;
	
	public BitString() {}
	
	public BitString(int row, int[] bits, int start, int end)
	{
		makeString(row, bits, start, end);
	}
	
	public void makeString(int row, int[] bits, int start, int end)
	{
		int[] cutBits = new int[end-start];
		for(int i=0; i<cutBits.length; i++)
			cutBits[i] = bits[start+i];
		makeString(cutBits);
		vertex = row;
	}
	
	public void makeString(int[] bits)
	{
		list = new PathList(bits);
		number = GetNumber(bits);
		for(int i=0; i<list.size(); i++)
			string += bits[i];
	}
	
	public int length()
	{
		return list.size();
	}
	
	public int value()
	{
		return number;
	}
	
	public int getVertex()
	{
		return vertex;
	}
	
	public boolean append(BitString toAdd)
	{
		int[] newBits = toAdd.toArray();
		for(int i=0; i<newBits.length; i++)
			append(newBits[i]);
		return true;
	}
	
	public boolean append(int bit)
	{
		if(bit != 0 && bit !=1)
			return false;
		
		list.add(bit);
		string += bit;
		number <<=1;
		if(bit==1)
			number++;
		
		return true;
	}
	
	private int GetNumber(int[] bits)
	{
		int num=0;
		for(int i=0; i<bits.length; i++)
		{
			num <<=1;
			if(bits[i]==1)
				num++;
		}
		return num;
	}
	
	public int[] toArray()
	{
		return list.toArray();
	}
	
	public void printString()
	{
		System.out.print(number+": ");
		System.out.println(string);
	}
	
	public String getString()
	{
		return string;
	}
	
	public String toString()
	{
		return ""+number+": "+string;
	}
}
