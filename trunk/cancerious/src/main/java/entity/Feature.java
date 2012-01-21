package entity;

import java.util.ArrayList;
import java.util.List;

public class Feature {

	public String name;
	public int priority;
	public List<FeatureValue> values;
	public double mean;
	public double stddev;

	public Feature(String name, int priority) {
		super();
		this.name = name;
		this.priority = priority;
		values = new ArrayList<FeatureValue>();
	}

}
