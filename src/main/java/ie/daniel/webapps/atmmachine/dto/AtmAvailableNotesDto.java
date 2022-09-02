package ie.daniel.webapps.atmmachine.dto;

import java.util.List;

import ie.daniel.webapps.atmmachine.model.AtmNotes;

public class AtmAvailableNotesDto {

    private List<AtmNotes> atmNotes;
    private Integer atmBalance;

    
	public AtmAvailableNotesDto(List<AtmNotes> atmNotes, Integer totalBalance) {
		this.atmNotes = atmNotes;
		this.atmBalance = totalBalance;
	}

	public List<AtmNotes> getAtmNotes() {
		return atmNotes;
	}

	public void setAtmNotes(List<AtmNotes> atmNotes) {
		this.atmNotes = atmNotes;
	}

	public Integer getAtmBalance() {
		return atmBalance;
	}

	public void setAtmBalance(Integer atmBalance) {
		this.atmBalance = atmBalance;
	}
    
    
}
