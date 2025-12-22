package com.api_videojuego.services.empresa.videojuego;

import com.api_videojuego.db.empresa.videojuego.BannerDB;
import com.api_videojuego.db.empresa.videojuego.ClasificacionVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.CrearVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.ImagenJuegoDB;
import com.api_videojuego.db.empresa.videojuego.SolicitudVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;

public class CrearVideojuegoService {

	private CrearVideojuegoDB crearVideojuegoDB;

	private VideojuegoDesarrolladoraDB videojuegoDesarrolladoraDB;

	private SolicitudVideojuegoDB solicitudVideojuegoDB;

	private ImagenJuegoDB imagenJuegoDB;

	private BannerDB bannerDB;

	private ClasificacionVideojuegoDB clasificacionVideojuegoDB;

	public CrearVideojuegoService() {
		this.crearVideojuegoDB = new CrearVideojuegoDB();
	}

	public void crearVideojuego(VideojuegoRequestDTO videojuego)
			throws Exception {

	}

	public void registrarVideojuegoDesarrolladora(Integer idVideojuego,
			Integer idEmpresa) throws Exception {
	}

	public void asignarCategoriasAVideojuego(Integer idVideojuego,
			Integer idCategoria) throws Exception {
	}

	public void guardarImagenVideojuego(Integer idVideojuego, String rutaImagen)
			throws Exception {
	}
}
