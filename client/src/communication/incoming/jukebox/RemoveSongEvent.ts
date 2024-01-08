import IncomingMessage from "../IncomingMessage";
import App from "@/App";

export default class RemoveSongEvent implements IncomingMessage {
  parse(data: any): void {
    const index: number = data.index;
    App.interfaceManager.bus.$emit("removeSong", index);
  }
}
