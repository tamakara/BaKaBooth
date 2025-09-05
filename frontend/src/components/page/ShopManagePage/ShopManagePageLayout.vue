<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <div class="page-title">店铺管理</div>
        </template>

        <!-- 店铺信息头部区域 -->
        <div class="header-section">
          <div class="header-card">
            <slot name="dashboard-header-left"/>
          </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="content-section">
          <div class="content-left">
            <slot name="dashboard-pane-left"/>
          </div>
          <div class="content-right">
            <slot name="dashboard-pane-right"/>
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
/* 页面标题 */
.page-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  padding: 20px 0;
  margin: 0;
}

/* 头部区域 */
.header-section {
  width: 1200px;
  max-width: 100%;
  margin: 0 auto 24px auto;
}

.header-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  border: 1px solid #e5e7eb;
  transition: all 0.2s ease;
}

.header-card:hover {
  border-color: #d1d5db;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

/* 内容区域 */
.content-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
  align-items: start;
  width: 1200px;
  max-width: 100%;
  margin: 0 auto;
}

.content-left,
.content-right {
  min-width: 0;
}

/* 通用卡片样式 */
:deep(.card) {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  border: 1px solid #e5e7eb;
  transition: all 0.2s ease;
}

:deep(.card:hover) {
  border-color: #d1d5db;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

/* 卡片标题 */
:deep(.card-title) {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 20px 0;
  display: flex;
  align-items: center;
  gap: 10px;
}

:deep(.card-icon) {
  width: 20px;
  height: 20px;
  color: #3b82f6;
}

/* 统计网格 */
:deep(.stats-grid) {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

:deep(.stat-item) {
  background: #f8fafc;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
  border: 1px solid #e2e8f0;
  transition: all 0.2s ease;
}

:deep(.stat-item:hover) {
  background: #f1f5f9;
  border-color: #cbd5e1;
  transform: translateY(-1px);
}

:deep(.stat-value) {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 6px;
}

:deep(.stat-label) {
  font-size: 13px;
  color: #64748b;
  font-weight: 500;
}

/* 导航列表 */
:deep(.nav-list) {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

:deep(.nav-item) {
  display: flex;
  align-items: center;
  padding: 18px;
  background: #f8fafc;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
  cursor: pointer;
  transition: all 0.2s ease;
  gap: 14px;
}

:deep(.nav-item:hover) {
  background: #f1f5f9;
  border-color: #3b82f6;
  transform: translateX(2px);
}

:deep(.nav-icon) {
  width: 40px;
  height: 40px;
  background: #3b82f6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

:deep(.nav-content) {
  flex: 1;
  min-width: 0;
}

:deep(.nav-title) {
  font-size: 15px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 3px;
}

:deep(.nav-desc) {
  font-size: 13px;
  color: #64748b;
  line-height: 1.4;
}

:deep(.nav-arrow) {
  color: #9ca3af;
  transition: all 0.2s ease;
  flex-shrink: 0;
  width: 18px;
  height: 18px;
}

:deep(.nav-item:hover .nav-arrow) {
  color: #3b82f6;
  transform: translateX(3px);
}

/* 按钮样式 */
:deep(.el-button) {
  height: 36px;
  padding: 0 16px;
  font-size: 13px;
  border-radius: 6px;
  font-weight: 500;
}

:deep(.el-button--large) {
  height: 40px;
  padding: 0 20px;
  font-size: 14px;
}

:deep(.el-button--small) {
  height: 28px;
  padding: 0 12px;
  font-size: 12px;
}

:deep(.el-icon) {
  width: 16px;
  height: 16px;
}

:deep(.el-button .el-icon) {
  margin-right: 4px;
}

/* 标签样式 */
:deep(.el-tag) {
  height: 24px;
  padding: 0 8px;
  font-size: 12px;
  border-radius: 4px;
  border: none;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .content-section {
    grid-template-columns: 1fr 350px;
    gap: 24px;
  }
}

@media (max-width: 968px) {
  .content-section {
    grid-template-columns: 1fr;
    gap: 24px;
  }

  .content-right {
    order: -1;
  }
}

@media (max-width: 768px) {
  .page-title {
    font-size: 20px;
    padding: 16px 0;
  }

  .content-section {
    gap: 20px;
  }

  :deep(.card) {
    padding: 16px;
    margin-bottom: 16px;
  }

  :deep(.stats-grid) {
    grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
    gap: 12px;
  }

  :deep(.stat-item) {
    padding: 12px;
  }

  :deep(.nav-item) {
    padding: 12px;
  }

  :deep(.nav-icon) {
    width: 32px;
    height: 32px;
  }
}

@media (max-width: 480px) {
  :deep(.stats-grid) {
    grid-template-columns: 1fr 1fr;
  }

  :deep(.stat-value) {
    font-size: 18px;
  }

  :deep(.card-title) {
    font-size: 15px;
  }
}
</style>