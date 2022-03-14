package cat.dbuenov.app.dades;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import cat.dbuenov.app.bean.*;

@Component
public class BaseDeDades {
	
	ArrayList<Empleat> empleats;
	int id=1;
	
	public BaseDeDades() {
		empleats = new ArrayList<Empleat>();		
	}
	
	// es la base de dades la que controla el id
	
	public void add(Empleat empleat) {
		empleat.setId(id);
		id++;
		empleats.add(empleat);
	}

	public ArrayList<Empleat> getEmpleats() {
		return empleats;
	}

	public void setEmpleats(ArrayList<Empleat> empleats) {
		this.empleats = empleats;
	}
	
	public Empleat buscaId(int id) {
		Empleat empleat=null;
		boolean trobat=false;
		int i=0;
		
		while(!trobat && empleats.size()>i) {
			if(empleats.get(i).getId()==id) {
				empleat = empleats.get(i);
				trobat = true;
			}
			i++;
		}
		
		return empleat;			
	}
	
	public ArrayList<Empleat> buscaPerFeina(String feina){
		
		ArrayList<Empleat> feines = new ArrayList<Empleat>();
				
		for (Empleat empleat : empleats) {
			if (empleat.getFeina().getNom().equalsIgnoreCase(feina)) {
				feines.add(empleat);
			}
		}
		
		return feines;		
	}
	
	public void esborraPerId(int id) {
		Iterator<Empleat> it = empleats.iterator();
		Empleat empleat=null;
		boolean trobat=false;
		while(it.hasNext() && !trobat) {
			empleat=it.next();
			if (empleat.getId()==id) {
				it.remove();
				trobat=true;
			}			
		}
	}
}
