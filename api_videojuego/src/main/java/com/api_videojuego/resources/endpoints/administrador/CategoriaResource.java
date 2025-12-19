package com.api_videojuego.resources.endpoints.administrador;

import com.api_videojuego.dto.categoria.CrearCategoriaDTO;
import com.api_videojuego.dto.categoria.EditarCategoriaDTO;
import com.api_videojuego.dto.categoria.ListaCategoriaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.services.administrador.categoria.CRUDCategoriaService;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categoria")
public class CategoriaResource {

	// * Función crearCategoria */
	@Path("/crear-categoria")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response crearCategoria(
			@BeanParam CrearCategoriaDTO crearCategoriaDTO) {

		CRUDCategoriaService service = new CRUDCategoriaService();

		try {
			service.crearCategoria(crearCategoriaDTO);

			return Response.status(Response.Status.CREATED)
					.entity("{\"mensaje\": \"Categoría creada exitosamente\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatoYaExiste e) {
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al insertar en la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error interno del servidor\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	// * Funcion obtener categorias */
	@Path("/obtener-categorias")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCategorias() {
		CRUDCategoriaService service = new CRUDCategoriaService();

		try {
			ListaCategoriaDTO categorias = service.obtenerCategorias();

			return Response.status(Response.Status.OK).entity(categorias)
					.header("Content-Type", "application/json").build();

		} catch (ErrorConsultaDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al consultar la base de datos\"}")
					.header("Content-Type", "application/json").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error interno del servidor\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	// * Funcion editarCategoria */
	@Path("/editar-categoria")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response editarCategoria(
			@BeanParam EditarCategoriaDTO editarCategoriaDTO) {
		CRUDCategoriaService service = new CRUDCategoriaService();
		try {
			service.editarCategoria(editarCategoriaDTO);

			return Response.status(Response.Status.OK)
					.entity("{\"mensaje\": \"Categoría editada exitosamente\"}")
					.header("Content-Type", "application/json").build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al insertar en la base de datos\"}")
					.header("Content-Type", "application/json").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error interno del servidor\"}")
					.header("Content-Type", "application/json").build();
		}
	}

	// * Funcion eliminarCategoria */
	@Path("/eliminar-categoria/{idCategoria}")
	@DELETE
	public Response eliminarCategoria(
			@PathParam("idCategoria") Integer idCategoria) {

		CRUDCategoriaService service = new CRUDCategoriaService();
		try {
			service.eliminarCategoria(idCategoria);

			return Response.status(Response.Status.NO_CONTENT).build();

		} catch (DatosInvalidos e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"error\": \"" + e.getMessage() + "\"}")
					.header("Content-Type", "application/json").build();

		} catch (ErrorInsertarDB e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error al eliminar en la base de datos\"}")
					.header("Content-Type", "application/json").build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"error\": \"Error interno del servidor\"}")
					.header("Content-Type", "application/json").build();
		}
	}

}
