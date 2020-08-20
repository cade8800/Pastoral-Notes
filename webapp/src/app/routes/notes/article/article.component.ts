import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { Note } from '../../../config/constants';
// import DOMPurify from 'dompurify';
import { EditorMDConfig } from '../../../config/editor.md.config';

declare var editormd: any;
declare var $: any;

@Component({
  selector: 'app-notes-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.less']
})
export class NotesArticleComponent implements OnInit {

  constructor(
    private http: _HttpClient,
    private activatedRoute: ActivatedRoute,
    public router: Router
  ) { }

  editorMD: any;
  noteDetail: any = { categories: [], labels: [] };
  loading = true;
  noteId: string;

  ngOnInit() {

    this.noteId = this.activatedRoute.snapshot.paramMap.get('id');

    if (this.noteId) {
      this.http.get(Note + this.noteId).subscribe(res => {
        this.loading = false;
        this.noteDetail = res.resultBody;
        this.markdownInitView(this.noteDetail.content);
      });
    }

  }

  markdownInitView(markdown: string) {
    if (!markdown) { return; }
    EditorMDConfig.markdown = markdown;
    this.editorMD = editormd.markdownToHTML('editormd_view', EditorMDConfig);
  }

  edit() {
    if (this.noteId) {
      this.router.navigate(['/notes/edit', { id: this.noteId }]);
    }
  }

  delete() {
    if (!this.noteDetail.id) return;
    this.http.delete(Note + this.noteDetail.id).subscribe(res => {
      this.router.navigate(['/notes/list']);
    });
  }

  goCategory(id: string) {
    if (!id) return;
    this.router.navigate(['/notes/list', { categoryid: id }]);
  }
  goLabel(id: string) {
    if (!id) return;
    this.router.navigate(['/notes/list', { labelid: id }]);
  }

}
