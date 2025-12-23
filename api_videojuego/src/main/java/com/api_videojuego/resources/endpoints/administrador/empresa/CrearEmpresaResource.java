package com.api_videojuego.resources.endpoints.administrador.empresa;

import com.api_videojuego.dto.administrador.empresa.CrearEmpresaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.services.administrador.empresa.CrearEmpresaService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empresa")
public class CrearEmpresaResource {

	@Path("/crear-empresa")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response crearEmpresa(@BeanParam CrearEmpresaDTO crearEmpresaDTO) {

		CrearEmpresaService service = new CrearEmpresaService();

		try {
			service.crearEmpresa(crearEmpresaDTO);

			return Response.status(Response.Status.CREATED)
					.entity("{\"mensaje\": \"Empresa creada exitosamente\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (UsuarioYaRegistrado e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorEncriptacion e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(
							"{\"error\": \"Error al procesar la información de seguridad\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al guardar en la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();

		}
	}
}
