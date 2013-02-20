/*    Copyright 2013 João Miguel Ferreira da Silva

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package net.joaosilva.j2meds;

/** A bog standard, naive binary storage tree implementation, since it 
 *  doesn't seem to exist in Java ME.
 *  
 *  To make comparing between keys easy and unambiguous, null keys are not allowed. 
 *  
 *  @author João Silva <joaomiguelsilva@gmail.com>
 */
public class BinaryTree implements Map {
	/** A dummy node, prior to the root. Both its children point to the root.
	 * It is the only node allowed to have a null key. This way, comparisons with it
	 * are always false.
	 */
	private BinaryTreeNode dummy;
	private BinaryTreeNode root;
	private int size;
	private Comparator comparator;
	
	/** Creates a new BinaryTree, using the specified {@link Comparator}.
	 * @param keyComparator A Comparator to compare keys. Usually, this will be one
	 *  of the ones provided in {@link Comparators}.
	 */
	public BinaryTree(Comparator keyComparator) {
		this.comparator = keyComparator;
		// dummy is the only node whose key can be null
		dummy = new BinaryTreeNode(null, null);
		clear();		
	}
	
	public void clear() {
		root = dummy.left = dummy.right = null;
		size = 0;
	}
	
	/** Returns the node above the one with that key.
	 *  Because of the dummy node, works even if the key is in the root.
	 * 
	 * @param key The key to search for.
	 * @param nullWhenNotFound If true, returns null when the key isn't found.
	 *        If false, returns the node prior to where the key would have been.
	 * @return The node above the one with the provided key. If the key is in the
	 *         root, returns the root, since there's no node above it. 
	 * 
	 */
	private BinaryTreeNode findNodePriorTo(Object key, boolean nullWhenNotFound) {
		BinaryTreeNode curNode = root;
		BinaryTreeNode prevNode = dummy; 
		/* prevNode is initialized to root, since we want to return root
		 * in the case where there's only one node.
		 */
		while(curNode != null) {			
			int compareValue = comparator.compare(key, curNode.key);
			if(compareValue == 0)
				return prevNode;
			
			prevNode = curNode;
			if(compareValue < 0)
				curNode = curNode.left;
			else
				curNode = curNode.right;
		}
		
		if(nullWhenNotFound)
			return null;
		else
			return prevNode; 
	}
	
	private BinaryTreeNode findNodeWithKey(Object key) {
		BinaryTreeNode prev = findNodePriorTo(key, true);
		if(prev == null)
			return null;
		if(prev == dummy)
			return root;
		
		int compareValue = comparator.compare(key, prev.key);		
		if(compareValue < 0)
			return prev.left;
		else
			return prev.right;		
	}
	
	public boolean containsKey(Object key) {
		return findNodeWithKey(key) != null;
	}
    
	public boolean containsValue(Object value, Comparator valueComparator) {
		MapIterator it = iterator();
		while(it.hasNext()) {
			Entry curEntry = it.next();
			if(valueComparator.compare(value, curEntry.value) == 0)
				return true;
		}
		return false;
	}
	
	public Object get(Object key) {
		BinaryTreeNode node = findNodeWithKey(key);
		if(node == null)
			return null;
		
		return node.value;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Replaces a node's value with a new value.  
	 * 
	 * @param node A non-null BinaryTreeNode, which will have its value 
	 *             replaced with newValue.
	 * @param newValue The value with which to replace the node's old value.
	 * @return The node's old value.
	 */
	private Object replaceValue(BinaryTreeNode node, Object newValue) {
		Assert.assert(node != null, 
				"BinaryTree.replaceValue must never be called with a null node");
		
		Object oldValue = node.value;
		node.value = newValue;
		return oldValue;
	}
	
	public Object put(Object key, Object value) {
		if(key == null)
			throw new InvalidKeyException("Can't insert null keys");
		if(root == null) { // empty tree
			root = new BinaryTreeNode(key, value);
			dummy.left = dummy.right = root;
			++size;
			return null;
		}
		
		BinaryTreeNode nearest = findNodePriorTo(key, false);
		BinaryTreeNode nodeToWorkOn = null;
		if(nearest == dummy) {
			Assert.assert(comparator.compare(root.key, key) == 0,
					"There's some bug in the binary tree's logic. Code should " + 
					"only reach here if the desired key is in the tree's root");
			nodeToWorkOn = root;
		}
		else {
			int compareValue = comparator.compare(key, nearest.key);
			Assert.assert(compareValue != 0, "There's a duplicate key in the tree");
			
			if(compareValue < 0) {
				if(nearest.left == null)
					nearest.left = new BinaryTreeNode(key, value);
				else
					nodeToWorkOn = nearest.left;
			}
			else {
				if(nearest.right == null)
					nearest.right = new BinaryTreeNode(key, value);
				else
					nodeToWorkOn = nearest.right;
			}
		}
		
		if(nodeToWorkOn == null) {
			++size;
			return null;
		}
		
		return replaceValue(nodeToWorkOn, value);
	}

	public void putAll(Map other) {
		if(comparator != other.getKeyComparator())
			throw new InvalidOperationException(
					"putAll can't work on maps with different comparators");
		MapIterator it = other.iterator();
		while(it.hasNext()) {
			Map.Entry entry = it.next();
			put(entry.getKey(), entry.getValue());
		}
	}
	
	public boolean equals(Object other) {
		if(!(other instanceof Map))
			return false;
		
		Map otherMap = (Map)other;
		if(otherMap.size() != size)
			return false;
		
		boolean result = true;
		MapIterator it = otherMap.iterator();
		while(it.hasNext()) {
			Map.Entry cur = it.next();
			if(!containsKey(cur.getKey())) {
				result = false;
				break;
			}			
		}
		
		return result;
	}
	
	public int hashCode() {
		MapIterator it = iterator();
		int result = 0;
		while(it.hasNext()) {
			Map.Entry cur = it.next();
			result += cur.hashCode();
		}
		return result;
	}
	
	private BinaryTreeNode findPredecessorParent(BinaryTreeNode node) {
		BinaryTreeNode curNode = node.left;
		BinaryTreeNode prevNode = node;
		while(curNode.right != null) {
			prevNode = curNode;
			curNode = curNode.right;
		}
		return prevNode;
	}
	
	private Object removeNode(BinaryTreeNode nodeToRemove, BinaryTreeNode parent) {
		Assert.assert(nodeToRemove == parent.left || nodeToRemove == parent.right,
				"parent isn't really this node's parent node");
		if(nodeToRemove == null)
			return null;
		Object result = nodeToRemove.value;
		if(nodeToRemove.left == null) {
			if(nodeToRemove.right == null) {				
				if(nodeToRemove == parent.left)
					parent.left = null;
				else
					parent.right = null;
				if(nodeToRemove == root) {
					clear();
					return result;
				}
			}
			else {
				if(nodeToRemove == parent.left)
					parent.left = nodeToRemove.right;
				else
					parent.right = nodeToRemove.right; 
			}
		}
		else {
			if(nodeToRemove == parent.left) {
				if(nodeToRemove == parent.left)
					parent.left = nodeToRemove.left;
				else
					parent.right = nodeToRemove.left;
			}
			else {
				BinaryTreeNode predecessorParent = findPredecessorParent(nodeToRemove);
				BinaryTreeNode predecessor = predecessorParent.right;
				nodeToRemove.key = predecessor.key;
				nodeToRemove.value = predecessor.value;
				removeNode(predecessor, predecessorParent);
				++size; // that removeNode operation decreases the size one more time than needed
				// that needs to be reverted.
			}
		}
		--size;
		return result;
	}
	

	public Object remove(Object key) {
		if(root == null)
			return null;

		BinaryTreeNode nearest = findNodePriorTo(key, false);
		BinaryTreeNode nodeToRemove = null;
		if(nearest == dummy)
			nodeToRemove = root;
		else {
			int compareValue = comparator.compare(key, nearest.key);
			Assert.assert(compareValue != 0, "Duplicate key detected");		
					
			if(compareValue < 0)
				nodeToRemove = nearest.left;
			else
				nodeToRemove = nearest.right;
		}
		return removeNode(nodeToRemove, nearest);		
	}
	
	public int size() {
		return size;
	}

	public Comparator getKeyComparator() {
		return comparator;
	}

	public MapIterator iterator() {
		return new BinaryTreeIterator(this, root);
	}
}

class BinaryTreeNode {
	public BinaryTreeNode(Object newKey, Object newValue) {
		left = right = null;
		key = newKey;
		value = newValue;
	}
	
	BinaryTreeNode left;
	BinaryTreeNode right;
	
	Object key, value;	
}
