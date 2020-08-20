import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DiaryTagsComponent } from './tags.component';

describe('DiaryTagsComponent', () => {
  let component: DiaryTagsComponent;
  let fixture: ComponentFixture<DiaryTagsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiaryTagsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiaryTagsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
