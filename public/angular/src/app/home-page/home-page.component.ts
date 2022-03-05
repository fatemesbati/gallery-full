import { Component, OnInit } from '@angular/core';
import { GetDataService } from '../get-data.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {


  user = {};


  constructor( 
    private apiService: GetDataService,) { }


  ngOnInit(): void {
    this.apiService.getUser().subscribe((data)=>{
      this.user = data;
    });
  }
  
}
