import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SearchComponent } from './components/search/search.component';
import { CharacterComponent } from './components/character/character.component';

const routes: Routes = [
  {path: '', component: HomeComponent, title: 'Search for Character'},
  {path: 'search/:query', component: SearchComponent},
  {path: 'character/:id', component: CharacterComponent},
  {path: '**', redirectTo: '', pathMatch: 'prefix'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
