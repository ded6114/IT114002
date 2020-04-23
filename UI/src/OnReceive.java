
public interface OnReceive {

	void onReceiveConnect(String userName);
	void onReceiveMessage(String Message);
	void onReceiveDisconnect(String Username);
}