package com.api_videojuego.resources.endpoints.administrador.comision;

import com.api_videojuego.dto.administrador.comision.EditarComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ObtenerComisionGlobalDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.services.administrador.comision.ComisionGlobalService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/comision-global")
public class ComisionGlobalResource {

	@Path("/actualizar-comision")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarComisionGlobal(EditarComisionGlobalDTO comision) {

		ComisionGlobalService comisionService = new ComisionGlobalService();

		try {
			comisionService.actualizarComisionGlobal(comision);
			return Response.status(Response.Status.OK)
					.entity("{\"mensaje\": \"Comisión editada exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"mensaje\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"mensaje\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}

	}

	@Path("/obtener-comision")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerComisionGlobal() {
		ComisionGlobalService comisionService = new ComisionGlobalService();

		try {
			ObtenerComisionGlobalDTO comision = comisionService
					.obtenerComisionGlobal();
			return Response.status(Response.Status.OK).entity(comision)
					.header("Content-Type", "application/json").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error del servidor, intentelo mas tarde\"}")
					.header("Content-Type", "application/json").build();
		}
	}

}
