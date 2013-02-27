/*    Copyright 2013 Jo�o Miguel Ferreira da Silva

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

/** Interface for classes meaning to compare two objects
 * 
 * @author Jo�o Silva
 */
public interface Comparator {
	/** Compares first with second.
	 * 
	 * @param first First object to compare.
	 * @param second Second object to compare.
	 * @return A value less than 0 if first < second, 0 if first == second, and a value
	 *		greater than 0 if first > second.  
	 */
	public int compare(Object first, Object second);
}
