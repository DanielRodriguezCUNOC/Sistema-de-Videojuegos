import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../share/footer.component/footer.component';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NavbarUsuarioEmpresaComponent } from '../crear/navbar-usuario-empresa.component/navbar-usuario-empresa.component';

@Component({
  selector: 'app-empresa-module',
  imports: [
    CommonModule,
    FooterComponent,
    RouterLinkActive,
    RouterLink,
    NavbarUsuarioEmpresaComponent,
  ],
  templateUrl: './empresa-module.component.html',
  styleUrl: './empresa-module.component.scss',
})
export class EmpresaModuleComponent {
  activeSection: string = 'dashboard';
  activeSubsection: string = '';

  constructor() {}

  setActiveSection(section: string): void {
    this.activeSection = section;
    this.activeSubsection = '';
  }

  setActiveSubsection(subsection: string): void {
    this.activeSubsection = subsection;
  }
}
