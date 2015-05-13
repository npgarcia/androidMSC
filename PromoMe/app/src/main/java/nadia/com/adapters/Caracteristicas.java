package nadia.com.adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class Caracteristicas implements Parcelable{
	private String caracteristica;
	private String valor;
	private String unidad;
	private boolean isGeneral;

	public Caracteristicas(){}

	public Caracteristicas(String k, String v, String u, boolean general){
		caracteristica = k;
		valor = v;
		unidad = u;
		isGeneral = general;
	}

	public Caracteristicas(String k, String v){
		caracteristica = k;
		valor = v;
		unidad = "";
	}

	public Caracteristicas(Parcel in){
		String[] data = new String[3];

		in.readStringArray(data);
		this.caracteristica = data[0];
		this.valor = data[1];
		this.unidad = data[2];

		boolean [] booleans = new boolean[1];
		in.readBooleanArray(booleans);
		this.isGeneral = booleans[0];
	}

	public boolean isGeneral() {
		return isGeneral;
	}

	public void setIsGeneral(boolean isGeneral) {
		this.isGeneral = isGeneral;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String [] data = {this.caracteristica,this.valor,this.unidad};
		dest.writeStringArray(data);

		boolean [] booleans = {this.isGeneral};
		dest.writeBooleanArray(booleans);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Caracteristicas createFromParcel(Parcel in) {
			return new Caracteristicas(in);
		}

		public Caracteristicas[] newArray(int size) {
			return new Caracteristicas[size];
		}
	};
}
