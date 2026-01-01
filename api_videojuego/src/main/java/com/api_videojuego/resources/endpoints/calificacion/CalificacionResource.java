package com.api_videojuego.resources.endpoints.calificacion;

import com.api_videojuego.dto.videojuego.CalificacionVideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.calificacion.CalificacionService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/calificacion-videojuego")
public class CalificacionResource {

	@Path("/registrar-calificacion")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarCalificacionVideojuego(
			CalificacionVideojuegoRequestDTO calificacionRequest) {
		CalificacionService calificacionService = new CalificacionService();

		try {
			calificacionService.registrarCalificacionVideojuego(calificacionRequest);
			return Response.ok().build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
