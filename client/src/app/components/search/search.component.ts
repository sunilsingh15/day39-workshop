import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SearchService } from 'src/app/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  service = inject(SearchService);
  query: string = '';

  ngOnInit(): void {
     this.query = this.activatedRoute.snapshot.params['query'];

     this.service.searchForCharacter(this.query).subscribe()
  }

}
