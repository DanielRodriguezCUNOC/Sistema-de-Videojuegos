import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../share/footer.component/footer.component';
import { MasterLoginService } from '../../../services/login/masterlogin';
import { RedireccionarService } from '../../../services/login/redireccionar.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-empresa-module',
  imports: [CommonModule, FooterComponent],
  templateUrl: './empresa-module.component.html',
  styleUrl: './empresa-module.component.scss',
})
export class EmpresaModuleComponent implements OnInit {
  activeSection: string = 'dashboard';
  activeSubsection: string = '';
  avatarUrl: string = '';
  correoUsuario: string = '';

  private subscripcion?: Subscription;

  constructor(
    private masterLoginService: MasterLoginService,
    private redireccionarService: RedireccionarService
  ) {}

  ngOnInit() {
    this.cargarDatosUsuario();
    this.subscribirseACambiosUsuario();
  }

  setActiveSection(section: string): void {
    this.activeSection = section;
    this.activeSubsection = '';
  }

  setActiveSubsection(subsection: string): void {
    this.activeSubsection = subsection;
  }

  logout(): void {
    this.masterLoginService.setLogout();
    this.redireccionarService.redirectToHome();
  }

  /**
   * Carga los datos del usuario actual
   */
  private cargarDatosUsuario(): void {
    //this.avatarUrl = this.masterLoginService.getAvatarUrl();
    this.correoUsuario = this.masterLoginService.getUserDisplayCorreo();
  }

  private subscribirseACambiosUsuario(): void {
    this.subscripcion = this.masterLoginService.currentUser$.subscribe((user) => {
      if (user) {
        this.cargarDatosUsuario();
      }
    });
  }
}
