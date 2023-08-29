import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  searchForCharacter(query: string): Observable<any> {
    let params = new HttpParams().set('query', query);
    return this.http.get('http://localhost:8080/api/characters', { params });
  }

  getC
}
