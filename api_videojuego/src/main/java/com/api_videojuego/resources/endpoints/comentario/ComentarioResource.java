package com.api_videojuego.resources.endpoints.comentario;

import com.api_videojuego.dto.videojuego.ComentarioRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.comentario.ComentarioService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/comentarios")
public class ComentarioResource {

	@Path("/obtener-comentarios/{idVideojuego}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerComentario(
			@PathParam("idVideojuego") Integer idVideojuego) {
		ComentarioService comentarioService = new ComentarioService();

		try {
			return Response
					.ok(comentarioService.obtenerComentariosPorVideojuego(idVideojuego))
					.build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"Error al obtener los comentarios del videojuego: " + e.getMessage())
					.build();
		}
	}

	@Path("/agregar-comentario")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response agregarComentario(ComentarioRequestDTO comentarioDTO) {
		ComentarioService comentarioService = new ComentarioService();

		try {
			comentarioService.agregarComentario(comentarioDTO);
			return Response.status(Response.Status.CREATED).build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al agregar el comentario: " + e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Datos invalidos: " + e.getMessage()).build();
		}
	}
}
