import { Module, MutationTree } from "vuex";
import { RewardsState, RootState } from "./../types";

const state: RewardsState = {
  message: "Loading...",
  closesIn: Date.now(),
  open: false,
};

const namespaced: boolean = true;

const mutations: MutationTree<RewardsState> = {
  setMessage(state, message: string) {
    state.message = message;
  },
  setClosesIn(state, closesIn: number) {
    const ms = Date.now() + closesIn * 1000;
    state.closesIn = ms;
  },
  setOpen(state, open: boolean) {
    state.open = open;
  },
};

export const rewards: Module<RewardsState, RootState> = {
  namespaced,
  state,
  mutations,
};
