from django import forms

class DateTimePickerInput(forms.DateTimeInput):
    input_type = 'dateTime'

class TimePickerInput(forms.DateInput):
    input_type = 'date'

class DatePickerInput(forms.TimeInput):
    input_type = 'time'