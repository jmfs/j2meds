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

import net.joaosilva.j2meds.Map.Entry;
import jmunit.framework.cldc11.TestCase;

public class BinaryTreeTest extends TestCase {
	
	public BinaryTreeTest() {
		super(5, "BinaryTreeTest");
	}

	public void test(int testNumber) {
		switch(testNumber) {
		case 0:
			testIsEmpty();
			break;
		case 1:
			testSize();
			break;
		case 2:
			testPutAll();
			break;
		case 3:
			testStringOrder();
			break;
		case 4:
			testIntegerOrder();
			break;
		
		default:
			break;
		}
	}
	
	public void testIsEmpty() {
		BinaryTree b = new BinaryTree(Comparators.STRING);
		assertTrue(b.isEmpty());
		b.put("test", new Integer(0));
		assertFalse(b.isEmpty());
		b.remove("some key that isn't there");
		assertFalse(b.isEmpty());
		b.remove("test");
		assertTrue(b.isEmpty());
		b.put("test", new Integer(0));
		assertFalse(b.isEmpty());
		MapIterator it = b.iterator();
		it.next();
		it.remove();
		assertTrue(b.isEmpty());
	}

	public void testSize() {
		BinaryTree b = new BinaryTree(Comparators.STRING);
		assertEquals(0, b.size());
		b.put("test", new Integer(0));
		assertEquals(1, b.size());
		b.remove("some key that isn't there");
		assertEquals(1, b.size());
		b.put("test", new Integer(1));
		assertEquals(1, b.size());
		b.remove("test");
		assertEquals(0, b.size());
		b.put("test", new Integer(0));
		assertEquals(1, b.size());
		MapIterator it = b.iterator();
		it.next();
		it.remove();
		assertEquals(0, b.size());
		b.put("bb", new Integer(2));
		b.put("ab", new Integer(1));
		b.put("cd", new Integer(3));
		assertEquals(3, b.size());
		b.put("aa", new Integer(1324));
		b.put("ac", new Integer(3241));
		assertEquals(5, b.size());
		b.put("ad", new Integer(6));
		assertEquals(6, b.size());
		b.remove("bb");
		assertEquals(5, b.size());
		b.clear();
		assertEquals(0, b.size());
	}
	
	public void testPutAll() {
		BinaryTree a = new BinaryTree(Comparators.INTEGER);
		a.put(new Integer(5), "five");
		a.put(new Integer(3), "three");
		a.put(new Integer(10), "ten");
		BinaryTree b = new BinaryTree(Comparators.INTEGER);
		b.put(new Integer(5), "five, eh");
		b.put(new Integer(1), "one, eh");
		b.put(new Integer(7), "seven, eh");
		b.put(new Integer(12), "twelve, eh");
		a.putAll(b);
		assertEquals(6, a.size());
		assertTrue(a.containsKey(new Integer(5)));
		assertEquals(0, ((String)a.get(new Integer(5))).compareTo("five, eh"));
		assertTrue(a.containsKey(new Integer(3)));
		assertEquals(0, ((String)a.get(new Integer(3))).compareTo("three"));
		assertTrue(a.containsKey(new Integer(10)));
		assertEquals(0, ((String)a.get(new Integer(10))).compareTo("ten"));
		assertTrue(a.containsKey(new Integer(1)));
		assertEquals(0, ((String)a.get(new Integer(1))).compareTo("one, eh"));
		assertTrue(a.containsKey(new Integer(7)));
		assertEquals(0, ((String)a.get(new Integer(7))).compareTo("seven, eh"));
		assertTrue(a.containsKey(new Integer(12)));
		assertEquals(0, ((String)a.get(new Integer(12))).compareTo("twelve, eh"));
		BinaryTree c = new BinaryTree(Comparators.STRING);
		c.put("stuff", new Integer(1));
		c.put("things", new Integer(6));
		try {
			a.putAll(c);
			/* this operation should cause an exception, so we put something obviously
			false here in case it doesn't
			*/
			assertTrue(false);
		}
		catch(InvalidOperationException e) {
			assertTrue(true);
		}
	}
	
	public void testStringOrder() {
		BinaryTree b = new BinaryTree(Comparators.STRING);
		b.put("mpompokpoko", null);
		b.put("adsasdfwet", null);
		b.put("jnioudfuojo", null);
		b.put("irjyidsff", null);
		b.put("sdhyu995", null);
		b.put("vcn9unthy95", null);
		b.put("caeiqjriot", null);
		b.put("324235etwsdfffas", null);
		MapIterator it = b.iterator();
		String latestValue = null;
		while(it.hasNext()) {
			Entry e = it.next();
			if(latestValue == null)
				latestValue = (String)e.getKey();
			else {
				assertTrue(latestValue.compareTo((String)e.getKey()) < 0);
				latestValue = (String)e.getKey();
			}
		}
	}
		
	public void testIntegerOrder() {
		BinaryTree b = new BinaryTree(Comparators.INTEGER);
		b.put(new Integer(31251235), null);
		b.put(new Integer(34834343), null);
		b.put(new Integer(135512673), null);
		b.put(new Integer(21432), null);
		b.put(new Integer(8978), null);
		b.put(new Integer(45731), null);
		b.put(new Integer(-23462346), null);
		b.put(new Integer(-36343), null);
		MapIterator it = b.iterator();
		Integer latestValue = null;
		while(it.hasNext()) {
			Entry e = it.next();
			if(latestValue == null)
				latestValue = (Integer)e.getKey();
			else {
				assertTrue(latestValue.intValue() < ((Integer)e.getKey()).intValue());
				latestValue = (Integer)e.getKey();
			}
		}
	}
}
