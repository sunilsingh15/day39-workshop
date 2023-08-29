import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SearchComponent } from './components/search/search.component';
import { CharacterComponent } from './components/character/character.component';
import { CommentComponent } from './components/comment/comment.component';

const routes: Routes = [
  {path: '', component: HomeComponent, title: 'Search for Character'},
  {path: 'search/:query', component: SearchComponent},
  {path: 'character/:id', component: CharacterComponent},
  {path: 'character/:id/comment', component: CommentComponent},
  {path: '**', redirectTo: '', pathMatch: 'prefix'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
