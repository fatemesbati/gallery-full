import { GetDataService } from './../get-data.service';
import { ActivatedRoute } from '@angular/router';

import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-comments-page',
  templateUrl: './comments-page.component.html',
  styleUrls: ['./comments-page.component.css']
})
export class CommentsPageComponent implements OnInit {

  comments = [];
  postUrl = this.route.snapshot.paramMap.get('url')
  postId = this.route.snapshot.paramMap.get('id');
  toggle: boolean = false;
  image: string;


  constructor(
    private apiService: GetDataService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
        // this.apiService.getComment().subscribe((data)=>{
        //     for(let index = 0; index < Object.keys(data).length; index++) {
        //       if(data[index]['postId'] === this.id){
        //         this.comments[this.count] = data[index];
        //         this.count++;
        //       }
        //     }
        //     this.ids = this.count;
        // });

        // this.getPhoto();
        // this.getPost();
  }


    // deleteComments(comment){
    //   this.apiService.deleteComment(comment.id).subscribe(() => {
    //     this.comments = this.comments.filter(item => item.id !== comment.id )
    //   });
    // }

    // addComments(newComment){
    //   if(newComment){
    //     let obj = {
    //       body: newComment,
    //       id: this.count++,
    //     }
    //     this.apiService.postComment(newComment).subscribe(() => {
    //       this.comments.push(obj);
    //    });
    //   }
    // }

    // getPhoto(): void {
    //   this.apiService.getPhoto().subscribe((data)=>{
    //     this.photo = data[this.id - 1]['url'];
    //   });
    // }

    // getPost(): void {
    //   this.apiService.getPost().subscribe((data)=>{
    //     this.post = data[this.id - 1];
    //   });
    // }

    clearContents(element) {
      element.value = '';
    }

    assign() {
      this.toggle = !this.toggle;
      this.getComments();
    }


    /////////////////////////////////////////
  sendComment(comment: string) {
    let post = {id: this.postId, image: this.postUrl}
    this.apiService.sendComment(comment, post).subscribe((data: any) => {
      this.getComments();
    });
  }

  getComments() {
    this.apiService.getComments(this.postId).subscribe((data: any) => {
      this.comments = data;
    });
  }

  deleteComment(id: string) {
    this.apiService.deleteComment(id).subscribe((data: any) => {
      this.getComments();
    });
  }

  hi(image: string) {
    this.image = '/external/' + image;
    return this.image;
  }
}



