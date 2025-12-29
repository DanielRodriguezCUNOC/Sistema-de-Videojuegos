package com.api_videojuego.db.administrador.empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.empresa.CambiarEstadoEmpresaDTO;
import com.api_videojuego.dto.administrador.empresa.EditarEmpresaRequetDTO;
import com.api_videojuego.dto.administrador.empresa.ListaEmpresasDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class GestionEmpresaDB {

	public void editarEmpresa(EditarEmpresaRequetDTO editarEmpresaRequetDTO)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE empresa_desarrolladora SET nombre_empresa = ?, descripcion = ? WHERE id_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, editarEmpresaRequetDTO.getNombreEmpresa());
			ps.setString(2, editarEmpresaRequetDTO.getDescripcion());
			ps.setInt(3, Integer.parseInt(editarEmpresaRequetDTO.getIdEmpresa()));

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al intentar editar la empresa en la BD");
		}
	}

	public void cambiarEstadoEmpresa(
			CambiarEstadoEmpresaDTO cambiarEstadoEmpresaDTO)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE empresa_desarrolladora SET estado = ? WHERE id_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, cambiarEstadoEmpresaDTO.getEstado().toLowerCase());
			ps.setInt(2, Integer.parseInt(cambiarEstadoEmpresaDTO.getIdEmpresa()));

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al intentar cambiar el estado de la empresa en la BD");
		}
	}

	public ListaEmpresasDTO obtenerEmpresas() throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_empresa, nombre_empresa, descripcion, estado FROM empresa_desarrolladora";

		ListaEmpresasDTO listaEmpresasDTO = new ListaEmpresasDTO();

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Integer idEmpresa = rs.getInt("id_empresa");
				String nombreEmpresa = rs.getString("nombre_empresa");
				String descripcion = rs.getString("descripcion");
				String estado = rs.getString("estado");
				listaEmpresasDTO.agregarEmpresa(idEmpresa, nombreEmpresa, descripcion,
						estado);
			}
			return listaEmpresasDTO;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"No se puo obtener el listado de empresas en la BD");
		}
	}
}
