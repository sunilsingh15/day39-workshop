import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SearchService } from 'src/app/search.service';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  service = inject(SearchService);
  sub$!: Subscription;
  commentSub$!: Subscription;
  characterId: string = '';
  character: any;
  comments: any[] = [];

  ngOnInit(): void {
    this.characterId = this.activatedRoute.snapshot.params['id'];

    this.sub$ = this.service.getCharacterById(this.characterId).subscribe({
      next: (result) => { this.character = result; },
      error: (err) => { console.log(err); },
      complete: () => { this.sub$.unsubscribe(); }
    })

    this.commentSub$ = this.service.getCommentsById(this.characterId).subscribe({
      next: (result) => { this.comments = result.comments; },
      error: (err) => { console.log(err); },
      complete: () => { this.commentSub$.unsubscribe(); }
    })
  }

}
