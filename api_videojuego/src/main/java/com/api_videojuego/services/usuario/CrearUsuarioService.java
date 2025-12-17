package com.api_videojuego.services.usuario;

import com.api_videojuego.db.usuario.CrearUsuarioDB;
import com.api_videojuego.dto.usuario.CrearUsuarioDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearUsuarioService {

  public void crearUsuario(CrearUsuarioDTO crearUsuarioDTO) throws Exception {

    CrearUsuarioDB crearUsuarioDB = new CrearUsuarioDB();

    try {

      // * Validamos los datos del usuario */
      if (!crearUsuarioDTO.usuarioValido()) {
        throw new DatosInvalidos("Datos de usuario inválidos.");
      }

      // * Verificamos el tamaño y tipo del avatar */

      if (crearUsuarioDTO.getAvatarSize() > ConfiguracionAvatar.AVATAR_SIZE
          && !ConfiguracionAvatar.AVATAR_TYPES.contains(crearUsuarioDTO.getAvatarType())) {
        throw new AvatarExcepcion("Avatar invalido");
      }

      // * Verificamos que el usuario no exista antes de registrarlo */
      boolean existeUsuario = crearUsuarioDB.existeUsuario(crearUsuarioDTO.getCorreo_usuario());

      if (existeUsuario) {
        throw new UsuarioYaRegistrado(
            "El usuario con el correo " + crearUsuarioDTO.getCorreo_usuario() + " ya está registrado.");
      }

      // *Encriptamos la contraseña */

      EncriptarPassword encriptarPassword = new EncriptarPassword();
      String passwordEncriptada = encriptarPassword.encriptarPassword(
          crearUsuarioDTO.getPassword(),
          crearUsuarioDTO.getCorreo_usuario());
      crearUsuarioDTO.setPassword(passwordEncriptada);

      // *Registramos el usuario */
      crearUsuarioDB.registrarUsuario(crearUsuarioDTO);

    } catch (UsuarioYaRegistrado e) {
      throw e;

    } catch (ErrorInsertarDB e) {
      throw new Exception("Error al crear el usuario: " + e.getMessage());
    } catch (DatosInvalidos e) {
      throw e;
    } catch (ErrorEncriptacion e) {
      throw e;

    }
  }
}
