import { ChangeDetectionStrategy, Component, HostListener } from '@angular/core';
import * as screenfull from 'screenfull';

@Component({
  selector: 'header-fullscreen',
  template: `
  <div class="alain-default__nav-item">
    <i nz-icon [nzType]="status ? 'fullscreen-exit' : 'fullscreen'"  class="alain-default__nav-item-icon"></i>
    <!-- {{ status ? '退出全屏' : '全屏' }} -->
  </div>
  `,
  // tslint:disable-next-line: no-host-metadata-property
  host: {
    '[class.d-block]': 'true',
  },
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderFullScreenComponent {
  status = false;
  private get sf(): screenfull.Screenfull {
    return screenfull as screenfull.Screenfull;
  }

  @HostListener('window:resize')
  _resize() {
    this.status = this.sf.isFullscreen;
  }

  @HostListener('click')
  _click() {
    if (this.sf.isEnabled) {
      this.sf.toggle();
    }
  }
}
