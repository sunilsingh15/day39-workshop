import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  searchForCharacter(query: string): Observable<any> {
    let params = new HttpParams().set('query', query);
    return this.http.get('http://localhost:8080/api/characters', { params });
  }

  getCharacterById(id: string): Observable<any> {
    return this.http.get(`http://localhost:8080/api/character/${id}`);
  }

  getCommentsById(id: string): Observable<any> {
    return this.http.get(`http://localhost:8080/api/character/${id}/comments`);
  }

  postComment(id: string, comment: any) {
    return this.http.post(`http://localhost:8080/api/character/${id}`, comment);
  }

}
