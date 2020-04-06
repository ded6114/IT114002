
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UI {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Online");
		frame.setLayout(new BorderLayout());
		JPanel Chat = new JPanel();
		Chat.setPreferredSize(new Dimension(500,300));
	    Chat.setLayout(new BorderLayout());
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("");
		JPanel chatArea = new JPanel();
		chatArea.setLayout(new BorderLayout());
		chatArea.add(textArea, BorderLayout.CENTER);
		chatArea.setBorder(BorderFactory.createLineBorder(Color.blue));
		Chat.add(chatArea, BorderLayout.CENTER);
		JPanel userInput = new JPanel();JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(100,40));
		JButton b = new JButton();
		b.setPreferredSize(new Dimension(100,40));
		b.setText("Send");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = textField.getText();
				if(message.length() > 0) {
					textArea.append("\n" + textField.getText());
					textField.setText("");
				}
			}
			
		});
		userInput.add(textField);
		userInput.add(b);
		Chat.add(userInput, BorderLayout.SOUTH);
		frame.add(Chat, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}