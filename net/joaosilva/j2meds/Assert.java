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
 * A poor man's version of the assert statement, since it is unavailable. 
 */
class Assert {
	private static final boolean ENABLED = true;  
	/** Throws an exception if expression is false. The exception's message
	 *  will be the one provided as a parameter.
	 * 
	 * @param expression Pass the result of the expression you want to test.
	 * @param message The exception's message
	 */
	public static void assert(boolean expression, String message) {
		if(ENABLED && !expression)
			throw new AssertException(message);
	}
}


class AssertException extends RuntimeException {
	public AssertException(String message) {
		super(message);
	}
}
