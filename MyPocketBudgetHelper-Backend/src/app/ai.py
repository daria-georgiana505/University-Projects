import pandas as pd
from datetime import datetime
from decimal import Decimal

def parse_deadline(deadline_str):
    return datetime.strptime(deadline_str, '%d/%m/%Y')

def average_monthly_savings(transactions):
    transactions_data = [
        {
            "date_time": transaction.date_time,
            "amount": float(transaction.amount),
            "category": transaction.transactionTypeID.description,
        }
        for transaction in transactions
    ]
    transactions_df = pd.DataFrame(transactions_data)

    total_transactions = transactions_df['amount'].sum()

    earliest_date = transactions_df['date_time'].min()
    latest_date = transactions_df['date_time'].max()
    num_months = (latest_date.year - earliest_date.year) * 12 + latest_date.month - earliest_date.month + 1
    average_monthly_savings = total_transactions / num_months

    return average_monthly_savings

def calculate_savings_rate(savings, income):
    return (savings / income) * 100

def identify_outliers(transactions):
    transactions_data = [
        {
            "date_time": transaction.date_time,
            "amount": float(transaction.amount),
            "category": transaction.transactionTypeID.description,
        }
        for transaction in transactions
    ]

    transactions_df = pd.DataFrame(transactions_data)
    z_scores = (transactions_df['amount'] - transactions_df['amount'].mean()) / transactions_df['amount'].std()
    outliers = transactions_df[abs(z_scores) > 3]
    return outliers

def calculate_average_monthly_spending(transactions):
    transactions_data = [
        {
            "date_time": transaction.date_time,
            "amount": float(transaction.amount),
            "category": transaction.transactionTypeID.description,
        }
        for transaction in transactions
    ]

    transactions_df = pd.DataFrame(transactions_data)

    total_expenses = 0

    for transaction in transactions:
        if transaction.amount < 0:
            total_expenses += abs(transaction.amount)

    earliest_date = transactions_df['date_time'].min()
    latest_date = transactions_df['date_time'].max()
    num_months = (latest_date.year - earliest_date.year) * 12 + latest_date.month - earliest_date.month + 1
    average_monthly_spending = total_expenses / num_months
    return average_monthly_spending

def generate_recommendations(savings_rate, savings_goal, deadline, monthly_income, debt_obligations, outliers, transactions, currency):
    recommendations = []

    if savings_rate < 20:
        recommendations.append("Your savings rate is low. Try to reduce unnecessary expenses to save more.")

    deadline_parsed = parse_deadline(deadline)
    remaining_months = (deadline_parsed.year - datetime.now().year) * 12 + deadline_parsed.month - datetime.now().month
    if remaining_months <= 0:
        recommendations.append("Please provide a deadline that is at least the next month.")
        return recommendations

    average_monthly_spending = calculate_average_monthly_spending(transactions)
    max_monthly_savings = monthly_income - average_monthly_spending - (Decimal(debt_obligations) / remaining_months)

    required_monthly_savings = (savings_goal + Decimal(debt_obligations)) / remaining_months

    if required_monthly_savings > max_monthly_savings:
        recommendations.append("Your savings goal may not be achievable with your current income and spending habits.")

        if max_monthly_savings > 0:
            partial_goal = max_monthly_savings * remaining_months
            recommendations.append(f"You can save up to {partial_goal:.2f} {currency} with your current income and spending habits.")
            recommendations.append("Try reducing expenses and utilizing your current income more effectively in order to achieve your desired goal.")
        else:
            recommendations.append("Consider finding ways to increase your income or reduce your expenses to make your savings goal more attainable.")
    else:
        recommendations.append(f"To reach your savings goal on time, you need to save {required_monthly_savings:.2f} {currency} per month.")
        recommendations.append(f"You could add a {(monthly_income - required_monthly_savings):.2f} {currency} spending limit on the card to help you reach your goal.")

    if not outliers.empty:
        recommendations.append(
            "Your spending includes some unusually large transactions. Review them to ensure they align with your financial goals.")

    recommendations.append("Note that we also took into consideration the amount of money you have in savings.")
    return recommendations
