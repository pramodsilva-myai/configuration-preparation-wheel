// 1. CanActivate Guard
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.isAuthenticated$.pipe(
      map(isAuth => {
        if (isAuth) {
          return true;
        }
        return this.router.createUrlTree(['/login'], {
          queryParams: { returnUrl: state.url }
        });
      })
    );
  }
}

// 2. CanDeactivate Guard
export interface CanComponentDeactivate {
  canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UnsavedChangesGuard implements CanDeactivate<CanComponentDeactivate> {
  canDeactivate(
    component: CanComponentDeactivate,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return component.canDeactivate ? component.canDeactivate() : true;
  }
}

// Example component using CanDeactivate
@Component({
  selector: 'app-edit-form',
  template: `
    <form [formGroup]="form">
      <input formControlName="name">
      <button (click)="save()">Save</button>
    </form>
  `
})
export class EditFormComponent implements CanComponentDeactivate {
  form: FormGroup;
  savedData: any;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['']
    });
    this.savedData = this.form.value;
  }

  canDeactivate(): boolean | Observable<boolean> {
    if (JSON.stringify(this.form.value) !== JSON.stringify(this.savedData)) {
      return confirm('You have unsaved changes. Do you really want to leave?');
    }
    return true;
  }

  save() {
    this.savedData = this.form.value;
  }
}

// 3. Resolver
interface User {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserResolver implements Resolve<User> {
  constructor(private userService: UserService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<User> | Promise<User> | User {
    const id = route.paramMap.get('id');
    return this.userService.getUser(id).pipe(
      catchError(error => {
        return EMPTY;
      })
    );
  }
}

// 4. Route Configuration using Guards and Resolver
const routes: Routes = [
  {
    path: 'edit/:id',
    component: EditFormComponent,
    canActivate: [AuthGuard],
    canDeactivate: [UnsavedChangesGuard],
    resolve: {
      user: UserResolver
    }
  }
];

// 5. Component using Resolver data
@Component({
  selector: 'app-user-edit',
  template: `
    <div *ngIf="user">
      <h2>Edit User: {{user.name}}</h2>
      <!-- form content -->
    </div>
  `
})
export class UserEditComponent implements OnInit {
  user: User;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.user = data.user;
    });
  }
}

// 6. CanActivateChild Guard
@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivateChild {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.isAdmin$.pipe(
      map(isAdmin => {
        if (isAdmin) {
          return true;
        }
        return this.router.createUrlTree(['/unauthorized']);
      })
    );
  }
}

// Usage in routes
const adminRoutes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivateChild: [AdminGuard],
    children: [
      { path: 'users', component: AdminUsersComponent },
      { path: 'settings', component: AdminSettingsComponent }
    ]
  }
];
