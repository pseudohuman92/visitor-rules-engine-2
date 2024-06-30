#lang br
(require brag/support)
(define (make-tokenizer port)
  (define (next-token)
    (define card-lexer
      (lexer
       [(from/to "//" "\n") (next-token)]
       ["<card>"
            (token 'CARD-TOK)]
       [(from/to "<name>" "<name/>")
             (token 'NAME-TOK (string-trim (trim-ends "<name>" lexeme "<name/>")))]
       [(from/to "<type>" "<type/>")
             (token 'TYPE-TOK (string-trim (trim-ends "<type>" lexeme "<type/>")))]
       [(from/to "<subtype>" "<subtype/>")
             (token 'SUBTYPE-TOK (string-trim (trim-ends "<subtype>" lexeme "<subtype/>")))]
       [(from/to "<cost>" "<cost/>")
             (token 'COST-TOK
                    (string->number
                               (string-trim (trim-ends "<cost>" lexeme "<cost/>"))))]
       [(from/to "<knowledge-cost>" "<knowledge-cost/>")
             (token 'KNOWLEDGE-COST-TOK
                    (string-trim (trim-ends "<knowledge-cost>" lexeme "<knowledge-cost/>")))]
       [(from/to "<attack>" "<attack/>")
             (token 'ATTACK-TOK
                    (string->number
                     (string-trim (trim-ends "<attack>" lexeme "<attack/>"))))]
       [(from/to "<health>" "<health/>")
             (token 'HEALTH-TOK (string->number
                                 (string-trim (trim-ends "<health>" lexeme "<health/>"))))]
       [(from/to "<card-text>" "<card-text/>")
             (token 'CARD-TEXT-TOK (string-trim (trim-ends "<card-text>" lexeme "<card-text/>")))]
       [(from/to "<keyword>" "<keyword/>")
             (token 'KEYWORD-TOK (string-trim (trim-ends "<keyword>" lexeme "<keyword/>")))]
       [(from/to "<ability>" "<ability/>")
             (token 'ABILITY-TOK (string-trim (trim-ends "<ability>" lexeme "<ability/>")))]
       [(from/to "<set>" "<set/>")
             (token 'SET-TOK (string-trim (trim-ends "<set>" lexeme "<set/>")))]
       [any-char (next-token)]))
    (card-lexer port))  
  next-token)
(provide make-tokenizer)
