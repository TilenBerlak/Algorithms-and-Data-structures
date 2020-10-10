package aps2.hashmap;

import java.util.LinkedList;

/**
 * Hash map employing chaining on collisions.
 */
public class HashMapChaining 
{
	private LinkedList<Element> table[];
	private HashFunction.HashingMethod h;
	
	public HashMapChaining(int m, HashFunction.HashingMethod h) 
	{
		this.h = h;
		this.table = new LinkedList[m];
		for (int i=0; i<table.length; i++) 
		{
			table[i] = new LinkedList<Element>();
		}
	}
	
	public LinkedList<Element>[] getTable() 
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
		
		
		Element e = new Element(k, v);
		if(!table[index].contains(e))
		{
			table[index].add(e);
			return true;
		}
		else
		{
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
		
		if(!table[index].isEmpty())
		{
			for(int i = 0; i < table[index].size(); i++)
			{
				if(k == table[index].get(i).key)
				{
					table[index].remove(i);
					return true;
				}
					
			}
			
			return false;
			
		}
		else
		{
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
		
		if(!table[index].isEmpty())
		{
			for(int i = 0; i < table[index].size(); i++)
			{
				if(k == table[index].get(i).key)
					return true;
				
			}
						
		}
		
		return false;		
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
		
		if(!table[index].isEmpty())
		{
			for(int i = 0; i < table[index].size(); i++)
			{
				if(k == table[index].get(i).key)
					return table[index].get(i).value;
								
			}
		}
		
		return null;
	}
	

	
}

