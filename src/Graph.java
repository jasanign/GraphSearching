import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class creates a Graph from the input files and provides
 * methods to process the graph in various different ways
 * @author jasanign
 *@version 10/29/2015
 */
public class Graph {
	
	/** List of vertices in the graph */
	private ArrayList<Vertex> vertexList;
	/** Adjacency list where the list at each index corresponds
	 * to the vertices adjacent to vertex ID in vertexList */
	private ArrayList<ArrayList<Integer>> adjList;
	/** Adjacency matrix where a value of 1 represents an edge
	 * between two vertices */
	private int[][] adjMatrix;
	/** Stack for Depth-first search */
	private Stack stack;
	/** Just a number to initialize adjMatrix */
	private final int maxNumOfVertices = 50;
	/** array to store user input */
	private int[] indices = new int[2];
	/** to store depth-first search result*/
	private String dfsResult = "";
	/** default vertex to start cycle search*/
	public final int default_vertex = 0;
	/** Number of vertices in vertexList*/
	private int numOfVertices;
	
	/**
	 * Default constructor of the Graph class to initialize the fields
	 */
	
	public Graph() {
		
		vertexList = new ArrayList<>();
		adjList = new ArrayList<>();
		adjMatrix = new int[maxNumOfVertices][maxNumOfVertices];
		for(int column=0; column < 20; column++){
			for(int raw=0; raw < 20; raw++){
				adjMatrix[raw][column] = 0;
			}
	    }
		stack = new Stack();
		
	}
	/**
	 * Initiates and runs the entire process described 1-5
	 * @param: String graphFile -- Name of the input file to be 
	 * 								read for the adjacency list
	 */
	public void startGraph(String graphFile){
		
		
		readInputGraph(graphFile);
		
		indices = readSourceDest();
		numOfVertices = vertexList.size();
		
		if(indices[0] >= numOfVertices){
			System.out.println("Source vertex is not "
					+ "present in the graph");
			System.out.println("Please try again with a "
					+ "valid source vertex #.");
			System.exit(0);
		}
		
		if(indices[1] >= numOfVertices){
			System.out.println("Destination vertex is not "
					+ "present in the graph");
			System.out.println("Please try again with a "
					+ "valid destination vertex #.");
			System.exit(0);
		}
			
		dfsResult = dfsSearch(indices[0], indices[1]);
		
		printGraphStats();
		
	}
	
	/**
	 * Reads the inputFile given at a command line argument and 
	 * places its contents into a master list vertexLis and 
	 * adjacency list adjList.
	 * @param: String graphFile -- Name of the input file to be read
	 */
	public void readInputGraph(String inputFile){

		try(Scanner myScanner = new Scanner(new File(inputFile))){
			
			while(myScanner.hasNextLine()){
				
				
				String line = myScanner.nextLine();
				String[] ints = line.split(" ");
				
				/*to prevent an empty line with a space from 
				breaking the code
				especially at the end of the file */
				if(ints.length == 0){
					continue;
				}
				int ID = 0;
				int connection = 0;
				try{
					ID = Integer.parseInt(ints[0]);
					connection = Integer.parseInt(ints[1]);
				} catch (NumberFormatException ime){
					System.out.println("Input file has "
							+ "unreadable data.");
					System.out.println("Please check the "
							+ "Input file and try again");
					System.exit(0);
				}
				int largest = Math.max(ID, connection);
				
				adjMatrix[ID][connection] = 1;
				
				for(int i = vertexList.size(); i < largest+1; i++){
					vertexList.add(new Vertex(i));
				}
				while(adjList.size() < largest+1){
					adjList.add(new ArrayList<>());
				}
				
				if(!(adjList.get(ID).contains(connection))){
					int size = adjList.get(ID).size();
					if(size == 0){
						adjList.get(ID).add(connection);
					}else{
						while(size != 0){	// Sorting 
							if(connection<adjList.get(ID).get(size-1)){
								size--;
							} else {
								break;
							}
						}
						adjList.get(ID).add(size, connection);
					}
				}				
			}
		} catch (FileNotFoundException fnfe) {
			
			System.out.println("Input file not found...");
			System.exit(0);
		}
		
		
	}
	
	/**
	 * Purpose: Prompts, checks, and accepts valid arguments for
	 * the source and destination
	 * @return sourceDesti array with source & Destination vertex #
	 */
	public int[] readSourceDest( ){
		
		int[] sourceDesti = new int[2];
		Scanner source = null;
		Scanner destination = null;
			
		try{
			source = new Scanner(System.in);
			System.out.println("Please enter the source vertex #: ");
			int sourceVertex = source.nextInt();
			if (sourceVertex < 0){
				System.out.println("Please try again with a "
						+ "valid source vertex #.");
				System.exit(0);
			}
			sourceDesti[0] = sourceVertex;
		} catch (InputMismatchException ime){
			System.out.println("Please try again with a "
					+ "valid source vertex #.");
			System.exit(0);
		} 
		
		try{
			destination = new Scanner(System.in);
			System.out.println("Please enter the destination "
					+ "vertex #: ");
			int destiVertex = destination.nextInt();
			if (destiVertex < 0){
				System.out.println("Please try again with a "
						+ "valid destination vertex #.");
				System.exit(0);
			}
			sourceDesti[1] = destiVertex;
		} catch (InputMismatchException ime){
			System.out.println("Please try again with a "
					+ "valid destination vertex #.");
			System.exit(0);
		}finally{
			source.close();
			destination.close();
		}	
		return sourceDesti;
	}
	
	/**
	 * Performs the depth-first search form source to destination or 
	 * until the search is exhausted if no path exist from source
	 * to destination
	 * @param source
	 * @param destination
	 * @return
	 */
	public String dfsSearch(int source, int destination){
		
		vertexList.get(source).setColor("Black");
		stack.push(source);
		int i = 0;
		
		while(!stack.isEmpty()){
			int peek = stack.peek();
			ArrayList<Integer> temp = adjList.get(peek);
			if (temp.isEmpty()){
				stack.pop();
				i++;
			} else {
				if(i < temp.size()){					
					int adjacent = temp.get(i);
					if(adjacent == destination){
						String path = ""+destination;
						while(!stack.isEmpty()){
							path =  stack.pop()+ " -> "+path;
						}
						return path;
					} else {
						if(!vertexColor(adjacent).equals("Black")){
							vertexList.get(adjacent).setColor("Black");
							stack.push(adjacent);
							i=0;
						} else {
							i++;
						}
					}
				} else {
					stack.pop();
					i = 0;
				}
			}
		}
		// Setting the color of vertices back to default
		for(int j = 0; j < vertexList.size(); j++){
	         vertexList.get(j).setColor("White");
	    }
		return "No path exists between source vertex and"
		+ " destination vertex.";
	}
	
	/**
	 * Performs the search for cycles starting at the first
	 * vertex of the graph
	 * @return true if cycle exists, false if not
	 */	
	public boolean cycleSearch(){
		
		stack.clear();
		String visited = "Gray";
		String popped = "Black";
		vertexList.get(default_vertex).setColor(visited);
		stack.push(default_vertex);
		int i = 0;
		
		while(!stack.isEmpty()){
			int peek = stack.peek();
			ArrayList<Integer> temp = adjList.get(peek);
			if (temp.isEmpty()){
				int poppedVertex = stack.pop();
				vertexList.get(poppedVertex).setColor(popped);
				i++;
			} else {
				if(i < temp.size()){
					int adjacent = temp.get(i);
					if(vertexColor(adjacent).equals(visited)){
						return true;
					} else if(!(vertexList.get(adjacent).equals(popped))){
						vertexList.get(adjacent).setColor(visited);
						stack.push(adjacent);
						i=0;
					}
				}else{
					int poppedVertex = stack.pop();
					vertexList.get(poppedVertex).setColor(popped);
					i++;
				}
			}
		}
		// Setting the color of vertices back to default
		for(int j = 0; j < vertexList.size(); j++){
	         vertexList.get(j).setColor("White");
	    }		
		return false;
	}
	
	/**
	 * Performs the transitive closure on the adjMatrix and return
	 * transitive closure of the graph
	 * @return transitive closure of the graph
	 */
	public int[][] transitiveClosure(){
		
		boolean[][] transClosure = new boolean[numOfVertices][numOfVertices]; 
		int[][] tc = new int[numOfVertices][numOfVertices];
		
		for (int i = 0; i < numOfVertices; i++){
            for (int j = 0; j < numOfVertices; j++){
                if(adjMatrix[i][j] == 0){
                	transClosure[i][j] = false;
                }else{
                	transClosure[i][j] = true;
                }
            }
        }
 
		for (int i = 0; i < numOfVertices; i++){
            for (int j = 0; j < numOfVertices; j++){
                if (transClosure[j][i]){ 
                    for (int k = 0; k < numOfVertices; k++){ 
                        if (transClosure[j][i] && transClosure[i][k]){
                        	transClosure[j][k] = true;
                        }
                    }
                }
            }
        }
		
		for (int i = 0; i < numOfVertices; i++){
            for (int j = 0; j < numOfVertices; j++){
                if(transClosure[i][j] == false){
                	tc[i][j] = 0;
                }else{
                	tc[i][j] = 1;
                }
            }
        }
        return tc;	
	}	
	
	/**
	 * Prints the information about the current state of the graph
	 */
	void printGraphStats(){
		
		System.out.println();
		System.out.println("Vertex List: "+ 
							vertexList.toString());
		System.out.println();
		System.out.println("Adjecency List: "+
								adjList.toString());
		System.out.println();
		System.out.println("DFS Path: "+dfsResult);
		System.out.println();
		if(cycleSearch()){
			System.out.println("Cycle Search Result: Cycle Exists");
		}else{
			System.out.println("Cycle Search Result: Cycle Does Not Exists");
		}
		System.out.println();
		System.out.println("Adjacency Matrix of the Graph:");
		printMatrix(adjMatrix);
		System.out.println("Transitive Closure of the Graph:");
		printMatrix(transitiveClosure());
	}
	
//====================== Helper Methods ======================
	
	/**
	 * Method to get the color of the vertex
	 * @param v Vertex ID
	 * @return Vertex color
	 */
	private String vertexColor(int v){
		
		 return vertexList.get(v).getColor();
	}
	
	/**
	 * Print the given adjacency matrix
	 * @param givenMatrix matrix provided
	 */
	private void printMatrix(int[][] givenMatrix){
		
		System.out.print("  ");
		for(int k = 0; k < numOfVertices; k++){
			System.out.print("  "+k);
		}
		System.out.println();
		System.out.print("  ");
		for(int l = 0; l < numOfVertices; l++){
			System.out.print("  -");
		}
		System.out.println();
		for (int i = 0; i < numOfVertices; i++){
			System.out.print(i + " | ");
	        for (int j = 0; j < numOfVertices; j++){
	        	System.out.print(givenMatrix[i][j] + "  ");
	        }
	        System.out.println();
	        System.out.println();
	    }
	}
	
	
//++++++++++++++++++++++++++ Stack class +++++++++++++++++++++
	/**
	 * Stack implementation for depth-first search and 
	 * cycle search in graph
	 * @author jasanign
	 * @version 11/4/2015
	 */
	
	private class Stack{
		/** Vertex ID holder for over stack */
		private int[] holder;
		/** top node tracker */
		private int top;
	
		/**
		 * Default constructor to initialize fields
		 */
		public Stack(){
			holder = new int[20];
			top = -1;
		}
	
		/**
		 * Add vertex ID on top of the stack
		 * @param j ID to be added
		 */
		public void push(int j){
			holder[++top] = j;
		}
	   
		/**
		 * Remove and return top ID
		 * @return top ID
		 */
		public int pop(){
			return holder[top--];
		}
	   
		/**
		 * Just return top ID without removing it
		 * @return top ID
		 */
		public int peek(){
			return holder[top];
		}
	   
		/**
		 * Check if stack is empty or not
		 * @return true if top is -1, false otherwise
		 */
		public boolean isEmpty(){
			return (top == -1);
		}
		
		/**
		 * Clear the stack
		 */
		public void clear(){
			top = -1;
		}
		
	}
}
