export default class Rare {
    public id: number;
    public name: string;
    public image: string;
    public category: number;
    public cost: {
        credits: number;
        points: number;
        pointsType: number;
    }

    constructor(id: number, name: string, image:string, category: number, cost: { credits: number; points: number; pointsType: number; }) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.cost = cost;
    }
}