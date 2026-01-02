package com.api_videojuego.dto.gamer.grupo;

import java.util.ArrayList;
import java.util.List;

public class GrupoResponseDTO {

	private Integer idGrupo;
	private String nombreGrupo;
	private boolean estado;
	private List<IntegranteGrupoDTO> integrantes;

	public GrupoResponseDTO() {
		this.integrantes = new ArrayList<>();
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<IntegranteGrupoDTO> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<IntegranteGrupoDTO> integrantes) {
		this.integrantes = integrantes;
	}

	public void addIntegrante(IntegranteGrupoDTO integrante) {
		this.integrantes.add(integrante);
	}

}
