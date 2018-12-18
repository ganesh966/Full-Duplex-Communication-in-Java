import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;


public class ServerChat {
	private ServerSocket serversocket;
	private Socket socket;
	private DataInputStream input1, input2;
	private DataOutputStream output1, output2;
	private boolean active = true;
	Thread t1 = new Thread(new Runnable() {
		@Override
		public void run() {
		String line = "";
			while(active){
				try{
					line = input1.readUTF();
					System.out.println("Client--->\t"+line);
					if(line.equals("OverNOut")){
						active = false;
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
	});
	Thread t2 = new Thread(new Runnable() {
		@Override
		public void run() {
			String line = "";
			while(active){
				try{
						line = input2.readLine();
						if(line.equals("OverNOut")){
							active = false;
						}
						output2.writeUTF(line);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
	});
	
	ServerChat(int port){
		try{
			serversocket = new ServerSocket(port);
			socket = serversocket.accept();
			input1 = new DataInputStream(socket.getInputStream());
			input2 = new DataInputStream(System.in);
			output2 = new DataOutputStream(socket.getOutputStream());
			output1 = new DataOutputStream(System.out);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println("**********Server is Ready**********");
		t1.start();
		t2.start();
	}
	
	
	
	public static void main(String[] args) {
		ServerChat server = new ServerChat(5005);
	}
}