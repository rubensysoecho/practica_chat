package servidor;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Mensaje implements Serializable{

	private Object mensaje;
	private TipoMensaje tipomensaje;
	
	public Mensaje(Object mensaje, TipoMensaje tipomensaje) {
		super();
		this.mensaje = mensaje;
		this.tipomensaje = tipomensaje;
	}

	public Object getMensaje() {
		return mensaje;
	}

	public TipoMensaje getTipomensaje() {
		return tipomensaje;
	}

	public enum TipoMensaje {
		NICKNUEVO,ACEPTADO,NOACEPTADO,LISTANICKS,CONECTANDO,NORMAL,DESCONEXION,DESCONEXIONACEPTADA;
	}
}
