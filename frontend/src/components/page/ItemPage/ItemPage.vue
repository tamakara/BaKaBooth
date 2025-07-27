<template>
  <ItemPageLayout>

    <template #item-image>
      <div class="item-image-wrapper">
        <el-image
            fit="contain"
            src="https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg"
            style="width: 100%;height: 100%;"
        />
      </div>

    </template>

    <template #item-text>
      <div class="item-text">
        {{ item.text }}
      </div>

    </template>

    <template #item-header>
      <div class="shop-avatar-name" @click="console.log('fuck')">
        <el-avatar :size="24">Y</el-avatar>
        <div class="shop-name">{{ item.shop.name }}</div>
      </div>
      <div class="item-title">{{ item.title }}</div>
    </template>

  </ItemPageLayout>
</template>

<script setup lang="ts">
import ItemPageLayout from './ItemPageLayout.vue';

import {useRoute} from 'vue-router';

const route = useRoute();

console.log(route.params.id)

const item = {
  title: ' 【体験版ゲームデータ】『パーガトリー・ブルー』CF用体験版',
  text: '   美遊・エーデルフェルト Miyu Edelfelt Fate/kaleid liner プリズマ☆イリヤ 抱き枕カバー(白雪R18)\n' +
      '\n' +
      '        ※生地：白雪（Shirayuki）IRAD超一流2wayトリコット\n' +
      '        他社の2wayトリコットとは異なり、当社は独自の織機とヤーンを採用しています。単に添加剤を使って"改良したふり"をするのではなく、細部にわたって深く掘り下げ、長期にわたる試行錯誤と最適化を重ね、真に肌触りの良い新しい体験を創り上げました。\n' +
      '        面料の質感は肌触りが良く、耐久性も優れているので、快適さと耐久性を求める理想的な選択肢です。\n' +
      '\n' +
      '        ※仕様：抱き枕カバー (両面印刷、ファスナー付)\n' +
      '\n' +
      '        ※サイズ：160×50cm\n' +
      '\n' +
      '        ※印刷方法：G7印刷\n' +
      '        エプソンモナリザプラットフォームに基づく熱転写デジタル印刷機は、エプソン独自のソフトウェア、ハードウェア、インクの優れたマッチングと、権威あるカラーマネージメントマスター（G7マスター）の組み合わせにより、カラートランジションとグレーバランスを新たなレベルに引き上げました。\n' +
      '        パターンデザインの詳細を完全に復元することで、より高いレベルの芸術的表現を作品に注入することができます。\n' +
      '        他の八色印刷デバイスと比べると、暗いパターンでのグレーバランスのパフォーマンスが優れており、ディテールの遷移も優れています。\n' +
      '\n' +
      '        ※カスタムについて\n' +
      '        こちらはカスタム抱き枕カバーを受け入れます。\n' +
      '        1.お客さまの方からHD画像を頂いて生産します。（追加料金なし）\n' +
      '        2.こちらにある画像を自由に合わせて生産します。（追加料金なし）\n' +
      '        3.こちらにある画像が変更する希望があれば、是非ご連絡くださいませ。（追加料金あり）\n' +
      '        4.こちらは超一流の画手がいますので、お客様のご要望に合わせて画像を描けます。詳しくはご相談してください。（追加料金あり）\n' +
      '\n' +
      '        ※本番環境：ゼロコンタクト・無塵生産で完璧な「妻」を実現できます。\n' +
      '\n' +
      '        ※他のサイズ（150×50cm、180×60cm、210×70cmなど）がお気に入れば、是非ご連絡くださいませ。\n' +
      '\n' +
      '        ※カバーのみの販売になります。抱き枕本体は付属しませんので、別途ご用意下さい。\n' +
      '\n' +
      '        ※全店商品は受注生産で、現品はありません。ご注意くださいませ。\n' +
      '\n' +
      '        ※ご注文からお届けまで3〜７日かかります。\n' +
      '\n' +
      '        ※日本国内で佐川急便を利用し、送料無料です。\n' +
      '\n' +
      '        ※海外に配送できますので、是非ご連絡くださいませ。\n' +
      '\n' +
      '        ※掲載画像は完成イメージのため、実物と異なる場合がございます。\n' +
      '\n' +
      '        ※こちらはLuchuxue公式正規品店です。\n' +
      '\n' +
      '        ※メーカーについて:\n' +
      '        China Wuxi JGは、高級織物のカスタムに注目されて研究開発企業です。ともに弊社は高いカスタムする5Aレベルのスマートファクトリーを構築するために巨額の資金を投じます。\n' +
      '        工場面積は2,000平方メートル以上で、恒温恒湿クリーンルーム、製品研究開発室、ウェブキャストルームなどがあります。\n' +
      '        エプソンの産業グレードの生産ラインなどを導入し、業界唯一の自社開発の柔軟なカスタマイズSaaSプラットフォームを採用します。\n' +
      '        弊社の創設チームメンバーは、IBM や Alibaba などのフォーチュン 500 企業の出身であり、業界をリードするテキスタイルの柔軟なカスタマイズソリューションを世界中のお客様に提供します。\n' +
      '        もし以下の商品が必要あれば、是非ご連絡くださいませ。\n' +
      '        1. ホームテキスタイル寝具（抱き枕、クション、毛布、寝具4点セットなど）。\n' +
      '        2. 靴・洋服・カバン（Tシャツ、セーター、キャンバスシューズ、帽子、キャンバスバッグなど）。\n' +
      '        3. 小物（タペストリー、スタンドカード、携帯電話ケース、マウスパッドなど）。\n',
  shop: {
    name: 'Sprite Recordings // 美少女ノベルゲーム制作中'
  }
}

</script>

<style scoped>

.item-image-wrapper {
  width: 550px;
  height: 550px;
  background-color: #ebedf2;
}

.item-text {
  font-size: 16px;
  white-space: pre-wrap;
  word-wrap: break-word;
}

.shop-avatar-name {
  height: 30px;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.shop-name {
  flex: 1;
  font-size: 16px;
  font-weight: 300;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.item-title {
  font-size: 24px;
  font-weight: 700;
}
</style>
