package edu.mikedev.task_manager.data;

import java.util.Objects;
import java.util.Set;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private Set<Task> tasks;

	public User(){}
		
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, email);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
