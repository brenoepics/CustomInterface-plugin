<template>
  <div class="habbo-reward-box">
    <div class="habbo-message">{{ rewards.message }}</div>
    <div class="habbo-buttons">
      <button
        class="habbo-button habbo-button-success"
        @click="buttonClick(true)"
      >
        Accept
      </button>
      <button class="habbo-button" @click="buttonClick(false)">Decline</button>
    </div>
  </div>
</template>

<script lang="ts">
import Component from "vue-class-component";
import { State } from "vuex-class";
import Vue from "vue";
import { RewardsState } from "@/store/types";
import ClaimRewardComposer from "@/communication/outgoing/general/ClaimRewardComposer";
import App from "@/App";

@Component({})
export default class ClaimRewardComponent extends Vue {
  @State((state) => state.rewards) rewards!: RewardsState;

  buttonClick(accept: boolean): void {
    App.communicationManager.sendMessage(new ClaimRewardComposer(accept));
    this.$store.commit("rewards/setOpen", false);
  }

  mounted() {
    const targetTimestamp = this.rewards.closesIn;
    const timeDiff = targetTimestamp - Date.now();

    if (timeDiff > 0) {
      setTimeout(() => {
        this.$store.commit("rewards/setOpen", false);
      }, timeDiff);
    }
  }
}
</script>

<style lang="scss" scoped>
.habbo-reward-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 400px;
  height: 70px;
  position: absolute;
  z-index: 1000;
  left: 50%;
  transform: translateX(-50%);
  top: 0;
  color: #fff;
  padding: 5px;
  border: 1px solid rgba(0, 0, 0, 0.29);
  border-radius: 6px;
  background: rgba(46, 46, 44, 0.66);
  box-shadow: inset 0 0 0 2px #fff3;
  gap: 6%;

  .habbo-message {
    color: #fff;
    font-size: 15px;
    font-weight: bold;
    font-family: ubuntuBold, ubuntu, sans-serif;
  }

  .habbo-buttons {
    display: flex;
    flex-direction: row;
    gap: 3%;
    justify-content: center;

    .habbo-button {
      border-image-source: url("~@/assets/button.png");
      border-image-slice: 4 4 4 4 fill;
      border-image-width: 4px 4px 4px 4px;
      border-radius: 4px;
      padding: 0.375rem 0.75rem;
      min-height: 22px;
      color: #000;
      font-size: 13px;
      cursor: pointer;

      &:hover {
        border-image-source: url("~@/assets/button_hover.png");
      }

      &:active {
        border-image-source: url("~@/assets/button_active.png");
      }
    }

    .habbo-button-success {
      border-image-source: url("~@/assets/button_success.png");
      color: #fff;
      font-family: ubuntuBold, ubuntu, sans-serif;

      &:hover {
        border-image-source: url("~@/assets/button_success_hover.png");
      }

      &:active {
        border-image-source: url("~@/assets/button_success_active.png");
      }
    }
  }
}
</style>
