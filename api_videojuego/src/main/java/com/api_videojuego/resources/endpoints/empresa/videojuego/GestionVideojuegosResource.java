package com.api_videojuego.resources.endpoints.empresa.videojuego;

import java.io.IOException;

import com.api_videojuego.dto.empresa.videojuego.EditarEstadoVideojuegoDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarPortadaVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.services.empresa.videojuego.CrearVideojuegoService;
import com.api_videojuego.services.empresa.videojuego.GestionarVideojuegoService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empresa/gestion_videojuegos")
public class GestionVideojuegosResource {

	@Path("/crear-videojuego")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response crearVideojuego(
			@BeanParam VideojuegoRequestDTO crearVideojuegoDTO) {

		CrearVideojuegoService service = new CrearVideojuegoService();

		try {
			service.crearVideojuego(crearVideojuegoDTO);

			return Response.status(Response.Status.CREATED)
					.entity("{\"mensaje\": \"Videojuego creado exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error inesperado: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		}

	}

	@Path("/cambiar-estado")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cambiarEstadoVideojuego(
			EditarEstadoVideojuegoDTO cambiarEstadoDTO) {
		GestionarVideojuegoService service = new GestionarVideojuegoService();

		try {
			service.cambiarEstadoVideojuego(cambiarEstadoDTO);

			return Response.status(Response.Status.OK).entity(
					"{\"mensaje\": \"Visibilidad del videojuego actualizada exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}

	}

	@Path("/editar-videojuego")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editarVideojuego(
			EditarVideojuegoRequestDTO editarVideojuegoDTO) {
		GestionarVideojuegoService service = new GestionarVideojuegoService();

		try {
			service.editarVideojuego(editarVideojuegoDTO);

			return Response.status(Response.Status.OK)
					.entity("{\"mensaje\": \"Videojuego editado exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}

	}

	@Path("/editar-portada")
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response editarPortadaVideojuego(
			@BeanParam EditarPortadaVideojuegoRequestDTO editarPortadaDTO) {
		GestionarVideojuegoService service = new GestionarVideojuegoService();
		try {
			service.cambiarPortadaVideojuego(editarPortadaDTO);

			return Response.status(Response.Status.OK)
					.entity(
							"{\"mensaje\": \"Portada del videojuego editada exitosamente\"}")
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorActualizarRegistro e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \" " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (AvatarExcepcion e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al procesar la imagen: " + e.getMessage()
							+ "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ExcepcionInesperada e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error inesperado: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}

	}

	@Path("/catalogo/{idUsuario}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuegos(
			@PathParam("idUsuario") Integer idUsuario) {
		GestionarVideojuegoService service = new GestionarVideojuegoService();
		try {
			return Response.status(Response.Status.OK)
					.entity(service.obtenerVideojuegos(idUsuario))
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	@Path("/obtener-videojuego/{idVideojuego}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerVideojuego(
			@PathParam("idVideojuego") String idVideojuego) {
		GestionarVideojuegoService service = new GestionarVideojuegoService();
		try {
			return Response.status(Response.Status.OK)
					.entity(service.obtenerDatosVideojuego(idVideojuego))
					.header("Content-Type", "application/json").build();
		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();
		}
	}

}