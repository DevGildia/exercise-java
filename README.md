# Wymagania

## Waluta
Informacja o walucie nie jest istotna, gdyż wszystkie konta prowadzone są w tej samej walucie.

## Zasilanie konta
Konto można zasilić dowolną kwotą.
Kwota zasilenie nie może być mniejsza lub równa zero.

## Obciążanie konta
Kwota obciążenia nie może być mniejsza lub równa zero.
Kwota obciążenia nie może być większa niż dostępne środki na koncie.

## Historia operacji
Każda operacja na koncie musi zostać odnotowana.
Niezbędne informacje:
- rodzaj operacji
- kwota
- data

## Sprawozdanie
Dla każdego konta musi istnieć możliwość wygenerowania tekstowego sprawozdania zawierającego wszystkie operacje.

Format sprawozdania:

```
Date | Amount | Balance
2021-05-25T12:13:14 | +100 | 100 
2021-05-26T08:09:10 | -30 | 70 
2021-05-27T01:02:03 | +50 | 120
```
