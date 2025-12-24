package com.api_videojuego.services.usuario.crear;

import java.io.IOException;
import java.io.InputStream;

import com.api_videojuego.db.usuario.crear.CrearUsuarioAdminDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioDB;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioAdminDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.ConvertirImagen;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearUsuarioAdminService {

  CrearUsuarioDB crearUsuarioGenericoDB;
  CrearUsuarioAdminDB crearUsuarioDB;
  ConvertirImagen convertirImagen;
  private static final Integer ROL_USUARIO = 1;

  public CrearUsuarioAdminService() {
    crearUsuarioGenericoDB = new CrearUsuarioDB();
    crearUsuarioDB = new CrearUsuarioAdminDB();
    convertirImagen = new ConvertirImagen();

  }

  public void crearUsuarioAdmin(CrearUsuarioAdminDTO crearUsuarioDTO)
      throws Exception {

    try {

      // * Validamos los datos del usuario */
      if (!crearUsuarioDTO.usuarioAdminValido()) {
        throw new DatosInvalidos("Datos de usuario inválidos.");
      }

      // * Verificamos el tamaño y tipo del avatar */

      if (crearUsuarioDTO.getAvatarAdminSize() > ConfiguracionAvatar.AVATAR_SIZE
          || !ConfiguracionAvatar.AVATAR_TYPES
              .contains(crearUsuarioDTO.getAvatarAdminType())) {
        throw new AvatarExcepcion("Avatar invalido");
      }

      // * Verificamos que el usuario no exista antes de registrarlo */
      boolean existeUsuario = crearUsuarioGenericoDB
          .existeUsuario(crearUsuarioDTO.getCorreoUsuario());

      if (existeUsuario) {
        throw new UsuarioYaRegistrado("El usuario con el correo "
            + crearUsuarioDTO.getCorreoUsuario() + " ya está registrado.");
      }

      // *Encriptamos la contraseña */

      EncriptarPassword encriptarPassword = new EncriptarPassword();
      String passwordEncriptada = encriptarPassword.encriptarPassword(
          crearUsuarioDTO.getPassword(), crearUsuarioDTO.getCorreoUsuario());
      crearUsuarioDTO.setPassword(passwordEncriptada);

      // *Registramos el usuario generico */
      registrarUsuarioGenerico(crearUsuarioDTO, ROL_USUARIO);

      // * Obtenemos el Id del usuario generico */
      Integer idUsuario = obtenerIdUsuarioPorCorreo(
          crearUsuarioDTO.getCorreoUsuario());

      // * Registramos el usuario administrador */
      registrarUsuarioAdministrador(crearUsuarioDTO, idUsuario);

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

  public void registrarUsuarioGenerico(CrearUsuarioAdminDTO crearUsuarioDTO,
      Integer idRol) throws Exception {

    byte[] avatarBytes = null;
    if (crearUsuarioDTO.getAvatarPart() != null) {
      try (InputStream avatarStream = crearUsuarioDTO.getAvatarPart()
          .getValueAs(InputStream.class)) {
        avatarBytes = avatarStream.readAllBytes();
      } catch (IOException e) {
        avatarBytes = convertirImagen.obtenerAvatarDefault();
      }
    }
    else {
      avatarBytes = convertirImagen.obtenerAvatarDefault();
    }

    crearUsuarioGenericoDB.registrarUsuario(idRol,
        crearUsuarioDTO.getCorreoUsuario(), crearUsuarioDTO.getPassword(),
        crearUsuarioDTO.getFechaNacimiento(),
        crearUsuarioDTO.getNumeroTelefonico(), crearUsuarioDTO.getPais(),
        avatarBytes);

  }

  public Integer obtenerIdUsuarioPorCorreo(String correoUsuario)
      throws Exception {
    return crearUsuarioGenericoDB.obtenerIdUsuarioPorCorreo(correoUsuario);
  }

  public void registrarUsuarioAdministrador(
      CrearUsuarioAdminDTO crearUsuarioDTO, Integer idUsuario)
      throws Exception {

    crearUsuarioDB.registrarUsuarioAdmin(crearUsuarioDTO.getNombreCompleto(),
        idUsuario);
  }

}
