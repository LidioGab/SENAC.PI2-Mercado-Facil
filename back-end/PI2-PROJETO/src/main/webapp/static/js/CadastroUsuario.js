// Função para buscar e exibir a lista de IDs de funcionários sem usuário
function carregarFuncionariosSemUsuario() {
    fetch('http://localhost:8080/PI2-PROJETO/funcionariosSemUsuario')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar funcionários');
            }
            return response.json();
        })
        .then(data => {
            const selectFuncionario = document.getElementById('idFuncionario');
            selectFuncionario.innerHTML = '<option value="">Selecione um funcionário</option>'; // Resetar opções
            data.forEach(id => {
                const option = document.createElement('option');
                option.value = id;
                option.textContent = `ID: ${id}`;
                selectFuncionario.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar funcionários:', error);
            const mensagem = document.getElementById('mensagem');
            mensagem.textContent = 'Erro ao carregar funcionários.';
            mensagem.style.color = 'red';
        });
}

window.onload = carregarFuncionariosSemUsuario;

document.getElementById("formCadastroUsuario").addEventListener("submit", function(event) {
    event.preventDefault();  // Impede o envio padrão do formulário

    // Cria um objeto FormData com os dados do formulário
    var formData = new FormData(this);
	alert(formData)
    // Envia os dados via POST usando a API Fetch
    fetch("http://localhost:8080/PI2-PROJETO/cadastroUsuario", {
        method: "POST",
        body: formData
    })
    .then(response => {
        console.log("Response Status:", response.status);
        console.log("Response OK:", response.ok);
        if (response.ok) {
            return response.text();
        } else {
            throw new Error("Erro no cadastro: " + response.statusText);
        }
    })
    .then(data => {
        console.log("Response Data:", data);
        alert("Usuário cadastrado com sucesso!");
        window.location.href = "cadastroSucesso.html";  // Redireciona para a página de sucesso
    })
    .catch(error => {
        console.error('Erro:', error);
        alert("Erro: " + error.message);
    });
});