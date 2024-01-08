<template>
  <div class="inventory-item" @click="$emit('clickItem', item)">
    <img :src="getItemIcon(item.image)" :alt="item.name" @error="setAltImg" />
  </div>
</template>

<script lang="ts">
import App from "@/App";
import Rare from "@/store/models/Rare";
import { RareValuesState } from "@/store/types";
import Vue from "vue";
import Component from "vue-class-component";
import { State } from "vuex-class";
@Component({
  props: {
    item: Rare,
  },
})
export default class InventoryItem extends Vue {
  @State((state) => state.rarevalues) rarevalues!: RareValuesState;
  item!: Rare;

  getItemIcon(code: string): string {
    //@ts-ignore
    const itemsUrl = ExternalConfig.items as string;
    if (itemsUrl == null) return "";

    return itemsUrl.replace(
      "%code%",
      this.rarevalues.errors.includes(code) ? "default" : code,
    );
  }

  setAltImg(event: Event): void {
    this.$store.commit("rarevalues/addError", this.item.image);
  }

  handleClick() {
    if (!this.item) return;

    //App.communicationManager.sendMessage(new ClickItemComposer(this.item.id));
  }
}
</script>

<style lang="scss" scoped>
.inventory-item {
  position: relative;
  width: 40px;
  height: 40px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 3px;
  border: 2px solid #b6bec5;
  border-radius: 5px;
  background-color: #cdd3d9;
  cursor: pointer;

  img {
    pointer-events: none;
  }
  &:hover {
    background-color: #ececec;
    border-color: #fff;
  }
  &:active {
    background-color: #d1d1d1;
    border-color: #fff;
  }

  &[active="true"] {
    border-color: #fff;
    background-color: #ececec;
  }
}
</style>
