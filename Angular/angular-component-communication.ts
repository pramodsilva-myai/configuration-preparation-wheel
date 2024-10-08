// 1. Parent to Child Communication (@Input)
// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <app-child [data]="parentData"></app-child>
    <button (click)="updateData()">Update Data</button>
  `
})
export class ParentComponent {
  parentData = 'Initial data';

  updateData() {
    this.parentData = 'Updated data';
  }
}

// child.component.ts
@Component({
  selector: 'app-child',
  template: `<p>Received from parent: {{data}}</p>`
})
export class ChildComponent {
  @Input() data: string;
}

// 2. Child to Parent Communication (@Output)
// child.component.ts
@Component({
  selector: 'app-child',
  template: `
    <button (click)="sendDataToParent()">Send to Parent</button>
  `
})
export class ChildComponent {
  @Output() dataEvent = new EventEmitter<string>();

  sendDataToParent() {
    this.dataEvent.emit('Data from child');
  }
}

// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <app-child (dataEvent)="handleChildData($event)"></app-child>
    <p>{{receivedData}}</p>
  `
})
export class ParentComponent {
  receivedData: string;

  handleChildData(data: string) {
    this.receivedData = data;
  }
}

// 3. Service for Sibling Communication
// data.service.ts
@Injectable({
  providedIn: 'root'
})
export class DataService {
  private dataSubject = new BehaviorSubject<string>('Initial value');
  data$ = this.dataSubject.asObservable();

  updateData(newData: string) {
    this.dataSubject.next(newData);
  }
}

// sibling1.component.ts
@Component({
  selector: 'app-sibling1',
  template: `
    <button (click)="sendData()">Send to Sibling 2</button>
  `
})
export class Sibling1Component {
  constructor(private dataService: DataService) {}

  sendData() {
    this.dataService.updateData('Hello from Sibling 1');
  }
}

// sibling2.component.ts
@Component({
  selector: 'app-sibling2',
  template: `<p>{{receivedData}}</p>`
})
export class Sibling2Component implements OnInit {
  receivedData: string;

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.dataService.data$.subscribe(data => {
      this.receivedData = data;
    });
  }
}
