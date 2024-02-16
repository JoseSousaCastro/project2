window.onload = function() {
   
  const usernameValue = localStorage.getItem('username')
  const passwordValue = localStorage.getItem('password')

  console.log('window on load está a funcionar!')
  getFirstName(usernameValue, passwordValue);
  getPhotoUrl(usernameValue, passwordValue);

  getRetroList(usernameValue, passwordValue);
  
};

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
  
          } else if (response.status === 401) {
              alert("Invalid credentials")
          } else if (response.status === 404) {
            alert("teste 404")
          }
  
      } catch (error) {
          console.error('Error:', error);
          alert("Something went wrong");
      }
  }

  async function getRetroList(usernameValue, passwordValue) {

    let retroListRequest = "http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/retrospective/all";
      
      try {
          const response = await fetch(retroListRequest, {
              method: 'GET',
              headers: {
                  'Content-Type': 'application/JSON',
                  'Accept': '*/*',
                  username: usernameValue,
                  password: passwordValue
              },    
          });
  
          if (response.ok) {
  
            const data = await response.json();
            console.log(data)

            data.forEach(retro => {
              createRetroTable(retro);
            });
          } else if (response.status === 401) {
            alert("Invalid credentials");
          } else if (response.status === 404) {
            alert("Error 404");
          }
      } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
      }
}

function createRetroTable(retro) {
  const table = document.createElement("table");
  table.classList.add("retros-table");
  const thead = document.createElement("thead");
  thead.classList.add("retros-table-header");

  const headerRow = document.createElement("tr");
  const dateHeader = document.createElement("th");
  dateHeader.classList.add("table-header");
  dateHeader.textContent = "Date";
  const titleHeader = document.createElement("th");
  titleHeader.classList.add("table-header");
  titleHeader.textContent = "Title";
  const membersHeader = document.createElement("th");
  membersHeader.classList.add("table-header");
  membersHeader.textContent = "Members";

  headerRow.appendChild(dateHeader);
  headerRow.appendChild(titleHeader);
  headerRow.appendChild(membersHeader);

  thead.appendChild(headerRow);
  table.appendChild(thead);

  const tbody = document.createElement("tbody");
  tbody.classList.add("retros-table-body");

  const row = document.createElement("tr");

  const dateCell = document.createElement("td");
  dateCell.textContent = retro.date;
  const titleCell = document.createElement("td");
  titleCell.textContent = retro.title;
  const membersCell = document.createElement("td");
  membersCell.textContent = retro.retrospectiveUsers.map(user => user.username).join(", ");

  row.appendChild(dateCell);
  row.appendChild(titleCell);
  row.appendChild(membersCell);

  tbody.appendChild(row);
  table.appendChild(tbody);

  let retroListContainer = document.getElementById("retro-list");
  retroListContainer.appendChild(table);
}


