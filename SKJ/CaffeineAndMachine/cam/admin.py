from django.contrib import admin

from .models import Vehicle, Coffee, VehComment, CofComment, Event, AttendsEvent

admin.site.register(Vehicle)
admin.site.register(Coffee)
admin.site.register(VehComment)
admin.site.register(CofComment)
admin.site.register(Event)
admin.site.register(AttendsEvent)
