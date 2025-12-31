import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CommonModule } from '@angular/common';
import { CarteraDigitalService } from '../../../../services/gamer/cartera/CarteraDigital.service';
import { RecargarCarteraRequestDTO } from '../../../../models/dtos/gamer/cartera/RecargarCarteraRequestDTO';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';

@Component({
  selector: 'app-recargar-cartera-digital.component',
  imports: [ReactiveFormsModule, SharePopupComponent, CommonModule],
  templateUrl: './recargar-cartera-digital.component.html',
  styleUrl: './recargar-cartera-digital.component.scss',
})
export class RecargarCarteraDigitalComponent implements OnInit {
  recargarForm: FormGroup;
  saldoActual: number = 250.5;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private service: CarteraDigitalService,
    private route: ActivatedRoute,
    private masterLogin: MasterLoginService
  ) {
    this.recargarForm = this.formBuilder.group({
      monto: ['', [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.saldoActual = params['saldoActual'];

      if (!this.saldoActual) {
        this.mostrarPopup('No se pudo obtener el saldo actual.', 'error');
        this.regresar();
      }
    });
  }

  onSubmit(): void {
    if (this.recargarForm.valid) {
      this.isLoading = true;

      const data: RecargarCarteraRequestDTO = {
        monto: this.recargarForm.value.monto,
        idUsuario: this.masterLogin.getUserId(),
      };

      this.service.recargarCartera(data).subscribe({
        next: () => {
          this.isLoading = false;
          this.mostrarPopup('Recarga exitosa.', 'success');
        },
        error: () => {
          this.isLoading = false;
          this.mostrarPopup('Error al recargar la cartera.', 'error');
        },
      });
    }
  }

  regresar(): void {
    this.router.navigate(['/user-gamer/cartera-digital']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
