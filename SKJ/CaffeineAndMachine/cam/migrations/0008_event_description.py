# Generated by Django 4.2.1 on 2023-05-28 19:29

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('cam', '0007_alter_cofcomment_text_alter_cofreply_text_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='event',
            name='description',
            field=models.TextField(default='dd'),
            preserve_default=False,
        ),
    ]
