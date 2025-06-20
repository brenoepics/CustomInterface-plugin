import IncomingMessage from "../IncomingMessage";
import App from "@/App";

export default class PlaySongEvent implements IncomingMessage {
  parse(data: any): void {
    const index: number = data.index;
    App.interfaceManager.bus.$emit("playSong", index);
  }
}
