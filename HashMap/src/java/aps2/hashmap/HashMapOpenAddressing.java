package aps2.hashmap;

/**
 * Hash map with open addressing.
 */
public class HashMapOpenAddressing 
{
	private Element table[]; // table content, if element is not present, use Integer.MIN_VALUE for Element's key
	private HashFunction.HashingMethod h;
	private CollisionProbeSequence c;
	
	public static enum CollisionProbeSequence 
	{
		LinearProbing,    // new h(k) = (h(k) + i) mod m
		QuadraticProbing, // new h(k) = (h(k) + i^2) mod m
		DoubleHashing     // new h(k) = (h(k) + i*h(k)) mod m
	};
	
	public HashMapOpenAddressing(int m, HashFunction.HashingMethod h, CollisionProbeSequence c) 
	{
		this.table = new Element[m];
		this.h = h;
		this.c = c;
		
		// init empty slot as MIN_VALUE
		for (int i=0; i<m; i++) 
		{
			table[i] = new Element(Integer.MIN_VALUE, "");
		}
	}

	public Element[] getTable() 
	{
		return this.table;
	}
	
	/**
	 * If the element doesn't exist yet, inserts it into the set.
	 * 
	 * @param k Element key
	 * @param v Element value
	 * @return true, if element was added; false otherwise.
	 */
	public boolean add(int k, String v) 
	{
		int index = HashFunction.hashCode(k, table.length, h);
		
		switch(c)
		{
			case LinearProbing:
				
				if(table[index].key == Integer.MIN_VALUE)
				{	
					table[index] = new Element(k, v);
					return true;
				}
				else
				{
					
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == Integer.MIN_VALUE)
						{
							table[x] = new Element(k, v);
							return true;
						}
						else
						{
							x = (index + i++) % table.length;
						}
					}
					
				}
				
				return false;
				
				
			case QuadraticProbing:
				
				if(table[index].key == Integer.MIN_VALUE)
				{	
					table[index] = new Element(k, v);
					return true;
				}
				else
				{
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == Integer.MIN_VALUE)
						{
							table[x] = new Element(k, v);
							return true;
						}
						else
						{
							x = (index + (int) Math.pow(i++, 2)) % table.length;
						}
					}
				}
				return false;
				
				
				
			case DoubleHashing:
				if(table[index].key == Integer.MIN_VALUE)
				{	
					table[index] = new Element(k, v);
					return true;
				}
				else
				{
					int i = 1;
					int x = index + i;
					//int hash2 = HashFunction.secondHashFunction(k, table.length);
					while(x != index)
					{
						if(table[x].key == Integer.MIN_VALUE)
						{
							table[x] = new Element(k, v);
							return true;
						}
						else
						{
							x = (index + i++ * index) % table.length;
						}
					}
				}
				
				return false;
								
			default:
				return false;
		}
		
	}

	/**
	 * Removes the element from the set.
	 * 
	 * @param k Element key
	 * @return true, if the element was removed; otherwise false
	 */
	public boolean remove(int k) 
	{
		int index = HashFunction.hashCode(k, table.length, h);
		
		switch(c)
		{
			case LinearProbing:
				
				if(table[index].key == k)
				{	
					table[index] = new Element(Integer.MIN_VALUE, "");
					return true;
				}
				else
				{
					
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							table[x] = new Element(Integer.MIN_VALUE, "");
							return true;
						}
						else
						{
							x = (index + i++) % table.length;
						}
					}
					
				}
				
				return false;
				
				
			case QuadraticProbing:
				
				if(table[index].key == k)
				{	
					table[index] = new Element(Integer.MIN_VALUE, "");
					return true;
				}
				else
				{
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							table[x] = new Element(Integer.MIN_VALUE, "");
							return true;
						}
						else
						{
							x = (index + (int) Math.pow(i++, 2)) % table.length;
						}
					}
				}
				return false;
				
				
				
			case DoubleHashing:
				if(table[index].key == k)
				{	
					table[index] = new Element(Integer.MIN_VALUE, "");
					return true;
				}
				else
				{
					int i = 1;
					int x = index + i;
					//int hash2 = HashFunction.secondHashFunction(k, table.length);
					while(x != index)
					{
						if(table[x].key == k)
						{
							table[x] = new Element(Integer.MIN_VALUE, "");
							return true;
						}
						else
						{
							x = (index + i++ * index) % table.length;
						}
					}
				}
				
				return false;
								
			default:
				return false;
		}
		
	}

	/**
	 * Finds the element.
	 * 
	 * @param k Element key
	 * @return true, if the element was found; false otherwise.
	 */
	public boolean contains(int k) 
	{
		int index = HashFunction.hashCode(k, table.length, h);
		
		switch(c)
		{
			case LinearProbing:
				
				if(table[index].key == k)
				{	
					return true;
				}
				else
				{
					
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							return true;
						}
						else
						{
							x = (index + i++) % table.length;
						}
					}
					
				}
				
				return false;
				
				
			case QuadraticProbing:
				
				if(table[index].key == k)
				{	
					return true;
				}
				else
				{
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							return true;
						}
						else
						{
							x = (index + (int) Math.pow(i++, 2)) % table.length;
						}
					}
				}
				return false;
				
				
				
			case DoubleHashing:
				if(table[index].key == k)
				{	
					return true;
				}
				else
				{
					int i = 1;
					int x = index + i;
					//int hash2 = HashFunction.secondHashFunction(k, table.length);
					while(x != index)
					{
						if(table[x].key == k)
						{
							return true;
						}
						else
						{
							x = (index + i++ * index) % table.length;
						}
					}
				}
				
				return false;
								
			default:
				return false;
		}
		
	}
	
	/**
	 * Maps the given key to its value, if the key exists in the hash map.
	 * 
	 * @param k Element key
	 * @return The value for the given key or null, if such a key does not exist.
	 */
	public String get(int k) 
	{
		int index = HashFunction.hashCode(k, table.length, h);
		
		switch(c)
		{
			case LinearProbing:
				
				if(table[index].key == k)
				{	
					return table[index].value;
				}
				else
				{
					
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							return table[x].value;
						}
						else
						{
							x = (index + i++) % table.length;
						}
					}
					
				}
				
				return null;
				
				
			case QuadraticProbing:
				
				if(table[index].key == k)
				{	
					return table[index].value;
				}
				else
				{
					int i = 2;
					int x = index + 1;
					while(x != index)
					{
						if(table[x].key == k)
						{
							return table[x].value;
						}
						else
						{
							x = (index + (int) Math.pow(i++, 2)) % table.length;
						}
					}
				}
				return null;
				
				
				
			case DoubleHashing:
				if(table[index].key == k)
				{	
					return table[index].value;
				}
				else
				{
					int i = 1;
					int x = index + i;
					int hash2 = HashFunction.secondHashFunction(k, table.length);
					while(x != index)
					{
						if(table[x].key == k)
						{
							return table[x].value;
						}
						else
						{
							x = (index + i++ * hash2) % table.length;
						}
					}
				}
				
				return null;
								
			default:
				return null;
		}
		
	}
}

