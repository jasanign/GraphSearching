/**
 * Driver for our graph class
 * @author jasanign
 * @version 11/04/2015
 */
public class GraphDriver {

	/**
	 * Entry point for driver and so the graph class
	 * @param args[0] filename
	 */
	public static void main(String[] args) {
		
		if(args.length < 1 || args.length > 1){
			
			System.out.println("Usage: file_name");
			System.out.println("Please try again using a "
					+ "valid file name");
			System.exit(0);
		}
		Graph graph = new Graph();
		graph.startGraph(args[0]);
	}
}
