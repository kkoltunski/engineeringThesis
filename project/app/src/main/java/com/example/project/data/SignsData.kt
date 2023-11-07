package com.example.project.data

//val loginForbiddenSigns: CharSequence = "$=_'-+,<>{}()\""
//val passwordRequiredSigns: CharSequence = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"

val loginForbiddenSigns: Set<Char> =
    setOf(
        ' ',
        '&',
        '=',
        '_',
        '\'',
        '-',
        '+',
        ',',
        '<',
        '>',
        '{',
        '}',
        '(',
        ')'
    )

val passwordRequiredSigns: Set<Char> =
    setOf(
        '!',
        '"',
        '#',
        '$',
        '%',
        '&',
        '\'',
        '(',
        ')',
        '*',
        '+',
        ',',
        '-',
        '.',
        '/',
        ':',
        ';',
        '<',
        '>',
        '=',
        '?',
        '@',
        '[',
        '\\',
        ']',
        '^',
        '_',
        '`',
        '{',
        '}',
        '|',
        '~',
        )