import { ViewportScroller } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { GetDataService } from '../get-data.service';

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
  postRequestResponse: string;
  // toggle = true;


  constructor(
    private viewportScroller: ViewportScroller,
    private apiService: GetDataService,
  ) { }


  ngOnInit(): void {
    this.apiService.getPost().subscribe((data)=>{
        // this.posts = data;
        for (let index = 0; index < Object.keys(data).length; index++) {
          if(data[index]['userId'] == 1)
          this.posts[this.count] = data[index];
          this.count++;
        }
    });


    this.apiService.getPhoto().subscribe((data)=>{
      for (let index = 0; index < this.count; index++) {
        this.photoes[index] = data[index];
      }
    });
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
    this.getData();
  }

  counter(i: number) {
    if(i != this.deleteIndex)
      return new Array(i);
  }

  public getData(): void {
    this.apiService.getData().subscribe((data: any) => {
      this.postRequestResponse = data;
    });
  }

  public sendData(name: string): void {
    this.apiService.sendData(name).subscribe(
      data => {
          console.log("Hi")
          console.log(JSON.stringify(data))
      }),
      error => {
        console.log(error)
      }
  }

}



