import { NgModule } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatDividerModule} from '@angular/material/divider';
import {MatBadgeModule} from '@angular/material/badge';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatMenuModule} from '@angular/material/menu';
import {MatInputModule} from '@angular/material/input';


const Material = [
  MatIconModule,
  MatCardModule,
  MatButtonModule,
  MatSidenavModule,
  MatGridListModule,
  MatDividerModule,
  MatBadgeModule,
  MatButtonToggleModule,
  MatMenuModule,
  MatInputModule
];

@NgModule({
  imports: [Material],
  exports: [Material]
})
export class MaterialModule { }
