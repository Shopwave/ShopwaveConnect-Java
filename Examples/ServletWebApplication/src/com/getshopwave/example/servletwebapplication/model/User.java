package com.getshopwave.example.servletwebapplication.model;

public class User 
{

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private Employee employee;
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public Employee getEmployee() 
	{
		return employee;
	}
	
	public void setEmployee(Employee employee) 
	{
		this.employee = employee;
	}
}
