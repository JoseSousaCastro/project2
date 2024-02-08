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
    var isFormValid = document.getElementById('registrationForm').checkValidity();

// Lógica de registro com localStorage
if (isFormValid) {
    // Cria um objeto com os dados do usuário
    var userData = {
        username: username,
        password: password,
        email: email,
        firstName: firstName,
        lastName: lastName,
        phone: phone,
        photoURL: photoURL
    };

    // Verifica se já existem dados no localStorage
    var existingData = localStorage.getItem('userData');

    if (existingData) {
        // Se houver dados existentes, converte-os de volta para um objeto JavaScript
        var existingDataObj = JSON.parse(existingData);

        // Adiciona os novos dados ao objeto existente
        existingDataObj.push(userData);

        // Armazena o objeto atualizado no localStorage
        localStorage.setItem('userData', JSON.stringify(existingDataObj));
    } else {
        // Se não houver dados existentes, cria um novo array com os dados do usuário
        var newDataArray = [userData];

        // Armazena o novo array no localStorage
        localStorage.setItem('userData', JSON.stringify(newDataArray));
    }

    // Redireciona para index.html após o registro bem-sucedido
    window.location.href = 'index.html';
}
});
