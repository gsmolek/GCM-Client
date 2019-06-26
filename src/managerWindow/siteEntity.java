package managerWindow;

public class siteEntity {

	private String id;
	private String name;
	private String type;
	private int accessiable;
	private double time;
	private String description;
	private double x;
	private double y;
	private int change;

	public siteEntity() {
		x = 0;
		y = 0;
	}

	public siteEntity(String name, String type, int accessiable, double time, String description, double x, double y,
			String id) {
		this.name = name;
		this.type = type;
		this.accessiable = accessiable;
		this.time = time;
		this.description = description;
		this.x = x;
		this.y = y;
		this.id = id;
		this.change = 0;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAccessiable(int accessiable) {
		this.accessiable = accessiable;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public int getAccessiable() {
		return this.accessiable;
	}

	public double getTime() {
		return this.time;
	}

	public String getDescription() {
		return this.description;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public String getID() {
		return this.id;
	}

	public int getChange() {
		return this.change;
	}

	public String toString() {
		return this.id;
	}

}
