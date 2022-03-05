import { PostsPageComponent } from './posts-page/posts-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CommentsPageComponent } from './comments-page/comments-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: 'Home', component: HomePageComponent},
  {path: '', redirectTo: '/Home', pathMatch: 'full' },
  {path: 'Posts', component: PostsPageComponent},
  {path: 'Comments/:id', component: CommentsPageComponent},
  // {path: 'Users/1'},
  // {path: 'Users/1/Posts'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    anchorScrolling: 'enabled',
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

