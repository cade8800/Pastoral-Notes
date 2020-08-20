import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { zip } from 'rxjs';
import { Diary, Label, LabelType } from '../../../config/constants';
import { EditorMDConfig } from '../../../config/editor.md.config';

declare var editormd: any;
declare var $: any;

@Component({
  selector: 'app-diary-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.less']
})
export class DiaryEditComponent implements OnInit {

  constructor(
    private http: _HttpClient,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  editorMD: any;
  contentImageList = [];
  labels: any = [];
  diaryDetail: any = {
    id: '',
    date: new Date(),
    location: '',
    weather: '',
    summary: '',
    coverUrl: '',
    content: '',
    labels: [],
  };
  newLabelLoading = false;
  isSpinning = true;
  submitLoading = false;

  ngOnInit() {

    const diaryId = this.activatedRoute.snapshot.paramMap.get('id');

    if (diaryId) {
      this.diaryDetail.id = diaryId;

      zip(
        this.http.get(Label + LabelType.DIARY),
        this.http.get(Diary + diaryId))
        .subscribe(([labelResult, diaryDetailResult]) => {

          this.isSpinning = false;

          this.getLabels(labelResult);
          this.getDiarysDetail(diaryDetailResult);

          if (this.diaryDetail.coverUrl) {
            this.contentImageList.push({ url: this.diaryDetail.coverUrl, selected: true });
          }

          this.initLabels();

          this.initEditor(this.diaryDetail.content);
          this.getContentImage();
        });

    } else {

      zip(this.http.get(Label + LabelType.DIARY))
        .subscribe(([labelResult]) => {

          this.isSpinning = false;

          this.getLabels(labelResult);
        });

      this.initEditor('');

    }

  }


  getDiarysDetail(res: any) {
    if (res.resultBody) {
      this.diaryDetail = res.resultBody;
    }
  }

  getLabels(res: any) {
    if (!res.resultBody) { return; }
    this.labels = res.resultBody;
  }
  initLabels() {
    const existingLabels = [];
    this.diaryDetail.labels.forEach(element => {
      existingLabels.push(element.id);
    });
    this.diaryDetail.labels = existingLabels;
  }
  addLabel(input: HTMLInputElement): void {
    const newLabel = input.value;
    if (!newLabel) { return; }
    const labelDto = { name: newLabel, type: LabelType.NOTE };
    this.newLabelLoading = true;
    this.http.put(Label, labelDto).subscribe(res => {
      this.newLabelLoading = false;
      input.value = '';
      this.http.get(Label + LabelType.NOTE).subscribe(result => { this.getLabels(result); });
    });
  }

  initEditor(markdown: string) {
    EditorMDConfig.markdown = markdown;
    this.editorMD = editormd('editor', EditorMDConfig);

    const that = this;
    this.editorMD.on('change', () => {
      // that.content = $('#editor :first').val();
      that.getContentImage();
    });
  }
  getContentImage() {
    this.contentImageList = this.diaryDetail.coverUrl ? [{ url: this.diaryDetail.coverUrl, selected: true }] : [];

    const imageList = $('.editormd-preview-container img:not([class*=\'emoji\'])');
    if (!imageList || imageList.length <= 0) { return; }
    for (const imgItem of imageList) {
      const imageUrl = $(imgItem).attr('src');
      if (imageUrl) {

        if (this.contentImageList.filter(t => {
          return t.url === imageUrl;
        }).length < 1) {
          const tImg = { url: imageUrl, selected: false };
          this.contentImageList.push(tImg);
        }
      }
    }
  }
  checkCover(item: any) {
    this.contentImageList.forEach(element => {
      if (element.url === item.url) {
        element.selected = !element.selected;
      } else {
        // if (item.selected === false)
        element.selected = false;
      }
    });
  }
  getSelectedCoverUrl() {
    const selectCoverList = this.contentImageList.filter(t => {
      return t.selected === true;
    });
    return selectCoverList.length > 0 ? selectCoverList[0].url : '';
  }

  sumbit() {
    this.diaryDetail.content = this.editorMD.getMarkdown();
    this.diaryDetail.coverUrl = this.getSelectedCoverUrl();

    this.submitLoading = true;
    this.http.put(Diary, this.diaryDetail).subscribe(res => {
      this.submitLoading = false;
      this.router.navigate(['/diary/list']);
    });

  }

}
