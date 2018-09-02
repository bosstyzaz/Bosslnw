package logic;

public class Movie {
	private String name;
	private int length;
	
	public Movie(String name, int length) {
		if((name.equals("")) || (length<1)) {
			this.name = "No Name";
			this.length = 1 ;
		} else {
			this.name = name ;
			this.length = length;
		}
	}
	public String getName() {
		return this.name;
	}
	public int getLength() {
		return this.length;
	}
}
