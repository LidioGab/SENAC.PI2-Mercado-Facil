function validateForm() {
    const fornecedor = document.getElementById("fornecedor").value;
    const categoria = document.getElementById("categoria").value;
    const status = document.getElementById("status").value;
    const cnpj = document.getElementById("email").value; 

    if (!fornecedor) {
        alert("Por favor, selecione um fornecedor.");
        return false;
    }
    if (!categoria) {
        alert("Por favor, selecione uma categoria.");
        return false;
    }
    if (!status) {
        alert("Por favor, selecione o status.");
        return false;
    }
    if (!cnpj) {
        alert("Por favor, preencha o CNPJ.");
        return false;
    }

    return true;
}
