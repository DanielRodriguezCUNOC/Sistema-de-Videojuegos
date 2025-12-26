package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;

public class EditarComisionGlobalDTO {
	private Integer id;
	private BigDecimal comision;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public boolean esValida() {
		return comision != null && comision.compareTo(BigDecimal.ZERO) >= 0
				&& comision.compareTo(new BigDecimal("100")) <= 0 && id != null
				&& id > 0;
	}
}
