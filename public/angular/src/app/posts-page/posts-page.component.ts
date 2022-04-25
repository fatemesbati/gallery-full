import { ViewportScroller } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { GetDataService } from '../get-data.service';
import {HttpClient} from "@angular/common/http";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-posts-page',
  templateUrl: './posts-page.component.html',
  styleUrls: ['./posts-page.component.css']
})
export class PostsPageComponent implements OnInit {

  photoes = [];
  posts = [];
  count = 0;
  numbers = 10;
  deleteIndex = -1;
  // postRequestResponse: string;
  // toggle = true;

  postRequestResponse: any = null;
  image: string;
  comments: string[];


  constructor(
    private viewportScroller: ViewportScroller,
    private apiService: GetDataService,
    private httpClient: HttpClient
  ) { }


  ngOnInit(): void {
    // this.apiService.getPost().subscribe((data)=>{
    //     // this.posts = data;
    //     for (let index = 0; index < Object.keys(data).length; index++) {
    //       if(data[index]['userId'] == 1)
    //       this.posts[this.count] = data[index];
    //       this.count++;
    //     }
    // });

    // this.apiService.getPhoto().subscribe((data)=>{
    //   for (let index = 0; index < this.count; index++) {
    //     this.photoes[index] = data[index];
    //   }
    // });

    this.getPosts();
  }


  deletePosts(post){
    this.apiService.deletePost(post.id).subscribe(() => {
      this.posts = this.posts.filter(item => item.id !== post.id )
    });
    this.deletePhotoes(post);
  }

  deletePhotoes(photo){
      this.apiService.deleteComment(photo.id).subscribe(() => {
      this.photoes = this.photoes.filter(item => item.id !== photo.id )
    });
  }

  onClickScroll(elementId: string): void {
    this.viewportScroller.scrollToAnchor(elementId);
  }

  counter(i: number) {
    if(i != this.deleteIndex)
      return new Array(i);
  }

  /////////////////////////////////////////////////////

  public getPosts(): void {
    this.apiService.getPosts().subscribe((data: any) => {
      this.postRequestResponse = data;
    });
  }

  hi(image: string) {
    this.image = '/external/' + image;
    return this.image;
  }

  deletePost(id: string) {
    this.apiService.deletePost(id).subscribe((data: any) => {
      this.getPosts();
    });
  }
}



