from django import forms
from .models import Vehicle, Coffee, Event, VehComment, CofComment
from .widget import DateTimePickerInput
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm

class UserForm(forms.Form):
    username = forms.CharField(max_length=65)
    password = forms.CharField(max_length=65, widget=forms.PasswordInput)

class RegForm(UserCreationForm):
    class Meta:
        model = User
        fields = ['username','email','password1','password2'] 

class VehicleForm(forms.ModelForm):
    class Meta:
        model = Vehicle
        fields = ['brand', 'model', 'year', 'desc', 'image']

class VehicleCommentForm(forms.ModelForm):
    class Meta:
        model = VehComment
        fields = ['text']

class CoffeeForm(forms.ModelForm):
    class Meta:
        model = Coffee
        fields = ['brand', 'blend']

class CoffeeCommentForm(forms.ModelForm):
    class Meta:
        model = CofComment
        fields = ['text']

class DateTimeInput(forms.DateTimeInput):
    input_type = 'dateTime'

class EventForm(forms.ModelForm):
    class Meta:
        model = Event
        fields = ['place', 'description', 'dateTime']
    #widgets = {'dateTime': DateTimePickerInput()}
