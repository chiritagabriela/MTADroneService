import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthentificationService } from '../../services/authentification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  registerForm!: FormGroup;

  submitted = false;

  constructor(private formBuilder: FormBuilder, private authentificationService: AuthentificationService,
    private router: Router) {
     
      if(this.authentificationService.currentUserValue !== null 
        && this.authentificationService.currentUserValue !== undefined){
          this.router.navigate(['/home']);
        }

  }


  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['',Validators.required],
      password: ['',Validators.required]
    });
    
    this.registerForm = this.formBuilder.group({
      username: ['',Validators.required],
      password: ['',Validators.required],
      email: ['',Validators.required]
    });
  }

  get fLogin(){
    return this.loginForm.controls;
  }

  get fRegister(){
    return this.registerForm.controls;
  }

  onSubmitLogin(){
    
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.authentificationService.login(this.fLogin.username.value,this.fLogin.password.value)
    .subscribe(
      data => {
        console.log("succes");
        this.router.navigate(['/home']);
      },
      error =>{
        alert("Username or password is incorrect.");
      }
    );

  }

  onSubmitRegister(){
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.authentificationService.register(this.registerForm.value)
    .subscribe(
      data => {
        this.router.navigate(['/home']);
      },
      error =>{
        alert("Username already exists.");
      }
    );
  }
}
