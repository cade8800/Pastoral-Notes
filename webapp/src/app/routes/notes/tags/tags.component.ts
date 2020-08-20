import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { Label, LabelType } from '../../../config/constants';

@Component({
  selector: 'app-notes-tags',
  templateUrl: './tags.component.html',
})
export class NotesTagsComponent implements OnInit {

  constructor(private http: _HttpClient) { }

  labelLoading = false;
  isVisible = false;
  labels: any = [];
  labelDto: any = {};


  ngOnInit() {
    this.getLabels();
  }

  getLabels() {
    this.labelLoading = true;
    this.http.get(Label + LabelType.NOTE).subscribe(res => {
      this.labels = res.resultBody;
      this.labelLoading = false;
    });
  }

  edit(label: any) {
    this.labelDto = label;
    this.isVisible = true;
  }

  delete(label: any) {
    if (label.id) {
      this.http.delete(Label + label.id).subscribe(res => {
        this.getLabels();
      });
    }
  }

  add() {
    this.labelDto = { name: '', type: LabelType.NOTE };
    this.isVisible = true;
  }

  handleOk(): void {
    if (!this.labelDto.name) return;

    this.http.put(Label, this.labelDto).subscribe(res => {
      this.isVisible = false;
      this.getLabels();
    });

  }

  handleCancel(): void {
    this.getLabels();
    this.isVisible = false;
  }

}
