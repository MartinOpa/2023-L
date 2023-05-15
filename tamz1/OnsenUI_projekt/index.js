function onLoad() {
    showTodoLists();
}

function labelOnclick() {
    var todoText = $("#taskName");
    todoText.css("border-bottom", "none");
}

function showTodoLists() {
    $("#todoLists").empty();
    var taskLists = JSON.parse(localStorage.getItem("lists")) || [];

    for (var i = 0; i < taskLists.length; i++) {
        var onsItem = document.createElement('ons-list-item');
        var todoList = taskLists[i];
        onsItem.innerHTML = `
        <div class="list-item">
            <div class="list-item__left">
                <div id="${i}" class="background"></div>
                <div class="list-item__icon">
                    <ion-icon name="search-outline" onclick="transferToTodo(${i})"></ion-icon>
                </div>
            </div>
            <div class="list-item__center list-item--material__center">
                <span class="list-item__title">${todoList.name}</span>
            </div>
            <div class="list-item__right">
                <div class="list-item__icon">
                    <ion-icon name="close-outline" onclick="removeList(${i})"></ion-icon>
                </div>
            </div>
        </div>
        `;

        document.getElementById('todoLists').appendChild(onsItem);
    }
}

function addListOfTasks() {
    var listName = $("#listName");
    if (listName.val() == '') {
        listName.attr("placeholder", "Jméno seznamu");
        listName.css("border-bottom", "solid rgba(255, 0, 0, .8) 2px");
    } else {
        var lists = JSON.parse(localStorage.getItem("lists"));
        if (lists === undefined || lists === null || lists === []) {
            id = 0;
        } else {
            var id = lists.length;
        }

        let todoList = {
            'name': listName.val(),
            'id': id,
            'tasks': []
        };
        
        lists.push(todoList);

        localStorage.setItem("lists", JSON.stringify(lists));

        $("#listName").attr("placeholder", "Jméno seznamu").val('');
        closeDialogMain();

        showTodoLists();
    }
}

function addTask() {
    var id = localStorage.getItem("index");
    var todoText = $("#taskName");

    var taskLists = JSON.parse(localStorage.getItem("lists"));
    var todoList = taskLists[id];

    let newTodoList = {
        'name': todoList.name,
        'id': todoList.id,
        'tasks': todoList.tasks
    };

    if (todoText.val() == '') {
        todoText.attr("placeholder", "Položka");
        todoText.css("border-bottom", "solid rgba(255, 0, 0, .8) 2px");
    } else {
        if (newTodoList.tasks != null && newTodoList.tasks != undefined) {
            taskId = newTodoList.tasks.length;
        } else {
            taskId = 0;
        }

        let todo = {
            'text': todoText.val(),
            'id': taskId,
            'completed': false
        };

        newTodoList.tasks.push(todo);

        taskLists[id] = newTodoList;

        var newList = [];
        for (var i = 0; i < taskLists.length; i++) {
            let todoList = {
                'name': taskLists[i].name,
                'id': i,
                'tasks': taskLists[i].tasks
            };
            newList.push(todoList);
        }    

        localStorage.setItem("lists", JSON.stringify(newList));

        $("#taskName").attr("placeholder", "Položka").val('');
        closeDialog();
    }

    showTodo(localStorage.getItem("index"));
}

function transferToTodo(i) {
    localStorage.setItem("index", i);
    if (document.location != 'page1.html') {
        document.location = 'page1.html';
    }
}

function showTodo(i) {
    if (i != undefined || i != null) {
        localStorage.setItem("index", i);
    }

    $("#todoList").empty();

    var id = localStorage.getItem("index");
    var taskLists = JSON.parse(localStorage.getItem("lists"));
    for (var i = 0; i < taskLists.length; i++) {
        if (taskLists[i].id == id) {
            localStorage.setItem("index", id);
            for (var j = 0; j < taskLists[i].tasks.length; j++) {
                var onsItem = document.createElement('ons-list-item');
                let todo = {
                    'text': taskLists[i].tasks[j].text,
                    'id': taskLists[i].tasks[j].id,
                    'completed': taskLists[i].tasks[j].completed
                }

                onsItem.innerHTML = `
                    <div class="todo">
                        <span class="todo_text">${todo.text}</span>
                        <div class="todo_checkbox">
                        <ons-checkbox id="checkbox-${i}-${j}" modifier="material" onclick="toggleCheckbox(${i}, ${j})" ${todo.completed ? "checked" : ""}></ons-checkbox>
                        </div>
                    </div>
                    `;               
                document.getElementById('todoList').appendChild(onsItem);
            }
        }
    }
}

function toggleCheckbox(i, j) {
    var taskLists = JSON.parse(localStorage.getItem("lists"));
    let todo = {
        'text': taskLists[i].tasks[j].text,
        'id': taskLists[i].tasks[j].id,
        'completed': taskLists[i].tasks[j].completed
    }
    todo.completed = !todo.completed || false; 
    taskLists[i].tasks[j] = todo;

    const checkbox = document.querySelector(`#checkbox-${i}-${j}`);
    checkbox.checked = taskLists[i].tasks[j].completed;

    localStorage.setItem("lists", JSON.stringify(taskLists));
}

function removeList(id) {
    var taskLists = JSON.parse(localStorage.getItem("lists")) || [];
    var newList = [];
    var newId = 0;

    console.log(id);

    for (var i = 0; i < taskLists.length; i++) {
        if (taskLists[i].id === id) {
            continue;
        }

        let todoList = {
            'name': taskLists[i].name,
            'id': newId,
            'tasks': taskLists[i].tasks
        };
        newId++;
        newList.push(todoList);
    }    

    localStorage.setItem("lists", JSON.stringify(newList));
    showTodoLists();
}

function addDialog() {
    document.getElementById('dialog-1').show();
}

function addListDialog() {
    document.getElementById('dialog-0').show();
}

function closeDialogMain() {
    document.getElementById('dialog-0').hide();
}

function closeDialog() {
    document.getElementById('dialog-1').hide();
}

function goBackToMenu() {
    document.location = 'index.html'; 
}
