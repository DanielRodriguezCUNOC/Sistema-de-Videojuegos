package com.api_videojuego.resources.endpoints.gamer.cartera;

import com.api_videojuego.dto.gamer.cartera.RecargarCarteraRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.services.gamer.cartera.CarteraDigitalService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gamer/cartera-digital")
public class CarteraDigitalResource {

	@Path("/recargar-cartera")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recargarCarteraDigital(RecargarCarteraRequestDTO requestDTO) {
		CarteraDigitalService service = new CarteraDigitalService();

		try {
			service.recargarCarteraDigital(requestDTO);
			return Response.ok().build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	@Path("/obtener-saldo/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerSaldoCarteraDigital(
			@PathParam("idUsuario") Integer idUsuario) {
		CarteraDigitalService service = new CarteraDigitalService();
		try {
			return Response.ok(service.obtenerSaldo(idUsuario)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

}
