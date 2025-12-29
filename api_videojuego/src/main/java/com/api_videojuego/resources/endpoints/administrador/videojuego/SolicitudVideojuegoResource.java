package com.api_videojuego.resources.endpoints.administrador.videojuego;

import com.api_videojuego.dto.administrador.videojuego.SolicitudVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.administrador.videojuego.SolicitudVideojuegoService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/solicitud-videojuego")
public class SolicitudVideojuegoResource {

	@Path("/listar-solicitudes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSolicitudes() {
		SolicitudVideojuegoService service = new SolicitudVideojuegoService();
		try {

			return Response.status(Response.Status.OK)
					.entity(service.listarSolicitudesVideojuego())
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\":\"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/gestionar-solicitud")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response gestionarSolicitud(
			SolicitudVideojuegoRequestDTO solicitudVideojuegoRequestDTO) {
		SolicitudVideojuegoService service = new SolicitudVideojuegoService();
		try {
			service.gestionarAccionesSolicitud(solicitudVideojuegoRequestDTO);

			return Response.status(Response.Status.OK)
					.entity("{\"message\":\"Solicitud gestionada\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\":\"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorEliminarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\":\"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\":\"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}

	}
}
