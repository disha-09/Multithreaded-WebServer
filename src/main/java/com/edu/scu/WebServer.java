package com.edu.scu;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WebServer {
    
	public static void main(String[] args) throws IOException{
    	
        if(args.length != 4 || !args[0].startsWith(Constants.documentRoot) || !args[2].startsWith(Constants.port)){
            System.out.println("Incorrect command usage.");
            System.out.println("Pass the correct arguments in the below format: \n" +
             "java WebServer -document_root {path to the root document} -port {port number}");
             System.exit(-1);
        }

        String rootDirectory = args[1];
        int portNumber = Integer.parseInt(args[3]);
        
        if(portNumber <= 8000 || portNumber >= 9999){
            System.out.println("Kindly refrain to using ports between 8000 and 9999 for testing purposes.");
        }

        File root = new File(rootDirectory);
 
        if (!root.isDirectory()) {
            System.out.println("Directory doesn't exist!! Please check the path again");
            System.exit(-1);
        }
        
		try(ServerSocket server = new ServerSocket(portNumber)){
        	System.out.println("Listening for connection on port "+portNumber+ "....");
        		
			while (true) {
        		Socket socket = server.accept();
                Thread t = new Thread(new Multithread(server, socket, rootDirectory));
				t.start();
        	}
        } catch(SocketTimeoutException e){
            System.out.println("Connection Closed");
        } catch(IOException e){
        	System.err.println("Cannot listen on port: "+ portNumber);
            e.printStackTrace();
        }               
    }    
}