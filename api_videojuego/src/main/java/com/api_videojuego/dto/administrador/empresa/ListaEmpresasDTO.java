package com.api_videojuego.dto.administrador.empresa;

import java.util.List;

public class ListaEmpresasDTO {

	private List<ObtenerDatoEmpresaDTO> empresas;

	public List<ObtenerDatoEmpresaDTO> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<ObtenerDatoEmpresaDTO> empresas) {
		this.empresas = empresas;
	}

	public void agregarEmpresa(Integer idEmpresa, String nombreEmpresa,
			String descripcion, String estado) {
		this.empresas.add(new ObtenerDatoEmpresaDTO(idEmpresa, nombreEmpresa,
				descripcion, estado));
	}

}
