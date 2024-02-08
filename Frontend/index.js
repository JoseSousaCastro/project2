// ID do botão loginButton
document.getElementById('loginButton').addEventListener('click', function() {
    var loginValue = document.getElementById('username').value.trim();
    var passwordValue = document.getElementById('password').value.trim();
    if (loginValue === '' || passwordValue === '') {
        document.getElementById('warningMessage').innerText = 'Fill in your username and password';
    } else {
        // Limpa mensagem de erro
        document.getElementById('warningMessage').innerText = '';
        var user = document.getElementById("username").value;
            sessionStorage.setItem("username", user);
        // Redireciona para a página home.html
        window.location.href = 'home.html';
    }
});