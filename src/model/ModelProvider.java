package model;

import java.util.ArrayList;
import java.util.List;

public enum ModelProvider {
	INSTANCE;
	
	private List<Person> persons;
	
	private ModelProvider() {
		persons = new ArrayList<Person>();
		persons.add(new Person("Ali", "Baba", "male", true));
		persons.add(new Person("Lily", "Smith", "female", false));
		persons.add(new Person("David", "Andrew", "male", true));
	}
	
	public List<Person> getPersons() {
		return persons;
	}
	
	

}
