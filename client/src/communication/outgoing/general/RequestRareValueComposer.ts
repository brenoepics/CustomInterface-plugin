import OutgoingMessage from "../OutgoingMessage";

export default class RequestRareValueComposer implements OutgoingMessage {
  header: string = "request_rare_values";
  data: any;

  constructor() {
    this.data = {
      author: "brenoepic",
    };
  }
}
