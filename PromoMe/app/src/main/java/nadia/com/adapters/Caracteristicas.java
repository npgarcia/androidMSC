package nadia.com.adapters;

public class Caracteristicas {
	private String caracteristica;
	private String valor;
	private String unidad;

	public Caracteristicas(){}

	public Caracteristicas(String k, String v, String u){
		caracteristica = k;
		valor = v;
		unidad = u;
	}

	public Caracteristicas(String k, String v){
		caracteristica = k;
		valor = v;
		unidad = "";
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
