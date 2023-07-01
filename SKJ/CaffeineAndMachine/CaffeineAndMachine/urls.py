"""
URL configuration for CaffeineAndMachine project.

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
from django.contrib import admin
from django.urls import path
from django.conf import settings
from django.conf.urls.static import static
from cam import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('login/', views.userLogin, name='login'),
    path('logout/', views.userLogOut, name='logout'),
    path('register/', views.userRegister, name='register'),
    path('browseVehicles/', views.browseVeh, name='vehicles'),
    path('browseVehicles/<int:postId>', views.detailVeh, name='vehicle'),
    path('browseVehicles/<int:postId>/postVehComment', views.postVehComment, name='postVehComment'),
    path('addVehicle/', views.addVeh, name='addVehicle'),
    path('browseCoffee/', views.browseCof, name='cafe'),
    path('browseCoffee/<int:postId>', views.detailCof, name='coffee'),
    path('browseCoffee/<int:postId>/postCofComment', views.postCofComment, name='postCofComment'),
    path('addCoffee/', views.addCof, name='addCoffee'),
    path('browseEvents/', views.browseEvt, name='events'),
    path('browseEvents/<int:postId>', views.detailEvt, name='event'),
    path('addEvent/', views.addEvt, name='addEvent'),
    path('browseEvents/<int:postId>/attend', views.attendEvt, name='attendEvt'),
    path('myEvents/', views.myEvents, name='myEvents')
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
