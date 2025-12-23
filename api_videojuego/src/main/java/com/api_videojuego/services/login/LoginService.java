package com.api_videojuego.services.login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import com.api_videojuego.db.login.LoginDB;
import com.api_videojuego.dto.login.LoginRequestDTO;
import com.api_videojuego.dto.login.LoginResponseDTO;
import com.api_videojuego.excepciones.CredencialesInvalidas;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.utils.EncriptarPassword;

public class LoginService {

  private LoginDB loginDB;

  public LoginService() {
    this.loginDB = new LoginDB();
  }

  public LoginResponseDTO autenticarUsuario(LoginRequestDTO loginDTO)
      throws Exception {

    try {
      // * Validar datos de entrada
      if (!loginDTO.loginValido()) {
        throw new DatosInvalidos("Correo y contraseña son requeridos");
      }

      // * Encriptar la contraseña
      EncriptarPassword encriptarPassword = new EncriptarPassword();
      String passwordEncriptada = encriptarPassword.encriptarPassword(
          loginDTO.getPassword(), loginDTO.getCorreoUsuario());

      // * Validar credenciales
      boolean credencialesValidas = loginDB
          .validarCredenciales(loginDTO.getCorreoUsuario(), passwordEncriptada);

      if (!credencialesValidas) {
        throw new CredencialesInvalidas("Correo o contraseña incorrectos");
      }

      // * Obtener datos del usuario
      return loginDB.obtenerDatosUsuario(loginDTO.getCorreoUsuario());

    } catch (CredencialesInvalidas e) {
      throw e;
    } catch (DatosInvalidos e) {
      throw e;
    } catch (ErrorEncriptacion e) {
      throw e;
    } catch (ErrorConsultaDB e) {
      throw e;
    } catch (Exception e) {
      throw new Exception("Error interno del servidor: " + e.getMessage());
    }
  }

  // *Convertir InputStream a Bytes */
  private String convertirInputStreamABase64(InputStream inputStream) {
    if (inputStream == null) {
      return null;
    }

    try {
      byte[] avatarBytes = inputStream.readAllBytes();
      return Base64.getEncoder().encodeToString(avatarBytes);
    } catch (IOException e) {
      return null;
    }
  }

}
