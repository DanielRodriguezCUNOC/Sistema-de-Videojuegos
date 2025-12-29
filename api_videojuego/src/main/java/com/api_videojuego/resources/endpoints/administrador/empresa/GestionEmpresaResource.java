package com.api_videojuego.resources.endpoints.administrador.empresa;

import com.api_videojuego.dto.administrador.empresa.CambiarEstadoEmpresaDTO;
import com.api_videojuego.dto.administrador.empresa.EditarEmpresaRequetDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.services.administrador.empresa.GestionarEmpresaService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/gestion-empresa")
public class GestionEmpresaResource {

	@Path("/editar-empresa")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editarEmpresa(EditarEmpresaRequetDTO editarEmpresaRequetDTO) {
		GestionarEmpresaService gestionarEmpresaService = new GestionarEmpresaService();
		try {

			gestionarEmpresaService.editarEmpresa(editarEmpresaRequetDTO);
			return Response.status(Response.Status.OK)
					.entity("Empresa editada correctamente").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \" " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/cambiar-estado")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cambiarEstadoEmpresa(
			CambiarEstadoEmpresaDTO cambiarEstadoEmpresaDTO) {

		GestionarEmpresaService gestionarEmpresaService = new GestionarEmpresaService();
		try {
			gestionarEmpresaService.cambiarEstadoEmpresa(cambiarEstadoEmpresaDTO);

			return Response.status(Response.Status.OK)
					.entity("Empresa editada correctamente").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \" " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/obtener-empresas")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerEmpresas() {
		GestionarEmpresaService gestionarEmpresaService = new GestionarEmpresaService();
		try {
			return Response.status(Response.Status.OK)
					.entity(gestionarEmpresaService.obtenerEmpresas())
					.header("Content-Type", "application/json").build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \" " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

}
