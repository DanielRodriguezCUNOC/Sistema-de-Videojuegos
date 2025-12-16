import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-share-popup',
  imports: [CommonModule],
  templateUrl: './share-popup.component.html',
  styleUrl: './share-popup.component.scss',
})
export class SharePopupComponent {
  @Input() mensaje: string = '';
  @Input() tipo: 'error' | 'success' | 'info' = 'info';
  @Input() mostrar: boolean = false;
  @Input() duracion: number = 5500;

  @Output() cerrarEvent = new EventEmitter<void>();

  private timeoutId?: any;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mostrar']?.currentValue) {
      if (this.timeoutId) clearTimeout(this.timeoutId);
      this.timeoutId = setTimeout(() => this.cerrar(), this.duracion);
    }
  }

  cerrar(): void {
    this.mostrar = false;
    this.cerrarEvent.emit();
  }
}
