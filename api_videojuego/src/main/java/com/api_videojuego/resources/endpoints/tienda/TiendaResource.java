package com.api_videojuego.resources.endpoints.tienda;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tienda")
public class TiendaResource {

	@Path("/obtener-videojuegos-titulo/{titulo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosPorTitulo(
			@PathParam("titulo") String titulo) {
		// Lógica para obtener videojuegos por título
		return Response.ok().entity(videojuegos).build();
	}

	@Path("/obtener-videojuegos/{offset}/{limit}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosParaTienda(@PathParam("offset") int offset,
			@PathParam("limit") int limit) {
		ntity(videojuegos).build();
	}

	@Path("/obtener-videojuego-categoria/{idCategoria}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosPorCategoria(
			@PathParam("idCategoria") Integer idCategoria) {
		return Response.ok().entity(videojuegos).build();
	}
}
