import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, HostBinding, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'header-search',
  template: `
    <nz-input-group [nzAddOnBeforeIcon]="focus ? 'arrow-down' : 'search'">
      <input
        nz-input
        [(ngModel)]="q"
        (focus)="qFocus()"
        (blur)="qBlur()"
        (keyup)='qKeyup($event)'
        [placeholder]="''"
      />
    </nz-input-group>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderSearchComponent implements AfterViewInit {
  q: string;

  qIpt: HTMLInputElement;

  @HostBinding('class.alain-default__search-focus')
  focus = false;

  @HostBinding('class.alain-default__search-toggled')
  searchToggled = false;

  @Input()
  set toggleChange(value: boolean) {
    if (typeof value === 'undefined') {
      return;
    }
    this.searchToggled = true;
    this.focus = true;
    setTimeout(() => this.qIpt.focus(), 3000);
  }

  constructor(private el: ElementRef,
    private router: Router) {
  }

  ngAfterViewInit() {
    this.qIpt = (this.el.nativeElement as HTMLElement).querySelector('.ant-input') as HTMLInputElement;
  }

  qFocus() {
    this.focus = true;
  }

  qBlur() {
    this.focus = false;
    this.searchToggled = false;
  }

  qKeyup(ev: any) {
    if (ev.which === 13) {
      if (window.location.href.indexOf('/notes/') > -1) {
        this.router.navigate(['/notes/list', { key: this.q || '' }]);
      } else if (window.location.href.indexOf('/diary/') > -1) {
        this.router.navigate(['/diary/list', { key: this.q || '' }]);
      }
    }
  }
}
