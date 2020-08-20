import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';
import { NotesRoutingModule } from './notes-routing.module';
import { NotesListComponent } from './list/list.component';
import { NotesArticleComponent } from './article/article.component';
import { NotesTagsComponent } from './tags/tags.component';
import { NotesCategoryComponent } from './category/category.component';
import { NotesEditComponent } from './edit/edit.component';

const COMPONENTS = [
  NotesListComponent,
  NotesArticleComponent,
  NotesTagsComponent,
  NotesCategoryComponent,
  NotesEditComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    NotesRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
})
export class NotesModule { }
