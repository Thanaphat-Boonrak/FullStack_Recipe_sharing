import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service';
import { inject } from '@angular/core';

export const authRedirectGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isLogin()) {
    router.navigate(['/home']);
    return false;
  }
  return true;
};
