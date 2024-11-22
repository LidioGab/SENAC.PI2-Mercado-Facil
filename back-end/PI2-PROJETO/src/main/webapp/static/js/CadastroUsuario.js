// idsFuncionarios.js

// Função para buscar e exibir a lista de IDs de funcionários sem usuário
function carregarFuncionariosSemUsuario() {
    fetch('http://localhost:8080/PI2-PROJETO/funcionariosSemUsuario')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);  // Verifique o que está sendo impresso no console do navegador
            const selectFuncionario = document.getElementById('idFuncionario');
            selectFuncionario.innerHTML = ''; // Limpa as opções anteriores

            // Adiciona as opções com os IDs ao <select>
            data.forEach(id => {
                const option = document.createElement('option');
                option.value = id;
                option.textContent = id; // Apenas o ID
                selectFuncionario.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao buscar a lista de funcionários:', error);
            document.getElementById('mensagem').textContent = 'Erro ao carregar funcionários.';
        });
}

// Função para lidar com o envio do formulário
document.getElementById('formCadastroUsuario').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário padrão

    // Exibir uma mensagem de sucesso temporária
    document.getElementById('mensagem').textContent = 'Usuário cadastrado com sucesso!';
});

// Chama a função para carregar os IDs quando a página carregar
window.onload = carregarFuncionariosSemUsuario; 
