<template>
  <div class="base-layout">
    <header class="header">
      <slot name="header"></slot>
    </header>
    <main class="main">
      <slot name="main"></slot>
    </main>
    <footer class="footer">
      <slot name="footer">
        <div class="footer-content">
          <p>&copy; 2025 BaKaBooth 二手交易平台. All rights reserved.</p>
        </div>
      </slot>
    </footer>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.base-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.main {
  flex: 1;
  background-color: #f5f5f5;
}

.footer {
  background: #2c3e50;
  color: white;
  padding: 20px 0;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
  padding: 0 20px;
}
</style>
