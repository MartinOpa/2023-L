# Generated by Django 4.2.1 on 2023-05-28 21:14

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('cam', '0011_alter_vehicle_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='vehicle',
            name='image',
            field=models.ImageField(default='trashcan.png', upload_to=''),
        ),
    ]
