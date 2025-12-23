package com.api_videojuego.resources.endpoints.empresa.videojuego;

import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.empresa.videojuego.CrearVideojuegoService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empresa")
public class CrearVideojuegoResource {

	@Path("/crear-videojuego")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response crearVideojuego(
			@BeanParam VideojuegoRequestDTO crearVideojuegoDTO) {

		CrearVideojuegoService service = new CrearVideojuegoService();

		try {
			service.crearVideojuego(crearVideojuegoDTO);

			return Response.status(Response.Status.CREATED)
					.entity("{\"mensaje\": \"Videojuego creado exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		}

	}
}
