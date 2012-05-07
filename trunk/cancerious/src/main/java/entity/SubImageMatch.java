package entity;

import java.io.Serializable;

public class SubImageMatch implements Serializable{

	private static final long serialVersionUID = 1L;

	public SubImageMatch(SubImage match1, SubImage match2) {
		super();
		this.match1 = match1;
		this.match2 = match2;
	}

	SubImage match1;
	SubImage match2;

	public SubImage getMatch1() {
		return match1;
	}

	public void setMatch1(SubImage match1) {
		this.match1 = match1;
	}

	public SubImage getMatch2() {
		return match2;
	}

	public void setMatch2(SubImage match2) {
		this.match2 = match2;
	}

	@Override
	public String toString() {
		return "SubImageMatch [match1=" + match1 + ", match2=" + match2 + "]";
	}

}
