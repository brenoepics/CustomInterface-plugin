import OutgoingMessage from "../OutgoingMessage";

export default class ClaimRewardComposer implements OutgoingMessage {
  header: string = "accept_reward";
  data: any;

  constructor(accept: boolean) {
    this.data = {
      accept: accept,
    };
  }
}
