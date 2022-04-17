import { MaterialModule } from './material/material.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { CommentsPageComponent } from './comments-page/comments-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClientModule } from '@angular/common/http';
import { PostsPageComponent } from './posts-page/posts-page.component';
import { ReactiveFormsModule } from "@angular/forms";
import { NgxUploaderModule } from "ngx-uploader";
import { UploadImageComponent } from "./upload-image/upload-image.component";

@NgModule({
  declarations: [
    AppComponent,
    CommentsPageComponent,
    HomePageComponent,
    PostsPageComponent,
    UploadImageComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MaterialModule,
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgxUploaderModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }



