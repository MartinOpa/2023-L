# -*- coding: utf-8 -*-

from tkinter import *
from math import sqrt
import tkinter.font
import os

class myApp:

    def prevod(self, event=None):
        v = float(self.ent_in.get())
        self.ent_out.delete(0, END)
        match self.dir.get():
            case 0:
                self.ent_out.insert(0, str(round(v*1.8 + 32, 2)))
            case 1:
                self.ent_out.insert(0, str(round((v-32)/1.8, 2)))
            case 2:
                self.ent_out.insert(0, str(round(v+273.15, 2)))
            case 3:
                self.ent_out.insert(0, str(round(v-273.15, 2)))

    def __init__(self, root):

        root.title('Převodník teplot')
        #root.resizable(False, False)
        root.bind('<Return>', self.prevod)        

        def_font = tkinter.font.nametofont("TkDefaultFont")
        def_font.config(size=16)

        self.left_frame = Frame(root)
        self.right_frame = Frame(root)
        
        self.dir = IntVar()
        self.dir.set(0)

        self.dir_frame = Frame(self.left_frame)

        self.r1 = Radiobutton(self.dir_frame, text="C -> F", variable=self.dir, value=0, padx=5, pady=5)
        self.r2 = Radiobutton(self.dir_frame, text="F -> C", variable=self.dir, value=1, padx=5, pady=5)
        self.r3 = Radiobutton(self.dir_frame, text="C -> K", variable=self.dir, value=2, padx=5, pady=5)
        self.r4 = Radiobutton(self.dir_frame, text="K -> C", variable=self.dir, value=3, padx=5, pady=5)

        self.r1.pack()
        self.r2.pack()
        self.r3.pack()
        self.r4.pack()
        
        self.ent_frame = Frame(self.left_frame)
        self.lbl_in = Label(self.ent_frame, text="Vstup", padx=5, pady=5)
        self.ent_in = Entry(self.ent_frame, width=10, font = def_font)
        self.ent_in.insert(0, '0')
        self.lbl_in.pack() 
        self.ent_in.pack(padx=100, pady=0, expand=1, fill='x')
       
        self.lbl_out = Label(self.ent_frame, text="Výstup", padx=5, pady=5)
        self.lbl_out.pack() 
        self.ent_out = Entry(self.ent_frame, width=10, font = def_font)
        self.ent_out.insert(0, '0')
        self.ent_out.pack(padx=100, pady=0, expand=1, fill='x')

        self.ca = Canvas(self.right_frame, width=300, height=400)
        self.photo = PhotoImage(file=os.path.abspath("URO/termPy/th.png"))
        self.ca.create_image(150, 200, image=self.photo)
        self.ca.pack()

        self.left_frame.pack(side="left", fill=X)
        self.right_frame.pack(side="right")
        self.dir_frame.pack(side="top")
        self.ent_frame.pack(side="bottom")
         
        self.bu = Button(self.ent_frame, text="Převod", command=lambda: self.prevod())
        self.bu.pack()

        self.ent_out.pack()
        self.ent_in.pack()

root = Tk()
app = myApp(root)
root.mainloop()

