document.getElementById('CPF').addEventListener('input', function(e) {
    var value = e.target.value;
    var CPFPattern = value.replace(/\D/g, '') // Remove qualquer coisa que não seja número
                          .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona ponto após o terceiro dígito
                          .replace(/(\d{3})(\d)/, '$1.$2') // Adiciona ponto após o sexto dígito
                          .replace(/(\d{3})(\d)/, '$1-$2') // Adiciona traço após o nono dígito
                          .replace(/(-\d{2})\d+?$/, '$1'); // Impede entrada de mais de 11 dígitos
    e.target.value = CPFPattern;
  });

  document.getElementById('opcaoInput').addEventListener('keydown', function(e) {
    if (e.key !== "Tab" && e.key !== "ArrowDown") {
      e.preventDefault();
    }
});