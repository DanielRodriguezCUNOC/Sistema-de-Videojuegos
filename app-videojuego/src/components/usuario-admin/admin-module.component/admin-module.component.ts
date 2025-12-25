import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FooterComponent } from '../../share/footer.component/footer.component';
import { NavbarUsuarioAdminComponent } from '../navbar-usuario-admin.component/navbar-usuario-admin.component';

@Component({
  selector: 'app-admin-module',
  imports: [CommonModule, FooterComponent, RouterLink, NavbarUsuarioAdminComponent],
  templateUrl: './admin-module.component.html',
  styleUrl: './admin-module.component.scss',
})
export class AdminModuleComponent {
  activeSection: string = 'dashboard';
  activeSubsection: string = '';

  setActiveSection(section: string): void {
    this.activeSection = section;
    this.activeSubsection = '';
  }

  setActiveSubsection(subsection: string): void {
    this.activeSubsection = subsection;
  }
}
