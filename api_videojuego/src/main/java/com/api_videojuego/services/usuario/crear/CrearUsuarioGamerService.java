package com.api_videojuego.services.usuario.crear;

import java.io.InputStream;

import com.api_videojuego.db.usuario.cartera.CarteraDigitalDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioGamerDB;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioGamerDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearUsuarioGamerService {

  private CrearUsuarioDB crearUsuarioGenericoDB;
  private CrearUsuarioGamerDB crearUsuarioDB;
  private CarteraDigitalDB carteraDigitalDB;
  private static final Integer ROL_USUARIO = 3;

  public CrearUsuarioGamerService() {
    crearUsuarioGenericoDB = new CrearUsuarioDB();
    crearUsuarioDB = new CrearUsuarioGamerDB();
    carteraDigitalDB = new CarteraDigitalDB();

  }

  public void crearUsuarioGamer(CrearUsuarioGamerDTO crearUsuarioDTO)
      throws Exception {

    try {

      // * Validamos los datos del usuario */
      if (!crearUsuarioDTO.usuarioGamerValido()) {
        throw new DatosInvalidos("Datos de usuario inválidos.");
      }

      // * Verificamos el tamaño y tipo del avatar */

      if (crearUsuarioDTO.getAvatarGamerSize() > ConfiguracionAvatar.AVATAR_SIZE
          || !ConfiguracionAvatar.AVATAR_TYPES
              .contains(crearUsuarioDTO.getAvatarGamerType())) {
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

      // * Registramos el usuario gamer */
      registrarUsuarioGamer(crearUsuarioDTO, idUsuario);

      // * Crear cartera digital */
      crearCarteraDigital(crearUsuarioDTO, idUsuario);

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

  private void registrarUsuarioGenerico(CrearUsuarioGamerDTO crearUsuarioDTO,
      Integer idRol) throws Exception {

    crearUsuarioGenericoDB.registrarUsuario(idRol,
        crearUsuarioDTO.getCorreoUsuario(), crearUsuarioDTO.getPassword(),
        crearUsuarioDTO.getFechaNacimiento(),
        crearUsuarioDTO.getNumeroTelefonico(), crearUsuarioDTO.getPais(),
        crearUsuarioDTO.getAvatarPart().getValueAs(InputStream.class));

  }

  private Integer obtenerIdUsuarioPorCorreo(String correoUsuario)
      throws Exception {
    return crearUsuarioGenericoDB.obtenerIdUsuarioPorCorreo(correoUsuario);
  }

  private void registrarUsuarioGamer(CrearUsuarioGamerDTO crearUsuarioDTO,
      Integer idUsuario) throws Exception {

    crearUsuarioDB.registrarUsuarioGamer(idUsuario,
        crearUsuarioDTO.getNickname());
  }

  private void crearCarteraDigital(CrearUsuarioGamerDTO crearUsuarioDTO,
      Integer idUsuario) throws Exception {

    carteraDigitalDB.crearCarteraDigital(crearUsuarioDTO, idUsuario);

  }
}
