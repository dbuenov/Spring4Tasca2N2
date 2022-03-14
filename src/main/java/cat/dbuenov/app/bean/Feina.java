package cat.dbuenov.app.bean;

import java.util.Objects;

public class Feina {
	private String nom;
	private float sou;
	
	
	public Feina(String nom) {
		
		this.nom = nom;
		if (this.nom.equalsIgnoreCase("tecnic")) {
			this.sou = 1600F;
		}else if(this.nom.equalsIgnoreCase("comercial")) {
			this.sou = 1800F;
		}else if(this.nom.equalsIgnoreCase("gerent")) {
			this.sou = 2000F;
		}else {
			this.sou = 1000F;
		}		
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public float getSou() {
		return sou;
	}
	public void setSou(float sou) {
		this.sou = sou;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nom, sou);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feina other = (Feina) obj;
		return Objects.equals(nom, other.nom) && Float.floatToIntBits(sou) == Float.floatToIntBits(other.sou);
	}
	@Override
	public String toString() {
		return "Feina [nom=" + nom + ", sou=" + sou + "]";
	}
	
	
	
}
