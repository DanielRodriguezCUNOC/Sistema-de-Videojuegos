package com.api_videojuego.resources.endpoints.usuario;

import com.api_videojuego.dto.usuario.crear.CrearUsuarioAdminDTO;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioEmpresaDTO;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioGamerDTO;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.services.usuario.crear.CrearUsuarioAdminService;
import com.api_videojuego.services.usuario.crear.CrearUsuarioEmpresaService;
import com.api_videojuego.services.usuario.crear.CrearUsuarioGamerService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
public class CrearUsuarioResource {

  @POST
  @Path("/crear-usuario-gamer")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response crearUsuario(@BeanParam CrearUsuarioGamerDTO crearUsuarioDTO) {

    CrearUsuarioGamerService service = new CrearUsuarioGamerService();

    try {
      service.crearUsuarioGamer(crearUsuarioDTO);

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

  @POST
  @Path("/crear-usuario-empresa")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response crearUsuarioEmpresa(@BeanParam CrearUsuarioEmpresaDTO crearUsuarioDTO) {

    CrearUsuarioEmpresaService service = new CrearUsuarioEmpresaService();

    try {
      service.crearUsuarioEmpresa(crearUsuarioDTO);

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

  @POST
  @Path("/crear-usuario-admin")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response crearUsuarioAdmin(@BeanParam CrearUsuarioAdminDTO crearUsuarioDTO) {

    CrearUsuarioAdminService service = new CrearUsuarioAdminService();

    try {
      service.crearUsuarioAdmin(crearUsuarioDTO);

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
