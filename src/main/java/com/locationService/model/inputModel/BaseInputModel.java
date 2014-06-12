package com.locationService.model.inputModel;

import javax.xml.bind.annotation.XmlRootElement;

import com.locationService.exception.InvalidInputException;

@XmlRootElement
public class BaseInputModel {

	public void checkInput() throws InvalidInputException{
		
	}
}
