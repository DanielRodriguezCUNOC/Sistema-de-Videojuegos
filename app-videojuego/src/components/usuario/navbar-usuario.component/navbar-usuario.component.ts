import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MasterLoginService } from '../../../services/login/masterlogin.service';

@Component({
  selector: 'app-navbar-usuario',
  imports: [RouterLink],
  templateUrl: './navbar-usuario.component.html',
  styleUrl: './navbar-usuario.component.scss',
})
export class NavbarUsuarioComponent implements OnInit {
  userAvatar = '';
  router = inject(Router);

  constructor(private masterLoginService: MasterLoginService) {}

  ngOnInit() {
    this.userAvatar = 'https://i.pravatar.cc/150?img=3';
  }

  logOut() {
    this.masterLoginService.setLogout();
    this.router.navigate(['']);
  }

  onAvatarError(event: Event) {
    const target = event.target as HTMLImageElement;
    target.src = '';
  }
}
