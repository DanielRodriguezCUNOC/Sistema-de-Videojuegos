package com.api_videojuego.resources.endpoints.tienda;

import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.services.tienda.TiendaService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tienda")
public class TiendaResource {

	@Path("/obtener-videojuegos-titulo/{titulo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosPorTitulo(
			@PathParam("titulo") String titulo) {
		TiendaService service = new TiendaService();
		try {
			return Response.ok(service.obtenerVideojuegosPorTitulo(titulo)).build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/obtener-videojuegos/{offset}/{limit}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosParaTienda(@PathParam("offset") int offset,
			@PathParam("limit") int limit) {
		TiendaService service = new TiendaService();
		try {
			return Response.ok(service.obtenerVideojuegosParaTienda(offset, limit))
					.build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/obtener-videojuegos-categoria/{idCategoria}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegosPorCategoria(
			@PathParam("idCategoria") Integer idCategoria) {
		TiendaService service = new TiendaService();
		try {
			return Response.ok(service.obtenerVideojuegosPorCategoria(idCategoria))
					.build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}
}
