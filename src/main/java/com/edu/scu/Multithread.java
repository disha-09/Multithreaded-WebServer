package com.edu.scu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Multithread implements Runnable{

    Socket socket;
    String rootDirectory;
    ServerSocket server;

    public Multithread(ServerSocket server, Socket socket, String rootDirectory){
        this.server = server;
        this.socket = socket;
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void run() {
        
        try {
            
            InputStreamReader isr =  new InputStreamReader(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();
            System.out.println("Client Request: " + line);

            if(line == null || line.isBlank()){
                System.out.println("Response: "+Constants.statusLine400);
                String response = HttpResponse.getHeader(Constants.statusLine400, "", Constants.entityBody400.length());
                outputStream.writeBytes(response);
                outputStream.writeBytes(Constants.CRLF);
                outputStream.write(Constants.entityBody400.getBytes());
                return;
            }

            String[] request = line.split("\\s+");
            String requestMethod = request[0];
            String requestedFile = request[1];
            String httpVersion = request[2];

            if(httpVersion.equals(Constants.HTTP0)){
                server.setSoTimeout(10000);
            }else if(httpVersion.equals(Constants.HTTP1)){
                server.setSoTimeout(15000);                                  
            }else{
                System.out.println("Response: "+Constants.statusLine505);
                String response = HttpResponse.getHeader(Constants.statusLine505, "", Constants.entityBody505.length());
                outputStream.writeBytes(response);
                outputStream.writeBytes(Constants.CRLF);
                outputStream.write(Constants.entityBody505.getBytes());
                return;
            }
            
            if(requestMethod.equals("POST") || requestMethod.equals("PUT") 
                || requestMethod.equals("DELETE")){
                    System.out.println("Response: "+Constants.statusLine501);
                    String response = HttpResponse.getHeader(httpVersion+Constants.statusLine501, "", Constants.entityBody501.length());
                    outputStream.writeBytes(response);
                    outputStream.writeBytes(Constants.CRLF);
                    outputStream.write(Constants.entityBody501.getBytes());
                return;
            }
            
            if(requestedFile.equals("/")){
                requestedFile = "index.html";
            }

            Path filepath = Paths.get(rootDirectory, requestedFile);
            
            if(Files.exists(filepath)){
                if (Files.isDirectory(filepath)) {
                    System.out.println("Response: "+Constants.statusLine400);
                    String response = HttpResponse.getHeader(httpVersion+Constants.statusLine400, "", Constants.entityBody400.length());
                    outputStream.writeBytes(response);
                    outputStream.writeBytes(Constants.CRLF);
                    outputStream.write(Constants.entityBody400.getBytes());
                    return;
                }else if(!Files.isReadable(filepath)){
                    System.out.println("Response: "+Constants.statusLine403);
                    String response = HttpResponse.getHeader(httpVersion+Constants.statusLine403, "", Constants.statusLine403.length());
                    outputStream.writeBytes(response);
                    outputStream.writeBytes(Constants.CRLF);
                    outputStream.write(Constants.statusLine403.getBytes());
                }else{
                    byte[] entityBody = Files.readAllBytes(filepath);
                    System.out.println("Response: "+Constants.statusLine200);
                    String response = HttpResponse.getHeader(httpVersion+Constants.statusLine200, requestedFile, entityBody.length);
                    outputStream.writeBytes(response);
                    outputStream.writeBytes(Constants.CRLF);
                    if(entityBody != null)
                        outputStream.write(entityBody);
                }
            }else{
                System.out.println("Response: "+Constants.statusLine404);
                String response = HttpResponse.getHeader(httpVersion+Constants.statusLine404, "", Constants.enetityBody404.length());
                outputStream.writeBytes(response);
                outputStream.writeBytes(Constants.CRLF);
                outputStream.write(Constants.enetityBody404.getBytes());
            }
            outputStream.close();
            reader.close();
            
        }
        catch(SocketTimeoutException e){
            System.out.println("Connection Closed");
        } 
        catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }    
}