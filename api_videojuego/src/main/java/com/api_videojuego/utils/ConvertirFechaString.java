package com.api_videojuego.utils;

import java.time.LocalDate;

public class ConvertirFechaString {

	public static LocalDate convertirFechaALocalDate(String fecha) {
		if (fecha == null) {
			return null;
		}
		return LocalDate.parse(fecha);
	}
}
