import { sendGET, sendPOST, sendDELETE } from '../js/api.js';

export function initializeTaskApp() {
    // Attach event listeners
    document.getElementById('view-all-tasks-form').addEventListener('submit', (e) => {
        e.preventDefault();
        displayAllTasks();
    });

    document.getElementById('priority-form').addEventListener('submit', (e) => {
        e.preventDefault();
        displayTasksWithPriority();
    });

    document.getElementById('add-task-form').addEventListener('submit', (e) => {
        e.preventDefault();
        addNewTask();
    });
}

export function displayAllTasks() {
    clearTasksTable();
    fetchAllTasks().then(displayTasks);
}

export function displayTasksWithPriority() {
    clearTasksTable();
    const priority = document.getElementById('priority-select').value;
    fetchTasksWithPriority(priority).then(displayTasks);
}

export function addNewTask() {
    const task = {
        name: document.getElementById('newTaskName').value,
        description: document.getElementById('newTaskDescription').value,
        priority: document.getElementById('newTaskPriority').value,
    };
    sendPOST("/tasks", task).then(displayAllTasks);
}

export function displayTask(taskName) {
    fetchTaskWithName(taskName).then((task) => {
        const taskDisplayElement = document.getElementById('currentTaskDisplay');
        taskDisplayElement.innerText = `${task.priority} priority task ${task.name} with description "${task.description}"`;
    });
}

export function deleteTask(taskId) {
    sendDELETE(`/tasks/${taskId}`).then(() => {
        clearTaskDisplay();
        displayAllTasks();
    });
}

// Helpers
function fetchTasksWithPriority(priority) {
    return sendGET(`/tasks/byPriority/${priority}`);
}

function fetchTaskWithName(name) {
    return sendGET(`/tasks/byName/${name}`);
}

function fetchAllTasks() {
    return sendGET("/tasks");
}

function clearTasksTable() {
    document.getElementById('tasksTableBody').innerHTML = "";
}

function clearTaskDisplay() {
    const taskDisplayElement = document.getElementById('currentTaskDisplay');
    taskDisplayElement.innerText = "None";
}

function displayTasks(tasks) {
    const tasksTableBody = document.getElementById('tasksTableBody');
    tasks.forEach((task) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${task.id}</td>
            <td>${task.name}</td>
            <td>${task.priority}</td>
            <td><button class="view-btn" data-name="${task.name}">View</button></td>
            <td><button class="delete-btn" data-id="${task.id}">Delete</button></td>
        `;
        tasksTableBody.appendChild(row);
    });

    // Attach event listeners to dynamically created buttons
    tasksTableBody.querySelectorAll('.view-btn').forEach((button) => {
        button.addEventListener('click', (e) => {
            const taskName = e.target.dataset.name;
            displayTask(taskName);
        });
    });

    tasksTableBody.querySelectorAll('.delete-btn').forEach((button) => {
        button.addEventListener('click', (e) => {
            const taskId = e.target.dataset.id;
            deleteTask(taskId);
        });
    });
}
