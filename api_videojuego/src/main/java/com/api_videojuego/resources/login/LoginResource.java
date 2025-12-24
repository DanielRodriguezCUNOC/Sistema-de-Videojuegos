package com.api_videojuego.resources.login;

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.api_videojuego.dto.login.LoginRequestDTO;
import com.api_videojuego.dto.login.LoginResponseDTO;
import com.api_videojuego.excepciones.CredencialesInvalidas;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.services.login.LoginService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(@FormDataParam("usuario") String correoUsuario,
      @FormDataParam("password") String pass) {

    LoginService loginService = new LoginService();

    try {
      LoginResponseDTO response = loginService
          .autenticarUsuario(new LoginRequestDTO(correoUsuario, pass));

      return Response.ok(response).build();

    } catch (CredencialesInvalidas e) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity("{\"error\": \"" + e.getMessage() + "\"}").build();

    } catch (DatosInvalidos e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("{\"error\": \"" + e.getMessage() + "\"}").build();

    } catch (ErrorEncriptacion e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error de encriptación\"}").build();

    } catch (ErrorConsultaDB e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error en la base de datos\"}").build();

    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error interno del servidor\"}").build();
    }
  }
}
