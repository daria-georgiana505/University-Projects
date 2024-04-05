from django.shortcuts import render
from django.db import transaction
from rest_framework.views import APIView
from .models import Card, Profile, Limit, LimitType, Transaction, TransactionType
from .serializers import CardSerializer, LimitSerializer, UserSerializer, TransactionSerializer, ProfileMoneyCurrencySerializer, ProfileSavingsCurrencySerializer, TransactionTypeSerializer, LimitTypeSerializer
from rest_framework.response import Response
from rest_framework.exceptions import NotFound
from rest_framework import status

from .ai import generate_recommendations, calculate_savings_rate, identify_outliers, average_monthly_savings
from decimal import Decimal

# Create your views here.

def convert_currencies(amount, from_currency, to_currency):
    if amount is None:
        raise ValueError("Amount cannot be None")

    amount = Decimal(str(amount))
    if from_currency == to_currency:
        return amount
    elif from_currency == 'EUR' and to_currency == 'USD':
        return amount * Decimal('1.08')
    elif from_currency == 'USD' and to_currency == 'EUR':
        return amount * Decimal('0.93')
    elif from_currency == 'EUR' and to_currency == 'RON':
        return amount * Decimal('4.98')
    elif from_currency == 'RON' and to_currency == 'EUR':
        return amount * Decimal('0.20')
    elif from_currency == 'USD' and to_currency == 'RON':
        return amount * Decimal('4.61')
    elif from_currency == 'RON' and to_currency == 'USD':
        return amount * Decimal('0.22')
    else:
        raise NotFound("Currency not found")

class CardsView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            cards = Card.objects.filter(profileID=profile)
            if not cards:
                return Response({"detail": "No card found"}, status=status.HTTP_404_NOT_FOUND)
        except Card.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving cards"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = CardSerializer(cards, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        serializer = CardSerializer(data=request.data)

        try:
            if serializer.is_valid(raise_exception=True):
                money_of_card = serializer.validated_data.get('money')
                currency_of_card = serializer.validated_data.get('currency')
                currency_of_profile = profile.currency_money

                try:
                    profile.money += convert_currencies(money_of_card, currency_of_card, currency_of_profile)
                except Exception as e:
                    return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

                serializer.save(profileID=profile)
                profile.save()
                return Response({"detail": "Card added successfully"}, status=status.HTTP_201_CREATED)
            else:
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class OneCardView(APIView):
    def get(self, request, card_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            card = Card.objects.get(profileID=profile, id=card_id)
        except Card.DoesNotExist:
            return Response({"detail": "Card not found"}, status=status.HTTP_404_NOT_FOUND)

        serializer = CardSerializer(card)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def delete(self, request, card_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            card = Card.objects.get(profileID=profile, id=card_id)
        except Card.DoesNotExist:
            return Response({"detail": "Card not found"}, status=status.HTTP_404_NOT_FOUND)

        money_of_card = card.money
        currency_of_card = card.currency
        currency_of_profile = profile.currency_money

        try:
            profile.money -= convert_currencies(money_of_card, currency_of_card, currency_of_profile)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        profile.save()
        card.delete()
        return Response({"detail": "Card deleted successfully"}, status=status.HTTP_200_OK)

class LimitsView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            limits = Limit.objects.filter(profileID=profile)
            if not limits:
                return Response({"detail": "No limit found"}, status=status.HTTP_404_NOT_FOUND)
        except Limit.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving limits"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = LimitSerializer(limits, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        serializer = LimitSerializer(data=request.data)
        try:
            if serializer.is_valid(raise_exception=True):
                serializer.save(profileID=profile)
                return Response({"detail": "Limit added successfully"}, status=status.HTTP_201_CREATED)
            else:
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class OneLimitView(APIView):
    def get(self, request, limit_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            limit = Limit.objects.get(profileID=profile, id=limit_id)
        except Limit.DoesNotExist:
            return Response({"detail": "Limit not found"}, status=status.HTTP_404_NOT_FOUND)

        serializer = LimitSerializer(limit)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def delete(self, request, limit_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            limit = Limit.objects.get(profileID=profile, id=limit_id)
        except Limit.DoesNotExist:
            return Response({"detail": "Limit not found"}, status=status.HTTP_404_NOT_FOUND)

        limit.delete()
        return Response({"detail": "Limit deleted successfully"}, status=status.HTTP_200_OK)

    def put(self, request, limit_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            limit = Limit.objects.get(profileID=profile, id=limit_id)
        except Limit.DoesNotExist:
            return Response({"detail": "Limit not found"}, status=status.HTTP_404_NOT_FOUND)

        serializer = LimitSerializer(limit, data=request.data)

        try:
            if serializer.is_valid(raise_exception=True):
                serializer.save(profileID=profile)
                return Response({"detail": "Limit updated successfully"}, status=status.HTTP_200_OK)
            else:
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class UserView(APIView):
    def get(self, request):
        user = request.user
        serializer = UserSerializer(user)
        return Response(serializer.data, status=status.HTTP_200_OK)

class ProfileMoneyView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        return Response({"money": profile.money, "currency": profile.currency_money}, status=status.HTTP_200_OK)

    def put(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        serializer = ProfileMoneyCurrencySerializer(data=request.data)

        try:
            if serializer.is_valid(raise_exception=True):
                from_currency = profile.currency_money
                to_currency = serializer.validated_data['currency_money']
                try:
                    profile.money = convert_currencies(profile.money, from_currency, to_currency)
                    profile.currency_money = to_currency
                except Exception as e:
                    return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

                profile.save()
                return Response({"detail": "Currency converted successfully"}, status=status.HTTP_200_OK)
            else:
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class ProfileSavingsView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        return Response({"savings": profile.savings, "currency": profile.currency_savings}, status=status.HTTP_200_OK)

    def put(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        serializer = ProfileSavingsCurrencySerializer(data=request.data)

        try:
            if serializer.is_valid(raise_exception=True):
                from_currency = profile.currency_savings
                to_currency = serializer.validated_data['currency_savings']
                try:
                    profile.savings = convert_currencies(profile.savings, from_currency, to_currency)
                    profile.currency_savings = to_currency
                except Exception as e:
                    return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

                profile.save()
                return Response({"detail": "Currency converted successfully"}, status=status.HTTP_200_OK)
            else:
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class TransferMoneyToSavings(APIView):
    def post(self, request, card_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            card = Card.objects.get(profileID=profile, id=card_id)
        except Card.DoesNotExist:
            return Response({"detail": "Card not found"}, status=status.HTTP_404_NOT_FOUND)

        try:
            amount = request.data.get('amount')
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        if amount is None:
            return Response({"detail": "Amount not provided"}, status=status.HTTP_400_BAD_REQUEST)

        try:
            amount = float(amount)
        except ValueError:
            return Response({"detail": "Invalid amount format"}, status=status.HTTP_400_BAD_REQUEST)

        if amount < 0:
            return Response({"detail": "Amount must be non-negative"}, status=status.HTTP_400_BAD_REQUEST)

        if amount > card.money:
            return Response({"detail": "Amount to transfer cannot be greater than the amount of money the card has"}, status=status.HTTP_400_BAD_REQUEST)

        with transaction.atomic():
            try:
                card.money -= Decimal(amount)

                from_currency = card.currency
                to_currency = profile.currency_money
                amount_for_total_money_profile = convert_currencies(amount, from_currency, to_currency)

                profile.money -= amount_for_total_money_profile

                to_currency = profile.currency_savings
                amount_for_savings = convert_currencies(amount, from_currency, to_currency)

                profile.savings += amount_for_savings

                card.save()
                profile.save()
            except Exception as e:
                return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        return Response({"detail": "Money transferred successfully"}, status=status.HTTP_200_OK)

class TransactionsView(APIView):
    def get(self, request, card_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            card = Card.objects.get(profileID=profile, id=card_id)
        except Card.DoesNotExist:
            return Response({"detail": "Card not found"}, status=status.HTTP_404_NOT_FOUND)

        try:
            transactions = Transaction.objects.filter(cardID=card)
            if not transactions:
                return Response({"detail": "No transactions found for card"}, status=status.HTTP_404_NOT_FOUND)
        except Transaction.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving transactions"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = TransactionSerializer(transactions, many=True)

        return Response(serializer.data, status=status.HTTP_200_OK)

class TransactionTypesView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            transaction_types = TransactionType.objects.all()
            if not transaction_types:
                return Response({"detail": "No transaction type found"}, status=status.HTTP_404_NOT_FOUND)
        except TransactionType.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving transaction types"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = TransactionTypeSerializer(transaction_types, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class LimitTypesView(APIView):
    def get(self, request):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            limit_types = LimitType.objects.all()
            if not limit_types:
                return Response({"detail": "No limit type found"}, status=status.HTTP_404_NOT_FOUND)
        except LimitType.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving limit types"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = LimitTypeSerializer(limit_types, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


class GenerateSavingsPlan(APIView):
    def get(self, request, card_id):
        user = request.user
        try:
            profile = Profile.objects.get(userID=user)
        except Profile.DoesNotExist:
            return Response({"detail": "Authentication failed"}, status=status.HTTP_404_NOT_FOUND)

        try:
            card = Card.objects.get(profileID=profile, id=card_id)
        except Card.DoesNotExist:
            return Response({"detail": "Card not found"}, status=status.HTTP_404_NOT_FOUND)

        try:
            transactions = Transaction.objects.filter(cardID=card)
            if not transactions:
                return Response({"detail": "No transactions found"}, status=status.HTTP_404_NOT_FOUND)
        except Transaction.DoesNotExist:
            return Response({"detail": "An error occurred while retrieving transactions"}, status=status.HTTP_400_BAD_REQUEST)

        try:
            deadline = request.data.get('deadline')
            savings_goal = request.data.get('savings_goal')
            monthly_income = request.data.get('monthly_income')
            debt_obligations = request.data.get('debt_obligations')

            monthly_savings = average_monthly_savings(transactions)

            savings_rate = calculate_savings_rate(monthly_savings, monthly_income)

            outliers = identify_outliers(transactions)

            currency_card = card.currency

            # take into account if the user already has money in savings
            currency_savings = profile.currency_savings

            try:
                savings_converted_to_card_currency = convert_currencies(profile.savings, currency_savings, currency_card)
            except Exception as e:
                return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

            if savings_converted_to_card_currency >= savings_goal:
                return Response({"detail": "You have enough money in savings to achieve your savings goal"}, status=status.HTTP_200_OK)
            savings_goal -= savings_converted_to_card_currency

            recommendations = generate_recommendations(savings_rate, savings_goal, deadline, monthly_income, debt_obligations, outliers, transactions, currency_card)
            return Response(recommendations, status=status.HTTP_200_OK)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)
