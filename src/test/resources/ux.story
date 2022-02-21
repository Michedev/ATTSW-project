Narrative: Common user interactions with the applications

Scenario: Authenticated user makes a new task with a deadline

Given an authenticated user with name "tizio" and password "caio"
When it makes a new task called "Task sample 1" with description "Task description 23" and deadline date "03/05/2021"
Then there should be a task with name "Task sample 1", description "Task description 23" and "03/05/2021" deadline and the secondary key of "tizio"


Scenario: User wants to modify a task

Given an authenticated user with name "tizio" and password "caio"
When it modifies the task with name "Run a marathon" to "Task to modify 2"
Then the task "Run a marathon" should not exists into the DB
And the task "Task to modify 2" should exists into the DB


Scenario: Authenticated user delete an existing task

Given an authenticated user with name "pippo" and password "pluto"
When it deletes a task called "Sample task title 1"
Then the task "Sample task title 1" should not exists into the DB


Scenario: Completing task

Given an authenticated user with name "pippo" and password "pluto"
When it completes a task called "Sample task title 1"
Then the task "Sample task title 1" should be completed

Scenario: User registration and creation of new task