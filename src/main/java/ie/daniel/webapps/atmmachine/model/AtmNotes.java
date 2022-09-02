package ie.daniel.webapps.atmmachine.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AtmNotes {

	@Id
	private Integer note;

	private Integer count;

	public AtmNotes() {
		
	}
	
	public AtmNotes(Integer note) {
		this.note = note;
	}
	
	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AtmNotes [note=" + note + ", count=" + count + "]";
	}

	
	
}
