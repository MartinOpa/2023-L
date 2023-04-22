def is_palindrome(data):
    """
    Returns True if `data` is a palindrome and False otherwise.
    Hint:
        slicing is your friend, use it
    Example:
        is_palindrome('aba') == True
        is_palindrome('abc') == False
    """
    if data == '':
            return True
    for i in range(0, int(len(data)/2)):
        if data[i] == data[len(data)-i-1]:
            continue
        else: return False
    return True



def lex_compare(a, b):
    """
    Lexicographically compare `a` with `b` and return the smaller string.
    Implement the comparison yourself, do not use the `<` operator for comparing strings :)

    Example:
        lex_compare('a', 'b') == 'a'
        lex_compare('ahoj', 'buvol') == 'ahoj'
        lex_compare('ahoj', 'ahojky') == 'ahoj'
        lex_compare('dum', 'automobil') == 'automobil'
    """
    try:
        for i in range(0, len(a)):
            if ord(a[i]) < ord(b[i]):
                return a
            elif ord(a[i]) > ord(b[i]):
                return b
    except IndexError: # len(b) < len(a) && a[0:len(b)] == b[0:len(b)] -> return b
        return b
    return a # len(b) >= len(a) && a[0:len(b)] == b[0:len(b)] -> return a
    


def count_successive(string):
    """
    Go through the string and for each character, count how many times it appears in succession.
    Store the character and the count in a tuple and return a list of such tuples.

    Example:
          count_successive("aaabbcccc") == [("a", 3), ("b", 2), ("c", 4)]
          count_successive("aba") == [("a", 1), ("b", 1), ("a", 1)]
    """
    if string == "":
        return []
    a = 0
    ch = ""
    tup = []
    try:
        for i in range (0, len(string)):
            if string[i] == ch:
                a += 1
            else:
                if a != 0:
                    tup.append((ch, a))
                ch = string[i]
                a = 1
    finally:
        tup.append((ch, a))
    return tup
        

        



def find_positions(items):
    """
    Go through the input list of items and collect indices of each individual item.
    Return a dictionary where the key will be an item and its value will be a list of indices
    where the item was found.

    Example:
        find_positions(["hello", 1, 1, 2, "hello", 2]) == {
            2: [3, 5],
            "hello": [0, 4],
            1: [1, 2]
        }
    """
    if items == None:
        return items
    dict = {}
    for i in range(0, len(items)):
        if items[i] in dict:
            continue
        else:
            a = []
            ch = items[i]
            for j in range (0, len(items)):
                if items[j] == ch:
                    a.append(j)
            dict[ch] = a
    return dict


def invert_dictionary(dictionary):
    """
    Invert the input dictionary. Turn keys into values and vice-versa.
    If more values would belong to the same key, return None.

    Example:
        invert_dictionary({1: 2, 3: 4}) == {2: 1, 4: 3}
        invert_dictionary({1: 2, 3: 2}) is None
    """
    if dictionary == {}:
        return {}
    dict = {}

    for k, v in dictionary.items():
        if dict.get(v) != None:
            return None
        dict[v] = k
    return dict