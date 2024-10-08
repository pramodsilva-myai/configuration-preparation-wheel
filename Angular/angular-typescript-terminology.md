# Angular and TypeScript Terminology Guide

## TypeScript Fundamentals

### 1. Types
Basic types in TypeScript with examples:

```typescript
// Primitive Types
let isDone: boolean = false;
let decimal: number = 6;
let color: string = "blue";
let list: number[] = [1, 2, 3];
let tuple: [string, number] = ["hello", 10];

// Special Types
let notSure: any = 4;
let unknown: unknown = "could be anything";
let voidType: void = undefined;
let nullValue: null = null;
let undefinedValue: undefined = undefined;
let neverType: never; // Function that never returns

// Object Type
let obj: object = { key: "value" };

// Type Assertions
let someValue: unknown = "this is a string";
let strLength: number = (someValue as string).length;
```

### 2. Interfaces
Defining contracts in code:

```typescript
interface User {
  name: string;
  age?: number; // Optional property
  readonly id: number; // Read-only property
}

let user: User = {
  name: "John",
  id: 1
};

interface Callable {
  (text: string): boolean; // Function type
}

let check: Callable = (text: string) => text.length > 0;
```

### 3. Classes
Object-oriented programming in TypeScript:

```typescript
class Animal {
  // Property
  private name: string;
  
  // Constructor
  constructor(name: string) {
    this.name = name;
  }
  
  // Method
  move(distance: number = 0): void {
    console.log(`${this.name} moved ${distance}m.`);
  }
}

// Inheritance
class Dog extends Animal {
  bark(): void {
    console.log('Woof!');
  }
}

const dog = new Dog("Rex");
dog.bark();
dog.move(10);
```

### 4. Decorators
Annotations that can modify behavior:

```typescript
// Class Decorator
function sealed(constructor: Function) {
  Object.seal(constructor);
  Object.seal(constructor.prototype);
}

// Property Decorator
function configurable(value: boolean) {
  return function (target: any, propertyKey: string): void {
    let descriptor = Object.getOwnPropertyDescriptor(target, propertyKey) || {};
    descriptor.configurable = value;
    Object.defineProperty(target, propertyKey, descriptor);
  };
}

@sealed
class Example {
  @configurable(false)
  property: string;
}
```

## Angular Concepts

### 1. Components
The building blocks of Angular applications:

```typescript
@Component({
  selector: 'app-hero',
  template: `
    <h1>{{title}}</h1>
    <p>{{description}}</p>
  `,
  styles: [`
    h1 { color: blue; }
  `]
})
export class HeroComponent {
  @Input() title: string;
  @Output() selected = new EventEmitter<void>();
  description = 'A heroic component';
}
```

#### Key Terms:
- **Selector**: CSS selector that identifies this component in a template
- **Template**: HTML that defines the component's UI
- **Styles**: CSS styles specific to this component
- **@Input()**: Decorator for component input properties
- **@Output()**: Decorator for component output properties

### 2. Directives
Three types of directives in Angular:

```typescript
// 1. Component Directive - Already covered above

// 2. Structural Directive
@Directive({
  selector: '[appUnless]'
})
export class UnlessDirective {
  @Input() set appUnless(condition: boolean) {
    if (!condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
  
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {}
  
  private hasView = false;
}

// 3. Attribute Directive
@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input('appHighlight') highlightColor: string;

  @HostListener('mouseenter') onMouseEnter() {
    this.highlight(this.highlightColor || 'yellow');
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.highlight(null);
  }

  constructor(private el: ElementRef) {}

  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
```

### 3. Services and Dependency Injection

```typescript
@Injectable({
  providedIn: 'root'
})
export class HeroService {
  private heroes: Hero[] = [];

  constructor(private http: HttpClient) {}

  getHeroes(): Observable<Hero[]> {
    return this.http.get<Hero[]>('/api/heroes');
  }
}

@Component({
  selector: 'app-hero-list',
  template: `
    <ul>
      <li *ngFor="let hero of heroes$ | async">
        {{hero.name}}
      </li>
    </ul>
  `
})
export class HeroListComponent implements OnInit {
  heroes$: Observable<Hero[]>;

  constructor(private heroService: HeroService) {}

  ngOnInit() {
    this.heroes$ = this.heroService.getHeroes();
  }
}
```

#### Key Terms:
- **@Injectable()**: Decorator that marks a class as available for dependency injection
- **providedIn**: Specifies the scope of the service
- **Dependency Injection**: Design pattern where dependencies are "injected" into components

### 4. Lifecycle Hooks

```typescript
@Component({
  selector: 'app-lifecycle',
  template: '<p>{{data}}</p>'
})
export class LifecycleComponent implements OnInit, OnDestroy, OnChanges {
  @Input() data: string;

  ngOnChanges(changes: SimpleChanges) {
    console.log('Input property changed', changes);
  }

  ngOnInit() {
    console.log('Component initialized');
  }

  ngOnDestroy() {
    console.log('Component destroyed');
  }
}
```

Common Lifecycle Hooks:
- **ngOnChanges**: When data-bound property changes
- **ngOnInit**: After first ngOnChanges
- **ngOnDestroy**: Just before component/directive is destroyed

### 5. Pipes

```typescript
// Built-in pipes
@Component({
  template: `
    <p>{{date | date:'short'}}</p>
    <p>{{amount | currency:'USD'}}</p>
    <p>{{object | json}}</p>
  `
})

// Custom pipe
@Pipe({
  name: 'exponential'
})
export class ExponentialPipe implements PipeTransform {
  transform(value: number, exponent = 1): number {
    return Math.pow(value, exponent);
  }
}

// Usage: {{2 | exponential:3}} outputs 8
```

### 6. Modules

```typescript
@NgModule({
  declarations: [
    AppComponent,
    HeroComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    HeroService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

#### Key Terms:
- **declarations**: Components, directives, and pipes that belong to this module
- **imports**: Other modules whose exported classes are needed
- **providers**: Services that the module contributes to the global collection of services
- **bootstrap**: The main application view, called the root component

### 7. Routing

```typescript
const routes: Routes = [
  { path: 'heroes', component: HeroListComponent },
  { path: 'hero/:id', component: HeroDetailComponent },
  { path: '', redirectTo: '/heroes', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

// In component template
<router-outlet></router-outlet>
<a routerLink="/heroes">Heroes</a>

// In component class
constructor(private router: Router) {}

navigateToHero(id: number) {
  this.router.navigate(['/hero', id]);
}
```

## Best Practices and Common Patterns

### 1. Observable Pattern

```typescript
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-search',
  template: '<input [formControl]="searchControl">'
})
export class SearchComponent implements OnInit {
  searchControl = new FormControl();
  private searchTerms = new Subject<string>();

  ngOnInit() {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => this.searchService.search(term))
    ).subscribe(results => {
      // handle results
    });
  }
}
```

### 2. Component Communication

```typescript
// Parent to Child: Using @Input
@Component({
  selector: 'app-child',
  template: '<p>{{data}}</p>'
})
export class ChildComponent {
  @Input() data: string;
}

// Child to Parent: Using @Output
@Component({
  selector: 'app-child',
  template: '<button (click)="onClick()">Click me</button>'
})
export class ChildComponent {
  @Output() clicked = new EventEmitter<void>();

  onClick() {
    this.clicked.emit();
  }
}

// Usage in parent template
<app-child [data]="parentData" (clicked)="onChildClicked()"></app-child>
```

## Additional Resources
- [Official TypeScript Documentation](https://www.typescriptlang.org/docs/)
- [Angular Official Documentation](https://angular.io/docs)
- [RxJS Documentation](https://rxjs.dev/guide/overview)

