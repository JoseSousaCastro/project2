window.onload = function() {
   
  const usernameValue = localStorage.getItem('username')
  const passwordValue = localStorage.getItem('password')

  console.log('window on load está a funcionar!')
  getFirstName(usernameValue, passwordValue);
  getPhotoUrl(usernameValue, passwordValue);

  getRetroList(usernameValue, passwordValue);

  const addRetroButton = document.getElementById("addRetroBTN");
  addRetroButton.addEventListener("click", addRetrospective);
  
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
  console.log("getRetroList")
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
              createRetroTableBody(retro);
              console.log(retro);
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

async function addRetrospective() {
  console.log("addRetrospective")
  const usernameValue = localStorage.getItem('username')
  const passwordValue = localStorage.getItem('password')

  const retroDate = document.getElementById("retroDate").value;
  const retroTitle = document.getElementById("retroTitle").value;

  if (retroDate === "" || retroTitle === "") {
    alert("Please fill in both date and title");
    return;
  }
  else {
    const retroCreate = {
      date: retroDate,
      title: retroTitle
    };
    try {
      const response = await fetch("http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/retrospective/add", {
          method: 'POST',
          headers: {
              'Content-Type': 'application/JSON',
              'Accept': '*/*',
              username: usernameValue,
              password: passwordValue
          },
          body: JSON.stringify(retroCreate)
      });

      if (response.ok) {
          const newRetro = await response.json();
          createRetroTable(newRetro);
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
}



function createRetroTableBody(retro) {
  let tbody = document.querySelector(".retros-table-body");

  let row = document.createElement("tr");

  let dateCell = document.createElement("td");
  dateCell.textContent = retro.date;
  let titleCell = document.createElement("td");
  titleCell.textContent = retro.title;
  let membersCell = document.createElement("td");
  membersCell.textContent = retro.retrospectiveUsers ? retro.retrospectiveUsers.map(user => (user && user.username) ? user.username : '').join(", ") : '';

  row.appendChild(dateCell);
  row.appendChild(titleCell);
  row.appendChild(membersCell);

  tbody.appendChild(row);
}

//LOGOUT 
document.getElementById("logout-button-header").addEventListener('click', async function() {

  let logoutRequest = "http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/logout";
    
    try {   
        const response = await fetch(logoutRequest, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/JSON',
                'Accept': '*/*',
            }, 
        });
        if (response.ok) {
            
          localStorage.removeItem("username");
          localStorage.removeItem("password");

          window.location.href="index.html";

        } 
    } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
    }
})
