package com.github.daniel.ifood.cadastro.infra;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

	default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		return true;
	}
}
