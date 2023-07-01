from django.db import models
from django.contrib.auth.models import User

class Vehicle(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    brand = models.CharField(max_length=50)
    model = models.CharField(max_length=50)
    year = models.PositiveIntegerField()
    desc = models.TextField()
    image = models.ImageField(upload_to='', default='trashcan.png')
    published = models.DateTimeField('post date', auto_now_add=True)

class VehComment(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    post = models.ForeignKey(Vehicle, on_delete=models.CASCADE)
    text = models.CharField(max_length=100)
    published = models.DateTimeField('comment date', auto_now_add=True)

class Coffee(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    brand = models.CharField(max_length=50)
    blend = models.CharField(max_length=50)
    published = models.DateTimeField('post date', auto_now_add=True)

class CofComment(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    post = models.ForeignKey(Coffee, on_delete=models.CASCADE)
    text = models.CharField(max_length=100)
    published = models.DateTimeField('comment date', auto_now_add=True)

class CofReply(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    comment = models.ForeignKey(CofComment, on_delete=models.CASCADE)
    text = models.CharField(max_length=100)
    published = models.DateTimeField('reply date', auto_now_add=True)

class Event(models.Model):
    organiser = models.ForeignKey(User, on_delete=models.DO_NOTHING)
    dateTime = models.DateTimeField('event date')
    place = models.TextField()
    description = models.TextField()
    
class AttendsEvent(models.Model):
    event = models.ForeignKey(Event, on_delete=models.DO_NOTHING)
    user = models.ForeignKey(User, on_delete=models.DO_NOTHING)
