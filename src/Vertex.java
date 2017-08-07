
/**
 * This class wraps the given data in the form of a vertex
 * for easier processing of it later
 * @author jasanign
 * @version 10/29/2015
 *
 */
public class Vertex {

	/** Unique Integer corresponding to a vertex's id number. */
	private int ID;
	/** Color of the vertex */
	private String color;
	/** Default color of instance */
	public final String DEFAULT_COLOR = "White";
	
	/**
	 * Modified constructor of the Vertex class
	 * @param ID
	 */
	public Vertex(int ID) {
		
		this.ID = ID;
		this.color = DEFAULT_COLOR;
		
		
	}
	/**
	 * Getter method of color field
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * Setter method of ID field
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * Getter method of color field
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * Setter method of color field
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	
	/**
	 * Purpose: To present this vertex in nicely formatted
	 * string format
	 * @return string form of the instance of this class
	 */
	@Override
	public String toString(){
		
		return "Vertex ID: "+ID;
	}
	
	/**
	 * Equals method to compare another object with this 
	 * vertex object
	 * @param obj 
	 * @return true if another object is also a vertex, 
	 * false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vertex other = (Vertex) obj;
		if (ID != other.ID) {
			return false;
		}
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		return true;
	}	
}
