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

public class DoubleComparator implements Comparator {
	public int compare(Object first, Object second) {
		double firstValue = ((Double)first).doubleValue();
		double secondValue = ((Double)second).doubleValue(); 
		if(firstValue < secondValue)
			return -1;
		else if(firstValue > secondValue)
			return 1;
		else
			return 0;
	}
}