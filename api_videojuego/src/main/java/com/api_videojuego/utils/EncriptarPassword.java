package com.api_videojuego.utils;

import java.security.MessageDigest;
import java.util.Base64;

import com.api_videojuego.excepciones.ErrorEncriptacion;

public class EncriptarPassword {

  public String encriptarPassword(String password, String correo) throws ErrorEncriptacion {

    try {
      String salt = password + correo;

      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(salt.getBytes());

      return Base64.getEncoder().encodeToString(hash);

    } catch (Exception e) {
      throw new ErrorEncriptacion("Error al encriptar la contraseña");
    }

  }
}
