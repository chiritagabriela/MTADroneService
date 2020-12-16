import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthentificationService } from 'src/app/services/authentification.service';
import { UserModel } from '../../models/user-model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  loggedIn = false;
  constructor(private authentificationService:AuthentificationService,
    private router:Router) { 
    this.authentificationService.currentUser$.subscribe(
      userModel => this.loggedIn = userModel != null
    );
  }

  ngOnInit(): void {
  }

  logoutClick(event:any)
  {
    event.preventDefault();
    this.authentificationService.logout();
    this.router.navigate(['/home']);
  }


}
