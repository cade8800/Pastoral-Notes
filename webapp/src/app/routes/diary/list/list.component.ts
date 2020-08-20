import { Component, OnInit, OnDestroy } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Diary, Label, LabelType } from "../../../config/constants";
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-diary-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.less']
})
export class DiaryListComponent implements OnInit, OnDestroy {

  constructor(
    private http: _HttpClient,
    public router: Router,
    private activatedRoute: ActivatedRoute
  ) {

    this.navigationSubscription = this.router.events.subscribe((event: any) => {

      if (event instanceof NavigationEnd) {

        this.getDiariesInput.pageIndex = 1;
        this.getDiariesInput.keyword = this.activatedRoute.snapshot.paramMap.get('key');

        this.getDiariesInput.labelId = this.activatedRoute.snapshot.paramMap.get('labelid');
        if (!this.getDiariesInput.labelId) {
          this.labels.forEach((element: any) => {
            if (element.selected)
              this.getDiariesInput.labelId = element.id;
          });
        }

        this.getDiaries();
      }

    });

  }

  navigationSubscription;
  noteLoading = false;
  labelLoading = false;
  noteLoadingEnd = false;
  loadMore = false;
  labels: any = [];
  diaryPages: any = { content: [] };
  dateRange;
  getDiariesInput = {
    categoryId: '',
    keyword: '',
    labelId: '',
    pageIndex: 1,
    pageSize: 10,
    startDate: null,
    endDate: null,
    hasNoLabels: false
  };

  selectedTags: string[] = [];

  ngOnInit(): void {
    this.getLabels();
  }
  ngOnDestroy(): void {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  getDiaries() {
    this.noteLoading = true;
    this.http.post(Diary, this.getDiariesInput).subscribe(res => {
      if (this.getDiariesInput.pageIndex <= 1) {
        this.diaryPages = res.resultBody;
      } else {
        this.diaryPages.content = [...this.diaryPages.content, ...res.resultBody.content];
      }
      this.noteLoading = false;
      this.loadMore = false;

      this.noteLoadingEnd = this.getDiariesInput.pageIndex >= res.resultBody.totalPages ? true : false;
    });
  }
  loadMoreDiaries() {
    this.loadMore = true;
    this.getDiariesInput.pageIndex++;
    this.getDiaries();
  }
  diaryEdit(item: any) {
    if (item.id) {
      this.router.navigate(['/diary/edit', { id: item.id }]);
    }
  }
  diaryView(item: any) {
    if (item.id) {
      this.router.navigate(['/diary/article', { id: item.id }]);
    }
  }
  diaryDelete(diary: any) {
    if (!diary.id) return;
    this.http.delete(Diary + diary.id).subscribe(res => {
      this.getDiariesInput.pageIndex = 1;
      this.getDiaries();
    });
  }

  getLabels() {
    this.labelLoading = true;
    this.http.get(Label + LabelType.DIARY).subscribe(res => {
      this.labels = res.resultBody;

      if (this.getDiariesInput.labelId) {
        this.labels.forEach(element => {
          element.selected = element.id === this.getDiariesInput.labelId;
        });
      }

      this.labelLoading = false;
    });
  }
  changeLabel(checked: boolean, tag: any): void {
    if (checked) {
      this.labels.forEach(element => {
        if (element.id !== tag.id) { element.selected = false; }
      });
      this.getDiariesInput.labelId = tag.id;
    } else {
      this.getDiariesInput.labelId = '';
    }
    this.getDiariesInput.hasNoLabels = false;
    this.getDiariesInput.pageIndex = 1;
    this.getDiaries();
  }
  getHasNoLabelDiaries(event: any) {
    this.labels.forEach(element => {
      element.selected = false;
    });
    this.getDiariesInput.labelId = '';
    this.getDiariesInput.pageIndex = 1;
    this.getDiaries();
  }


  rangeDateChange(value: [Date]) {
    this.getDiariesByDate();
  }


  calendarChange(value: Date): void {
    this.dateRange = [value, value];
    this.getDiariesByDate();
  }

  getDiariesByDate() {
    this.getDiariesInput.startDate = this.dateRange[0] || null;
    this.getDiariesInput.endDate = this.dateRange[1] || null;
    this.getDiariesInput.pageIndex = 1;
    this.getDiaries();
  }


}
