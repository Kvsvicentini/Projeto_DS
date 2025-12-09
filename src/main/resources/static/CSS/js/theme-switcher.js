document.addEventListener('DOMContentLoaded', () => {
    const themeToggleButton = document.getElementById('theme-toggle');
    const currentTheme = localStorage.getItem('theme'); // Obtém a preferência salva

    // 1. Aplica o tema salvo ao carregar a página
    if (currentTheme) {
        document.body.classList.add(currentTheme);
        if (themeToggleButton) {
            themeToggleButton.textContent = currentTheme === 'dark-mode' ? '🌞 Tema Claro' : '🌙 Tema Escuro';
        }
    } else {
        // Define o texto inicial se não houver preferência salva
        if (themeToggleButton) {
             themeToggleButton.textContent = '🌙 Tema Escuro';
        }
    }

    // 2. Função de alternância ao clicar no botão
    if (themeToggleButton) {
        themeToggleButton.addEventListener('click', () => {
            const isDarkMode = document.body.classList.toggle('dark-mode');
            
            if (isDarkMode) {
                localStorage.setItem('theme', 'dark-mode');
                themeToggleButton.textContent = '🌞 Tema Claro';
            } else {
                localStorage.removeItem('theme'); // Limpa para voltar ao padrão (claro)
                themeToggleButton.textContent = '🌙 Tema Escuro';
            }
        });
    }
});