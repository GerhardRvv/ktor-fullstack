package com.example.model

class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task(id = 1, "cooking", "Cook dinner", Priority.High),
        Task(id = 2, "cleaning", "Clean the house", Priority.Low),
        Task(id = 3, "shopping", "Buy the groceries", Priority.High),
        Task(id = 4, "painting", "Paint the fence", Priority.Medium)
    )

    override suspend fun allTasks(): List<Task> = tasks

    override suspend fun tasksByPriority(priority: Priority) = tasks.filter {
        it.priority == priority
    }

    override suspend fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    override suspend fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalArgumentException("Task with name ${task.name} already exists")
        }
        tasks.add(task)
    }

    override suspend fun removeTask(id: Int): Boolean {
        return tasks.removeIf { it.id ==  id}
    }
}
