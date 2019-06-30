package managerWindow;
/**
 * this class is an entity for tours
 * 
 *
 */
public class toursEntity {
	/**
	 * tours variables
	 */

	private String name;
	private String description;
	private int id;
	public toursEntity(int id, String description,String name) {
		this.id= id;
		this.description=description;
		this.name = name;
	}
	/**
	 * getters and setters
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
}
