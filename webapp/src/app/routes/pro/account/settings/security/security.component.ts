import { ChangeDetectionStrategy, Component } from '@angular/core';
import { _HttpClient, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UserUpdatePassword } from "../../../../../config/constants";
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators, MinLengthValidator } from '@angular/forms';
import { min } from 'rxjs/operators';

@Component({
  selector: 'app-account-settings-security',
  templateUrl: './security.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProAccountSettingsSecurityComponent {

  validateForm: FormGroup;
  isVisible = false;

  constructor(
    public msg: NzMessageService,
    public setting: SettingsService,
    private http: _HttpClient,
    private fb: FormBuilder) {

    this.validateForm = this.fb.group({
      oldPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(32)]],
      newPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(32)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(32), ProAccountSettingsSecurityComponent.passwordEquar]]
    });

  }

  static passwordEquar(control: FormControl) {
    if (!control || !control.parent) {
      return null;
    }
    if (control.value !== control.parent.get('newPassword').value) {
      return { equar: true };
    }
    return null;
  }

  editPasswordOk(): void {

    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsDirty();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    if (this.validateForm.invalid) {
      return;
    }

    this.http.put(UserUpdatePassword, this.validateForm.value).subscribe(res => {
      this.msg.success("密码重置成功");
      this.validateForm.reset();
      this.isVisible = false;
    });

  }

}
