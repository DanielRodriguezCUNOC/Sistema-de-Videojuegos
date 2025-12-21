package com.api_videojuego.resources.endpoints.administrador.comision;

import com.api_videojuego.dto.administrador.comision.ComisionEspecificaRequestDTO;
import com.api_videojuego.dto.administrador.comision.EditarComisionEspecificaDTO;
import com.api_videojuego.dto.administrador.comision.ListaComisionEspecificaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.administrador.comision.ComisionEspecificaService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/comision-especifica")
public class ComisionEspecificaResource {

	@Path("/registrar-comision")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registrarComisionEspecifica(
			@BeanParam ComisionEspecificaRequestDTO comision) {
		ComisionEspecificaService comisionService = new ComisionEspecificaService();
		try {
			comisionService.registrarComisionEspecifica(comision);
			return Response.status(Response.Status.CREATED)
					.entity("{\"mensaje\": \"Comisión registrada exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"mensaje\": \"Datos inválidos\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"mensaje\": \"La comisión específica ya existe\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error al insertar en la base de datos\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error del servidor, intentelo mas tarde\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/actualizar-comision")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarComisionEspecifica(
			@BeanParam EditarComisionEspecificaDTO comision) {
		ComisionEspecificaService comisionService = new ComisionEspecificaService();

		try {
			comisionService.actualizarComisionEspecifica(comision);
			return Response.status(Response.Status.OK)
					.entity("{\"mensaje\": \"Comisión editada exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"mensaje\": \"Datos inválidos\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"mensaje\": \"Error al actualizar el registro\"}")
					.header("Content-Type", "application/json").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error del servidor, intentelo mas tarde\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/obtener-comisiones")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerComisionesEspecificas() {
		ComisionEspecificaService comisionService = new ComisionEspecificaService();
		try {
			ListaComisionEspecificaDTO comisiones = comisionService
					.obtenerComisionesEspecificas();
			return Response.status(Response.Status.OK).entity(comisiones)
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"mensaje\": \"Error del servidor, intentelo mas tarde\"}")
					.header("Content-Type", "application/json").build();
		}
	}

}