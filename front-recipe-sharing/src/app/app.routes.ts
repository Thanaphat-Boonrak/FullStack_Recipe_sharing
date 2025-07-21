import { Routes } from '@angular/router';
import { Auth } from './pages/auth/auth';
import { HomePages } from './pages/home-pages/home-pages';
import { authGuard } from './filter/auth-guard';
import { authRedirectGuard } from './filter/auth-redirect-guard';

export const routes: Routes = [
  { path: 'auth', component: Auth, canActivate: [authRedirectGuard] },
  { path: 'home', component: HomePages, canActivate: [authGuard] },
  { path: '**', redirectTo: 'auth' },
];
