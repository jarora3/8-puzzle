
import java.util.*;

/*Node is a data structure from which the search tree is constructed*/
class Node {
	String state;      // state of the board configuration
	Node parent;       // parent node
	int depth = 0;     // depth of the node
	
	public void set_state(String current_st) {
		state = current_st;
	}
	public void set_parent(Node node) {
		parent = node;
	}
	public String get_state() {
		return state;
	}
	public Node get_parent() {
		return parent;
	}
	public void set_depth(int dep) {
		depth = dep;
	}
	public int get_depth() {
		return depth;
	}
	
}


/*SearchTechniques is an abstract class from which the classes BreadthFS and DepthFS are derived. Both the classes derived
  from it implement "search_goal" function which defines the strategy in which the goal is searched */

abstract public class SearchTechniques {
	  long start_time;                                 // Start Time when the object was allocated
	  protected String initial_state;                  // Initial start state of the board
	  protected String goal_state;                     // Goal State of the board that needs to be achieved
	  protected int num_of_nodes = 0;                  // Number of nodes traversed before finding the goal state
	  protected static final Node NULL = null;
	  Runtime rt = Runtime.getRuntime();
	  protected static final String[] actions = {"left", "right", "up", "down"};
	  
	  SearchTechniques(String start_state, String final_state){
		  initial_state = start_state;
		  goal_state = final_state;
		  start_time = System.currentTimeMillis();
		  rt.gc();
	  }
	  
	  abstract void search_goal();
	  
	  private void print_matrix(String state) {
		  int statearray[][]= new int[4][4], k=0;
		  String matrix_out = "";
		  for(int i =0; i<=3; i++)
		  {
			  for(int j=0; j<=3; j+=1)
			  {
				 if(state.charAt(k) == 'A') {
					 statearray[i][j] = 10;
			     }else if(state.charAt(k) == 'B') {
			    	 statearray[i][j] = 11;
			     }else if(state.charAt(k) == 'C') {
			    	 statearray[i][j] = 12;
			     }else if(state.charAt(k) == 'D') {
			    	 statearray[i][j] = 13;
			     }else if(state.charAt(k) == 'E') {
			    	 statearray[i][j] = 14;
			     }else if(state.charAt(k) == 'F') {
			    	 statearray[i][j] = 15;
			     }else {
			    	 statearray[i][j] = state.charAt(k) - '0';
			     }
				 k+=1;
			  }
		  }
		  for(int i =0; i<=3; i++)
		  {
			  for(int j=0; j<=3; j+=1)
			  {
				matrix_out = matrix_out + " " + statearray[i][j]; 
			  }
			  matrix_out = matrix_out +"\n";
		  } 
		  System.out.println(matrix_out);
	  }
	  
	  protected void print_solution(Node curr_state) {
		  System.out.println("Total Memory Used : " + ((rt.totalMemory() - rt.freeMemory())/1024) + " KBs");
		  System.out.println("Trace of Sequences :");
		  System.out.println("Depth Of Solution " + curr_state.get_depth());
		  Stack<Node> pathList = new Stack<Node>();
		  while(curr_state != NULL) {
			  pathList.push(curr_state);
			  curr_state = curr_state.get_parent();
		  }
		  /* Print Matrix Representation of the output */
		  while(!pathList.isEmpty()) {
			  curr_state = pathList.pop();
			  String state = curr_state.get_state();
			  print_matrix(state);
		  }	  
		  
	  }
	  
	  /*It returns the new configuration generated after applying action on the current configuration. Both the current configuration 
	    and the action are the input to this function*/ 
	  protected String apply_action(String state, String action) {
		  String next_state = state;
		  int indexOf0 = state.indexOf("0");
		  if(action == "left") {
			  if(indexOf0 != 0 && indexOf0 != 4 && indexOf0 != 8 && indexOf0 != 12){
				  next_state = state.substring(0, indexOf0-1) + "0" +
			                   state.charAt(indexOf0-1) + state.substring(indexOf0+1);
			  }
			  
		  } else if (action == "right") {
            if(indexOf0 != 3 && indexOf0 != 7 && indexOf0 != 11 && indexOf0 != 15){
          	  next_state = state.substring(0,indexOf0) + state.charAt(indexOf0+1)
          	               + "0" + state.substring(indexOf0+2); 
			  }
			  
		  } else if (action == "up") {
            if(indexOf0 != 0 && indexOf0 != 1 && indexOf0 != 2 && indexOf0 != 3){
          	  
          	 next_state = state.substring(0,indexOf0-4)+ "0" +
          	              state.substring(indexOf0-3,indexOf0)+ state.charAt(indexOf0-4)
          	              + state.substring(indexOf0+1);
				  
			  }
			  
		  } else if (action == "down") {
            if(indexOf0 != 12 && indexOf0 != 13 && indexOf0 != 14 && indexOf0 != 15){
				  next_state = state.substring(0, indexOf0)+ state.substring(indexOf0+4, indexOf0+5)
				               +state.substring(indexOf0+1,indexOf0+4)+"0"+ state.substring(indexOf0+5);
			  }
			  
		  }
		  return next_state;
	  }
	
}

/*class IterativeDLS is derived from SearchTechniques and implement iterative DLS by calling DLS 
  *iteratively from depth 0 to infinity
  */

class IterativeDLS extends SearchTechniques {
	 

	 
	 IterativeDLS(String start_state, String final_state) {
		  super(start_state, final_state);
	  }
	  private boolean DLS(Node node, int depth) {
		  String current_state = node.get_state();
		  if (depth >= 0) {
			  if(current_state.equals(goal_state)){
				  System.out.println("Execution Time : " + (System.currentTimeMillis() - start_time));
				  System.out.println("Num of Nodes Generated : " + num_of_nodes);
				  print_solution(node);
				  return true;
			  }
			 // explored_nodes.add(node.get_state());
  
			  for(int i = 0; i < actions.length; i++) {
				  String new_state = apply_action(current_state, actions[i]);
				  Node new_node = new Node();
				  num_of_nodes++;
				  new_node.set_state(new_state);
				  new_node.set_depth(node.get_depth() + 1);
				  new_node.set_parent(node);
				  boolean found = DLS(new_node, depth-1);
				  if(found) {
					  return true;
				  } 
			  }
		  }
		  //explored_nodes.clear();
		  return false;
	  }
	  void search_goal() {
		  Node start_state = new Node();
		  num_of_nodes++;
		  start_state.set_state(initial_state);
		  start_state.set_depth(0);
		  start_state.set_parent(NULL);
		  for(int depth = 0; depth < 1000; depth++) {
			  if( DLS(start_state, depth) ) {
				  return;
			  }
		  }
		  
	  }
	
}


/**
 * 
 * HeuristicFunction class contains implementation for both the heuristics- misplaced tiles and manhattan distance
 *
 */

class HeuristicFunction {
	static private int find_x(int loc) {
		if(loc <= 3)
			return 0;
		else if(loc <= 7)
			return 1;
		else if(loc <= 11)
			return 2;
		else 
			return 3;
		
	}
	static private int find_y(int loc) {
    	if(loc <= 3)
			return loc - 0;
		else if(loc <= 7)
			return loc - 4;
		else if(loc <= 11)
			return loc - 8;
		else 
			return loc - 12;
    	
		
	}
    
	static public int get_manhattan_dist(String goal_state, Node n) {
		String state = n.get_state();
		int manhattan_dist=0;
		for( int i = 0; i < goal_state.length(); i++ ) {
			char ch = state.charAt(i);
			if(ch=='0')
				continue;
			int loc = goal_state.indexOf(ch);
			int x_s=find_x(i);
			int y_s=find_y(i);
			int x_g=find_x(loc);
			int y_g=find_y(loc);
			manhattan_dist += Math.abs(x_s - x_g) + Math.abs(y_s -y_g);
		}
		return manhattan_dist;
			
	}
	
	static public int get_misplaced_tiles(String goal_state, Node n) {
		String state = n.get_state();
		int num_misplaced_tiles=0;
		for( int i = 0; i < goal_state.length(); i++ ) {
			if(state.charAt(i) != goal_state.charAt(i)) {
				num_misplaced_tiles++;
			}
		}
		return num_misplaced_tiles;
	}	
}


/**
 * PriorityQueue Comparator using cost of node obtained by using Heuristic 1 for comparison
 *
 */

class StateComparator_H1 implements Comparator<Node> {
	String goal_state;
	StateComparator_H1(String goal) {goal_state = goal;}
    
		public int compare(Node a, Node b){
		int f_a_val = a.get_depth() + HeuristicFunction.get_misplaced_tiles(goal_state, a);
		int f_b_val = b.get_depth() + HeuristicFunction.get_misplaced_tiles(goal_state, b);
		if(f_a_val < f_b_val) {
			return -1;
		} else {
			return 1;
		}
	}
	
}


/**
 * PriorityQueue Comparator using cost of node obtained by using Heuristic 2 for comparison
 *
 */

class StateComparator_H2 implements Comparator<Node> {
	String goal_state;
	
	StateComparator_H2(String goal) {goal_state = goal;}
	
	public int compare(Node a, Node b){
		int f_a_val = a.get_depth() + HeuristicFunction.get_manhattan_dist(goal_state, a);
		int f_b_val = b.get_depth() + HeuristicFunction.get_manhattan_dist(goal_state, b);
		if(f_a_val < f_b_val) {
			return -1;
		} else {
			return 1;
		}
	}
	
}


/*
 * A_Star implements A* algorithm for both Heuristic 1 and Heuristic 2 of 15 puzzle
 */

class A_Star extends SearchTechniques {
	  String heuristic;
	  A_Star(String start_state, String final_state, String heuristic_str) {
		  super(start_state, final_state);
		  heuristic = heuristic_str;
	  }
	  
	  private Comparator<Node> get_comparator() {
		  if(heuristic.equals("Heuristic_1")) {
			  return new StateComparator_H1(goal_state);
		  } else {
			  return new StateComparator_H2(goal_state);
		  }
	  }
	  
	  public void search_goal() {	  
		  Node start_state = new Node();
		  num_of_nodes++;
		  start_state.set_state(initial_state);
	      start_state.set_parent(NULL);
	      start_state.set_depth(0);
	      if(initial_state.equals(goal_state)) {
	    	  print_solution(start_state);
	    	  return;	
	      }
	      Comparator<Node> comparator = get_comparator();
	      PriorityQueue<Node> frontier_list = new PriorityQueue<Node>(100, comparator);
	      Set<String> explored_nodes = new HashSet<String>();
	      frontier_list.add(start_state);
	      Iterator<Node> list_iter = frontier_list.iterator();
	      while(list_iter.hasNext()){
	    	  Node current_state = frontier_list.remove();
	    	  explored_nodes.add(current_state.get_state());
	    	  for(int i = 0; i < actions.length; i++) {
	    		  String state = apply_action(current_state.get_state(), actions[i]);
	    		  Node new_state = new Node();
	    		  num_of_nodes++;
	    		  new_state.set_parent(current_state);
	    		  new_state.set_state(state);
	    		  new_state.set_depth(current_state.get_depth() + 1);
	    		  if( !explored_nodes.contains(state) ) {
	    			  if(state.equals(goal_state)) {
	    				  System.out.println("Execution Time : " + (System.currentTimeMillis() - start_time));
	    				  System.out.println("Num of Nodes Generated : " + num_of_nodes);
	    				  print_solution(new_state);
	    				  return;
	    			  } else {
	    				  frontier_list.add(new_state);
	    			  }
	    		  }
	    	  }
		  }
	   }
	  
}


/**
 * 
 * IDA_Star implements IDA* for both heuristic 1 and heuristic 2 of 15 puzzle
 *
 */

class IDA_Star extends SearchTechniques {
	  String heuristic;
	  IDA_Star(String start_state, String final_state, String heuristic_str) {
		  super(start_state, final_state);
		  heuristic = heuristic_str;
	  }
	 int min_value = -1;
	 
	 
	  
	 private int Bounded_AStar(Node node, int bound) {
		  String current_state = node.get_state();
		  int f_val = node.get_depth() + ( heuristic.equals("Heuristic_1") ? HeuristicFunction.get_misplaced_tiles(goal_state, node) :
                                          HeuristicFunction.get_manhattan_dist(goal_state, node) );
		  if( f_val > bound) {
			  return f_val;
		  }
		  if(current_state.equals(goal_state)){
			  System.out.println("Execution Time : " + (System.currentTimeMillis() - start_time));
			  System.out.println("Num of Nodes Generated : " + num_of_nodes);
			  print_solution(node);
			  return -1;
		  }
		  for(int i = 0; i < actions.length; i++) {
			 
			  String new_state = apply_action(current_state, actions[i]);
			  Node new_node = new Node();
			  num_of_nodes++;
			  new_node.set_state(new_state);
		      new_node.set_depth(node.get_depth() + 1);
		      new_node.set_parent(node);
		 	  int found = Bounded_AStar(new_node, bound);
			  if(found == -1) {
				  return found;
			  } 
			  if(found < min_value || min_value < 0) {
				  min_value = found;
			  }
		   }
		  return min_value;
	  }
	  
	 public void search_goal() {
		  Node start_state = new Node();
		  num_of_nodes++;
		  start_state.set_state(initial_state);
		  start_state.set_depth(0);
		  start_state.set_parent(NULL);
		  int f_val = start_state.get_depth() + ( heuristic.equals("Heuristic_1") ? HeuristicFunction.get_misplaced_tiles(goal_state, start_state) :
			                                    HeuristicFunction.get_manhattan_dist(goal_state, start_state) ); 
		  int last_bound;
		  for(int depth = 0; depth < 1000; depth++) {
			  last_bound = Bounded_AStar(start_state, f_val) ;
              if(last_bound == -1 ){
				  return;
			  }
              f_val = last_bound;
              min_value = -1;
		  }
		  return;
	 }
	  
}


  

  

