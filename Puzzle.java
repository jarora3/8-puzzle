/* Name - Jyoti Arora
 * Puzzle class has the main function that takes initial state and final state as an input from the user and then use
 * IterativeDLS, Astar and IDAstar to explore all the steps that need to be taken to reach the goal state. All these Search Techniques
 * are defined in the SearchTechniques.java file
 */
 

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Puzzle {
	private static String create_string_input(String str, String Delim) {
		 StringTokenizer st = new StringTokenizer(str, Delim);
	     String new_str = "";
	     while(st.hasMoreTokens()) {
	    	 String ch = st.nextToken(Delim);
	    	 ch = ch.trim();
	    	 if(ch.equals("10")) {
	    		 new_str += "A";
	    	 }else if(ch.equals("11")) {
	    		 new_str += "B";
	    	 }else if(ch.equals("12")) {
	    		 new_str += "C";
	    	 }else if(ch.equals("13")) {
	    		 new_str += "D";
	    	 }else if(ch.equals("14")) {
	    		 new_str += "E";
	    	 }else if(ch.equals("15")){
	    		 new_str += "F";
	    	 }else {
	    		 new_str += ch;
	    	 }
	     }	
	     return new_str;
	}
	public static void main(String args[]) throws IOException {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     System.out.print("Enter Initial State as a String  :  ");
	     String initial_state = br.readLine();
	    // System.out.print("Enter Final State as a String :  ");
	     String final_state = "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
	     //System.out.println("Starting ID-DFS");
	    // IterativeDLS dls = new IterativeDLS(create_string_input(initial_state, ","), create_string_input(final_state, ","));
		// dls.search_goal();
		 System.out.println("Starting A* - Heuristic 1");
	     A_Star a1 = new A_Star(create_string_input(initial_state, ","), create_string_input(final_state, ","), "Heuristic_1");
	     a1.search_goal();
	     System.out.println("Starting IDA* - Heuristic 1");
	     IDA_Star a2 = new IDA_Star(create_string_input(initial_state, ","), create_string_input(final_state, ","), "Heuristic_1");
		 a2.search_goal();
		 System.out.println("Starting A* - Heuristic 2");
		 A_Star a3 = new A_Star(create_string_input(initial_state, ","), create_string_input(final_state, ","), "Heuristic_2");
	     a3.search_goal();
	     System.out.println("Starting IDA* - Heuristic 2");
	     IDA_Star a4 = new IDA_Star(create_string_input(initial_state, ","), create_string_input(final_state, ","), "Heuristic_2");
		 a4.search_goal();
	}

}
