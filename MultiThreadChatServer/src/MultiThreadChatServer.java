import java.io.DataInputStream;

import java.io.PrintStream;

import java.io.IOException;

import java.net.Socket;

import java.net.ServerSocket;

//Chat server to deliver public messages

public class MultiThreadChatServer {

// This is server socket.

private static ServerSocket serverSocket = null;

// This is client socket.

private static Socket clientSocket = null;

//maximum number of clients can be connected to server is maxClients

private static final int maxClients = 3;//we can give more than 3 clients

private static final clientThread[] threads = new clientThread[maxClients];

public static void main(String args[]) {

// By default port number will be 2222

// We can specify port if available

int portNumber = 2222;

if (args.length < 1) {

System.out

.println("Usage: MultiThreadChatServer <portNumber>\n"

+ "Now using port number=" + portNumber);

} else {

portNumber = Integer.valueOf(args[0]).intValue();

}

//trying to open server socket with portNumber

try {

serverSocket = new ServerSocket(portNumber);

} catch (IOException e) {

System.out.println(e);

}

//When connected,for every connection we are creating a client socket

//We are passing newly created client socket to new client thread

while (true) {

try {

clientSocket = serverSocket.accept();

int i = 0;

//it will check maximum number of allowable connection based on initial input(example 3 for our program)

for (i = 0; i < maxClients; i++) {

if (threads[i] == null) {

(threads[i] = new clientThread(clientSocket, threads)).start();

break;//it will break once it reaches maximum limit

}

}

//if the limit reaches (example 3 for our program) it will display message

if (i == maxClients) {

PrintStream os = new PrintStream(clientSocket.getOutputStream());

os.println("Server too busy. Try later or Wait for some time");

os.close();//closing of server

clientSocket.close();

}

} catch (IOException e) {

System.out.println(e);

}

}

}

}

//here is the chat client thread for multiple hosting.

//client thread opens input and output streams for all clients based on thread

class clientThread extends Thread {

private DataInputStream is = null;

private PrintStream os = null;

private Socket clientSocket = null;

private final clientThread[] threads;

private int maxClients;

public clientThread(Socket clientSocket, clientThread[] threads) {

this.clientSocket = clientSocket;

this.threads = threads;

maxClients = threads.length;

}

public void run() {

int maxClients = this.maxClients;

clientThread[] threads = this.threads;

try {

  

// Create input and output streams for this client.

is = new DataInputStream(clientSocket.getInputStream());

os = new PrintStream(clientSocket.getOutputStream());

os.println("Enter your full name :");

String name = is.readLine().trim();

os.println("Hey! How are you. Welcome " + name

+ " to our chat room.\nTo exit please enter /quit in a new line");

for (int i = 0; i < maxClients; i++) {

if (threads[i] != null && threads[i] != this) {

threads[i].os.println("*** New user " + name

+ " entered the chat room !!! ***");

}

}

while (true) {

String line = is.readLine();

if (line.startsWith("/quit")) {

break;

}

for (int i = 0; i < maxClients; i++) {

if (threads[i] != null) {

threads[i].os.println("<" + name + "&gr; " + line);

}

}

}

for (int i = 0; i < maxClients; i++) {

if (threads[i] != null && threads[i] != this) {

threads[i].os.println("*** User " + name

+ " is leaving the chat room !!! ***");

}

}

os.println("*** Bye Bye " + name + " . Hope you enjoyed ***");

//once client exit th chat server we need to clean up the thread. We are setting variable to null so that new client can join

for (int i = 0; i < maxClients; i++) {

if (threads[i] == this) {

threads[i] = null;

}

}

//closing output stream and input stream

is.close();

os.close();

//closing socket

clientSocket.close();

} catch (IOException e) {

}

}

}

