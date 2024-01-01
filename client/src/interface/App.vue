<template>
  <div id="app" class="youtube-overlay">
    <transition name="slide-down">
      <ClaimRewardComponent v-if="rewards.open"/>
    </transition>
  </div>
</template>

<script lang="ts">
import Component from "vue-class-component";

import Vue from "vue";
import { State } from 'vuex-class';
import { RewardsState } from '@/store/types';
import ClaimRewardComponent from "./components/ClaimRewardComponent.vue";

@Component({
  components: {
    ClaimRewardComponent,
  },
})
export default class App extends Vue {
  @State(state => state.rewards) rewards!: RewardsState;
}
</script>

<style lang="scss">
@import '../assets/app.scss';

.slide-down-enter-active, .slide-down-leave-active {
    transition: all 1.0s;
  }

  .slide-down-enter, .slide-down-leave-to {
    margin-top: -100%;
  }
</style>
