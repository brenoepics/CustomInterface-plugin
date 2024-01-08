import { Module, MutationTree } from 'vuex';
import { RareValuesState, RootState } from './../types';
import Rare from '../models/Rare';
import RareCategory from '../models/RareCategory';

const state: RareValuesState = {
    open: false,
    categories: [],
    rares: [],
    owned: [],
    frontpage: "",
    errors: []
}

const namespaced: boolean = true;

const mutations: MutationTree<RareValuesState> = {
  setCategories(state, categories: RareCategory[]) {
    state.categories = categories.sort((a, b) => a.id - b.id);
  },
  setRares(state, rares: Rare[]) {
    state.rares = rares;
  },
  setOwned(state, owned: number[]) {
    state.owned = owned;
  },
  setFrontpage(state, frontpage: string) {
    state.frontpage = frontpage;
  },
  addError(state, error: string) {
    state.errors.push(error);
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