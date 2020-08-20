import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { DiaryRoutingModule } from './diary-routing.module';
import { DiaryTagsComponent } from './tags/tags.component';
import { DiaryArticleComponent } from './article/article.component';
import { DiaryListComponent } from './list/list.component';
import { DiaryEditComponent } from './edit/edit.component';

const COMPONENTS = [
  DiaryTagsComponent,
  DiaryArticleComponent,
  DiaryListComponent,
  DiaryEditComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    DiaryRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
})
export class DiaryModule { }
