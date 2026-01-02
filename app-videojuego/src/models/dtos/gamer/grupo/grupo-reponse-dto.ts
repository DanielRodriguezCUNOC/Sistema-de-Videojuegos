import { IntegranteGrupoDto } from './integrante-grupo-dto';

export interface GrupoResponseDto {
  idGrupo: number;
  nombreGrupo: string;
  estado: boolean;
  integrantes: IntegranteGrupoDto[];
}
