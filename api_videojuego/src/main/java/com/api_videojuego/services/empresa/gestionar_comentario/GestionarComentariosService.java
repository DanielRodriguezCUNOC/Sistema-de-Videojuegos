package com.api_videojuego.services.empresa.gestionar_comentario;

import java.util.List;

import com.api_videojuego.db.empresa.EmpresaDesarrolladoraDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoEmpresaRequestDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoEmpresaResponseDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoVideojuegoResponseDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class GestionarComentariosService {

	private EmpresaDesarrolladoraDB empresaDesarrolladoraDB;
	private VideojuegoDesarrolladoraDB videojuegoDesarrolladoraDB;

	public GestionarComentariosService() {
		this.empresaDesarrolladoraDB = new EmpresaDesarrolladoraDB();
		this.videojuegoDesarrolladoraDB = new VideojuegoDesarrolladoraDB();
	}

	public EditarEstadoEmpresaResponseDTO obtenerEstadoComentariosEmpresa(
			Integer idEmpresa) throws ErrorConsultaDB {
		try {
			return empresaDesarrolladoraDB.obtenerEstadoComentariosEmpresa(idEmpresa);

		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public void cambiarEstadoComentariosEmpresa(
			EditarEstadoEmpresaRequestDTO editarEstadoEmpresaRequestDTO)
			throws ErrorActualizarRegistro {
		try {
			empresaDesarrolladoraDB
					.cambiarEstadoComentarios(editarEstadoEmpresaRequestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}

	public List<EditarEstadoVideojuegoResponseDTO> obtenerEstadoComentariosVideojuegosEmpresa(
			Integer idEmpresa) throws ErrorConsultaDB {
		try {
			return videojuegoDesarrolladoraDB
					.obtenerEstadoComentariosVideojuegosEmpresa(idEmpresa);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public void cambiarEstadoComentariosVideojuego(
			EditarEstadoVideojuegoRequestDTO editarEstadoVideojuegoRequestDTO)
			throws ErrorActualizarRegistro {
		try {
			videojuegoDesarrolladoraDB
					.cambiarEstadoComentariosVideojuego(editarEstadoVideojuegoRequestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}
}
