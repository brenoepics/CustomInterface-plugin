export interface Rare {
    id: number;
    name: string;
    category: number;
    cost: {
        credits: number;
        points: number;
        pointsType: number;
    }
}