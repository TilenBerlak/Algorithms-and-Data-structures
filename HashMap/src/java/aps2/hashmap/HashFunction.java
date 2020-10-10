package aps2.hashmap;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class HashFunction 
{
	public static enum HashingMethod 
	{
		DivisionMethod,
		KnuthMethod
	};
	
	/**
	 * Hash function using division method.
	 * If negative key is given, first multiply it by -1.
	 * 
	 * @param k Key
	 * @param m Table size
	 * @return Index in the table of size m.
	 */
	public static int DivisionMethod(int k, int m) 
	{
		if(k < 0)
			k *= -1;
		
		return k % m;
	}
	
	/**
	 * Hash function using multiplication method implemented by Knuth:
	 * h(k) = m (k A mod 1)
	 * 
	 * Where A is the inverse golden ratio (\sqrt(5)-1)/2.
	 * Use double precision number type.
	 * If negative key is given, first multiply it by -1.
	 * 
	 * @param k Key
	 * @param m Table size
	 * @return Index in the table of size m.
	 */
	public static int KnuthMethod(int k, int m) 
	{
		if(k < 0)
			k *= -1;
		
		double A = (Math.sqrt(5) - 1) / 2;
		
		int index = (int) Math.floor( m * (k * A % 1) );
		
		return index;
	}
	
	
	public static int hashCode(int k, int m, HashFunction.HashingMethod h)
	{
		int index;		
		if(h == HashFunction.HashingMethod.DivisionMethod)
		{
			index = HashFunction.DivisionMethod(k, m);
		}
		else
		{
			index = HashFunction.KnuthMethod(k, m);
		}
		
		return index;
	}
	
	public static int secondHashFunction(int k, int m)
	{
		int prime;
		if(m > 7)
		{
			prime = 7;
		}
		else if(m > 5)
		{
			prime = 5;		
		}
		else if(m > 3)
		{
			prime = 3;
		}
		else
		{
			prime = 1;
		}
				
		return prime - (k % prime);
	}
	
	
	
}