// 1. Template-Driven Form
// template-form.component.ts
@Component({
  selector: 'app-template-form',
  template: `
    <form #userForm="ngForm" (ngSubmit)="onSubmit(userForm.value)">
      <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" [(ngModel)]="user.name" required #name="ngModel">
        <div *ngIf="name.invalid && (name.dirty || name.touched)">
          Name is required
        </div>
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" [(ngModel)]="user.email" required email #email="ngModel">
        <div *ngIf="email.invalid && (email.dirty || email.touched)">
          Valid email is required
        </div>
      </div>
      <button type="submit" [disabled]="userForm.invalid">Submit</button>
    </form>
  `
})
export class TemplateFormComponent {
  user = {
    name: '',
    email: ''
  };

  onSubmit(formValue: any) {
    console.log('Form submitted', formValue);
  }
}

// 2. Reactive Form
// reactive-form.component.ts
@Component({
  selector: 'app-reactive-form',
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <div>
        <label for="name">Name:</label>
        <input type="text" id="name" formControlName="name">
        <div *ngIf="userForm.get('name').invalid && (userForm.get('name').dirty || userForm.get('name').touched)">
          <div *ngIf="userForm.get('name').errors?.required">Name is required</div>
          <div *ngIf="userForm.get('name').errors?.minlength">Name must be at least 3 characters</div>
        </div>
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" formControlName="email">
        <div *ngIf="userForm.get('email').invalid && (userForm.get('email').dirty || userForm.get('email').touched)">
          <div *ngIf="userForm.get('email').errors?.required">Email is required</div>
          <div *ngIf="userForm.get('email').errors?.email">Invalid email format</div>
        </div>
      </div>
      <button type="submit" [disabled]="userForm.invalid">Submit</button>
    </form>
  `
})
export class ReactiveFormComponent implements OnInit {
  userForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      console.log('Form submitted', this.userForm.value);
    }
  }
}

// 3. Custom Validator
export function passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  if (password && confirmPassword && password.value !== confirmPassword.value) {
    return { passwordMismatch: true };
  }
  return null;
}

// Usage in Reactive Form
@Component({
  selector: 'app-password-form',
  template: `
    <form [formGroup]="passwordForm" (ngSubmit)="onSubmit()">
      <input type="password" formControlName="password">
      <input type="password" formControlName="confirmPassword">
      <div *ngIf="passwordForm.errors?.passwordMismatch">
        Passwords do not match
      </div>
      <button type="submit">Submit</button>
    </form>
  `
})
export class PasswordFormComponent implements OnInit {
  passwordForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.passwordForm = this.fb.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, { validators: passwordMatchValidator });
  }

  onSubmit() {
    if (this.passwordForm.valid) {
      console.log('Form submitted');
    }
  }
}
