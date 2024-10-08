// 1. Basic Observable and Subscription
@Component({
  selector: 'app-timer',
  template: `<p>Timer: {{count}}</p>`
})
export class TimerComponent implements OnInit, OnDestroy {
  count = 0;
  private subscription: Subscription;

  ngOnInit() {
    const timer$ = interval(1000);
    this.subscription = timer$.subscribe(
      (value) => this.count = value
    );
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}

// 2. Subject Types and Usage
@Injectable({
  providedIn: 'root'
})
export class DataService {
  // Regular Subject
  private subject = new Subject<string>();
  
  // BehaviorSubject with initial value
  private behaviorSubject = new BehaviorSubject<string>('initial value');
  
  // ReplaySubject that remembers last 2 values
  private replaySubject = new ReplaySubject<string>(2);
  
  // AsyncSubject only emits last value upon completion
  private asyncSubject = new AsyncSubject<string>();

  // Observables from subjects
  subject$ = this.subject.asObservable();
  behaviorSubject$ = this.behaviorSubject.asObservable();
  replaySubject$ = this.replaySubject.asObservable();
  asyncSubject$ = this.asyncSubject.asObservable();

  emitValue(value: string) {
    this.subject.next(value);
    this.behaviorSubject.next(value);
    this.replaySubject.next(value);
    this.asyncSubject.next(value);
  }

  complete() {
    this.asyncSubject.complete();
  }
}

// 3. Common RxJS Operators
@Component({
  selector: 'app-rxjs-demo',
  template: `
    <input [formControl]="searchControl">
    <div *ngFor="let result of searchResults$ | async">{{result}}</div>
  `
})
export class RxjsDemoComponent implements OnInit {
  searchControl = new FormControl('');
  searchResults$: Observable<string[]>;

  constructor(private searchService: SearchService) {}

  ngOnInit() {
    this.searchResults$ = this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      filter(term => term.length >= 2),
      switchMap(term => this.searchService.search(term)),
      catchError(error => {
        console.error('Search error:', error);
        return of([]);
      })
    );
  }
}

// 4. Combining Observables
@Component({
  selector: 'app-data-combiner',
  template: `
    <div *ngIf="combinedData$ | async as data">
      <p>User: {{data.user.name}}</p>
      <p>Settings: {{data.settings.theme}}</p>
    </div>
  `
})
export class DataCombinerComponent implements OnInit {
  combinedData$: Observable<any>;

  constructor(
    private userService: UserService,
    private settingsService: SettingsService
  ) {}

  ngOnInit() {
    const user$ = this.userService.getUser();
    const settings$ = this.settingsService.getSettings();

    this.combinedData$ = combineLatest([user$, settings$]).pipe(
      map(([user, settings]) => ({ user, settings }))
    );

    // Alternative using forkJoin for HTTP requests
    this.combinedData$ = forkJoin({
      user: this.userService.getUser(),
      settings: this.settingsService.getSettings()
    });
  }
}

// 5. Error Handling and Retrying
@Injectable({
  providedIn: 'root'
})
export class RetryService {
  getData(): Observable<any> {
    return this.http.get('/api/data').pipe(
      retry(3),
      catchError(error => {
        if (error.status === 404) {
          return of({ empty: true });
        }
        return throwError(() => new Error('Critical error occurred'));
      })
    );
  }

  getDataWithBackoff(): Observable<any> {
    return this.http.get('/api/data').pipe(
      retryWhen(errors => 
        errors.pipe(
          concatMap((error, index) => {
            const retryAttempt = index + 1;
            if (retryAttempt > 3) {
              return throwError(() => new Error('Max retries reached'));
            }
            console.log(`Retry attempt ${retryAttempt}`);
            return timer(retryAttempt * 1000);
          })
        )
      )
    );
  }
}

// 6. Custom Operator
function filterByType<T>(type: string) {
  return (source: Observable<T>) =>
    new Observable<T>(subscriber => {
      return source.subscribe({
        next(value) {
          if (typeof value === type) {
            subscriber.next(value);
          }
        },
        error(error) { subscriber.error(error); },
        complete() { subscriber.complete(); }
      });
    });
}

// Usage of custom operator
const mixed$ = of(1, 'hello', true, 42);
const numbers$ = mixed$.pipe(filterByType('number'));
numbers$.subscribe(value => console.log(value)); // Outputs: 1, 42

// 7. Multicasting with Operators
@Component({
  selector: 'app-multicast-demo',
  template: `
    <button (click)="fetchData()">Fetch Data</button>
    <div *ngFor="let subscriber of subscribers">
      {{subscriber.data}}
    </div>
  `
})
export class MulticastDemoComponent {
  private readonly getData = () => 
    timer(1000).pipe(
      map(() => Math.random()),
      shareReplay(1)
    );

  subscribers = [];

  fetchData() {
    const shared$ = this.getData();
    
    // Multiple subscribers will get the same value
    for (let i = 0; i < 3; i++) {
      shared$.subscribe(data => {
        this.subscribers[i] = { data };
      });
    }
  }
}
