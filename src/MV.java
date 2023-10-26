public class MV {
	
	public long line;
	public String completeName;
	public String name;
	public String year;
	public Type type;
	public String episode;

	public MV() {
		this.line = 0;
		this.name = "";
		this.year = "";
		this.type = Type.NONE;
		this.episode = "";
	}

	public long getLine() {
		return line;
	}
	
	public Long setLine(long line) {
		this.line = line;
		return line;
	}
	
	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String cName) {
		this.completeName = cName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String chapter) {
		this.episode = chapter;
	}

	public void printMovie() {
		System.out.println("--- Movie BEGIN --- ");
		System.out.println("line = " + this.line + "\nName = " + this.name + "\nYear = " + this.year + "\nType = " + this.type + "\nChapter = " + this.episode);
		System.out.println("---  Movie END  --- \n");
	}

}
