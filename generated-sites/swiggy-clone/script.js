// Swiggy Clone JavaScript

document.addEventListener('DOMContentLoaded', function() {
  // Search functionality
  const searchInput = document.querySelector('.search-bar input');
  const searchButton = document.querySelector('.search-bar button');
  
  if (searchButton) {
    searchButton.addEventListener('click', function() {
      const query = searchInput.value.trim();
      if (query) {
        // Simulate search
        alert('Searching for: ' + query);
        // In real app: redirect to search results page
      } else {
        alert('Please enter a search term');
      }
    });
  }
  
  // Allow Enter key to trigger search
  if (searchInput) {
    searchInput.addEventListener('keypress', function(e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        searchButton.click();
      }
    });
  }
  
  // Category hover effect enhancement
  const categoryItems = document.querySelectorAll('.category-item');
  categoryItems.forEach(item => {
    item.addEventListener('mouseenter', function() {
      this.style.transform = 'scale(1.05)';
    });
    
    item.addEventListener('mouseleave', function() {
      this.style.transform = 'scale(1)';
    });
  });
  
  // Restaurant card animations
  const restaurantCards = document.querySelectorAll('.restaurant-card');
  restaurantCards.forEach(card => {
    card.addEventListener('click', function() {
      // Simulate clicking on restaurant
      const restaurantName = this.querySelector('h3').textContent;
      alert('Opening restaurant: ' + restaurantName);
      // In real app: navigate to restaurant page
    });
  });
  
  // Simple cart functionality (demo)
  let cartItems = 0;
  
  // Example: Add to cart button (would be in restaurant card in real implementation)
  // This is just to demonstrate the concept
  
  // Smooth scrolling for anchor links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      document.querySelector(this.getAttribute('href')).scrollIntoView({
        behavior: 'smooth'
      });
    });
  });
  
  // Footer animation (optional)
  const footer = document.querySelector('footer');
  if (footer) {
    footer.style.opacity = '0';
    footer.style.transform = 'translateY(20px)';
    footer.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
    
    // Trigger animation after slight delay
    setTimeout(() => {
      footer.style.opacity = '1';
      footer.style.transform = 'translateY(0)';
    }, 500);
  }
});

// Simple helper function for debouncing (useful for search)
function debounce(func, delay) {
  let timeoutId;
  return function (...args) {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => func.apply(this, args), delay);
  };
}