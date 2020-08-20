import { NgModule } from '@angular/core';

import { SharedModule } from '@shared';
import { ProRoutingModule } from './pro-routing.module';

import { ProAccountSettingsBaseComponent } from './account/settings/base/base.component';
import { ProAccountSettingsBindingComponent } from './account/settings/binding/binding.component';
import { ProAccountSettingsNotificationComponent } from './account/settings/notification/notification.component';
import { ProAccountSettingsSecurityComponent } from './account/settings/security/security.component';
import { ProAccountSettingsComponent } from './account/settings/settings.component';

const COMPONENTS = [
  ProAccountSettingsComponent,
  ProAccountSettingsBaseComponent,
  ProAccountSettingsSecurityComponent,
  ProAccountSettingsBindingComponent,
  ProAccountSettingsNotificationComponent,
];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [SharedModule, ProRoutingModule],
  declarations: [...COMPONENTS, ...COMPONENTS_NOROUNT],
  entryComponents: COMPONENTS_NOROUNT,
})
export class ProModule { }
