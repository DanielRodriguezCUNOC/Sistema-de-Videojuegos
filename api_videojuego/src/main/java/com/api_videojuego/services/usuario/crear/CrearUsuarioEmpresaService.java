package com.api_videojuego.services.usuario.crear;

import java.io.InputStream;

import com.api_videojuego.db.usuario.crear.CrearUsuarioDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioEmpresaDB;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioEmpresaDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearUsuarioEmpresaService {

  CrearUsuarioEmpresaDB crearUsuarioDB;
  CrearUsuarioDB crearUsuarioGenericoDB;
  private static final Integer ROL_USUARIO = 2;

  public CrearUsuarioEmpresaService() {
    crearUsuarioDB = new CrearUsuarioEmpresaDB();
    crearUsuarioGenericoDB = new CrearUsuarioDB();
  }

  public void crearUsuarioEmpresa(CrearUsuarioEmpresaDTO crearUsuarioDTO)
      throws Exception {

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
      registrarUsuarioEmpresa(crearUsuarioDTO, idUsuario);

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

  public void registrarUsuarioGenerico(CrearUsuarioEmpresaDTO crearUsuarioDTO,
      Integer idRol) throws Exception {

    crearUsuarioGenericoDB.registrarUsuario(idRol,
        crearUsuarioDTO.getCorreoUsuario(), crearUsuarioDTO.getPassword(),
        crearUsuarioDTO.getFechaNacimiento(),
        crearUsuarioDTO.getNumeroTelefonico(), crearUsuarioDTO.getPais(),
        crearUsuarioDTO.getAvatarPart().getValueAs(InputStream.class));

  }

  public Integer obtenerIdUsuarioPorCorreo(String correoUsuario)
      throws Exception {
    return crearUsuarioGenericoDB.obtenerIdUsuarioPorCorreo(correoUsuario);
  }

  public void registrarUsuarioEmpresa(CrearUsuarioEmpresaDTO crearUsuarioDTO,
      Integer idUsuario) throws Exception {

    crearUsuarioDB.registrarUsuarioEmpresa(crearUsuarioDTO.getNombreCompleto(),
        idUsuario, crearUsuarioDTO.getIdEmpresa());
  }

}
