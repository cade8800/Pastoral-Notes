import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiaryTagsComponent } from './tags/tags.component';
import { DiaryArticleComponent } from './article/article.component';
import { DiaryListComponent } from './list/list.component';
import { DiaryEditComponent } from './edit/edit.component';

const routes: Routes = [
  { path: 'tags', component: DiaryTagsComponent },
  { path: 'article', component: DiaryArticleComponent },
  { path: 'list', component: DiaryListComponent },
  { path: 'edit', component: DiaryEditComponent },
  { path: 'new', component: DiaryEditComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DiaryRoutingModule { }
