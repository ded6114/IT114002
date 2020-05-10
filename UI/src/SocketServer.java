import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;


public class SocketServer {
	int port = 3002;
	public static boolean isRunning = true;
	private List<ServerThread> clients = new ArrayList<ServerThread>();
	
	Queue<String> messages = new LinkedList<String>();
	public GameState state = new GameState();
	public static long ClientID = 0;
	public synchronized long getNextId() {
		ClientID++;
		return ClientID;
	}
	public void processCommand(String message) {
        if (message == null) {
        	return;
        }
	if(message.contains("mute @")) {
		
		String[] partial = message.split("mute @");
		if(partial.length >= 2) {
			partial = partial[1].split(" ");
			String user = partial[0];
		
			return ;
		}
			
	}
	if (message.contains("*") && message.contains(contains(" *"))){
		 message = message.replaceAll("* ", "<b>");
		 message = message.replaceAll("* ", "<b>");
	}
	
	}
	private CharSequence contains(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	public synchronized void toggleButton(Payload payload) {
		if(state.isButtonOn && !payload.IsOn()) {
			state.isButtonOn = false;
			broadcast(payload);
		}
		else if (!state.isButtonOn && payload.IsOn()) {
			state.isButtonOn = true;
			broadcast(payload);
		}
	}
	
	//Generate random integers 
	
	class GenerateRandom {
	int int_random = ThreadLocalRandom.current().nextInt();  
	}
	
	//Create Bold and Italic font 
	
	public void paint(Graphics g) {
		
		 
		 Font myFont = new Font("Courier", Font.BOLD | Font.ITALIC ,10);
		 
		 g.setFont(myFont);
		 
		 g.drawString("Bold & Italic Font Example", 10, 50);
		 }
	
	
	private void start(int port) {
		this.port = port;
		startQueueReader();
		
		System.out.println("Waiting for client");
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			
			while(SocketServer.isRunning) {
				try {
					Socket client = serverSocket.accept();

					System.out.println("Client connecting...");
					
					ServerThread thread = new ServerThread(client, this);
					thread.start();
					thread.setClientId(getNextId());
					//add client thread to list of clients
					clients.add(thread);
                                    
					System.out.println("Client added to clients pool");
                                        Iterator<ServerThread> iter = clients.iterator();
                                        
                               }
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				isRunning = false;
				Thread.sleep(50);
				System.out.println("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

  void startQueueReader() {
		System.out.println("Preparing Queue Reader");
		Thread queueReader = new Thread() {
			@Override
			public void run() {
				String message = "";
				try(FileWriter write = new FileWriter("chathistory.txt", true)){
					while(isRunning) {
						message = messages.poll();
						if(message != null) {
							message = messages.poll();
							write.append(message);
							write.write(System.lineSeparator());
							write.flush();
						}
						
						sleep(50);
					}
				}
				catch(IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		queueReader.start();
		System.out.println("Started Queue Reader");
                Thread activeList = new Thread() {
			@Override
			public void run() {
				
				try{
					while(isRunning) {
                                            String message = "";
                                            Iterator<ServerThread> iter = clients.iterator();
                                            while(iter.hasNext()){
                                                message += iter.next().getClientName() + "\n";
                                            }
                                            Payload payload = new Payload();
                                            payload.setPayloadType(PayloadType.ACTIVE);
                                            payload.setMessage(message);
                                            iter = clients.iterator();
                                            while(iter.hasNext()) {
                                                ServerThread client = iter.next();
                                                boolean messageSent = client.send(payload);
                                            }
		
                                            sleep(1000);
					}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
                activeList.start();
	}
	@Deprecated
	int getClientIndexByThreadId(long id) {
		for(int i = 0, l = clients.size(); i < l;i++) {
			if(clients.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}
	public synchronized void broadcast(Payload payload, String name) {
		String msg = payload.getMessage();
		payload.setMessage(
				
				(name!=null?name:"[Name Error]") 
				
				+ (msg != null?": "+ msg:"")
		);
		broadcast(payload);
	}
	

   
     
	public synchronized void broadcast(Payload payload) {
		System.out.println("Sending message to " + clients.size() + " clients");
		
		
		Iterator<ServerThread> iter = clients.iterator();
		
		payload.setMessage(processCommand(payload.getMessage()));
		if(payload.getMessage() == null) {
			return;
		}
		while(iter.hasNext()) {
			ServerThread client = iter.next();
			boolean messageSent = client.send(payload);
			if(!messageSent) {
				
				iter.remove();
				System.out.println("Removed client " + client.getId());
			}
		}
	}
	 
	//private String processCommand(String message) {
		// TODO Auto-generated method stub
		//return null;
	//}
	
	public synchronized void broadcast(Payload payload, long id) {
		
		int from = getClientIndexByThreadId(id);
		String msg = payload.getMessage();
		payload.setMessage(
				
				(from>-1?"Client[" + from+"]":"unknown") 
				
				+ (msg != null?": "+ msg:"")
		);
		
		broadcast(payload);
		
	}
	
	public synchronized void broadcast(String message, long id) {
		Payload payload = new Payload();
		payload.setPayloadType(PayloadType.MESSAGE);
		payload.setMessage(message);
		broadcast(payload, id);
	}
	public static void main(String[] args) {
		
		int port = 3001;
		if(args.length >= 1) {
			String arg = args[0];
			try {
				port = Integer.parseInt(arg);
			}
			catch(Exception e) {
				
			}
		}
		System.out.println("Starting Server");
		SocketServer server = new SocketServer();
		System.out.println("Listening on port " + port);
		server.start(port);
		System.out.println("Server Stopped");
	}
}
class GameState{
	boolean isButtonOn = false;
}