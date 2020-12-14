import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from 'src/app/services/authentification.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  loggedIn = false;
  constructor(private authentificationService:AuthentificationService) { }

  ngOnInit(): void {
  }

  logoutClick(event:any)
  {
    event.preventDefault();
    this.authentificationService.logout();
  }


}
