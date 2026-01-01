package com.api_videojuego.resources.endpoints.perfiles;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.services.perfiles.PerfilVideojuegoService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/perfil/videojuego")
public class PerfilVideojuegoResource {

	@Path("/obtener-informacion/{idVideojuego}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerInformacionPerfilVideojuego(
			@PathParam("idVideojuego") Integer idVideojuego) {
		PerfilVideojuegoService service = new PerfilVideojuegoService();
		try {
			return Response.ok()
					.entity(service.obtenerInformacionVideojuego(idVideojuego)).build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error al consultar la base de datos: " + e.getMessage())
					.build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error inesperado del servidor: " + e.getMessage()).build();
		}
	}

}
