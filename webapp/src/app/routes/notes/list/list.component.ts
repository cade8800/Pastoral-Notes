import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { Category, Label, Note, LabelType } from '../../../config/constants';

@Component({
  selector: 'app-notes-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.less']
})
export class NotesListComponent implements OnInit, OnDestroy {


  constructor(
    private http: _HttpClient,
    public router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.navigationSubscription = this.router.events.subscribe((event: any) => {

      if (event instanceof NavigationEnd) {

        this.getNotesInput.pageIndex = 1;
        this.getNotesInput.keyword = this.activatedRoute.snapshot.paramMap.get('key');

        this.getNotesInput.categoryId = this.activatedRoute.snapshot.paramMap.get('categoryid');
        if (!this.getNotesInput.categoryId) {
          this.categories.forEach((element: any) => {
            if (element.selected)
              this.getNotesInput.categoryId = element.id;
          });
        }

        this.getNotesInput.labelId = this.activatedRoute.snapshot.paramMap.get('labelid');
        if (!this.getNotesInput.labelId) {
          this.labels.forEach((element: any) => {
            if (element.selected)
              this.getNotesInput.labelId = element.id;
          });
        }

        this.getNotes();
      }

    });
  }

  navigationSubscription;
  categoryLoading = false;
  noteLoading = false;
  labelLoading = false;
  loadMore = false;
  noteLoadingEnd = false;
  notePages: any = { content: [] };
  labels: any = [];
  categories: any = [];
  getNotesInput = {
    categoryId: '',
    keyword: '',
    labelId: '',
    pageIndex: 1,
    pageSize: 10,
    hasNoLabels: false,
    hasNoCategories: false
  };

  ngOnInit(): void {
    this.getLabels();
    this.getCategories();
  }
  ngOnDestroy(): void {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  getNotes() {
    this.noteLoading = true;
    this.http.post(Note, this.getNotesInput).subscribe(res => {
      if (this.getNotesInput.pageIndex <= 1) {
        this.notePages = res.resultBody;
      } else {
        this.notePages.content = [...this.notePages.content, ...res.resultBody.content];
      }
      this.noteLoading = false;
      this.loadMore = false;

      this.noteLoadingEnd = this.getNotesInput.pageIndex >= res.resultBody.totalPages ? true : false;
    });
  }

  getLabels() {
    this.labelLoading = true;
    this.http.get(Label + LabelType.NOTE).subscribe(res => {
      this.labels = res.resultBody;

      if (this.getNotesInput.labelId) {
        this.labels.forEach(element => {
          element.selected = element.id === this.getNotesInput.labelId;
        });
      }

      this.labelLoading = false;
    });
  }

  getCategories() {
    this.categoryLoading = true;
    this.http.get(Category).subscribe(res => {
      this.categories = res.resultBody;

      if (this.getNotesInput.categoryId) {
        this.categories.forEach(element => {
          if (element.id === this.getNotesInput.categoryId) {
            element.selected = true;
          } else {
            element.selected = false;
          }
        });
      }

      this.categoryLoading = false;
    });
  }

  edit(item: any) {
    if (item.id) {
      this.router.navigate(['/notes/edit', { id: item.id }]);
    }
  }

  view(item: any) {
    if (item.id) {
      this.router.navigate(['/notes/article', { id: item.id }]);
    }
  }

  changeLabel(checked: boolean, tag: any): void {
    if (checked) {
      this.labels.forEach(element => {
        if (element.id !== tag.id) { element.selected = false; }
      });
      this.getNotesInput.labelId = tag.id;
    } else {
      this.getNotesInput.labelId = '';
    }
    this.getNotesInput.hasNoLabels = false;
    this.getNotesInput.pageIndex = 1;
    this.getNotes();
  }
  getHasNoLabelNotes(event: any) {
    this.labels.forEach(element => element.selected = false);
    this.getNotesInput.labelId = '';
    this.getNotesInput.pageIndex = 1;
    this.getNotes();
  }

  changeCategory(item: any) {
    item.selected = !item.selected;
    if (item.selected) {
      this.categories.forEach(e => {
        if (e.id !== item.id) {
          e.selected = false;
        }
      });
      this.getNotesInput.categoryId = item.id;
    } else {
      this.getNotesInput.categoryId = '';
    }
    this.getNotesInput.hasNoCategories = false;
    this.getNotesInput.pageIndex = 1;
    this.getNotes();
  }
  getHasNoCategoryNotes() {
    this.getNotesInput.hasNoCategories = !this.getNotesInput.hasNoCategories;
    this.categories.forEach((e: any) => e.selected = false);
    this.getNotesInput.categoryId = '';
    this.getNotesInput.pageIndex = 1;
    this.getNotes();
  }

  loadMoreNote() {
    this.loadMore = true;
    this.getNotesInput.pageIndex++;
    this.getNotes();
  }

  deleteNote(note: any) {
    if (!note.id) return;
    this.http.delete(Note + note.id).subscribe(res => {
      this.getNotesInput.pageIndex = 1;
      this.getNotes();
    });
  }


}
