package com.api_videojuego.resources.endpoints.usuario;

import com.api_videojuego.dto.usuario.reponse.UsuarioAdminResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioEmpresaResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioGamerResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.services.usuario.response.UsuarioResponseService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario/obtener")
public class UsuarioResponseResource {

	@Path("/admin/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioAdminResponse(
			@PathParam("idUsuario") Integer idUsuario) {

		UsuarioResponseService usuarioResponseService = new UsuarioResponseService();
		try {
			UsuarioAdminResponseDTO usuarioAdminResponseDTO = usuarioResponseService
					.usuarioAdminResponseDTO(idUsuario);

			return Response.status(Response.Status.OK).entity(usuarioAdminResponseDTO)
					.build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

	}

	@Path("/usuario-empresa/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioEmpresaResponse(
			@PathParam("idUsuario") Integer idUsuario) {

		UsuarioResponseService usuarioResponseService = new UsuarioResponseService();
		try {
			UsuarioEmpresaResponseDTO usuarioEmpresaResponseDTO = usuarioResponseService
					.usuarioEmpresaResponseDTO(idUsuario);

			return Response.status(Response.Status.OK)
					.entity(usuarioEmpresaResponseDTO).build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

	}

	@Path("/gamer/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioGamerResponse(
			@PathParam("idUsuario") Integer idUsuario) {

		UsuarioResponseService usuarioResponseService = new UsuarioResponseService();
		try {
			UsuarioGamerResponseDTO usuarioGamerResponseDTO = usuarioResponseService
					.usuarioGamerResponseDTO(idUsuario);

			return Response.status(Response.Status.OK).entity(usuarioGamerResponseDTO)
					.build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

	}

}
