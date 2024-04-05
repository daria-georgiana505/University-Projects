from rest_framework import serializers
from .models import User, Card, Limit, Profile, Transaction, TransactionType, LimitType

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username', 'first_name', 'last_name', 'email']

class ProfileMoneyCurrencySerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = ['currency_money']

class ProfileSavingsCurrencySerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = ['currency_savings']

class CardSerializer(serializers.ModelSerializer):
    expiration_date = serializers.DateField(format="%m/%Y", input_formats=["%m/%Y"])
    class Meta:
        model = Card
        fields = ['id', 'card_number', 'expiration_date', 'type', 'money', 'currency']

class LimitSerializer(serializers.ModelSerializer):
    period_start = serializers.DateField(format="%d/%m/%Y", input_formats=["%d/%m/%Y"])
    period_end = serializers.DateField(format="%d/%m/%Y", input_formats=["%d/%m/%Y"])
    class Meta:
        model = Limit
        fields = ['id', 'amount', 'recurring', 'name', 'description', 'period_start', 'period_end', 'currency', 'LimitTypeID']

class TransactionSerializer(serializers.ModelSerializer):
    date_time = serializers.DateTimeField(format="%d/%m/%Y %H:%M:%S", input_formats=["%d/%m/%Y %H:%M:%S"])

    class Meta:
        model = Transaction
        fields = ['id', 'amount', 'date_time', 'trader', 'transactionTypeID']

class TransactionTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = TransactionType
        fields = '__all__'

class LimitTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = LimitType
        fields = '__all__'