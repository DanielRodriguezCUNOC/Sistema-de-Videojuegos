package com.api_videojuego.resources.endpoints.gamer.compra;

import com.api_videojuego.dto.gamer.compra.ComprarVideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.gamer.compra.ComprarVideojuegoService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gamer/compra")
public class ComprarVideojuegoResource {

	@Path("/comprar-videojuego")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response comprarVideojuego(ComprarVideojuegoRequestDTO requestDTO) {
		ComprarVideojuegoService service = new ComprarVideojuegoService();
		try {
			service.comprarVideojuego(requestDTO);
			return Response.ok().build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage())
					.build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error de actualización: " + e.getMessage()).build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error de insercion: " + e.getMessage()).build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error de consulta: " + e.getMessage()).build();
		}
	}

}
