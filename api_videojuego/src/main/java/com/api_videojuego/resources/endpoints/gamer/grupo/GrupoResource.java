package com.api_videojuego.resources.endpoints.gamer.grupo;

import com.api_videojuego.dto.gamer.grupo.EditarEstadoGrupoRquestDTO;
import com.api_videojuego.dto.gamer.grupo.EditarGrupoRequestDTO;
import com.api_videojuego.dto.gamer.grupo.GrupoRequestDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.gamer.grupo.GrupoService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/grupo")
public class GrupoResource {

	@Path("/registrar-grupo")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarGrupo(GrupoRequestDTO grupoRequestDTO) {
		GrupoService grupoService = new GrupoService();
		try {
			grupoService.registrarGrupo(grupoRequestDTO);
			return Response.status(Response.Status.CREATED).build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage())
					.build();
		}
	}

	@Path("/editar-grupo")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editarGrupo(EditarGrupoRequestDTO editarGrupoRequestDTO) {
		GrupoService grupoService = new GrupoService();
		try {
			grupoService.editarGrupo(editarGrupoRequestDTO);
			return Response.status(Response.Status.OK).build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage())
					.build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.NOT_MODIFIED)
					.entity(e.getMessage()).build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/cambiar-estado-grupo")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cambiarEstadoGrupo(
			EditarEstadoGrupoRquestDTO editarEstadoGrupoRquestDTO) {
		GrupoService grupoService = new GrupoService();
		try {
			grupoService.cambiarEstadoGrupo(editarEstadoGrupoRquestDTO);
			return Response.status(Response.Status.OK).build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.NOT_MODIFIED)
					.entity(e.getMessage()).build();
		}
	}
}
