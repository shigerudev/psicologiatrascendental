document.addEventListener('DOMContentLoaded', () => {
    initStars();
    initParallax();
    initTheme();
    initScrollReveal();
    initNavbar();
    initSmoothScroll();
});

// ========== Animated stars background ==========
function initStars() {
    const starsContainer = ['stars', 'stars2', 'stars3'];
    const counts = [100, 60, 40];
    const sizes = [1, 2, 3];

    starsContainer.forEach((id, i) => {
        const container = document.getElementById(id);
        if (!container) return;

        for (let j = 0; j < counts[i]; j++) {
            const star = document.createElement('div');
            star.className = 'star';
            star.style.cssText = `
                position: absolute;
                width: ${sizes[i]}px;
                height: ${sizes[i]}px;
                background: rgba(201, 160, 220, ${0.2 + Math.random() * 0.6});
                border-radius: 50%;
                left: ${Math.random() * 100}%;
                top: ${Math.random() * 100}%;
                animation: twinkle ${2 + Math.random() * 4}s ease-in-out infinite;
                animation-delay: ${Math.random() * 3}s;
                transition: transform 0.3s ease-out;
            `;
            container.appendChild(star);
        }
    });

    if (!document.getElementById('star-styles')) {
        const style = document.createElement('style');
        style.id = 'star-styles';
        style.textContent = `
            @keyframes twinkle {
                0%, 100% { opacity: 0.2; }
                50% { opacity: 1; }
            }
        `;
        document.head.appendChild(style);
    }
}

// ========== Mouse parallax (interactive background) ==========
function initParallax() {
    const layers = ['stars', 'stars2', 'stars3'];
    let mouseX = 0, mouseY = 0;
    let targetX = 0, targetY = 0;

    document.addEventListener('mousemove', (e) => {
        mouseX = (e.clientX / window.innerWidth - 0.5) * 2;
        mouseY = (e.clientY / window.innerHeight - 0.5) * 2;
    });

    function animate() {
        const theme = document.documentElement.getAttribute('data-theme');
        const smoothing = theme === 'light' ? 0.02 : 0.04;
        const baseFactor = theme === 'light' ? 6 : 15;

        targetX += (mouseX - targetX) * smoothing;
        targetY += (mouseY - targetY) * smoothing;

        layers.forEach((id, i) => {
            const el = document.getElementById(id);
            if (!el) return;
            const factor = (i + 1) * baseFactor;
            const x = targetX * factor;
            const y = targetY * factor;
            el.style.transform = `translate(${x}px, ${y}px)`;
        });

        requestAnimationFrame(animate);
    }
    animate();
}

// ========== Theme toggle ==========
function initTheme() {
    const btn = document.getElementById('theme-toggle');
    const html = document.documentElement;
    const sunIcon = document.querySelector('.sun-icon');
    const moonIcon = document.querySelector('.moon-icon');

    const saved = localStorage.getItem('theme');
    if (saved) setTheme(saved);

    btn?.addEventListener('click', () => {
        const current = html.getAttribute('data-theme');
        setTheme(current === 'dark' ? 'light' : 'dark');
    });

    function setTheme(theme) {
        html.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);
        if (sunIcon && moonIcon) {
            sunIcon.style.display = theme === 'dark' ? 'block' : 'none';
            moonIcon.style.display = theme === 'dark' ? 'none' : 'block';
        }
    }
}

// ========== Scroll reveal ==========
function initScrollReveal() {
    const reveals = document.querySelectorAll('.reveal');
    const threshold = 0.15;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, { threshold, rootMargin: '0px 0px -50px 0px' });

    reveals.forEach(el => observer.observe(el));
}

// ========== Navbar scroll effect ==========
function initNavbar() {
    const navbar = document.querySelector('.navbar');
    window.addEventListener('scroll', () => {
        const theme = document.documentElement.getAttribute('data-theme');
        const shadow = theme === 'light' 
            ? '0 4px 24px rgba(126, 34, 206, 0.08)' 
            : '0 4px 30px rgba(0,0,0,0.2)';
        if (window.scrollY > 80) {
            navbar?.style.setProperty('box-shadow', shadow);
        } else {
            navbar?.style.removeProperty('box-shadow');
        }
    });
}

// ========== Smooth scroll ==========
function initSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href === '#') return;
            const target = document.querySelector(href);
            if (target) {
                e.preventDefault();
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
}
