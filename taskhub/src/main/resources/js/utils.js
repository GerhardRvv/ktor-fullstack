export function clearTasksTable() {
    tasksTable().innerHTML = "";
}

export function clearTaskDisplay() {
    taskDisplay().innerText = "None";
}

export function displayTasks(tasks) {
    const tasksTableBody = tasksTable();
    tasks.forEach(task => {
        const newRow = taskRow(task);
        tasksTableBody.appendChild(newRow);
    });
}

function tasksTable() {
    return document.getElementById("tasksTableBody");
}

function taskDisplay() {
    return document.getElementById("currentTaskDisplay");
}

function taskRow(task) {
    return tr([
        td(task.name),
        td(task.priority),
        td(viewLink(task.name)),
        td(deleteLink(task.name)),
    ]);
}

function tr(children) {
    const node = document.createElement("tr");
    children.forEach(child => node.appendChild(child));
    return node;
}

function td(content) {
    const node = document.createElement("td");
    if (content instanceof Element) {
        node.appendChild(content);
    } else {
        node.appendChild(document.createTextNode(content));
    }
    return node;
}

function viewLink(taskName) {
    const node = document.createElement("a");
    node.setAttribute("href", `javascript:displayTask("${taskName}")`);
    node.appendChild(document.createTextNode("view"));
    return node;
}

function deleteLink(taskName) {
    const node = document.createElement("a");
    node.setAttribute("href", `javascript:deleteTask("${taskName}")`);
    node.appendChild(document.createTextNode("delete"));
    return node;
}
