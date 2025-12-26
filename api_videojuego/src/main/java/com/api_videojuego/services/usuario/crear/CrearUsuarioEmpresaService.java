package com.api_videojuego.services.usuario.crear;

import java.io.IOException;
import java.io.InputStream;

import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.db.gamer.crear.CrearUsuarioDB;
import com.api_videojuego.db.gamer.crear.CrearUsuarioEmpresaDB;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioEmpresaDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.ConvertirImagen;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearUsuarioEmpresaService {

  CrearUsuarioEmpresaDB crearUsuarioDB;
  CrearUsuarioDB crearUsuarioGenericoDB;
  UsuarioEmpresaDB usuarioEmpresaDB;
  ConvertirImagen convertirImagen;
  private static final Integer ROL_USUARIO = 2;

  public CrearUsuarioEmpresaService() {
    crearUsuarioDB = new CrearUsuarioEmpresaDB();
    crearUsuarioGenericoDB = new CrearUsuarioDB();
    usuarioEmpresaDB = new UsuarioEmpresaDB();
    convertirImagen = new ConvertirImagen();
  }

  public void crearUsuarioEmpresa(CrearUsuarioEmpresaDTO crearUsuarioDTO)
      throws AvatarExcepcion, DatosInvalidos, UsuarioYaRegistrado,
      ErrorInsertarDB, ErrorEncriptacion, ErrorConsultaDB, IOException,
      ExcepcionInesperada {

    try {

      // * Validamos los datos del usuario */
      if (!crearUsuarioDTO.usuarioEmpresaValido()) {
        throw new DatosInvalidos("Datos de usuario inválidos.");
      }

      // * Verificamos el tamaño y tipo del avatar */

      if (crearUsuarioDTO
          .getAvatarEmpresaSize() > ConfiguracionAvatar.AVATAR_SIZE
          || !ConfiguracionAvatar.AVATAR_TYPES
              .contains(crearUsuarioDTO.getAvatarEmpresaType())) {
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

      // * Registramos el usuario genérico */
      registrarUsuarioGenerico(crearUsuarioDTO, ROL_USUARIO);

      // * Obtenemos el Id del usuario generico */
      Integer idUsuario = obtenerIdUsuarioPorCorreo(
          crearUsuarioDTO.getCorreoUsuario());

      // * Registramos el usuario empresa */
      registrarUsuarioEmpresa(crearUsuarioDTO.getNombreCompleto(), idUsuario,
          Integer.parseInt(crearUsuarioDTO.getIdUsuarioCreador()));

    } catch (UsuarioYaRegistrado e) {
      throw e;

    } catch (ErrorInsertarDB e) {
      throw e;
    } catch (DatosInvalidos e) {
      throw e;
    } catch (ErrorEncriptacion e) {
      throw e;
    } catch (ErrorConsultaDB e) {
      throw e;

    } catch (IOException e) {
      throw e;
    } catch (ExcepcionInesperada e) {
      throw e;
    }
  }

  public void registrarUsuarioGenerico(CrearUsuarioEmpresaDTO crearUsuarioDTO,
      Integer idRol) throws IOException, ErrorInsertarDB, ExcepcionInesperada {

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
      throws ErrorConsultaDB {
    return crearUsuarioGenericoDB.obtenerIdUsuarioPorCorreo(correoUsuario);
  }

  public void registrarUsuarioEmpresa(String nombreCompleto, Integer idUsuario,
      Integer idUsuarioCreador) throws ErrorInsertarDB, ErrorConsultaDB {

    crearUsuarioDB.registrarUsuarioEmpresa(nombreCompleto, idUsuario,
        obtenerIdEmpresaMedianteUsuario(idUsuarioCreador));
  }

  public Integer obtenerIdEmpresaMedianteUsuario(Integer idUsuario)
      throws ErrorConsultaDB {

    return usuarioEmpresaDB.obtenerIdEmpresaPorIdUsuario(idUsuario);

  }

}
