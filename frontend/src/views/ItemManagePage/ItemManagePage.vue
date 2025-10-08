<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="item-manage-page">
        <div class="container">
          <div class="page-header">
            <h1>商品管理</h1>
            <div class="header-actions">
              <el-button type="primary" @click="createNewItem">
                <PlusIcon class="button-icon"/>
                发布新商品
              </el-button>
            </div>
          </div>

          <div class="filters">
            <el-select v-model="selectedStatus" @change="loadItems" placeholder="商品状态">
              <el-option label="全部状态" :value="0"/>
              <el-option label="草稿" :value="1"/>
              <el-option label="在售" :value="2"/>
              <el-option label="已下架" :value="3"/>
            </el-select>
          </div>

          <div v-if="loading" class="loading-table">
            <el-skeleton animated>
              <template #template>
                <el-skeleton-item variant="text" style="width: 100%; height: 40px; margin-bottom: 10px;" v-for="i in 8"
                                  :key="i"/>
              </template>
            </el-skeleton>
          </div>

          <div v-else-if="items.length === 0" class="empty-state">
            <el-empty description="您还没有发布任何商品">
              <el-button type="primary" @click="createNewItem">发布第一个商品</el-button>
            </el-empty>
          </div>

          <div v-else class="items-table">
            <el-table :data="items" stripe style="width: 100%">
              <el-table-column width="120">
                <template #default="{ row }">
                  <el-image
                      :src="row.images[0]"
                      fit="cover"
                      style="width: 80px; height: 80px; border-radius: 4px;"
                  >
                    <template #placeholder>
                      <div class="image-placeholder">
                        <PhotoIcon class="placeholder-icon"/>
                      </div>
                    </template>
                    <template #error>
                      <div class="image-placeholder">
                        <PhotoIcon class="placeholder-icon"/>
                      </div>
                    </template>
                  </el-image>
                </template>
              </el-table-column>

              <el-table-column prop="name" label="商品名称" min-width="200">
                <template #default="{ row }">
                  <div class="item-name">
                    <span>{{ row.name }}</span>
                    <div class="item-tags">
                      <el-tag v-for="tag in row.tags" :key="tag" size="small" type="info">
                        {{ tag }}
                      </el-tag>
                    </div>
                  </div>
                </template>
              </el-table-column>

              <el-table-column prop="price" label="价格" width="120">
                <template #default="{ row }">
                  <span class="price">¥{{ formatPrice(row.price) }}</span>
                </template>
              </el-table-column>

              <el-table-column prop="favorites" label="收藏数" width="100">
                <template #default="{ row }">
                  <span class="favorites">
                    <HeartIcon class="meta-icon"/>
                    {{ row.favorites }}
                  </span>
                </template>
              </el-table-column>

              <el-table-column prop="stateCode" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="getStatusType(row.stateCode)">
                    {{ getStatusText(row.stateCode) }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column prop="createdAt" label="创建时间" width="120">
                <template #default="{ row }">
                  {{ formatTime(row.createdAt) }}
                </template>
              </el-table-column>

              <el-table-column label="操作" width="200" fixed="right">
                <template #default="{ row }">
                  <div class="item-actions">
                    <el-button size="small" @click="editItem(row.id)">编辑</el-button>
                    <el-button
                        v-if="row.stateCode === 2"
                        size="small"
                        type="warning"
                        @click="takeDownItem(row.id)"
                    >
                      下架
                    </el-button>
                    <el-button
                        v-if="row.stateCode === 3"
                        size="small"
                        type="success"
                        @click="putUpItem(row.id)"
                    >
                      上架
                    </el-button>
                    <el-button
                        size="small"
                        type="danger"
                        @click="deleteItem(row.id)"
                    >
                      删除
                    </el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 分页 -->
          <div v-if="items.length > 0" class="pagination">
            <el-pagination
                v-model:current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                layout="prev, pager, next, total"
                @current-change="handlePageChange"
            />
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {PlusIcon, PhotoIcon, HeartIcon} from "@heroicons/vue/24/outline";
import {
  getItemPageVO,
  deleteItem as deleteItemApi,
  takeDownItem as takeDownItemApi,
  putUpItem as putUpItemApi
} from "@/services/item.ts";
import type {ItemVO} from "@/types/item.d.ts";
import {useUserStore} from "@/stores/user";

const router = useRouter();
const userStore = useUserStore();

// 响应式数据
const items = ref<ItemVO[]>([]);
const loading = ref(false);
const selectedStatus = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);
const total = ref(0);

// 加载商品列表
const loadItems = async () => {
  try {
    loading.value = true;
    // 获取当前用户的商品列表
    const page = await getItemPageVO({
      sellerId: userStore.user.id,
      stateCode: selectedStatus.value,
      pageNo: currentPage.value,
      pageSize: pageSize.value,
    });
    items.value = page.records;
    total.value = page.total;
  } catch (error) {
    console.error('加载商品失败:', error);
    ElMessage.error('加载商品失败');
  } finally {
    loading.value = false;
  }
};

// 创建新商品
const createNewItem = () => {
  router.push('/item/new/edit');
};

// 编辑商品
const editItem = (itemId: number) => {
  router.push(`/item/${itemId}/edit`);
};

// 下架商品
const takeDownItem = async (itemId: number) => {
  try {
    await ElMessageBox.confirm(
        '确定要下架这个商品吗？下架后买家将无法购买。',
        '下架商品',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    await takeDownItemApi(itemId.toString());
    ElMessage.success('商品已下架');
    loadItems();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('下架失败');
    }
  }
};

// 上架商品
const putUpItem = async (itemId: number) => {
  try {
    await putUpItemApi(itemId.toString());
    ElMessage.success('商品已上架');
    loadItems();
  } catch (error) {
    ElMessage.error('上架失败');
  }
};

// 删除商品
const deleteItem = async (itemId: number) => {
  try {
    await ElMessageBox.confirm(
        '确定要删除这个商品吗？删除后无法恢复。',
        '删除商品',
        {
          confirmButtonText: '确定删除',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    await deleteItemApi(itemId.toString());
    ElMessage.success('商品已删除');
    loadItems();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败');
    }
  }
};

// 分页处理
const handlePageChange = (page: number) => {
  currentPage.value = page;
  loadItems();
};

// 获取状态类型
const getStatusType = (stateCode: number) => {
  switch (stateCode) {
    case 1:
      return 'info';
    case 2:
      return 'success';
    case 3:
      return 'warning';
    default:
      return '';
  }
};

// 获取状态文本
const getStatusText = (stateCode: number) => {
  switch (stateCode) {
    case 1:
      return '草稿';
    case 2:
      return '在售';
    case 3:
      return '已下架';
    default:
      return '未知';
  }
};

// 格式化价格
const formatPrice = (price: number) => {
  return price.toFixed(2);
};

// 格式化时间
const formatTime = (time: string) => {
  return new Date(time).toLocaleDateString();
};

// 页面加载时获取数据
onMounted(() => {
  loadItems();
});
</script>

<style scoped>
.item-manage-page {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding: 24px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.page-header h1 {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.filters {
  margin-bottom: 20px;
  padding: 16px 24px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.loading-table {
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.empty-state {
  background: white;
  border-radius: 12px;
  padding: 60px 20px;
  text-align: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.items-table {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  background-color: #f5f5f5;
  color: #c0c4cc;
  border-radius: 4px;
}

.placeholder-icon {
  width: 24px;
  height: 24px;
  color: #c0c4cc;
}

.item-name {
  line-height: 1.4;
}

.item-tags {
  margin-top: 8px;
  display: flex;
  gap: 4px;
  flex-wrap: wrap;
}

.price {
  font-weight: 600;
  color: #e74c3c;
}

.favorites {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #7f8c8d;
}

.item-actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

.button-icon {
  width: 16px;
  height: 16px;
  margin-right: 4px;
}

.meta-icon {
  width: 16px;
  height: 16px;
}
</style>
