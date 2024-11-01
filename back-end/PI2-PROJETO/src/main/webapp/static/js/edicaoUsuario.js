
//Validação CPF

document.getElementById('cpf').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // Remove tudo que não é dígito
    if (value.length > 11) value = value.slice(0, 11); // Limita a 11 dígitos

    // Adiciona a máscara
    const formattedValue = value
        .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona o primeiro ponto
        .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona o segundo ponto
        .replace(/(\d{3})(\d)/, '$1-$2'); // Adiciona o hífen

    e.target.value = formattedValue; // Atualiza o valor do campo
});
