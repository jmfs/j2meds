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

import java.util.Stack;

import net.joaosilva.j2meds.Map.Entry;

class BinaryTreeIterator implements MapIterator {
	private Stack nodesToProcess;
	private BinaryTreeNode curNode;
	private BinaryTree source;
	
	public BinaryTreeIterator(BinaryTree source, BinaryTreeNode root) {
		this.source = source;
		curNode = null;
		nodesToProcess = new Stack();
		pushAllNodesToTheLeftOf(root);
	}	
	
	private void pushAllNodesToTheLeftOf(BinaryTreeNode node) {
		BinaryTreeNode iterNode = node;
		while(iterNode != null) {		
			nodesToProcess.addElement(iterNode);
			iterNode = iterNode.left;
		}
	}

	public boolean hasNext() {
		return !nodesToProcess.empty();
	}

	public Entry next() {
		curNode = (BinaryTreeNode)nodesToProcess.pop();
		pushAllNodesToTheLeftOf(curNode.right);
		
		return new Entry(curNode.key, curNode.value);
	}

	public void remove() {
		// note: this only works because BinaryTree's remove deletes the predecessor node,
		// if the node to remove has 2 children. If it deleted the successor node, more
		// would have to be done.
		source.remove(curNode.key);
	}

}
