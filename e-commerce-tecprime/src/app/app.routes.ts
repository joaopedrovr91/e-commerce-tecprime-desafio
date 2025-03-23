import { Routes } from '@angular/router';
import { LoginComponent } from './pages/admin/login/login.component';
import { LayoutComponent } from './pages/admin/layout/layout.component';
import { ProductsComponent } from './pages/admin/products/products.component';
import { CartComponent } from './pages/admin/cart/cart.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { AuthGuard } from './guards/auth.guard';
import { RegisterComponent } from './pages/admin/register/register.component';

export const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent
    },
     {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: '',
        component: LayoutComponent,
        children: [
            {
                path: '', // Rota padrão (página inicial)
                component: ProductsComponent
            },
            {
                path: 'cart',
                component: CartComponent
            },
            {
                path: 'checkout',
                component: CheckoutComponent,
                canActivate: [AuthGuard] // Rota protegida
            }
        ]
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];
