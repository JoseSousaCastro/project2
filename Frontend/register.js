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