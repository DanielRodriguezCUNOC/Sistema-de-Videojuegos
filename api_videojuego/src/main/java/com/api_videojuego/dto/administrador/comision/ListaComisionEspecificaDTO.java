package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ListaComisionEspecificaDTO {

	private List<ComisionEspecificaResponseDTO> comisiones;

	// * Getters and Setters */
	public List<ComisionEspecificaResponseDTO> getComisiones() {
		return comisiones;
	}

	public void setComisiones(List<ComisionEspecificaResponseDTO> comisiones) {
		this.comisiones = comisiones;
	}

	public void agregarComisionEspecifica(Integer idComisionEspecifica,
			String nombreEmpresa, BigDecimal comisionEspecifica,
			Date fechaActualizacion) {
		this.comisiones.add(new ComisionEspecificaResponseDTO(idComisionEspecifica,
				nombreEmpresa, comisionEspecifica, fechaActualizacion));
	}
}
