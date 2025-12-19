package com.api_videojuego.utils;

import java.util.Set;

public class ConfiguracionAvatar {

  // * Tamaño máximo permitido para el avatar en bytes */
  public static final long AVATAR_SIZE = 5 * 1024 * 1024;
  // * Tipos permitidos para el avatar */
  public static final Set<String> AVATAR_TYPES = Set.of("image/jpeg",
      "image/png");

}
