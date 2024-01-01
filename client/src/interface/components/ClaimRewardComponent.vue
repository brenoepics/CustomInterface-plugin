<template>
    <div class="reward-box">
        <div class="message">{{ rewards.message }}</div>
        <div class="buttons">
            <button class="button button-success" @click="buttonClick(true)">Accept</button>
            <button class="button" @click="buttonClick(false)">Refuse</button>
        </div>
    </div>
  </template>
  
  <script lang="ts">
  import Component from "vue-class-component";
  import { State } from 'vuex-class';
  import Vue from 'vue';
  import { RewardsState } from "@/store/types";
  import ClaimRewardComposer from "@/communication/outgoing/general/ClaimRewardComposer";
  import App from "@/App";
  
  @Component({})
  export default class ClaimRewardComponent extends Vue {
    @State(state => state.rewards) rewards!: RewardsState;

    buttonClick(accept: boolean): void {
        App.communicationManager.sendMessage(new ClaimRewardComposer(accept));
        this.$store.commit('rewards/setOpen', false);
    }

    mounted() {
    const targetTimestamp = this.rewards.closesIn;
    const timeDiff = targetTimestamp - Date.now();

    if (timeDiff > 0) {
      setTimeout(() => {
        this.$store.commit('rewards/setOpen', false);
      }, timeDiff);
    }
  }
  }
  </script>
  
  <style lang="scss" scoped>
    .reward-box {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 400px;
      height: 65px;
      position: absolute;
      z-index: 1000;
      left: 50%; transform: translateX(-50%);
      top: 0;
      border-image-slice: 33 7 8 7 fill;
      border-image-source: url("~@/assets/body.png");
      border-image-repeat: repeat repeat;
      border-image-width: 0px 7px 8px 7px;
      color: #000;
      padding: 7px;
      gap: 6%;

    .message {
        color: #000;
        font-size: 15px;
        font-weight: bold;
        font-family: ubuntuBold, ubuntu, sans-serif;
    }

    .buttons {
        display:flex;
        flex-direction:row;
        gap: 3%;
        justify-content: center;

        .button {
            border-image-source: url("~@/assets/button.png");
            border-image-slice: 4 4 4 4 fill;
            border-image-width: 4px 4px 4px 4px;
            padding: .375rem .75rem;
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

        .button-success {
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