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

public interface Map {
	void clear();	
	boolean containsKey(Object key);
	Object get(Object key);
	boolean isEmpty();
	Object put(Object key, Object value);
	void putAll(Map other);			
	boolean equals(Object other);	
	int hashCode();
	Object remove(Object key);
	int size();
	Comparator getKeyComparator();
	MapIterator iterator();
	
	class Entry {
		public Entry(Object key, Object value) {
			super();
			if(key == null)
				throw new InvalidKeyException("Keys cannot be null");
			this.key = key;
			this.value = value;
		}

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}
		
		public Object setValue(Object newValue) {
			Object oldValue = value;
			value = newValue;
			return oldValue;
		}
		
		public boolean equals(Object other) {
			if(!(other instanceof Entry))
				return false;
			Entry otherEntry = (Entry)other;
			return (key == null ? otherEntry.key == null : key.equals(otherEntry.key)) &&
					(value == null ? 
							otherEntry.value == null : value.equals(otherEntry.value));		
		}
		
		public int hashCode() {
			 return (key == null ? 0 : key.hashCode()) ^
					 (value == null ? 0 : value.hashCode());
		}

		Object key, value; 
	}
}
