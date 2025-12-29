package com.api_videojuego.db.empresa.videojuego;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.administrador.videojuego.SolicitudCategoriaDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearVideojuegoDB {

	public boolean existeVideojuegoPorTitulo(String titulo)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) AS cantidad FROM videojuego WHERE titulo = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, titulo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int cantidad = rs.getInt("cantidad");
				return cantidad > 0;
			}

		} catch (Exception e) {
			throw new ErrorConsultaDB(
					"Error al verificar si el videojuego ya esta registrado");
		}

		return false;
	}

	public Integer registrarVideojuego(VideojuegoRequestDTO videojuego)
			throws ErrorInsertarDB, ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		try {
			// * Iniciamos la transacción */
			conn.setAutoCommit(false);

			SolicitudCategoriaDB solicitudCategoriaDB = new SolicitudCategoriaDB();
			VideojuegoDesarrolladoraDB videojuegoDesarrolladoraDB = new VideojuegoDesarrolladoraDB();
			ClasificacionVideojuegoDB clasificacionVideojuegoDB = new ClasificacionVideojuegoDB();
			UsuarioEmpresaDB usuarioEmpresaDB = new UsuarioEmpresaDB();

			System.out.println(
					"Iniciando registro de videojuego: " + videojuego.getTitulo());

			// * Registramos el videojuego y obtenemos su ID */
			Integer idVideojuego = crearVideojuego(videojuego.getTitulo(),
					videojuego.getDescripcion(), videojuego.getFechaLanzamiento(),
					videojuego.getPrecio(), videojuego.getRecursosMinimos(), conn);
			System.out.println("Videojuego creado con ID: " + idVideojuego);

			// * Generamos la solicitud de categoria */
			Integer idSolicitudCategoria = solicitudCategoriaDB
					.generarSolicitudCategoria(idVideojuego,
							videojuego.getIdUsuarioEmpresa(), videojuego.getCategorias(),
							conn);
			System.out
					.println("Solicitudes de categoría creadas: " + idSolicitudCategoria);

			// * Obtenemos el id de la empresa con el id del usuario */
			Integer idEmpresa = usuarioEmpresaDB.obtenerIdEmpresaPorIdUsuario(
					Integer.parseInt(videojuego.getIdUsuarioEmpresa()));
			System.out.println("ID EMPRESA: " + idEmpresa);

			// * Registramos la relacion entre el videojuego y la desarrolladora */
			Integer idVideojuegoDesarrolladora = videojuegoDesarrolladoraDB
					.registrarVideojuegoDesarrolladora(idEmpresa, idVideojuego, conn);
			System.out.println("Relación videojuego-desarrolladora creada: "
					+ idVideojuegoDesarrolladora);

			// * Registramos la clasificacion del videojuego */
			Integer idClasificacion = clasificacionVideojuegoDB
					.registrarClasificacionVideojuego(idVideojuego,
							videojuego.getClasificacion(), conn);
			System.out.println("Clasificación registrada: " + idClasificacion);

			if (idVideojuego > 0 && idSolicitudCategoria > 0
					&& idVideojuegoDesarrolladora > 0 && idClasificacion > 0) {

				// * Confirmamos la transacción */
				conn.commit();
				System.out.println("Transacción confirmada exitosamente");

				return idVideojuego;
			}
			else {
				conn.rollback();
				System.out.println("Rollback ejecutado - valores: idVideojuego="
						+ idVideojuego + ", idSolicitudCategoria=" + idSolicitudCategoria
						+ ", idVideojuegoDesarrolladora=" + idVideojuegoDesarrolladora
						+ ", idClasificacion=" + idClasificacion);
				throw new ErrorInsertarDB(
						"No se pudo registrar el videojuego correctamente.");
			}
		} catch (SQLException e) {
			System.out
					.println("SQLException en registrarVideojuego: " + e.getMessage());
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex) {
				System.out.println("Error en rollback: " + ex.getMessage());
			}
			throw new ErrorInsertarDB(
					"Error SQL al registrar el videojuego: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(
					"Exception general en registrarVideojuego: " + e.getMessage());
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex) {
				System.out.println("Error en rollback: " + ex.getMessage());
			}
			throw new ErrorInsertarDB(
					"Error inesperado al registrar el videojuego: " + e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error al restaurar autocommit: " + e.getMessage());
			}
		}
	}

	public Integer crearVideojuego(String titulo, String descripcion,
			String fechaLanzamiento, String precio, String recursosMinimos,
			Connection conexion) throws ErrorInsertarDB, ErrorConsultaDB {
		Connection conn = conexion;

		String query = "INSERT INTO videojuego (titulo, descripcion, fecha_lanzamiento, precio, recursos_minimos, estado) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, titulo);
			ps.setString(2, descripcion);
			ps.setDate(3, Date.valueOf(fechaLanzamiento));
			ps.setBigDecimal(4, new BigDecimal(precio));
			ps.setString(5, recursosMinimos);
			ps.setBoolean(6, false);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorInsertarDB(
						"No se pudo crear el videojuego en la base de datos");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
				else {
					throw new ErrorConsultaDB(
							"No se pudo obtener el ID del videojuego creado.");
				}
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al crear el videojuego en la base de datos");
		}
	}

}
