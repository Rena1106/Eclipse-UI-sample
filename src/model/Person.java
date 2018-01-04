package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Person {
	
	private String firstName;
	private String lastName;
	private String gender;
	private boolean married;
	private Integer age;
	
	//for extend with Eclipse Data Binding Support
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public Person() {}
	
	public Person(String firstName, String lastName, String gender, boolean married) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.married = married;
	}
	
	public void addPropertyChangeListener (String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		propertyChangeSupport.firePropertyChange("firstName", this.firstName, this.firstName = firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		propertyChangeSupport.firePropertyChange("lastName", this.lastName, this.lastName = lastName);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		propertyChangeSupport.firePropertyChange("gender", this.gender, this.gender = gender);
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		propertyChangeSupport.firePropertyChange("married", this.married, this.married = married);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		propertyChangeSupport.firePropertyChange("age", this.age, this.age = age);
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}
