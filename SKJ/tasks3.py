import math
import re
import os

def create_appender(default_value):
    """
    Create an empty list. Return a function which will append its only parameter to this list
    when called and then return a copy of the modified list.
    If no parameter is passed, it will add `default_value` to the list.

    Example:
        appender = create_appender(5)
        appender(1) # [1]
        appender(2) # [1, 2]
        appender() # [1, 2, 5]

        appender2 = create_appender(0)
        appender2(2) # [2]
    """
    lst = []
    def appender(value=default_value):
        lst.append(value)
        return list(lst)
    return appender


def fibonacci_closure():
    """
    Return a closure (function) that will generate elements of the Fibonacci sequence (starting
    from 1) when called repeatedly.
    Example:
        g = fibonacci_closure()
        g() # 1
        g() # 1
        g() # 2
        g() # 3
        ...
    """
    a = 0
    b = 1
    def g():
        nonlocal a, b
        c = a + b
        a = b
        b = c
        return a
    return g


def prime_generator(count):
    """
    Return a generator that will generate 'count' of prime numbers, starting from 2.
    """
    i = 0
    res = []
    current = 2
    while (i < count):
        prime = True
        for x in range(2, int(math.sqrt(current) + 1)):
            if current % x == 0: 
                prime = False
                break
        
        if prime:
            res.append(current)
            i += 1

        current += 1
    return res


def word_extractor(sentence: str):
    """
    Return a generator that will iterate through individual words from the input sentence.
    Words are separated by the following separators: space (' '), dot ('.'), exclamation mark ('!')
    and question mark ('?'). Skip empty words and separators.

    If you encounter the word "stop", ignore the word and stop the generator.

    Example:
        sentence = "Hello world. How are you doing today? I'm doing fine!"
        for word in word_extractor(sentence):
            print(i)
        # "Hello", "world", "How", "are", "you", "doing", "today", ...

        sentence = "Hello world stop this is not extracted anymore."
        for word in word_extractor(sentence):
            print(i)
        # "Hello", "world"
    """
    lst = re.split("[?! .]", sentence)
    for x in lst:
        if x != "stop":
            if x != "":
                yield x
        else:
            break


def transform_file(src: str, dst: str, keyword: str):
    """
    Open file located at `src`, keep only lines that contain the `keyword`, sort them in descending
    order and write them to file located at `dst`.

    If `keyword` is empty, raise an Exception with message "invalid keyword".
    If `src` does not exist, raise an Exception with message "file not found".

    Example:
        transform_file('in.txt', 'out.txt', 'or')

        in.txt:
        barrens
        stormwind
        gondor
        ashenvale
        hogwarts
        yavin
        coruscant

        out.txt:
        stormwind
        gondor
        coruscant
    """
    class InvalidKeyword(Exception):
        "invalid keyword"
        pass

    class FileNotFound(Exception):
        "file not found"
        pass

    if keyword == None or keyword == "":
        raise InvalidKeyword("invalid keyword")
    
    if not os.path.isfile(src):
        raise FileNotFound("file not found")

    with open(src) as f:
        temp = f.read().splitlines()
        res = []
        for a in temp:
            if keyword in a:
                res.append(a)
                
        res.sort()
        res.reverse()

        print(res)
    
    with open(dst, "w") as f:
        f.write(os.linesep.join(res))


def tree_walker(tree, order):
    """
    Write a generator that traverses `tree` in the given `order` ('inorder', 'preorder' or 'postorder').
    You should know this from 'Algoritmy II'.
    The tree is represented with nested tuples (left subtree, value, right subtree).
    If there is no subtree, it will be marked as None.
    Example:
        tree = (((None, 8, None), 3, (None, 4, None)), 5, (None, 1, None))
            5
           / \
          3   1
         / \
        8   4
        list(tree_walker(tree, 'inorder')) == [8, 3, 4, 5, 1]
        list(tree_walker(tree, 'preorder')) == [5, 3, 8, 4, 1]
        list(tree_walker(tree, 'postorder')) == [8, 4, 3, 1, 5]
    """
    if order == 'preorder':
        yield tree[1]
        if tree[0] is not None:
            yield from tree_walker(tree[0], order)
        if tree[2] is not None:
            yield from tree_walker(tree[2], order)
    elif order == 'inorder':
        if tree[0] is not None:
            yield from tree_walker(tree[0], order)
        yield tree[1]
        if tree[2] is not None:
            yield from tree_walker(tree[2], order)
    elif order == 'postorder':
        if tree[0] is not None:
            yield from tree_walker(tree[0], order)
        if tree[2] is not None:
            yield from tree_walker(tree[2], order)
        yield tree[1]
        