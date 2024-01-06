import { RootState } from './types';
import Vue from 'vue';
import Vuex, { StoreOptions } from 'vuex';
import { session } from './modules/session';
import { commands } from './modules/commands';
import { jukebox } from './modules/jukebox';
import { slotmachine } from './modules/slotmachine';
import { youtubeplayer } from './modules/youtubeplayer';
import { twitchplayer } from './modules/twitchplayer';
import { rewards } from './modules/rewards';
import { rarevalues } from './modules/rarevalues';

Vue.use(Vuex);

const store: StoreOptions<RootState>  = {
    state: {
        connected: false    
    },

    modules: {
        session,
        commands,
        jukebox,
        slotmachine,
        youtubeplayer,
        twitchplayer,
        rewards,
        rarevalues
    },

    mutations: {
        setConnected(state: RootState, val: boolean)  {
            state.connected = val;
        }
    }
};

export default new Vuex.Store<RootState>(store);