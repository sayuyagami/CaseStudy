import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CropsdataComponent } from './cropsdata.component';

describe('CropsdataComponent', () => {
  let component: CropsdataComponent;
  let fixture: ComponentFixture<CropsdataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CropsdataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CropsdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
