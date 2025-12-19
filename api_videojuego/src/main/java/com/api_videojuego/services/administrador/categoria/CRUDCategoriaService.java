package com.api_videojuego.services.administrador.categoria;

import com.api_videojuego.db.administrador.categoria.CRUDCategoriaDB;
import com.api_videojuego.dto.categoria.CrearCategoriaDTO;
import com.api_videojuego.dto.categoria.EditarCategoriaDTO;
import com.api_videojuego.dto.categoria.ListaCategoriaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CRUDCategoriaService {

	private CRUDCategoriaDB crudCategoriaDB;

	public CRUDCategoriaService() {
		this.crudCategoriaDB = new CRUDCategoriaDB();
	}

	public void crearCategoria(CrearCategoriaDTO crearCategoriaDTO)
			throws Exception {
		try {
			if (!crearCategoriaDTO.esCategoriaValida()) {
				throw new DatosInvalidos("Los datos ingresados no son válidos");
			}

			boolean existeCategoria = crudCategoriaDB
					.categoriaExiste(crearCategoriaDTO.getCategoria());
			if (existeCategoria) {
				throw new DatoYaExiste("Ya existe una categoría con ese nombre");
			}

			crudCategoriaDB.crearCategoria(crearCategoriaDTO);

		} catch (DatoYaExiste e) {
			throw e;
		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error interno del servidor: " + e.getMessage());
		}
	}

	public ListaCategoriaDTO obtenerCategorias() throws Exception {
		try {
			return crudCategoriaDB.obtenerCategorias();
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error interno del servidor: " + e.getMessage());
		}
	}

	public void editarCategoria(EditarCategoriaDTO editarCategoriaDTO)
			throws Exception {
		try {
			if (!editarCategoriaDTO.esCategoriaValida()) {
				throw new DatosInvalidos("Los datos ingresados no son válidos");
			}

			crudCategoriaDB.editarCategoria(editarCategoriaDTO);

		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error interno del servidor: " + e.getMessage());
		}
	}

	public void eliminarCategoria(Integer idCategoria) throws Exception {
		try {
			if (idCategoria == null || idCategoria <= 0) {
				throw new DatosInvalidos("ID de categoría inválido");
			}

			crudCategoriaDB.eliminarCategoria(idCategoria);

		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error interno del servidor: " + e.getMessage());
		}
	}
}