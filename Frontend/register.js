// validation.js

document.addEventListener("DOMContentLoaded", function () {
    var registerButton = document.getElementById('register-button');

    registerButton.addEventListener('click', function () {
        validateAllFields();
    });

    function validateAllFields() {
        var isValid = true;

        if (!validateField('username-register', /^[a-zA-Z0-9._-]+$/, 'O username só pode conter letras (maiúsculas e minúsculas), dígitos, ".", "-", e "_", sem espaços.')) {
            isValid = false;
        }

        if (!validateField('password-register', /^[a-zA-Z0-9!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+$/, 'A password só pode conter letras (maiúsculas e minúsculas), dígitos e caracteres especiais, sem espaços.')) {
            isValid = false;
        }

        if (!validateField('passwordConfirm-register', '', 'As senhas não coincidem.', validatePasswordConfirmation)) {
            isValid = false;
        }

        if (!validateField('email-register', /^[^\s@]+@[^\s@]+\.[^\s@]+$/, 'Por favor, insira um endereço de e-mail válido.')) {
            isValid = false;
        }

        if (!validateField('firstName-register', /^[a-zA-Z]+$/, 'O primeiro nome só pode conter letras (maiúsculas e minúsculas).')) {
            isValid = false;
        }

        if (!validateField('lastName-register', /^[a-zA-Z]+$/, 'O último nome só pode conter letras (maiúsculas e minúsculas).')) {
            isValid = false;
        }

        if (!validateField('phone-register', /^\d{9}$/, 'Por favor, insira um número de telefone válido com 9 dígitos.')) {
            isValid = false;
        }

        if (!validateField('photoURL-register', /^(http:\/\/|https:\/\/).+$/i, 'Por favor, insira uma URL válida começando com "http://" ou "https://".')) {
            isValid = false;
        }

        // Se todos os campos forem válidos, você pode prosseguir com o registro
        if (isValid) {
            alert('Todos os campos estão preenchidos corretamente. Realizar registro!');
            // Adicione aqui a lógica de registro, se necessário
        }
    }

    function validateField(inputId, pattern, errorMessage, additionalValidationFunction) {
        var inputElement = document.getElementById(inputId);
        var inputValue = inputElement.value.trim();

        var isValid = true;

        // Verifica se o campo atende ao padrão
        if (pattern && !pattern.test(inputValue)) {
            isValid = false;
        }

        // Se houver uma função de validação adicional, executa-a
        if (additionalValidationFunction && !additionalValidationFunction()) {
            isValid = false;
        }

        // Se o campo não for válido, exibe a mensagem de alerta
        if (!isValid) {
            alert(errorMessage);
            inputElement.style.border = '2px solid red';
        } else {
            // Se o campo for válido, remove qualquer estilo de borda anterior
            inputElement.style.border = '';
        }

        return isValid;
    }

    function validatePasswordConfirmation() {
        var passwordInput = document.getElementById('password-register');
        var passwordConfirmInput = document.getElementById('passwordConfirm-register');

        return passwordInput.value === passwordConfirmInput.value;
    }
});
