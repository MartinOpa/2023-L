#------------------------------------------------------------------------------#
# Kalkulacka                                                                   #
#------------------------------------------------------------------------------#

from tkinter import * 
from math import *
from tkinter import font

class MyApp:

    def insKey(self, znak):        
        if (znak == "reset"):
            self.strexpr = ""
            self.current = "0"
            self.expr.set(self.current)
        elif (znak == "softreset"):
            self.strexpr = self.strexpr[:-len(self.current)]
            self.current = "0"
            self.expr.set(self.current)
        elif (znak == "="):
            self.strexpr = str(eval(self.strexpr))
            self.current = "0"
            self.expr.set(self.strexpr)
            #self.la = Label(root, text=self.current, background="#ffffff", anchor=E, relief=SUNKEN, height=2, font=self.font)
        elif (znak == "*(-1)"):
            self.strexpr = self.strexpr + znak
            self.strexpr = str(eval(self.strexpr))
            self.current = self.strexpr
            self.expr.set(self.strexpr)
        elif (znak == "%"):
            self.strexpr = self.strexpr + "*0.01"
            self.strexpr = str(eval(self.strexpr))
            self.current = self.strexpr
            self.expr.set(self.strexpr)
        elif (znak == "+" or znak == "-" or znak == "*" or znak == "/"):
            self.current = "0"
            self.expr.set(eval(self.strexpr))
            self.strexpr = self.strexpr + znak
        else:
            if (znak == "." and self.current.count(".") > 0):
                return    
            if (self.current == "0" and znak != "."):
                self.current = znak
            else:
                self.current = self.current + znak

            self.strexpr = self.strexpr + znak
            self.expr.set(self.current)
            print(znak)

        
    def __init__(self, root):
        self.fo = StringVar()
        root.title("Calculator")
        self.font = font.Font(size=10, weight="normal")

        self.current = "0"
        self.strexpr = ""
        self.expr = StringVar()
        self.expr.set(self.strexpr)

        self.la = Entry(root, textvariable=self.expr, background="#ffffff", font=self.font)
        self.la.pack(fill=X, side=TOP, padx=8, pady=5)

        self.numbts = Frame(root)
        self.numbts.pack(fill=BOTH, expand=1, padx=4, pady=4)

        #self.menu = Menu(root)
        #self.menuitems = Menu(self.menu, tearoff=False)
        #self.menuitems.add_command(label="π", command=lambda: self.insKey(str(pi)))
        #self.menuitems.add_command(label="Euler's number", command=lambda: self.insKey(str(e)))
        #self.menuitems.add_command(label="Tau", command=lambda: self.insKey(str(tau)))
        #self.menuitems.add_command(label="Avogadro's constant", command=lambda: self.insKey(str(6.02214076 * (10^23))))
        #root.config(menu=self.menu)
        #self.menu.add_cascade(label="Item1", menu=self.menu)

        Grid.rowconfigure(root, 0, weight=1)
        Grid.columnconfigure(root, 0, weight=1)

        for y in range(6):
            Grid.rowconfigure(self.numbts, y, weight=1)
            for x in range(4):
                Grid.columnconfigure(self.numbts, x, weight=1)
                match y:
                    case 0:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="AC",width=5, height=2, font=self.font, command=lambda: self.insKey("reset"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text="C",width=5, height=2, font=self.font, command=lambda: self.insKey("softreset"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="+/-",width=5, height=2, font=self.font, command=lambda: self.insKey("*(-1)"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 3:
                                self.btn = Button(self.numbts, text="÷",width=5, height=2, font=self.font, command=lambda: self.insKey("/"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                    case 1:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="7",width=5, height=2, font=self.font, command=lambda: self.insKey("7"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text="8",width=5, height=2, font=self.font, command=lambda: self.insKey("8"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="9",width=5, height=2, font=self.font, command=lambda: self.insKey("9"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 3:
                                self.btn = Button(self.numbts, text="x",width=5, height=2, font=self.font, command=lambda: self.insKey("*"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                    case 2:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="4",width=5, height=2, font=self.font, command=lambda: self.insKey("4"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text="5",width=5, height=2, font=self.font, command=lambda: self.insKey("5"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="6",width=5, height=2, font=self.font, command=lambda: self.insKey("6"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 3:
                                self.btn = Button(self.numbts, text="-",width=5, height=2, font=self.font, command=lambda: self.insKey("-"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                    case 3:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="1",width=5, height=2, font=self.font, command=lambda: self.insKey("1"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text="2",width=5, height=2, font=self.font, command=lambda: self.insKey("2"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="3",width=5, height=2, font=self.font, command=lambda: self.insKey("3"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 3:
                                self.btn = Button(self.numbts, text="+",width=5, height=2, font=self.font, command=lambda: self.insKey("+"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                    case 4:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="0",width=5, height=2, font=self.font, command=lambda: self.insKey("0"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text=",",width=5, height=2, font=self.font, command=lambda: self.insKey("."))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="%",width=5, height=2, font=self.font, command=lambda: self.insKey("%"))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 3:
                                self.btn = Button(self.numbts, text="=",width=5, height=2, font=self.font, command=lambda: self.insKey("="))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                    case 5:
                        match x:
                            case 0:
                                self.btn = Button(self.numbts, text="π",width=5, height=2, font=self.font, command=lambda: self.insKey(str(pi)))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 1:
                                self.btn = Button(self.numbts, text="Euler",width=5, height=2, font=self.font, command=lambda: self.insKey(str(e)))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)
                            case 2:
                                self.btn = Button(self.numbts, text="Tau",width=5, height=2, font=self.font, command=lambda: self.insKey(str(tau)))
                                self.btn.grid(row=y*2, column=x, rowspan=2,sticky=W+E+N+S, padx=2, pady=2)                     
    
        self.login = Label(root, text="martin.opalka.st@vsb.cz", anchor=E, height=1, font=self.font)
        self.login.pack(fill=X, side=BOTTOM, padx=2, pady=2)

root = Tk()
app = MyApp(root)
root.mainloop()
