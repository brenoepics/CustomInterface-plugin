import { Module, MutationTree } from 'vuex';
import { RareValuesState, RootState } from './../types';
import { Rare } from '../models/Rare';

const state: RareValuesState = {
    open: false,
    categories: [],
    rares: []
}

const namespaced: boolean = true;

const mutations: MutationTree<RareValuesState> = {
  setCategories(state, categories: string[]) {
    state.categories = categories;
  },
  setRares(state, rares: Rare[]) {
    state.rares = rares;
  },
  setOpen(state, open: boolean) {
    state.open = open;
  }
}

export const rarevalues: Module<RareValuesState, RootState> = {
  namespaced,
  state,
  mutations
}