Narrative: Common user interactions with the applications


Scenario: Authenticated user makes a new task without  a deadline

Given an authenticated user with name "User1"
When it makes a new task called "Task sample 1"
Then  there should be a task with name "Task sample 1", null deadline and the secondary key of  "User1"

Scenario: Authenticated user makes a new task with a deadline

Given an authenticated user with name "User1"
When it makes a new task called "Task sample 1" and deadline date 03/05/2021
Then  there should be a task with name "Task sample 1", 03/05/2021 deadline and the secondary key of  "User1"

Scenario: Not authenticated user fails to make a new task

Given an unauthenticated user
When it makes a new task called "Task sample 1" and deadline date 03/05/2021
Then  it should throw an IllegallAccessException

Scenario: Authenticated user delete an existing task

Given an authenticated user with name "User1"
When it deletes a task called "Task to delete 1"
Then the task with name "Task to delete 1" should not exists into the DB

Scenario: User wants to modify a task

Given an authenticated user with name "User1"
When it modifies the task with name "Task to modify 1" to "Task to modify 2"
Then the task "Task to modify 1" should not exists into the DB 
And "Task to modify 2" should exists into the DB

Scenario: Authenticated user delete a non-existing task

Given an authenticated user with name "User1"
When it deletes a task called "Task not existing"
Then it should throw IllegalArgumentException
