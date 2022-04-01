package edu.mikedev.task_manager.data;

import java.util.Date;
import java.util.Objects;

public class Task {
	
	private int id;
	private String title;
	private String description;
	private Date deadline;
    private boolean done;
	private User user;

	public Task(){}


	public Task(String title, String description, Date deadline, boolean done) {
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.done = done;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return id == task.id && done == task.done && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline) && Objects.equals(user, task.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, deadline, done, user);
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", deadline=" + deadline +
				", done=" + done +
				", id_user=" + user.getId() +
				'}';
	}
}
