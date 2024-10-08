// app-routing.module.ts
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { 
    path: 'users', 
    component: UsersComponent,
    children: [
      { path: ':id', component: UserDetailComponent }
    ]
  },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard]
  },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// Auth Guard
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
  ): boolean | UrlTree {
    if (this.authService.isAuthenticated()) {
      return true;
    }
    return this.router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url }
    });
  }
}

// Route Parameters and Navigation
@Component({
  selector: 'app-user-list',
  template: `
    <div *ngFor="let user of users">
      <a [routerLink]="['/users', user.id]" [queryParams]="{tab: 'profile'}">
        {{user.name}}
      </a>
    </div>
    <button (click)="navigateToUser(5)">Go to User 5</button>
  `
})
export class UserListComponent {
  users = [
    { id: 1, name: 'User 1' },
    { id: 2, name: 'User 2' }
  ];

  constructor(private router: Router) {}

  navigateToUser(id: number) {
    this.router.navigate(['/users', id], {
      queryParams: { tab: 'settings' },
      fragment: 'top'
    });
  }
}

// Handling Route Parameters
@Component({
  selector: 'app-user-detail',
  template: `<p>User ID: {{userId}}</p>`
})
export class UserDetailComponent implements OnInit {
  userId: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    // Option 1: Snapshot
    this.userId = this.route.snapshot.paramMap.get('id');

    // Option 2: Observable
    this.route.paramMap.subscribe(params => {
      this.userId = params.get('id');
    });

    // Query Parameters
    this.route.queryParamMap.subscribe(params => {
      const tab = params.get('tab');
      console.log('Current tab:', tab);
    });
  }
}
