import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SearchService } from 'src/app/search.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {

  activatedRoute = inject(ActivatedRoute);
  service = inject(SearchService);
  characterId: string = this.activatedRoute.snapshot.params['id'];
  sub$!: Subscription;
  msg: string = '';

  @ViewChild('comment')
  comment!: ElementRef;

  submitComment() {
    const comment = this.comment.nativeElement.value;

    const commentObj = {
      comment: comment
    }

    this.sub$ = this.service.postComment(this.characterId, commentObj).subscribe(
      (result) => {
        this.msg = 'Your comment has been posted successfully.';
      })
  }

}
