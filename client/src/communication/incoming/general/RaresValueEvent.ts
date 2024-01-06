import IncomingMessage from '../IncomingMessage';

export default class RaresValueEvent implements IncomingMessage {
    parse(data: any): void {
        console.log(data);
    }
}