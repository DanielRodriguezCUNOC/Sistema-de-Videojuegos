package com.api_videojuego.services.empresa.videojuego;

import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.db.empresa.videojuego.BannerDB;
import com.api_videojuego.db.empresa.videojuego.ClasificacionVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.CrearVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.ImagenJuegoDB;
import com.api_videojuego.db.empresa.videojuego.SolicitudCategoriaDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatoYaExiste;

public class CrearVideojuegoService {

	private CrearVideojuegoDB crearVideojuegoDB;

	private UsuarioEmpresaDB usuarioEmpresaDB;

	private ImagenJuegoDB imagenJuegoDB;

	private BannerDB bannerDB;

	public CrearVideojuegoService() {
		this.crearVideojuegoDB = new CrearVideojuegoDB();
		this.usuarioEmpresaDB = new UsuarioEmpresaDB();
	}

	public void crearVideojuego(VideojuegoRequestDTO videojuego) {

		try {

			// * Verificamos si existe un videojuego con el mismo titulo */
			if (crearVideojuegoDB.existeVideojuegoPorTitulo(videojuego.getTitulo())) {
				throw new DatoYaExiste("Ya existe un videojuego con el mismo titulo");

			}

			Integer idEmpresa = usuarioEmpresaDB
					.obtenerIdEmpresaPorIdUsuario(videojuego.getIdUsuarioEmpresa());

			crearVideojuegoDB.registrarVideojuego(videojuego, idEmpresa);

		} catch (Exception e) {
		}

	}

}
