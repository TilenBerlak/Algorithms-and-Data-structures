package aps2.bstmap;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of the (unbalanced) Binary Search Tree set node.
 */
public class BSTMapNode 
{
	private static int counter;
	private BSTMapNode left, right, parent;
	private int key;
	private String value;

	public BSTMapNode(BSTMapNode l, BSTMapNode r, BSTMapNode p,
			int key, String value) 
	{
		super();
		this.left = l;
		this.right = r;
		this.parent = p;
		this.key = key;
		this.value = value;
	}

	public BSTMapNode getLeft() 
	{
		return left;
	}

	public void setLeft(BSTMapNode left) 
	{
		this.left = left;
	}

	public BSTMapNode getRight() 
	{
		return right;
	}

	public void setRight(BSTMapNode right) 
	{
		this.right = right;
	}

	public int getKey() 
	{
		return key;
	}

	public void setKey(int key) 
	{
		this.key = key;
	}
	
	public String getValue() 
	{
		return this.value;
	}
	
	public void setValue(String value) 
	{
		this.value = value;
	}

	public int compare(BSTMapNode node) 
	{
		counter++;
		return node.key-this.key;
	}

	public int getCounter() 
	{
		return counter;
	}

	public void resetCounter() 
	{
		counter = 0;
	}

	/**
	 * If the element doesn't exist yet, adds the given element to the subtree.
	 * 
	 * @param element Given key/value wrapped inside an empty BSTNode instance
	 * @return true, if the element was added; false otherwise.
	 */
	public boolean add(BSTMapNode element) 
	{
		if(element.getKey() < this.key)
		{
			if(this.left == null)
			{
				BSTMapNode e = new BSTMapNode(null, null, this, element.getKey(), element.getValue());
				this.left = e;
				return true;
			}
			else 
			{
				this.left.add(element);
			}
		}
		else if(element.getKey() > this.key)
		{
			if(this.right == null)
			{
				BSTMapNode e = new BSTMapNode(null, null, this, element.getKey(), element.getValue());
				this.right = e;
				return true;
			}
			else
			{
				this.right.add(element);
			}
		}

		return false;
	}
	
	/**
	 * Finds and removes the element with the given key from the subtree.
	 * 
	 * @param element Given key wrapped inside an empty BSTNode instance
	 * @return true, if the element was found and removed; false otherwise.
	 */
	public boolean remove(BSTMapNode element) 
	{
		
		if(element.getKey() == this.key)
		{
			
			
			return true;
			
		}
		else if(element.getKey() < this.key)
		{
			if(this.left == null)
			{
				return false;
			}
			else
			{
				return this.left.contains(element);
			}
		}
		else
		{
			if(this.right == null)
			{
				return false;
			}
			else
			{
				return this.right.contains(element);
			}
		}
		
	}

	/**
	 * Checks whether the element with the given key exists in the subtree.
	 * 
	 * @param element Query key wrapped inside an empty BSTNode instance
	 * @return true, if an element with the given key is contained in the subtree; false otherwise.
	 */
	public boolean contains(BSTMapNode element) 
	{
		this.compare(element);
		if(element.getKey() == this.key)
		{
			return true;
			
		}
		else if(element.getKey() < this.key)
		{
			if(this.left == null)
			{
				return false;
			}
			else
			{
				return this.left.contains(element);
			}
		}
		else
		{
			if(this.right == null)
			{
				return false;
			}
			else
			{
				return this.right.contains(element);
			}
		}
		
	}
	
	/**
	 * Maps the given key to its value.
	 * 
	 * @param element Query key wrapped inside an empty BSTNode instance
	 * @return String value of the given key; null, if an element with the given key does not exist in the subtree.
	 */
	public String get(BSTMapNode element) 
	{
		if(element.getKey() == this.key)
		{
			return this.value;
		}
		else if(element.getKey() < this.key)
		{
			if(this.left == null)
			{
				return null;
			}
			else
			{
				return this.left.get(element);
			}
		}
		else
		{
			if(this.right == null)
			{
				return null;
			}
			else
			{
				return this.right.get(element);
			}
		}
	}

	/**
	 * Finds the smallest element in the subtree.
	 * 
	 * @return Smallest element in the subtree
	 */
	public BSTMapNode findMin() 
	{
		
		
		return null;
	}
	
	/**
	 * Depth-first pre-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by pre-order traversing the tree.
	 */
	List<Integer> traversePreOrder(List<Integer> oList) 
	{		
		oList.add(this.key);
		
		if(this.left != null)
		{
			this.left.traversePreOrder(oList);
		}
		
		if(this.right != null)
		{
			this.right.traversePreOrder(oList);
		}
		
		return oList;		
	}

	/**
	 * Depth-first in-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by in-order traversing the tree.
	 */
	List<Integer> traverseInOrder(List<Integer> oList) 
	{
		if(this.left != null)
		{
			this.left.traverseInOrder(oList);
		}
	
		oList.add(this.key);
	
		if(this.right != null)
		{
			this.right.traverseInOrder(oList);
		}
		
		return oList;
	}

	/**
	 * Depth-first post-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by post-order traversing the tree.
	 */
	List<Integer> traversePostOrder(List<Integer> oList) 
	{
		if(this.left != null)
		{
			this.left.traversePostOrder(oList);
		}
	
		if(this.right != null)
		{
			this.right.traversePostOrder(oList);
		}
		
		oList.add(this.key);
		
		return oList;
	}

	/**
	 * Breadth-first (or level-order) traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by breadth-first traversal of the tree.
	 */
	List<Integer> traverseLevelOrder(List<Integer> oList) 
	{
		Queue<BSTMapNode> q = new LinkedList<BSTMapNode>();
		q.add(this);
		
		while(!q.isEmpty())
		{
			BSTMapNode node = q.poll();
			oList.add(node.getKey());
			if(node.left != null)
			{
				q.add(node.left);
			}
			if(node.right != null)
			{
				q.add(node.right);
			}
		}
		
		return oList;
	}
}
