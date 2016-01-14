package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import commun.Event;
import commun.Matrix;
import commun.Response;
public class Controller {
    private ServerSocket server;
    private Socket client;
    private boolean gameover;
    /**
     * Create a server listening on a given port.
     * @param m
     * @param port
     * @throws IOException
     */
    public Controller(int port) throws IOException {
        gameover = false;
        server = new ServerSocket(port);
        while(true)
        {
            Model model = new Model(4);
            client = server.accept();
            System.out.println("* client has connected");
            new Thread(new EventLoop(client, model)).start();
        }
    }

    /**
     * Reads the events from the client and reacts to them.
     */
    class EventLoop implements Runnable {
    	Socket socket;
    	Model model;
    	public EventLoop(Socket client, Model model)
    	{
    		this.socket = client;
    		this.model = model;
    	}
    	
        public void run() {
            ObjectOutputStream socket_out;
            ObjectInputStream socket_in;
            try {
                try {
                    model.addTile();
                } catch (NoFreeSpaceException e2) {
                    // will never happen
                }
                socket_out = new ObjectOutputStream(socket.getOutputStream());
                socket_in = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Matrix snapshot = model.getSnapshot();
                    
                    socket_out.writeObject(snapshot);
                    Event e = (Event)socket_in.readObject();
                    switch (e) {
                    case DOWN:
                    case UP:
                    case LEFT:
                    case RIGHT:
                        if (!gameover) {
                            model.setGravity(e.toDirection());
                        }
                        break;
                    case RESTART:
                        model = new Model(model.getSize());
                        gameover = false;
                        socket_out.writeObject(Response.NO_GAMEOVER);
                        break;
                    case CLOSE:
                        // never received
                        break;
                    }
                    if (!model.isMovePossible()) {
                        gameover = true;
                        socket_out.writeObject(Response.GAMEOVER);
                    }
                    try {
                        Matrix new_snapshot = model.getSnapshot();
                        if (!new_snapshot.equals(snapshot)) {
                            model.addTile();
                        }
                    } catch (NoFreeSpaceException e1) {
                        ;
                    }
                }
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("* Client has disconnected, closing...");
            }
        }
    }
}