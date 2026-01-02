package com.api_videojuego.resources.endpoints.gamer.grupo;

import com.api_videojuego.dto.gamer.grupo.EliminarIntegranteDTO;
import com.api_videojuego.dto.gamer.grupo.NuevoIntegranteRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.gamer.grupo.IntegranteGrupoService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/integrante-grupo")
public class IntegranteGrupoResource {

	@Path("/obtener-grupos/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerGrupos(@PathParam("idUsuario") Integer idUsuario) {

		IntegranteGrupoService service = new IntegranteGrupoService();

		try {
			return Response.status(Response.Status.OK)
					.entity(service.obtenerGrupo(idUsuario)).build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/eliminar-integrante")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response eliminarIntegrante(
			EliminarIntegranteDTO eliminarIntegranteDTO) {
		IntegranteGrupoService service = new IntegranteGrupoService();
		try {

			service.eliminarIntegrante(eliminarIntegranteDTO);
			return Response.status(Response.Status.OK).build();

		} catch (ErrorEliminarRegistro e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		}

	}

	@Path("/agregar-integrante")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response agregarIntegrante(
			NuevoIntegranteRequestDTO nuevoIntegranteRequestDTO) {
		IntegranteGrupoService service = new IntegranteGrupoService();
		try {
			service.agregarIntegrante(nuevoIntegranteRequestDTO);
			return Response.status(Response.Status.CREATED).build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		}
	}

}
