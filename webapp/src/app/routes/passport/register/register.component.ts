import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { GetMailCaptcha, GetSmsCaptcha, UserRegister } from '../../../config/constants';

@Component({
  selector: 'passport-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less'],
})
export class UserRegisterComponent implements OnDestroy {

  constructor(fb: FormBuilder, private router: Router, public http: _HttpClient, public msg: NzMessageService) {
    this.form = fb.group({
      mail: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6), UserRegisterComponent.checkPassword.bind(this)]],
      confirm: [null, [Validators.required, Validators.minLength(6), UserRegisterComponent.passwordEquar]],
      mobilePrefix: ['+86'],
      mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      captcha: [null, [Validators.required]],
      mailCaptcha: [null, [Validators.required]],
    });
  }

  // #region fields

  get mail() {
    return this.form.controls.mail;
  }
  get password() {
    return this.form.controls.password;
  }
  get confirm() {
    return this.form.controls.confirm;
  }
  get mobile() {
    return this.form.controls.mobile;
  }
  get captcha() {
    return this.form.controls.captcha;
  }
  get mailCaptcha() {
    return this.form.controls.mailCaptcha;
  }

  form: FormGroup;
  error = '';
  type = 0;
  visible = false;
  status = 'pool';
  progress = 0;
  passwordProgressMap = {
    ok: 'success',
    pass: 'normal',
    pool: 'exception',
  };
  count = 0;
  mailCount = 0;
  interval$: any;
  mailInterval$: any;
  mailCaptchaLoding = false;
  smsCaptchaLoding = false;

  static checkPassword(control: FormControl) {
    if (!control) {
      return null;
    }
    const self: any = this;
    self.visible = !!control.value;
    if (control.value && control.value.length > 9) {
      self.status = 'ok';
    } else if (control.value && control.value.length > 5) {
      self.status = 'pass';
    } else {
      self.status = 'pool';
    }

    if (self.visible) {
      self.progress = control.value.length * 10 > 100 ? 100 : control.value.length * 10;
    }
  }

  static passwordEquar(control: FormControl) {
    if (!control || !control.parent) {
      return null;
    }
    if (control.value !== control.parent.get('password').value) {
      return { equar: true };
    }
    return null;
  }

  getCaptcha() {
    if (this.mobile.invalid) {
      this.mobile.markAsDirty({ onlySelf: true });
      this.mobile.updateValueAndValidity({ onlySelf: true });
      return;
    }

    this.smsCaptchaLoding = true;
    this.http.post(GetSmsCaptcha, { mobile: this.mobile.value }).subscribe(res => {
      if (res.success === false) {
        this.error = res.errorMsg;
      }
      this.smsCaptchaLoding = false;
    });

    this.count = 59;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
    }, 1000);
  }


  getMailCaptcha() {
    if (this.mail.invalid) {
      this.mail.markAsDirty({ onlySelf: true });
      this.mail.updateValueAndValidity({ onlySelf: true });
      return;
    }

    this.mailCaptchaLoding = true;
    this.http.post(GetMailCaptcha, { mail: this.mail.value }).subscribe(res => {
      if (res.success === false) {
        this.error = res.errorMsg;
      }
      this.mailCaptchaLoding = false;
    });

    this.mailCount = 59;
    this.mailInterval$ = setInterval(() => {
      this.mailCount -= 1;
      if (this.mailCount <= 0) {
        clearInterval(this.mailInterval$);
      }
    }, 1000);
  }

  // #endregion

  submit() {
    this.error = '';
    Object.keys(this.form.controls).forEach((key) => {
      this.form.controls[key].markAsDirty();
      this.form.controls[key].updateValueAndValidity();
    });
    if (this.form.invalid) {
      return;
    }
    const data = this.form.value;
    // console.log(data);
    this.http.post(UserRegister, data).subscribe((res) => {
      if (res.success === false) {
        this.error = res.errorMsg;
        return;
      }
      this.router.navigate(['/passport/register-result'], {
        queryParams: { email: data.mail },
      });
    });

  }

  ngOnDestroy(): void {
    if (this.interval$) {
      clearInterval(this.interval$);
    }
    if (this.mailInterval$) {
      clearInterval(this.mailInterval$);
    }
  }
}
