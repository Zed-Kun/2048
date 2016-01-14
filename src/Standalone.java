import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import client.View;
import server.Model;



public class Standalone {
	 public static void main(String[] args) {
		 
		
		 
	        Thread server = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	  
	                try {
	                	 
	                	 
	                   server.Controller c = 
	                           new server.Controller(new Model(4), 2048);
	                	 
	                } catch (IOException e) {
	                    ;
	                }
	            }
	        });
	        
	        
	        
	        Thread client = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                   client.Controller c = 
	                            new client.Controller(new View(4), "localhost", 2048);
	                } catch (IOException e) {
	                    ;
	                }
	            }
	        });
	        
	        
	        

	        server.start();
	        client.start();
	        try {
	            client.join();
	            server.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
}
