import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { _HttpClient, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { zip } from 'rxjs';
import { UserInfo, FileUpload } from "../../../../../config/constants";
import { NzUploadChangeParam } from 'ng-zorro-antd/upload';

@Component({
  selector: 'app-account-settings-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProAccountSettingsBaseComponent implements OnInit {

  constructor(private http: _HttpClient,
    private cdr: ChangeDetectorRef,
    private msg: NzMessageService,
    private setting: SettingsService) { }

  fileUpload = FileUpload;
  avatar = '';
  userLoading = false;
  user: any = {};
  avatarList = ['assets/img/1.png',
    'assets/img/2.png',
    'assets/img/3.png',
    'assets/img/4.png',
    'assets/img/5.png',
    'assets/img/6.png'];

  ngOnInit(): void {
    this.user = { ... this.setting.user };
    if (!this.user.avatarUrl) {
      this.user.avatarUrl = 'assets/img/default_avatar.jpg';
    }
  }

  uploadAvatar(info: NzUploadChangeParam): void {
    if (info.file.status === 'done') {
      if (info.file.response.resultBody.fileUri)
        this.user.avatarUrl = info.file.response.resultBody.fileUri;
    } else if (info.file.status === 'error') {
      this.msg.error("上传失败，请稍后再试");
    }
  }

  selectedAvatar(url: string) {
    if (url)
      this.user.avatarUrl = url;
  }

  save() {
    // this.msg.success(JSON.stringify(this.user));
    console.log(this.user);

    this.http.put(UserInfo, this.user).subscribe((res: any) => {
      this.msg.success('更新成功');
      this.setting.setUser({ ...this.user });
    });

    return false;
  }
}
