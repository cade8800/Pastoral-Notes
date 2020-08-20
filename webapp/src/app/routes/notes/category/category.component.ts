import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Category } from '../../../config/constants';

@Component({
  selector: 'app-notes-category',
  templateUrl: './category.component.html',
})
export class NotesCategoryComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  categoryLoading = false;
  isVisible = false;
  categories: any = [];
  categoryDto: any = {};


  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryLoading = true;
    this.http.get(Category).subscribe(res => {
      this.categories = res.resultBody;
      this.categoryLoading = false;
    });
  }

  edit(category: any) {
    this.categoryDto = category;
    this.isVisible = true;
  }

  delete(category: any) {
    if (category.id) {
      this.http.delete(Category + category.id).subscribe(res => {
        this.getCategories();
      });
    }
  }

  add() {
    this.categoryDto = { name: '' };
    this.isVisible = true;
  }

  handleOk(): void {
    if (!this.categoryDto.name) return;

    this.http.put(Category, this.categoryDto).subscribe(res => {
      this.isVisible = false;
      this.getCategories();
    });

  }

  handleCancel(): void {
    this.getCategories();
    this.isVisible = false;
  }

}
