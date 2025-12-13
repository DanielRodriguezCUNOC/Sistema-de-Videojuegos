export interface User {
  idUsuario: number;
  idRol: number;
  nombreCompleto: string;
  usuario: string;
  password: string;
  correo: string;
  telefono: string;
  foto?: string;
}
