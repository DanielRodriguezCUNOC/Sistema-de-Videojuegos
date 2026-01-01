package com.api_videojuego.resources.endpoints.empresa.comentarios;

import com.api_videojuego.dto.empresa.comentarios.EditarEstadoEmpresaRequestDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.services.empresa.gestionar_comentario.GestionarComentariosService;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empresa/gestionar-comentarios")
public class GestionComentariosResource {

	@Path("/estado-comentarios-empresa/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerEstadoComentariosEmpresa(
			@PathParam("idUsuario") Integer idUsuario) {
		GestionarComentariosService service = new GestionarComentariosService();
		try {
			return Response.ok(service.obtenerEstadoComentariosEmpresa(idUsuario))
					.build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/actualizar-estado-empresa")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarEstadoComentariosEmpresa(
			EditarEstadoEmpresaRequestDTO estadoDTO) {
		GestionarComentariosService service = new GestionarComentariosService();
		try {
			service.cambiarEstadoComentariosEmpresa(estadoDTO);
			return Response.ok().build();

		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/estado-comentarios-videojuego/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerEstadoComentariosVideojuegosEmpresa(
			@PathParam("idUsuario") Integer idUsuario) {
		GestionarComentariosService service = new GestionarComentariosService();
		try {
			return Response
					.ok(service.obtenerEstadoComentariosVideojuegosEmpresa(idUsuario))
					.build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		}
	}

	@Path("/actualizar-estado-videojuego")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarEstadoComentariosVideojuegosEmpresa(
			EditarEstadoVideojuegoRequestDTO estadoDTO) {
		GestionarComentariosService service = new GestionarComentariosService();
		try {
			service.cambiarEstadoComentariosVideojuego(estadoDTO);
			return Response.ok().build();

		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
