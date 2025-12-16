import { Component } from '@angular/core';
import { PageLayoutComponent } from '../../components/share/page-layout.component/page-layout.component';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-home-page',
  imports: [PageLayoutComponent, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
})
export class HomePageComponent {
  constructor() {}
}
