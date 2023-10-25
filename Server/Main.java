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

                    // Read the first request from the client
                    StringBuilder request= new StringBuilder();

                    String line;
                    line = br.readLine();
                    while(!line.isBlank()) {
                        request.append(line + "\r\n");
                        line = br.readLine();
                    }

                    System.out.println("--REQUEST--");
                    System.out.println(request);
                    
                    // Decide how we'd like to respond

                        // Get the first line of the request
                        String firstLine = request.toString().split("\n")[0];

                        // Get the second "element" from the first line (separated by spaces)
                        String resource = firstLine.split(" ")[2];

                        // Compare the second element to our list of things
                        if (resource.equals("/image")) {
                            // Send back an image
                            // Load the image from the filesystem
                            FileInputStream image = new FileInputStream("image.jpg");
                            System.out.println(image.toString());
                        
                            // Send back the image
                            OutputStream clientOutput = client.getOutputStream();
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());
                            clientOutput.write((image.readAllBytes()));
                            clientOutput.flush();
                        }
                        
                        else if (resource.equals("/hello")) {
                            // Just send back a simple "Hello World"
                            OutputStream clientOutput = client.getOutputStream();
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());
                            clientOutput.write(("Hello World").getBytes());
                            clientOutput.flush();
                        }
                        
                        else {
                            // Just send back a simple "Hello World"
                            OutputStream clientOutput = client.getOutputStream();
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());
                            clientOutput.write(("Whatchya looking for?").getBytes());
                            clientOutput.flush();
                        }
                     
                     client.close();
                } 
            }
        }
    }
}
