//Fazer selecionar o botão certo consoante o status da task para a priority e status
// fazer logica para editar a task


window.onload = function () {
      
    const usernameValue = localStorage.getItem('username');
    const passwordValue = localStorage.getItem('password');
      
    if (usernameValue === null || passwordValue === null) {
        window.location.href = "index.html";
    } else {
        getFirstName(usernameValue, passwordValue);
        getPhotoUrl(usernameValue, passwordValue);
        const taskId = sessionStorage.getItem('taskId');

        getAllUsersTasks(usernameValue, passwordValue).then(tasksArray => {
            tasksArray.forEach(task => {
              if (task.id === taskId) {
                document.getElementById('titulo-task').textContent = task.title; // Colocar o título no input title
                document.getElementById('descricao-task').textContent = task.description; // Colocar a descrição na text area
                document.getElementById('tasktitle').innerHTML = task.title; // Colocar o título no título da página
              }
            });
        }).catch(error => {
            console.error('Error:', error);
            alert("Something went wrong while loading tasks");
        });
        
    }       
        

}

    const usernameValue = localStorage.getItem('username');
    const passwordValue = localStorage.getItem('password');

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
          alert("Something went wrong");
      }
    };



// Definir os botões de status
const todoButton = document.getElementById("todo-button"); // Atribuir o elemento respetivo à variável todoButton
const doingButton = document.getElementById("doing-button"); // Atribuir o elemento respetivo à variável doingButton
const doneButton = document.getElementById("done-button"); // Atribuir o elemento respetivo à variável doneButton


// Definir os botões de priority
const lowButton = document.getElementById("low-button");
const mediumButton = document.getElementById("medium-button");
const highButton = document.getElementById("high-button");

// Definir o botão To Do como default
let taskStatus = sessionStorage.getItem("taskStatus");
if(taskStatus == "todo"){
todoButton.classList.add("selected");
} else if( taskStatus== "doing"){
doingButton.classList.add("selected");
} else if(taskStatus == "done"){
doneButton.classList.add("selected");
}

// Definir o botão Low como default
var taskPriority = sessionStorage.getItem("taskPriority");
if(taskPriority == "low"){
    lowButton.classList.add("selected");
} else if( taskPriority== "medium"){
    mediumButton.classList.add("selected");
} else if(taskPriority == "high"){
    highButton.classList.add("selected");
}
// Função para definir o estado no grupo de botões status
function setStatusButtonSelected(button, status) {
    const buttons = [todoButton, doingButton, doneButton];
    buttons.forEach(btn => btn.classList.remove("selected"));
    button.classList.add("selected");
    sessionStorage.setItem("taskStatus", status);
}

// Função para definir o estado no grupo de botões priority
function setPriorityButtonSelected(button, priority) {
    const buttons = [lowButton, mediumButton, highButton];
    buttons.forEach(btn => btn.classList.remove("selected"));
    button.classList.add("selected");
    sessionStorage.setItem("taskPriority", priority);
}

// Event listeners para os botões status
todoButton.addEventListener("click", () => setStatusButtonSelected(todoButton, "todo"));
doingButton.addEventListener("click", () => setStatusButtonSelected(doingButton, "doing"));
doneButton.addEventListener("click", () => setStatusButtonSelected(doneButton, "done"));

// Event listeners para os botões priority
lowButton.addEventListener("click", () => setPriorityButtonSelected(lowButton, "low"));
mediumButton.addEventListener("click", () => setPriorityButtonSelected(mediumButton, "medium"));
highButton.addEventListener("click", () => setPriorityButtonSelected(highButton, "high"));

const cancelbutton = document.getElementById("cancel-button");
cancelbutton.addEventListener("click", () => {
    // Abrir o modal de cancel
    const cancelModal = document.getElementById("cancel-modal");
    cancelModal.style.display = "block";


    const cancelButton = document.getElementById("continue-editing-button");
    cancelButton.addEventListener("click", () => {
        window.location.href = 'task.html';
    });

    // Event listener para o botão de confirmação
    const confirmButton = document.getElementById("confirm-cancel-button");
    confirmButton.addEventListener("click", () => {
        sessionStorage.removeItem("taskDescription");
        sessionStorage.removeItem("taskTitle");
        sessionStorage.removeItem("taskid");
        sessionStorage.removeItem("taskStatus");
        sessionStorage.removeItem("taskPriority");
        window.location.href = 'home.html';    
    });
    cancelModal.style.display = "grid";
});

// Função para update das tasks
const updateTasks = (tasks, taskid, taskStatus, taskDescription, taskTitle, taskPriority) => {
    tasks.forEach(category => {
        category.forEach(task => {
            if (task.identificacao === taskid) {
                task.title = taskTitle;
                task.description = taskDescription;
                task.status = taskStatus;
                task.priority = taskPriority;
            }
        });
    });
};

// Event listener para o botão save
const savebutton = document.getElementById("save-button");
savebutton.addEventListener("click", () => {
    var tasks = JSON.parse(localStorage.getItem("tasks"));
    var taskid = sessionStorage.getItem("taskid");
    var taskStatus = sessionStorage.getItem("taskStatus");
    var taskDescription = document.getElementById("descricao-task").value.trim();
    var taskTitle = document.getElementById("titulo-task").value.trim();
    var taskPriority = sessionStorage.getItem("taskPriority");
    
    if (taskDescription === "" || taskTitle === "") {
        document.getElementById('warningMessage3').innerText = 'Your task must have a title and a description';
            return;
    } else {
        // Limpa mensagem de erro
        document.getElementById('warningMessage3').innerText = '';
    }
   
    updateTasks(tasks, taskid, taskStatus, taskDescription, taskTitle, taskPriority);

    localStorage.setItem("tasks", JSON.stringify(tasks));
    sessionStorage.removeItem("taskDescription");
    sessionStorage.removeItem("taskTitle");
    sessionStorage.removeItem("taskid");
    sessionStorage.removeItem("taskStatus");
    sessionStorage.removeItem("taskPriority");
    window.location.href = 'home.html';
});
