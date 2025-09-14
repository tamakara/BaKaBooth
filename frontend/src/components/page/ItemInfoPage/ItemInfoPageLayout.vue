<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout class="item-page-layout">
        <div class="main-content">
          <div class="left-column">
            <div class="left-panel">
              <div class="item-image-section">
                <slot name="item-image"/>
              </div>
              <div class="item-description-section">
                <slot name="item-text"/>
              </div>
            </div>
          </div>
          <div class="right-column">
            <div class="right-panel">
              <div class="item-header-section">
                <slot name="item-header"/>
              </div>

              <div class="item-variations-section">
                <slot name="item-variations"/>
              </div>

              <div class="item-info-section">
                <slot name="item-terms"/>
              </div>
            </div>
          </div>
        </div>

        <div class="seller-info-section">
          <div class="seller-info-wrapper">
            <slot name="seller-info"/>
          </div>
        </div>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
</script>

<style scoped>
/* === 布局样式 === */
.item-page-layout {
  padding: 0;
}

.main-content {
  width: 100%;
  display: flex;
  background: white;
  border-radius: 8px 8px 0 0;
  box-shadow: none;
  overflow: hidden;
  margin-bottom: 0;
}

.left-column {
  flex: 0 0 550px; /* 固定左侧宽度，不收缩不放大 */
  min-width: 550px;
}

.right-column {
  flex: 1; /* 右侧占剩余所有空间 */
  min-width: 0; /* 允许内容收缩 */
}

.left-panel {
  background: #fafafa;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.right-panel {
  padding: 24px;
  background: white;
  border-left: 1px solid #e4e7ed;
  flex: 1;
  min-width: 0;
}

.item-description-section {
  background: white;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  flex: 1;
  display: flex;
  flex-direction: column;
}

.item-header-section {
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.item-variations-section {
  margin-bottom: 24px;
}

.item-info-section {
  background: #f8f9fa;
  border-radius: 8px;
}

.seller-info-section {
  background: white;
  border-radius: 0 0 8px 8px;
  margin-top: 0;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  border-top: 1px solid #e4e7ed;
  padding: 0;
  width: 100%;
}

.seller-info-wrapper {
  padding: 0;
  width: 100%;
}

/* 响应式处理 - 集成ColLayout的响应式逻辑 */
@media (max-width: 1200px) {
  .main-content {
    flex-direction: column;
  }

  .left-column {
    flex: none;
    width: 100%;
    min-width: 0;
  }

  .right-column {
    flex: none;
    width: 100%;
  }

  .left-panel,
  .right-panel {
    padding: 16px;
  }

  .item-description-section,
  .item-info-section {
    padding: 16px;
  }

  .seller-info-wrapper {
    padding: 16px;
  }
}

/* === 商品内容样式 === */
/* 商品图片区域 */
:deep(.item-image-wrapper) {
  height: 550px;
  background-color: #ebedf2;
  flex-shrink: 0;
}

:deep(.loading-placeholder) {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 商品描述 */
:deep(.item-text) {
  font-size: 16px;
  line-height: 1.6;
  color: #4a5568;
  white-space: pre-wrap;
  word-wrap: break-word;
  max-width: 100%;
  box-sizing: border-box;
  flex: 1;
  padding: 20px;
}

/* 商品头部区域 - 保持朴素样式 */
:deep(.item-title) {
  font-size: 24px;
  font-weight: 700;
  color: #2d3748;
  margin: 0 0 20px 0;
  padding: 0;
}

/* 按钮区域 - 简化样式 */
:deep(.item-buttons) {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

:deep(.item-buttons-left) {
  flex: 1;
}

:deep(.item-buttons-right) {
  display: flex;
  gap: 8px;
}

:deep(.item-button-favorite) {
  min-width: 120px;
  height: 44px;
  border: 2px solid #e4e7ed;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

:deep(.favorite-text) {
  font-size: 15px;
  font-weight: 600;
}

:deep(.favorite-count) {
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 600;
  min-width: 20px;
  text-align: center;
}

:deep(.item-button-action) {
  width: 44px;
  height: 44px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 商品规格 - 添加简洁边框 */
:deep(.item-variations) {
  margin-bottom: 16px;
}

:deep(.variation-container) {
  background: #ffffff;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 16px;
}

:deep(.variation-container:last-child) {
  margin-bottom: 0;
}

:deep(.variation-header) {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

:deep(.variation-name) {
  font-size: 18px;
  font-weight: 600;
  margin: 0;
  color: #333;
}

:deep(.variation-price) {
  display: flex;
  align-items: baseline;
  gap: 4px;
}

:deep(.price-currency) {
  font-size: 16px;
  color: #e53e3e;
  font-weight: 600;
}

:deep(.price-amount) {
  font-size: 22px;
  font-weight: 700;
  color: #e53e3e;
}

:deep(.variation-details) {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

:deep(.variation-stock) {
  font-size: 14px;
}

:deep(.stock-label) {
  color: #666;
  font-weight: 500;
}

:deep(.stock-count) {
  font-weight: 600;
}

:deep(.low-stock) {
  color: #f56500;
}

:deep(.out-of-stock) {
  color: #e53e3e;
}

:deep(.buy-now-button) {
  min-width: 120px;
  height: 40px;
  font-weight: 600;
}

:deep(.button-icon) {
  width: 16px;
  height: 16px;
  margin-right: 6px;
}

/* 无规格信息提示 */
:deep(.no-variations) {
  text-align: center;
  padding: 40px 20px;
  color: #9ca3af;
  font-size: 16px;
  background: #f9fafb;
  border: 1px dashed #d1d5db;
  border-radius: 8px;
  margin: 16px 0;
}

/* 商品服务信息样式 */
:deep(.item-delivery),
:deep(.item-service),
:deep(.item-tags) {
  background: white;
  border-radius: 8px;
  padding: 16px;
  border: 1px solid #f0f0f0;
}

:deep(.item-terms-title) {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 12px;
}

:deep(.item-terms-content) {
  padding-left: 14px;
}

:deep(.info-item) {
  display: flex;
  margin-bottom: 8px;
  font-size: 14px;
}

:deep(.info-label) {
  color: #666;
  min-width: 80px;
  font-weight: 500;
}

:deep(.tags-container) {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

:deep(.tag-item) {
  margin: 0;
}

:deep(.no-tags) {
  color: #999;
  font-style: italic;
}

/* 卖家信息区域 - 优化边框和图标 */
:deep(.seller-info-container) {
  width: 100%;
  padding: 0;
}

:deep(.seller-content) {
  width: 100%;
}

:deep(.seller-card) {
  display: flex;
  background: #ffffff;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

:deep(.seller-card:hover) {
  background: #f8f9fa;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
  transform: translateY(-1px);
}

:deep(.seller-skeleton) {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
}

:deep(.seller-skeleton-text) {
  flex: 1;
}

:deep(.seller-main) {
  display: flex;
  padding: 20px 24px;
  flex: 1;
  gap: 20px;
  align-items: flex-start;
}

:deep(.seller-avatar-section) {
  position: relative;
  flex-shrink: 0;
}

:deep(.seller-avatar) {
  border: 3px solid #fff;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
}

:deep(.seller-status-badge) {
  position: absolute;
  top: -3px;
  right: -3px;
  background: linear-gradient(135deg, #52c41a, #73d13d);
  color: white;
  padding: 3px 8px;
  font-size: 11px;
  font-weight: 600;
  border: 2px solid white;
  border-radius: 12px;
  display: flex;
  align-items: center;
  gap: 3px;
  box-shadow: 0 2px 8px rgba(82, 196, 26, 0.3);
}

:deep(.seller-info-main) {
  flex: 1;
  min-width: 0;
}

:deep(.seller-header-info) {
  margin-bottom: 14px;
}

:deep(.seller-name) {
  font-size: 19px;
  font-weight: 700;
  margin: 0 0 8px 0;
  color: #1a202c;
}

:deep(.seller-name-row) {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

:deep(.seller-actions) {
  display: flex;
  gap: 12px;
  justify-content: flex-start;
}

:deep(.follow-btn),
:deep(.contact-btn) {
  height: 36px;
  padding: 0 16px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  font-size: 13px;
  font-weight: 600;
  min-width: auto;
  white-space: nowrap;
}

:deep(.follow-btn) {
  background: linear-gradient(135deg, #e6f7ff, #bae7ff);
  border: 1px solid #91d5ff;
  color: #1890ff;
}

:deep(.follow-btn:hover) {
  background: linear-gradient(135deg, #bae7ff, #91d5ff);
  border-color: #40a9ff;
  color: #096dd9;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(24, 144, 255, 0.3);
}

:deep(.contact-btn) {
  background: linear-gradient(135deg, #f6ffed, #d9f7be);
  border: 1px solid #b7eb8f;
  color: #52c41a;
}

:deep(.contact-btn:hover) {
  background: linear-gradient(135deg, #d9f7be, #b7eb8f);
  border-color: #73d13d;
  color: #389e0d;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(82, 196, 26, 0.3);
}

:deep(.seller-meta) {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}

:deep(.meta-item) {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #666;
  font-size: 13px;
  background: rgba(0, 0, 0, 0.03);
  padding: 4px 8px;
  border-radius: 6px;
}

:deep(.seller-stats) {
  display: flex;
  gap: 20px;
  margin-bottom: 14px;
  padding: 12px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
}

:deep(.stat-item) {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
}

:deep(.stat-number) {
  font-size: 17px;
  font-weight: 800;
  color: #1890ff;
  margin-bottom: 2px;
}

:deep(.stat-label) {
  font-size: 12px;
  color: #8c8c8c;
  font-weight: 600;
  text-transform: uppercase;
}

:deep(.stat-divider) {
  width: 1px;
  height: 28px;
  background: #f0f0f0;
}

:deep(.seller-description) {
  background: linear-gradient(135deg, rgba(24, 144, 255, 0.06), rgba(24, 144, 255, 0.03));
  border-left: 3px solid #1890ff;
  padding: 12px 16px;
  border-radius: 8px;
  margin: 0;
  font-size: 14px;
  line-height: 1.5;
  color: #4a5568;
  box-shadow: 0 1px 3px rgba(24, 144, 255, 0.1);
}

:deep(.seller-description p) {
  margin: 0;
}

/* 状态样式 */
:deep(.starred) {
  color: #f56500;
}

:deep(.favorited) {
  color: #e53e3e;
}

/* 响应式设计 */
@media (max-width: 768px) {


  :deep(.item-title) {
    font-size: 20px;
    padding: 16px;
  }

  :deep(.item-text) {
    padding: 16px;
  }

  :deep(.item-buttons) {
    flex-direction: column;
    gap: 12px;
    padding: 16px;
  }

  :deep(.variation-container),
  :deep(.item-delivery),
  :deep(.item-service),
  :deep(.item-tags) {
    padding: 16px;
  }

  :deep(.seller-card) {
    flex-direction: column;
  }

  :deep(.seller-main) {
    padding: 20px;
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  :deep(.seller-actions) {
    flex-direction: row;
    border-left: none;
    border-top: 1px solid #e4e7ed;
    padding: 16px 20px;
  }

  :deep(.follow-btn),
  :deep(.contact-btn) {
    flex: 1;
  }
}
</style>
