export default class Logger {
  private static DebugMode = true;

  public static Log(msg: string) {
    if (!Logger.DebugMode) return;

    const date = new Date();
    console.log(
      "[" +
        date.getHours() +
        ":" +
        date.getMinutes() +
        ":" +
        date.getSeconds() +
        "] " +
        msg,
    );
  }
}
