<template>
  <div id="app" class="youtube-overlay">
    <transition name="slide-down">
      <ClaimRewardComponent v-if="rewards.open" />
    </transition>
    <RareValueComponent v-if="rarevalue.open" />
    <Menu />
  </div>
</template>

<script lang="ts">
import Component from "vue-class-component";

import Vue from "vue";
import { State } from "vuex-class";
import { RareValuesState, RewardsState, SessionState } from "@/store/types";
import ClaimRewardComponent from "./components/ClaimRewardComponent.vue";
import RareValueComponent from "./components/RareValueComponent.vue";
import Menu from "./components/Menu.vue";

@Component({
  components: {
    ClaimRewardComponent,
    RareValueComponent,
    Menu
  },
})
export default class App extends Vue {
  @State((state) => state.rewards) rewards!: RewardsState;
  @State((state) => state.rarevalues) rarevalue!: RareValuesState;
  @State('session') session!: SessionState;
}
</script>

<style lang="scss">
@import "../assets/app.scss";

.slide-down-enter-active,
.slide-down-leave-active {
  transition: all 1s;
}

.slide-down-enter,
.slide-down-leave-to {
  margin-top: -100%;
}
</style>
