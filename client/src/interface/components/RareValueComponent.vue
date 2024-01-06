<template>
    <div class="habbo-rares-box">
        <div class="habbo-items">
            <div class="habbo-item" v-for="rare in rarevalue.rares" :key="rare.id">{{ rare.name }}</div>
        </div>
    </div>
  </template>
  
  <script lang="ts">
  import Component from "vue-class-component";
  import Vue from 'vue';
  import { State } from "vuex-class";
  import { RareValuesState } from "@/store/types";
  @Component({})
  export default class RareValueComponent extends Vue {
    @State(state => state.rarevalue) rarevalue!: RareValuesState;
   mounted() {
      console.log(this.rarevalue);
   }
  }
  </script>
  
  <style lang="scss" scoped>
  .habbo-rares-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 400px;
    height: 70px;
    position: absolute;
    z-index: 1000;
    left: 50%; transform: translateX(-50%);
    top: 0;
    color: #fff;
    padding: 5px;
    border: 1px solid rgba(0,0,0,0.29); border-radius: 6px; background: rgba(46,46,44,0.66); box-shadow: inset 0 0 0 2px #fff3; 
    gap: 6%;
  }
  </style>