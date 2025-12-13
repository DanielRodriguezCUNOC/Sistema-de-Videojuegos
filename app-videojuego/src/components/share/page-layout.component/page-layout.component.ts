import { Component } from '@angular/core';
import { LoginService } from '../../../services/login/login.service';
import { NavbarComponent } from '../../navbar.component/navbar.component';
import { FooterComponent } from '../../footer.component/footer.component';

@Component({
  selector: 'app-page-layout',
  imports: [NavbarComponent, FooterComponent],
  templateUrl: './page-layout.component.html',
  styleUrl: './page-layout.component.scss',
})
export class PageLayoutComponent {
  isSysAdmin: boolean = false;
  currentUser: any;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.currentUser = this.loginService.currentUser;

    this.isSysAdmin = this.currentUser?.idRol === 1;
  }
}
