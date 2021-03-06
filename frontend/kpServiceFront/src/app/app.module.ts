import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { PayPalSuccessComponent } from './pay-pal-success/pay-pal-success.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BitcoinSuccessComponent } from './bitcoin-success/bitcoin-success.component';
import { BitcoinCancelComponent } from './bitcoin-cancel/bitcoin-cancel.component';
import { PayPalCancelComponent } from './pay-pal-cancel/pay-pal-cancel.component';
import { RegisterMerchantComponent } from './register-merchant/register-merchant.component';
import { RegisterCompanyComponent } from './register-company/register-company.component';
import { BuyJournalComponent } from './buy-journal/buy-journal.component';
import { RegisterMethodOfPaymentComponent } from './register-method-of-payment/register-method-of-payment.component';
import { CreatePaymentComponent } from './create-payment/create-payment.component';
import { JournalSubscriptionComponent } from './journal-subscription/journal-subscription.component';
import { CreateSubscriptionDetailsComponent } from './create-subscription-details/create-subscription-details.component';
import { SubSuccessComponent } from './sub-success/sub-success.component';
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { LoginComponent } from './login/login.component';
import { CancelSubscriptionComponent } from './cancel-subscription/cancel-subscription.component';

//':id1/:id2' - id1 - issn, id2 - cena
const appRoutes: Routes = [ {path: '', component : HomepageComponent},
                            {path : 'ppsuccess', component : PayPalSuccessComponent},
                            {path : 'ppcancel', component : PayPalCancelComponent},
                            {path : 'b/bitcoinCancel/:oid', component : BitcoinCancelComponent},
                            {path : 'b/bitcoinSuccess/:oid', component : BitcoinSuccessComponent},
                            {path : 'registerCompany', component : RegisterCompanyComponent},
                            {path : 'registerMerchant', component : RegisterMerchantComponent},
                            {path : 'journal/:id1/:id2', component : BuyJournalComponent},
                            {path : 'registerMethodOfPayment', component : RegisterMethodOfPaymentComponent},
                            {path : 'createPayment/:id', component : CreatePaymentComponent},
                            {path : 'subscription/:id1/:id2', component : JournalSubscriptionComponent},
                            {path : 'subDetails', component : CreateSubscriptionDetailsComponent},
                            {path : 'subSuccess', component : SubSuccessComponent},
                            {path : 'login', component : LoginComponent},
                            {path : 'cancelSub/:id1/:id2', component : CancelSubscriptionComponent}
 ]

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    PayPalSuccessComponent,
    BitcoinSuccessComponent,
    BitcoinCancelComponent,
    PayPalCancelComponent,
    RegisterMerchantComponent,
    RegisterCompanyComponent,
    BuyJournalComponent,
    RegisterMethodOfPaymentComponent,
    CreatePaymentComponent,
    JournalSubscriptionComponent,
    CreateSubscriptionDetailsComponent,
    SubSuccessComponent,
    LoginComponent,
    CancelSubscriptionComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing : true}
    ),
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
