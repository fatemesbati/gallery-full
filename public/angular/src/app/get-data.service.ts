import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class GetDataService {

  urlUser = 'https://jsonplaceholder.typicode.com';
  urlPost = 'https://jsonplaceholder.typicode.com';
  urlPhoto = 'https://jsonplaceholder.typicode.com/photos';

  // dataPostTestUrl = '/api/postTest';

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }


  ///////////////////////////
  // Users
  public getUser(){
    return this.httpClient.get(this.urlUser + "/users/1")
    .pipe(
      catchError(this.handleError)
    );
  }


  ///////////////////////////
  // Posts

  public getPost(){
    return this.httpClient.get(this.urlUser + "/posts")
    .pipe(
      catchError(this.handleError)
    );
  }

  public postPost(newPost){
    return this.httpClient.post(this.urlUser + "/posts", newPost);
  }

  public deletePost(idPost){
    return this.httpClient.delete(this.urlUser + "/posts/" + idPost);
  }



  ///////////////////////////
  // Comments

  public getComment(){
    return this.httpClient.get(this.urlPost + '/comments')
    .pipe(
      catchError(this.handleError)
    );
  }

  public postComment(newComment){
    return this.httpClient.post(this.urlPost + '/comments', JSON.stringify(newComment));
  }

  public deleteComment(idComment){
    return this.httpClient.delete(this.urlPost + '/comments/' + idComment);
  }



  ///////////////////////////
  // Photos

  public getPhoto(){
    return this.httpClient.get(this.urlPhoto);
  }

  public deletePhotoes(idPhotoes){
    return this.httpClient.delete(this.urlUser + "/photos/" + idPhotoes);
  }

  ////////////////////////////
  // test play back
  /**
   * Makes a http post request to send some data to backend & get response.
   */
  public getPosts(): Observable<any> {
    return this.httpClient.get("/posts");
  }

  public sendComment(content: string, post: any): Observable<any> {
    return this.httpClient.post("/comment", {content: content, post: post});
  }

  public getComments(post: any) {
    return this.httpClient.post("/comments", {post: post});
  }
}
