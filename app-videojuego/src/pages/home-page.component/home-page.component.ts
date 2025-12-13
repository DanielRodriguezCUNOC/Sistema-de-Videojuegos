import { Component } from '@angular/core';
import { NavbarComponent } from '../../components/navbar.component/navbar.component';
import { FooterComponent } from '../../components/footer.component/footer.component';
import { PageLayoutComponent } from '../../components/share/page-layout.component/page-layout.component';

@Component({
  selector: 'app-home-page',
  imports: [PageLayoutComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
})
export class HomePageComponent {}
