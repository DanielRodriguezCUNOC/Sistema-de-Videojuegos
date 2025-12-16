import { Component, inject } from '@angular/core';
import { Router, RouterLinkActive } from '@angular/router';
import { MasterLoginService } from '../../../services/login/masterlogin';

@Component({
  selector: 'app-navbar',
  imports: [RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  router = inject(Router);
  isLoggedIn = false;

  constructor(private masterLoginService: MasterLoginService) {
    this.readDataLoggedIn();
  }

  readDataLoggedIn() {
    this.isLoggedIn = this.masterLoginService.isLoggedIn();
  }

  logout() {
    this.masterLoginService.setLogout();
    this.router.navigateByUrl('/login');
  }
}
