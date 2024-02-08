// register.js

document.getElementById('registerButton-register').addEventListener("click", function () {
    var username = document.getElementById('username-register').value.trim();
    var password = document.getElementById('password-register').value.trim();
    var passwordConfirm = document.getElementById('passwordConfirm-register').value.trim();
    var email = document.getElementById('email-register').value.trim();
    var firstName = document.getElementById('firstName-register').value.trim();
    var lastName = document.getElementById('lastName-register').value.trim();
    var phone = document.getElementById('phone-register').value.trim();
    var photoURL = document.getElementById('photoURL-register').value.trim();

    // Validar o username
    if (username === '') {
        document.getElementById('username-register').setCustomValidity('Username é obrigatório.');
    } else if (!/^[a-zA-Z0-9._-]+$/.test(username)) {
        document.getElementById('username-register').setCustomValidity('Username só pode ter letras (maiúsculas e minúsculas), dígitos, ".", "-" e "_".');
    } else {
        document.getElementById('username-register').setCustomValidity('');
    }

    // Validar a password
    if (password === '') {
        document.getElementById('password-register').setCustomValidity('Password é obrigatória.');
    } else if (!/^[a-zA-Z0-9!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+$/.test(password)) {
        document.getElementById('password-register').setCustomValidity('Password só pode ter letras (maiúsculas e minúsculas), dígitos e caracteres especiais.');
    } else {
        document.getElementById('password-register').setCustomValidity('');
    }

    // Validar a confirmação da password
    if (passwordConfirm === '') {
        document.getElementById('passwordConfirm-register').setCustomValidity('Confirmação de password é obrigatória.');
    } else if (password !== passwordConfirm) {
        document.getElementById('passwordConfirm-register').setCustomValidity('A confirmação de password não coincide.');
    } else {
        document.getElementById('passwordConfirm-register').setCustomValidity('');
    }

    // Validar o email
    if (email === '') {
        document.getElementById('email-register').setCustomValidity('Email é obrigatório.');
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        document.getElementById('email-register').setCustomValidity('Por favor, insira um endereço de e-mail válido.');
    } else {
        document.getElementById('email-register').setCustomValidity('');
    }

    // Validar o primeiro nome
    if (firstName === '') {
        document.getElementById('firstName-register').setCustomValidity('Primeiro nome é obrigatório.');
    } else if (!/^[a-zA-Z]+$/.test(firstName)) {
        document.getElementById('firstName-register').setCustomValidity('Primeiro nome só pode conter letras (maiúsculas e minúsculas).');
    } else {
        document.getElementById('firstName-register').setCustomValidity('');
    }

    // Validar o último nome
    if (lastName === '') {
        document.getElementById('lastName-register').setCustomValidity('Último nome é obrigatório.');
    } else if (!/^[a-zA-Z]+$/.test(lastName)) {
        document.getElementById('lastName-register').setCustomValidity('Último nome só pode conter letras (maiúsculas e minúsculas).');
    } else {
        document.getElementById('lastName-register').setCustomValidity('');
    }

    // Validar o número de telefone
    if (phone === '') {
        document.getElementById('phone-register').setCustomValidity('Número de telefone é obrigatório.');
    } else if (!/^\d{9}$/.test(phone)) {
        document.getElementById('phone-register').setCustomValidity('Por favor, insira um número de telefone válido com 9 dígitos.');
    } else {
        document.getElementById('phone-register').setCustomValidity('');
    }

    // Validar o URL da foto
    if (photoURL === '') {
        document.getElementById('photoURL-register').setCustomValidity('URL da foto é obrigatório.');
    } else if (!/^(http:\/\/|https:\/\/).+$/i.test(photoURL)) {
        document.getElementById('photoURL-register').setCustomValidity('Por favor, insira uma URL válida começando com "http://" ou "https://".');
    } else {
        document.getElementById('photoURL-register').setCustomValidity('');
    }

      // Retorna true se o formulário for válido, false caso contrário
document.getElementById('registrationForm').addEventListener('submit', function (event) {
    event.preventDefault();
    let newUser = createUserData();
// Send userData to the backend
fetch('http://localhost:8080/backend_war_exploded/rest/users/add', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(newUser)
})
    .then(response => {
        if (response.ok) {
            // Redirect to index.html after successful registration
            window.location.href = 'index.html';
        } else {
            throw new Error('Failed to register');
        }
    })
    .catch(error => {
        console.error(error);
        // Handle error
    });
});

    function createUserData() {
        let isFormValid = document.getElementById('registrationForm').checkValidity();
        console.log('Form is valid');

        let userData =null;

        if (isFormValid) {
            userData = {
                username: username,
                password: password,
                email: email,
                firstName: firstName,
                lastName: lastName,
                phone: phone,
                photoURL: photoURL
            };

        } else {
            document.getElementById('registrationForm').reportValidity();
            console.error('Form is not valid');
        }
        return userData;
    }

});
