
import javax.swing.text.DefaultCaret;



public class UIChat extends javax.swing.JFrame implements OnReceive {

    
    	private static final long serialVersionUID = 507030164269413483L;
	static SocketClient client;
    public UIChat() {
        initComponents();
        DefaultCaret caret = (DefaultCaret)history.getCaret();
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        DefaultCaret caret1 = (DefaultCaret)activeClients.getCaret();
	caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    
    @SuppressWarnings("unchecked")
    
   
    private void initComponents() {

        userName = new javax.swing.JTextField();
        host = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        activeClients = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        send = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        history = new javax.swing.JTextArea();
        connect = new javax.swing.JButton();
        disconnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userName.setText("User");
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });

        host.setText("127.0.0.1");
        host.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostActionPerformed(evt);
            }
        });

        port.setText("3001");

        activeClients.setColumns(20);
        activeClients.setRows(5);
        jScrollPane2.setViewportView(activeClients);

        messageArea.setColumns(20);
        messageArea.setRows(5);
        jScrollPane4.setViewportView(messageArea);

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        history.setColumns(20);
        history.setRows(5);
        jScrollPane6.setViewportView(history);

        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connect)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane6)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(send)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(disconnect)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(send)
                    .addComponent(disconnect))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {
       
    }

    private void hostActionPerformed(java.awt.event.ActionEvent evt) {
       
    }

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {
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
		    	client.registerListener(this);
		    	client.postConnectionData(userName.getText());
		    	connect.setEnabled(false);
		   
		    	
		  
	    	} 
    }
    private void sendActionPerformed(java.awt.event.ActionEvent evt) {
        
        client.sendMessage(messageArea.getText());
        
        messageArea.setText("");
        
    }
    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {
        client.disconnect();
    }
     
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIChat().setVisible(true);
            }
        });
    }
    
    @Override
public void onReciveActive(String userNames) {
	
	if(activeClients != null) {
		activeClients.setText(userNames);
		
	    }	
	
	
	
}
    
    @Override
public void onReceiveConnect(String userName) {
	
	if(history != null) {
		history.append(userName);
		history.append(System.lineSeparator());
	    }	
	
	
	
}

@Override
public void onReceiveMessage(String Message) {
	
	if(history != null) {
		history.append(Message);
		history.append(System.lineSeparator());
	    }
	
}

@Override
public void onReceiveDisconnect(String Username) {
	
	if(history != null) {
		history.append(Username);
		history.append(System.lineSeparator());
	    }	
}

   
    private javax.swing.JTextArea activeClients;
    private javax.swing.JButton connect;
    private javax.swing.JButton disconnect;
    private javax.swing.JTextArea history;
    private javax.swing.JTextField host;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JTextField port;
    private javax.swing.JButton send;
    private javax.swing.JTextField userName;
  
}
