import java.util.*; // util

import java.io.*; // io

import java.net.*; // net

import java.awt.*; // awt

import java.awt.event.*; // event

import javax.swing.*; // swing

// client class

public class ChatBocClient {

static JFrame jf; // JFrame jf

// Jpanel jp1,2,3

static JPanel jp1;   

static JPanel jp2;

static JPanel jp3;

// jp_button

static JButton jb;

static JTextField jt1; // JTextfield jt1

static JLabel jl1; // JLabel

static JTextField jt2; // testfield 2

static JLabel jl2; //

static JTextField jt3; // JTextfield jt3

static JTextArea jt4; JTextfield jt4

static JLabel jl3; // JLabel

static JLabel jl;   

static JScrollPane scroll ;

static String address = "";

static String name = null;   

static String curr = "";   

public static void main(String[] args) {

jf = new JFrame("ChatBox");

jp1 = new JPanel();

jp2 = new JPanel();

jp3 = new JPanel();

jp1.setBackground(Color.WHITE);

jp2.setBackground(Color.WHITE);

jp3.setBackground(Color.WHITE);

jp3.setBorder(BorderFactory.createLineBorder (Color.black));

jl = new JLabel("");

jl.setSize(400, 400);

jl1 = new JLabel("IP Address");

jl2 = new JLabel("User Name");

jl3 = new JLabel("Enter the Message:");   

jb = new JButton("SEND");   

jt1 = new JTextField("");

jt41 = new JTextArea("CHAT BOX");

jt41.setColumns(50);

jt41.setAlignmentY(400);

jt41.setEditable(false);

jt1.addActionListener(new IP());

jt1.setColumns(10);

jt1.setToolTipText("Enter Server address: ");   

jt2 = new JTextField("");

jt2.setToolTipText("Enter your username");

jt2.setColumns(10);   

jt3 = new JTextField("");

jt3.setToolTipText("Enter your message");

jt3.setColumns(20);

jt3.setEditable(false);

  

jp1.add(jl1, BorderLayout.PAGE_START);

jp1.add(jt1, BorderLayout.PAGE_START);

jp1.add(jl2, BorderLayout.PAGE_START);

jp1.add(jt2, BorderLayout.PAGE_END);

jp3.add(jl, BorderLayout.PAGE_START);

jp3.add(jt41, BorderLayout.CENTER);

jp2.add(jl3, BorderLayout.LINE_START);

jp2.add(jt3, BorderLayout.CENTER);

jp2.add(jb, BorderLayout.EAST);

scroll = new JScrollPane (jt41);

jf.setSize(800,800);

jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

jf.add(jp1, BorderLayout.PAGE_START);

jf.add(jp3, BorderLayout.CENTER);

jf.add(jp2, BorderLayout.SOUTH);

jf.add(scroll);

jf.setVisible(true);   

while(address.equals("")){System.out.print("");}   

System.out.println("Yes, it is working");

// try block

try{

Socket s = new Socket(address,5190);

PrintWriter out = new PrintWriter(s.getOutputStream(), true);

jt2.addActionListener(new Name(out));

jb.addActionListener(new Message(out));

jt3.addActionListener(new Message(out));

new Read(s).start();   

}

// catch block

catch(IOException e){

System.out.println("Connection to the goo_gle has failed. :(");

}   

}   

static class IP implements ActionListener{

@Override

public void actionPerformed(ActionEvent ae){

JTextField jt = (JTextField) ae.getSource();

address = jt.getText();

jt.setEditable(false);

}

}

static class Name implements ActionListener{

PrintWriter out;

Name(PrintWriter newOut){out = newOut;}

@Override

public void actionPerformed(ActionEvent ae){

JTextField jt = (JTextField) ae.getSource();

name = jt.getText();

out.println(name);

jt3.setEditable(true);

jt.setEditable(false);   

}

}

static class Message implements ActionListener{

//Socket s;

PrintWriter out;

//Message(Socket newS){s = newS;}

Message(PrintWriter newOut){out = newOut;}

@Override

public void actionPerformed(ActionEvent ae){

String line = jt3.getText();

out.println(line);

jt3.setText("");   

}   

}   

static class Read extends Thread{

Socket s;

Read(Socket newS){s = newS;}

synchronized public void run(){

Scanner sin;

curr = jt41.getText();

try {

sin = new Scanner(s.getInputStream());

while(!s.isClosed()){   

while(sin.hasNext()){

curr =curr+"\n"+sin.nextLine();

jt41.setText(curr);

}

}   

} catch (IOException ex) {

System.out.println("EXIT");

}

}

}

}