import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SettingsService } from '@delon/theme';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent implements OnInit {
  searchToggleStatus: boolean;

  constructor(public settings: SettingsService) { }

  ngOnInit() {
    // 控制侧边菜单栏显示/隐藏
    this.settings.setLayout('collapsed', false);
  }

  toggleCollapsedSidebar() {
    this.settings.setLayout('collapsed', !this.settings.layout.collapsed);
  }

  searchToggleChange() {
    this.searchToggleStatus = !this.searchToggleStatus;
  }
}
