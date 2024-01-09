<template>
    <DragAble
    handle=".move"
    :grid="[5, 5]"
    :style="{ top: cardStyle('x') + 'px', right: cardStyle('y') + 'px' }"
    class="habbo-menu-box">
      <div class="habbo-button rares" @click="button('rarevalue')"></div>
      <div class="habbo-button move"></div>
    </DragAble>
  </template>
  
  <script lang="ts">
  import Component from "vue-class-component";
  import { State } from "vuex-class";
  import Vue from "vue";
  import App from "@/App";
  import { RareValuesState } from "@/store/types";
  import RequestRareValueComposer from "@/communication/outgoing/general/RequestRareValueComposer";
  
  @Component({})
  export default class Menu extends Vue {
    @State((state) => state.rarevalues) rarevalues!: RareValuesState;
    button(page: string): void {
        if(page === "rarevalue" && !this.rarevalues.open) {
            App.communicationManager.sendMessage(new RequestRareValueComposer());
        }
    }
    cardStyle(type: "x" | "y"): number {
    //@ts-ignore
    const menuTop = ExternalConfig.menu.top as number;
    //@ts-ignore
    const menuRight = ExternalConfig.menu.right as number;

    return (type === "x" ? menuTop : menuRight);
  }
  }
  </script>
  
  <style lang="scss" scoped>
  .habbo-menu-box {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 40px;
    position: absolute;
    z-index: 1000;
    color: #fff;
    border: 1px solid rgba(0, 0, 0, 0.29);
    border-radius: 6px;
    background: rgba(46, 46, 44, 0.66);
    box-shadow: inset 0 0 0 2px #fff3;
    padding: 5px;
    gap: 3%;

    .habbo-button {
        width:30px;
        height: 30px;
        cursor: not-allowed;
        background-image: url("~@/assets/default_icon.png");
        background-repeat: no-repeat;
        background-position: center;
        &.rares {
            background-image: url("~@/assets/diamond_dragon_icon.png");
            background-repeat: no-repeat;
            cursor: pointer;
        }
        &.move {
            background-image: url("~@/assets/move_icon.png");
            background-repeat: no-repeat;
            cursor: grab;
            &:active {
                cursor: grabbing;
            }
        }

        &:hover {
            filter:brightness(1.2)
        }
        &:active {
            filter:brightness(0.8)
        }
    }
  }
  </style>
  