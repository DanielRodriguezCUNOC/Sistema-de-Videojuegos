package com.api_videojuego.services.administrador.empresa;

import java.io.IOException;
import java.io.InputStream;

import com.api_videojuego.db.administrador.empresa.CrearEmpresaDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioDB;
import com.api_videojuego.db.usuario.crear.CrearUsuarioEmpresaDB;
import com.api_videojuego.dto.administrador.empresa.CrearEmpresaDTO;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioEmpresaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEncriptacion;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.excepciones.UsuarioYaRegistrado;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.ConvertirImagen;
import com.api_videojuego.utils.EncriptarPassword;

public class CrearEmpresaService {

	private CrearEmpresaDB crearEmpresaDB;
	private CrearUsuarioEmpresaDB usuarioEmpresaDB;
	private CrearUsuarioDB usuarioGenericoDB;
	private ConvertirImagen convertirImagen;

	private static final Integer ROL_USUARIO = 2;

	public CrearEmpresaService() {
		this.crearEmpresaDB = new CrearEmpresaDB();
		this.usuarioEmpresaDB = new CrearUsuarioEmpresaDB();
		this.usuarioGenericoDB = new CrearUsuarioDB();
		this.convertirImagen = new ConvertirImagen();
	}

	public void crearEmpresa(CrearEmpresaDTO crearEmpresaDTO)
			throws DatoYaExiste, DatosInvalidos, UsuarioYaRegistrado, ErrorInsertarDB,
			ErrorConsultaDB, ErrorEncriptacion, ExcepcionInesperada {
		try {

			// *Validar datos del frontend */
			if (!crearEmpresaDTO.esEmpresaValida()) {
				throw new DatosInvalidos("Datos de empresa inválidos.");
			}

			// *Verificar si la empresa ya existe
			if (crearEmpresaDB
					.existeEmpresaPorNombre(crearEmpresaDTO.getNombreEmpresa())) {
				throw new DatoYaExiste("Ya existe una empresa con este nombre.");
			}

			// * Registrar la nueva empresa
			Integer idEmpresa = crearEmpresaDB.registrarEmpresa(
					crearEmpresaDTO.getNombreEmpresa(), crearEmpresaDTO.getDescripcion(),
					crearEmpresaDTO.getEstadoComentario());
			if (idEmpresa == null) {
				throw new ErrorInsertarDB("No se pudo registrar la empresa.");
			}

			// * Obtenemos el usuario del DTO general
			CrearUsuarioEmpresaDTO crearUsuarioEmpresaDTO = convertirACrearUsuarioEmpresaDTO(
					crearEmpresaDTO, idEmpresa);

			// *Validamos datos del usuario */

			if (!crearUsuarioEmpresaDTO.usuarioEmpresaValido()) {
				throw new DatosInvalidos("Datos de usuario inválidos.");
			}

			if (crearUsuarioEmpresaDTO
					.getAvatarEmpresaSize() > ConfiguracionAvatar.AVATAR_SIZE
					|| !ConfiguracionAvatar.AVATAR_TYPES
							.contains(crearUsuarioEmpresaDTO.getAvatarEmpresaType())) {
				throw new DatosInvalidos("Avatar inválido.");

			}

			// * Verificamos que el usuario no exista antes de registrarlo */
			boolean existeUsuario = usuarioGenericoDB
					.existeUsuario(crearUsuarioEmpresaDTO.getCorreoUsuario());
			if (existeUsuario) {
				throw new UsuarioYaRegistrado("El usuario con el correo "
						+ crearUsuarioEmpresaDTO.getCorreoUsuario()
						+ " ya está registrado.");
			}

			// *Encriptamos la contraseña */
			crearUsuarioEmpresaDTO
					.setPassword(encriptarPassword(crearUsuarioEmpresaDTO.getPassword(),
							crearUsuarioEmpresaDTO.getCorreoUsuario()));

			// *Creamos el usuario generico */
			crearUsuarioGenerico(crearUsuarioEmpresaDTO);

			// * Obtenemos el Id del usuario generico */
			Integer idUsuario = usuarioGenericoDB
					.obtenerIdUsuarioPorCorreo(crearUsuarioEmpresaDTO.getCorreoUsuario());

			// * Registramos el usuario empresa */
			registrarUsuarioEmpresa(crearUsuarioEmpresaDTO.getNombreCompleto(),
					idUsuario, idEmpresa);

		} catch (DatoYaExiste e) {
			throw e;
		} catch (DatosInvalidos e) {
			throw e;
		} catch (UsuarioYaRegistrado e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		}
	}

	private String encriptarPassword(String password, String correoUsuario)
			throws ErrorEncriptacion {
		EncriptarPassword encriptarPassword = new EncriptarPassword();
		return encriptarPassword.encriptarPassword(password, correoUsuario);
	}

	private void registrarUsuarioEmpresa(String nombreCompleto, Integer idUsuario,
			Integer idEmpresa) throws ErrorInsertarDB {
		usuarioEmpresaDB.registrarUsuarioEmpresa(nombreCompleto, idUsuario,
				idEmpresa);
	}

	private void crearUsuarioGenerico(CrearUsuarioEmpresaDTO usuario)
			throws ExcepcionInesperada, ErrorInsertarDB {

		// * Procesar el avatar */
		byte[] avatarBytes = null;

		if (usuario.getAvatarPart() != null) {

			try (InputStream avatarStream = usuario.getAvatarPart()
					.getValueAs(InputStream.class)) {

				avatarBytes = avatarStream.readAllBytes();

			} catch (IOException e) {
				avatarBytes = convertirImagen.obtenerAvatarDefault();
			}
		}
		else {
			avatarBytes = convertirImagen.obtenerAvatarDefault();
		}

		usuarioGenericoDB.registrarUsuario(ROL_USUARIO, usuario.getCorreoUsuario(),
				usuario.getPassword(), usuario.getFechaNacimiento(),
				usuario.getNumeroTelefonico(), usuario.getPais(), avatarBytes);

	}

	private CrearUsuarioEmpresaDTO convertirACrearUsuarioEmpresaDTO(
			CrearEmpresaDTO crearEmpresaDTO, Integer idEmpresa) {
		CrearUsuarioEmpresaDTO crearUsuarioEmpresaDTO = new CrearUsuarioEmpresaDTO();
		crearUsuarioEmpresaDTO.setCorreoUsuario(crearEmpresaDTO.getCorreoUsuario());
		crearUsuarioEmpresaDTO
				.setNombreCompleto(crearEmpresaDTO.getNombreCompleto());
		crearUsuarioEmpresaDTO.setPassword(crearEmpresaDTO.getPassword());
		crearUsuarioEmpresaDTO
				.setFechaNacimiento(crearEmpresaDTO.getFechaNacimiento());
		crearUsuarioEmpresaDTO
				.setNumeroTelefonico(crearEmpresaDTO.getNumeroTelefonico());
		crearUsuarioEmpresaDTO.setPais(crearEmpresaDTO.getPais());
		crearUsuarioEmpresaDTO.setAvatarPart(crearEmpresaDTO.getAvatarPart());
		return crearUsuarioEmpresaDTO;
	}

}
