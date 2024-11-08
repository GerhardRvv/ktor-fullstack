package com.example.model

object TaskRepository {
    private val tasks = mutableListOf(
        Task("Buy milk", "Go to the store and buy some milk", Priority.Medium),
        Task("Clean the house", "Clean the whole house", Priority.High),
        Task("Call mom", "Call mom and say hello", Priority.Low),
        Task("Clean the Other house", "Clean the other whole house", Priority.High),
        Task("Call mom", "Call mom and say hello", Priority.Low),
//        Task("Do homework", "Do the math homework", Priority.Vital)
    )

    fun allTasks(): List<Task> = tasks

    fun tasksByPriority(priority: Priority) = tasks.filter {
        it.priority == priority
    }

    fun tasksByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    fun addTask(task: Task) {
        if (tasksByName(task.name) == null) {
            throw IllegalArgumentException("Task with name ${task.name} already exists")
        }
        tasks.add(task)
    }
}
