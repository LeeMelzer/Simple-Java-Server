import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Start receiving messages - ready to receive messages!
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started\nListening for messages.");     

            while(true) {
                // Handle a new incoming message
                try (Socket client = serverSocket.accept()) {
                    // client <-- messages queued up in it!
                    System.out.println("Debug: got new message " + client.toString());
                    
                    // Read the request - listen to the message
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    
                    BufferedReader br = new BufferedReader(isr);

                    StringBuilder request= new StringBuilder();

                    String line;
                    line = br.readLine();
                    while(!line.isBlank()) {
                        request.append(line + "\r\n");
                        line = br.readLine();
                    }

                    System.out.println("--REQUEST--");
                    System.out.println(request);


                } 
            }
        }
    }
}
