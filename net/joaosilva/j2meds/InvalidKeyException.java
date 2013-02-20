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

/** 
 * Exception class thrown when null keys are inserted into the tree,
 * since null keys are reserved for the dummy node above the root.
 * @author João Silva <joaomiguelsilva@gmail.com>
 *
 */
public class InvalidKeyException extends RuntimeException {
	public InvalidKeyException() {
		super();
	}
	
	public InvalidKeyException(String msg) {
		super(msg);
	}
}
