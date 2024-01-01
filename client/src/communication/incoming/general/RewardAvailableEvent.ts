import IncomingMessage from '../IncomingMessage';
import App from '@/App';

export default class RewardAvailableEvent implements IncomingMessage {
    parse(data: any): void {
        App.interfaceManager.container.$store.commit('rewards/setMessage', data.message);
        App.interfaceManager.container.$store.commit('rewards/setClosesIn', data.close);
        App.interfaceManager.container.$store.commit('rewards/setOpen', true);
    }
}