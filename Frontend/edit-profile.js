window.onload = function() {
  
    let usernameValue = localStorage.getItem('username');
    let passwordValue = localStorage.getItem('password');
  
    console.log('window on load está a funcionar!')
    getFirstName(usernameValue, passwordValue);
    getPhotoUrl(usernameValue, passwordValue);
    loadUserData(usernameValue, passwordValue);
  
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

  async function loadUserData(usernameValue, passwordValue) {

    let loadDataRequest = `http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/${usernameValue}`;

    try {
        const response = await fetch(loadDataRequest, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
                username: usernameValue,
                password: passwordValue
            },
        });

        if (response.ok) {
            const data = await response.json();

            
            document.getElementById("username-title-editProfile").textContent = data.username || '';
            document.getElementById("firstName-editProfile").value = data.firstName || '';
            document.getElementById("lastName-editProfile").value = data.lastName || '';
            document.getElementById("phone-editProfile").value = data.phone || '';
            document.getElementById("photoURL-editProfile").value = data.photoURL || '';
            document.getElementById("email-editProfile").value = data.email || '';
            document.getElementById("currentPass-editProfile").value = data.password || '';

        } else {

        }
    } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
    }
}

document.getElementById("profile-save-button").addEventListener('click', async function (event) {
    event.preventDefault();

    let usernameValue = localStorage.getItem('username');
    let passwordValue = localStorage.getItem('password');

    let updatedUser = createUserData();

    let updateUserRequest =  `http://localhost:8080/jl_jc_pd_project2_war_exploded/rest/users/update/${usernameValue}`;

    try {
        const response = await fetch(updateUserRequest, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
                username: usernameValue,
                password: passwordValue
            },
            body: JSON.stringify(updatedUser)
        });

        if (response.ok) {
            alert("Profile updated successfully")
        } else if (response.status === 406) {
            alert("Invalid username on path")
        } else if (response.status === 401) {
            alert("Invalid credentials")
        }
    } catch (error) {
        console.error('Error:', error);
        alert("Something went wrong");
    }
});

function createUserData() {

    let isFormValid = document.getElementById('edit-profile-form').checkValidity();

    if (isFormValid) {
        
        //let password = document.getElementById('password-register').value.trim();
        let email = document.getElementById('email-editProfile').value.trim();
        let firstName = document.getElementById('firstName-editProfile').value.trim();
        let lastName = document.getElementById('lastName-editProfile').value.trim();
        let phone = document.getElementById('phone-editProfile').value.trim();
        let photoURL = document.getElementById('photoURL-editProfile').value.trim();

        return {
            
            
            email: email,
            firstName: firstName,
            lastName: lastName,
            phone: phone,
            photoURL: photoURL
        };
    } else {
        document.getElementById('registrationForm').reportValidity();
        console.error('Form is not valid');
        return null;
    }
}
