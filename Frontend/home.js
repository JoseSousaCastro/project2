
window.onload = function() {
  const usernameValue = localStorage.getItem('username');
  const passwordValue = localStorage.getItem('password');

  console.log('window on load está a funcionar!')
  getFirstName(usernameValue, passwordValue);
  getPhotoUrl(usernameValue, passwordValue);
  //loadTasks();

  };

  function getValuesFromLocalStorage() {
    const usernameValue = localStorage.getItem('username');
    const passwordValue = localStorage.getItem('password');
    const userValues = [usernameValue, passwordValue];     
    return userValues;
  }

  function cleanAllTaskFields() {
    document.getElementById('warningMessage2').innerText = '';
    // Limpar os input fields depois de adicionar a task
    document.getElementById('taskName').value = '';
    document.getElementById('taskDescription').value = '';
    document.getElementById('task-startDate').value = '';
    document.getElementById('task-limitDate').value = '';
    removeSelectedPriorityButton();
    taskPriority = null;
  }

const tasks = document.querySelectorAll('.task')
const panels = document.querySelectorAll('.panel')

function attachDragAndDropListeners(task) { // Adiciona os listeners de drag and drop a uma tarefa criada dinamicamente
  task.addEventListener('dragstart', () => {
      task.classList.add('dragging')
  });

  task.addEventListener('dragend', () => {
      task.classList.remove('dragging')
  });
}

panels.forEach(panel => { // Adiciona os listeners de drag and drop a um painel
  panel.addEventListener('dragover', e => {
    e.preventDefault()
    const afterElement = getDragAfterElement(panel, e.clientY)
    const task = document.querySelector('.dragging')
    const panelID = document.getElementById(panel.id) // Guarda o ID do painel onde a tarefa vai ser colocada
    if (afterElement == null) {
      panel.appendChild(task)
      task.stateId = panel.id;
      for (var i = 0; i < tasks.length; i++) { // Percorre o array de tarefas e altera o stateId da tarefa para o painel onde foi colocada
        if (tasks[i].id == task.id) {
          tasks[i].stateId = panelID; // Atualiza o stateId da tarefa
        }
      }
    
    } else {
      panel.insertBefore(task, afterElement)
      task.stateId = panel.id;
      for (var i = 0; i < tasks.length; i++) {
        if (tasks[i].id == task.id) {
          tasks[i].stateId = panelID;
        }
      }
    }
  })
})

// Definir os botões de priority
const lowButton = document.getElementById("low-button-home");
const mediumButton = document.getElementById("medium-button-home");
const highButton = document.getElementById("high-button-home");
let taskPriority;


function setPriorityButtonSelected(button, priority) {
  const buttons = [lowButton, mediumButton, highButton];
  buttons.forEach(btn => btn.classList.remove("selected"));
  button.classList.add("selected");
  taskPriority = priority;
}
function removeSelectedPriorityButton() {
  const buttons = [lowButton, mediumButton, highButton];
  buttons.forEach(btn => btn.classList.remove("selected"));
}



// Event listeners para os botões priority
lowButton.addEventListener("click", () => setPriorityButtonSelected(lowButton, "low"));
mediumButton.addEventListener("click", () => setPriorityButtonSelected(mediumButton, "medium"));
highButton.addEventListener("click", () => setPriorityButtonSelected(highButton, "high"));

/* function getDragAfterElement(panel, y) {
    const draggableElements = [...panel.querySelectorAll('.task:not(.dragging)')] // Dentro da lista de painéis, seleciona todos os elementos com a classe task que nao tenham a classe dragging  
    return draggableElements.reduce((closest, child) => { // Retorna o elemento mais próximo do que esáa a ser arrastado e que está a ser comparado
        const box = child.getBoundingClientRect() // Retorna o tamanho do elemento e a sua posição relativamente ao viewport
        const offset = y - box.top - box.height / 2 // Calcula a distância entre o elemento que está a ser arrastado e o que está a ser comparado
        if (offset < 0 && offset > closest.offset) { // Se a distância for menor que 0 e maior que a distância do elemento mais próximo até agora
            return { offset: offset, element: child }
        } else { //
            return closest // Retorna o elemento mais próximo até agora
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element}  */

 

async function newTask(usernameValue, passwordValue, task) {
console.log('username: ' + usernameValue);
  let newTask = `http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/${usernameValue}/addTask`;
    
    try {
        const response = await fetch(newTask, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
                'username': usernameValue,
                'password': passwordValue
            },    
            body: JSON.stringify(task)
        });

          if (response.ok) {
            alert("Task created successfully");
          } else if (response.status === 401) {
            alert("Invalid credentials")
          } else if (response.status === 404) {
            alert("Impossible to create task. Verify all fields")
          }
      
    } catch (error) {
        console.error('Error:', error);
        alert("Task not created. Something went wrong");
    }
  };


  async function getAllUsersTasks(usernameValue, passwordValue) {

    let getTasks = `http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/${usernameValue}/tasks`;
      
      try {
          const response = await fetch(getTasks, {
              method: 'GET',
              headers: {
                  'Content-Type': 'application/JSON',
                  'Accept': '*/*',
                  username: usernameValue,
                  password: passwordValue
              },    
          });
  
            if (response.ok) {
              const tasks = await response.json(); 
              return tasks;
            } else if (response.status === 401) {
              alert("Invalid credentials")
            } else if (response.status === 406) {
              alert("Unauthorized access")
            }
        
      } catch (error) {
          console.error('Error:', error);
          alert("Task not created. Something went wrong");
      }
    };





function createTask(title, description, priority, startDate, limitDate) { // Cria uma nova task com os dados inseridos pelo utilizador
  const task = {
  title :title,
  description: description,
  stateId: 'todo',
  priority: priority,
  startDate: startDate,
  limitDate: limitDate
  }
  return task;
}

 // Event listener do botão add task para criar uma nova task e colocá-la no painel To Do (default para qualquer task criada)
 document.getElementById('addTask').addEventListener('click', function() {
console.log('addTask button clicked')

  let description = document.getElementById('taskDescription').value.trim();
  let title = document.getElementById('taskName').value.trim();
  let priority = taskPriority;
  let startDate = document.getElementById('task-startDate').value;
  let limitDate = document.getElementById('task-limitDate').value;
            
  if (title === '' || description === '' || priority === null || startDate === '' || limitDate === '' || startDate > limitDate || document.getElementsByClassName('selected').length === 0) {
    console.log('entrou no if para verificar se os campos estão preenchidos');
    document.getElementById('warningMessage2').innerText = 'Fill in all fields and define a priority';
  } else {
    let task = createTask(title, description, priority, startDate, limitDate);
    console.log('username no localStorage = ' + getValuesFromLocalStorage()[0]);
    const usernameValue = getValuesFromLocalStorage()[0];
    const passwordValue = getValuesFromLocalStorage()[1];
    newTask(usernameValue, passwordValue, task).then (() => {
      removeAllTaskElements();
      loadTasks();
      cleanAllTaskFields();
    });
  }
});

function createTaskElement(task) {
    const taskElement = document.createElement('div');
    taskElement.id = task.id;
    taskElement.priority = task.priority;
    taskElement.classList.add('task'); 
    if (task.priority === 'low') {
        taskElement.classList.add('low');
    } else if (task.priority === 'medium') {
        taskElement.classList.add('medium');
    } else if (task.priority === 'high') {
        taskElement.classList.add('high');
    }
    taskElement.draggable = true;
    taskElement.description = task.description;
    taskElement.title = task.title;
    taskElement.stateId = task.stateId;

    const postIt = document.createElement('div');
    postIt.className = 'post-it';

    const taskTitle = document.createElement('h3');
    taskTitle.textContent = task.title;
    const descriprioncontainer = document.createElement('div');
    descriprioncontainer.className = 'post-it-text';
    const displayDescription = document.createElement('p');
    displayDescription.textContent = task.description;

    const deleteButton = document.createElement('img');
    deleteButton.src = 'multimedia/dark-cross-01.png';
    deleteButton.className = 'apagarButton';
    deleteButton.addEventListener('click', function () {
        const  deletemodal = document.getElementById('delete-modal');
         deletemodal.style.display = "grid"; 
        const deletebtn = document.getElementById('delete-button');
        deletebtn.addEventListener('click', () => {
            deleteTask(taskElement.id);
            taskElement.remove();
            deletemodal.style.display = "none";
        });
        const cancelbtn = document.getElementById('cancel-delete-button');
        cancelbtn.addEventListener('click', () => {
            deletemodal.style.display = "none";
        });
    });
    descriprioncontainer.appendChild(displayDescription);
    postIt.appendChild(taskTitle);
    postIt.appendChild(deleteButton);
    taskElement.appendChild(postIt);
    postIt.appendChild(descriprioncontainer);
    taskElement.addEventListener('dblclick', function () {
        sessionStorage.setItem("taskDescription", taskElement.description);
        sessionStorage.setItem("taskTitle", taskElement.title);
        sessionStorage.setItem("taskid", taskElement.id);
        sessionStorage.setItem("taskstateId", taskElement.stateId);
        sessionStorage.setItem("taskPriority", taskElement.priority);
        window.location.href = 'task.html';
    });

    return taskElement;
}


  /* tasks.forEach(task => {
    const taskData = {
      id: task.id,
      title: task.title,
      description: task.description,
      stateId: task.stateId,
      priority: task.priority,
      startDate: task.startDate,
      editionDate: task.editionDate,
      limitDate: task.limitDate,
    };

    // Determina o stateId de cada task e coloca-a no array correspondente
    taskArrays[task.stateId].push(taskData);
  });

  // Combina todos os arrays de tasks num único array
  const tasksArray = [taskArrays.todo, taskArrays.doing, taskArrays.done];

  // Guarda o array global de tasks na local storage
  localStorage.setItem('tasks', JSON.stringify(tasksArray)); */

// Carrega as tarefas guardadas na local storage
function loadTasks() {
  
  getAllUsersTasks(getValuesFromLocalStorage()[0], getValuesFromLocalStorage()[1]).then(tasksArray => {
    tasksArray.forEach(task => {
      const taskElement = createTaskElement(task);
      const panel = document.getElementById(task.stateId);
      panel.appendChild(taskElement);
      attachDragAndDropListeners(taskElement);
    });
  }).catch(error => {
    console.error('Error:', error);
    alert("Something went wrong while loading tasks");
  });
}


function removeAllTaskElements() {
  const tasks = document.querySelectorAll('.task');
  tasks.forEach(task => task.remove());
}


function deleteTask(id) {
  const tasksArray = JSON.parse(localStorage.getItem('tasks'));

  // Iteração sobre todos os arrays de tasks para encontrar e remover a task
  tasksArray.forEach(taskArray => {
    const index = taskArray.findIndex(task => task.stateId === id); // Retorna o index da tarefa com o ID passado como argumento
    if (index !== -1) { // Se o index for diferente de -1
      taskArray.splice(index, 1); // Remove a tarefa do array
      const taskElement = document.getElementById(id); // Guarda o elemento da tarefa
      taskElement.remove(); // Remove o elemento da tarefa
    }
  });
}

window.onclose = function () { // Guarda as tarefas na local storage quando a página é fechada
 
}

document.getElementById("logout-button-header").addEventListener('click', function() {
  localStorage.removeItem("username");
  localStorage.removeItem("password");
  window.location.href="index.html";
})


async function getFirstName(usernameValue, passwordValue) {

  let firstNameRequest = "http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/getFirstName";
    
    try {
        const response = await fetch(firstNameRequest, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/JSON',
                'Accept': '*/*',
                username: usernameValue,
                password: passwordValue
            },    
        });

        if (response.ok) {

          const data = await response.text();
          console.log(data.firstName)
          document.getElementById("first-name-label").innerText = data;

        } else if (!response.ok) {
            alert("Invalid credentials")
        }

    } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
    }
}

async function getPhotoUrl(usernameValue, passwordValue) {

  
  let photoUrlRequest = "http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/getPhotoUrl";
    
    try {
        const response = await fetch(photoUrlRequest, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/JSON',
                'Accept': '*/*',
                username: usernameValue,
                password: passwordValue
            },    
        });

        if (response.ok) {

          const data = await response.text();
          document.getElementById("profile-pic").src = data;

        } else if (response.stateId === 401) {
            alert("Invalid credentials")
        } else if (response.stateId === 404) {
          alert("teste 404")
        }

    } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
    }
}