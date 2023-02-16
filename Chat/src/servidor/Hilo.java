package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import servidor.Mensaje.TipoMensaje;

public class Hilo extends Thread {

	private Socket socket;
	private Compartida compartida;
	private String nick;
	private ventanaServidor ventana;
	
	public Hilo(Socket socket, Compartida compartida,ventanaServidor ventana) {
		super();
		this.socket = socket;
		this.compartida = compartida;
		this.ventana=ventana;
	}
	
	public void run() {
		try {
			ObjectInputStream entrada=new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream salida=new ObjectOutputStream(socket.getOutputStream());
			boolean fin=false;
			while(!fin) {
				Mensaje recibido=(Mensaje) entrada.readObject();
				
				switch (recibido.getTipomensaje()) {
				case CONECTANDO:
					nick=(String) recibido.getMensaje();
					if (compartida.comprobarnick(nick, salida)) {
						// Nick valido, cliente conectado
						Mensaje mensaje = new Mensaje("Nick correcto", TipoMensaje.ACEPTADO);
						salida.writeObject(mensaje);
						salida.flush();
						ventana.getAreaServidor().append("Cliente con IP " + socket.getInetAddress() + " con nick " +nick + " se ha conectado.\n");
						compartida.enviarnicks();
						compartida.enviaratodos(new Mensaje(nick + " se ha conectado.\n", TipoMensaje.NORMAL));
					}
					else {
						JOptionPane.showMessageDialog(null, recibido.getMensaje());
						fin=true;
					}
					break;
				case NORMAL:
					Mensaje mensaje=new Mensaje(nick+" dice: "+recibido.getMensaje()+"\n" , TipoMensaje.NORMAL);
					
					compartida.enviaratodos(mensaje);
					break;
				case DESCONEXION:
					Mensaje msg = new Mensaje("Desconexion aceptada", TipoMensaje.DESCONEXIONACEPTADA);
					salida.writeObject(msg);
					salida.flush();
					socket.close();
					compartida.desconectar(nick, salida);
					compartida.enviarnicks();
					compartida.enviaratodos(new Mensaje(nick + " se ha desconectado.\n", TipoMensaje.NORMAL));
					ventana.getAreaServidor().append("Cliente con IP " + socket.getInetAddress() + " con nick " +nick + " se ha desconectado.\n");
					fin = true;
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		finally {
			try {
				socket.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	
}
