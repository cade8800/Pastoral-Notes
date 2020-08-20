import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { EditorMDConfig } from '../../../config/editor.md.config';
import { Diary } from '../../../config/constants';
import { ActivatedRoute, Router } from '@angular/router';

declare var editormd: any;
declare var $: any;

@Component({
  selector: 'app-diary-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.less']
})
export class DiaryArticleComponent implements OnInit {

  constructor(
    private http: _HttpClient,
    private activatedRoute: ActivatedRoute,
    public router: Router
  ) { }

  editorMD: any;
  diaryDetail: any = { categories: [], labels: [] };
  loading = true;
  diaryId: string;

  ngOnInit() {

    this.diaryId = this.activatedRoute.snapshot.paramMap.get('id');

    if (this.diaryId) {
      this.http.get(Diary + this.diaryId).subscribe(res => {
        this.loading = false;
        this.diaryDetail = res.resultBody;
        this.markdownInitView(this.diaryDetail.content);
      });
    }

  }

  markdownInitView(markdown: string) {
    if (!markdown) { return; }
    EditorMDConfig.markdown = markdown;
    this.editorMD = editormd.markdownToHTML('editormd_view', EditorMDConfig);
  }

  edit() {
    if (this.diaryId) {
      this.router.navigate(['/diary/edit', { id: this.diaryId }]);
    }
  }

  delete() {
    if (!this.diaryDetail.id) return;
    this.http.delete(Diary + this.diaryDetail.id).subscribe(res => {
      this.router.navigate(['/diary/list']);
    });
  }

  goLabel(id: string) {
    if (!id) return;
    this.router.navigate(['/diary/list', { labelid: id }]);
  }

}
