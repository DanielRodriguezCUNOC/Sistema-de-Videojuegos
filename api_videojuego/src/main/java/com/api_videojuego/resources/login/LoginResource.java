package com.api_videojuego.resources.login;

import com.api_videojuego.dto.login.LoginRequestDTO;
import com.api_videojuego.dto.login.LoginResponseDTO;
import com.api_videojuego.excepciones.CredencialesInvalidas;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.services.login.LoginService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response login(@BeanParam LoginRequestDTO loginRequestDTO) {

    LoginService loginService = new LoginService();

    try {
      LoginResponseDTO response = loginService
          .autenticarUsuario(loginRequestDTO);
      String jsonResponse = loginService.crearRespuestaJSON(response);

      return Response.status(Response.Status.OK).entity(jsonResponse)
          .header("Content-Type", "application/json").build();

    } catch (CredencialesInvalidas e) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity("{\"error\": \"" + e.getMessage() + "\"}")
          .header("Content-Type", "application/json").build();

    } catch (DatosInvalidos e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("{\"error\": \"" + e.getMessage() + "\"}")
          .header("Content-Type", "application/json").build();

    } catch (ErrorEncriptacion e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error de encriptación\"}")
          .header("Content-Type", "application/json").build();

    } catch (ErrorConsultaDB e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error en la base de datos\"}")
          .header("Content-Type", "application/json").build();

    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity("{\"error\": \"Error interno del servidor\"}")
          .header("Content-Type", "application/json").build();
    }
  }
}
