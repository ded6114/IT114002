import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;



public class UIChat extends JFrame implements OnReceive{
	/**
	 * 
	 */
	private static final long serialVersionUID = 507030164269413483L;
	static SocketClient client;
	
	static JTextArea history;
	
public UIChat() {
	
	super("SocketClient");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// add a window listener
	this.addWindowListener(new WindowAdapter() {
		/* (non-Javadoc)
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			// before we stop the JVM stop the example
			//client.isRunning = false;
			super.windowClosing(e);
		}
	});
}
	
public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException ex) {
	} catch (InstantiationException ex) {
	} catch (IllegalAccessException ex) {
	} catch (UnsupportedLookAndFeelException ex) {
	}
	UIChat window = new UIChat();
	window.setLayout(new BorderLayout());
	JPanel connectionDetails = new JPanel();
	JTextField userName = new JTextField();
	userName.setText("User");
	JTextField host = new JTextField();
	host.setText("127.0.0.1");
	
	
	JTextField port = new JTextField();
	port.setText("3001");
	JButton connect = new JButton();
	
	connect.setText("Connect");
	connectionDetails.add(userName);
	connectionDetails.add(host);
	connectionDetails.add(port);
	connectionDetails.add(connect);
	window.add(connectionDetails, BorderLayout.NORTH);
	JPanel area = new JPanel();
	area.setLayout(new BorderLayout());
	window.add(area, BorderLayout.CENTER);
	
	
	
	connect.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	
	    	int _port = -1;
	    	try {
	    		_port = Integer.parseInt(port.getText());
	    	}
	    	catch(Exception num) {
	    		System.out.println("Port not a number");
	    	}
	    	if(_port > -1) {
		    	Object defaultIP;
		    	client = SocketClient.connect(host.getText(), _port);
		    	client.registerListener(window);
		    	client.postConnectionData(userName.getText());
		    	connect.setEnabled(false);
		   
		    	
		  
	    	}
	    }
	});
	
	
	
	JPanel container = new JPanel();
	container.setLayout(new BorderLayout());
	JTextArea ta = new JTextArea();
	ta.setEditable(false);
	history = ta;
	history.setWrapStyleWord(true);
	history.setAutoscrolls(true);
	history.setLineWrap(true);
	JScrollPane scroll = new JScrollPane(history);
	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	DefaultCaret caret = (DefaultCaret)history.getCaret();
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
   
	container.add(scroll,BorderLayout.CENTER);
	area.add(container,BorderLayout.CENTER);
	window.setPreferredSize(new Dimension(400,600));
	window.pack();
	window.setVisible(true); }

	

@Override
public void onReceiveConnect(String userName) {
	// TODO Auto-generated method stub
	if(history != null) {
		history.append(userName);
		history.append(System.lineSeparator());
	    }	
	
	
	
}

@Override
public void onReceiveMessage(String Message) {
	// TODO Auto-generated method stub
	if(history != null) {
		history.append(Message);
		history.append(System.lineSeparator());
	    }
	
}

@Override
public void onReceiveDisconnect(String Username) {
	// TODO Auto-generated method stub
	if(history != null) {
		history.append(Username);
		history.append(System.lineSeparator());
	    }	
}
}
		