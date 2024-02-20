import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';

export let browserRefresh = false;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'BlueParadise';
  name = 'Angular 6';
  subscription: Subscription;
  browserRefresh!: boolean;

  constructor(private router: Router) {
    this.subscription = router.events.subscribe((event) => {
        if (event instanceof NavigationStart) {
          browserRefresh = !router.navigated;
        }
    });
  }


  ngOnInit(): void {
    this.browserRefresh = browserRefresh;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
