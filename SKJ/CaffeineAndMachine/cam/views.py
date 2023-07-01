from django.shortcuts import render, redirect
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth import login, logout, authenticate
from .models import Vehicle, Coffee, VehComment, CofComment, Event, AttendsEvent
from .forms import UserForm, RegForm, VehicleForm, CoffeeForm, CoffeeCommentForm, EventForm, VehicleCommentForm

def userLogin(request):
    if request.method == 'GET':
        if request.user.is_authenticated:
            return redirect('vehicles')
        form = UserForm()
        return render(request, 'cam/login.html', {'form': form})
    elif request.method == 'POST':
        form = UserForm(request.POST)
        
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user = authenticate(request,username=username,password=password)
            if user:
                login(request, user)
                return redirect('vehicles')
        
        return render(request,'users/login.html',{'form': form})

def userRegister(request):
    if request.method == 'GET':
        form = RegForm()
        return render(request, 'cam/register.html', {'form': form})
    if request.method == 'POST':
        form = RegForm(request.POST) 
        if form.is_valid():
            user = form.save(commit=False)
            user.username = user.username.lower()
            user.save()
            return redirect('login')
        else:
            return render(request, 'cam/register.html', {'form': form})

def userLogOut(request):
    logout(request)
    return redirect('login')  

def userDetail(request):
    return render(request, 'cam/user.detail.html', {})

def addVeh(request):
    if request.method == 'GET':
        form = VehicleForm()
        return render(request, 'cam/vehicle.add.html', {'form': form})
    if request.method == 'POST':
        form = VehicleForm(request.POST, request.FILES)
        if form.is_valid():
            vehicle = form.save(commit=False)
            vehicle.user = request.user
            vehicle.save()
            return redirect('vehicles')

def addCof(request):
    if request.method == 'GET':
        form = CoffeeForm()
        return render(request, 'cam/coffee.add.html', {'form': form})
    if request.method == 'POST':
        form = CoffeeForm(request.POST)
        if form.is_valid():
            coffee = form.save(commit=False)
            coffee.user = request.user
            coffee.save()
            return redirect('cafe')

def browseVeh(request):
    posts = Vehicle.objects.all()
    return render(request, 'cam/vehicle.browse.html', {'posts': posts})

def browseCof(request):
    posts = Coffee.objects.all()
    return render(request, 'cam/coffee.browse.html', {'posts': posts})

def addEvt(request):
    if request.method == 'GET':
        form = EventForm()
        return render(request, 'cam/event.add.html', {'form': form})
    if request.method == 'POST':
        form = EventForm(request.POST)
        if form.is_valid():
            event = form.save(commit=False)
            event.organiser = request.user
            event.save()
            return redirect('events')

def browseEvt(request):
    posts = Event.objects.all()
    return render(request, 'cam/event.browse.html', {'posts': posts})

def detailVeh(request, postId):
    post = Vehicle.objects.get(pk=postId)
    comments = VehComment.objects.filter(post_id=post.id)
    commentForm = VehicleCommentForm()
    
    return render(request, 'cam/vehicle.detail.html', {'post': post, 'comments': comments, 
                                                    'commentForm': commentForm})
    
def postVehComment(request, postId):
    form = VehicleCommentForm(request.POST)
    if form.is_valid():
        comment = form.save(commit=False)
        comment.user = request.user
        comment.post = Vehicle.objects.get(pk=postId)
        comment.save()
        return redirect('vehicle', postId=postId)

def detailCof(request, postId):
    post = Coffee.objects.get(pk=postId)
    comments = CofComment.objects.filter(post_id=post.id)
    commentForm = CoffeeCommentForm()
    
    return render(request, 'cam/coffee.detail.html', {'post': post, 'comments': comments, 
                                                    'commentForm': commentForm})

def postCofComment(request, postId):
    form = CoffeeCommentForm(request.POST)
    if form.is_valid():
        comment = form.save(commit=False)
        comment.user = request.user
        comment.post = Coffee.objects.get(pk=postId)
        comment.save()
        return redirect('coffee', postId=postId)

def detailEvt(request, postId):
    post = Event.objects.get(pk=postId)
    return render(request, 'cam/event.detail.html', {'post': post})

def attendEvt(request, postId):
    post = Event.objects.get(pk=postId)
    attends = AttendsEvent(event = post, user = request.user)
    attends.save()
    return redirect('event', postId=postId)

def myEvents(request):
    events = AttendsEvent.objects.filter(user=request.user)
    post = []
    for item in events:
        post.append(item.event)
    return render(request, 'cam/event.myevents.html', {'posts': post})
