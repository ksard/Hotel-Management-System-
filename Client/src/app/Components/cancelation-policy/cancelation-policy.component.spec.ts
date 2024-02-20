import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelationPolicyComponent } from './cancelation-policy.component';

describe('CancelationPolicyComponent', () => {
  let component: CancelationPolicyComponent;
  let fixture: ComponentFixture<CancelationPolicyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelationPolicyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancelationPolicyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
