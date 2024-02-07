// Função para remover o placeholder quando há clique no input
function removePlaceholder(input) {
    if (input.getAttribute("data-original-placeholder")) {
        input.placeholder = "";
        input.addEventListener("blur", function () {
            if (!input.value.trim()) {
                input.placeholder = input.getAttribute("data-original-placeholder");
            }
        });
    }
}
// Voltar a colocar os placeholders originais quando, após o clique, o input fica vazio 
document.addEventListener("DOMContentLoaded", function () {
    var inputs = document.querySelectorAll('input[placeholder]');
    inputs.forEach(function (input) {
        input.setAttribute("data-original-placeholder", input.getAttribute("placeholder"));
        input.addEventListener("focus", function () {
            removePlaceholder(input);
        });
    });
});

// ID do botão loginButton
document.getElementById('loginButton').addEventListener('click', function() {
    var loginValue = document.getElementById('login').value.trim();
    var passwordValue = document.getElementById('password').value.trim();
    if (loginValue === '' || passwordValue === '') {
        document.getElementById('warningMessage').innerText = 'Fill in your username and password';
    } else {
        // Limpa mensagem de erro
        document.getElementById('warningMessage').innerText = '';
        var user = document.getElementById("login").value;
            sessionStorage.setItem("login", user);
        // Redireciona para a página home.html
        window.location.href = 'home.html';
    }
});