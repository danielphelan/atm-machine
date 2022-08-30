package ie.daniel.webapps.atmmachine.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AtmNotes {

	@Id
	private Integer id;

	private Integer note;

	private Integer count;

	
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

	
}
