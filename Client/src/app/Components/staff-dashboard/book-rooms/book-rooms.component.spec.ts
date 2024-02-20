import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookRoomsComponent } from './book-rooms.component';

describe('BookRoomsComponent', () => {
  let component: BookRoomsComponent;
  let fixture: ComponentFixture<BookRoomsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookRoomsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookRoomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
