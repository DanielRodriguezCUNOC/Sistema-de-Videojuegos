package com.api_videojuego.resources.endpoints.gamer.biblioteca;

import com.api_videojuego.dto.gamer.biblioteca.VideojuegoCompradoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.services.gamer.biblioteca.BibliotecaService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gamer/biblioteca")
public class BibliotecaResource {

	@Path("/listado/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosComprados(
			@PathParam("idUsuario") Integer idUsuario) {
		BibliotecaService bibliotecaService = new BibliotecaService();

		try {
			return Response.ok(bibliotecaService.obtenerVideojuegos(idUsuario))
					.build();
		} catch (ErrorConsultaDB e) {

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@Path("/cambiar-estado-instalacion")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cambiarEstadoInstalacionVideojuego(
			VideojuegoCompradoRequestDTO videojuegoCompradoRequestDTO) {
		BibliotecaService bibliotecaService = new BibliotecaService();

		try {
			bibliotecaService
					.cambiarEstadoInstalacionVideojuego(videojuegoCompradoRequestDTO);
			return Response.ok().build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
