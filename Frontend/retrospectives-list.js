window.onload = function() {
   
    const usernameValue = localStorage.getItem('username')
    const passwordValue = localStorage.getItem('password')
  
    console.log('window on load está a funcionar!')
    getFirstName(usernameValue, passwordValue);
    getPhotoUrl(usernameValue, passwordValue);
    
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