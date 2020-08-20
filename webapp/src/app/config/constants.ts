export const Host = 'http://localhost';

export const UserPasswordLogin = Host + '/user/login';
export const UserMiniProgramLogin = Host + '/user/login';
export const UserMobilePhoneCaptchaLogin = Host + '/user/login/c';

export const UserRegister = Host + '/user/register?_allow_anonymous=true';

export const UserInfo = Host + '/user/info/get';

export const UserUpdatePassword = Host + '/user/p';

export const GetSmsCaptcha = Host + '/user/login/v/s';
export const GetMailCaptcha = Host + '/user/login/v/e';

export const Note = Host + '/note/get/';

export const Diary = Host + '/diary/get/';

export const Label = Host + '/label/get/';

export const Category = Host + '/category/get/';

export const FileUpload = Host + '/file/upload';


export enum LabelType { NOTE = 'NOTE', DIARY = 'DIARY' }












