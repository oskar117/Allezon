Reverse Proxy - działa po stronie serwera, jest pośrednikiem dzięki którym
	dowonly klient może połączyć się z serwerem.
Forward proxy - jest pośrednikie, który pozwala klientom połączyć się z 
	każdym serwerem.

Nginx pozwala na postawienie serwera reverse proxy, dzięki czemu klient z
	zewnątrz może połączyć się z serwere wildfly, bez udostępniania tego 
	serwera publicznie.

OAuth 2.0 Authorization Code Grant Flow polega na tym, że strona logowania 
    google przekazuje do określonego servletu kod, który możemy potem wymienić na 
    token. Dzięki tokenowi możemy zarządać danych, które wcześnie określiliśmy przy 
    przekierowaniu na stronę logowania.