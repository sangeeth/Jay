package jay.gsm;

public interface ConnectionListener {
	public void onCommandExecuted(CommandResult commandResult);
	public void onUnsolicitedResult(UnsolicitedResult e);
}
