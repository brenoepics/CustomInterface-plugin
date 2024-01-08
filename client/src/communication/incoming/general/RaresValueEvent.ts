import Rare from '@/store/models/Rare';
import IncomingMessage from '../IncomingMessage';
import App from '@/App';
import RareCategory from '@/store/models/RareCategory';

interface Data {
    rares: {
        id: number;
        name: string;
        image: string;
        category: number;
        cost: {
            credits: number;
            points: number;
            pointsType: number;
        }
    }[];
    categories: {
        id: number;
        name: string;
    }[];
    owned: number[];
    frontpage: string;
}
export default class RaresValueEvent implements IncomingMessage {
    parse(data: Data): void {
        App.interfaceManager.container.$store.commit('rarevalues/setRares', data.rares.map(rare => new Rare(rare.id, rare.name, rare.image, rare.category, rare.cost)));
        App.interfaceManager.container.$store.commit('rarevalues/setCategories', data.categories.map(category => new RareCategory(category.id, category.name)));
        App.interfaceManager.container.$store.commit('rarevalues/setOwned', data.owned);
        App.interfaceManager.container.$store.commit('rarevalues/setFrontpage', data.frontpage);
        App.interfaceManager.container.$store.commit('rarevalues/setOpen', true);
    }
}