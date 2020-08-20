import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProAccountSettingsBaseComponent } from './account/settings/base/base.component';
import { ProAccountSettingsBindingComponent } from './account/settings/binding/binding.component';
import { ProAccountSettingsNotificationComponent } from './account/settings/notification/notification.component';
import { ProAccountSettingsSecurityComponent } from './account/settings/security/security.component';
import { ProAccountSettingsComponent } from './account/settings/settings.component';


const routes: Routes = [{
  path: 'account',
  children: [
    {
      path: 'settings',
      component: ProAccountSettingsComponent,
      children: [
        { path: '', redirectTo: 'base', pathMatch: 'full' },
        {
          path: 'base',
          component: ProAccountSettingsBaseComponent,
          data: { title: '基本设置' },
        },
        {
          path: 'security',
          component: ProAccountSettingsSecurityComponent,
          data: { title: '安全设置' },
        },
        {
          path: 'binding',
          component: ProAccountSettingsBindingComponent,
          data: { title: 'pro-account-settings' },
        },
        {
          path: 'notification',
          component: ProAccountSettingsNotificationComponent,
          data: { title: 'pro-account-settings' },
        },
      ],
    },
  ],
},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProRoutingModule { }
