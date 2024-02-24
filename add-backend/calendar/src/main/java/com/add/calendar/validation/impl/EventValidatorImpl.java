package com.add.calendar.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.add.calendar.validation.EventValidator;

import com.add.calendar.exception.event.EventNotValid;
import com.add.calendar.model.Event;

import java.util.Objects;

public class EventValidatorImpl implements ConstraintValidator<EventValidator, Event> {

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		if (Objects.isNull(event)) {
			throw new EventNotValid("Event can not be null");
		}
		if (event.getStart().isAfter(event.getEnd())) {
			throw new EventNotValid("Start date can not be after end date");
		}
		if (event.isReccurent() && (event.getDays() == null || event.getDays().isEmpty())) {
			throw new EventNotValid("Reccurent event must have a set of days not null and not empy");

		}
		return true;
	}
}