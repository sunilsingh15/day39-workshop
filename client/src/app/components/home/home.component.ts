import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  router = inject(Router);

  @ViewChild('search')
  query!: ElementRef;

  searchCharacter() {
    const searchQuery = this.query.nativeElement.value;
    this.router.navigate(['/search/', searchQuery]);
  }

}
