// 1. Material Table with Sorting and Pagination
@Component({
  selector: 'app-data-table',
  template: `
    <mat-form-field>
      <mat-label>Filter</mat-label>
      <input matInput (keyup)="applyFilter($event)" placeholder="Ex. John" #input>
    </mat-form-field>

    <mat-table [dataSource]="dataSource" matSort>
      <ng-container matColumnDef="name">
        <mat-header-cell *matHeaderCellDef mat-sort-header> Name </mat-header-cell>
        <mat-cell *matCellDef="let element"> {{element.name}} </mat-cell>
      </ng-container>

      <ng-container matColumnDef="age">
        <mat-header-cell *matHeaderCellDef mat-sort-header> Age </mat-header-cell>
        <mat-cell *matCellDef="let element"> {{element.age}} </mat-cell>
      </ng-container>

      <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>
      <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
    </mat-table>

    <mat-paginator [pageSizeOptions]="[5, 10, 25, 100]" aria-label="Select page"></mat-paginator>
  `
})
export class DataTableComponent implements OnInit {
  displayedColumns: string[] = ['name', 'age'];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor() {
    const users = [
      { name: 'John', age: 30 },
      { name: 'Jane', age: 25 },
    ];
    this.dataSource = new MatTableDataSource(users);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

// 2. Dialog Component
@Component({
  selector: 'app-dialog-example',
  template: `
    <h2 mat-dialog-title>Delete Item</h2>
    <mat-dialog-content>
      Are you sure you want to delete this item?
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-button [mat-dialog-close]="true" color="warn">Delete</button>
    </mat-dialog-actions>
  `
})
export class DialogExampleComponent {}

// Usage in another component
@Component({
  selector: 'app-main',
  template: `
    <button mat-button (click)="openDialog()">Open Dialog</button>
  `
})
export class MainComponent {
  constructor(public dialog: MatDialog) {}

  openDialog() {
    const dialogRef = this.dialog.open(DialogExampleComponent, {
      width: '250px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('User confirmed deletion');
      }
    });
  }
}

// 3. CDK Drag and Drop
@Component({
  selector: 'app-drag-drop',
  template: `
    <div cdkDropList class="example-list" (cdkDropListDropped)="drop($event)">
      <div class="example-box" *ngFor="let item of items" cdkDrag>{{item}}</div>
    </div>
  `,
  styles: [`
    .example-list {
      width: 500px;
      max-width: 100%;
      border: solid 1px #ccc;
      min-height: 60px;
      display: block;
      background: white;
      border-radius: 4px;
      overflow: hidden;
    }

    .example-box {
      padding: 20px 10px;
      border-bottom: solid 1px #ccc;
      color: rgba(0, 0, 0, 0