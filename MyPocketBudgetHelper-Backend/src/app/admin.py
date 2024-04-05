from django.contrib import admin
from . import models


# Register your models here.
admin.site.register(models.Profile)
admin.site.register(models.Card)
admin.site.register(models.TransactionType)
admin.site.register(models.Transaction)
admin.site.register(models.LimitType)
admin.site.register(models.Limit)
