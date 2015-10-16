
public class Pair 
{
	private Integer one;
	private Integer two;

	public Pair(Integer v1, Integer v2) 
	{
		one = v1;
		two = v2;
	}
	
	public int getFirst()
	{
		return one;
	}
	
	public int getSecond()
	{
		return two;
	}

	public boolean equals(Object obj) 
	{
		return (obj != null) && 
				(this.getClass()==obj.getClass()) &&
					one==((Pair)obj).one && two==((Pair)obj).two;
	}

	public int hashCode() 
	{
		int result = 17;
		result = 37 * result + one.hashCode();
		result = 37 * result + two.hashCode();
		return result;
	}

	public String toString() 
	{
		return String.format("(%s,%s)", (one+1), (two+1));
	}
}