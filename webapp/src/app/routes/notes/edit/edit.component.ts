import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { Observable, zip } from 'rxjs';
import { Category, Label, Note, LabelType } from '../../../config/constants';
import { EditorMDConfig } from '../../../config/editor.md.config';

declare var editormd: any;
declare var $: any;
// declare var marked: any;

@Component({
  selector: 'app-notes-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.less']
})
export class NotesEditComponent implements OnInit {

  constructor(
    private http: _HttpClient,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  editorMD: any;
  contentImageList = [];
  noteDetail: any = {
    id: '',
    title: '',
    summary: '',
    coverUrl: '',
    content: '',
    labels: [],
    categories: [],
  };
  labels: any = [];
  categories: any = [];
  isSpinning = true;
  newLabelLoading = false;
  submitLoading = false;

  ngOnInit() {

    const noteId = this.activatedRoute.snapshot.paramMap.get('id');

    if (noteId) {
      this.noteDetail.id = noteId;

      zip(
        this.http.get(Label + LabelType.NOTE),
        this.http.get(Category),
        this.http.get(Note + noteId))
        .subscribe(([labelResult, categoryResult, noteDetailResult]) => {

          this.isSpinning = false;

          this.getLabels(labelResult);
          this.getCategories(categoryResult);
          this.getNotesDetail(noteDetailResult);

          if (this.noteDetail.coverUrl) {
            this.contentImageList.push({ url: this.noteDetail.coverUrl, selected: true });
          }

          this.initNoteCategoryes();
          this.initLabels();

          this.initEditor(this.noteDetail.content);
          this.getContentImage();
        });

    } else {

      zip(this.http.get(Label + LabelType.NOTE),
        this.http.get(Category))
        .subscribe(([labelResult, categoryResult]) => {

          this.isSpinning = false;

          this.getLabels(labelResult);
          this.getCategories(categoryResult);
        });

      this.initEditor('');

    }

  }

  initNoteCategoryes() {
    const existingCategories = [];
    this.noteDetail.categories.forEach(element => {
      existingCategories.push(element.id);
    });

    this.categories.forEach(element => {
      if (existingCategories.indexOf(element.value) > -1) {
        element.checked = true;
      }
    });
  }

  initLabels() {
    const existingLabels = [];
    this.noteDetail.labels.forEach(element => {
      existingLabels.push(element.id);
    });
    this.noteDetail.labels = existingLabels;
  }

  getNotesDetail(res: any) {
    if (res.resultBody) {
      this.noteDetail = res.resultBody;
    }
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
    this.contentImageList = this.noteDetail.coverUrl ? [{ url: this.noteDetail.coverUrl, selected: true }] : [];

    const imageList = $('.editormd-preview-container img:not([class*=\'emoji\'])');
    if (!imageList || imageList.length <= 0) { return; }
    for (let i = 0; i < imageList.length; i++) {
      const imageUrl = $(imageList[i]).attr('src');
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

  addLabel(input: HTMLInputElement): void {
    const newLabel = input.value;
    if (!newLabel) return;
    const labelDto = { name: newLabel, type: LabelType.NOTE };
    this.newLabelLoading = true;
    this.http.put(Label, labelDto).subscribe(res => {
      this.newLabelLoading = false;
      input.value = '';
      this.http.get(Label + LabelType.NOTE).subscribe(result => { this.getLabels(result); });
    });
  }


  getLabels(res: any) {
    if (!res.resultBody) { return; }
    this.labels = res.resultBody;
  }

  getCategories(res: any) {
    this.categories = [];
    res.resultBody.forEach(element => {
      const c = {
        label: element.name,
        value: element.id,
        checked: false
      };
      this.categories.push(c);
    });
  }

  getSelectCategoies() {
    let selectCategoies = [];
    this.categories.forEach(element => {
      if (element.checked === true && selectCategoies.indexOf(element.value) === -1) {
        selectCategoies = [...selectCategoies, element.value];
      }
    });
    return selectCategoies;
  }

  getSelectedCoverUrl() {
    const selectCoverList = this.contentImageList.filter(t => {
      return t.selected === true;
    });
    return selectCoverList.length > 0 ? selectCoverList[0].url : '';
  }

  sumbit() {
    this.noteDetail.content = this.editorMD.getMarkdown();
    this.noteDetail.coverUrl = this.getSelectedCoverUrl();
    this.noteDetail.categories = this.getSelectCategoies();
    this.submitLoading = true;
    this.http.put(Note, this.noteDetail).subscribe(res => {
      this.submitLoading = false;
      this.router.navigate(['/notes/list']);
    });

  }

}
