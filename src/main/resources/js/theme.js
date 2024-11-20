export function toggleTheme() {
    const currentTheme = localStorage.getItem('theme') || 'light-theme';
    const newTheme = currentTheme === 'light-theme' ? 'dark-theme' : 'light-theme';

    // Update the stylesheet
    const themeStylesheet = document.getElementById('theme-stylesheet');
    themeStylesheet.href = `../css/${newTheme}.css`;

    // Save the preference to local storage
    localStorage.setItem('theme', newTheme);
}

export function applySavedTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light-theme';
    const themeStylesheet = document.getElementById('theme-stylesheet');
    themeStylesheet.href = `../css/${savedTheme}.css`;
}

// Attach event listener to the theme toggle button
document.addEventListener('DOMContentLoaded', () => {
    const themeToggleButton = document.getElementById('theme-toggle-button');
    themeToggleButton.addEventListener('click', toggleTheme);
});
