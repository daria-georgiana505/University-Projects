"""
URL configuration for core project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path
from . import views


urlpatterns = [
    path('api/cards/', views.CardsView.as_view()),
    path('api/oneCard/<int:card_id>/', views.OneCardView.as_view()),
    path('api/limits/', views.LimitsView.as_view()),
    path('api/oneLimit/<int:limit_id>/', views.OneLimitView.as_view()),
    path('api/userDetails/', views.UserView.as_view()),
    path('api/profileMoney/', views.ProfileMoneyView.as_view()),
    path('api/profileSavings/', views.ProfileSavingsView.as_view()),
    path('api/transferMoneyToSavings/<int:card_id>/', views.TransferMoneyToSavings.as_view()),
    path('api/transactionsForCard/<int:card_id>/', views.TransactionsView.as_view()),
    path('api/transactionTypes/', views.TransactionTypesView.as_view()),
    path('api/limitTypes/', views.LimitTypesView.as_view()),
    path('api/generateSavingsPlan/<int:card_id>/', views.GenerateSavingsPlan.as_view()),
]