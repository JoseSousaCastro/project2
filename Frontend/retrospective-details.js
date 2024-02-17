window.onload = function() {
   
  const usernameValue = localStorage.getItem('username')
  const passwordValue = localStorage.getItem('password')

  console.log('window on load está a funcionar!')
  getFirstName(usernameValue, passwordValue);
  getPhotoUrl(usernameValue, passwordValue);

  // Obter o ID da retrospectiva da URL
  const urlParams = new URLSearchParams(window.location.search);
  const retrospectiveId = urlParams.get('id');
  console.log('ID da retrospectiva:', retrospectiveId);

  if (retrospectiveId) {
    // Obter detalhes da retrospectiva e atualizar a página
    getRetrospectiveDetails(usernameValue, passwordValue, retrospectiveId);
  } else {
    console.error('ID da retrospectiva não encontrado na URL.');
  } 
  
};

function getValuesFromLocalStorage() {
  const usernameValue = localStorage.getItem('username');
  const passwordValue = localStorage.getItem('password');
  const userValues = [usernameValue, passwordValue];     
  return userValues;
}


async function getRetrospectiveDetails(usernameValue, passwordValue, retrospectiveId) {
  const retrospectiveEndpoint = `http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/retrospective/${retrospectiveId}`;

  try {
    const response = await fetch(retrospectiveEndpoint, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Accept': '*/*',
        'username': usernameValue,
        'password': passwordValue
      },
    });

    if (response.ok) {
      const retrospectiveInfo = await response.json();

      const retroTitleElement = document.getElementById('retro-title');
      retroTitleElement.innerText = retrospectiveInfo.title;

      const retroTitleElement2 = document.getElementById('retro-title-aside');
      retroTitleElement2.innerText = retrospectiveInfo.title;

      const retroDateElement = document.getElementById('retro-date-each');
      retroDateElement.innerText = retrospectiveInfo.date;

    } else if (response.status === 401) {
      alert("Invalid credentials");
    } else if (response.status === 404) {
      alert("Retrospective not found");
    }

  } catch (error) {
    alert("Something went wrong");
  }
}

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
        alert("Something went wrong2");
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
