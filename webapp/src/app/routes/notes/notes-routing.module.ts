import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotesListComponent } from './list/list.component';
import { NotesArticleComponent } from './article/article.component';
import { NotesTagsComponent } from './tags/tags.component';
import { NotesCategoryComponent } from './category/category.component';
import { NotesEditComponent } from './edit/edit.component';

const routes: Routes = [
  { path: 'list', component: NotesListComponent, runGuardsAndResolvers: 'always' },
  { path: 'article', component: NotesArticleComponent },
  { path: 'tags', component: NotesTagsComponent },
  { path: 'category', component: NotesCategoryComponent },
  { path: 'edit', component: NotesEditComponent },
  { path: 'new', component: NotesEditComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotesRoutingModule { }
