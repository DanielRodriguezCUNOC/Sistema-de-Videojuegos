package com.api_videojuego.resources.endpoints.usuario;

import com.api_videojuego.dto.usuario.CrearUsuarioDTO;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.services.usuario.CrearUsuarioService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioResource {

  @POST
  @Path("/crear-usuario")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response crearUsuario(@BeanParam CrearUsuarioDTO crearUsuarioDTO) {

    CrearUsuarioService service = new CrearUsuarioService();

    try {
      service.crearUsuario(crearUsuarioDTO);

      return Response.status(Response.Status.CREATED)
          .entity("{\"mensaje\": \"Usuario creado exitosamente\"}")
          .header("Content-Type", "application/json")
          .build();
    } catch (UsuarioYaRegistrado e) {
      return Response.status(Response.Status.CONFLICT)
          .entity("{\"error\": \"" + e.getMessage() + "\"}")
          .header("Content-Type", "application/json")
          .build();

    } catch (ErrorInsertarDB e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error en la base de datos: " + e.getMessage() + "\"}")
          .header("Content-Type", "application/json")
          .build();

    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("{\"error\": \"" + e.getMessage() + "\"}")
          .header("Content-Type", "application/json")
          .build();
    }

  }
}
