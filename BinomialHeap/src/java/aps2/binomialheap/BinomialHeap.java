package aps2.binomialheap;

import java.util.Vector;

/**
 * This class is an implementation of the Binomial min-heap.
 */
public class BinomialHeap {
	Vector<BinomialNode> data; 	   // list of root nodes
	int n = 0;                     // number of elements
	
	BinomialHeap(){
		data = new Vector<BinomialNode>();
	}
	
	/**
	 * Inserts a new key to the binomial heap and consolidates the heap.
	 * Duplicates are allowed.
	 * 
	 * @param key Key to be inserted
	 * @return True, if the insertion was successful; False otherwise.
	 */
	public boolean insert(int key) 
	{
		data.add(new BinomialNode(key));
		n++;
			
		consolidate();
		
		return true;
	}
	
	/**
	 * Returns the minimum element in the binomial heap. If the heap is empty,
	 * return the maximum integer value.
	 * 
	 * @return The minimum element in the heap or the maximum integer value, if the heap is empty.
	 */
	public int getMin() {
		
		if(data.isEmpty())
			return Integer.MAX_VALUE;
		
		int min = data.get(0).getKey();
		for(BinomialNode e : data)
		{
			if(e.getKey() < min)
				min = e.getKey();
		}
		
		
		
		return min;
	}
	
	/**
	 * Find and removes the minimum element in the binomial heap and
	 * consolidates the heap.
	 * 
	 * @return True, if the element was deleted; False otherwise.
	 */
	public boolean delMin() {	
		
		int min = getMin();
		
		if(min != Integer.MAX_VALUE)
		{
			for(int i = 0; i < data.size(); i++)
			{
				BinomialNode e = data.get(i);
				if(e.getKey() == min)
				{
					// Find min element check
					
					// Save children					
					Vector<BinomialNode> children = e.getChildren();
					
					// delete min element
					data.remove(i);
					n--;
					
					// add children to the heap and consolidate
					for(int j = 0; j < children.size(); j++)
					{
						data.add(children.get(j));

						consolidate();
					}
					
					return true;
				}
			}
		}

		return false;
			
	}
	
	/**
	 * Merges two binomial trees.
	 * 
	 * @param t1 The first tree
	 * @param t2 The second tree
	 * @return Returns the new parent tree
	 */
	public static BinomialNode mergeTrees(BinomialNode t1, BinomialNode t2) {
			
		BinomialNode newNode = new BinomialNode(t1.getKey());
		if(t1.compare(t2) == 1)
		{
			newNode = t1;
			newNode.addChild(t2);
		}
		else if(t1.compare(t2) == -1)
		{
			newNode = t2;
			newNode.addChild(t1);
		}
		
		return newNode;
	}
	
	/**
	 * This function consolidates the binomial heap ie. merges the binomial
	 * trees with the same degree into a single one.
	 * 
	 * @return True, if changes were made to the list of root nodes; False otherwise.
	 */
	private boolean consolidate() {
		
		/*
		 * Preveri, ali so v binomski kopici koreni, ki imajo enako število otrok.
		 * V primeru, da imajo, vzame duplikata in jih združi z metodo mergeTrees(t1, t2)
		 * v eno drevo.
		 */
		
		for(int i = 0; i < data.size(); i++)
		{
			BinomialNode t1 = data.get(i);
			int degree1 = t1.getDegree();

			for(int j = i + 1; j < data.size(); j++)
			{				
				BinomialNode t2 = data.get(j);		
				int degree2 = data.get(j).getDegree();
				
				if(degree1 == degree2)
				{
					data.remove(j);
					data.set(i, mergeTrees(t1, t2));
					
					/*
					 * Zamenjaj elemente v binomskem drevesu, tako da dobimo min-heap (najmanjši element v korenu) ter
					 * največji elementi v listih drevesa.
					 */
					
					// Razvrsti novo nastalo binomsko drevo
					
					consolidate();
					
					return true;
				}
			}
			
		}
		
		return false;
		
	}
	
}

