import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  searchForCharacter(query: string) {
    let params = new HttpParams().set('query', query);
    return this.http.get('api/characters', { params });
  }
}
