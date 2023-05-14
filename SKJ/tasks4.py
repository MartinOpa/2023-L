import time
import os
import string


class Vector:
    """
    Implement the methods below to create an immutable 3D vector class.

    Magic methods cheatsheet: https://rszalski.github.io/magicmethods
    """

    """
    Implement a constructor that takes three coordinates (x, y, z) and stores
    them as attributes with the same names in the Vector.
    Default value for all coordinates should be 0.
    Example:
        v = Vector(1.2, 3.5, 4.1)
        v.x # 1.2
        v = Vector(z=1) # == Vector(0, 0, 1)
    """
    def __init__(self, x = 0, y = 0, z = 0):
        self.x = x
        self.y = y
        self.z = z
    """
    Implement vector addition and subtraction using `+` and `-` operators.
    Both operators should return a new vector and not modify its operands.
    If the second operand isn't a vector, raise ValueError.
    Example:
        Vector(1, 2, 3) + Vector(4, 5, 6) # Vector(5, 7, 8)
        Vector(1, 2, 3) - Vector(4, 5, 6) # Vector(-3, -3, -3)
    Hint:
        You can use isinstance(object, class) to check whether `object` is an instance of `class`.
    """
    def __add__(self, other):
        if (not isinstance(other, Vector)):
            raise ValueError

        x = self.x + other.x
        y = self.y + other.y
        z = self.z + other.z
        return Vector(x, y, z)
    
    def __sub__(self, other):
        if (not isinstance(other, Vector)):
            raise ValueError

        x = self.x - other.x
        y = self.y - other.y
        z = self.z - other.z
        return Vector(x, y, z)
    """
    Implement the `==` comparison operator for Vector that returns True if both vectors have the same attributes.
    If the second operand isn't a vector, return False.
    Example:
        Vector(1, 1, 1) == Vector(1, 1, 1)  # True
        Vector(1, 1, 1) == Vector(2, 1, 1)  # False
        Vector(1, 2, 3) == 5                # False
    """

    def __eq__(self, other):
        if (not isinstance(other, Vector)):
            return False

        return self.x == other.x and self.y == other.y and self.z == other.z

    """
    Implement string representation of Vector in the form `(x, y, z)`.
    Example:
        str(Vector(1, 2, 3))    # (1, 2, 3)
        print(Vector(0, 0, 0))  # (0, 0, 0)
    """

    def __str__(self):
        return "(" + str(self.x) + ", " + str(self.y) + ", " + str(self.z) + ")"

    """
    Implement the iterator protocol for the vector.
    Hint:
        Use `yield`.
    Example:
        v = Vector(1, 2, 3)
        for x in v:
            print(x) # prints 1, 2, 3
    """
    def __iter__(self):
        yield self.x
        yield self.y
        yield self.z

    """
    Implement indexing for the vector, both for reading and writing.
    If the index is out of range (> 2), raise IndexError.
    Example:
        v = Vector(1, 2, 3)
        v[0] # 1
        v[2] # 3
        v[1] = 5 # v.y == 5

        v[10] # raises IndexError
    """
    def __getitem__(self, index):
        if index == 0:
            return self.x
        elif index == 1:
            return self.y
        elif index == 2:
            return self.z
        else: raise IndexError

    def __setitem__(self, index, value):
        if index == 0:
            self.x = value
        elif index == 1:
            self.y = value
        elif index == 2:
            self.z = value
        else: raise IndexError
  
        return Vector(self.x, self.y, self.z)

class UpperCaseDecorator:
    """
    Implement the `decorator` design pattern.
    UpperCaseDecorator should decorate a file which will be passed to its constructor.
    It should make all lower case characters written to the file uppercase and remove all
    upper case characters.
    It is enough to support the `write` and `writelines` methods of file.
    Example:
        with open("file.txt", "w") as f:
            decorated = UpperCaseDecorator(f)
            decorated.write("Hello World\n")
            decorated.writelines(["Nice to MEET\n", "YOU"])

        file.txt content after the above code is executed:
        ELLO ORLD
        ICE TO

    """
    def __init__(self, f):
        self.f = f
    
    def write(self, str):
        res = str.maketrans('', '', string.ascii_uppercase)
        self.f.write(str.translate(res).upper())

    def writelines(self, arr):
        for str in arr:
            self.write(str)


class Observable:
    """
    Implement the `observer` design pattern.
    Observable should have a `subscribe` method for adding new subscribers.
    It should also have a `notify` method that calls all of the stored subscribers and passes them its parameters.
    Example:
        obs = Observable()

        def fn1(x):
            print("fn1: {}".format(x))

        def fn2(x):
            print("fn2: {}".format(x))

        unsub1 = obs.subscribe(fn1)     # fn1 will be called everytime obs is notified
        unsub2 = obs.subscribe(fn2)     # fn2 will be called everytime obs is notified
        obs.notify(5)                   # should call fn1(5) and fn2(5)
        unsub1()                        # fn1 is no longer subscribed
        obs.notify(6)                   # should call fn2(6)
    """
    def __init__(self):
        self.subscribers = []

    def subscribe(self, subscriber):
        """
        Add subscriber to collection of subscribers.
        Return a function that will remove this subscriber from the collection when called.
        """
        self.subscribers.append(subscriber)

        def unsub():
            self.subscribers.remove(subscriber)

        return unsub

    def notify(self, *args, **kwargs):
        """
        Pass all parameters given to this function to all stored subscribers by calling them.
        """
        for sub in self.subscribers:
            sub(*args, **kwargs)

